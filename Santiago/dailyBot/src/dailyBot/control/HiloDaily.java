package dailyBot.control;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HiloDaily extends Thread 
{
	public RunnableDaily runnable;
	
	public HiloDaily(RunnableDaily r, long intervalo)
	{
		super(((Runnable) r));
		runnable = r;
		runnable.ponerIntervaloActulizacion(intervalo);
	}
	
	public static final Random random = new Random();
	
	public static void sleep(long millis)
	{
		try
		{
			millis += random.nextInt(101);
			Thread.sleep(millis);
		}
		catch(InterruptedException e)
		{
			String st = "";
			for(StackTraceElement ste : e.getStackTrace())
				st += "\n" + ste;
			Error.agregar(e.getMessage() + " error de interrupcion en: " + st);
		}
	}
	
	static class LockSafe extends ReentrantReadWriteLock.WriteLock
	{
		private static final long serialVersionUID = -2449883455415346160L;
		
		ReentrantReadWriteLock rwl;
		
		public LockSafe(ReentrantReadWriteLock rwl)
		{
			super(rwl);
			this.rwl = rwl;
		}
		
		@Override
		public void lock() 
		{
			int numLocks = rwl.getReadHoldCount();
			while(rwl.getReadHoldCount() != 0)
				rwl.readLock().unlock();
			super.lock();
			while(rwl.getReadHoldCount() < numLocks)
				rwl.readLock().lock();
		}
	};
	
	public static Lock darWriteLockSeguro(ReentrantReadWriteLock rwl)
	{
		return new LockSafe(rwl);
	}
}
package control;

import java.util.Random;

public class HiloDaily extends Thread 
{
	public RunnableDaily runnable;
	
	public HiloDaily(RunnableDaily r, long intervalo)
	{
		super(((Runnable) r));
		runnable = r;
		runnable.intervalorActualizacion = intervalo;
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
}

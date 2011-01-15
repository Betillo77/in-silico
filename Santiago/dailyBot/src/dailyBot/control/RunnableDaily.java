package dailyBot.control;

import java.util.concurrent.atomic.AtomicLong;

public abstract class RunnableDaily implements Runnable
{
	private AtomicLong ultimaActualizacion = new AtomicLong(System.currentTimeMillis());
	private AtomicLong intervalorActualizacion = new AtomicLong();
	
	public void ponerUltimaActulizacion(long nueva)
	{
		ultimaActualizacion.getAndSet(nueva);
	}
	
	public long darUltimaActualizacion()
	{
		return ultimaActualizacion.getAndAdd(0);
	}
	
	public void ponerIntervaloActulizacion(long nueva)
	{
		intervalorActualizacion.getAndSet(nueva);
	}
	
	public long darIntervaloActualizacion()
	{
		return intervalorActualizacion.getAndAdd(0);
	}
}
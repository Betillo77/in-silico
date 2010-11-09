package control;

public abstract class RunnableDaily implements Runnable
{
	public volatile long ultimaActualizacion = System.currentTimeMillis();
	public long intervalorActualizacion;
}

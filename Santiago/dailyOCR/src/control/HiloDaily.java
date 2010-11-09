package control;

public class HiloDaily extends Thread 
{
	public RunnableDaily runnable;
	
	public HiloDaily(RunnableDaily r, long intervalo)
	{
		super(((Runnable) r));
		runnable = r;
		runnable.intervalorActualizacion = intervalo;
	}
}

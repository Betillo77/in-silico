package modelo;

import java.io.File;
import java.io.IOException;

import control.AdministradorHilos;
import control.Error;

public class Proceso 
{
	String path;
	Process proceso;
	
	public Proceso(String p) throws IOException
	{
		path = p;
		iniciar();
	}

	public void cerrar()
	{
		proceso.destroy();
	}
	
	private void iniciar() throws IOException
	{
		ProcessBuilder pb = new ProcessBuilder("");
		pb.directory(new File("/home/santiago/Desktop/dailyOCR/" + path));
		pb.command("wine", "terminal.exe");
		proceso = pb.start();
		final Thread hiloMonitor = new Thread(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Thread.sleep(30000);
					proceso.waitFor();
					Error.agregar("Reiniciando proceso: " + path);
					Thread.sleep(10000);
					AdministradorHilos.eliminarHilo("Monitor proceso " + path);
					iniciar();
				} 
				catch (Exception e)
				{
					Error.agregar(e.getMessage() + " Error en el vigilante del proceso: " + path);
				}
			}
		});
		hiloMonitor.setName("Monitor proceso " + path);
		AdministradorHilos.agregarHilo(hiloMonitor);
	}
}

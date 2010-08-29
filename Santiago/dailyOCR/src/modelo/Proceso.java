package modelo;

import java.io.File;
import java.io.IOException;
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
		new Thread(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					proceso.waitFor();
					iniciar();
				} 
				catch (Exception e)
				{
					Error.agregar(e.getMessage() + " Error en el vigilante del proceso: " + path);
				}
			}
		}).start();
	}
}

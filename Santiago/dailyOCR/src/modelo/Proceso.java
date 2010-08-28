package modelo;

import java.io.File;
import java.io.IOException;

public class Proceso 
{
	String path;
	Process proceso;
	
	public Proceso(String p) throws IOException
	{
		path = p;
		iniciar();
	}

	public void reiniciar() throws IOException 
	{
		proceso.destroy();
		iniciar();
	}
	
	public void iniciar() throws IOException
	{
		ProcessBuilder pb = new ProcessBuilder("");
		pb.directory(new File("/home/santiago/Desktop/dailyOCR/" + path));
		pb.command("wine", "terminal.exe");
		proceso = pb.start();
	}
}

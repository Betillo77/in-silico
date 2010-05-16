package control;

import java.io.File;
import java.io.FileWriter;

import control.conexion.ConexionServidorMensajes;

public class Error 
{
	public static synchronized void agregar(String error)
	{
		try
		{
			FileWriter fw = new FileWriter(new File("Error.txt"), true);
			fw.write(error);
			fw.write(System.getProperty("line.separator"));
			ConexionServidorMensajes.enviarMensaje("DailyOCR", error);
			fw.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error en el manejador de errores");
		}
	}

	public static void agregar(String error, boolean b) 
	{
		try
		{
			FileWriter fw = new FileWriter(new File("Error.txt"), true);
			fw.write(error);
			fw.write(System.getProperty("line.separator"));
			fw.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error en el manejador de errores");
		}
	}
}
package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import control.conexion.ConexionServidorMensajes;

public class Error 
{
	static int hora = 0;
	static int numeroErrores = 0;
	
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
		chequearHora();
	}

	public static synchronized void agregar(String error, boolean b) 
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
		chequearHora();
	}
	
	private static void chequearHora() 
	{
		Calendar c = Calendar.getInstance();
		int h = c.get(Calendar.HOUR_OF_DAY);
		if(hora == h)
		{
			numeroErrores++;
		}
		else
		{
			hora = h;
			numeroErrores = 1;
		}
		if(numeroErrores == 50)
		{
			Error.agregar("50 errores en una hora, reiniciando");
			try 
			{
				Runtime.getRuntime().exec("shutdown now -r");
			} 
			catch (IOException e) 
			{
				Error.agregar(e.getMessage());
			}
		}
	}
}
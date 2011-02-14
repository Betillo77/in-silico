package dailyBot.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JOptionPane;

import dailyBot.control.conexion.ConexionServidorMensajes;
import dailyBot.modelo.Estrategia.IdEstrategia;
import dailyBot.modelo.Proveedor.IdProveedor;

public class Error 
{
	private static int hora = 0;
	private static int numeroErrores = 0;
	
	public static void agregar(String error)
	{
		enviar("DailyBot-error", error, true);
		chequearHora();
	}
	
	public static void agregarInfo(String info)
	{
		enviar("DailyBot-info", info, true);
	}

	public static void agregarSinCorreo(String error) 
	{
		enviar("DailyBot-error", error, false);
		chequearHora();
	}
	
	public static void agregarConTitulo(String titulo, String info)
	{
		enviar("DailyBot-" + titulo, info, true);
	}
	
	public static void agregarRMI(String error) 
	{
		JOptionPane.showMessageDialog(null, error);
	}
	
	private static void enviar(String titulo, String mensaje, boolean correo)
	{
		try
		{
			FileWriter fw = new FileWriter(new File("Error.txt"), true);
			fw.write(mensaje);
			fw.write(System.getProperty("line.separator"));
			if(correo)
				ConexionServidorMensajes.enviarMensaje(titulo, mensaje);
			fw.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error en el gestionador de errores");
		}
	}
	
	private static void chequearHora() 
	{
		Calendar c = Calendar.getInstance();
		int h = c.get(Calendar.HOUR_OF_DAY);
		int numeroErroresAnt;
		synchronized(Error.class.getClass())
		{
			if(hora == h)
			{
				numeroErrores++;
			}
			else
			{
				hora = h;
				numeroErrores = 1;
			}
			numeroErroresAnt = numeroErrores;
		}
		if(numeroErroresAnt == 200)
		{
			enviar("DailyOCR-error", "200 errores en una hora, reiniciando", true);
			reiniciar();
		}
	}
	
	public static void reiniciar()
	{
		for(IdProveedor p : IdProveedor.values())
			p.darProveedor().escribir();
		for(IdEstrategia e : IdEstrategia.values())
			e.darEstrategia().escribir();
		reiniciarEquipo();
	}
	
	public static void reiniciarSinPersistir()
	{
		reiniciarEquipo();
	}
	
	private static void reiniciarEquipo()
	{
		try 
		{
			String mensaje = "Reiniciando, stack:\n";
			StackTraceElement[] st = Thread.currentThread().getStackTrace();
			if(st != null)
				for(StackTraceElement s : st)
				{
					mensaje += s + "\n";
				}
			Error.agregar(mensaje);
			HiloDaily.sleep(60000);
			Runtime.getRuntime().exec("shutdown now -r");
			System.exit(0);
			throw(new RuntimeException());
		} 
		catch (IOException e) 
		{
			Error.agregar("Error reiniciando equipo " + e.getMessage());
			System.exit(0);
		}
	}
}
package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
import control.conexion.ConexionServidorMensajes;

public class Error 
{
	private static int hora = 0;
	private static int numeroErrores = 0;
	
	public static void agregar(String error)
	{
		enviar("DailyOCR-error", error, true);
		chequearHora();
	}
	
	public static void agregarInfo(String info)
	{
		enviar("DailyOCR-info", info, true);
	}

	public static void agregarSinCorreo(String error) 
	{
		enviar("DailyOCR-error", error, false);
		chequearHora();
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
	
	private static void chequearHora() 
	{
		Calendar c = Calendar.getInstance();
		int h = c.get(Calendar.HOUR_OF_DAY);
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
			if(numeroErrores == 200)
			{
				Error.agregar("200 errores en una hora, reiniciando");
				reiniciar();
			}
		}
	}

	public static void agregarRMI(String string) {
		// TODO Auto-generated method stub
		
	}
}
package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import control.AdministradorHilos;
import control.Error;
import control.HiloDaily;
import control.RunnableDaily;

public class Proceso 
{
	private String path;
	private Process proceso;
	private Socket socket = null;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	public Proceso(String p)
	{
		try
		{
			path = p;
			HiloDaily hiloMonitor = new HiloDaily(new RunnableDaily() 
			{
				public void run() 
				{
					try 
					{
						while(true)
						{
							ProcessBuilder pb = new ProcessBuilder("");
							pb.directory(new File("/home/santiago/Desktop/dailyOCR/" + path));
							pb.command("wine", "terminal.exe");
							proceso = pb.start();
							Error.agregarInfo("Iniciando proceso " + path);
							iniciarSocket();
							Error.agregarInfo("Conexion establecida " + path);
							Thread.sleep(30000);
							proceso.waitFor();
							Error.agregar("Reiniciando proceso y socket: " + path);
							try
							{
								proceso.destroy();
							}
							catch(Exception e)
							{
								Error.agregar("Proceso no se pudo cerrar en: " + path + ", reiniciando");
								Error.reiniciar();
							}
							try
							{
								cerrarSocket();
							}
							catch(Exception e)
							{
								Error.agregar("Error reiniciando proceso, reinicando equipo");
								Error.reiniciar();
							}
							Thread.sleep(100000);
						}
					} 
					catch (Exception e)
					{
						Error.agregar(e.getMessage() + " error en el vigilante del proceso: " + path);
					}
				}
			}, Long.MAX_VALUE);
			hiloMonitor.setName("Monitor proceso " + path);
			AdministradorHilos.agregarHilo(hiloMonitor);
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error iniciando proceso, reinicando equipo");
			Error.reiniciar();
		}
	}

	private synchronized void iniciarSocket()
	{
		 String s = null;
		 try
		 {
			 Thread.sleep(100000);
			 Scanner sc = new Scanner(new File(path + "port.txt"));
		     socket = new Socket(s, sc.nextInt());
		     socketOut = new PrintWriter(socket.getOutputStream(), true);
		     socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 }
		 catch(Exception e)
		 {
			 Error.agregar(e.getMessage() + " error iniciando socket, " + path);
			 Error.reiniciar();
		 }
	}
	
	public synchronized void escribir(String mensaje)
	{
		try
		{
			socketOut.println(mensaje);
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error escribiendo en el socket, " + path);
		}
	}
	
	public synchronized String leer()
	{
		try
		{
			final Object espera = new Object();
			final StringBuffer salida = new StringBuffer();
			new Thread(new Runnable()
			{
				@Override
				public void run() 
				{
					try
					{
						socket.setSoTimeout(600000);
						String resultado = socketIn.readLine();
						if(resultado.equals(""))
							resultado = " ";
						salida.append(resultado);
						synchronized(espera)
						{
							espera.notifyAll();
						}
					}
					catch(Exception e)
					{
						Error.agregar(e.getMessage() + " error leyendo en el socket, " + path);
					}
				}	
			}).start();
			long tiempoInicio = System.currentTimeMillis();
			while((System.currentTimeMillis() - tiempoInicio < 600000) && salida.length() == 0)
			{
				synchronized(espera)
				{
					try 
					{
						espera.wait(20000);
					}
					catch (InterruptedException e) 
					{
						Error.agregar("Excepcion de interrupcion en el proceso: " + path);
					}
				}
			}
			String resultado = salida.toString();
			if(salida.length() == 0)
			{
				Error.agregar("Error de lectura en el socket, " + path + " no se leyo nada");
				resultado = null;
			}
			return resultado.equals(" ") ? "" : resultado;
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + ", error de lectura en el socket: " + path);
			return null;
		}
	}
	
	private synchronized void cerrarSocket() throws IOException
	{
		socket.close();
	}
	
	public synchronized void cerrar()
	{
		proceso.destroy();
	}
}

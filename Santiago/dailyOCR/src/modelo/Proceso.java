package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import control.AdministradorHilos;
import control.Error;

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
			Thread hiloMonitor = new Thread(new Runnable() 
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
							Error.agregar("Iniciando proceso " + path);
							iniciarSocket();
							Error.agregar("Conexion establecida " + path);
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
								reiniciarEquipo();
							}
							try
							{
								cerrarSocket();
							}
							catch(Exception e)
							{
								Error.agregar("Error reiniciando proceso, reinicando equipo");
								reiniciarEquipo();
							}
							Thread.sleep(10000);
						}
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
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error iniciando proceso, reinicando equipo");
			reiniciarEquipo();
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
			 reiniciarEquipo();
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
			reiniciarEquipo();
		}
	}
	
	public synchronized String leer(int tiempoEspera)
	{
		try
		{
			socket.setSoTimeout(tiempoEspera);
			return socketIn.readLine();
		}
		catch(SocketTimeoutException e)
		{
			Error.agregar("Socket no respondio en " + tiempoEspera + " esperando");
			cerrar();
			return "";
		}
		catch(IOException e)
		{
			Error.agregar(e.getMessage() + " error leyendo en el socket, " + path);
			cerrar();
			return "";
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
	
	private void reiniciarEquipo()
	{
		 try 
		 {
			 Runtime.getRuntime().exec("shutdown now -r");
			 System.exit(0);
		 } 
		 catch (IOException e1) 
		 {
			 Error.agregar("Error reiniciando equipo " + e1.getMessage());
			 System.exit(0);
		 }
	}
}

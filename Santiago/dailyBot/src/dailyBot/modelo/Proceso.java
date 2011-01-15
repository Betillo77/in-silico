package dailyBot.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import dailyBot.control.AdministradorHilos;
import dailyBot.control.Error;
import dailyBot.control.HiloDaily;
import dailyBot.control.RunnableDaily;

public class Proceso 
{
	private String path;
	private Process proceso;
	private ExecutorService executor;
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private boolean cerrado = false;
	private ReentrantLock lock = new ReentrantLock(true);
	
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
							HiloDaily.sleep(30000);
							proceso.waitFor();
							lock.lock();
							try
							{
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
								if(cerrado)
									return;
							}
							finally
							{
								lock.unlock();
							}
							Error.agregar("Reiniciando proceso y socket: " + path);
							HiloDaily.sleep(100000);
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
			executor = Executors.newSingleThreadExecutor();
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error iniciando proceso, reinicando equipo");
			Error.reiniciar();
		}
	}

	private void iniciarSocket()
	{
		lock.lock();
		try
		{
			HiloDaily.sleep(100000);
			Scanner sc = new Scanner(new File(path + "port.txt"));
			socket = new Socket((String) null, sc.nextInt());
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error iniciando socket, " + path);
			Error.reiniciar();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public void escribir(String mensaje)
	{
		lock.lock();
		try
		{
			socketOut.println(mensaje);
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error escribiendo en el socket, " + path);
		}
		finally
		{
			lock.unlock();
		}
	}
	
	class CallableSocket implements Callable <String>
	{
		@Override
		public String call() throws Exception 
		{
			lock.lock();
			try
			{
				socket.setSoTimeout(600000);
				String resultado = socketIn.readLine();
				if(resultado.equals(""))
					resultado = " ";
				return resultado;
			}
			finally
			{
				lock.unlock();
			}
		}
	}
	
	public String leer()
	{
		try
		{
			Future <String> futureResultado = executor.submit(new CallableSocket());
			String resultado = futureResultado.get(600, TimeUnit.SECONDS);
			return resultado.equals(" ") ? "" : resultado;
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + ", error de lectura en el socket: " + path);
			return null;
		}
	}
	
	private void cerrarSocket() throws IOException
	{
		lock.lock();
		try
		{
			socket.close();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public void cerrar()
	{
		lock.lock();
		try
		{
			proceso.destroy();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public void cerrarProceso()
	{
		lock.lock();
		try
		{
			cerrado = true;
			proceso.destroy();
		}
		finally
		{
			lock.unlock();
		}
	}
}
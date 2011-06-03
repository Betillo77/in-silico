package dailyBot.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;
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
import dailyBot.control.Propiedades;
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
							lock.lock();
							try
							{
								Calendar c = Calendar.getInstance();
								int dia = c.get(Calendar.DAY_OF_WEEK);
								while(dia == Calendar.SATURDAY || dia == Calendar.SUNDAY)
								{
									int hora = c.get(Calendar.HOUR_OF_DAY);
									if(hora > 16 && dia == Calendar.SUNDAY)
										break;
									HiloDaily.sleep(300000L);
									c = Calendar.getInstance();
									dia = c.get(Calendar.DAY_OF_WEEK);
								}
								iniciarProceso();
							}
							finally
							{
								lock.unlock();
							}
							while(true)
							{
								HiloDaily.sleep(300000);
								if(cerrado)
									return;
								lock.lock();
								try
								{
									if(chequear() == null)
									{
										cerrarSocket();
										cerrarProceso();
										break;
									}
								}
								finally
								{
									lock.unlock();
								}
							}
							Error.agregar("Reiniciando proceso y socket: " + path);
							HiloDaily.sleep(100000);
						}
					} 
					catch (Exception e)
					{
						Error.agregar(e.getMessage() + " error en el vigilante del proceso: " + path);
						Error.reiniciar();
					}
				}
			}, Long.MAX_VALUE);
			hiloMonitor.setName("Monitor proceso " + path);
			executor = Executors.newSingleThreadExecutor();
			AdministradorHilos.agregarHilo(hiloMonitor);
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error iniciando proceso, reinicando equipo");
			Error.reiniciar();
		}
	}

	private void iniciarProceso()
	{
		lock.lock();
		try
		{
			Error.agregarInfo("Iniciando proceso " + path);
			for(int i = 0; i < 10; i++)
			{
				if(iniciarProcesoMeta())
					return;
				else if(i == 9)
				{
					Error.agregar("Error iniciando proceso en 10 intentos, " + path + ", reiniciando");
					Error.reiniciar();
				}
				else
				{
					cerrarSocket();
					cerrarProceso();
					HiloDaily.sleep(100000);
				}
			}
		}
		catch(Exception e)
		{
			Error.agregar("Error iniciando proceso, " + path + ", reiniciando");
			Error.reiniciar();
		}
		finally
		{
			lock.unlock();
		}
	}
	
	private boolean iniciarProcesoMeta()
	{
		lock.lock();
		try
		{
			ProcessBuilder pb = new ProcessBuilder("");
			pb.directory(new File(Propiedades.darPropiedad("dailyBot.control.DailyBot.directorioDailyBot") + "/" + path));
			pb.command("wine", "terminal.exe");
			proceso = pb.start();
			if(iniciarSocket())
			{
				Error.agregarInfo("Conexion establecida " + path);
				HiloDaily.sleep(20000);
				return true;
			}
			else
				return false;
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error, proceso no se pudo iniciar");
			return false;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	private boolean iniciarSocket()
	{
		lock.lock();
		try
		{
			HiloDaily.sleep(100000);
			Scanner sc = new Scanner(new File(path + "port.txt"));
			socket = new Socket((String) null, sc.nextInt());
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return true;
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error, socket no se pudo iniciar");
			return false;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	private void cerrarSocket()
	{
		lock.lock();
		try
		{
			if(socket != null)
				socket.close();
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error, socket no se pudo cerrar");
		}
		finally
		{
			lock.unlock();
		}
	}
	
	private void cerrarProceso()
	{
		lock.lock();
		try
		{
			if(proceso != null)
				proceso.destroy();
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error, proceso no se pudo cerrar");
		}
		finally
		{
			lock.unlock();
		}
	}
	
	private boolean escribirMeta(String mensaje)
	{
		lock.lock();
		try
		{
			socketOut.println(mensaje);
			return true;
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error escribiendo en el socket, " + path);
			return false;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	private class CallableSocket implements Callable <String>
	{
		@Override
		public String call() throws Exception 
		{
			lock.lock();
			try
			{
				socket.setSoTimeout(600000);
				String resultado = socketIn.readLine();
				resultado = resultado.substring(0, resultado.length() - 3);
				if(resultado.equals(""))
					resultado = " ";
				return resultado;
			}
			catch(NullPointerException e)
			{
				throw e;
			}
			finally
			{
				lock.unlock();
			}
		}
	}
	
	private String leerMeta()
	{
		int cuenta = lock.getHoldCount();
		while(lock.getHoldCount() != 0)
			lock.unlock();
		try
		{
			Future <String> futureResultado = executor.submit(new CallableSocket());
			String resultado = futureResultado.get(600, TimeUnit.SECONDS);
			if(resultado == null || resultado.equals(""))
				return null;
			return resultado.equals(" ") ? "" : resultado;
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + ", error de lectura en el socket: " + path);
			return null;
		}
		finally
		{
			while(lock.getHoldCount() < cuenta)
				lock.lock();
		}
	}
	
	private String chequear() 
	{
		lock.lock();
		try
		{
			if(escribirMeta("GBPCHF;LIST;CLOSE;0"))
				return leerMeta();
			else
				return null;
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + ", error chequeando senales: " + path);
			return null;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public String enviar(String s)
	{
		lock.lock();
		try
		{
			for(int i = 0; i < 10; i++)
			{
				String salida;
				if(escribirMeta(s) && ((salida = leerMeta()) != null))
					return salida;
				else if(i == 9)
				{
					Error.agregar("Error enviando a meta en 10 intentos, " + path + ", reiniciando");
					Error.reiniciar();
					return null;
				}
				else
				{
					cerrarSocket();
					cerrarProceso();
					HiloDaily.sleep(100000);
					iniciarProceso();
				}
			}
			return null;
		}
		catch(Exception e)
		{
			Error.agregar("Error enviando a meta, " + path + ", reiniciando");
			Error.reiniciar();
			return null;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public String chequearSenales()
	{
		lock.lock();
		try
		{
			return enviar("GBPCHF;LIST;CLOSE;0");
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
			cerrado = true;
			cerrarProceso();
		}
		finally
		{
			lock.unlock();
		}
	}
}
package dailyBot.modelo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import dailyBot.control.AdministradorHilos;
import dailyBot.control.Error;
import dailyBot.control.HiloDaily;
import dailyBot.control.RunnableDaily;

public class Escritor
{	
	private class EntradaEscritor
	{
		private Par par;
		private String linea;
		private boolean cierre;
		private SenalProveedor afectada;

		public EntradaEscritor(String l)
		{
			par = null;
			linea = l;
			cierre = false;
			afectada = null;
		}
		
		public EntradaEscritor(Par p, String l, boolean c, SenalProveedor a) 
		{
			par = p;
			linea = l;
			cierre = c;
			afectada = a;
		}
	}
	
	private final LinkedBlockingQueue < ArrayList <EntradaEscritor> > entradas;
	private ArrayList <EntradaEscritor> enConstruccion;
	private String pathMeta;
	private Proceso proceso;
	private volatile String mensajeDebug = "";
	private volatile String mensajeDebugHilo = "";
	private ReentrantLock lock = new ReentrantLock(true);
	private ReentrantLock lockConstruir = new ReentrantLock(true);
	public volatile boolean debug = true;
	
	public Escritor(String path)
	{
		pathMeta = path;
		entradas = new LinkedBlockingQueue < ArrayList <EntradaEscritor> > ();
		proceso = new Proceso(path);
		enConstruccion = new ArrayList <EntradaEscritor> ();
		HiloDaily hiloEscritor = new HiloDaily(new RunnableDaily()
		{
			public void run() 
			{
				while(true)
				{
					ponerUltimaActulizacion(System.currentTimeMillis());
					try
					{
						ponerUltimaActulizacion(System.currentTimeMillis());
						ArrayList <EntradaEscritor> leidas = entradas.poll(600000L, TimeUnit.MILLISECONDS);
						String mensajeDebug = Escritor.this.mensajeDebug;
						Escritor.this.mensajeDebug = "";
						try
						{						
							lock.lock();
							ponerUltimaActulizacion(System.currentTimeMillis());
							if(leidas == null)
								continue;
							if(debug)
								mensajeDebug += " Notificado " + pathMeta + " " + System.currentTimeMillis();
							ponerUltimaActulizacion(System.currentTimeMillis());
							mensajeDebugHilo = "";
							procesar(leidas);
							mensajeDebug += mensajeDebugHilo;
						}
						finally
						{
							lock.unlock();
						}
						if(debug)
							Error.agregarConTitulo("rangos", mensajeDebug);
						ponerUltimaActulizacion(System.currentTimeMillis());
					}
					catch(Exception e)
					{
						Error.agregar("Error en el hilo de escritura en path: " + pathMeta);
						ponerUltimaActulizacion(System.currentTimeMillis());
					}
				}
			}
		}, 1000000L);
		hiloEscritor.setName("Escritor " + path);
		AdministradorHilos.agregarHilo(hiloEscritor);
	}
	
	public void terminarCiclo()
	{
		lockConstruir.lock();
		try
		{
			if(enConstruccion.size() != 0)
			{
				String mensaje = "";
				try 
				{
					for(EntradaEscritor e : enConstruccion)
						mensaje += e.linea + ";";
					if(debug)
					{
						if(mensajeDebug.length() != 0)
						{
							HiloDaily.sleep(1000);
							if(mensajeDebug.length() != 0)
								Error.agregar("Mensaje debug no era vacio: " + mensajeDebug);
						}
						mensajeDebug += "Encolando " + pathMeta + " " + mensaje + " " + System.currentTimeMillis();
					}
					entradas.add(enConstruccion);
				} 
				catch (Exception e)
				{
					Error.agregar(e.getMessage() + ", Error agregando a la cola en path: " + pathMeta);
				}
				enConstruccion = new ArrayList <EntradaEscritor> ();
			}
		}
		finally
		{
			lockConstruir.unlock();
		}
	}
	
	private String enviar(ArrayList <EntradaEscritor> trabajoActual) 
	{
		try
		{
			File archivoEscritura = new File(pathMeta + "log.txt");
			if(!archivoEscritura.exists())
				archivoEscritura.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura, true);
			String mensaje = "";
			String lineaEnvio = "";
			for(EntradaEscritor entrada : trabajoActual)
			{
				String linea = entrada.linea;
				lineaEnvio += linea + "-";
				fw.write(linea + ";\n");
				mensaje += entrada.linea + ";";
			}
			lineaEnvio = lineaEnvio.substring(0, lineaEnvio.length() - 1);
			fw.close();
			if(debug)
				mensajeDebugHilo += " Escribiendo " + mensaje;
			return proceso.enviar(lineaEnvio);
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error enviando en el path " + pathMeta);
			return null;
		}
	}

	private ArrayList <String> leer(String magicos)
	{
		try
		{
			File archivoEscritura = new File(pathMeta + "log.txt");
			if(!archivoEscritura.exists())
				archivoEscritura.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura, true);
			ArrayList <String> leidos = new ArrayList <String> ();
			fw.write(magicos + "\n");
			if(debug)
				mensajeDebugHilo += " Leido: " + magicos + ", " + System.currentTimeMillis();
			if(magicos == null)
				return null;
			String [] magicosPartidos = magicos.split("-");
			for(String s : magicosPartidos)
				leidos.add(s);
			return leidos;
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error leyendo en el socket: " + pathMeta);
			return null;
		}
	}
	
	private ArrayList <String> cargarEntradas(ArrayList <EntradaEscritor> trabajoActual)
	{
		return leer(enviar(trabajoActual));
	}

	private boolean procesar(EntradaEscritor entrada, String lectura)
	{
		if(entrada.cierre)
		{
			if(lectura.endsWith("_CIERRE"))
			{
				Scanner sc = new Scanner(lectura);
		        sc.useDelimiter("\\Q;\\E");
		        sc.next();
		        String stringMagico = sc.next().replace("_CIERRE", "");
		        int magico = 0;
		        boolean error = false;
		        try
		        {
		        	magico = Integer.parseInt(stringMagico);
		        }
		        catch(Exception e)
		        {
		        	error = true;
		        }
		        if(error)
		        	Error.agregar("Error procesando magico de cierre en escritor: " + pathMeta + ", respuesta: " + lectura);
		        else
		        {
		        	SenalProveedor actual = entrada.afectada;
		        	if(actual == null)
		        		return true;
		        	if(magico == actual.getMagico())
		        	{
		        		return true;
		        	}
		        	else
		        	{
		        		Error.agregar("Senal no existia: " + entrada.linea + ", en escritor: " + pathMeta);
			        	return false;
		        	}
		        }
				return true;
			}
			else
			{
				Error.agregar("Error, resultado de un cierre no fue OK o senal no existia, respuesta fue: " + lectura);
				return false;
			}
		}
		else
		{
			Scanner sc = new Scanner(lectura);
	        sc.useDelimiter("\\Q;\\E");
	        int magico = sc.nextInt();
	        Par par = Par.convertirPar(sc.next());
	        if(entrada.par.equals(par))
	        {
	        	SenalProveedor actual = entrada.afectada;
	        	if(actual != null)
	        	{
	        		actual.setMagico(magico);
	        		if(debug)
	        			mensajeDebugHilo += " Procesado " + lectura + " -> " + " " + par.toString() + " " + magico + " " + System.currentTimeMillis();
	        	}
	        	else
	        		Error.agregar("Error leyendo magicos en path: " + pathMeta + ", no se encuentra par: " + entrada.par);
	        }
	        else
	        {
	        	Error.agregar("Error leyendo magicos en path: " + pathMeta + ", no coinciden: " + entrada.par + " y " + par);
	        }
	        sc.close();
	        return true;
		}
	}

	public void procesar(ArrayList <EntradaEscritor> trabajoActual)
	{
		ArrayList <String> entradas = cargarEntradas(trabajoActual);
		if(trabajoActual.size() != entradas.size())
		{
			Error.agregar("Error procesando magicos en path: " + pathMeta + ", tamanos distintos");
			return;
		}
		else
		{
			try
			{
				Iterator <String> it = entradas.iterator();
				String actual = it.next();
				for(EntradaEscritor entrada : trabajoActual)
				{
					if(procesar(entrada, actual))
					{
						if(it.hasNext())
							actual = it.next();
						else
							actual = null;
					}
				}
			}
			catch(Exception e)
			{
				Error.agregar("Error procesando magicos en path: " + pathMeta + ", " + e.getMessage());
			}
		}
	}
	
	public ArrayList <String> chequearSenales() 
	{
		lock.lock();
		try
		{
			debug = false;
			ArrayList <String> entradas = leer(proceso.chequearSenales());
			for(Iterator <String> it = entradas.iterator(); it.hasNext();)
				if(it.next().trim().equals(""))
					it.remove();
			debug = true;
			return entradas;
		}
		catch(Exception e)
		{
			Error.agregar("Error en la lecutura del socket, reiniciando despues de diez intentos");
			Error.reiniciar();
			return null;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public void cerrar(SenalProveedor afectada)
	{
		lockConstruir.lock();
		try
		{
			if(afectada.getMagico() != 0)
				enConstruccion.add(new EntradaEscritor(afectada.getPar(), afectada.getPar() + ";" + (afectada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.getMagico(), true, afectada));
			else
				Error.agregar("Senal proveedor: " + afectada.getProveedor() +", estrategia: " + afectada.getEstrategia() + ", par: " + afectada.getPar() + ", se intento cerrar y tiene magico 0");
		}
		finally
		{
			lockConstruir.unlock();
		}
	}
	
	public void cerrarStop(SenalProveedor afectada)
	{
		lockConstruir.lock();
		try
		{
			if(afectada.getMagico() != 0)
			{
				enConstruccion.add(new EntradaEscritor(afectada.getPar(), afectada.getPar() + ";" + (afectada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.getMagico(), true, afectada));
				if(debug)
					Error.agregarInfo("Cerrado: " + afectada.getPar() + ";" + (afectada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.getMagico() + " toco stop en: " + System.currentTimeMillis());
			}
		}
		finally
		{
			lockConstruir.unlock();
		}
	}

	public void abrir(SenalProveedor nueva)
	{
		lockConstruir.lock();
		try
		{
			enConstruccion.add(new EntradaEscritor(nueva.getPar(), nueva.getPar() + ";" + (nueva.isCompra() ? "BUY" : "SELL") + ";OPEN;0", false, nueva)); 
		}
		finally
		{
			lockConstruir.unlock();
		}
	}
	
	public void agregarLinea(String linea)
	{
		lockConstruir.lock();
		try
		{
			enConstruccion.add(new EntradaEscritor(linea));
		}
		finally
		{
			lockConstruir.unlock();
		}
	}
	
	public int chequearMagico(SenalProveedor dudosa)
	{
		lockConstruir.lock();
		lock.lock();
		try
		{
			for(EntradaEscritor e : enConstruccion)
				if(e.afectada == dudosa)
					return -1;
			for(ArrayList <EntradaEscritor> ae : entradas)
				for(EntradaEscritor e : ae)
					if(e.afectada == dudosa)
						return -1;
			int magico = dudosa.getMagico();
			return magico == -1 ? 0 : magico;
		}
		finally
		{
			lock.unlock();
			lockConstruir.unlock();
		}
	}

	public void cerrarProceso() 
	{
		lock.lock();
		lockConstruir.lock();
		try
		{
			proceso.cerrar();
		}
		finally
		{
			lockConstruir.unlock();
			lock.unlock();
		}
	}
}

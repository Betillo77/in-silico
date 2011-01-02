package modelo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import control.AdministradorHilos;
import control.Error;
import control.HiloDaily;
import control.RunnableDaily;


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
	
	private final LinkedBlockingQueue < LinkedBlockingQueue <EntradaEscritor> > entradas;
	private LinkedBlockingQueue <EntradaEscritor> enConstruccion;
	private String pathMeta;
	private Proceso proceso;
	private String mensaje = "";
	public volatile boolean debug = true;
	
	private void reiniciarProceso()
	{
		proceso.cerrar();
		try
		{
			Thread.sleep(100000);
		}
		catch(InterruptedException e)
		{
			Error.agregar("Error de interrupcion en escritor: " + pathMeta);
		}
	}
	
	public Escritor(String path)
	{
		pathMeta = path;
		entradas = new LinkedBlockingQueue < LinkedBlockingQueue <EntradaEscritor> > ();
		proceso = new Proceso(path);
		enConstruccion = new LinkedBlockingQueue<EntradaEscritor> ();
		final Escritor este = this;
		HiloDaily hiloEscritor = new HiloDaily(new RunnableDaily()
		{
			public void run() 
			{
				while(true)
				{
					try
					{
						Thread.sleep(1000);
						ultimaActualizacion = System.currentTimeMillis();
					}
					catch(InterruptedException e)
					{
						Error.agregar("Error de interrupcion en: " + pathMeta);
					}
					try
					{
						synchronized(entradas)
						{
							while(entradas.size() == 0)
							{
								ultimaActualizacion = System.currentTimeMillis();
								entradas.wait(100000);
							}
						}
						if(debug)
							Error.agregarInfo("Notificado " + pathMeta + " " + System.currentTimeMillis());
						synchronized(este)
						{
							ultimaActualizacion = System.currentTimeMillis();
							procesar(entradas.peek());
							if(entradas.size() > 0)
								entradas.take();
							ultimaActualizacion = System.currentTimeMillis();
						}
					}
					catch(Exception e)
					{
						Error.agregar("Error en el hilo de escritura en path: " + pathMeta);
					}
					ultimaActualizacion = System.currentTimeMillis();
				}
			}
		}, 1000000L);
		hiloEscritor.setName("Escritor " + path);
		AdministradorHilos.agregarHilo(hiloEscritor);
	}
	
	public synchronized void terminarCiclo()
	{
		if(enConstruccion.size() != 0 && mensaje.equals(""))
		{
			try 
			{
				entradas.put(enConstruccion);
				for(EntradaEscritor e : enConstruccion)
					mensaje += e.linea + ";";
			} 
			catch (InterruptedException e)
			{
				Error.agregar("Error de interrupcion en path: " + pathMeta);
			}
			enConstruccion = new LinkedBlockingQueue <EntradaEscritor> ();
			synchronized(entradas)
			{
				if(debug)
					Error.agregarInfo("Encolando y notificando " + pathMeta + " " + mensaje + System.currentTimeMillis());
				entradas.notifyAll();
			}
			mensaje = "";
		}
		else if(!mensaje.equals(""))
		{
			Error.agregar("Error, se encolaron: " + mensaje + " y no se procesaron");
			mensaje = "";
		}
	}
	
	private void escribir(LinkedBlockingQueue <EntradaEscritor> trabajoActual) 
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
			proceso.escribir(lineaEnvio);
			if(debug)
				Error.agregarInfo("Escribiendo " + mensaje);
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error escribiendo en el socket " + pathMeta);
		}
	}

	private ArrayList <String> leer()
	{
		try
		{
			File archivoEscritura = new File(pathMeta + "log.txt");
			if(!archivoEscritura.exists())
				archivoEscritura.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura, true);
			ArrayList <String> leidos = new ArrayList <String> ();
			String magicos = proceso.leer();
			fw.write(magicos + "\n");
			if(debug)
				Error.agregarInfo("Leido: " + magicos + ", " + System.currentTimeMillis());
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
	
	private ArrayList <String> cargarEntradas(LinkedBlockingQueue <EntradaEscritor> trabajoActual)
	{
		escribir(trabajoActual);
		return leer();
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
	        			Error.agregarInfo("Procesado " + lectura + " -> " + " " + par.toString() + " " + magico + " " + System.currentTimeMillis());
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

	public synchronized void procesar(LinkedBlockingQueue <EntradaEscritor> trabajoActual)
	{
		ArrayList <String> entradas = null;
		for(int i = 0; i < 11; i++)
		{
			entradas = cargarEntradas(trabajoActual);
			if(entradas != null)
				break;
			else
			{
				if(i == 10)
				{
					Error.agregar("Error en la lecutura del socket, reiniciando despues de diez intentos");
					Error.reiniciar();
				}
				else
				{
					Error.agregar("Error en la lectura del socket, reiniciando proceso");
					reiniciarProceso();
				}
			}
		}
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
	
	public synchronized ArrayList <String> chequearSenales() 
	{
		try
		{
			File archivoEscritura = new File(pathMeta + "log.txt");
			if(!archivoEscritura.exists())
				archivoEscritura.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura, true);
			fw.write("Chequeando senales\n");
			fw.close();
			if(debug)
				Error.agregarInfo("Chequeando senales");
			LinkedBlockingQueue <EntradaEscritor> trabajoActual = new LinkedBlockingQueue <EntradaEscritor> ();
			trabajoActual.add(new EntradaEscritor("GBPCHF;LIST;CLOSE;0"));
			ArrayList <String> entradas = null;
			for(int i = 0; i < 11; i++)
			{
				entradas = cargarEntradas(trabajoActual);
				if(entradas != null)
					break;
				else
				{
					if(i == 10)
					{
						Error.agregar("Error en la lecutura del socket, reiniciando despues de diez intentos");
						Error.reiniciar();
					}
					else
					{
						Error.agregar("Error en la lecutura del socket, reiniciando proceso");
						reiniciarProceso();
					}
				}
			}
			for(Iterator <String> it = entradas.iterator(); it.hasNext();)
			{
				if(it.next().equals(""))
					it.remove();
			}
			return entradas;
		}
		catch(Exception e)
		{
			Error.agregar("Error en la lecutura del socket, reiniciando despues de diez intentos");
			Error.reiniciar();
			return null;
		}
	}
	
	public synchronized void cerrar(SenalProveedor afectada)
	{
		if(afectada.getMagico() != 0)
			enConstruccion.add(new EntradaEscritor(afectada.getPar(), afectada.getPar() + ";" + (afectada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.getMagico(), true, afectada));
	}
	
	public synchronized void cerrarStop(SenalProveedor afectada)
	{
		if(afectada.getMagico() != 0)
		{
			enConstruccion.add(new EntradaEscritor(afectada.getPar(), afectada.getPar() + ";" + (afectada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.getMagico(), true, afectada));
			if(debug)
				Error.agregarInfo("Cerrado: " + afectada.getPar() + ";" + (afectada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.getMagico() + " toco stop en: " + System.currentTimeMillis());
		}
	}

	public synchronized void abrir(SenalProveedor nueva)
	{
		enConstruccion.add(new EntradaEscritor(nueva.getPar(), nueva.getPar() + ";" + (nueva.isCompra() ? "BUY" : "SELL") + ";OPEN;0", false, nueva)); 
	}
	
	public synchronized void agregarLinea(String linea)
	{
		enConstruccion.add(new EntradaEscritor(linea));
	}
}

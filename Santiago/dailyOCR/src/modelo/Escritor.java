package modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import control.AdministradorHilos;
import control.Error;
import control.dailyOCR;
import control.conexion.ConexionMySql;


public class Escritor
{	
	private final LinkedBlockingQueue < ArrayList <EntradaEscritor> > entradas;
	private ArrayList <EntradaEscritor> enConstruccion;
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
			Error.agregar("Error de interrupcion en: " + pathMeta);
		}
	}
	
	private void reiniciarEquipo()
	{
		try 
		{
			Runtime.getRuntime().exec("shutdown now -r");
			System.exit(0);
		} 
		catch (IOException e) 
		{
			Error.agregar("Error reiniciando equipo " + e.getMessage());
			System.exit(0);
		}
	}
	
	public Escritor(String path, ArrayList < ArrayList <EntradaEscritor> > iniciales)
	{
		pathMeta = path;
		entradas = new LinkedBlockingQueue < ArrayList <EntradaEscritor> > ();
		try
		{
			for(ArrayList <EntradaEscritor> entrada : iniciales)
				entradas.put(entrada);
		}
		catch(Exception e)
		{
			Error.agregar("Error inicializando escritor en path: " + pathMeta);
		}
		proceso = new Proceso(path);
		enConstruccion = new ArrayList <EntradaEscritor> ();
		final Escritor este = this;
		Thread hiloEscritor = new Thread(new Runnable()
		{
			public void run() 
			{
				while(true)
				{
					try
					{
						Thread.sleep(160000);
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
								entradas.wait(100000);
						}
						if(debug)
						{
							Error.agregar("Notificado " + pathMeta + " " + System.currentTimeMillis());
						}
						synchronized(este)
						{
							procesar(entradas.peek());
							entradas.take();
						}
					}
					catch(Exception e)
					{
						Error.agregar("Error en el hilo de escritura en path: " + pathMeta);
					}
				}
			}
		});
		hiloEscritor.setName("Escritor " + path);
		AdministradorHilos.agregarHilo(hiloEscritor);
	}
	
	public void terminarCiclo()
	{
		if(enConstruccion.size() != 0)
		{
			String men = "";
			try 
			{
				entradas.put(enConstruccion);
				for(EntradaEscritor e : enConstruccion)
					men += e.getLinea() + ";";
			} 
			catch (InterruptedException e)
			{
				Error.agregar("Error de interrupcion en path: " + pathMeta);
			}
			enConstruccion = new ArrayList <EntradaEscritor> ();
			synchronized(entradas)
			{
				if(debug)
				{
					Error.agregar("Encolando y notificando " + pathMeta + " " + men + ", se encolaron: " + mensaje + " " + System.currentTimeMillis());
				}
				entradas.notifyAll();
			}
			mensaje = "";
		}
		else if(!mensaje.equals(""))
		{
			Error.agregar("Se encolaron: " + mensaje + " y no se procesaron");
			mensaje = "";
		}
	}
	
	private void escribir(ArrayList <EntradaEscritor> trabajoActual) 
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
				String linea = entrada.getLinea();
				lineaEnvio += linea + "-";
				fw.write(linea + ";\n");
				mensaje += entrada.getLinea() + ";";
			}
			lineaEnvio = lineaEnvio.substring(0, lineaEnvio.length() - 1);
			fw.close();
			proceso.escribir(lineaEnvio);
			if(debug)
				Error.agregar("Escribiendo " + mensaje);
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error escribiendo en el socket " + pathMeta);
			reiniciarEquipo();
		}
	}

	private ArrayList <String> leer(int tiempoEspera)
	{
		try
		{
			ArrayList <String> leidos = new ArrayList <String> ();
			File archivoEscritura = new File(pathMeta + "log.txt");
			String magicos = proceso.leer(tiempoEspera * 5);
			if(debug)
				Error.agregar("Leido: " + magicos + ", " + System.currentTimeMillis());
			if(!archivoEscritura.exists())
				archivoEscritura.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura, true);
			fw.write(magicos + "\n");
			fw.close();
			String [] magicosPartidos = magicos.split("-");
			for(String s : magicosPartidos)
				leidos.add(s);
			return leidos;
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " error escribiendo en el socket");
			reiniciarProceso();
		}
		return null;
	}
	
	private ArrayList <String> cargarEntradas(ArrayList <EntradaEscritor> trabajoActual, int tiempoExtra)
	{
		escribir(trabajoActual);
		return leer(10000 + 20000 * trabajoActual.size() + tiempoExtra);
	}
	
	protected Senal darSenal(EntradaEscritor entrada) 
	{
		return dailyOCR.darEstrategia(entrada.getId()).tienePar(entrada.getPar());
	}
	
	protected boolean procesar(EntradaEscritor entrada, String lectura)
	{
		if(entrada.isCierre())
		{
			if(lectura.endsWith("_CIERRE"))
			{
				Scanner sc = new Scanner(lectura);
		        sc.useDelimiter("\\Q;\\E");
		        String ganancia = sc.next();
		        int gananciaReal;
		        if(ganancia.charAt(0) == '(')
		        	gananciaReal = Integer.parseInt(ganancia.substring(1)) * -1;
		        else
		        	gananciaReal = Integer.parseInt(ganancia);
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
		        	Senal actual = entrada.getAfectada();
		        	if(actual == null)
		        		return true;
		        	if(magico == actual.darMagico(0))
		        	{
			        	for(int i = 0; i < entrada.getNumeroLotes() - 1; i++)
							ConexionMySql.agregarEntrada(entrada.getId(), actual);
			        	actual.ponerGananciaReal(gananciaReal);
			        	ConexionMySql.agregarEntrada(entrada.getId(), actual);
		        	}
		        	else
		        	{
		        		Error.agregar("Senal no existia: " + entrada.getLinea() + ", en escritor: " + pathMeta);
			        	for(int i = 0; i < entrada.getNumeroLotes(); i++)
							ConexionMySql.agregarEntrada(entrada.getId(), actual);
			        	return false;
		        	}
		        }
				return true;
			}
			else
			{
				Error.agregar("Error, resultado de un cierre no fue OK o senal no existia, respuesta fue: " + lectura);
				Senal actual = entrada.getAfectada();
				if(actual != null)
					for(int i = 0; i < entrada.getNumeroLotes(); i++)
						ConexionMySql.agregarEntrada(entrada.getId(), actual);
				return false;
			}
		}
		Scanner sc = new Scanner(lectura);
        sc.useDelimiter("\\Q;\\E");
        int magico = sc.nextInt();
        Par par = Par.convertirPar(sc.next());
        if(entrada.getPar().equals(par))
        {
        	Senal actual = darSenal(entrada);
        	if(actual != null)
        	{
        		actual.ponerMagico(0, magico);
        		if(debug)
        		{
        			Error.agregar("Procesado " + lectura + " -> " + entrada.getId().toString() + " " + par.toString() + " " + magico + " " + System.currentTimeMillis());
        		}
        	}
        	else
        		Error.agregar("Error leyendo magicos en path: " + pathMeta + ", en estrategia: " + entrada.getId().toString() + ", no se encuentra par: " + entrada.getPar());
        }
        else
        {
        	Error.agregar("Error leyendo magicos en path: " + pathMeta + ", no coinciden: " + entrada.getPar() + " y " + par);
        }
        sc.close();
        return true;
	}

	public synchronized void procesar(ArrayList <EntradaEscritor> trabajoActual)
	{
		ArrayList <String> entradas = null;
		for(int i = 0; i < 11; i++)
		{
			entradas = cargarEntradas(trabajoActual, i == 0 ? 0 : 90000);
			if(entradas != null)
				break;
			else
			{
				if(i == 10)
				{
					Error.agregar("Error en la lecutura del socket, reiniciando despues de diez intentos");
					reiniciarEquipo();
				}
				else
				{
					Error.agregar("Error en la lecutura del socket, reiniciando proceso");
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
		debug = false;
		ArrayList <EntradaEscritor> trabajoActual = new ArrayList <EntradaEscritor> ();
		trabajoActual.add(new EntradaEscritor(null, null, "GBPCHF;LIST;CLOSE;0", false));
		ArrayList <String> entradas = null;
		for(int i = 0; i < 11; i++)
		{
			entradas = cargarEntradas(trabajoActual, i == 0 ? 0 : 90000);
			if(entradas != null)
				break;
			else
			{
				if(i == 10)
				{
					Error.agregar("Error en la lecutura del socket, reiniciando despues de diez intentos");
					reiniciarEquipo();
				}
				else
				{
					Error.agregar("Error en la lecutura del socket, reiniciando proceso");
					reiniciarProceso();
				}
			}
		}
		debug = true;
		return entradas;
	}
	
	public void cerrar(SenalEntrada entrada, Senal afectada)
	{
		if(entrada.getNumeroLotes() > 5)
		{
    		Error.agregar("Mas de cinco lotes abiertos en: " + entrada.getPar().toString() + ", en el path: " + pathMeta);
		}
		afectada.setNumeroLotes(afectada.getNumeroLotes() - entrada.getNumeroLotes());
		if(afectada.darMagico(0) != 0)
		{
			if(afectada.getNumeroLotes() == 0)
			{
				if(!afectada.isTocoStop())
				{
					enConstruccion.add(new EntradaEscritor(entrada.getEstrategia(), entrada.getPar(), entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.darMagico(0), true, entrada.getNumeroLotes(), afectada));
					mensaje += entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.darMagico(0) + " ";
				}
			}
			else
			{
				for(int i = 0; i < entrada.getNumeroLotes(); i++)
					ConexionMySql.agregarEntrada(entrada.getEstrategia(), afectada);			
			}
		}
		else
		{
			for(int i = 0; i < entrada.getNumeroLotes(); i++)
				ConexionMySql.agregarEntrada(entrada.getEstrategia(), afectada);
		}
		if(afectada.getNumeroLotes() <= 0)
			return;
		int[] magicoCopy = afectada.darMagicoCopy();
		afectada.setMagico(Arrays.copyOfRange(magicoCopy, 0, magicoCopy.length - entrada.getNumeroLotes()));
	}
	
	public void cerrarStop(Senal afectada)
	{
		if(afectada.darMagico(0) != 0)
		{
			enConstruccion.add(new EntradaEscritor(afectada.getEstrategia(), afectada.getPar(), afectada.getPar() + ";" + (afectada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.darMagico(0), true, afectada.getNumeroLotes(), afectada));
			mensaje += afectada.getPar() + ";" + (afectada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.darMagico(0) + " ";
			Error.agregar("Cerrado: " + afectada.getPar() + ";" + (afectada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.darMagico(0) + " toco stop en: " + System.currentTimeMillis());
		}
	}

	public void abrir(SenalEntrada entrada, Senal nueva)
	{
		Estrategia estrategia = dailyOCR.darEstrategiaSenal(nueva);
		if(entrada.getNumeroLotes() > 5)
		{
    		Error.agregar("Mas de cinco lotes abiertos en: " + entrada.getPar().toString() + ", en el path: " + pathMeta);
		}
		if(estrategia.darActivo(entrada.getPar()))
		{
			enConstruccion.add(new EntradaEscritor(estrategia.getId(), entrada.getPar(), entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";OPEN;0", false)); 
			mensaje += entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";OPEN;0 ";
		}
		nueva.setMagico(new int[entrada.getNumeroLotes()]);
	}
	
	public void agregarLinea(String linea)
	{
		enConstruccion.add(new EntradaEscritor(null, null, linea, true));
		mensaje += linea + " ";
	}
	
	public ArrayList < ArrayList <EntradaEscritor> > darCopiaEntradas() 
	{
		ArrayList < ArrayList <EntradaEscritor> > entradasNuevas = new ArrayList < ArrayList <EntradaEscritor> > ();
		for(ArrayList <EntradaEscritor> actual : entradas)
		{
			entradasNuevas.add(actual);
		}
		return entradasNuevas;
	}
}

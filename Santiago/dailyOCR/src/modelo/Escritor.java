package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import control.Error;
import control.IdEstrategia;
import control.dailyOCR;


public class Escritor
{	
	private final LinkedBlockingQueue < ArrayList <EntradaEscritor> > entradas;
	private ArrayList <EntradaEscritor> enConstruccion;
	private String pathMeta;
	private Proceso proceso;
	
	private void reiniciarProceso()
	{
		try
		{
			proceso.cerrar();
		}
		catch(Exception e)
		{
			Error.agregar("Error reiniciando proceso, reinicando equipo");
			reiniciarEquipo();
		}
	}
	
	private void reiniciarEquipo()
	{
		new File(pathMeta + "ordenes.txt").delete();
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
		pathMeta = path + "experts/files/";
		entradas = new LinkedBlockingQueue <ArrayList <EntradaEscritor> > ();
		try
		{
			for(ArrayList <EntradaEscritor> entrada : iniciales)
				entradas.put(entrada);
		}
		catch(Exception e)
		{
			Error.agregar("Error inicializando escritor en path: " + pathMeta);
		}
		try
		{
			proceso = new Proceso(path);
		}
		catch(Exception e)
		{
			Error.agregar("Error iniciando proceso, reinicando equipo");
			reiniciarEquipo();
		}
		enConstruccion = new ArrayList <EntradaEscritor> ();
		new Thread(new Runnable()
		{
			public void run() 
			{
				while(true)
				{
					try
					{
						synchronized(entradas)
						{
							while(entradas.size() == 0)
								entradas.wait();
						}
						procesar(entradas.peek());
						entradas.take();
					}
					catch(Exception e)
					{
						Error.agregar("Error en el hilo de escritura en path: " + pathMeta);
					}
				}
			}
		}).start();
	}
	
	public void terminarCiclo()
	{
		if(enConstruccion.size() != 0)
		{
			try 
			{
				entradas.put(enConstruccion);
			} 
			catch (InterruptedException e)
			{
				Error.agregar("Error de interrupcion en path: " + pathMeta);
			}
			enConstruccion = new ArrayList <EntradaEscritor> ();
			synchronized(entradas)
			{
				entradas.notifyAll();
			}
		}
	}
	
	private void chequearArchivo(String archivo, long tiempoEspera) throws FileNotFoundException
	{
		int numeroVeces = 0;
		try
		{
			while(true)
			{
				File archivoMagicos = new File(pathMeta + archivo);
				if(archivoMagicos.exists())
					break;
				numeroVeces++;
				if(numeroVeces == 1000)
				{
					Error.agregar("Error de lectura, magicos no fueron leidos, en path, despues de 10 reinicios de proceso: " + pathMeta);
					reiniciarEquipo();
				}
				if(numeroVeces % 100 == 0)
				{
					Error.agregar("Error de lectura, magicos no fueron leidos, en path: " + pathMeta);
					reiniciarProceso();
					if(!new File(pathMeta + "ordenes.txt").exists())
						throw(new FileNotFoundException("Archivo no encontrado " + pathMeta + "ordenes.txt"));
					else
						Thread.sleep(tiempoEspera + 90000);
				}
				if(!archivoMagicos.exists())
				{
					Thread.sleep(2000);
				}
				else
					break;
			}
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error en la lectura del archivo magico, en path: " + pathMeta);
			reiniciarEquipo();
			return;
		}
	}
	
	private void escribir(ArrayList <EntradaEscritor> trabajoActual) 
	{
		try
		{
			new File(pathMeta + "magicos.txt").delete();
			new File(pathMeta + "lista.txt").delete();
			File archivoEscritura = new File(pathMeta + "ordenes.txt");
			File archivoEscritura1 = new File(pathMeta + "log.txt");
			if(!archivoEscritura.exists())
				archivoEscritura.createNewFile();
			if(!archivoEscritura1.exists())
				archivoEscritura1.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura, true);
			FileWriter fw1 = new FileWriter(archivoEscritura1, true);
			for(EntradaEscritor entrada : trabajoActual)
			{
				String linea = entrada.linea;
				fw.write(linea + ";");
				fw1.write(linea + ";\n");
			}
			fw.close();
			fw1.close();

		}
		catch(Exception e)
		{
			Error.agregar("No se pudo escribir en el archivo: " + pathMeta + "ordenes.txt");
			reiniciarEquipo();
		}
	}

	private ArrayList <String> leer(String archivo, long tiempoEspera) throws FileNotFoundException
	{
		boolean termino = false;
		try 
		{
			Thread.sleep(tiempoEspera);
		}
		catch (InterruptedException e) 
		{
			Error.agregar(e.getMessage() + " Error de interrupcion al leer archivo: " + archivo + ", en path: " + pathMeta);
		}
		chequearArchivo(archivo, tiempoEspera);
		ArrayList <String> leidos = new ArrayList <String> ();
		for(int i = 0; i < 60 && !termino; i++)
		{
			Scanner sc = new Scanner("1");
			if(i != 0)
				try 
				{
					Thread.sleep(3000);
				} 
				catch (InterruptedException e1)
				{
					Error.agregar(e1.getMessage() + " Error de interrupcion al leer archivo: " + archivo + ", en path: " + pathMeta);
				}
			try 
			{
				sc = new Scanner(new File(pathMeta + archivo));
				while(sc.hasNext())
				{
					leidos.add(sc.next());
				}
				sc.close();
				termino = true;
			} 
			catch (Exception e)
			{
	    		Error.agregar(e.getMessage() + " Error en el scanner en leer magicos, en path: " + pathMeta);
	    		reiniciarEquipo();
			}
			finally
			{
				sc.close();
			}
		}
		new File(pathMeta + archivo).delete();
		new File(pathMeta + "ordenes.txt").delete();
		return leidos;
	}
	
	private ArrayList <String> cargarEntradas(String archivo, ArrayList <EntradaEscritor> trabajoActual, long tiempoExtra) throws FileNotFoundException
	{
		escribir(trabajoActual);
		return leer(archivo, 10000 + 25000 * trabajoActual.size() + tiempoExtra);
	}
	
	protected Senal darSenal(EntradaEscritor entrada) 
	{
		return dailyOCR.darEstrategia(entrada.id).tienePar(entrada.par);
	}
	
	protected void procesar(EntradaEscritor entrada, String lectura)
	{
		Scanner sc = new Scanner(lectura);
        sc.useDelimiter("\\Q;\\E");
        int magico = sc.nextInt();
        Par par = Par.convertirPar(sc.next());
        if(entrada.getPar().equals(par))
        {
        	Senal actual = darSenal(entrada);
        	if(actual != null)
        		actual.ponerMagico(0, magico);
        	else
        		Error.agregar("Error leyendo magicos en path: " + pathMeta + ", en estrategia: " + entrada.id.toString() + ", no se encuentra par: " + entrada.getPar());
        }
        else
        {
        	Error.agregar("Error leyendo magicos en path: " + pathMeta + ", no coinciden: " + entrada.getPar() + " y " + par);
        }
        sc.close();
	}

	public synchronized void procesar(ArrayList <EntradaEscritor> trabajoActual)
	{
		ArrayList <String> entradas = null;
		for(int i = 0; i < 11; i++)
		{
			try
			{
				entradas = cargarEntradas("magicos.txt", trabajoActual, i == 0 ? 0 : 90000);
				break;
			}
			catch(FileNotFoundException e)
			{
				if(i == 10)
				{
					Error.agregar(e.getMessage() + " despues de diez intentos, reiniciando");
					reiniciarEquipo();
				}
				else
				{
					Error.agregar(e.getMessage());
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
				String actual;
				for(EntradaEscritor entrada : trabajoActual)
				{
					if(entrada.cierre)
						continue;
					actual = it.next();
					procesar(entrada, actual);
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
		ArrayList <EntradaEscritor> trabajoActual = new ArrayList <EntradaEscritor> ();
		trabajoActual.add(new EntradaEscritor(null, null, "GBPCHF;LIST;CLOSE;0", false));
		ArrayList <String> entradas = null;
		for(int i = 0; i < 11; i++)
		{
			try
			{
				entradas = cargarEntradas("lista.txt", trabajoActual, i == 0 ? 0 : 90000);
				break;
			}
			catch(FileNotFoundException e)
			{
				if(i == 10)
				{
					Error.agregar(e.getMessage() + " despues de diez intentos, reiniciando");
					reiniciarEquipo();
				}
				else
				{
					Error.agregar(e.getMessage());
				}
			}
		}
		return entradas;
	}
	
	public void cerrar(SenalEntrada entrada, Senal afectada)
	{
		Estrategia estrategia = dailyOCR.darEstrategiaSenal(afectada);
		if(entrada.getNumeroLotes() > 5)
		{
    		Error.agregar("Mas de cinco lotes abiertos en: " + entrada.getPar().toString() + ", en el path: " + pathMeta);
		}
		afectada.setNumeroLotes(afectada.getNumeroLotes() - entrada.getNumeroLotes());
		if(afectada.darMagico(0) != 0)
		{
			if(afectada.getNumeroLotes() == 0)
				enConstruccion.add(new EntradaEscritor(estrategia.getId(), entrada.getPar(), entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.darMagico(0), true));
		}
		if(afectada.getNumeroLotes() <= 0)
			return;
		int[] magicoCopy = afectada.darMagicoCopy();
		afectada.setMagico(Arrays.copyOfRange(magicoCopy, 0, magicoCopy.length - entrada.getNumeroLotes()));
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
		}
		nueva.setMagico(new int[entrada.getNumeroLotes()]);
	}
	
	public void agregarLinea(String linea)
	{
		enConstruccion.add(new EntradaEscritor(null, null, linea, true));
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
	
	public class EntradaEscritor
	{
		private IdEstrategia id;
		private Par par;
		private String linea;
		private boolean cierre;

		public EntradaEscritor()
		{
		}
		
		public EntradaEscritor(IdEstrategia i, Par p, String l, boolean c) 
		{
			id = i;
			par = p;
			linea = l;
			cierre = c;
		}

		public IdEstrategia getId() {
			return id;
		}
		
		public void setId(IdEstrategia id) {
			this.id = id;
		}
		
		public Par getPar() {
			return par;
		}
		
		public void setPar(Par par) {
			this.par = par;
		}

		public void setLinea(String linea) {
			this.linea = linea;
		}

		public String getLinea() {
			return linea;
		}
		
		public boolean isCierre() {
			return cierre;
		}

		public void setCierre(boolean cierre) {
			this.cierre = cierre;
		}
	}
}

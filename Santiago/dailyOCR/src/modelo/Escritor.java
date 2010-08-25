package modelo;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import control.Error;
import control.dailyOCR;


public class Escritor
{	
	public ArrayList <String> lineas = new ArrayList <String> ();
	private ArrayList <Senal> senales = new ArrayList <Senal> ();
	private String pathMeta;
	
	public Escritor(String path)
	{
		pathMeta = path;
	}
	
	public void escribir() 
	{
		try
		{
			File archivoEscritura = new File(pathMeta + "ordenes.txt");
			File archivoEscritura1 = new File(pathMeta + "log.txt");
			if(!lineas.isEmpty())
			{
				if(!archivoEscritura.exists())
					archivoEscritura.createNewFile();
				if(!archivoEscritura1.exists())
					archivoEscritura1.createNewFile();
				FileWriter fw = new FileWriter(archivoEscritura, true);
				FileWriter fw1 = new FileWriter(archivoEscritura1, true);
				for(String linea : lineas)
				{
					fw.write(linea + ";");
					fw1.write(linea + ";\n");
				}
				fw.close();
				fw1.close();
			}
			lineas = new ArrayList <String> ();
			new File(pathMeta + "magicos.txt").delete();
		}
		catch(Exception e)
		{
			lineas = new ArrayList <String> ();
			Error.agregar("No se pudo escribir en el archivo: " + pathMeta + "ordenes.txt");
		}
	}

	public void leerMagicos() 
	{
		boolean termino = false;
		int numero = 0;
		for(Senal s : senales)
			numero += s.getNumeroLotes();
		if(senales.size() > 0)
		{
			try 
			{
				Thread.sleep(60000 + 30000 * numero);
			}
			catch (InterruptedException e) 
			{
	    		Error.agregar(e.getMessage() + " Error de interrupcion al leer magicos en path: " + pathMeta);
			}
		}
		else
		{
			return;
		}
		int numeroVeces = 0;
		try
		{
			while(true)
			{
				numeroVeces++;
				if(numeroVeces == 100)
				{
					Error.agregar("Error de lectura, magicos no fueron leidos, en path: " + pathMeta);
					return;
				}
				File archivoMagicos = new File(pathMeta + "magicos.txt");
				if(!archivoMagicos.exists())
				{
					Thread.sleep(30000);
				}
				else
					break;
			}
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error en la lectura del archivo magico, en path: " + pathMeta);
			return;
		}
		for(int i = 0; i < 60 && !termino; i++)
		{
			Scanner sc = new Scanner("1");
			if(i != 0)
				try 
				{
					Thread.sleep(60000);
				} 
				catch (InterruptedException e1)
				{
					Error.agregar("Error de interrupcion al leer magicos en path: " + pathMeta);
				}
			try 
			{
				sc = new Scanner(new File(pathMeta + "magicos.txt"));
				Iterator <Senal> it = senales.iterator();
				Senal actual = it.next();
				int numeroActual = 0;
				while(sc.hasNext())
				{
					Scanner sc2 = new Scanner(sc.next());
					sc2.useDelimiter("\\Q;\\E");
					int magico = sc2.nextInt();
					actual.getMagico()[numeroActual++] = magico;
					if(numeroActual == 1)
					{
						numeroActual = 0;
						if(it.hasNext())
							actual = it.next();
					}
					sc2.close();
				}
				sc.close();
				termino = true;
			} 
			catch (Exception e)
			{
	    		Error.agregar(e.getMessage() + " Error en el scanner en leer magicos, en path: " + pathMeta);
			}
			finally
			{
				sc.close();
			}
		}
		if(new File(pathMeta + "magicos.txt").canWrite())
			new File(pathMeta + "magicos.txt").delete();
		for(Senal s : senales)
		{
			if(s.getMagico()[0] == 0)
				Error.agregar("Error en " + s.getEstrategia() + " al leer el magico de " + s.getPar());
		}
		senales = new ArrayList <Senal> ();
	}

	public ArrayList <String> chequearSenales() 
	{
		ArrayList <String> leidos = new ArrayList <String> (14);
		try
		{
			File archivoEscritura = new File(pathMeta + "ordenes.txt");
			if(!archivoEscritura.exists())
				archivoEscritura.createNewFile();
			FileWriter fw = new FileWriter(archivoEscritura, true);
			fw.write("GBPCHF;LIST;CLOSE;0;");
			fw.close();
		}
		catch(Exception e)
		{
			Error.agregar("No se pudo escribir en el archivo: " + pathMeta + "ordenes.txt");
		}
		boolean termino = false;
		try 
		{
			Thread.sleep(30000);
		}
		catch (InterruptedException e)
		{
    		Error.agregar(e.getMessage() + " Error de interrupcion al leer magicos en path: " + pathMeta);
		}
		int numeroVeces = 0;
		try
		{
			while(true)
			{
				numeroVeces++;
				if(numeroVeces == 100)
				{
					Error.agregar("Error de lectura, magicos no fueron leidos, en path: " + pathMeta);
					return leidos;
				}
				File archivoLista = new File(pathMeta + "lista.txt");
				if(!archivoLista.exists())
				{
					Thread.sleep(30000);
				}
				else
					break;
			}
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error en la lectura del archivo magico, en path: " + pathMeta);
			return leidos;
		}
		for(int i = 0; i < 60 && !termino; i++)
		{
			Scanner sc = new Scanner("1");
			if(i != 0)
				try 
				{
					Thread.sleep(60000);
				} 
				catch (InterruptedException e1)
				{
					Error.agregar("Error de interrupcion al leer magicos en path: " + pathMeta);
				}
			try 
			{
				sc = new Scanner(new File(pathMeta + "lista.txt"));
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
			}
			finally
			{
				sc.close();
			}
		}
		if(new File(pathMeta + "lista.txt").canWrite())
			new File(pathMeta + "lista.txt").delete();
		if(new File(pathMeta + "magicos.txt").canWrite())
			new File(pathMeta + "magicos.txt").delete();
		return leidos;
	}

	public void cerrar(SenalEntrada entrada, Senal afectada)
	{
		if(entrada.getNumeroLotes() > 5)
		{
    		Error.agregar("Mas de cinco lotes abiertos en: " + entrada.getPar().toString() + ", en el path: " + pathMeta);
		}
		afectada.setNumeroLotes(afectada.getNumeroLotes() - entrada.getNumeroLotes());
		if(afectada.getMagico()[0] != 0)
		{
			if(afectada.getNumeroLotes() == 0)
				lineas.add(entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.getMagico()[0]);
		}
		if(afectada.getNumeroLotes() <= 0)
			return;
		afectada.setMagico(Arrays.copyOfRange(afectada.getMagico(), 0, afectada.getMagico().length - entrada.getNumeroLotes()));
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
			lineas.add(entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";OPEN;0");
			senales.add(nueva);
		}
		nueva.setMagico(new int[entrada.getNumeroLotes()]);
	}
}

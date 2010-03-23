package control;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import modelo.Estrategia;
import modelo.Senal;
import modelo.SenalEntrada;

public class Escritor
{	
	private ArrayList <String> lineas = new ArrayList <String> ();
	private ArrayList <Senal> senales = new ArrayList <Senal> ();
	private String pathMeta;
	private Method metodoMeta;
	
	public Escritor(String path, Class <?> clase)
	{
		pathMeta = path;
		try 
		{
			metodoMeta = clase.getMethod("metodoMeta", SenalEntrada.class, Senal.class);
		} 
		catch(Exception e) 
		{
			Error.agregar(e.getMessage() + " Error en metodoMeta de: " + pathMeta);
		}
	}
	
	public void escribir() 
	{
		try
		{
			File archivoEscritura = new File(pathMeta + "ordenes.txt");
			if(!lineas.isEmpty())
			{
				if(!archivoEscritura.exists())
					archivoEscritura.createNewFile();
				FileWriter fw = new FileWriter(archivoEscritura, true);
				for(String linea : lineas)
				{
					fw.write(linea + ";");
				}
				fw.close();
			}
			lineas = new ArrayList <String> ();
		}
		catch(Exception e)
		{
			lineas = new ArrayList <String> ();
			Error.agregar("No se pudo escribir en el archivo: " + pathMeta + "ordenes.txt");
		}
		new File(pathMeta + "magicos.txt").delete();
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
					if(numeroActual == actual.getNumeroLotes())
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
		senales = new ArrayList <Senal> ();
	}

	@SuppressWarnings("unchecked")
	public void cerrar(SenalEntrada entrada, Senal afectada)
	{
		if(entrada.getNumeroLotes() > 5)
		{
    		Error.agregar("Mas de cinco lotes abiertos en: " + entrada.getPar().toString() + ", en el path: " + pathMeta);
		}
		try 
		{
			lineas.addAll((Collection <String>) metodoMeta.invoke(entrada, afectada));
		} 
		catch (Exception e) 
		{
			Error.agregar(e.getMessage() + " Error en metodoMeta en " + pathMeta);
		}
		afectada.setNumeroLotes(afectada.getNumeroLotes() - entrada.getNumeroLotes());
		if(afectada.getNumeroLotes() <= 0)
			return;
		afectada.setMagico(Arrays.copyOfRange(afectada.getMagico(), entrada.getNumeroLotes(), afectada.getMagico().length));
	}

	@SuppressWarnings("unchecked")
	public void abrir(SenalEntrada entrada, Senal nueva)
	{
		Estrategia estrategia = dailyOCR.darEstrategiaSenal(nueva);
		if(entrada.getNumeroLotes() > 5)
		{
    		Error.agregar("Mas de cinco lotes abiertos en: " + entrada.getPar().toString() + ", en el path: " + pathMeta);
		}
		if(estrategia.darActivo(entrada.getPar()))
		{
			try 
			{
				lineas.addAll((Collection <String>) metodoMeta.invoke(null, entrada, nueva));
			} 
			catch (Exception e) 
			{
				Error.agregar(e.getMessage() + " Error en metodoMeta en " + pathMeta);
			}
			senales.add(nueva);
		}
		nueva.setMagico(new int[entrada.getNumeroLotes()]);
	}
}

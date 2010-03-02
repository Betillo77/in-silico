package control;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;



public class Escritor implements Serializable
{
	private static final long serialVersionUID = -6112706980498122769L;
	
	public ArrayList <String> lineas = new ArrayList <String> ();
	public ArrayList <Senal> senales = new ArrayList <Senal> ();
	public String pathMeta;
	
	public Escritor(String path)
	{
		pathMeta = path;
	}
	
	public void escribir() 
	{
		try
		{
			File archivoEscritura = new File(pathMeta + "ordenes.txt");
			if(!lineas.isEmpty())
			{
				archivoEscritura.createNewFile();
				FileWriter fw = new FileWriter(archivoEscritura, true);
				for(String linea : lineas.subList(0, lineas.size() - 1))
				{
					fw.write(linea + ";");
				}
				fw.write(lineas.get(lineas.size() - 1) + ";");
				fw.close();
			}
			lineas = new ArrayList <String> ();
		}
		catch(Exception e)
		{
			lineas = new ArrayList <String> ();
			Error.agregar("No se pudo escribir en el archivo");
		}
	}

	public void leerMagicos() 
	{
		boolean termino = false;
		int numero = 0;
		for(Senal s : senales)
			numero += s.numeroLotes;
		if(senales.size() > 0)
		{
			try 
			{
				Thread.sleep(60000 + 30000 * numero);
			}
			catch (InterruptedException e) 
			{
	    		Error.agregar(e.getMessage() + " Error de interrupcion al leer magicos");
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
					Error.agregar("Error de lectura, magicos no fueron leidos");
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
			Error.agregar(e.getMessage() + " Error en la lectura del archivo magico");
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
					actual.magico[numeroActual++] = magico;
					if(numeroActual == actual.numeroLotes)
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
	    		Error.agregar(e.getMessage() + " Error en el scanner en leer magicos");
			}
			finally
			{
				sc.close();
			}
		}
		if(new File(pathMeta + "magicos.txt").canWrite())
			new File(pathMeta + "magicos.txt").delete();
		senales.clear();
	}

	public void cerrar(SenalEntrada entrada, Senal afectada)
	{
		if(entrada.numeroLotes > 5)
		{
    		Error.agregar("Mas de cinco lotes abiertos en: " + entrada.par.toString());
		}
		for(int i = 0; i < entrada.numeroLotes; i++)
		{
			lineas.add(entrada.par + ";" + (entrada.compra ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.magico[i]);
		}
		afectada.numeroLotes -= entrada.numeroLotes;
		if(afectada.numeroLotes <= 0)
			return;
		afectada.magico = Arrays.copyOfRange(afectada.magico, entrada.numeroLotes, afectada.magico.length);
	}

	public void abrir(SenalEntrada entrada, Senal nueva)
	{
		Estrategia estrategia = dailyOCR.darEstrategiaSenal(nueva);
		if(entrada.numeroLotes > 5)
		{
    		Error.agregar("Mas de cinco lotes abiertos en: " + entrada.par.toString());
		}
		if(estrategia.darActivo(entrada.par))
		{
			for(int i = 0; i < entrada.numeroLotes; i++)
			{
				if(estrategia.darActivo(entrada.par))
				{
					lineas.add(entrada.par + ";" + (entrada.compra ? "BUY" : "SELL") + ";" + "OPEN;" + (nueva.estrategia == IdEstrategia.BREAKOUT2 ? "1" : "0"));
				}
			}
			senales.add(nueva);
		}
		nueva.magico = new int[entrada.numeroLotes];
	}
}

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;


public class Escritor 
{
	public static ArrayList <String> lineas = new ArrayList <String> ();
	public static ArrayList <Senal> senales = new ArrayList <Senal> ();
	public static final String pathMeta = "D:/Archivos de programa/MetaTrader/experts/files/";
//	public static File archivoEscritura = new File(pathMeta + "ordenes.txt");
	public static File archivoEscritura = new File(dailyOCR.pathPrincipal + "Error.txt");
	public static File archivoMagicos = new File(pathMeta + "magicos.txt");
	
	
	public static void escribir() 
	{
		try
		{
//			archivoEscritura.delete();
			if(!lineas.isEmpty())
			{
//				archivoEscritura.createNewFile();
				FileWriter fw = new FileWriter(archivoEscritura, true);
				for(String linea : lineas.subList(0, lineas.size() - 1))
				{
					fw.write(linea + ";");
				}
				fw.write(lineas.get(lineas.size() - 1));
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

	public static void leerMagicos() 
	{
		boolean termino = false;
		int numero = 0;
		for(Senal s : senales)
			numero += s.numeroLotes;
		if(senales.size() > 0)
		{
			try 
			{
				Thread.sleep(25000 + 5000 * numero);
				for(int i = 0; i < 25; i++)
				{
					Thread.sleep(1000);
					if(archivoMagicos.exists())
						break;
				}
			}
			catch (InterruptedException e) 
			{
				// TODO Manejo de errores
			}
		}
		if(!archivoMagicos.exists())
		{
			// TODO Manejo de errores
			return;
		}
		for(int i = 0; i < 3 && !termino; i++)
		{
			try 
			{
				Scanner sc = new Scanner(archivoMagicos);
				Iterator <Senal> it = senales.iterator();
				Senal actual = it.next();
				int numeroActual = 0;
				while(sc.hasNext())
				{
					Scanner sc2 = new Scanner(sc.next());
					sc2.useDelimiter("\\Q;\\E");
					sc2.next();
					int magico = sc2.nextInt();
					actual.magico[numeroActual++] = magico;
					if(numeroActual == actual.numeroLotes)
					{
						numeroActual = 0;
						actual = it.next();
					}
					sc2.close();
				}
				sc.close();
				termino = true;
			} 
			catch (Exception e)
			{
				// TODO Manejo de errores
			}
		}
		senales.clear();
		archivoMagicos.delete();
	}

	public static void cerrar(SenalEntrada entrada, Senal afectada)
	{
		if(entrada.numero > 5)
		{
			// TODO Manejo de errores
		}
		for(int i = 0; i < entrada.numero; i++)
		{
			lineas.add(entrada.par + ";" + (entrada.compra ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.magico[i]);
		}
		afectada.numeroLotes -= entrada.numero;
		if(afectada.numeroLotes <= 0)
			return;
		afectada.magico = Arrays.copyOfRange(afectada.magico, entrada.numero, afectada.magico.length);
	}

	public static void abrir(SenalEntrada entrada, Senal nueva)
	{
		if(entrada.numero > 5)
		{
			// TODO Manejo de errores
		}
		for(int i = 0; i < entrada.numero; i++)
		{
			lineas.add(entrada.par + ";" + (entrada.compra ? "BUY" : "SELL") + ";" + "OPEN;" + 0);
		}
		nueva.magico = new int[entrada.numero];
		senales.add(nueva);
	}
}

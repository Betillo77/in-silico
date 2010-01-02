import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Escritor 
{
	public static ArrayList <String> lineas = new ArrayList <String> ();
	public static ArrayList <Senal> senales = new ArrayList <Senal> ();
	public static final String pathMeta = "D:/Archivos de programa/MetaTrader/experts/files/";
	public static File archivoEscritura = new File(pathMeta + "ordenes.txt");
	public static File archivoMagicos = new File(pathMeta + "magicos.txt");
	
	
	public static void escribir() 
	{
		try
		{
			archivoEscritura.delete();
			if(!lineas.isEmpty())
			{
				archivoEscritura.createNewFile();
				FileWriter fw = new FileWriter(archivoEscritura);
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
	
	public static void agregar(Senal senal)
	{
		lineas.add(Estrategia.darNombrePar(senal)+ ";" + (senal.compra ? "BUY" : "SELL") + ";" + (senal.tipo == Senal.TRADE ? "OPEN" : "CLOSE") + ";" + senal.magico);
		if(senal.tipo == Senal.TRADE)
		{
			senales.add(senal);
		}
	}
	
	public static void agregar(Senal senal, Senal opuesta)
	{
		
		agregar(new Senal(senal.estrategia, senal.par, senal.tipo, senal.compra, opuesta.magico));
	}

	public static void leerMagicos() 
	{
		boolean termino = false;
		for(int i = 0; i < 3 && !termino; i++)
		{
			try 
			{
				Scanner sc = new Scanner(archivoMagicos);
				while(sc.hasNext())
				{
					Scanner sc2 = new Scanner(sc.next());
					sc2.useDelimiter("\\Q;\\E");
					String par = sc2.next();
					int magico = sc2.nextInt();
					for(Senal s : senales)
					{
						if(Estrategia.darNombrePar(s).equals(par) && s.magico == 0)
						{
							s.magico = magico;
							break;
						}
					}
					sc2.close();
				}
				sc.close();
				termino = true;
			} 
			catch (Exception e)
			{
				if(!senales.isEmpty())
				{
					Error.agregar("Error leyendo los numeros magicos");
					try 
					{
						Thread.sleep(40000);
					}
					catch (Exception e1) 
					{
					}
				}
				else
				{
					termino = true;
				}
			}
		}
		senales.clear();
		archivoMagicos.delete();
	}
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;


public class Includes 
{
	static ArrayList <ArchivoOcropus> includes = new ArrayList <ArchivoOcropus> ();
	static TreeSet <ArchivoOcropus> existentes = new TreeSet <ArchivoOcropus> ();
	static TreeSet <String> posfijos = new TreeSet <String> ();
	
	static class ArchivoOcropus implements Comparable <ArchivoOcropus>
	{
		String nombre;
		ArrayList <String> llamados = new ArrayList <String> ();
		
		public ArchivoOcropus(String n, String p)
		{
			nombre = n;
			llamados.add(p);
		}
		
		public int compareTo(ArchivoOcropus otro) 
		{
			return nombre.compareTo(otro.nombre);
		}
		
		public boolean equals(Object otro)
		{
			return nombre.compareTo(((ArchivoOcropus) otro).nombre) == 0;
		}
	}
	
	
	private static void leerArchivo(File f) 
	{
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(f));
			StringBuilder sb = new StringBuilder((int) (f.length() * 2));
			String linea;
			while((linea = br.readLine()) != null)
			{
				sb.append(linea);
				sb.append('\n');
			}
			String archivo = sb.toString();
			while(archivo.contains("#include"))
			{
				archivo = archivo.substring(archivo.indexOf("#include") + "#include".length() + 1);
				if(archivo.charAt(0) == '<' || archivo.charAt(0) == '"')
				{
					archivo = archivo.substring(1);
				}
				int indice = Math.min(archivo.indexOf('"') == -1 ? Integer.MAX_VALUE : archivo.indexOf('"'), archivo.indexOf('>') == -1 ? Integer.MAX_VALUE : archivo.indexOf('>'));
				indice = Math.min(indice, archivo.indexOf('\n') == -1 ? Integer.MAX_VALUE : archivo.indexOf('\n'));
				String[] pedazos = archivo.substring(0, indice).split("/");
				String aAgregar = pedazos[pedazos.length - 1];
				int tiene = includes.indexOf(new ArchivoOcropus(aAgregar, ""));
				if(tiene != -1)
				{
					if(pedazos.length != 1)
					{
						for(int i = 0; i < pedazos.length - 1; i++)
						{
							includes.get(tiene).llamados.add(pedazos[i] + "/");
						}
					}
					includes.get(tiene).llamados.add(f.getPath());
				}
				else
				{
					ArchivoOcropus nuevo = new ArchivoOcropus(aAgregar, "");
					nuevo.llamados.clear();
					if(pedazos.length != 1)
					{
						for(int i = 0; i < pedazos.length - 1; i++)
						{
							nuevo.llamados.add(pedazos[i] + "/");
						}
					}
					nuevo.llamados.add(f.getPath());
					includes.add(nuevo);
				}
			}
			existentes.add(new ArchivoOcropus(f.getName(), ""));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void leerDirectorio(File directorio)
	{
		for(File f : directorio.listFiles())
		{
			String nombre = f.getName();
			if(f.isFile())
			{
				if(nombre.endsWith(".cc") || nombre.endsWith(".h"))
				{
					leerArchivo(f);
				}
				else
				{
					if(nombre.length() > 3)
						posfijos.add(nombre.substring(nombre.length() - 3));
					else
						posfijos.add(nombre);
				}
			}
			else
			{
				leerDirectorio(f);
			}
		}
	}

	public static void main(String[] args)
	{
		leerDirectorio(new File("ocropus/"));
		includes.removeAll(existentes);
		for(ArchivoOcropus a : includes)
		{
			System.out.print(a.nombre + ": ");
			for(String s : a.llamados)
			{
				System.out.print(s + " ");
			}
			System.out.println();
		}
		System.out.println();
		for(String s : posfijos)
			System.out.println(s);
	}

}

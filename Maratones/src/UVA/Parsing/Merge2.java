import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Merge2 
{
	static class Problema
	{
		String nombre;
		ArrayList <String> palabras = new ArrayList <String> ();
		ArrayList <Integer> problemas = new ArrayList <Integer> ();
		boolean eliminado = false;
		
		public Problema(String n)
		{
			nombre = n;
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		System.setIn(new FileInputStream("entrada.csv"));
		System.setOut(new PrintStream("salida.csv"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String linea;
		ArrayList <Problema> problemas = new ArrayList <Problema> (1000);
		while((linea = br.readLine()) != null)
		{
			String[] partes = linea.split(";");
			partes[2] = partes[2].replace("\"", "");
			Problema actual = new Problema(partes[0].replace("\"", ""));
			for(String palabra : actual.nombre.split(" "))
				actual.palabras.add(palabra);
			for(String numero : partes[2].split(" "))
			{
				try
				{
					actual.problemas.add(Integer.parseInt(numero));
				}
				catch(Exception e)
				{
				}
			}
			problemas.add(actual);
		}
		ArrayList <String> palabrasEliminadas = new ArrayList <String> ();
		for(int k = 1; k < 6; k++)
		{
			for(int i = 0; i < problemas.size(); i++)
			{
				Problema buscado = problemas.get(i);
				if(buscado.eliminado)
					continue;
				for(int j = i - 1; j > -1; j--)
				{
					Problema actual = problemas.get(j);
					if(actual.eliminado)
						continue;
					for(int m = 0; m < buscado.palabras.size() - k + 1; m++)
					{
						String palabraActual = "";
						for(int o = m; o < m + k; o++)
							palabraActual += buscado.palabras.get(o);
						if(palabrasEliminadas.contains(palabraActual))
							continue;
						for(int n = 0; n < actual.palabras.size() - k + 1; n++)
						{
							String palabraPosible = "";
							for(int o = n; o < n + k; o++)
								palabraPosible += actual.palabras.get(o);
							if(palabraActual.equals(palabraPosible))
							{
								int z = JOptionPane.showOptionDialog(null, buscado.nombre + "\n" + "Esta por la palabra \"" + palabraActual + "\" en:\n" + actual.nombre, "Unir", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Unir", "No unir", "Eliminar palabra"}, "No unir");
								if(z == 0)
								{
									buscado.eliminado = true;
									actual.problemas.addAll(buscado.problemas);
									break;
								}
								else if(z == 2)
								{
									palabrasEliminadas.add(palabraActual);
								}
							}
						}
						if(buscado.eliminado)
							break;
					}
					if(buscado.eliminado)
						break;
				}
			}
		}
		for(Problema p : problemas)
		{
			if(p.eliminado)
				continue;
			System.out.print("\"" + p.nombre + "\";" + p.problemas.size() + ";");
			for(int n : p.problemas)
				System.out.print(" " + n);
			System.out.println();
		}
	}
}

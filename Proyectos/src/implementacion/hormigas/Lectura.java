package implementacion.hormigas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Ciudad
{
	int x;
	int y;
}

public class Lectura 
{
	public static Object[] leer()
	{
		Scanner sc;
		try {
			sc = new Scanner(new File("src/implementacion/hormigas/Cities.xml"));
		} catch (FileNotFoundException e) {
			sc = null;
		}
		sc.nextLine();
		sc.nextLine();
		ArrayList <Ciudad> ciudades = new ArrayList <Ciudad> ();
		while(sc.hasNextLine())
		{
			boolean primero = false;
			String primeroS = "";
			String segundoS = "";
			boolean segundoInicial = false;
			boolean segundo = false;
			String a = sc.nextLine();
			if(a.equals("</CityList>"))
				break;
			for(char c : a.toCharArray())
			{
				if(!primero)
				{
					if(c == '"')
					{
						primero = true;
						continue;
					}
				}
				if(primero && !segundoInicial && !segundo)
				{
					if(c == '"')
					{
						segundoInicial = true;
						primero = true;
						continue;
					}
					primeroS += c;
				}
				if(segundoInicial && !segundo)
				{
					if(c == '"')
					{
						segundoInicial = false;
						segundo = true;
						continue;
					}
				}
				if(segundo)
				{
					if(c == '"')
					{
						break;
					}
					segundoS += c;
				}
			}
			Ciudad ci = new Ciudad();
			ci.x = Integer.parseInt(primeroS);
			ci.y = Integer.parseInt(segundoS);
			ciudades.add(ci);
		}
		double [][] datosPrueba = new double[ciudades.size()][ciudades.size()];
		for(int i = 0; i < ciudades.size(); i++)
			for(int j = 0; j < ciudades.size(); j++)
			{
				if(i == j)
				{
					datosPrueba[i][j] = Double.POSITIVE_INFINITY;
				}
				else
				{
					datosPrueba[i][j] = Math.sqrt((ciudades.get(i).x - ciudades.get(j).x) * (ciudades.get(i).x - ciudades.get(j).x) + (ciudades.get(i).y - ciudades.get(j).y) * (ciudades.get(i).y - ciudades.get(j).y));
				}
			}
		Object[] retorno = {ciudades, datosPrueba};
		return retorno;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		leer();
	}
}


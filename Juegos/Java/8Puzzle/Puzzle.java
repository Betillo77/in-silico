import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.JOptionPane;


public class Puzzle 
{
	private static String solucionar(String s)
	{
		Tablero actual = new Tablero(s, "", 0);
		ArrayList <Tablero> colaPrioridad = new ArrayList <Tablero> ();
		poner(actual, colaPrioridad);
		ArrayList <Tablero> visitados = new ArrayList <Tablero> ();
		while(true)
		{
			actual = colaPrioridad.remove(0);
			visitados.add(actual);
			if(actual.darDistanciaManhattan() == 0)
			{
				return actual.pasos;
			}
			for(Tablero hijo : actual.hijos())
			{
				if(!visitados.contains(hijo))
				{
					poner(hijo, colaPrioridad);
				}
			}
		}
	}
	
	private static void poner(Tablero actual, ArrayList <Tablero> colaPrioridad)
	{
		if(colaPrioridad.isEmpty())
		{
			colaPrioridad.add(actual);
		}
		else
		{
			for(int i = 0; i < colaPrioridad.size(); i++)
			{
				if(colaPrioridad.get(i).valor > actual.valor)
				{
					colaPrioridad.add(i, actual);
					return;
				}
			}
			colaPrioridad.add(actual);
		}
	}

	
	public static boolean contar(String s)
	{
		int acumulado = 0;
		int tamaño = s.length();
		for(int i = 0; i < tamaño; i++)
		{
			int actual = Integer.parseInt(s.substring(0, 1));
			if(actual != 0)
			{
				for(int j = 1; j < s.length(); j++)
				{
					if(Integer.parseInt(s.substring(j, j + 1)) > actual)
					{
						acumulado++;
					}
				}
			}
			s = s.substring(1, s.length());
		}
		return acumulado % 2 == 0;
	}
	public static void main(String [] args)
	{
		String s = JOptionPane.showInputDialog("Ingrese el tablero inicial");
		if(contar(s))
			System.out.print(solucionar(s));
		else
			System.out.print("Error: estado no tiene solucion");
	}


}

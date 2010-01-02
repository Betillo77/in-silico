package implementacion.aStar;

import java.util.LinkedList;

import javax.swing.JOptionPane;

import genericos.aStar.AStar;
import genericos.aStar.AStarState;
import genericos.anchura.Estado;

public class OchoPuzzle implements AStarState
{

	
	public String tablero = "";
	public String pasos = "";
	public int nPasos = 0;
	public AStarState padre;
	
	public OchoPuzzle(String nuevo, String p, int np)
	{
		tablero = nuevo;
		pasos = p;
		nPasos = np;
 	}
	
	public Integer darDistanciaManhattan()
	{
		String tableroNuevo = tablero;
		int acumulado = 0;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				String actual = tableroNuevo.substring(0, 1);
				int nActual = Integer.parseInt(actual);
				tableroNuevo = tableroNuevo.substring(1, tableroNuevo.length());
				if(nActual == 0)
					continue;
				int columna = (nActual - 1) % 3;
				int fila = (nActual - 1) / 3;
				acumulado += Math.abs(columna - j);
				acumulado += Math.abs(fila - i);
				
			}
		}
		return acumulado;
	}

	public double getH() 
	{
		return darDistanciaManhattan() + nPasos;
	}

	@SuppressWarnings("unchecked")
	public Comparable getKey() 
	{
		int acum = 0;
		for(int i = 0; i < 9; i++)
		{
			acum += tablero.charAt(i) << i * 4;
		}
		return acum;
	}

	public AStarState getPadre() 
	{
		return padre;
	}

	public void setPadre(AStarState padre) 
	{
		this.padre = padre;
	}

	@SuppressWarnings("unchecked")
	public LinkedList sigEstados() 
	{
		String tableroNuevo = tablero;
		int filaCero = 0;
		int columnaCero = 0;
		int posicionCero = tableroNuevo.indexOf('0');
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				String actual = tableroNuevo.substring(0, 1);
				int nActual = Integer.parseInt(actual);
				if(nActual == 0)
				{
					filaCero = i;
					columnaCero = j;
				}
				tableroNuevo = tableroNuevo.substring(1, tableroNuevo.length());
			}
		}
		LinkedList hijos = new LinkedList();
		if(filaCero != 0)
		{
			tableroNuevo = tablero;
			char temp = tableroNuevo.charAt(posicionCero - 3);
			tableroNuevo = tableroNuevo.replace(temp, 'A');
			tableroNuevo = tableroNuevo.replace('0', temp);
			tableroNuevo = tableroNuevo.replace('A', '0');
			hijos.add(new OchoPuzzle(tableroNuevo, pasos + "B", nPasos + 1));
		}
		if(columnaCero != 0)
		{
			tableroNuevo = tablero;
			char temp = tableroNuevo.charAt(posicionCero - 1);
			tableroNuevo = tableroNuevo.replace(temp, 'A');
			tableroNuevo = tableroNuevo.replace('0', temp);
			tableroNuevo = tableroNuevo.replace('A', '0');
			hijos.add(new OchoPuzzle(tableroNuevo, pasos + "D", nPasos + 1));
		}
		if(filaCero != 2)
		{
			tableroNuevo = tablero;
			char temp = tableroNuevo.charAt(posicionCero + 3);
			tableroNuevo = tableroNuevo.replace(temp, 'A');
			tableroNuevo = tableroNuevo.replace('0', temp);
			tableroNuevo = tableroNuevo.replace('A', '0');
			hijos.add(new OchoPuzzle(tableroNuevo, pasos + "S", nPasos + 1));
		}
		if(columnaCero != 2)
		{
			tableroNuevo = tablero;
			char temp = tableroNuevo.charAt(posicionCero + 1);
			tableroNuevo = tableroNuevo.replace(temp, 'A');
			tableroNuevo = tableroNuevo.replace('0', temp);
			tableroNuevo = tableroNuevo.replace('A', '0');
			hijos.add(new OchoPuzzle(tableroNuevo, pasos + "I", nPasos + 1));
		}
		return hijos;
	}

	public void setPadre(Estado padre)
	{
		this.padre = (AStarState) padre;
	}
	
	public static boolean contar(String tablero)
	{
		int acumulado = 0;
		int tamaño = tablero.length();
		for(int i = 0; i < tamaño; i++)
		{
			int actual = Integer.parseInt(tablero.substring(0, 1));
			if(actual != 0)
			{
				for(int j = 1; j < tablero.length(); j++)
				{
					if(Integer.parseInt(tablero.substring(j, j + 1)) > actual)
					{
						acumulado++;
					}
				}
			}
			tablero = tablero.substring(1, tablero.length());
		}
		return acumulado % 2 == 0;
	}
	
	public static void main(String [] args) 
	{
		String i = JOptionPane.showInputDialog("Ingrese el tablero inicial");
		OchoPuzzle inicio = new OchoPuzzle(i, "", 0);
		OchoPuzzle destino = new OchoPuzzle("123456780", "", 0);
		AStar solucion = new AStar();
		if(contar(i))
			System.out.print(((OchoPuzzle)solucion.resolver(inicio, destino)).pasos);
		else
			System.out.print("Error: estado no tiene solucion");
	}
}

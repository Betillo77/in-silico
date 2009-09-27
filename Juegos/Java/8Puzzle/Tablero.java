import java.util.ArrayList;


public class Tablero 
{

	public String tablero = "";
	public String pasos = "";
	public int nPasos = 0;
	public int valor = 0;
	
	public Tablero(String nuevo, String p, int np)
	{
		tablero = nuevo;
		pasos = p;
		nPasos = np;
		valor = darDistanciaManhattan() + nPasos;
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
	
	public ArrayList <Tablero> hijos()
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
		ArrayList <Tablero> hijos = new ArrayList <Tablero> ();
		if(filaCero != 0)
		{
			tableroNuevo = tablero;
			char temp = tableroNuevo.charAt(posicionCero - 3);
			tableroNuevo = tableroNuevo.replace(temp, 'A');
			tableroNuevo = tableroNuevo.replace('0', temp);
			tableroNuevo = tableroNuevo.replace('A', '0');
			hijos.add(new Tablero(tableroNuevo, pasos + "B", nPasos + 1));
		}
		if(columnaCero != 0)
		{
			tableroNuevo = tablero;
			char temp = tableroNuevo.charAt(posicionCero - 1);
			tableroNuevo = tableroNuevo.replace(temp, 'A');
			tableroNuevo = tableroNuevo.replace('0', temp);
			tableroNuevo = tableroNuevo.replace('A', '0');
			hijos.add(new Tablero(tableroNuevo, pasos + "D", nPasos + 1));
		}
		if(filaCero != 2)
		{
			tableroNuevo = tablero;
			char temp = tableroNuevo.charAt(posicionCero + 3);
			tableroNuevo = tableroNuevo.replace(temp, 'A');
			tableroNuevo = tableroNuevo.replace('0', temp);
			tableroNuevo = tableroNuevo.replace('A', '0');
			hijos.add(new Tablero(tableroNuevo, pasos + "S", nPasos + 1));
		}
		if(columnaCero != 2)
		{
			tableroNuevo = tablero;
			char temp = tableroNuevo.charAt(posicionCero + 1);
			tableroNuevo = tableroNuevo.replace(temp, 'A');
			tableroNuevo = tableroNuevo.replace('0', temp);
			tableroNuevo = tableroNuevo.replace('A', '0');
			hijos.add(new Tablero(tableroNuevo, pasos + "I", nPasos + 1));
		}
		return hijos;
	}
	
	@Override
	public boolean equals(Object otro)
	{
		
		return tablero.equals(((Tablero)otro).tablero);
	}
	
	public static void main(String [] args) 
	{
		Tablero b = new Tablero("123405678", "", 0);
		b.hijos();
	}
}

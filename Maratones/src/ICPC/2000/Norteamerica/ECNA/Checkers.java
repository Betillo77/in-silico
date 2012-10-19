import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Checkers 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		String nextLine()
		{
			try { return br.readLine(); } catch(Exception e) { throw new RuntimeException(e); }
		}
		
		String next()
		{
			while(st == null || !st.hasMoreTokens())
			{
				String linea = nextLine();
				if(linea == null)
					return null;
				st = new StringTokenizer(linea);
			}
			return st.nextToken();
		}
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		Integer nextInteger()
		{
			String next = next();
			if(next == null)
				return null;
			return Integer.parseInt(next);
		}
		
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
	}

	
	static final String[] tS = new String[]
			{
				"0-1-0-2-0-3-0-4",
				"5-0-6-0-7-0-8-0",
				"0-9-0-10-0-11-0-12",
				"13-0-14-0-15-0-16-0",
				"0-17-0-18-0-19-0-20",
				"21-0-22-0-23-0-24-0",
				"0-25-0-26-0-27-0-28",
				"29-0-30-0-31-0-32-0"
			};

	static final int[][] tableroI = darTablero();
	
	static int[][] darTablero()
	{
		int[][] tablero = new int[8][8];
		for(int i = 0; i < tS.length; i++)
		{
			String s = tS[i];
			StringTokenizer st = new StringTokenizer(s, "-");
			for(int j = 0; st.hasMoreTokens(); j++)
				tablero[i][j] = Integer.parseInt(st.nextToken());
		}
		return tablero;
	}
	
	static boolean valida(int i, int j)
	{
		return i >= 0 && i < 8 && j >= 0 && j < 8;
	}
	
	static Posicion[] tablero = new Posicion[33];
	static int[][] diffsA = new int[][]{{-1, 1}, {-1, -1}};
	static int[][] diffsB = new int[][]{{1, 1}, {1, -1}};
	
	static class Posicion
	{
		Boolean quien;
		boolean rey;
		boolean volvioRey = false;
		
		int[][] vecinosAbajo;
		int[][] vecinosArriba;
		int[][] todosVecinos;
		
		
		void generarVecinos(int i, int j)
		{
			ArrayList <int[]> arriba = new ArrayList <int[]> ();
			for(int[] d : diffsA)
			{
				int iNuevo = i + d[0];
				int jNuevo = j + d[1];
				if(valida(iNuevo, jNuevo))
				{
					int valor = tableroI[iNuevo][jNuevo];
					int iN2 = iNuevo + d[0];
					int jN2 = jNuevo + d[1];
					int salto = 0;
					if(valida(iN2, jN2))
						salto = tableroI[iN2][jN2];
					arriba.add(new int[]{valor, salto});
				}
			}
			ArrayList <int[]> abajo = new ArrayList <int[]> ();
			for(int[] d : diffsB)
			{
				int iNuevo = i + d[0];
				int jNuevo = j + d[1];
				if(valida(iNuevo, jNuevo))
				{
					int valor = tableroI[iNuevo][jNuevo];
					int iN2 = iNuevo + d[0];
					int jN2 = jNuevo + d[1];
					int salto = 0;
					if(valida(iN2, jN2))
						salto = tableroI[iN2][jN2];
					abajo.add(new int[]{valor, salto});
				}
			}
			ArrayList <int[]> todos = new ArrayList <int[]> ();
			todos.addAll(arriba);
			todos.addAll(abajo);
			vecinosArriba = arriba.toArray(new int[0][]);
			vecinosAbajo = abajo.toArray(new int[0][]);
			todosVecinos = todos.toArray(new int[0][]);
		}
		
		boolean esValida(int aDonde, boolean color)
		{
			if(quien == null || quien.booleanValue() != color || tablero[aDonde].quien != null)
				return false;
			for(int[] siguiente : rey ? todosVecinos : color ? vecinosAbajo : vecinosArriba)
			{
				if(aDonde == siguiente[0])
					return true;
				else if(aDonde == siguiente[1])
					return tablero[siguiente[0]].quien != null && tablero[siguiente[0]].quien.booleanValue() != color;
			}
			return false;
		}
		
		boolean tieneSalto()
		{
			if(quien == null)
				return false;
			if(volvioRey)
				return false;
			for(int[] siguiente : rey ? todosVecinos : quien.booleanValue() ? vecinosAbajo : vecinosArriba)
			{
				if(siguiente[1] != 0 && tablero[siguiente[0]].quien != null && tablero[siguiente[0]].quien.booleanValue() != quien.booleanValue() && tablero[siguiente[1]].quien == null)
					return true;
			}
			return false;
		}
		
		boolean esSalto(int aDonde, boolean color)
		{
			if(quien == null || quien.booleanValue() != color || tablero[aDonde].quien != null)
				return false;
			for(int[] siguiente : rey ? todosVecinos : color ? vecinosAbajo : vecinosArriba)
			{
				if(aDonde == siguiente[0])
					return false;
				else if(aDonde == siguiente[1])
					return true;
			}
			return false;
		}
		
		void jugar(int aDonde)
		{
			tablero[aDonde].quien = quien;
			tablero[aDonde].rey = rey;
			quien = null;
			rey = false;
			if(tablero[aDonde].quien != null)
			{
				for(int[] siguiente : tablero[aDonde].rey ? todosVecinos : tablero[aDonde].quien.booleanValue() ? vecinosAbajo : vecinosArriba)
				{
					if(siguiente[1] == aDonde)
					{
						tablero[siguiente[0]].quien = null;
						tablero[siguiente[0]].rey = false;
					}
				}
			}
			boolean reyNuevo = (tablero[aDonde].quien.booleanValue() ? tablero[aDonde].vecinosAbajo : tablero[aDonde].vecinosArriba).length == 0;
			if(reyNuevo && !tablero[aDonde].rey)
			{
				tablero[aDonde].rey = true;
				tablero[aDonde].volvioRey = true;
			}
			
		}
	}
	
	private static boolean tieneSalto(boolean actual) 
	{
		for(int i = 1; i < 33; i++)
			if(tablero[i].quien != null && tablero[i].quien.booleanValue() == actual)
				if(tablero[i].tieneSalto())
					return true;
		return false;
	}

	private static int[] readMoves(String s)
	{
		StringTokenizer st = new StringTokenizer(s, "-");
		int[] res = new int[st.countTokens()];
		for(int i = 0; i < res.length; i++)
			res[i] = Integer.parseInt(st.nextToken());
		return res;
	}
	
	public static void main(String[] args)
	{
		for(int i = 1; i < 33; i++)
			tablero[i] = new Posicion();
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(tableroI[i][j] != 0)
					tablero[tableroI[i][j]].generarVecinos(i, j);
		Scanner sc = new Scanner();
		while(true)
		{
			int r = sc.nextInt();
			int w = sc.nextInt();
			if(r == 0 && w == 0)
				return;
			for(int i = 1; i < 33; i++)
			{
				tablero[i].quien = null;
				tablero[i].rey = false;
			}
			for(int i = 0; i < r; i++)
			{
				int val = sc.nextInt();
				boolean rey = false;
				if(val < 0)
				{
					rey = true;
					val = -val;
				}
				tablero[val].quien = true;
				tablero[val].rey = rey;
			}
			for(int i = 0; i < w; i++)
			{
				int val = sc.nextInt();
				boolean rey = false;
				if(val < 0)
				{
					rey = true;
					val = -val;
				}
				tablero[val].quien = false;
				tablero[val].rey = rey;
			}
			int nMoves = sc.nextInt();
			boolean actual = sc.next().contains("R");
			String[] moves = new String[nMoves];
			for(int i = 0; i < nMoves; i++)
				moves[i] = sc.next();
			int i;
			out:
			for(i = 0; i < nMoves; i++)
			{
				for(int j = 1; j < 33; j++)
					tablero[j].volvioRey = false;
				int[] v = readMoves(moves[i]);
				Posicion anterior = tablero[v[0]];
				boolean anteriorSalto = true;
				for(int j = 1; j < v.length; j++)
				{
					if(!anterior.esValida(v[j], actual))
						break out;
					boolean esSalto = anterior.esSalto(v[j], actual);
					anteriorSalto = esSalto;
					if(j == 1 && esSalto != tieneSalto(actual))
						break out;
					if(j != 1 && esSalto != anterior.tieneSalto())
						break out;
					if(!esSalto && j != v.length - 1)
						break out;
					anterior.jugar(v[j]);
					anterior = tablero[v[j]];
				}
				if(anteriorSalto && anterior.tieneSalto())
					break out;
				actual = !actual;
			}
			if(i == nMoves)
				System.out.println("All moves valid");
			else
				System.out.println("Move " + (i + 1) + " is invalid");
		}
		
	}
}
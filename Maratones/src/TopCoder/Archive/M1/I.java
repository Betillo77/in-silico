import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;


public class I 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		
		String nextLine()
		{
			try { return br.readLine(); } catch(Exception e) { throw new RuntimeException(e); }
		}
		
		String next()
		{
			while(!st.hasMoreTokens())
			{
				String line = nextLine();
				if(line == null) return null;
				st = new StringTokenizer(line);
			}
			return st.nextToken();
		}
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
	}


	private static String mejor(String mejor, String posible) 
	{
		if(mejor == null)
			return posible;
		if(posible == null)
			return mejor;
		if(mejor.length() == posible.length())
			if(mejor.compareTo(posible) < 0)
				return mejor;
			else
				return posible;
		if(mejor.length() < posible.length())
			return mejor;
		else
			return posible;
	}
	
	static class Entrada
	{
		int i0, i1, j0, j1,  pF, pC;

		public Entrada(int i02, int j02, int i12, int j12, int pF2, int pC2) 
		{
			i0 = i02;
			i1 = i12;
			j0 = j02;
			j1 = j12;
			pF = pF2;
			pC = pC2;
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + i0;
			result = prime * result + i1;
			result = prime * result + j0;
			result = prime * result + j1;
			result = prime * result + pC;
			result = prime * result + pF;
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entrada other = (Entrada) obj;
			if (i0 != other.i0)
				return false;
			if (i1 != other.i1)
				return false;
			if (j0 != other.j0)
				return false;
			if (j1 != other.j1)
				return false;
			if (pC != other.pC)
				return false;
			if (pF != other.pF)
				return false;
			return true;
		}
		
		
	}
	static boolean[][] imagen;
	static HashMap <Entrada, String> dp = new HashMap <Entrada, String> ();
	
	static String dp(int i0, int j0, int i1, int j1, int pF, int pC)
	{
		int paridadFilas = 1 << pF;
		int paridadColumnas = 1 << pC;
		if(paridadFilas > 32 || paridadColumnas > 32)
			return null;
		if(i0 >= imagen.length || j0 >= imagen[0].length)
			return null;
		Entrada e = new Entrada(i0, j0, i1, j1, pF, pC);
		if(dp.containsKey(e))
			return dp.get(e);
		boolean valor = imagen[i0][j0];
		boolean iguales = true;
		int columnas = 0;
		int filas = 0;
		for(int i = i0; i < i1; i += paridadFilas)
		{
			for(int j = j0; (iguales || (i == i0)) && j < j1; j += paridadColumnas)
			{
				if(i == i0)
					columnas++;
				if(imagen[i][j] != valor)
				{
					iguales = false;
				}
			}
			filas++;
		}
		if(iguales)
			return valor ? "B" : "W";
		String mejor = null;
		if(columnas > 1)
		{
			int mitad = (j1 - j0) / 2;
			if(((j1 - j0) & 1) == 1)
				mitad++;
			int siguiente = mitad;
			while((siguiente % paridadColumnas) != 0)
				siguiente++;
			String left = dp(i0, j0, i1, j0 + mitad, pF, pC);
			String right = siguiente >= j1 ? null : dp(i0, j0 + siguiente, i1, j1, pF, pC);
			String posible = left == null || right == null ? null : "L" + left + right;
			mejor = mejor(mejor, posible);
		}
		if(filas > 1)
		{
			int mitad = (i1 - i0) / 2;
			if(((i1 - i0) & 1) == 1)
				mitad++;
			int siguiente = mitad;
			while((siguiente % paridadFilas) != 0)
				siguiente++;
			String left = dp(i0, j0, i0 + mitad, j1, pF, pC);
			String right = siguiente >= i1 ? null : dp(i0 + siguiente, j0, i1, j1, pF, pC);
			String posible = left == null || right == null ? null : "U" + left + right;
			mejor = mejor(mejor, posible);
		}
		if(filas > 1)
		{
			String left = dp(i0, j0, i1, j1, pF + 1, pC);
			String right = i0 + paridadFilas >= i1 ? null : dp(i0 + paridadFilas, j0, i1, j1, pF + 1, pC);
			String posible = left == null || right == null ? null : "R" + left + right;
			mejor = mejor(mejor, posible);
		}
		if(columnas > 1)
		{
			String left = dp(i0, j0, i1, j1, pF, pC + 1);
			String right = j0 + paridadColumnas >= j1 ? null : dp(i0, j0 + paridadColumnas, i1, j1, pF, pC + 1);
			String posible = left == null || right == null ? null : "C" + left + right;
			mejor = mejor(mejor, posible);
		}
		dp.put(e, mejor);
		return mejor;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			dp.clear();
			imagen = new boolean[n][m];
			for(int i = 0; i < n; i++)
			{
				String linea = sc.next();
				for(int j = 0; j < m; j++)
					imagen[i][j] = linea.charAt(j) == 'B';
			}	
			System.out.println(dp(0, 0, n, m, 0, 0));
		}
	}
}

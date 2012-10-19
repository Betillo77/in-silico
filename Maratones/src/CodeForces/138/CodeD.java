import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class CodeD 
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
		
		long nextLong()
		{
			return Long.parseLong(next());
		}
		
		
		private double nextDouble() 
		{
			return Double.parseDouble(next());
		}

		int[] nextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
		
		Integer[] nextIntegerArray(int n)
		{
			Integer[] nA = new Integer[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
		
		long[] nextLongArray(int n)
		{
			long[] nA = new long[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextLong();
			return nA;
		}
		
		Long[] nextLongOArray(int n)
		{
			Long[] nA = new Long[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextLong();
			return nA;
		}
		
		double[] nextDoubleArray(int n)
		{
			double[] nA = new double[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextDouble();
			return nA;
		}

		Double[] nextDoubleOArray(int n)
		{
			Double[] nA = new Double[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextDouble();
			return nA;
		}
		
		String[] nextStringArray(int n)
		{
			String[] nA = new String[n];
			for(int i = 0; i < n; i++)
				nA[i] = next();
			return nA;
		}
	}
	
	static int nSegmentos;
	
	static class Culebra
	{
		byte[] segmentos;
		boolean termino;
		int cuenta;
		
		public Culebra(byte[] is)
		{
			segmentos = is;
		}
		
		static final int[][] diffs = new int[][] {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};
		
		Culebra generarHijos(LinkedList <Culebra> c, HashSet <Culebra> h)
		{
			for(int[] d : diffs)
			{
				int iCab = segmentos[0] + d[0];
				int jCab = segmentos[1] + d[1];
				if(valido(iCab, jCab))
				{
					Culebra hijo = generarHijo(iCab, jCab);
					if(!h.contains(hijo))
					{
						h.add(hijo);
						c.add(hijo);
						if(hijo.termino)
							return hijo;
					}
				}
			}
			return null;
		}

		private Culebra generarHijo(int iCab, int jCab) 
		{
			byte[] sNuevos = segmentos.clone();
			Culebra nueva = new Culebra(sNuevos);
			sNuevos[0] = (byte) iCab;
			sNuevos[1] = (byte) jCab;
			for(int i = 1; i < nSegmentos; i++)
			{
				sNuevos[i << 1] = segmentos[(i - 1) << 1];
				sNuevos[(i << 1) + 1] = segmentos[((i - 1) << 1) + 1];
			}
			nueva.cuenta = cuenta + 1;
			nueva.termino = original[iCab][jCab] == '@';
			return nueva;
		}

		static final boolean[][] tieneSegmentos = new boolean[15][15];
		
		private boolean valido(int iCab, int jCab)
		{
			boolean bien = iCab >= 0 && iCab < original.length && jCab >= 0 && jCab < original[0].length && original[iCab][jCab] != '#';
			if(!bien)
				return bien;
			for(int i = 0; i < nSegmentos - 1; i++)
				tieneSegmentos[segmentos[i << 1]][segmentos[(i << 1) + 1]] = true;
			if(tieneSegmentos[iCab][jCab])
				bien = false;
			for(int i = 0; i < nSegmentos - 1; i++)
				tieneSegmentos[segmentos[i << 1]][segmentos[(i << 1) + 1]] = false;
			return bien;
		}

		@Override
		public int hashCode()
		{
			return Arrays.hashCode(segmentos);
		}

		@Override
		public boolean equals(Object obj)
		{
			return Arrays.equals(segmentos, ((Culebra) obj).segmentos);
		}
	}
	
	static byte[] calcularSegmentos(char[][] tablero)
	{
		byte[] segmentos = new byte[nSegmentos << 1];
		for(int i = 0; i < tablero.length; i++)
		{
			for(int j = 0; j < tablero[0].length; j++)
				if(tablero[i][j] >= '1' && tablero[i][j] <= '9')
				{
					int indice = (tablero[i][j] - '1') << 1;
					segmentos[indice] = (byte) i;
					segmentos[indice + 1] = (byte) j;
					tablero[i][j] = '.';
				}
		}
		return segmentos;
	}
	
	static char[][] original;
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int m = sc.nextInt();
		original = new char[n][];
		for(int i = 0; i < n; i++)
			original[i] = sc.next().toCharArray();
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				if(original[i][j] >= '1' && original[i][j] <= '9')
					nSegmentos = Math.max(nSegmentos, original[i][j] - '0');
		Culebra inicial = new Culebra(calcularSegmentos(original));
		inicial.cuenta = 0;
		LinkedList <Culebra> c = new LinkedList <Culebra> ();
		HashSet <Culebra> h = new HashSet <Culebra> ();
		c.add(inicial);
		h.add(inicial);
		while(!c.isEmpty())
		{
			Culebra actual = c.poll();
			Culebra posible = actual.generarHijos(c, h);
			if(posible != null)
			{
				System.out.println(posible.cuenta);
				return;
			}
		}
		System.out.println("-1");
	}
}
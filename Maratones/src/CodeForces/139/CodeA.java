import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CodeA 
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
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int arriba = sc.nextInt();
		int abajoActual = 7 - arriba;
		boolean posible = true;
		for(int i = 0; i < n; i++)
		{
			boolean[] p = new boolean[7];
			int izquierda = sc.nextInt();
			int derecha = sc.nextInt();
			p[izquierda] = true;
			p[derecha] = true;
			p[7 - izquierda] = true;
			p[7 - derecha] = true;
			if(!p[7 - abajoActual])
				abajoActual = 7 - abajoActual;
			else
				posible = false;
		}
		System.out.println(posible ? "YES" : "NO");
	}
}
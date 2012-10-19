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
		int x = sc.nextInt();
		int y = sc.nextInt();
		int z = sc.nextInt();
		int b = (int) Math.sqrt((x * y) / z);
		int a = x / b;
		int c = z / a;
		System.out.println(4 * a + 4 * b + 4 * c);
	}
}
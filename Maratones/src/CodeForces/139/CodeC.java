import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CodeC 
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

	static int m;
	static int x;
	static int y;
	static int[][] costo;
	static Integer[][][] dp = new Integer[1005][1005][2];
	
	public static int dp(int actual, int cuenta, boolean cual)
	{
		if(dp[actual][cuenta][cual ? 1 : 0] != null)
			return dp[actual][cuenta][cual ? 1 : 0];
		if(actual == m)
			return dp[actual][cuenta][cual ? 1 : 0] = (cuenta >= x && cuenta <= y) ? 0 : Integer.MAX_VALUE;
		int respuesta = Integer.MAX_VALUE;
		if(cuenta > y)
			return dp[actual][cuenta][cual ? 1 : 0] = Integer.MAX_VALUE;
		if(cual && (cuenta >= x && cuenta <= y))
		{
			int siguiente = dp(actual + 1, 1, false);
			if(siguiente != Integer.MAX_VALUE)
				respuesta = Math.min(respuesta, siguiente + costo[actual][0]);
		}
		if(!cual && (cuenta >= x && cuenta <= y))
		{
			int siguiente = dp(actual + 1, 1, true);
			if(siguiente != Integer.MAX_VALUE)
				respuesta = Math.min(respuesta, siguiente + costo[actual][1]);
		}
		int siguiente = dp(actual + 1, cuenta + 1, cual);
		if(siguiente != Integer.MAX_VALUE)
			respuesta = Math.min(respuesta, siguiente + costo[actual][cual ? 1 : 0]);
		return dp[actual][cuenta][cual ? 1 : 0] = respuesta;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		m = sc.nextInt();
		x = sc.nextInt();
		y = sc.nextInt();
		costo = new int[m][2];
		for(int i = 0; i < n; i++)
		{
			String s = sc.next();
			int j = 0;
			for(char c : s.toCharArray())
			{
				if(c == '#')
					costo[j++][0]++;
				else
					costo[j++][1]++;
			}
		}
		int a = dp(1, 1, true);
		if(a != Integer.MAX_VALUE)
			a += costo[0][1];
		int b = dp(1, 1, false);
		if(b != Integer.MAX_VALUE)
			b += costo[0][0];
		System.out.println(Math.min(a, b));
	}
}
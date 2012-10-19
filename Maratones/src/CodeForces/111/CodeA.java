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
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		int[] readNextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
	}
	
	static int n;
	static int sumaTotal;
	static int[] monedas;
	static Integer[][] dp;
	
	static int dp(int actual, int suma)
	{
		if(dp[actual][suma] != null)
			return dp[actual][suma];
		if(actual == n)
			return dp[actual][suma] = suma > (sumaTotal - suma) ? 0 : 1000000; 
		int menor = dp(actual + 1, suma);
		menor = Math.min(menor, 1 + dp(actual + 1, suma + monedas[actual]));
		return dp[actual][suma] = menor;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		n = sc.nextInt();
		monedas = sc.readNextIntArray(n);
		sumaTotal = 0;
		for(int i : monedas)
			sumaTotal += i;
		dp = new Integer[105][10001];
		System.out.println(dp(0, 0));
	}

}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class F 
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
	
	static Integer[][][] dp = new Integer[110][110][2];
	static int nTotal;
	
	static int dp(int n, int k, boolean anterior)
	{
		if(k < 0)
			return 0;
		if(dp[n][k][anterior ? 1 : 0] != null)
			return dp[n][k][anterior ? 1 : 0];
		if(n == nTotal)
			return dp[n][k][anterior ? 1 : 0] = k == 0 ? 1 : 0;
		int cuenta = 0;
		if(anterior)
			cuenta += dp(n + 1, k - 1, true);
		else
			cuenta += dp(n + 1, k, true);
		cuenta += dp(n + 1, k, false);
		return dp[n][k][anterior ? 1 : 0] = cuenta;
	}

	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int numero = sc.nextInt();
			dp = new Integer[110][110][2];
			nTotal = sc.nextInt();
			int k = sc.nextInt();
			System.out.println(numero + " " + dp(0, k, false));
		}
	}
}

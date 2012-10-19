import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class C 
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
	
	static Integer[][] dp = new Integer[1010][55];
	
	static int dp(int pisos, int m)
	{
		if(dp[pisos][m] != null)
			return dp[pisos][m];
		if(m == 1 || pisos <= 1)
			return dp[pisos][m] = pisos;
		int mejor = Integer.MAX_VALUE;
		for(int i = 1; i < pisos; i++)
			mejor = Math.min(1 + Math.max(dp(i, m - 1), dp(pisos - i, m)), mejor);
		if(mejor == Integer.MAX_VALUE)
			System.out.print("");
		return dp[pisos][m] = mejor;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int numero = sc.nextInt();
			int m = sc.nextInt();
			int pisos = sc.nextInt();
			System.out.println(numero + " " + (dp(pisos, m) - 1));
		}
	}
}

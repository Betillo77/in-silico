import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class B 
{
	static class Scanner
	{
		BufferedReader br;
		StringTokenizer st;

		public Scanner()
		{ 
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public String next()
		{

			while(st == null || !st.hasMoreTokens())
			{
				try { st = new StringTokenizer(br.readLine()); }
				catch(Exception e) { throw new RuntimeException(); }
			}
			return st.nextToken();
		}

		public int nextInt()
		{
			return Integer.parseInt(next());
		}

		public double nextDouble()
		{
			return Double.parseDouble(next());
		}

		public String nextLine()
		{
			st = null;
			try { return br.readLine(); }
			catch(Exception e) { throw new RuntimeException(); }
		}

		public boolean endLine()
		{
			try 
			{
				String next = br.readLine();
				while(next != null && next.trim().isEmpty())
					next = br.readLine();
				if(next == null)
					return true;
				st = new StringTokenizer(next);
				return st.hasMoreTokens();
			}
			catch(Exception e) { throw new RuntimeException(); }
		}
	}
	
	static Long[] dp = new Long[1000];
	
	static long dp(int indice)
	{
		if(dp[indice] != null)
			return dp[indice];
		if(indice == 0)
			return dp[indice] = 0L;
		return dp[indice] = indice * ((indice + 1) * (indice + 2)) / 2 + dp(indice - 1);
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int c = sc.nextInt();
			System.out.println(caso + " " + c + " " + dp(c));
		}
	}
}
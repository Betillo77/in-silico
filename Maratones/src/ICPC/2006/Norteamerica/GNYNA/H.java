import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class H 
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
	
	static Long[][][] dp = new Long[31][31][1001];
	static int v;
	static int[] vs = new int[30];
	static int inicial;
	
	static long dp(int actual, int menor, int cuenta)
	{
		if(dp[actual][menor][cuenta] != null)
			return dp[actual][menor][cuenta];
		if(actual == v)
		{
			if((menor == v || cuenta < vs[menor]) && cuenta != inicial)
				return dp[actual][menor][cuenta] = 1L;
			else
				return dp[actual][menor][cuenta] = 0L;
		}
		long c = 0;
		c += dp(actual + 1, (menor == v || vs[menor] > vs[actual]) ? actual : menor, cuenta);
		if(vs[actual] <= cuenta)
			c += dp(actual + 1, menor, cuenta - vs[actual]);
		return dp[actual][menor][cuenta] = c;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			v = sc.nextInt();
			int d = sc.nextInt();
			inicial = d;
			for(int i = 0; i < v; i++)
				vs[i] = sc.nextInt();
			for(int i = 0; i <= v; i++)
				for(int j = 0; j <= v; j++)
					for(int k = 0; k <= 1000; k++)
						dp[i][j][k] = null;
			System.out.println(caso + " " + dp(0, v, d));
		}
	}
}
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.StringTokenizer;

public class Bet 
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
		
		public long nextLong()
		{
			return Long.parseLong(next());
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
	}
	
	static int[] board;
	static Double[][] dp;
	
	static double dp(int pos, int t)
	{
		if(pos >= board.length && t >= 0)
			return 1.0;
		if(pos >= board.length || t <= 0)
			return 0.0;
		try
		{
			if(dp[pos][t] != null)
				return dp[pos][t];
		}
		catch(Throwable e)
		{
			return 0.0;
		}
		int nextA = Math.min(board.length, pos + 1);
		double resA = 0;
		if(nextA == board.length)
			resA = dp(nextA, t - 1);
		else
		{
			if(board[nextA] == Integer.MAX_VALUE)
				resA = dp(nextA, t - 2);
			else
			{
				int nextDest = Math.min(board.length, Math.max(0, nextA + board[nextA]));
				resA = dp(nextDest, t - 1);
			}
		}
		int nextB = Math.min(board.length, pos + 2);
		double resB = 0;
		if(nextB == board.length)
			resB = dp(nextB, t - 1);
		else
		{
			if(board[nextB] == Integer.MAX_VALUE)
				resB = dp(nextB, t - 2);
			else
			{
				int nextDest = Math.min(board.length, Math.max(0, nextB + board[nextB]));
				resB = dp(nextDest, t - 1);
			}
		}
		return dp[pos][t] = 0.5 * resA + 0.5 * resB;
	}
	
	public static void main(String[] args)
	{
			Scanner sc = new Scanner();
			int casos = sc.nextInt();
			for(int caso = 1; caso <= casos; caso++)
			{
				int m = sc.nextInt();
				int t = sc.nextInt();
				dp = new Double[m + 3][t + 3];
				board = new int[m + 1];
				for(int i = 1; i < board.length; i++)
				{
					String val = sc.next();
					if(val.equals("L"))
						board[i] = Integer.MAX_VALUE;
					else
					{
						try
						{
							board[i] = Integer.parseInt(val);
						}
						catch(Throwable t1)
						{
							board[i] = new BigDecimal(val).intValue();
						}
					}
				}
				double res = dp(0, t);
				if(Math.abs(res - 0.5) < 1e-8)
					System.out.printf("Push. 0.5000\n");
				else if(res < 0.5)
					System.out.printf("Bet against. %.4f\n", res);
				else
					System.out.printf("Bet for. %.4f\n", res);
			}
	}

}

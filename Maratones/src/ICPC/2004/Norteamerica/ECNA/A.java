import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class A 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		String nextLine()
		{
			try
			{
				String s = br.readLine();
				return s;
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		
		String next()
		{
			while(st == null || !st.hasMoreTokens())
			{
				String linea = nextLine();
				if(linea == null)
					return null;
				st = new StringTokenizer(linea);
			}
			return st.nextToken();
		}
	}
	
	static Long[] dp;
	static char[] w;
	
	static long dp(int i)
	{
		if(dp[i] != null)
			return dp[i];
		if(i == w.length)
			return dp[i] = 1L;
		if(w[i] == '0')
			return dp[i] = 0L;
		long cuenta = dp(i + 1);
		if(i != w.length - 1 && (w[i] == '1' || (w[i] == '2' && w[i + 1] <= '6')))
			cuenta += dp(i + 2);
		return dp[i] = cuenta;
 	}
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			String s = sc.next();
			if(s.equals("0"))
				return;
			w = s.toCharArray();
			dp = new Long[w.length + 1];
			System.out.println(dp(0));
		}
	}

}

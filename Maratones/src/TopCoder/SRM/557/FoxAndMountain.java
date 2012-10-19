import java.util.ArrayList;

public class FoxAndMountain
{	
	static final int MOD = 1000000009;
	Character[] c;
	int nActual;
	Long[][][] dp;
	static String h;
	int acceptedMatch;
	
	// KMP fall
	static int caida(int actual, char nuevo)
	{
		String t = h.substring(0, actual) + nuevo;
		while(!h.startsWith(t))
			t = t.substring(1);
		return t.length();
	}

	long dp(int n, int length, int count)
	{
		if(count < 0)
			return 0;
		if(dp[n][length][count] != null)
			return dp[n][length][count];
		if(length == h.length() && n > acceptedMatch)
			return dp[n][length][count] = 0L;
		if(n == nActual)
			return dp[n][length][count] = count == 0 ? 1L : 0L;
		if(c[n] == null)
		{
			long a = dp(n + 1, caida(length, 'U'), count + 1);
			long b = dp(n + 1, caida(length, 'D'), count - 1);
			return dp[n][length][count] = (a + b) % MOD;
		}
		else
		{
			if(c[n].charValue() == 'U')
				return dp[n][length][count] = dp(n + 1, caida(length, 'U'), count + 1);
			else
				return dp[n][length][count] = dp(n + 1, caida(length, 'D'), count - 1);
		}
	}
	
	
	public int count(int n, String history)
	{
		nActual = n;
		char[] charH = history.toCharArray();
		long cuenta = 0;
		h = history;
		for(int i = 0; i <= n; i++)
		{
			ArrayList <Character> cA = new ArrayList <Character> ();
			for(int j = 0; j < i; j++)
				cA.add(null);
			for(char c1 : charH)
				cA.add(c1);
			if(cA.size() > n)
				break;
			acceptedMatch = cA.size();
			while(cA.size() < n)
				cA.add(null);
			c = cA.toArray(new Character[0]);
			dp = new Long[n + 5][n + 5][100];
			cuenta += dp(0, 0, 0);
			cuenta %= MOD;
		}
		return (int) cuenta;
	}
}
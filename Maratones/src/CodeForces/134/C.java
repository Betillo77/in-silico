import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class C 
{
	static int k;
	static int[] cols;
	static final int[][] dp = new int[500001][26];
	static final int[][] whereDP = new int[500001][26];
	
	static void fillDP()
	{
		final int[] c = cols;
		for(int i = 0; i < k; i++)
			dp[c.length][i] = 0;
		for(int i = c.length - 1; i >= 0; i--)
			for(int j = 0; j < k; j++)
			{
				int best = 1000000;
				int where = 0;
				if(j != c[i])
				{
					best = dp[i + 1][c[i]];
					where = c[i];
				}
				for(int m = 0; m < k; m++)
				{
					if(j != m)
					{
						int ne = (m == c[i] ? 0 : 1) + dp[i + 1][m];
						if(ne < best)
						{
							best = ne;
							where = m;
						}
					}
					if(i != 0 && m != c[i - 1] && m != c[i] && i != c.length - 1 && m != c[i + 1])
						break;
				}
				dp[i][j] = best;
				whereDP[i][j] = where;
			}
		int best = 1000000;
		int bestI = 0;
		for(int i = 0; i < k; i++)
		{
			if(dp[0][i] < best)
			{
				best = dp[0][i];
				bestI = i;
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < c.length; i++)
		{
			bestI = whereDP[i][bestI];
			sb.append((char) (bestI + 'A'));
		}
		System.out.println(best);
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		st.nextToken();
		k = Integer.parseInt(st.nextToken());
		char[] este = br.readLine().trim().toCharArray();
		cols = new int[este.length];
		for(int i = 0; i < cols.length; i++)
			cols[i] = este[i] - 'A';
		fillDP();
	}
}

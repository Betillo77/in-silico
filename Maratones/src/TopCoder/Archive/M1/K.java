import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class K
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
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
	}
	
	static int[][] armas;
	static int[] bosses;
	static int todos;
	static int numero;
	
	static Integer[][] dp;
	
	static int dp(int mascara, int n)
	{
		if(dp[mascara][n] != null)
			return dp[mascara][n];
		int mejorArma = 1;
		for(int i = 0; i < numero; i++)
		{
			if(((mascara >> i) & 1) == 1)
				mejorArma = Math.max(mejorArma, armas[i][n]);
		}
		int costo = bosses[n] / mejorArma;
		if(bosses[n] % mejorArma != 0)
			costo++;
		int mejor = Integer.MAX_VALUE;
		for(int i = 0; i < numero; i++)
		{
			if(i == n)
				continue;
			if(((mascara >> i) & 1) == 0)
				mejor = Math.min(mejor, costo + dp(mascara | (1 << n), i));
		}
		return dp[mascara][n] = mejor == Integer.MAX_VALUE ? costo : mejor;
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			String linea = sc.nextLine();
			if(linea == null)
				return;
			StringTokenizer st = new StringTokenizer(linea);
			numero = st.countTokens();
			dp = new Integer[1 << numero][numero];
			armas = new int[numero][numero];
			for(int i = 0; i < numero; i++)
			{
				int j = 0;
				for(char c : st.nextToken().toCharArray())
					armas[i][j++] = c - '0';
			}
			bosses = new int[numero];
			for(int i = 0; i < numero; i++)
				bosses[i] = sc.nextInt();
			todos = (1 << numero) - 1;
			int mejor = Integer.MAX_VALUE;
			for(int i = 0; i < numero; i++)
				mejor = Math.min(mejor, dp(0, i));
			System.out.println(mejor);
		}
	}
}

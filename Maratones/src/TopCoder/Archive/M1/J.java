import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class J 
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
	
	static Long[][] dpChoose = new Long[35][35];
	
	static long choose(int a, int b)
	{
		if(dpChoose[a][b] != null)
			return dpChoose[a][b];
		if(a == 0)
			return dpChoose[a][b] = 1L;
		if(b == 0)
			return dpChoose[a][b] = 0L;
		return dpChoose[a][b] = choose(a - 1, b - 1) + choose(a, b - 1);
	}
	
	static int n;
	static int[] vals;
	static Long[][][][][] dp;
	
	static long dp(int actual, int suma, boolean quien, boolean empezoA, boolean empezoB)
	{
		if(suma < 0)
			return 0;
		if(dp[actual][quien ? 1 : 0][empezoA ? 1 : 0][empezoB ? 1 : 0][suma] != null)
			return dp[actual][quien ? 1 : 0][empezoA ? 1 : 0][empezoB ? 1 : 0][suma];
		if(actual == n)
			return dp[actual][quien ? 1 : 0][empezoA ? 1 : 0][empezoB ? 1 : 0][suma] = suma == 0 && empezoA && empezoB ? 1L : 0L;	
		if(!quien)
		{
			int cuantos = 0;
			for(int i = actual; i < n && vals[i] == vals[actual]; i++)
				cuantos++;
			long cuenta = 0;
			for(int i = 0; i <= cuantos; i++)
			{
				cuenta += choose(i, cuantos) * dp(actual + cuantos, suma + i * vals[actual], quien, empezoA || i != 0, empezoB);
				if(i != 0)
					cuenta += choose(i, cuantos) * dp(actual + i, suma + i * vals[actual], !quien, empezoA || i != 0, empezoB);
			}
			return dp[actual][quien ? 1 : 0][empezoA ? 1 : 0][empezoB ? 1 : 0][suma] = cuenta;
		}
		else
		{
			long cuenta = 0;
			cuenta += dp(actual + 1, suma, quien, empezoA, empezoB);
			cuenta += dp(actual + 1, suma - vals[actual], quien, empezoA, true);
			return dp[actual][quien ? 1 : 0][empezoA ? 1 : 0][empezoB ? 1 : 0][suma] = cuenta;
		}
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
			int numero = st.countTokens();
			n = numero;
			vals = new int[numero];
			dp = new Long[numero + 1][2][2][2][numero * 1001];
			for(int i = 0; i < vals.length; i++)
				vals[i] = Integer.parseInt(st.nextToken());
			Arrays.sort(vals);
			System.out.println(dp(0, 0, false, false, false));
		}
	}
}

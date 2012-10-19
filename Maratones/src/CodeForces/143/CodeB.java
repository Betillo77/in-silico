import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class CodeB
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
	
	static class Entrada
	{
		int numero;
		Entrada siguiente;
		
		public Entrada(int i, Entrada e) 
		{
			numero = i;
			siguiente = e;
		}
	}
	
	static Entrada[][] dp = new Entrada[101][21000];
	static int n;
	static int l;
	static Entrada nula = new Entrada(-2, null);
	
	static Entrada dp(int nAct, int suma)
	{
		if(Math.abs(suma) > 10000)
			return nula;
		if(dp[nAct][suma + 10100] != null)
			return dp[nAct][suma + 10100];
		if(nAct == n)
			return suma == 0 ? new Entrada(-1, null) : nula;
		for(int i = 1; i <= l; i++)
		{
			Entrada posible = dp(nAct + 1, i - suma);
			if(posible != nula)
				return dp[nAct][suma + 10100] = new Entrada(i, posible);
		}
		return dp[nAct][suma + 10100] = nula;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		n = sc.nextInt();
		int d = sc.nextInt();
		l = sc.nextInt();
		Entrada posible = dp(0, d);
		if(posible == nula)
			System.out.println(-1);
		else
		{
			System.out.print(posible.numero);
			posible = posible.siguiente;
			while(posible.numero != -1)
			{
				System.out.print(" " + posible.numero);
				posible = posible.siguiente;
			}
			System.out.println();
		}
	}

}

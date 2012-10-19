import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class A 
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
	
	static int[][] dpSol = new int[501][];
	
	static int[] sol(int n)
	{
		if(dpSol[n] != null)
			return dpSol[n];
		int[] mejor = null;
		int mejorCuenta = Integer.MAX_VALUE;
		if(n == 0)
			return dpSol[n] = new int[4];
		if(n >= 25)
		{
			int[] otra = sol(n - 25);
			if(cuenta(otra) + 1 < mejorCuenta)
			{
				mejor = otra.clone();
				mejor[0]++;
				mejorCuenta = cuenta(otra) + 1;
			}
		}
		if(n >= 10)
		{
			int[] otra = sol(n - 10);
			if(cuenta(otra) + 1 < mejorCuenta)
			{
				mejor = otra.clone();
				mejor[1]++;
				mejorCuenta = cuenta(otra) + 1;
			}
		}
		if(n >= 5)
		{
			int[] otra = sol(n - 5);
			if(cuenta(otra) + 1 < mejorCuenta)
			{
				mejor = otra.clone();
				mejor[2]++;
				mejorCuenta = cuenta(otra) + 1;
			}
		}
		if(n >= 1)
		{
			int[] otra = sol(n - 1);
			if(cuenta(otra) + 1 < mejorCuenta)
			{
				mejor = otra.clone();
				mejor[3]++;
				mejorCuenta = cuenta(otra) + 1;
			}
		}
		return dpSol[n] = mejor;
	}
	
	private static int cuenta(int[] otra) 
	{
		int c = 0;
		for(int i : otra)
			c += i;
		return c;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int next = sc.nextInt();
			int[] res = sol(next);
			System.out.println(caso + " " + res[0] + " QUARTER(S), " + res[1] + " DIME(S), " + res[2] + " NICKEL(S), " + res[3] + " PENNY(S)");
		}
	}
}
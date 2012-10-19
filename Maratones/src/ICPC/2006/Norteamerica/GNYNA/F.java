import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class F 
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
	
	static boolean[][] ok = new boolean[2000][2000];
	static int[][] cuentaFila = new int[2000][2000];
	
	static void solve()
	{
		for(int i = 1; i < 1001; i++)
		{
			for(int j = 0; j <= i; j++)
			{
				if(ok[i][j])
				{
					int iP = i + i;
					int jP = j + j;
					while(iP <= 1000 && jP <= 1000)
					{
						ok[iP][jP] = false;
						iP += i;
						jP += j;
					}
				}
			}
			for(int j = i; j >= 0; j--)
			{
				if(ok[j][i])
				{
					int iP = j + j;
					int jP = i + i;
					while(iP <= 1000 && jP <= 1000)
					{
						ok[iP][jP] = false;
						iP += j;
						jP += i;
					}
				}
			}
		}
		for(int i = 0; i <= 1000; i++)
		{
			cuentaFila[i][0] = i != 0 && ok[i][0] ? 1 : 0;  
			for(int j = 1; j <= 1000; j++)
				cuentaFila[i][j] = cuentaFila[i][j - 1] + (ok[i][j] ? 1 : 0);
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int i = 0; i < 1001; i++)
			for(int j = 0; j < 1001; j++)
				ok[i][j] = true;
		solve();
		for(int caso = 1; caso <= casos; caso++)
		{
			int c = sc.nextInt();
			int cuenta = 0;
			for(int i = 0; i <= c; i++)
				cuenta += cuentaFila[i][c];
			System.out.println(caso + " " + c + " " + cuenta);
		}
	}
}
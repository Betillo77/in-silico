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
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int k = sc.nextInt();
		int[] puntos = new int[2 * n + 1];
		boolean[] usados = new boolean[2 * n + 1];
		for(int i = 0; i < puntos.length; i++)
			puntos[i] = sc.nextInt();
		while(k != 0)
		{
			for(int i = 0; i < 2 * n + 1; i++)
			{
				if(((i & 1) == 1) && !usados[i] && puntos[i] - 1 > puntos[i - 1] && puntos[i] - 1 > puntos[i + 1])
				{
					puntos[i]--;
					k--;
					usados[i] = true;
					break;
				}
			}
		}
		for(int i = 0; i < puntos.length; i++)
			System.out.print(puntos[i] + " ");
		System.out.println();
	}

	
}

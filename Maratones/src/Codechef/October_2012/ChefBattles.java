import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ChefBattles 
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
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		int[] readNextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
	}

	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int t = sc.nextInt();
		for(int caso = 1; caso <= t; caso++)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			int[] counts = new int[101];
			for(int i = 0; i < n; i++)
			{
				int c = sc.nextInt();
				counts[sc.nextInt()] += c;
			}
			for(int i = 0; i < m; i++)
			{
				int c = sc.nextInt();
				counts[sc.nextInt()] -= c;
			}
			int result = 0;
			for(int i = 0; i < 101; i++)
				if(counts[i] < 0)
					result += counts[i];
			System.out.println(-result);
		}
	}

}

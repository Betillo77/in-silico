import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class CodeC
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
		
		long nextLong()
		{
			return Long.parseLong(next());
		}
		
		int[] readNextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
		
		Integer[] readNextIntegerArray(int n)
		{
			Integer[] nA = new Integer[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		long k = sc.nextLong();
		Integer[] ns = sc.readNextIntegerArray(n);
		Arrays.sort(ns);
		int current = 0;
		out:
		while(current < n)
		{
			long count = 0;
			while(current + count < n && ns[current].intValue() == ns[(int) (current + count)])
				count++;
			k -= count * n;
			if(k <= 0)
			{
				k += count * n;
				for(int i = 0; i < n; i++)
				{
					k -= count;
					if(k <= 0)
					{
						System.out.println(ns[current] + " " + ns[i]);
						break out;
					}
				}
			}
			current += count;
		}
	}
}

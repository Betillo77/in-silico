import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class B
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		String nextLine()
		{
			try { return br.readLine(); } catch(Exception e) { throw new RuntimeException(e); }
		}
		
		String next()
		{
			while(st == null || !st.hasMoreTokens())
			{
				String linea = nextLine();
				if(linea == null)
					return null;
				st = new StringTokenizer(linea);
			}
			return st.nextToken();
		}
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		Integer nextInteger()
		{
			String next = next();
			if(next == null)
				return null;
			return Integer.parseInt(next);
		}
		
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
	}
	
	static long solve(ArrayList < ArrayList <Integer> > balls)
	{
		long[] current = new long[balls.get(0).size()];
		long suma = 0;
		for(int i = 0; i < balls.get(0).size(); i++)
		{
			suma += balls.get(0).get(i);
			current[i] = suma;
		}
		
		long best = maxOut(current);
		for(int i = 1; i < balls.size(); i++)
		{
			int currentSize = balls.get(0).size() - i;
			long[] next = new long[currentSize];
			suma = 0;
			for(int j = 0; j < currentSize; j++)
			{
				suma += balls.get(i).get(j);
				next[j] = suma + current[j];
			}
			best = Math.max(best, maxOut(next));
			current = next;
		}
		return Math.max(best, 0);
	}

	private static long maxOut(long[] current) 
	{
		long best = Long.MIN_VALUE;
		for(int i = current.length - 1; i >= 0; i--)
		{
			best = Math.max(best, current[i]);
			current[i] = best;
		}
		return best;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			ArrayList < ArrayList <Integer> > entrada = new ArrayList < ArrayList <Integer> > ();
			for(int i = 0; i < n; i++)
				entrada.add(new ArrayList <Integer> ());
			for(int i = 0; i < n; i++)
				for(int j = 0; j <= i; j++)
					entrada.get(j).add(sc.nextInt());
			System.out.println(solve(entrada));
		}
	}
}

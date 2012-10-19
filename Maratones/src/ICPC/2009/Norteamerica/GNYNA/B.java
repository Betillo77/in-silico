import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class B 
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
		int casos = sc.nextInt();
		HashSet <Long> valores = new HashSet <Long> (50000);
		for(int caso = 1; caso <= casos; caso++)
		{
			int numero = sc.nextInt();
			int n = sc.nextInt();
			int[] a = sc.readNextIntArray(n);
			valores.clear();
			long suma = 0;
			for(int i = 0; i < a.length; i++)
			{
				suma += a[i];
				valores.add(suma);
			}
			long mejor = Long.MAX_VALUE;
			int este = 0;
			for(int i = 0; i < a.length; i++)
			{
				este += a[i];
				if((suma % este) == 0)
				{
					boolean bien = true;
					for(long j = este; j <= suma; j += este)
						if(!valores.contains(j))
						{
							bien = false;
							break;
						}
					if(bien)
						mejor = Math.min(mejor, este);
				}
			}
			System.out.println(numero + " " + mejor);
		}
	}
}

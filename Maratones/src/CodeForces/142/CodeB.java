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
		
		long nextLong()
		{
			return Long.parseLong(next());
		}
		
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
	}
	
	static boolean tieneRaiz(long n, boolean unica)
	{
		if(n == 1)
			return false;
		long raiz = (long) (Math.sqrt(n));
		for(long i = raiz - 2; i < raiz + 3; i++)
		if(i * i == n)
			return esPrimo(i);
		return false;

	}
	
	private static boolean esPrimo(long n)
	{
		if(n < 0 || n >= primos.length)
			return false;
		return primos[(int) n];
	}

	static boolean[] primos = new boolean[1000001];
	public static void main(String[] args)
	{
		primos[0] = primos[1] = false;
		for(int i = 2; i <= 1000000; i++)
			primos[i] = true;
		for(int i = 2; i <= 1000000; i++)
		{
			if(primos[i])
			{
				long iC = i;
				iC *= i;
				if(iC > 1000000)
					break;
				for(int j = (int) iC; j <= 1000000; j += i)
					primos[j] = false;
			}
		}
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		for(int i = 0; i < n; i++)
			System.out.println(tieneRaiz(sc.nextLong(), true) ? "YES" : "NO");
	}

}

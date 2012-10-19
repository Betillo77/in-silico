import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class F
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
	
	static int vecesFactorial(long primo, int n)
	{
		int cuenta = 0;
		long primoTot = primo;
		while(n / primoTot != 0)
		{
			cuenta += n / primoTot;
			primoTot *= primo;
		}
		return cuenta;
	}
	
	static int[][] factorizar(int n)
	{
		ArrayList <int[]> factores = new ArrayList <int[]> ();
		int raiz = (int) Math.min(n - 1, Math.ceil(Math.sqrt(n)));
		for(int i = 2; i <= raiz; i++)
		{
			if(n % i == 0)
			{
				int cuenta = 0;
				while(n % i == 0)
				{
					cuenta++;
					n /= i;
				}
				raiz = (int) Math.min(n - 1, Math.ceil(Math.sqrt(n)));
				factores.add(new int[]{i, cuenta});
			}
		}
		if(n != 1)
			factores.add(new int[]{n, 1});
		return factores.toArray(new int[0][]);
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int a = sc.nextInt();
			int b = sc.nextInt();
			int[][] factores = factorizar(b);
			int ans = 1;
			for(int[] f : factores)
				ans *= Math.pow(f[0], Math.min(f[1], vecesFactorial(f[0], a)));
			System.out.println(ans);
			
		}
	}
}

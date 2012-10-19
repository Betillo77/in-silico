import java.io.BufferedReader;
import java.io.InputStreamReader;
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
	
	static long inicio = System.currentTimeMillis();
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int m = sc.nextInt();
		int[][] derecha = new int[n][m];
		int[][] izquierda = new int[n][m];
		for(int i = 0; i < n; i++)
		{
			boolean[] actual = new boolean[m];
			char[] s = sc.next().toCharArray();
			int primero = -1;
			int ultimo = -1;
			for(int j = 0; j < m; j++)
			{
				actual[j] = s[j] == '1';
				if(actual[j] && primero == -1)
					primero = j;
				if(actual[j])
					ultimo = j;
			}
			if(primero == -1)
			{
				System.out.println("-1");
				return;
			}
			derecha[i][m - 1] = actual[m - 1] ? 0 : primero + 1;
			for(int j = m - 2; j >= 0; j--)
			{
				if(actual[j])
					derecha[i][j] = 0;
				else
					derecha[i][j] = derecha[i][j + 1] + 1;
			}
			izquierda[i][0] = actual[0] ? 0 : m - ultimo;
			for(int j = 1; j < m; j++)
			{
				if(actual[j])
					izquierda[i][j] = 0;
				else
					izquierda[i][j] = izquierda[i][j - 1] + 1;
			}
		}
		int mejorCuenta = Integer.MAX_VALUE;
		for(int i = 0; i < m; i++)
		{
			int cuenta = 0;
			for(int j = 0; j < n; j++)
				cuenta += Math.min(izquierda[j][i], derecha[j][i]);
			mejorCuenta = Math.min(mejorCuenta, cuenta);
		}
		System.out.println(mejorCuenta);
	}
}
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class CodeA
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
	
	static boolean cumple(char[] a, char[] b)
	{
		for(int i = 0; i < a.length; i++)
			if(a[i] == b[i])
				return false;
		boolean mayor = a[0] < b[0];
		boolean bien = true;
		for(int i = 0; i < a.length; i++)
		{
			if(mayor != (a[i] < b[i]))
				bien = false;
		}
		return bien;
	}
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int[][] matriz = new int[n][];
		for(int i = 0; i < n; i++)
			matriz[i] = sc.readNextIntArray(n);
		int cuenta = 0;
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
			{
				int sumaFila = 0;
				for(int k = 0; k < n; k++)
					sumaFila += matriz[i][k];
				int sumaColumna = 0;
				for(int k = 0; k < n; k++)
					sumaColumna += matriz[k][j];
				if(sumaColumna > sumaFila)
					cuenta++;
			}
		System.out.println(cuenta);
	}
}

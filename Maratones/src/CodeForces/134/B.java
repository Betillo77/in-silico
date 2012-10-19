import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class B 
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
		int m = sc.nextInt();
		int[] numeros = new int[m];
		for(int i = 0; i < m; i++)
			numeros[i] = sc.nextInt();
		int[] nT = numeros.clone();
		int cuentaMaximo = 0;
		for(int i = 0; i < n; i++)
		{
			int maximo = 0;
			int indiceMaximo = 0;
			for(int j = 0; j < m; j++)
			{
				if(numeros[j] > maximo)
				{
					maximo = numeros[j];
					indiceMaximo = j;
				}
			}
			if(maximo == 0)
				break;
			cuentaMaximo += maximo;
			numeros[indiceMaximo]--;
		}
		numeros = nT;
		int cuentaMinimo = 0;
		for(int i = 0; i < n; i++)
		{
			int minimo = Integer.MAX_VALUE;
			int indiceMinimo = 0;
			for(int j = 0; j < m; j++)
			{
				if(numeros[j] < minimo && numeros[j] != 0)
				{
					minimo = numeros[j];
					indiceMinimo = j;
				}
			}
			if(minimo == Integer.MAX_VALUE)
				break;
			cuentaMinimo += minimo;
			numeros[indiceMinimo]--;
		}
		System.out.println(cuentaMaximo + " " + cuentaMinimo);
	}

}

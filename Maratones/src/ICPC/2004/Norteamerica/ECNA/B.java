import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class B 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		String nextLine()
		{
			try
			{
				String s = br.readLine();
				return s;
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
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

		public int nextInt() 
		{
			return Integer.parseInt(next());
		}
	}
	
	static boolean[] esPrimo = new boolean[1000000];

	static boolean backtrack(int[] valores, int[] posibles, boolean[] usados, int primeroNoUsado, int actual)
	{
		if(actual == valores.length)
			return true;
		for(int i = primeroNoUsado; i < usados.length; i++)
		{
			if(!usados[i])
			{
				if(actual > 0 && esPrimo[posibles[i] + valores[actual - 1]])
					continue;
				valores[actual] = posibles[i];
				int suma = valores[actual];
				boolean paila = false;
				for(int j = actual - 1; j > Math.max(-1, actual - d); j--)
				{
					suma += valores[j];
					if(esPrimo[suma])
						paila = true;
				}
				if(!paila)
				{
					usados[i] = true;
					if(backtrack(valores, posibles, usados, i == primeroNoUsado ? primeroNoUsado + 1 : primeroNoUsado, actual + 1))
						return true;
					usados[i] = false;
				}
			}
		}
		return false;
	}
	
	static int d;
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		for(int i = 0; i < esPrimo.length; i++)
			esPrimo[i] = true;
		esPrimo[0] = esPrimo[1] = false;
		for(int i = 2; i < esPrimo.length; i++)
			if(esPrimo[i])
				if(((long) i * i) + i < Integer.MAX_VALUE)
					for(int j = i * i; j < esPrimo.length; j += i)
						esPrimo[j] = false;
		while(true)
		{
			int inicio = sc.nextInt();
			int fin = sc.nextInt();
			d = sc.nextInt();
			if(inicio == 0 && fin == 0 && d == 0)
				return;
			int[] numeros = new int[fin - inicio + 1];
			int[] valores = new int[fin - inicio + 1];
			for(int i = inicio; i <= fin; i++)
				valores[i - inicio] = i;
			boolean[] usados = new boolean[fin - inicio + 1];
			if(backtrack(numeros, valores, usados, 0, 0))
				System.out.println(Arrays.toString(numeros).replace("[", "").replace("]", "").replace(", ", ","));
			else
				System.out.println("No anti-prime sequence exists.");
		}
	}
}

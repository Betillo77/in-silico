import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;


public class CodeD 
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
		int n = sc.nextInt();
		int[] numeros = sc.readNextIntArray(n);
		Integer[] nS = new Integer[n];
		for(int i = 0; i < n; i++)
			nS[i] = numeros[i];
		Arrays.sort(nS, Collections.reverseOrder());
		for(int i = 0; i < n; i++)
			numeros[i] = nS[i];
		long[] sumatoria = new long[n];
		sumatoria[0] = numeros[0];
		for(int i = 1; i < n; i++)
			sumatoria[i] = sumatoria[i - 1] + numeros[i];
		long casoUno = 0;
		for(int i = 0; i < n; i++)
			casoUno += ((long) (i)) * numeros[i]; 
		int q = sc.nextInt();
		int[] qs = sc.readNextIntArray(q);
		boolean empezo = false;
		for(int i = 0; i < q; i++)
		{
			int qA = qs[i];
			long respuesta = 0;
			if(qA == 1) 
				respuesta = casoUno;
			else
			{
				long res = 0;
				long cuentaActual = 1;
				for(int aLa = 1; cuentaActual < n; aLa++)
				{
					long cuentaEste = (long) (cuentaActual + Math.pow(qA, aLa));
					int limite = (int) Math.min(cuentaEste - 1, n - 1);
					long s = sumatoria[limite] - sumatoria[(int) (cuentaActual - 1)];
					res += aLa * s;
					cuentaActual = cuentaEste;
				}
				respuesta = res;
			}
			if(!empezo)
			{
				System.out.print(respuesta);
				empezo = true;
			}
			else
				System.out.print(" " + respuesta);
		}
		System.out.println();
	}

}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
		
		
		private double nextDouble() 
		{
			return Double.parseDouble(next());
		}

		int[] nextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
		
		Integer[] nextIntegerArray(int n)
		{
			Integer[] nA = new Integer[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
		
		long[] nextLongArray(int n)
		{
			long[] nA = new long[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextLong();
			return nA;
		}
		
		Long[] nextLongOArray(int n)
		{
			Long[] nA = new Long[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextLong();
			return nA;
		}
		
		double[] nextDoubleArray(int n)
		{
			double[] nA = new double[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextDouble();
			return nA;
		}

		Double[] nextDoubleOArray(int n)
		{
			Double[] nA = new Double[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextDouble();
			return nA;
		}
		
		String[] nextStringArray(int n)
		{
			String[] nA = new String[n];
			for(int i = 0; i < n; i++)
				nA[i] = next();
			return nA;
		}
	}
	
	static HashMap <Long, Integer> darSubsets(int[] valores, int a, int b)
	{
		HashMap <Long, Integer> h = new HashMap <Long, Integer> ();
		int limite = 1 << (b - a);
		for(int i = 0; i < limite; i++)
		{
			int mascara = i;
			int j = 0;
			long cuenta = 0;
			while(mascara != 0)
			{
				if((mascara & 1) == 1)
					cuenta += valores[a + j];
				j++;
				mascara >>= 1;
			}
			h.put(cuenta, i);
		}
		return h;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int k = sc.nextInt();
		int[] fibo = new int[100];
		fibo[0] = 1;
		int limite = 0;
		for(int i = 1; true; i++)
		{
			long result = fibo[i - 1];
			int cuenta = 1;
			for(int j = i - 2; cuenta < k && j >= 0; j--, cuenta++)
				result += fibo[j];
			if(result > 1E9)
			{
				limite = i;
				break;
			}
			fibo[i] = (int) result;
		}
		HashMap <Long, Integer> a = darSubsets(fibo, 0, limite / 2);
		HashMap <Long, Integer> b = darSubsets(fibo, limite / 2, limite);
		for(Map.Entry<Long, Integer> e : a.entrySet())
		{
			long buscado = n - e.getKey();
			if(b.containsKey(buscado) || buscado == 0)
			{
				long mascaraA = e.getValue();
				long mascaraB = buscado == 0 ? 0 : b.get(buscado);
				mascaraA |= mascaraB << limite / 2;
				ArrayList <Integer> respuesta = new ArrayList <Integer> ();
				for(int i = 0; i < limite; i++)
				{
					if((mascaraA & 1) == 1)
						respuesta.add(fibo[i]);
					mascaraA >>= 1;
				}
				if(respuesta.size() == 1)
					respuesta.add(0);
				System.out.println(respuesta.size());
				boolean empezo = false;
				for(int i : respuesta)
					if(!empezo)
					{
						empezo = true;
						System.out.print(i);
					}
					else
						System.out.print(" " + i);
				System.out.println();
				return;
			}
		}
	}
}

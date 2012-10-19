import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
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
	
	public static class Sumas
	{
		long[] suma = new long[100002];
		int tam = 0;
		
		long query(int a, int b)
		{
		    if (a == 0)
		    {
		    	return suma[b];
		    }
		    else
		    {
		        return suma[b] - suma[a - 1];
		    }
		}

		void increase(int k, long inc)
		{
			if(k == 0)
				suma[k] = inc;
			else
				suma[k] = inc + suma[k - 1];
		}
		
		void agregar(long n)
		{
			tam++;
			increase(tam - 1, n);
		}
	}
	static Sumas sumas = new Sumas();
	static int k;
	

	private static int busquedaBinaria(int i, int j, long actual, int r)
	{
		if(r >= 100)
			return i;
		if(i == j)
			return i;
		if(i + 1 == j)
		{
			if(calcular(i, actual) <= k)
				return i;
			else
				return j;
		}
		int mid = (i + j) >>> 1;
		long llave = calcular(mid, actual);
		if(llave > k)
		{
			if(mid == i)
				return i;
			return busquedaBinaria(mid, j, actual, r + 1);
		}
		else
		{
			if(mid == 0)
				return mid;
			if(mid == j)
				return i;
			return busquedaBinaria(i, mid, actual, r + 1);
		}
	}
	
	private static long calcular(int mid, long actual) 
	{
		if(mid >= sumas.tam || mid < 0)
			return 0;
		int numero = sumas.tam - mid;
		long valor = sumas.query(mid, sumas.tam - 1);
		return -(valor - numero * actual);
	}

	static int pos;
	static int mejor;
	static long numMejor;
	
	static void agregar(long[] nums)
	{
		if(pos >= nums.length)
			return;
		long actual = nums[pos];
		while(pos < nums.length && nums[pos] == actual)
		{
			sumas.agregar(actual);
			pos++;
		}
		int indice = busquedaBinaria(0, sumas.tam - 1, actual, 0);
		int valor = sumas.tam - indice;
		if(valor > mejor)
		{
			mejor = valor;
			numMejor = actual;
		}
	}
	
	static int solve(long[] nums)
	{
		Long[] nums2 = new Long[nums.length];
		for(int i = 0; i < nums2.length; i++)
			nums2[i] = nums[i];
		Arrays.sort(nums2);
		for(int i = 0; i < nums.length; i++)
			nums[i] = nums2[i];
		pos = 0;
		mejor = 0;
		numMejor = 0;
		while(pos < nums.length)
			agregar(nums);
		return mejor;
	}
	
	static long inicio = System.currentTimeMillis();
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		k = sc.nextInt();
		long[] nums = new long[n];
		for(int i = 0; i < n; i++)
			nums[i] = sc.nextInt();
		solve(nums);
		System.out.println(mejor + " " + numMejor);
	}
}
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;



public class Trees
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		String nextLine()
		{
			try { return br.readLine(); } catch(Exception e) { throw new RuntimeException(e); }
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
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		Integer nextInteger()
		{
			String next = next();
			if(next == null)
				return null;
			return Integer.parseInt(next);
		}
		
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
	}
	
	static Long[] dpCatalan = new Long[20];
	static long catalan(int n)
	{
		if(dpCatalan[n] != null)
			return dpCatalan[n];
		if(n == 0)
			return dpCatalan[n] = 1L;
		long res = 0;
		for(int i = 0; i <= n - 1; i++)
			res += catalan(i) * catalan(n - 1 - i);
		return dpCatalan[n] = res;
	}
	
	static long[] sums = darSumas();
	
	static long[] darSumas()
	{
		long[] res = new long[20];
		res[0] = 0;
		res[1] = 1;
		for(int i = 2; i < 20; i++)
			res[i] = res[i - 1] + catalan(i - 1);
		return res;
	}
	
	static int giveCount(long val)
	{
		for(int i = 1; true; i++)
			if(val >= sums[i - 1] && val < sums[i])
				return i - 1;
	}
	
	static class Nodo
	{
		Nodo a;
		Nodo b;
		
		static Nodo darNodo(long c, int nodes)
		{
			if(nodes == 0)
				return null;
			if(nodes == 1)
				return new Nodo();
			nodes--;
			long current = 0;
			int leftNodes = nodes;
			for(int i = 0; i <= nodes; i++)
			{
				long next = current + catalan(i) * catalan(nodes - i);
				if(next > c)
				{
					c -= current;
					leftNodes = i;
					break;
				}
				current = next;
			}
			long rightCount = catalan(nodes - leftNodes);
			Nodo nuevo = new Nodo();
			nuevo.a = darNodo(c / rightCount, leftNodes);
			nuevo.b = darNodo(c % rightCount, nodes - leftNodes);
			return nuevo;
		}
		
		static String imprimir(Nodo n, boolean empezo)
		{
			if(n == null)
				return "";
			if(n.a == null && n.b == null)
				return empezo ? "(X)" : "X";
			return (empezo ? "(" : "") + imprimir(n.a, true) + "X" + imprimir(n.b, true) + (empezo ? ")" : "");
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			System.out.println(Nodo.imprimir(Nodo.darNodo(n - sums[giveCount(n)], giveCount(n)), false));
		}
	}
}
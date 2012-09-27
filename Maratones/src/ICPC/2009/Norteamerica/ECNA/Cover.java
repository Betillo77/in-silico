import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class Cover
{
	static class Scanner
	{
		BufferedReader br;
		StringTokenizer st;
		
		public Scanner()
		{
	    	System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
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
				return !st.hasMoreTokens();
			}
			catch(Exception e) { throw new RuntimeException(); }
		}
	}
	
	static class Entrada implements Comparable <Entrada>
	{
		int m;
		int l;
		double p;
		
		Entrada(int m, int l, double p)
		{
			this.m = m;
			this.l = l;
			this.p = p;
		}
		
		Entrada(Entrada anterior, boolean eL)
		{
			m = anterior.m - 1;
			l = eL ? anterior.l - 1 : anterior.l;
			double probQuitada = eL ? anterior.probabilidadL() : anterior.probabilidadNormal();
			if(l == 0)
				p = anterior.p == 1.0 ? 1.0 : 0;
			else
				p = (l * anterior.probabilidadL()) / (1 - probQuitada);
		}
		
		double probabilidadL()
		{
			if(l == 0)
				return 0;
			return p / l;
		}
		
		double probabilidadNormal()
		{
			if(m != l)
				return (1 - p) / (m - l);
			else
				return 0;
		}

		@Override
		public int compareTo(Entrada o) 
		{
			if(m != o.m)
				return m - o.m;
			if(l != o.l)
				return l - o.l;
			return Double.compare(p, o.p);
		}
	}
	
	static TreeMap <Entrada[], Double> dp = new TreeMap <Entrada[], Double> (new Comparator<Entrada[]>() 
	{

				@Override
				public int compare(Entrada[] o1, Entrada[] o2) {
					if(o1.length != o2.length)
						return o1.length - o2.length;
					for(int i = 0; i < o1.length; i++)
					{
						int cmp = o1[i].compareTo(o2[i]);
						if(cmp != 0)
							return cmp;
					}
					return 0;
				}
	});
	
	public static double backtrack(Entrada[] entradas)
	{
		if(entradas.length == 0)
			return 1;
		if(dp.containsKey(entradas))
			return dp.get(entradas);
		int tam = entradas.length;
		int limite = 1 << tam;
		double mejor = 0;
		outer:
		for(int i = 0; i < limite; i++)
		{
			Entrada[] base = new Entrada[tam];
			for(int j = 0; j < tam; j++)
			{
				if(entradas[j].m == 1)
					base[j] = null;
				else if(((i >> j) & 1) == 1)
				{
					if(entradas[j].l == 0)
						continue outer;
					base[j] = new Entrada(entradas[j], true);
				}
				else
				{
					if(entradas[j].l == entradas[j].m)
						continue outer;
					base[j] = new Entrada(entradas[j], false);
				}
			}
			double peor = 0;
			outer2:
			for(int j = 1; j < limite; j++)
			{
				ArrayList <Entrada> nuevo = new ArrayList <Entrada> ();
				double probabilidad = 1;
				for(int k = 0; k < tam; k++)
				{
					if(base[k] == null && (((j >> k) & 1) == 0))
						continue outer2;
					if(base[k] != null && (((j >> k) & 1) != 1))
						nuevo.add(base[k]);
					double probIndividual = (((i >> k) & 1) == 1) ? entradas[k].probabilidadL() : entradas[k].probabilidadNormal();
					if((((j >> k) & 1) == 0))
						probIndividual = 1 - probIndividual;
					probabilidad *= probIndividual;
				}
				peor += probabilidad * backtrack(nuevo.toArray(new Entrada[0]));
			}
			mejor = Math.max(mejor, peor);
		}
		dp.put(entradas, mejor);
		return mejor;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			Entrada[] iniciales = new Entrada[n];
			for(int i = 0; i < n; i++)
				iniciales[i] = new Entrada(sc.nextInt(), sc.nextInt(), sc.nextDouble());
			dp.clear();
			double res = backtrack(iniciales);
			System.out.println(new BigDecimal(res).divide(BigDecimal.ONE, 3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros());
		}
	}
}
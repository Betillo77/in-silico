import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class CodeD 
{
	static class Rango implements Comparable <Rango>
	{
		int a;
		int b;
		
		public Rango(int anteriorInicio, int anteriorFin)
		{
			a = anteriorInicio;
			b = anteriorFin;
		}

		@Override
		public int compareTo(Rango o) 
		{
			if((a <= o.a && o.a <= b) || (o.a <= a && b <= o.b))
				return 0;
			return a - o.a;
		}
	}
	
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
		
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
	}
	
	static TreeMap <Rango, Rango> [] rangos;

	
	static class Arista implements Comparable <Arista>
	{
		Nodo otro;
		int costo;
		
		public Arista(Nodo nodo, int c)
		{
			otro = nodo;
			costo = c;
		}

		@Override
		public int compareTo(Arista o) 
		{
			return costo - o.costo;
		}
	}
	
	static class Nodo
	{
		int mejor = Integer.MAX_VALUE;
		ArrayList <Arista> aristas = new ArrayList <Arista> ();
		int n;
		
		Nodo(int nA)
		{
			n = nA;
		}
	}
	
	static Nodo[] nodos;
	
	
	static int bestDistance()
	{
		PriorityQueue <Arista> pq = new PriorityQueue <Arista> ();
		final Nodo[] ciudades = nodos;
		final TreeMap <Rango, Rango> [] ranges = rangos;
		Rango temp = new Rango(0, 0);
		ciudades[0].mejor = 0;
		if(ranges[0] != null && ranges[0].containsKey(temp))
			ciudades[0].mejor = ranges[0].get(temp).b + 1;
		pq.add(new Arista(ciudades[0], ciudades[0].mejor));
		while(!pq.isEmpty())
		{
			Arista ar = pq.poll();
			Nodo actual = ar.otro;
			if(actual.mejor != ar.costo)
				continue;
			if(actual.n == ciudades.length - 1)
				return actual.mejor;
			for(Arista a : actual.aristas)
			{
				int costo = actual.mejor + a.costo;
				temp.a = temp.b = costo;
				if(a.otro.n != ciudades.length - 1 && ranges[a.otro.n] != null && ranges[a.otro.n].containsKey(temp))
				{
					Rango r = ranges[a.otro.n].get(temp);
					costo = r.b + 1;
				}
				if(costo < a.otro.mejor)
				{
					a.otro.mejor = costo;
					pq.add(new Arista(a.otro, costo));
				}
			}
		}
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int m = sc.nextInt();
		nodos = new Nodo[n];
		for(int i = 0; i < n; i++)
			nodos[i] = new Nodo(i);
		for(int i = 0; i < m; i++)
		{
			int a = sc.nextInt();
			int b = sc.nextInt();
			int c = sc.nextInt();
			nodos[a - 1].aristas.add(new Arista(nodos[b - 1], c));
			nodos[b - 1].aristas.add(new Arista(nodos[a - 1], c));
		}
		rangos = new TreeMap[n];
		for(int i = 0; i < n; i++)
		{
			int k = sc.nextInt();
			if(k == 0)
				continue;
			rangos[i] = new TreeMap <Rango, Rango> ();
			int anteriorInicio = -2;
			int anteriorFin = -2;
			for(int j = 0; j < k; j++)
			{
				int kn = sc.nextInt();
				if(kn == anteriorFin)
					continue;
				if(kn == anteriorFin + 1)
					anteriorFin++;
				else
				{
					if(anteriorInicio != -2)
					{
						Rango nuevo = new Rango(anteriorInicio, anteriorFin);
						rangos[i].put(nuevo, nuevo);
					}
					anteriorInicio = kn;
					anteriorFin = kn;
				}
			}
			Rango nuevo = new Rango(anteriorInicio, anteriorFin);
			rangos[i].put(nuevo, nuevo);
		}
		System.out.println(bestDistance());
	}
}

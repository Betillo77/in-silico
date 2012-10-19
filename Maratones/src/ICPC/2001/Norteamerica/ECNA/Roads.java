import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class Roads 
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
		
		BigDecimal nextBigDecimal()
		{
			return new BigDecimal(next());
		}
	}

	static class Aviso implements Comparable <Aviso>
	{
		long costo;
		String c;
		
		public Aviso(long l, String nombre)
		{
			costo = l;
			c = nombre;
		}

		@Override
		public int compareTo(Aviso o) 
		{
			if(costo == o.costo)
				return c.compareTo(o.c);
			return Long.valueOf(costo).compareTo(o.costo);
		}
	}
	
	static class Arista
	{
		Nodo a;
		Nodo b;
		Nodo ini;
		long costo;
		long costoAviso = -1;
		ArrayList <Aviso> avisos = new ArrayList <Aviso> ();
		
		Arista(Nodo i1, Nodo i2, BigDecimal c)
		{
			a = i1;
			b = i2;
			costo = c.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).longValueExact();
		}

		public void imprimir() 
		{
			Collections.sort(avisos);
			for(Aviso a : avisos)
			{
				long mod = a.costo % 100;
				long cN = a.costo / 100;
				if(mod >= 50)
					cN++;
				a.costo = cN;
			}
			Collections.sort(avisos);
			for(Aviso a : avisos)
			{
				String res = a.c;
				while(res.length() != 20)
					res += " ";
				System.out.println(res + a.costo);
			}
		}
	}
	
	static class Nodo
	{
		ArrayList <Arista> aristas = new ArrayList <Arista> ();
		String nombre;
		Entrada mejorEntrada;
	}
	
	static class Entrada implements Comparable <Entrada>
	{
		long costo;
		Arista ultima;
		Nodo n;
		
		Entrada(long costo, Arista ultima, Nodo n) 
		{
			this.costo = costo;
			this.ultima = ultima;
			this.n = n;
		}

		@Override
		public int compareTo(Entrada o)
		{
			return Long.valueOf(costo).compareTo(o.costo);
		}
	}
	
	static void dijkstra(Nodo ciudadInicial)
	{
		PriorityQueue <Entrada> pq = new PriorityQueue <Entrada> ();
		ciudadInicial.mejorEntrada = new Entrada(0, null, ciudadInicial);
		pq.add(ciudadInicial.mejorEntrada);
		while(!pq.isEmpty())
		{
			Entrada actual = pq.poll();
			if(actual.ultima != null && actual.ultima.costoAviso != -1 && actual.ultima.ini == actual.n)
			{
				long co = 0;
				if(actual.ultima.ini == actual.n)
					co = actual.costo - actual.ultima.costoAviso;
				else
					co = actual.costo - (actual.ultima.costo - actual.ultima.costoAviso);
				actual.ultima.avisos.add(new Aviso(co, ciudadInicial.nombre));
			}
			for(Arista a : actual.n.aristas)
			{
				Nodo otra = a.a == actual.n ? a.b : a.a;
				long costo = actual.costo + a.costo;
				if(otra.mejorEntrada == null || otra.mejorEntrada.costo > costo)
				{
					if(otra.mejorEntrada != null)
						pq.remove(otra.mejorEntrada);
					otra.mejorEntrada = new Entrada(costo, a, otra);
					pq.add(otra.mejorEntrada);
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			int k = sc.nextInt();
			Nodo[] nodos = new Nodo[n];
			for(int i = 0; i < n; i++)
				nodos[i] = new Nodo();
			Arista[][] aristas = new Arista[n][n];
			for(int i = 0; i < m; i++)
			{
				int i1 = sc.nextInt();
				int i2 = sc.nextInt();
				BigDecimal b = sc.nextBigDecimal();
				Arista nueva1 = new Arista(nodos[i1], nodos[i2], b);
				Arista nueva2 = new Arista(nodos[i2], nodos[i1], b);
				nodos[i1].aristas.add(nueva1);
				nodos[i2].aristas.add(nueva2);
				aristas[i2][i1] = nueva1;
				aristas[i1][i2] = nueva2;
			}
			for(int i = 0; i < k; i++)
			{
				int c = sc.nextInt();
				nodos[c].nombre = sc.next();
			}
			int s = sc.nextInt();
			Arista[] enOrden = new Arista[s];
			for(int i = 0; i < s; i++)
			{
				int i1 = sc.nextInt();
				int i2 = sc.nextInt();
				long dist = sc.nextBigDecimal().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).longValueExact();
				aristas[i1][i2].costoAviso = dist;
				aristas[i1][i2].ini = nodos[i1];
				enOrden[i] = aristas[i1][i2];
			}
			for(int i = 0; i < n; i++)
				if(nodos[i].nombre != null)
				{
					for(int j = 0; j < n; j++)
						nodos[j].mejorEntrada = null;
					dijkstra(nodos[i]);
				}
			if(caso != 1)
				System.out.println();
			for(int i = 0; i < s; i++)
			{
				if(i != 0)
					System.out.println();
				enOrden[i].imprimir();
			}
		}
	}
	
}

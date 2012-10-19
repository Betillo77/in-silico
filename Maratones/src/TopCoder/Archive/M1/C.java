import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


public class C 
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
	
	static class DisjointSet
	{
		int[] p, rank;
		int size;

		public DisjointSet(int sizeI)
		{
			rank = new int[sizeI];
			p = new int[sizeI];
			size = sizeI;
			reset();
		}

		public void reset()
		{
			for(int i = 0; i < size; i++)
				make_set(i);
		}

		private void make_set(int x)
		{
			p[x] = x;
			rank[x] = 0;
		}

		public void merge(int x, int y)
		{
			link(find_set(x), find_set(y));
		}

		public void link(int x, int y)
		{
			if(rank[x] > rank[y])
				p[y] = x;
			else
			{
				p[x] = y;
				if(rank[x] == rank[y])
					rank[y] += 1;
			}
		}

		public int find_set(int x)
		{
			if(x != p[x])
				p[x] = find_set(p[x]);
			return p[x];
		}
	}
	
	static class Arista implements Comparable <Arista>
	{
		int i;
		int j;
		int costo;
		
		public Arista(int i, int j, int costo)
		{
			this.i = i;
			this.j = j;
			this.costo = costo;
		}

		@Override
		public int compareTo(Arista o) 
		{
			return costo - o.costo;
		}
	}
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			String first = sc.next();
			if(first == null)
				return;
			int n = first.length();
			int[][] matriz = new int[n][];
			matriz[0] = leer(first);
			for(int i = 1; i < n; i++)
				matriz[i] = leer(sc.next());
			DisjointSet s = new DisjointSet(n);
			ArrayList <Arista> aristas = new ArrayList <Arista> ();
			for(int i = 0; i < n; i++)
				for(int j = 0; j < n; j++)
					if(matriz[i][j] != -1)
						aristas.add(new Arista(i, j, matriz[i][j]));
			Collections.sort(aristas);
			int cuantas = 0;
			int total = 0;
			int costo = 0;
			for(Arista a : aristas)
			{
				total += a.costo;
				if(s.find_set(a.i) != s.find_set(a.j))
				{
					cuantas++;
					costo += a.costo;
					s.merge(a.i, a.j);
				}
			}
			if(cuantas != n - 1)
				System.out.println(-1);
			else
				System.out.println(total - costo);
		}
	}

	private static int[] leer(String next) 
	{
		int[] v = new int[next.length()];
		int i = 0;
		for(char c : next.toCharArray())
			if(c == '0')
				v[i++] = -1;
			else if(c >= 'A' && c <= 'Z')
				v[i++] = c - 'A' + 27;
			else
				v[i++] = c - 'a' + 1;
		return v;
	}

}

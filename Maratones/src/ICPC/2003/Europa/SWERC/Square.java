import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Square 
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
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int empezo = 0;
		while(true)
		{
			Integer p = sc.nextInteger();
			if(p == null)
				return;
			int r = sc.nextInt();
			DisjointSet d = new DisjointSet(p + 1);
			int cuenta = 0;
			for(int i = 0; i < r; i++)
			{
				int a = sc.nextInt();
				int b = sc.nextInt();
				if(d.find_set(a) != d.find_set(b))
					d.merge(a, b);
				else
					cuenta++;
			}
			if(empezo++ != 0)
				System.out.println();
			System.out.println(cuenta);
		}
	}
}

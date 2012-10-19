import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class Men 
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
	
	static class Estado
	{
		int i;
		int j;
		int mod;
		int valor = 0;
		
		Estado(int i, int j, int mod) 
		{
			this.i = i;
			this.j = j;
			this.mod = mod;
		}
		
		boolean esValido()
		{
			if(i < 0 || i >= n || j < 0 || j >= n)
				return false;
			if(mods[i][j] == '0')
				return inicial[i][j] == '.';
			int orig = (mods[i][j] - '0');
			int modulo = orig * 2;
			return (mod % modulo < orig) ? inicial[i][j] == '.' : inicial[i][j] == '*';
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + i;
			result = prime * result + j;
			result = prime * result + mod;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Estado other = (Estado) obj;
			if (i != other.i)
				return false;
			if (j != other.j)
				return false;
			if (mod != other.mod)
				return false;
			return true;
		}
		
		
		
	}

	static char[][] inicial;
	static char[][] mods;
	static int n;
	static final int[][] diffs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {0, 0}};
	
	static int BFS()
	{
		Estado inicial = new Estado(0, 0, 0);
		if(!inicial.esValido())
			return -1;
		HashSet <Estado> set = new HashSet <Estado> ();
		LinkedList <Estado> cola = new LinkedList <Estado> ();
		cola.add(inicial);
		set.add(inicial);
		while(!cola.isEmpty())
		{
			Estado actual = cola.poll();
			if(actual.i == n - 1 && actual.j == n - 1)
				return actual.valor;
			for(int[] d : diffs)
			{
				Estado nuevo = new Estado(actual.i + d[0], actual.j + d[1], (actual.mod + 1) % 5040);
				nuevo.valor = actual.valor + 1;
				if(!nuevo.esValido() || set.contains(nuevo))
					continue;
				set.add(nuevo);
				cola.add(nuevo);
			}
		}
		return -1;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int empezo = 0;
		while(true)
		{
			Integer n1 = sc.nextInteger();
			if(n1 == null)
				return;
			n = n1;
			inicial = new char[n][];
			mods = new char[n][];
			for(int i = 0; i < n; i++)
				inicial[i] = sc.next().trim().toCharArray();
			for(int i = 0; i < n; i++)
				mods[i] = sc.next().trim().toCharArray();
			int ans = BFS();
			if(empezo++ != 0)
				System.out.println();
			if(ans == -1)
				System.out.println("NO");
			else
				System.out.println(ans);
		}
	}
}

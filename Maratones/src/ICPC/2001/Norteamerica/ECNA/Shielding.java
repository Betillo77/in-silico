import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class Shielding 
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
	
	static class Cube
	{
		boolean existe;
		boolean infectada;
		int i, j, q;
		
		public Cube(int i2, int j2, int q2) 
		{
			i = i2;
			j = j2;
			q = q2;
		}

		static boolean valido(int a, int b, int c)
		{
			return a >= 0 && a < k && b >= 0 && b < m && c >= 0 && c < n;
		}
		
		int contar(int a, int b, int c)
		{
			return (valido(a, b, c) && nave[a][b][c].existe) ? 0 : (valido(a, b, c) && !nave[a][b][c].infectada ? 0 : 1);
		}
		
		int contar()
		{
			if(!existe)
				return 0;
			int cuenta = 0;
			cuenta += contar(i - 1, j, q);
			cuenta += contar(i + 1, j, q);
			cuenta += contar(i, j - 1, q);
			cuenta += contar(i, j + 1, q);
			cuenta += contar(i, j, q - 1);
			cuenta += contar(i, j, q + 1);
			return cuenta;
		}
	}
	
	static Cube[][][] nave;
	
	static Cube[] generar(int n, int m, int k)
	{
		nave = new Cube[k][m][n];
		Cube[] cubos = new Cube[n * m * k];
		int actual = 0;
		for(int i = 0; i < k; i++)
			for(int j = 0; j < m; j++)
				for(int q = 0; q < n; q++)
					cubos[actual++] = nave[i][j][q] = new Cube(i, j , q);
		return cubos;
	}

	static int n, m, k;
	
	static void agregar(LinkedList <Cube> cola, int i, int j, int q)
	{
		if(Cube.valido(i, j, q) && !nave[i][j][q].existe && !nave[i][j][q].infectada)
		{
			nave[i][j][q].infectada = true;
			cola.add(nave[i][j][q]);
		}
	}
	
	private static void agregar(LinkedList<Cube> cola, int a, int b, int c, int d, int e, int f) 
	{
		for(int i = a; i < b; i++)
			for(int j = c; j < d; j++)
				for(int k = e; k < f; k++)
					agregar(cola, i, j, k);
	}
	
	static void infectar()
	{
		LinkedList <Cube> cola = new LinkedList <Cube> ();
		agregar(cola, 0, k, 0, m, 0, 1);
		agregar(cola, 0, k, 0, m, n - 1, n);
		agregar(cola, 0, 1, 0, m, 0, n);
		agregar(cola, k - 1, k, 0, m, 0, n);
		agregar(cola, 0, k, 0, 1, 0, n);
		agregar(cola, 0, k, m - 1, m, 0, n);
		while(!cola.isEmpty())
		{
			Cube act = cola.poll();
			int i = act.i;
			int j = act.j;
			int q = act.q;
			agregar(cola, i - 1, j, q);
			agregar(cola, i + 1, j, q);
			agregar(cola, i, j - 1, q);
			agregar(cola, i, j + 1, q);
			agregar(cola, i, j, q - 1);
			agregar(cola, i, j, q + 1);
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			n = sc.nextInt();
			m = sc.nextInt();
			k = sc.nextInt();
			int l = sc.nextInt();
			if(n == 0 && m == 0 && k == 0 && l == 0)
				return;
			Cube[] cubos = generar(n, m, k);
			for(int i = 0; i < l; i++)
				cubos[sc.nextInt()].existe = true;
			int cuenta = 0;
			infectar();
			for(int i = 0; i < cubos.length; i++)
				cuenta += cubos[i].contar();
			System.out.println("The number of faces needing shielding is " + cuenta + ".");
		}
	}
}

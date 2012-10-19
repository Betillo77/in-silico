import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;



public class E
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
		
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}

		public int leerInt() 
		{
			String next = next();
			char signo = next.charAt(0);
			next = next.substring(1);
			return Integer.parseInt(next) * (signo == '+' ? 1 : -1);
		}
	}
	
	static int[] tablero;
	static int m;
	static ArrayList <Integer> respuesta = new ArrayList <Integer> ();
	
	static void jugar(int n)
	{
		if(n < 0 || n >= m)
			return;
		for(int i = 0, j = n; i <= j; i++, j--)
		{
			int temp1 = -tablero[i];
			int temp2 = -tablero[j];
			tablero[j] = temp1;
			tablero[i] = temp2;
		}
		respuesta.add(n + 1);
	}
	
	static void debug()
	{
		for(int i = 0; i < m; i++)
			System.out.print((tablero[i] > 0 ? "+" : "") + tablero[i] + " ");
		System.out.println();
	}
	
	static int posicion(int val)
	{
		for(int i = 0; i < m; i++)
			if(tablero[i] == val || -tablero[i] == val)
				return i;
		return 0;
	}
	static void simular()
	{
		int arreglados = 0;
		while(arreglados != m && !estaBienSigno())
		{
			while(arreglados < m && tablero[arreglados] > 0)
				arreglados++;
			if(arreglados == m)
				break;
			int siguienteMalo = arreglados;
			while(siguienteMalo < m && tablero[siguienteMalo] < 0)
				siguienteMalo++;
			jugar(arreglados - 1);
			if(siguienteMalo != 0)
				jugar(siguienteMalo - 1);
		}
	}
	
	static void arreglar()
	{
		int actual = 1;
		while(actual <= m && !estaBien())
		{
			int posAct = posicion(actual);
			if(posAct != 0)
				jugar(posAct - 1);
			if(actual == m || estaBien())
				break;
			jugar(posAct);
			actual++;
		}
	}
	
	static boolean estaBien()
	{
		boolean bien = true;
		for(int i = 0; i < m; i++)
			bien = bien && tablero[i] == (i + 1);
		return bien;
	}
	
	static boolean estaBienSigno()
	{
		boolean bien = true;
		for(int i = 0; i < m; i++)
			bien = bien && tablero[i] > 0;
		return bien;
	}

	public static void main(String[] args)
	{
		
			Scanner sc = new Scanner();
			int casos = sc.nextInt();
			for(int caso = 1; caso <= casos; caso++)
			{
				m = sc.nextInt();
				tablero = new int[m];
				for(int i = 0; i < m; i++)
				{
					tablero[i] = sc.leerInt();
				}
				int[] inicial = tablero.clone();
				respuesta.clear();
				simular();
				arreglar();
				if(respuesta.size() > (3 * m - 2))
				{
					respuesta.clear();
					tablero = inicial;
					jugar(m - 1);
					simular();
					arreglar();
				}
				System.out.print(caso + " " + respuesta.size());
				for(int i : respuesta)
					System.out.print(" " + i);
				System.out.println();
			}
	}

}

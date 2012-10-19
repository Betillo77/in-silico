import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class I 
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
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		int[] readNextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
	}
	
	static final int[][] adjacentes = new int[][]{{5, 1}, {0, 2, 6}, {1, 3}, {2, 4}, {3, 5, 6}, {0, 4}, {1, 4}};


	private static String darSiguiente(String actual, int vacio, int otro) 
	{
		char[] vals = actual.toCharArray();
		vals[vacio] = vals[otro];
		vals[otro] = ' ';
		return new String(vals);
	}
	
	static String buscar(String inicial)
	{
		HashMap <String, String> visitados = new HashMap <String, String> ();
		visitados.put(inicial, "");
		LinkedList <String> cola = new LinkedList <String> ();
		cola.add(inicial);
		while(!cola.isEmpty())
		{
			String actual = cola.pollFirst();
			String cuenta = visitados.get(actual);
			if(actual.equals("ABCDEF "))
				return cuenta;
			int vacio = -1;
			for(int i = 0; i < 7; i++)
				if(actual.charAt(i) == ' ')
					vacio = i;
			for(int otro : adjacentes[vacio])
			{
				String a = darSiguiente(actual, vacio, otro);
				if(!visitados.containsKey(a))
				{
					visitados.put(a, cuenta + actual.charAt(otro));
					cola.add(a);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int numero = sc.nextInt();
			String s = sc.next();
			String val = buscar(s + " ");
			if(val == null)
				System.out.println(numero + " NO SOLUTION");
			else
				System.out.println(numero + " " + val.length() + " " + val);
		}
	}
}

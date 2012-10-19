import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class F 
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
	
	static class Rama
	{
		Rama izquierda;
		Rama derecha;
		
		
		long contar()
		{
			if(izquierda == null && derecha == null)
				return 1;
			long izq = izquierda.contar();
			long der = derecha.contar();
			return Math.max(izq, der) * 2;
		}
	}

	static Rama parsear(LinkedList <Character> actual)
	{
		actual.poll();
		if(actual.peek().charValue() == ']')
		{
			actual.poll();
			Rama nueva = new Rama();
			nueva.izquierda = new Rama();
			nueva.derecha = new Rama();
			return nueva;
		}
		Rama nueva = new Rama();
		nueva.izquierda = parsear(actual);
		nueva.derecha = parsear(actual);
		actual.poll();
		return nueva;
	}
	
	public static void main(String[] args)
	{
		
			Scanner sc = new Scanner();
			int casos = sc.nextInt();
			for(int caso = 1; caso <= casos; caso++)
			{
				String linea = sc.nextLine().trim();
				if(linea.isEmpty())
					System.out.println(caso + " " + 1);
				else
				{
					LinkedList <Character> l = new LinkedList <Character> ();
					for(char c : linea.toCharArray())
						l.add(c);
					System.out.println(caso + " " + parsear(l).contar());
				}
			}
	}
}

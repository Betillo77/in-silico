import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class A
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
	
	static LinkedList <Integer[]> permutaciones;
	
	static boolean posible(double gap)
	{
		for(Integer[] e : permutaciones)
		{
			if(posible(gap, e))
				return true;
		}
		return false;
	}
	private static boolean posible(double gap, Integer[] e)
	{
		double inicio = rangos[e[0]][0];
		for(int i = 1; i < e.length; i++)
		{
			double siguiente = inicio + gap;
			if(siguiente > rangos[e[i]][1])
				return false;
			inicio = Math.max(rangos[e[i]][0], siguiente);
		}
		return true;
	}
	
	static double busquedaBinaria(double inicio, double fin)
	{
		for(int i = 0; i < 20; i++)
		{
			double medio = (inicio + fin) / 2;
			if(posible(medio))
				inicio = medio;
			else
				fin = medio;
		}
		return inicio;
	}
	
	static void generatePermutations(LinkedList <Integer> todas, LinkedList <Integer> actuales)
	{
		if(todas.size() == 0)
			permutaciones.add(actuales.toArray(new Integer[0]));
		for(int i = 0; i < todas.size(); i++)
		{
			actuales.addLast(todas.get(i));
			todas.remove(i);
			generatePermutations(todas, actuales);
			todas.add(i, actuales.pollLast());
		}
	}
	
	private static boolean valida(Integer[] p) 
	{
		for(int i = 1; i < p.length; i++)
		{
			int[] este = rangos[p[i]];
			int[] anterior = rangos[p[i - 1]];
			if(este[1] < anterior[0])
				return false;
		}
		return true;
	}
	
	static int n;
	static int[][] rangos;
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			n = sc.nextInt();
			if(n == 0)
				return;
			System.out.print("Case " + caso++ + ": ");
			rangos = new int[n][2];
			for(int i = 0; i < n; i++)
			{
				rangos[i][0] = sc.nextInt();
				rangos[i][1] = sc.nextInt();
			}
			permutaciones = new LinkedList <Integer[]> ();
			LinkedList <Integer> todas = new LinkedList <Integer> ();
			for(int i = 0; i < n; i++)
				todas.add(i);
			generatePermutations(todas, new LinkedList <Integer> ());
			for(Iterator <Integer[]> it = permutaciones.iterator(); it.hasNext();)
			{
				Integer[] siguientePermutacion = it.next();
				if(!valida(siguientePermutacion))
					it.remove();
			}
			double res = busquedaBinaria(0, 1440);
			int minutos = (int) res;
			int segundos = (int) Math.round((res - minutos) * 60);
			if(segundos == 60)
			{
				minutos++;
				segundos = 0;
			}
			System.out.println(minutos + ":" + (segundos < 10 ? "0" + segundos : segundos));
		}
	}
}

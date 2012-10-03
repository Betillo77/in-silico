import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;


public class Picnic
{
	static class Scanner
	{
		BufferedReader br;
		StringTokenizer st;
		
		public Scanner()
		{
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
		
		public long nextLong()
		{
			return Long.parseLong(next());
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
	}
	
	static class Conjunto
	{
		ArrayList <Integer> numeros = new ArrayList <Integer> ();
		int costoCero = -1;
	}
	static ArrayList <Conjunto> conjuntos = new ArrayList <Conjunto> ();
	static HashMap <String, Integer> personas = new HashMap <String, Integer> ();
	static int actual;
	
	static int darPersona(String s)
	{
		if(personas.containsKey(s))
			return personas.get(s);
		personas.put(s, actual);
		if(actual == 0)
			return actual++;
		Conjunto nuevo = new Conjunto();
		nuevo.numeros.add(actual);
		conjuntos.add(nuevo);
		return actual++;
	}
	
	static int darConjunto(int n)
	{
		for(int i = 0; i < conjuntos.size(); i++)
			if(conjuntos.get(i).numeros.contains(n))
				return i;
		return -1;
	}
	
	static int unir(int a, int b)
	{
		if(conjuntos.get(a).costoCero != -1 && conjuntos.get(b).costoCero != -1)
		{
			conjuntos.get(a).numeros.addAll(conjuntos.get(b).numeros);
			int val = Math.max(conjuntos.get(a).costoCero, conjuntos.get(b).costoCero);
			conjuntos.get(a).costoCero = Math.min(conjuntos.get(a).costoCero, conjuntos.get(b).costoCero);
			conjuntos.remove(b);
			return -val;
		}
		conjuntos.get(a).numeros.addAll(conjuntos.get(b).numeros);
		conjuntos.get(a).costoCero = Math.max(conjuntos.get(a).costoCero, conjuntos.get(b).costoCero);
		conjuntos.remove(b);
		return 0;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 0; caso < casos; caso++)
		{
			int ar = sc.nextInt();
			int[][] aristas = new int[ar][3];
			conjuntos.clear();
			personas.clear();
			actual = 0;
			darPersona("Park");
			for(int i = 0; i < aristas.length; i++)
			{
				aristas[i][0] = darPersona(sc.next());
				aristas[i][1] = darPersona(sc.next());
				aristas[i][2] = sc.nextInt();
			}
			Arrays.sort(aristas, new Comparator<int[]>() 
			{
				@Override
				public int compare(int[] o1, int[] o2)
				{
					return o1[2] - o2[2];
				}
			});
			int s = sc.nextInt();
			int cuenta = 0;
			for(int[] a : aristas)
			{
				if(a[0] == 0 || a[1] == 0)
				{
					int otro = a[0] == 0 ? a[1] : a[0];
					conjuntos.get(darConjunto(otro)).costoCero = a[2];
					conjuntos.get(darConjunto(otro)).numeros.add(0);
					cuenta += a[2];
				}
			}
			int mejor = Integer.MAX_VALUE;
			for(int i = 0; i < aristas.length; i++)
			{
				for(int[] a : aristas)
				{
					if(a[0] == 0 || a[1] == 0 || (conjuntos.get(darConjunto(a[0])).numeros.contains(0) && conjuntos.get(darConjunto(a[1])).numeros.contains(0)))
						continue;
					if(darConjunto(a[0]) != darConjunto(a[1]))
					{
						cuenta += a[2];
						unir(darConjunto(a[0]), darConjunto(a[1]));
					}
				}
			}
			if(solucion(s))
				mejor = Math.min(mejor, cuenta);
			for(int i = 0; i < aristas.length; i++)
			{
				int posibleMejor = Integer.MAX_VALUE;
				for(int[] a : aristas)
				{
					if(a[0] == 0 || a[1] == 0)
						continue;
					if(darConjunto(a[0]) != darConjunto(a[1]))
					{
						int act = a[2];
						if(conjuntos.get(darConjunto(a[0])).costoCero != -1 && conjuntos.get(darConjunto(a[1])).costoCero != -1)
							act -= Math.max(conjuntos.get(darConjunto(a[0])).costoCero, conjuntos.get(darConjunto(a[1])).costoCero);
						posibleMejor = Math.min(posibleMejor, act);
					}
				}
				for(int[] a : aristas)
				{
					if(a[0] == 0 || a[1] == 0)
						continue;
					if(darConjunto(a[0]) != darConjunto(a[1]))
					{
						
						int act = a[2];
						if(conjuntos.get(darConjunto(a[0])).costoCero != -1 && conjuntos.get(darConjunto(a[1])).costoCero != -1)
							act -= Math.max(conjuntos.get(darConjunto(a[0])).costoCero, conjuntos.get(darConjunto(a[1])).costoCero);
						if(act == posibleMejor)
						{
							cuenta += act;
							unir(darConjunto(a[0]), darConjunto(a[1]));
							break;
						}
					}
				}
				if(solucion(s))
					mejor = Math.min(mejor, cuenta);
			}
			if(solucion(s))
				mejor = Math.min(mejor, cuenta);
			if(caso != 0)
				System.out.println();
			System.out.println("Total miles driven: " + mejor);
		}
	}

	private static boolean solucion(int objetivo) 
	{
		for(Conjunto c : conjuntos)
			if(!c.numeros.contains(0))
				return false;
		HashSet <Integer> s = new HashSet <Integer> ();
		for(Conjunto c : conjuntos)
			s.addAll(c.numeros);
		return s.size() == personas.size() && conjuntos.size() <= objetivo;
	}
}
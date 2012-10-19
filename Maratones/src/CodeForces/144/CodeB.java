import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class CodeB 
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
		
		long nextLong()
		{
			return Long.parseLong(next());
		}
		
		
		private double nextDouble() 
		{
			return Double.parseDouble(next());
		}

		int[] nextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
		
		Integer[] nextIntegerArray(int n)
		{
			Integer[] nA = new Integer[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
		
		long[] nextLongArray(int n)
		{
			long[] nA = new long[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextLong();
			return nA;
		}
		
		Long[] nextLongOArray(int n)
		{
			Long[] nA = new Long[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextLong();
			return nA;
		}
		
		double[] nextDoubleArray(int n)
		{
			double[] nA = new double[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextDouble();
			return nA;
		}

		Double[] nextDoubleOArray(int n)
		{
			Double[] nA = new Double[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextDouble();
			return nA;
		}
		
		String[] nextStringArray(int n)
		{
			String[] nA = new String[n];
			for(int i = 0; i < n; i++)
				nA[i] = next();
			return nA;
		}
	}
	
		static Long[][] dpChoose = new Long[101][101];
		
		static long choose(int a, int b)
		{
			if(dpChoose[a][b] != null)
				return dpChoose[a][b];
			if(a == 0)
				return dpChoose[a][b] = 1L;
			if(b == 0)
				return dpChoose[a][b] = 0L;
			return dpChoose[a][b] = choose(a - 1, b - 1) + choose(a, b - 1);
		}
		
	    static long n;
	    
	    static void generate(LinkedList <Nodo> actuales, int needed)
	    {
	    	if(needed == 0)
	    		return;
	    	int extras = 3;
	    	int current = (int) choose(3, 3);
	    	while(current <= needed && extras + 2 < actuales.size())
	    	{
	    		extras++;
	    		current = (int) (choose(3, extras) - choose(3, extras - 1));
	    	}
	    	extras--;
	    	if(actuales.size() == 0 || extras < 3)
	    	{
	    		generate(needed);
	    		return;
	    	}
	    	current = (int) (choose(3, extras) - choose(3, extras - 1));
		    Nodo nuevo = new Nodo(nAct++);
		    todosNodos.add(nuevo);
		    for(int i = 0; i < extras - 1; i++)
		    {
		    	Nodo a = actuales.poll();
		    	a.adjacentes.add(nuevo.n);
		    	nuevo.adjacentes.add(a.n);
		    }
		    generate(actuales, needed - current);
	    }
	    
	    
	    static LinkedList <Nodo> generarLleno(int n)
	    {
	    	LinkedList <Nodo> todos = new LinkedList <Nodo> ();
	    	for(int i = 0; i < n; i++)
	    	{
	    		Nodo actual = new Nodo(nAct++);
	    		todos.add(actual);
	    	}
	    	for(int i = 0; i < n; i++)
	    	{
	    		for(int j = i + 1; j < n; j++)
	    		{
	    			todos.get(i).adjacentes.add(todos.get(j).n);
	    			todos.get(j).adjacentes.add(todos.get(i).n);
	    		}
	    	}
	    	return todos;
	    }
	    
	    static LinkedList <Nodo> todosNodos = new LinkedList <Nodo> ();
	    static int nAct = 0;
	    static int lastUsed = 0;
	    
	    static void generate(int k)
	    {
	    	if(k == 0)
	    	{
	    		todosNodos = new LinkedList <Nodo> ();
	    		todosNodos.add(new Nodo(0));
	    		return;
	    	}
	    	for(int i = 3; i <= 101; i++)
	    		if(choose(3, i) > k)
	    		{
	    			LinkedList <Nodo> nuevo = generarLleno(i - 1);
	    			todosNodos.addAll(nuevo);
	    			generate(nuevo, (int) (k - choose(3, i - 1)));
	    			return;
	    		}
	    	return;
	    }
	    
	    static class Nodo
	    {
	    	public Nodo(int i) 
	    	{
	    		n = i;
			}
			int n;
	    	ArrayList <Integer> adjacentes = new ArrayList <Integer> ();
	    }
	    
		public static void main(String[] args)
		{
			Scanner sc = new Scanner();
			int k = sc.nextInt();
			nAct = 0;
		    todosNodos = new LinkedList <Nodo> ();
			generate(k);
			boolean[][] matriz = new boolean[todosNodos.size()][todosNodos.size()];
			for(Nodo n : todosNodos)
			{
				for(int otro : n.adjacentes)
				{
					matriz[n.n][otro] = true;
				}
			}
			System.out.println(todosNodos.size());
			for(int i = 0; i < matriz.length; i++)
			{
				for(int j = 0; j < matriz[0].length; j++)
					System.out.print(matriz[i][j] ? "1" : "0");
				System.out.println();
			}
		}
}

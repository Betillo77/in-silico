import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class D 
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
				return st.hasMoreTokens();
			}
			catch(Exception e) { throw new RuntimeException(); }
		}
	}

	static class Orden
	{
		int n;
		int desde;
		
		Orden(int n2, int padre)
		{
			n = n2;
			desde = padre;
		}
	}
	
	static class Arista
	{
		int otro;
		int desde;
		
		Arista(int o, int d)
		{
			otro = o;
			desde = d;
		}
	}
	
	static LinkedList <Orden> ordenes = new LinkedList <Orden> ();
	static Nodo[] nodos;
	
	static class Nodo
	{
		ArrayList <Arista> aristas = new ArrayList <Arista> ();
		ArrayList <Integer> soluciones = new ArrayList <Integer> ();
		TreeMap <Integer, Integer> dondeAristas = new TreeMap <Integer, Integer> (); 
		LinkedList <Arista> porResolver = new LinkedList <Arista> ();
		int resueltos;
		int cuentaTotal;
		int n;
		
		public Nodo(int i) 
		{
			n = i;
		}
		

		void arreglar() 
		{
			int i = 0;
			for(Arista a : aristas)
			{
				soluciones.add(null);
				dondeAristas.put(a.otro, i++);
				porResolver.add(a);
			}
		}

		void resolver(int padre)
		{
			if(resuleto(padre) != -1)
				return;
			Iterator <Arista> it = porResolver.iterator();
			while(it.hasNext())
			{
				Arista a = it.next();
				if(a.otro == padre)
					continue;
				if(nodos[a.otro].resuleto(n) != -1)
				{
					int cuenta = nodos[a.otro].resuleto(n);
					if(a.desde != n)
						cuenta++;
					soluciones.set(dondeAristas.get(a.otro), cuenta);
					cuentaTotal += cuenta;
					resueltos++;
					it.remove();
				}
				else
				{
					ordenes.addFirst(new Orden(n, padre));
					ordenes.addFirst(new Orden(a.otro, n));
					return;
				}
			}
		}
		
		int resuleto(int padre)
		{
			if(padre == -1 && resueltos == aristas.size())
				return cuentaTotal;
			else if(padre != -1)
			{
				if(resueltos == aristas.size())
					return cuentaTotal - soluciones.get(dondeAristas.get(padre));
				else if(resueltos == aristas.size() - 1 && soluciones.get(dondeAristas.get(padre)) == null)
					return cuentaTotal;
			}
			return -1;
		}
	}
	
	static int solve(int c)
	{
		Nodo ciudad = nodos[c];
		ordenes.clear();
		ordenes.add(new Orden(c, -1));
		while(!ordenes.isEmpty())
		{
			Orden o = ordenes.poll();
			nodos[o.n].resolver(o.desde);
		}
		return ciudad.resuleto(-1);
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		nodos = new Nodo[n];
		for(int i = 0; i < n; i++)
			nodos[i] = new Nodo(i);
		for(int i = 0; i < n - 1; i++)
		{
			int a = sc.nextInt() - 1;
			int b = sc.nextInt() - 1;
			Arista ab = new Arista(b, a);
			Arista ba = new Arista(a, a);
			nodos[a].aristas.add(ab);
			nodos[b].aristas.add(ba);
		}
		for(int i = 0; i < n; i++)
			nodos[i].arreglar();
		ArrayList <Integer> mejoresAct = new ArrayList <Integer> ();
		int mejor = 100000000;
		for(int i = 0; i < n; i++)
		{
			int count = solve(i);
			if(count < mejor)
			{
				mejor = count;
				mejoresAct.clear();
				mejoresAct.add(i + 1);
			}
			else if(count == mejor)
				mejoresAct.add(i + 1);
		}
		System.out.println(mejor);
		System.out.print(mejoresAct.get(0));
		for(int i = 1; i < mejoresAct.size(); i++)
			System.out.print(" " + mejoresAct.get(i));
		System.out.println();
	}
}
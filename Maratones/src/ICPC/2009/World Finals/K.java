import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;


public class K
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
	}

	static class Regla
	{
		long a;
		long b;

		public Regla(long aa, long bb) 
		{
			a = aa;
			b = bb;
		}
	}
	
	static class Arista implements Comparable <Arista>
	{
		Nodo otro;
		long costo;
		
		public Arista(Nodo o, long nuevoCosto) 
		{
			otro = o;
			costo = nuevoCosto;
		}

		@Override
		public int compareTo(Arista o) 
		{
			return Long.valueOf(costo).compareTo(o.costo);
		}
	}
	
	static class Nodo
	{
		long id;
		boolean llenas = false;
		Nodo hijo;
		int tam;
		char primeraLetra;
		ArrayList <Arista> aristas = new ArrayList <Arista> ();
		ArrayList <Long> distancias = new ArrayList <Long> ();
		
		public Nodo(long i, int t, char p, Nodo h)
		{
			id = i;
			tam = t;
			primeraLetra = p;
			hijo = h;
			if(tam == 0)
				distancias.add(0L);
		}
		
		Long darDistancia(long otro)
		{
			int idOtro = (int) otro;
			while(distancias.size() < idOtro + 1)
				distancias.add(null);
			return distancias.get(idOtro);
		}

		public long ponerDistancia(long otro, long costo)
		{
			int idOtro = (int) otro;			
			while(distancias.size() < idOtro + 1)
				distancias.add(null);
			distancias.set(idOtro, costo);
			return costo;
		}
	}

	static Regla[] reglas;
	static ArrayList <Nodo> [] nodos;
	static HashMap <String, Long> todos = new HashMap <String, Long> ();
	static int[] tams;
	
	static void llenarAristas(Nodo n)
	{
		if(n.llenas)
			return;
		n.llenas = true;
		ArrayList <Long> adjacentes = new ArrayList <Long> ();
		for(int i = 0; i < reglas.length; i++)
			if(reglas[i].a == n.id)
			{
				n.aristas.add(new Arista(darNodo(reglas[i].b), 1));
				adjacentes.add(reglas[i].b);
			}
		if(n.tam > 1)
		{
			for(Nodo vecino : nodos[n.tam])
				if(vecino != n && vecino.primeraLetra == n.primeraLetra && !adjacentes.contains(vecino.id))
				{
					long mCost = mejorCosto(n.hijo, vecino.hijo);
					if(mCost != infinito)
						n.aristas.add(new Arista(vecino, mCost));
				}
		}
	}
	
	static long mejorCosto(Nodo a, Nodo b) 
	{
		if(a == null || b == null)
			return 0;
		if(a.tam == 0 || b.tam == 0)
			return 0;
		if(a.darDistancia(b.id) != null)
			return a.darDistancia(b.id);
		if(a == b)
			return a.ponerDistancia(b.id, 0);
		PriorityQueue <Arista> pq = new PriorityQueue <Arista> ();
		pq.add(new Arista(a, 0));
		a.ponerDistancia(a.id, 0L);
		a.ponerDistancia(b.id, infinito);
		while(!pq.isEmpty())
		{
			Arista menor = pq.poll();
			Nodo este = menor.otro;
			if(a.darDistancia(este.id) != menor.costo)
				continue;
			a.ponerDistancia(este.id, menor.costo);
			for(Arista siguiente : este.aristas)
			{
				long nuevoCosto = menor.costo + siguiente.costo;
				if(a.darDistancia(siguiente.otro.id) == null || a.darDistancia(siguiente.otro.id) > nuevoCosto)
				{
					a.ponerDistancia(siguiente.otro.id, nuevoCosto);
					pq.add(new Arista(siguiente.otro, nuevoCosto));
				}
			}
		}
		return a.darDistancia(b.id);
	}
	

	private static void llenarTodos(String inicial) 
	{
		for(int i = 0; i < inicial.length(); i++)
			dar(inicial.substring(i));
		for(int i = 1; i <= inicial.length(); i++)
			dar(inicial.substring(0, i));
	}

	private static Nodo dar(String s) 
	{
		if(todos.containsKey(s))
			return darNodo(todos.get(s));
		long tamSL = nodos[s.length()].size();
		long nuevoId = (((long) (s.length())) << 32) | tamSL;
		Nodo nuevo = s.length() != 0 ? new Nodo(nuevoId, s.length(), s.charAt(0), dar(s.substring(1))) : new Nodo(0L, 0, 'a', null);
		todos.put(s, nuevoId);
		nodos[s.length()].add(nuevo);
		return darNodo(nuevoId);
	}

	static Nodo darNodo(long id) 
	{
		int tam = (int) (id >> 32);
		return nodos[tam].get((int) id);
	}


	static Random r = new Random();

	public static void generateRandom() throws IOException
	{
		BufferedWriter bw = new BufferedWriter(new FileWriter("test.tmp"));
		bw.write(generateRandom(20) + " " + generateRandom(20) + " " + 100 + "\n");
		for(int i = 0; i < 100; i++)
		{
			int size = r.nextInt(20) + 1;
			bw.write(generateRandom(size) + " " + generateRandom(size) + "\n");
		}
		bw.close();
	}
	
	private static String generateRandom(int tam)
	{
		String s = "";
		for(int i = 0; i < tam; i++)
			s += ((char) ('A' + r.nextInt(4)));
		return s;
	}


	static long infinito = Long.MAX_VALUE;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException
	{
//		generateRandom();
//		System.setIn(new FileInputStream("test.tmp"));
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			String a = sc.next().trim();
			if(a.equals("."))
				return;
			String inicial = a;
			String fin = sc.next().trim();
			nodos = new ArrayList[21];
			for(int i = 0; i < 21; i++)
				nodos[i] = new ArrayList <Nodo> ();
			todos.clear();
			int nReglas = sc.nextInt();
			reglas = new Regla[nReglas];
			llenarTodos(inicial);
			llenarTodos(fin);
			for(int i = 0; i < nReglas; i++)
			{
				String rA = sc.next().trim();
				String rB = sc.next().trim();
				reglas[i] = new Regla(dar(rA).id, dar(rB).id);
				llenarTodos(rA);
				llenarTodos(rB);
			}
			for(int i = 0; i < 21; i++)
				for(Nodo n : nodos[i])
				{
					llenarAristas(n);
					if(n.hijo != null)
						llenarAristas(n.hijo);
				}
			long res = mejorCosto(dar(inicial), dar(fin));
			System.out.println("Case " + caso++ + ": " + ((res == infinito) ? "No solution" : res + ""));
		}
	}
}

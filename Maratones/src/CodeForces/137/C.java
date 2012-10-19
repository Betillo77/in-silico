import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class C 
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
	 
	static final int[] primos = new int[600];
	static final int[] cuadrados = new int[600];
	static final int[] todos = new int[10000001];
	
	static void agregar(int numero, int signo)
	{
		for(int i = 0; true; i++)
		{
			if(cuadrados[i] > numero)
				break;
			int pA = primos[i];
			while(numero % pA == 0)
			{
				todos[pA] += signo;
				numero /= pA;
			}
		}
		if(numero != 1)
			todos[numero] += signo;
	}

	static int idA = 0;
	
	static class Numero implements Comparable <Numero>
	{
		int numero;
		int veces;
		int id;
		
		Numero(int n)
		{
			numero = n;
			id = idA++;
		}

		@Override
		public int compareTo(Numero o) 
		{
			if(numero == o.numero)
				return id - o.id;
			return numero - o.numero;
		}
		
		@Override
		public String toString()
		{
			return numero + ":" + veces;
		}
	}
	static int[] factores = new int[100];
	static int[] veces = new int[100];
	
	static int posibleAgregar(int numero, int signo)
	{
		int nFactores = 0;
		for(int i = 0; true; i++)
		{
			if(cuadrados[i] > numero)
				break;
			int pA = primos[i];
			int cuenta = 0;
			while(numero % pA == 0)
			{
				cuenta++;
				numero /= pA;
			}
			if(cuenta != 0)
			{
				factores[nFactores] = pA;
				veces[nFactores++] = cuenta;
			}
		}
		if(numero != 1)
		{
			factores[nFactores] = numero;
			veces[nFactores++] = 1;
		}
		if(signo == -1)
		{
			int minimo = Integer.MAX_VALUE;
			for(int i = 0; i < nFactores; i++)
			{
				if(todos[factores[i]] > -veces[i])
					return 0;
				else
					minimo = Math.min(minimo, (-todos[factores[i]]) / veces[i]);
			}
			return minimo;
		}
		else
		{
			int minimo = Integer.MAX_VALUE;
			for(int i = 0; i < nFactores; i++)
			{
				if(todos[factores[i]] < veces[i])
					return 0;
				else
					minimo = Math.min(minimo, (todos[factores[i]]) / veces[i]);
			}
			return minimo;
		}
	}
	
	static class CachedMap extends TreeMap <Numero, Numero>
	{
		private static final long serialVersionUID = 276345233289370682L;
		
		Numero last;
		Numero first;
		
		Numero giveLast()
		{
			if(last == null)
				last = lastKey();
			return last;
		}
		
		Numero giveFirst()
		{
			if(first == null)
				first = firstKey();
			return first;
		}
		
		int pollLastKey()
		{
			Numero cLast = giveLast();
			if(cLast.veces == 1)
			{
				pollLastEntry();
				last = null;
			}
			cLast.veces--;
			return cLast.numero;
		}
		
		int pollFirstKey()
		{
			Numero cFirst = giveFirst();
			if(cFirst.veces == 1)
			{
				pollFirstEntry();
				first = null;
			}
			cFirst.veces--;
			return cFirst.numero;
		}

		public void addFirst(int primero) 
		{
			Numero cFirst = giveFirst();
			if(cFirst.numero == primero)
				cFirst.veces++;
			else
			{
				first = null;
				Numero nuevo = new Numero(primero);
				nuevo.veces = 1;
				put(nuevo, nuevo);
			}
		}

		public void removeMiddle(Numero numero) 
		{
			if(numero.veces == 1)
			{
				if(numero == first)
					first = null;
				if(numero == last)
					last = null;
				remove(numero);
			}
			else
				numero.veces--;
		}
	}
	
	static void solucionar(CachedMap as, LinkedList <Integer> pA)
	{
		Numero temp = new Numero(0);
		temp.id = 10000000;
		Long ultimo = null;
		while(!as.isEmpty())
		{
			if(ultimo == null)
				ultimo = (long) as.pollLastKey();
			if(!as.isEmpty())
			{
				int primero = as.giveFirst().numero;
				if(ultimo * primero > 10000000)
				{
					pA.add((int) ultimo.longValue());
					ultimo = null;
				}
				else
				{
					as.pollFirstKey();
					if(!as.isEmpty())
					{
						int segundo = as.giveFirst().numero;
						if(ultimo * primero * segundo > 10000000)
						{
							as.addFirst(primero);
							temp.numero = (int) (10000000 / ultimo);
							Numero mejor = as.floorKey(temp);
							as.removeMiddle(mejor);
							pA.add((int) (ultimo * mejor.numero));
							ultimo = null;
						}
						else
						{
							ultimo = ultimo * primero;
						}
					}
					else
					{
						pA.add((int) (primero * ultimo));
						ultimo = null;
					}
				}
			}
			else
			{
				pA.add((int) ultimo.longValue());
				ultimo = null;
			}
		}
		if(ultimo != null)
			pA.add((int) ultimo.longValue());
	}
	
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		boolean[] esPrimo = new boolean[4000];
		esPrimo[0] = esPrimo[1] = false;
		for(int i = 2; i < 4000; i++)
			esPrimo[i] = true;
		for(int i = 0; i < 4000; i++)
		{
			if(i * i > 4000)
				break;
			if(esPrimo[i])
				for(int j = i * i; j < 4000; j += i)
					esPrimo[j] = false;
		}
		int cuenta = 0;
		for(int i = 0; i < 4000; i++)
			if(esPrimo[i])
			{
				primos[cuenta] = i;
				cuadrados[cuenta++] = i * i;
			}
		int n = sc.nextInt();
		int m = sc.nextInt();
		int[] aInicial = new int[n];
		int[] bInicial = new int[m];
		TreeMap <Integer, Integer> actuales = new TreeMap <Integer, Integer> ();
		for(int i = 0; i < n; i++)
		{
			aInicial[i] = sc.nextInt();
			if(aInicial[i] != 1)
				actuales.put(aInicial[i], actuales.containsKey(aInicial[i]) ? actuales.get(aInicial[i]) + 1 : 1);
		}
		for(Map.Entry<Integer, Integer> e : actuales.entrySet())
			agregar(e.getKey(), e.getValue());
		actuales.clear();
		for(int i = 0; i < m; i++)
		{
			bInicial[i] = sc.nextInt();
			if(bInicial[i] != 1)
				actuales.put(bInicial[i], actuales.containsKey(bInicial[i]) ? actuales.get(bInicial[i]) + 1 : 1);
		}
		for(Map.Entry<Integer, Integer> e : actuales.entrySet())
			agregar(e.getKey(), -e.getValue());
		actuales.clear();
		LinkedList <Integer> pA = new LinkedList <Integer> ();
		LinkedList <Integer> pB = new LinkedList <Integer> ();
		Arrays.sort(aInicial);
		Arrays.sort(bInicial);
		for(int i = n - 1; i >= Math.max(0, n - 2); i--)
		{
			if(aInicial[i] != 1 && aInicial[i] > 5000000)
			{
				int vs = posibleAgregar(aInicial[i], 1);
				if(vs != 0)
				{
					for(int j = 0; j < vs; j++)
						pA.add(aInicial[i]);
					agregar(aInicial[i], -vs);
				}
			}
		}
		for(int i = m - 1; i >= Math.max(0, m - 2); i--)
		{
			if(bInicial[i] != 1 && bInicial[i] > 5000000)
			{
				int vs = posibleAgregar(bInicial[i], -1);
				if(vs != 0)
				{
					for(int j = 0; j < vs; j++)
						pB.add(bInicial[i]);
					agregar(bInicial[i], vs);
				}
			}
		}
		CachedMap as = new CachedMap();
		CachedMap bs = new CachedMap();
		StringBuilder a = new StringBuilder();
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < todos.length; i++)
		{
			if(todos[i] > 0)
			{
				Numero nuevo = new Numero(i);
				nuevo.veces = todos[i];
				as.put(nuevo, nuevo);
			}
		}
		for(int i = 0; i < todos.length; i++)
		{
			if(todos[i] < 0)
			{
				Numero nuevo = new Numero(i);
				nuevo.veces = -todos[i];
				bs.put(nuevo, nuevo);
			}
		}
		solucionar(as, pA);
		solucionar(bs, pB);
		if(pA.isEmpty())
			pA.add(1);
		if(pB.isEmpty())
			pB.add(1);
		System.out.println(pA.size() + " " + pB.size());
		boolean empezo = false;
		for(int i : pA)
		{
			if(empezo)
				a.append(" " + i);
			else
			{
				empezo = true;
				a.append(i);
			}
		}
		empezo = false;
		for(int i : pB)
		{
			if(empezo)
				b.append(" " + i);
			else
			{
				empezo = true;
				b.append(i);
			}
		}
		System.out.println(a);
		System.out.println(b);
	}
}
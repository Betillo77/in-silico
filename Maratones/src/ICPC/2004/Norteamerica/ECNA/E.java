import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class E
{
	 static class Entry 
	 {
	        private int   hash;
	        private long   key;
	        private Object value;
	        private Entry  next;

	        Entry(int hash, long key, Object value, Entry next) 
	        {
	            this.hash  = hash;
	            this.key   = key;
	            this.value = value;
	            this.next  = next;
	        }

	        long getKey() { return key; }

	        Object getValue() { return value; }
	    }
	 
	static class LongHashMap
	{
	   

	    /**
	     * The hash table data.
	     */
	    transient Entry table[];

	    /**
	     * The total number of mappings in the hash table.
	     */
	    transient int size;

	    /**
	     * The table is rehashed when its size exceeds this threshold.  (The
	     * value of this field is (int)(capacity * loadFactor).)
	     *
	     * @serial
	     */
	    int threshold;

	    /**
	     * The load factor for the hash table.
	     *
	     * @serial
	     */
	    final float loadFactor;

	    /**
	     * Constructs a new, empty map with the specified initial
	     * capacity and the specified load factor.
	     *
	     * @param      initialCapacity   the initial capacity of the HashMap.
	     * @param      loadFactor        the load factor of the HashMap
	     * @throws     IllegalArgumentException  if the initial capacity is less
	     *               than zero, or if the load factor is nonpositive.
	     */
	    public LongHashMap(int initialCapacity, float loadFactor)
	    {
	        this.loadFactor = loadFactor;
	        table = new Entry[initialCapacity];
	        threshold = (int)(initialCapacity * loadFactor);
	    }

	    /**
	     * Constructs a new, empty map with the specified initial capacity
	     * and default load factor, which is <tt>0.75</tt>.
	     *
	     * @param   initialCapacity   the initial capacity of the HashMap.
	     * @throws    IllegalArgumentException if the initial capacity is less
	     *              than zero.
	     */
	    public LongHashMap(int initialCapacity)
	    {
	        this(initialCapacity, 0.75f);
	    }
	    
	    public Object get(long key) {
	        Entry e = getEntry(key);
	        return (e == null ? null : e.value);
	    }

	    public boolean containsKey(long key) 
	    {
	        return getEntry(key) != null;
	    }

	    Entry getEntry(long key)
	    {
	        Entry tab[] = table;
	        int hash = (int) (key ^ (key >> 32));
	        int index = (hash & 0x7FFFFFFF) % tab.length;
	        for (Entry e = tab[index]; e != null; e = e.next)
	            if (e.hash == hash && e.key==key)
	                return e;

	        return null;
	    }


	    public Object put(long key, Object value)
	    {
	        Entry tab[] = table;
	        int hash = (int) (key ^ (key >> 32));
	        int index = (hash & 0x7FFFFFFF) % tab.length;
	        for (Entry e = tab[index] ; e != null ; e = e.next) {
	            if (e.hash == hash && e.key == key) {
	                Object oldValue = e.value;
	                e.value = value;
	                return oldValue;
	            }
	        }
	        if (size >= threshold) 
	        {
	            rehash();
	            tab = table;
	            index = (hash & 0x7FFFFFFF) % tab.length;
	        }
	        size++;
	        tab[index] = newEntry(hash, key, value, tab[index]);
	        return null;
	    }

	    public Object remove(long key)
	    {
	        Entry e = removeEntryForKey(key);
	        return (e == null ? null : e.value);
	    }

	    /**
	     * Removes and returns the entry associated with the specified key
	     * in the HashMap.  Returns null if the HashMap contains no mapping
	     * for this key.
	     */
	    Entry removeEntryForKey(long key)
	    {
	        Entry tab[] = table;
	        int hash = (int) key;
	        int index = (hash & 0x7FFFFFFF) % tab.length;

	        for (Entry e = tab[index], prev = null; e != null;
	             prev = e, e = e.next) {
	            if (e.hash == hash && e.key == key) {
	                if (prev != null)
	                    prev.next = e.next;
	                else
	                    tab[index] = e.next;

	                size--;
	                return e;
	            }
	        }

	        return null;
	    }


	    /**
	     * Rehashes the contents of this map into a new <tt>HashMap</tt> instance
	     * with a larger capacity. This method is called automatically when the
	     * number of keys in this map exceeds its capacity and load factor.
	     */
	    void rehash() {
	        Entry oldTable[] = table;
	        int oldCapacity = oldTable.length;
	        int newCapacity = oldCapacity * 2 + 1;
	        Entry newTable[] = new Entry[newCapacity];
	        threshold = (int)(newCapacity * loadFactor);
	        table = newTable;

	        for (int i = oldCapacity ; i-- > 0 ;) {
	            for (Entry old = oldTable[i] ; old != null ; ) {
	                Entry e = old;
	                old = old.next;

	                int index = (e.hash & 0x7FFFFFFF) % newCapacity;
	                e.next = newTable[index];
	                newTable[index] = e;
	            }
	        }
	    }

	    static boolean eq(Object o1, Object o2) 
	    {
	        return (o1==null ? o2==null : o1.equals(o2));
	    }

	    Entry newEntry(int hash, long key, Object value, Entry next) 
	    {
	        return new Entry(hash, key, value, next);
	    }
	}
	
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		String nextLine()
		{
			try
			{
				String s = br.readLine();
				return s;
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
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

		public int nextInt() 
		{
			return Integer.parseInt(next());
		}
	}
	
	static int[][] dados;
	
	static long[][] dp;
	
	static long[] dpSumas(int dado)
	{
		if(dp[dado] != null)
			return dp[dado];
		long[] respuesta = new long[1001];
		if(dado == dados.length)
		{
			respuesta[0] = 1L;
			return dp[dado] = respuesta;
		}
		for(int i : dados[dado])
		{
			long[] siguientes = dpSumas(dado + 1);
			for(int j = 0; j < siguientes.length; j++)
			{
				int nuevaSuma = j + i;
				if(nuevaSuma > 1000)
					break;
				respuesta[nuevaSuma] += siguientes[j];
			}
		}
		return dp[dado] = respuesta;
	}
	
	static class Entrada
	{
		BitSet key;
		BitSet value;
		
		Entrada(BitSet a, BitSet b)
		{
			key = a;
			value = b;
		}
		
		Entrada[] interseccion(Entrada otra)
		{
			BitSet temp = new BitSet(key.size());
			temp.or(key);
			temp.andNot(otra.key);
			Entrada sobrante = null;
			if(!temp.isEmpty())
			{
				BitSet n = new BitSet(value.size());
				n.or(value);
				sobrante = new Entrada(temp, n);
				temp = new BitSet(key.size());
			}
			temp.clear();
			temp.or(key);
			temp.and(otra.key);
			BitSet n = new BitSet(value.size());
			n.or(value);
			n.and(otra.value);
			Entrada interseccion = null;
			if(!n.isEmpty())
				interseccion = new Entrada(temp, n);
			BitSet t1 = new BitSet(otra.key.size());
			t1.or(otra.key);
			t1.andNot(key);
			otra.key = t1;
			return new Entrada[] {interseccion, sobrante};
		}
	}
	
	static class Respuesta
	{
		int[] donde = new int[25000];
		ArrayList <Entrada> entradas = new ArrayList <Entrada> ();
		int actual = 0;
		
		Respuesta()
		{
			for(int i = 0; i < donde.length; i++)
				donde[i] = -1;
		}
		void agregar(BitSet d, BitSet s)
		{
			for(int current = d.nextSetBit(0); current != -1; current = d.nextSetBit(current + 1)) 
				donde[current] = actual;
			entradas.add(new Entrada(d, s));
			actual++;
		}
		
		void eliminar(int indice)
		{
			BitSet d = entradas.get(indice).key;
			entradas.set(indice, null);
			for(int current = d.nextSetBit(0); current != -1; current = d.nextSetBit(current + 1)) 
				donde[current] = -1;
		}
		
		void intersectar(Entrada e)
		{
			BitSet d = e.key;
			Set <Integer> s = new TreeSet <Integer> ();
			for(int current = d.nextSetBit(0); current != -1; current = d.nextSetBit(current + 1)) 
			{
				if(donde[current] != -1)
					s.add(donde[current]);
			}
			for(int i : s)
			{
				if(e.key.isEmpty())
					return;
				Entrada esta = entradas.get(i);
				eliminar(i);
				Entrada[] interseccion = esta.interseccion(e);
				for(Entrada nueva : interseccion)
					if(nueva != null)
						agregar(nueva.key, nueva.value);
			}
		}

		public Dado mejorDado(int nP) 
		{
			Dado mejor = null;
			int[][] aD = todosDados[nP >> 1];
			int[][] bD = todosDados[nP - (nP >> 1)];
			for(int i = 0; i < donde.length; i++)
			{
				if(donde[i] == -1)
					continue;
				BitSet valor = entradas.get(donde[i]).value;
				if(!valor.isEmpty())
				{
					int[] a = aD[i];
					int[] b = bD[valor.nextSetBit(0)];
					int[] nuevo = new int[a.length + b.length];
					for(int j = 0; j < a.length; j++)
						nuevo[j] = a[j];
					for(int j = 0; j < b.length; j++)
						nuevo[a.length + j] = b[j];
					Arrays.sort(nuevo);
					Dado n = new Dado(nuevo);
					if(mejor == null || n.compareTo(mejor) < 0)
						mejor = n;
				}
			}
			return mejor;
		}

		public void borrarTodosN(BitSet d)
		{
			for(int current = d.nextClearBit(0); current < 25000; current = d.nextClearBit(current + 1)) 
			{
				if(donde[current] != -1)
				{
					entradas.get(donde[current]).key.clear(current);
					donde[current] = -1;
				}
			}
		}
 	}
	
	static int[][] agregarDados(int numeros) 
	{
		ArrayList<int[]> a = new ArrayList <int[]> ();
		if(numeros == 1)
		{
			int[] dado = new int[1];
			for(int i = 1; i <= 50; i++)
			{
				dado[0] = i;
				a.add(dado.clone());
			}
		}
		else if(numeros == 2)
		{
			int[] dado = new int[2];
			for(int i = 1; i <= 50; i++)
				for(int j = i; j <= 50; j++)
				{
					dado[0] = i;
					dado[1] = j;
					a.add(dado.clone());
				}
		}
		else
		{
			int[] dado = new int[3];
			for(int i = 1; i <= 50; i++)
				for(int j = i; j <= 50; j++)
					for(int k = j; k <= 50; k++)
					{
						dado[0] = i;
						dado[1] = j;
						dado[2] = k;
						a.add(dado.clone());
					}
		}
		return a.toArray(new int[0][0]);
	}

	
	static class Dado implements Comparable <Dado>
	{

		int[] d;
		
		public Dado(int[] dd)
		{
			d = dd;
		}

		@Override
		public int compareTo(Dado o) 
		{
			for(int i = 0; i < d.length; i++)
				if(d[i] != o.d[i])
					return d[i] - o.d[i];
			return 0;
		}

		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Dado other = (Dado) obj;
			if (!Arrays.equals(d, other.d))
				return false;
			return true;
		}
	}
	
	static void solucionarDado(int[] dado, int objetivo, long[] sumas, LongHashMap actual, int nDado)
	{
		long sumaObjetivo = 0;
		for(int i : dado)
			if(objetivo - i >= 0)
				sumaObjetivo += sumas[objetivo - i];
		if(actual.containsKey(sumaObjetivo))
			((BitSet) (actual.get(sumaObjetivo))).set(nDado);
		else
		{
			BitSet a = new BitSet();
			a.set(nDado);
			actual.put(sumaObjetivo, a);
		}
	}
	
	static LongHashMap generarDados(int numeros, int objetivo, long[] sumas)
	{
		int[][] dadosEste = todosDados[numeros];
		LongHashMap respuesta = new LongHashMap(50000);
		int nDado = 0;
		for(int[] dado : dadosEste)
		{
			solucionarDado(dado, objetivo, sumas, respuesta, nDado);
			nDado++;
		}
		return respuesta;
	}
	
	static int[][] entrada;
	
	static Respuesta solucionar(long[] sumas, int n)
	{
		Respuesta respuesta = null;
		for(int [] e : entrada)
		{
			BitSet todos = new BitSet();
			Respuesta parcial = new Respuesta();
			LongHashMap a = generarDados(n >> 1, e[0], sumas);
			LongHashMap b = generarDados(n - (n >> 1), e[0], sumas);
			for(int i = 0; i < a.table.length; i++)
			{
				Entry value = a.table[i];
				while(value != null)
				{
					if(b.containsKey(e[1] - value.getKey()))
					{
							todos.or((BitSet) value.getValue());
							if(respuesta == null)
								parcial.agregar((BitSet) (value.getValue()), (BitSet) (b.get(e[1] - value.getKey())));
							else
								respuesta.intersectar(new Entrada((BitSet) (value.getValue()), (BitSet) (b.get(e[1] - value.getKey()))));
						
					}
					value = value.next;
				}
			}
			if(respuesta == null)
				respuesta = parcial;
			respuesta.borrarTodosN(todos);
		}
		return respuesta;
	}
	
	static int[][][] todosDados = new int[4][][];
	
	public static void main(String[] args)
	{
		for(int i = 1; i <= 3; i++)
			todosDados[i] = agregarDados(i);
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			dados = new int[n][];
			for(int i = 0; i < n; i++)
			{
				int f = sc.nextInt();
				int[] dado = new int[f];
				dados[i] = dado;
				for(int j = 0; j < f; j++)
					dado[j] = sc.nextInt();
			}
			dp = new long[n + 1][];
			long[] sumas = dpSumas(0);
			int r = sc.nextInt();
			int m = sc.nextInt();
			entrada = new int[m][2];
			for(int i = 0; i < m; i++)
			{
				entrada[i][0] = sc.nextInt();
				entrada[i][1] = sc.nextInt();
			}
			Dado respuesta = solucionar(sumas, r).mejorDado(r);
			if(respuesta == null)
				System.out.println("Impossible");
			else
				System.out.println("Final die face values are " + Arrays.toString(respuesta.d).replace("[", "").replace("]", "").replace(",", ""));
		}
	}
}

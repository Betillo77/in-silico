import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class FoolGame
{
	static class Entry 
	{
		int key;
		int iteration;
		boolean value;
		Entry next;

		Entry(int key, int iteration, boolean value, Entry next) 
		{
			this.key   = key;
			this.iteration = iteration;
			this.value = value;
			this.next  = next;
		}
	}
	
	static int colisiones = 0;
	static int currentIteration;
	
	static class IntHashMap
	{
		 Entry table[];

		 int size;

		 public IntHashMap(int initialCapacity)
		 {
			 table = new Entry[initialCapacity];
		 }

		 public boolean get(int key) 
		 {
			 Entry e = getEntry(key);
			 return e.value;
		 }

		 public boolean containsKey(int key) 
		 {
			 return getEntry(key) != null;
		 }

		 Entry getEntry(int key)
		 {
			 Entry tab[] = table;
			 int hash = key;
			 int index = (hash & 0x7FFFFFFF) % tab.length;
			 if(tab[index] != null && tab[index].iteration != currentIteration)
				 tab[index] = null;
			 for (Entry e = tab[index]; e != null; e = e.next)
			 {
				 if (e.key == key)
					 return e;
			 }
			 return null;
		 }

		 public void put(int key, boolean value)
		 {
			 Entry tab[] = table;
			 int hash = key;
			 int index = (hash & 0x7FFFFFFF) % tab.length;
			 tab[index] = newEntry(key, currentIteration, value, tab[index]);
		 }


		  Entry newEntry(int key, int iteration, boolean value, Entry next) 
		  {
			  return new Entry(key, iteration, value, next);
		  }
	}


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
             
             public Integer nextInteger()
             {
            	 String next = next();
            	 if(next == null)
            		 return null;
            	 return Integer.parseInt(next);
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
	 
	static class ArrayListInt
	{
		int current = 0;
		final int[] values;
		
		ArrayListInt(int tam)
		{
			values = new int[tam];
		}
		
		void add(int n)
		{
			values[current++] = n;
		}
		
		int[] toIntArray()
		{
			int[] n = new int[current];
			for(int i = 0; i < current; i++)
				n[i] = values[i];
			return n;
		}
		
		void clear()
		{
			current = 0;
		}

		public int size()
		{
			return current;
		}
	}
	
	static final int tam = 1 << 12;
	static final int[] cartas = new int[12];
	static int triunfo;
	

	private static boolean puedeCubrir(int i, int j) 
	{
		int valorI = i & 15;
		int valorJ = j & 15;
		int pintaI = i >> 4;
		int pintaJ = j >> 4;
		if(pintaI == pintaJ)
			return valorI > valorJ;
		else if(pintaI == triunfo)
			return true;
		else
			return false;
	}
	
	static IntHashMap dp = new IntHashMap (20000037);
	static int nCartas;
	
	static int hash(int mascaraActual, int mascaraOtro, int enTablero, int porCubrir, boolean cubre, boolean seRindio)
	{
		int hash = 0;
		int primera = 1;
		for(int i = 0; i < nCartas; i++)
		{
			hash *= 5;
			if((mascaraActual & primera) != 0)
				hash += 1;
			else if((mascaraOtro & primera) != 0)
				hash += 2;
			else if((enTablero & primera) != 0)
			{
				if((porCubrir & primera) != 0)
					hash += 3;
				else
					hash += 4;
			}
			primera <<= 1;
		}
		if(cubre)
			hash |= 1L << 29;
		if(seRindio)
			hash |= 1L << 30;
		return hash;
	}
	
	static int[] conteo = new int[1 << 12];
	static boolean[][] tempsTablero = new boolean[1 << 12][];
	
	static boolean[] tempsTablero(int enTablero)
	{
		if(tempsTablero[enTablero] != null)
			return tempsTablero[enTablero];
		boolean[] tT = new boolean[15];
		int primera = enTablero;
		for(int i = 0; primera != 0 && i < nCartas; i++)
		{
			if((primera & 1) != 0)
				tT[cartas[i] & 15] = true;
			primera >>= 1;
		}
		return tempsTablero[enTablero] = tT;
	}
	static boolean dpJuego(int mascaraActual, int mascaraOtro, int enTablero, int porCubrir, boolean cubre, boolean seRindio)
	{
		int e = hash(mascaraActual, mascaraOtro, enTablero, porCubrir, cubre, seRindio);
		if(dp.containsKey(e))
			return dp.get(e);
		if(!cubre)
		{
			if(mascaraActual == 0)
			{
				dp.put(e, true);
				return true;
			}
			if(seRindio)
			{
				int limite = conteo[mascaraOtro] - conteo[porCubrir];
				if(limite != 0)
				{
					int aBotar = 0;
					int botadas = 0;
					boolean[] tempsTablero = tempsTablero(enTablero);
					int primera = mascaraActual;
					for(int i = 0; botadas != limite && i < nCartas; i++)
					{
						if(((primera & 1) != 0) && tempsTablero[cartas[i] & 15])
						{
							aBotar |= 1 << i;
							botadas++;
						}
						primera >>= 1;
					}
					if(dpJuego(mascaraActual & (~(aBotar)), mascaraOtro | enTablero | aBotar, 0, 0, cubre, false))
					{
						dp.put(e, true);
						return true;
					}
				}
				if(dpJuego(mascaraActual, mascaraOtro | enTablero, 0, 0, cubre, false))
				{
					dp.put(e, true);
					return true;
				}
				dp.put(e, false);
				return false;
			}
			else
			{
				boolean[] tempsTablero = tempsTablero(enTablero);
				if(conteo[porCubrir] < conteo[mascaraOtro])
				{
					int primera = 1;
					for(int i = 0; i < nCartas; i++)
					{
						if(((mascaraActual & primera) != 0) && (enTablero == 0 || tempsTablero[cartas[i] & 15]))
						{
							if(dpJuego(mascaraActual & (~primera), mascaraOtro, enTablero | primera, porCubrir  | primera, cubre, false))
							{
								dp.put(e, true);
								return true;
							}
						}
						primera <<= 1;
					}
				}		
				if(enTablero == 0)
				{	
					dp.put(e, false);
					return false;
				}
				if(porCubrir == 0 && !dpJuego(mascaraOtro, mascaraActual, 0, 0, false, false))
				{
					dp.put(e, true);
					return true;
				}
			    if(porCubrir != 0 && !dpJuego(mascaraOtro, mascaraActual, enTablero, porCubrir, !cubre, false))
				{
					dp.put(e, true);
					return true;
				}
				dp.put(e, false);
				return false;
			}
		}
		else
		{
			if(mascaraOtro == 0)
			{
				dp.put(e, false);
				return false;
			}
			int tamCubrir = conteo[porCubrir];
			if(tamCubrir == 0)
			{
				boolean valor = !dpJuego(mascaraOtro, mascaraActual, enTablero, porCubrir, !cubre, false);
				dp.put(e, valor);
				return valor;
			}
			int primeraCubrir = Integer.numberOfTrailingZeros(porCubrir);
			int primera = 1;
			for(int i = 0; i < nCartas; i++)
			{
				if((primera & mascaraActual) != 0)
					if(puedeCubrir(cartas[i], cartas[primeraCubrir]))
						if(dpJuego(mascaraActual & (~primera), mascaraOtro, enTablero | primera, porCubrir & (~(1 << primeraCubrir)), cubre, false))
						{
							dp.put(e, true);
							return true;
						}
				primera <<= 1;
			}
			if(tamCubrir != 0 && !dpJuego(mascaraOtro, mascaraActual, enTablero, porCubrir, !cubre, true))
			{
				dp.put(e, true);
				return true;
			}
			dp.put(e, false);
			return false;
		}
	}
	
	static final String suits = "SCDH";
	static final String ranks = "6789TJQKA";
	
	public static void main(String[] args) throws FileNotFoundException
	{
		for(int i = 0; i < conteo.length; i++)
			conteo[i] = Integer.bitCount(i);
		Scanner sc = new Scanner();
		while(true)
		{
			Integer aN = sc.nextInteger();
			if(aN == null)
				return;
			int bN = sc.nextInt();
			triunfo = suits.indexOf(sc.next());
			currentIteration++;
			colisiones = 0;
			for(int i = 0; i < aN + bN; i++)
			{
				String carta = sc.next();
				cartas[i] = (ranks.indexOf(carta.charAt(0)) + 6) | (suits.indexOf(carta.charAt(1)) << 4);
			}
			nCartas = aN + bN;
			int lim = 1 << nCartas;
			for(int i = 0; i < lim; i++)
				tempsTablero[i] = null;
			String res = dpJuego((1 << aN) - 1, ((1 << bN) - 1) << aN, 0, 0, false, false) ? "FIRST" : "SECOND";
			System.out.println(res);
		}
	}
}

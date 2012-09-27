import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.TreeSet;


public class Error 
{
	static class SuperScanner
	{
		char[] buffer;
		InputStreamReader isr;
		int p;
		int start;

		public SuperScanner()
		{
			buffer = new char[40000001];
			isr = new InputStreamReader(System.in);         
			read(0);
		}

		void read(int init)
		{
			try
			{
				int tam;
				if((tam = isr.read(buffer, init, buffer.length - init)) < buffer.length - init)
				{
					if(tam < 0)
						tam = 0;
					if(tam == buffer.length)
						throw(new RuntimeException());
					buffer[tam] = '\0';
				}
			}
			catch(Exception e)
			{
				throw(new RuntimeException());
			}
		}

		public void readNext()
		{
			int tam = buffer.length;
			int pos = p;
			while(pos < tam && buffer[pos] <= ' ')
				pos++;
			if(pos == tam)
			{
				read(0);
				readNext();
				return;
			}
			start = pos;
			while(pos < tam && buffer[pos] > ' ')
				pos++;
			if(pos == tam)
			{
				System.arraycopy(buffer, start, buffer, 0, buffer.length - start);
				read(buffer.length - start);
				p = start;
				readNext();
				return;
			}
			p = pos;
		}

		public String next()
		{
			readNext();
			return new String(buffer, start, p - start);
		}

		static final int v2 = 27 * 27;
		
		public int nextCiudad()
		{
			readNext();
			return v2 * (buffer[start] - 'A') + 27 * (buffer[start + 1] - 'A') + (buffer[start + 2] - 'A');
		}


		public long nextLong()
		{
			readNext();
			int d = start;
			int pos = p;
			long r = 0;
			boolean n = buffer[d] == '-';
			if(n)
				d++;
			r -= buffer[d++] - '0';
			while (d < pos && (r = (r << 1) + (r << 3)) <= 0)
				r -= buffer[d++] - '0';
			return n ? r : -r;
		}

		public int nextInt()
		{
			return (int) nextLong();
		}

		public double nextDouble()
		{
			return Double.parseDouble(next());
		}

		public String nextLine()
		{
			int tam = buffer.length;
			int pos = p;
			while(pos < tam && buffer[pos] != '\n')
				pos++;
			if(pos == tam)
			{
				read(0);
				return nextLine();
			}
			start = ++pos;
			while(pos < tam && buffer[pos] != '\n')
				pos++;
			if(pos >= tam)
			{
				System.arraycopy(buffer, start, buffer, 0, buffer.length - start);
				read(buffer.length - start);
				return nextLine();
			}
			p = pos;
			return new String(buffer, start, p - start);
		}

		public boolean EOF()
		{
			int tam = buffer.length;
			int pos = p;
			while(pos < tam && buffer[pos] <= ' ')
				if(buffer[pos++] == '\0')
					return true;
			pos++;
			if(pos >= tam)
			{
				if(p == 0)
					return false;
				System.arraycopy(buffer, p, buffer, 0, buffer.length - p);
				read(buffer.length - p);
				p = 0;
				return EOF();
			}
			return false;
		}
	}

	static class Arista
	{
		Ciudad b;
		boolean existe = false;
		Arista siguiente;

		public Arista(Ciudad b)
		{
			this.b = b;
		}
	}

	static class Ciudad
	{
		int t;
		Arista aristas = null;
		boolean visitado = false;
		int numero = 0;
		TreeSet <Integer> existentes = null;
		int lastSeen = -1;
		int bestScore = 0;

		Ciudad(int n, int test)
		{
			numero = n;
			t = test;
		}

		@Override
		public int hashCode() 
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + numero;
			return result;
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
			Ciudad other = (Ciudad) obj;
			if (numero != other.numero)
				return false;
			return true;
		}
	}

	static Ciudad[] ciudades = new Ciudad[20000];
	static int test = 0;

	static Ciudad darCiudad(int val)
	{
		if(ciudades[val] != null && ciudades[val].t == test)
			return ciudades[val];
		Ciudad nuevo = new Ciudad(val, test);
		ciudades[val] = nuevo;
		return nuevo;
	}


	public static void main(String[] args) throws FileNotFoundException
	{
		SuperScanner sc = new SuperScanner();
		int tests = sc.nextInt();
		int[] aristas = new int[200000];
		int[] eAristas = new int[200000];
		for(test = 0; test < tests; test++)
		{
			int conexiones = sc.nextInt();
			int limite = conexiones << 1;
			for(int i = 0; i < limite; i += 2)
			{
				int cA = sc.nextCiudad();
				int cB = sc.nextCiudad();
				aristas[i] = (cA << 16) + cB;
				aristas[i + 1] = (cB << 16) + cA;
			}
			int faulty = sc.nextInt();
			int start = sc.nextCiudad();
			int last = start;
			for(int i = 0; i < faulty; i++)
			{
				int t = sc.nextCiudad();
				eAristas[i] = (last << 16) + t;
				last = t;
			}
			Arrays.sort(aristas, 0, limite);
			Arrays.sort(eAristas, 0, faulty);
			int faultyActual = 0;
			for(int i = 0; i < limite; i++)
			{
				int actual = aristas[i];
				Ciudad a = darCiudad(actual >> 16);
				Ciudad b = darCiudad((short) actual);
				Arista ab = new Arista(b);
				if(faultyActual < faulty && eAristas[faultyActual] == actual)
				{
					ab.existe = true;
					while(faultyActual < faulty && eAristas[faultyActual] == actual)
						faultyActual++;
				}
				ab.siguiente = a.aristas;
				a.aristas = ab;
			}
			System.out.println(dijkstra(darCiudad(start), darCiudad(last)));
		}
	}

	static int[] t1 = new int[100000];
	static int[] t2 = new int[100000];
	
	private static int dijkstra(Ciudad start, Ciudad last)
	{
		int[] current = t1;
		int[] next = t2;
		Ciudad[] cities = ciudades;
		current[0] = start.numero;
		start.lastSeen = 0;
		start.bestScore = 0;
		int sizeCurrent = 1;
		int currentIteration = 0;
		while(true)
		{
			int sizeNext = 0;
			for(int i = 0; i < sizeCurrent; i++)
			{
				Ciudad currentCity = cities[current[i]];
				if(currentCity == last)
					return currentCity.bestScore;
				Arista actual = currentCity.aristas;
				while(actual != null)
				{
					int nextScore = currentCity.bestScore + (actual.existe ? 0 : 1);
					if(actual.b.lastSeen == -1)
					{
						actual.b.lastSeen = currentIteration;
						actual.b.bestScore = nextScore;
						next[sizeNext++] = actual.b.numero;
					}
					else if(actual.b.lastSeen == currentIteration && actual.b.bestScore > nextScore)
						actual.b.bestScore = nextScore;
					actual = actual.siguiente;
				}
			}
			currentIteration++;
			int[] t = next;
			next = current;
			current = t;
			sizeCurrent = sizeNext;
		}
	}
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class R2D2 
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
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
	}
	
	@SuppressWarnings("unchecked")
	static PriorityQueue <Integer> [] estructura = new PriorityQueue[1001];
	static int k;
	static int cuenta;

	private static void agregar(int valor) 
	{
		int mejorIndice = -1;
		int mejorValor = Integer.MAX_VALUE;
		for(int i = valor; i <= k; i++)
		{
			if(estructura[i].isEmpty())
				continue;
			if(estructura[i].peek() < mejorValor)
			{
				mejorValor = estructura[i].peek();
				mejorIndice = i;
			}
		}
		if(mejorIndice == -1)
		{
			estructura[k].add(cuenta++);
			agregar(valor);
			return;
		}
		int c = estructura[mejorIndice].poll();
		estructura[mejorIndice - valor].add(c);
	}
	
	private static void agregar(int cuantos, int valor) 
	{
		TreeMap <Integer, Integer> mapa = new TreeMap <Integer, Integer> ();
		for(int i = valor; i <= k; i++)
		{
			if(estructura[i].isEmpty())
				continue;
			mapa.put(estructura[i].peek(), i);
		}
		for(int i = 0; i < cuantos; i++)
		{
			Map.Entry<Integer, Integer> e = mapa.pollFirstEntry();
			if(e == null)
			{
				estructura[k - valor].add(cuenta++);
				if(k - valor >= valor)
					mapa.put(estructura[k - valor].peek(), k - valor);
			}
			else
			{
				int indice = estructura[e.getValue()].poll();
				if(!estructura[e.getValue()].isEmpty())
					mapa.put(estructura[e.getValue()].peek(), e.getValue());
				if(!estructura[e.getValue() - valor].isEmpty())
					mapa.remove(estructura[e.getValue() - valor].peek());
				estructura[e.getValue() - valor].add(indice);
				if(e.getValue() - valor >= valor)
					mapa.put(estructura[e.getValue() - valor].peek(), e.getValue() - valor);
			}
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		for(int i = 0; i <= 1000; i++)
			estructura[i] = new PriorityQueue <Integer> ();
		int inicio = 0;
		while(true)
		{
			Integer k1 = sc.nextInteger();
			if(k1 == null)
				return;
			k = k1;
			for(int i = 0; i <= k; i++)
				estructura[i].clear();
			int n = sc.nextInt();
			int acumulado = 0;
			int containers = 0;
			cuenta = 0;
			while(containers < n)
			{
				String siguiente = sc.next();
				int numero = 1;
				int valor = 0;
				if(siguiente.equals("b"))
				{
					numero = sc.nextInt();
					valor = sc.nextInt();
				}
				else
					valor = Integer.parseInt(siguiente);
				if(numero == 1)
					agregar(valor);
				else
					agregar(numero, valor);
				containers += numero;
				acumulado += valor * numero;
			}
			int usados = cuenta;
			int waste = usados * k - acumulado;
			if(inicio++ != 0)
				System.out.println();
			System.out.println(usados + " " + waste);
		}
	}
}
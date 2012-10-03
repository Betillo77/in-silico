import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Cactus 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		String nextLine()
		{
			try { return br.readLine(); } catch(Exception e) { throw new RuntimeException(e); }
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
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		Integer nextInteger()
		{
			String next = next();
			if(next == null)
				return null;
			return Integer.parseInt(next);
		}
		
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
	}

	
	static class Arista
	{
		Nodo a;
		Nodo b;
		Arista pareja;
		boolean ciclo = false;
		
		public Arista(Nodo aA, Nodo bB) 
		{
			a = aA;
			b = bB;
		}
	}
	
	static class Nodo
	{
		Arista anterior;
		int numero;
		boolean visitado;
		
		ArrayList <Arista> aristas = new ArrayList <Arista> ();
	}
	
	static class Entrada
	{
		Arista anterior;
		Nodo actual;
		
		Entrada(Arista anterior, Nodo actual)
		{
			this.anterior = anterior;
			this.actual = actual;
		}
	}
	static BigInteger DFS(Nodo inicial)
	{
		ArrayDeque <Entrada> cola = new ArrayDeque <Entrada> ();
		BigInteger cuentaActual = BigInteger.ONE;
		cola.add(new Entrada(null, inicial));
		while(!cola.isEmpty())
		{
			Entrada eActual = cola.pollFirst();
			Nodo actual = eActual.actual;
			if(actual.visitado)
			{
				Arista current = eActual.anterior;
				if(current.ciclo)
					continue;
				int cuenta = 0;
				do
				{
					if(current.ciclo)
						return null;
					cuenta++;
					current.ciclo = true;
					current.pareja.ciclo = true;
					current = current.a.anterior;
				}
				while(current != actual.anterior);
				cuentaActual = cuentaActual.multiply(BigInteger.valueOf(cuenta + 1));
			}
			else
			{
				actual.anterior = eActual.anterior;
				for(Arista a : actual.aristas)
				{
					if(actual.anterior != null && a.b == actual.anterior.a)
						continue;
					cola.addFirst(new Entrada(a, a.b));
				}
			}
			actual.visitado = true;
		}
		return cuentaActual;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		Nodo[] nodos = new Nodo[20000];
		boolean inicio = false;
		while(true)
		{
			Integer n = sc.nextInteger();
			if(n == null)
				return;
			final int ni = n;
			for(int i = 0; i < ni; i++)
			{
				nodos[i] = new Nodo();
				nodos[i].numero = i + 1;
			}
			int m = sc.nextInt();
			for(int i = 0; i < m; i++)
			{ 
				int k = sc.nextInt();
				int anterior = -1;
				for(int j = 0; j < k; j++)
				{
					int siguiente = sc.nextInt();
					if(anterior == -1)
						anterior = siguiente;
					else
					{
						Arista a = new Arista(nodos[anterior - 1], nodos[siguiente - 1]);
						Arista b = new Arista(nodos[siguiente - 1], nodos[anterior - 1]);
						a.pareja = b;
						b.pareja = a;
						nodos[anterior - 1].aristas.add(a);
						nodos[siguiente - 1].aristas.add(b);
						anterior = siguiente;
					}
				}
			}
			BigInteger res = DFS(nodos[0]);
			if(res == null)
				res = BigInteger.ZERO;
			for(int i = 0; i < n; i++)
				if(!nodos[i].visitado)
					res = BigInteger.ZERO;
			if(!inicio)
				inicio = true;
			else
				System.out.println();
			System.out.println(res);
		}
	}

}

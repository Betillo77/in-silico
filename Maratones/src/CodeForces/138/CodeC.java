import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class CodeC 
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

	static class Nodo
	{
		char valor;
		int numero = -1;
		StringBuilder sb = new StringBuilder();
	}
	
	static Nodo peekNext(ListIterator <Nodo> it)
	{
		if(it.hasNext())
		{
			Nodo r = it.next();
			it.previous();
			return r;
		}
		return null;
	}
	static void arreglar(Nodo current, char inicio, int cuenta, ListIterator <Nodo> it)
	{
		Nodo next = it.next();
		int posibleCuenta = 0;
		Nodo posibleInterno = null;
		if(next.numero != -1)
		{
			posibleCuenta = next.numero;
			posibleInterno = next;
			if(!it.hasNext())
				return;
			Nodo nP = peekNext(it);
			if(nP != null && next.valor == contrario(inicio))
				it.remove();
			next = it.next();
			if(nP != null && next.valor == contrario(inicio))
				it.remove();
		}
		if(next.valor == contrario(inicio))
		{
			current.numero = posibleCuenta + cuenta;
			current.valor = 0;
			current.sb.append(inicio);
			if(posibleInterno != null)
				current.sb.append(posibleInterno.sb);
			current.sb.append(next.valor);
			if(it.hasPrevious())
				it.previous();
			if(it.hasPrevious())
				it.previous();
		}
		else
			it.previous();
	}
	
	static void arreglarNumero(Nodo current, ListIterator <Nodo> it)
	{
		Nodo next = it.next();
		if(next.numero != -1)
		{
			current.numero += next.numero;
			current.sb.append(next.sb);
			it.remove();
			it.previous();
			return;
		}
		else
			it.previous();
	}
	
	static void iterar(LinkedList <Nodo> nodos)
	{
		ListIterator <Nodo> it = nodos.listIterator();
		while(it.hasNext())
		{
			Nodo current = it.next();
			if(current.sb.toString().equals("(())"))
				System.out.println();
			if(current.valor == '[' && it.hasNext())
				arreglar(current, '[', 1, it);
			else if(current.valor == '(' && it.hasNext())
				arreglar(current, '(', 0, it);
			else if(current.numero != -1 && it.hasNext())
			{
				if(it.hasNext())
				{
					Nodo siguiente = it.next();
					if(siguiente.valor == ']' || siguiente.valor == ')')
					{
						it.previous();
						if(it.hasPrevious())
						{
							Nodo anterior = it.previous();
							if(anterior == current && it.hasPrevious())
								anterior = it.previous();
							if(anterior.valor == contrario(siguiente.valor))
							{
								it.previous();
								continue;
							}
							else
								it.next();
						}
					}
					else
						it.previous();
				}
				arreglarNumero(current, it);
			}
		}
	}
	
	private static char contrario(char valor) 
	{
		if(valor == ']')
			return '[';
		if(valor == '[')
			return ']';
		if(valor == '(')
			return ')';
		return '(';
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		String s = sc.next();
		LinkedList <Nodo> lista = new LinkedList <Nodo> ();
		for(char c : s.toCharArray())
		{
			Nodo nuevo = new Nodo();
			nuevo.valor = c;
			lista.add(nuevo);
		}
		iterar(lista);
		iterar(lista);
		int mejorCuenta = -1;
		Nodo mejor = null;
		for(Nodo n : lista)
		{
			if(n.numero != -1 && n.numero > mejorCuenta)
			{
				mejorCuenta = n.numero;
				mejor = n;
			}
		}
		if(mejorCuenta == -1)
			mejorCuenta = 0;
		System.out.println(mejorCuenta);
		if(mejor == null)
			System.out.println();
		else
			System.out.println(mejor.sb);
	}
}
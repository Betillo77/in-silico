import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class D
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
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		int[] readNextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
	}
	
	static class Numero implements Comparable <Numero>
	{
		int n;
		int indice;
		
		Numero(int nT, int i)
		{
			n = nT;
			indice = i;
		}

		@Override
		public int compareTo(Numero o) 
		{
			if(n == o.n)
				return indice - o.indice;
			return n - o.n;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		int[] medianas = new int[10000];
		for(int caso = 1; caso <= casos; caso++)
		{
			int numero = sc.nextInt();
			int n = sc.nextInt();
			TreeSet <Numero> mayores = new TreeSet <Numero> ();
			TreeSet <Numero> menores = new TreeSet <Numero> ();
			menores.add(new Numero(sc.nextInt(), 0));
			int nMedianas = 0;
			medianas[nMedianas++] = menores.last().n;
			for(int i = 1; i < n; i++)
			{
				int siguiente = sc.nextInt();
				if(i == 1)
					menores.add(new Numero(siguiente, i));
				else
				{
					if(mayores.first().n > siguiente)
						menores.add(new Numero(siguiente, i));
					else
						mayores.add(new Numero(siguiente, i));
				}
				if((i & 1) == 1)
				{
					while(mayores.size() > menores.size())
						menores.add(mayores.pollFirst());
					while(mayores.size() < menores.size())
						mayores.add(menores.pollLast());
				}
				else
				{
					while(mayores.size() > menores.size() - 1)
						menores.add(mayores.pollFirst());
					while(menores.size() > mayores.size() + 1)
						mayores.add(menores.pollLast());
					medianas[nMedianas++] = menores.last().n;
				}
			}
			int cuenta = 0;
			System.out.print(numero + " " + nMedianas);
			for(int i = 0; i < nMedianas; i++)
			{
				if(cuenta == 0)
				{
					System.out.println();
					System.out.print(medianas[i]);
				}
				else
					System.out.print(" " + medianas[i]);
				cuenta++;
				cuenta %= 10;
			}
			System.out.println();
		}
	}
}
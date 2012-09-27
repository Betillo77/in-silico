import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;


public class Arithmetically 
{	
	static class Scanner
	{
		BufferedReader br;
		StringTokenizer st;
		
		public Scanner()
		{
	    	System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
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
				return !st.hasMoreTokens();
			}
			catch(Exception e) { throw new RuntimeException(); }
		}
	}
	
	static class Entrada implements Comparable <Entrada>
	{
		boolean[] usados;
		int total;
		
		public Entrada(int a, int n, Entrada anterior)
		{
			usados = new boolean[4];
			if(anterior != null)
				for(int i = 0; i < 4; i++)
					usados[i] = anterior.usados[i];
			total = a;
			usados[n] = true;
		}

		public Entrada(int a, Entrada e1, Entrada e) {			
			usados = new boolean[4];
			for(int i = 0; i < 4; i++)
				usados[i] = e1.usados[i] || e.usados[i];
			total = a;
		}

		@Override
		public int hashCode() 
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + total;
			result = prime * result + Arrays.hashCode(usados);
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
			Entrada other = (Entrada) obj;
			if (total != other.total)
				return false;
			if (!Arrays.equals(usados, other.usados))
				return false;
			return true;
		}

		@Override 
		public int compareTo(Entrada o) 
		{
			return total - o.total;
		}
		
		
	}

	static final HashSet <Entrada> todas = new HashSet <Entrada> ();
	
	public static void generar(int[] numeros)
	{
		todas.add(new Entrada(numeros[0], 0, null));
		todas.add(new Entrada(numeros[1], 1, null));
		todas.add(new Entrada(numeros[2], 2, null));
		todas.add(new Entrada(numeros[3], 3, null));
		boolean cambio = true;
		while(cambio)
		{
			ArrayList <Entrada> nuevas = new ArrayList <Entrada> ();
			for(Entrada e : todas)
			{
				for(Entrada e1 : todas)
				{
					boolean paila = false;
					for(int i = 0; i < 4; i++)
						if(e.usados[i] && e1.usados[i])
							paila = true;
					if(paila)
						continue;
					nuevas.add(new Entrada(e.total + e1.total, e1, e));
					nuevas.add(new Entrada(e.total - e1.total, e1, e));
					nuevas.add(new Entrada(e.total * e1.total, e1, e));
					if(e1.total != 0 && (e.total % e1.total == 0))
						nuevas.add(new Entrada(e.total / e1.total, e1, e));
						nuevas.add(new Entrada(e1.total - e.total, e1, e));
						if(e.total != 0 && (e1.total % e.total == 0))
							nuevas.add(new Entrada(e1.total / e.total, e1, e));
				}
			}
			int tamAnterior = todas.size();
			todas.addAll(nuevas);
			if(todas.size() == tamAnterior)
				cambio = false;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			int[] numeros = new int[] {sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()};
			boolean ceros = true;
			for(int j : numeros)
				if(j != 0)
					ceros = false;
			if(ceros)
				return;
			todas.clear();
			generar(numeros);
			ArrayList <Entrada> t = new ArrayList <Entrada> ();
			for(Entrada e : todas)
			{
				boolean posible = true;
				for(int i = 0; i < 4; i++)
					if(!e.usados[i])
						posible = false;
				if(posible)
					t.add(e);
			}
			int anterior = Integer.MIN_VALUE;
			int tamSeguidas = 0;
			int mejorSeguidas = 0;
			int inicio = 0;
			int fin = 0;
			Collections.sort(t);
			for(Entrada e : t)
			{
				if(e.total == anterior)
					continue;
				else if(e.total == anterior + 1)
				{
					tamSeguidas++;
					if(tamSeguidas >= mejorSeguidas)
					{
						mejorSeguidas = tamSeguidas;
						inicio = e.total - tamSeguidas + 1;
						fin = e.total;
					}
				}
				else
				{
					tamSeguidas = 1;
					if(tamSeguidas >= mejorSeguidas)
					{
						mejorSeguidas = tamSeguidas;
						inicio = e.total - tamSeguidas + 1;
						fin = e.total;
					}
				}
				anterior = e.total;
			}
			System.out.println("Case " + caso++ + ": " + inicio + " to " + fin);
		}
	}
}
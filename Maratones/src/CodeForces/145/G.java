import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class G
{
	static boolean DEBUG = false;
	
	static class Scanner
	{
		
		BufferedReader br;
		StringTokenizer st;
		BufferedWriter bw;

		public Scanner()
		{ 
			if(DEBUG)
			{
				br = new BufferedReader(new InputStreamReader(System.in));
				bw = new BufferedWriter(new OutputStreamWriter(System.out));
			}
			else
				try 
				{
					br = new BufferedReader(new FileReader("input.txt"));
					bw = new BufferedWriter(new FileWriter("output.txt"));
				} 
				catch (Exception e) 
				{
				}
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
		
		public void print(String s)
		{
			try 
			{
				bw.write(s);
			} 
			catch (IOException e) 
			{
			}
		}
		
		public void println(String s)
		{
			print(s);
			print("\n");
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		boolean faltaUno = false;
		if((n & 1) == 1)
		{
			faltaUno = true;
			n--;
		}
		ArrayList <String> results = new ArrayList <String> ();
		LinkedList <Integer> inicial = new LinkedList <Integer> ();
		boolean[] quienes = new boolean[n];
		for(int i = 1; i <= n / 2; i++)
		{
			inicial.add(i);
			quienes[i + n / 2 - 1] = true;
		}
		if(faltaUno)
			inicial.add(n + 1);
		results.add(imprimir(inicial, sc));
		if(faltaUno)
			inicial.pollLast();
		LinkedList < LinkedList<Integer> > actuales = new LinkedList < LinkedList <Integer> > ();
		actuales.add(inicial);
		while(actuales.size() != n / 2)
		{
			LinkedList < LinkedList<Integer> > nuevos = new LinkedList < LinkedList <Integer> > ();
			LinkedList <Integer> arriba = new LinkedList <Integer> ();
			for(LinkedList <Integer> l : actuales)
				dividir(l, nuevos, arriba, n);
			LinkedList <Integer> abajo = new LinkedList <Integer> ();
			generarAbajo(arriba, abajo);
			int cuentaArriba = 0;
			int cuentaAbajo = 0;
			for(int i : arriba)
			{
				if(!quienes[i - 1])
					cuentaAbajo++;
			}
			for(int i : abajo)
			{
				if(!quienes[i - 1])
					cuentaArriba++;
			}
			if(faltaUno && cuentaArriba >= cuentaAbajo)
			{
				arriba.add(n + 1);
				for(int i : abajo)
					quienes[i - 1] = true;
					
			}
			else if(faltaUno)
			{
				for(int i : arriba)
					quienes[i - 1] = true;
			}
			results.add(imprimir(arriba, sc));
			actuales = nuevos;
		}
		boolean faltabaUno = faltaUno;
		faltaUno = false;
		for(int i = 0; i < n; i++)
			if(!quienes[i])
				faltaUno = true;
		if(faltabaUno && faltaUno)
			results.add("1 " + (n + 1));
		sc.println(results.size() + "");
		for(String s : results)
			sc.println(s);
		try 
		{
			sc.bw.flush();
			sc.bw.close();
		} 
		catch (Exception e) 
		{
		}
	}

	private static void generarAbajo(LinkedList<Integer> arriba, LinkedList<Integer> abajo) 
	{
		for(int i : arriba)
		{
			if(i > arriba.size())
				abajo.add(i - arriba.size());
			else
				abajo.add(i + arriba.size());
		}
	}

	private static void dividir(LinkedList<Integer> l, LinkedList<LinkedList<Integer>> nuevos, LinkedList<Integer> arriba, int n) 
	{
		if(l.size() == 1)
		{
			nuevos.add(l);
			arriba.add(l.get(0));
		}
		else
		{
			int mitad = l.size() / 2;
			LinkedList <Integer> a = new LinkedList <Integer> ();
			if((l.size() & 1) == 1)
				mitad++;
			for(int i = 0; i < mitad; i++)
				a.add(l.pollFirst());
			arriba.addAll(a);
			for(int i : l)
				arriba.add(i + n / 2);
			nuevos.add(a);
			nuevos.add(l);
		}
	}

	private static String imprimir(LinkedList <Integer> l, Scanner sc)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(l.size());
		for(int i : l)
			sb.append(" " + i);
		return sb.toString();
	}
}
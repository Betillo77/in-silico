import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;


public class Decomp 
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

	static HashMap <String, String> mapa = new HashMap <String, String> ();
	static HashSet <String> todos = new HashSet <String> ();
	static HashSet <String> palabras = new HashSet <String> (); 
	static ArrayList <String> seguidos = new ArrayList <String> ();
	
	public static String convertirTam(String n, int tam)
	{
		while(n.length() < tam)
			n = "0" + n;
		return n;
	}
	
	static int tamActual;
	static int nActual;
	
	private static void agregar(String s) 
	{
		if(todos.contains(s))
			return;
		String nA = (nActual + 1) + "";
		if(nA.length() > tamActual)
		{
			tamActual++;
			HashMap <String, String> nuevo = new HashMap <String, String> ();
			for(Map.Entry<String, String> e : mapa.entrySet())
				nuevo.put(convertirTam(e.getKey(), tamActual), e.getValue());
			mapa = nuevo;
		}
		mapa.put(convertirTam(nActual + "", tamActual), s);
		seguidos.add(s);
		todos.add(s);
		nActual++;
	}
	
	public static void arreglarTam()
	{
		String nA = (nActual - 1) + "";
		String nN = (nActual) + "";
		if(nA.length() != nN.length())
		{
			tamActual++;
			HashMap <String, String> nuevo = new HashMap <String, String> ();
			for(Map.Entry<String, String> e : mapa.entrySet())
				nuevo.put(convertirTam(e.getKey(), tamActual), e.getValue());
			mapa = nuevo;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			String entrada = sc.next();
			if(entrada.equals("0"))
				return;
			mapa.clear();
			seguidos.clear();
			todos.clear();
			palabras.clear();
			int n = sc.nextInt();
			int tam = ((n - 1) + "").length();
			for(int i = 0; i < n; i++)
			{
				String siguiente = sc.next();
				mapa.put(convertirTam(i + "", tam), siguiente);
				seguidos.add(siguiente);
				todos.add(siguiente);
				palabras.add(siguiente);
			}
			tamActual = tam;
			nActual = n;
			String anterior = "";
			System.out.print("Case " + caso++ + ": ");
			int iter = 0;
			while(!entrada.isEmpty())
			{
				if(iter++ == 1)
					arreglarTam();
				String llave = entrada.substring(0, Math.min(entrada.length(), tamActual));
				llave = convertirTam(llave, tamActual);
				entrada = entrada.substring(Math.min(entrada.length(), tamActual));
				String valor = "";
				if(!mapa.containsKey(llave))
				{
					valor = anterior + anterior.charAt(0);
					System.out.print(valor);
					agregar(valor);
					
				}
				else
				{
					valor = mapa.get(llave);
					System.out.print(valor);
				}
				if(!anterior.isEmpty())
					agregar(anterior + valor.charAt(0));
				anterior = valor;
			}
			System.out.println();
		}
	}
}
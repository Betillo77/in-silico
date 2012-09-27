import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Puzzle
{
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
     }
	 
	static class Letra
	{
		char l;
		ArrayList <Palabra> palabras = new ArrayList <Palabra> ();
		
		public Letra(char lT)
		{
			l = lT;
		}
	}
	
	static class Palabra
	{
		char[] p;
		boolean visitada;
		ArrayList <Palabra> adjacentes = new ArrayList <Palabra> ();
		ArrayList <Letra> letras;

		public Palabra(String pS) 
		{
			p = pS.toCharArray();
		}
	}
	
	static Letra[][] tablero;
	static Palabra[] palabras;
	static ArrayList < ArrayList <Palabra> > faltantes = new ArrayList < ArrayList <Palabra> > ();
	static final int[][] deltas = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
	
	static ArrayList <Letra> buscar(int i, int j, Palabra p)
	{
		final int tam = p.p.length;
		for(int[] d : deltas)
		{
			ArrayList <Letra> actuales = new ArrayList <Letra> ();
			int iA = i;
			int jA = j;
			for(int act = 0; act < tam; act++)
			{
				if(iA < 0 || jA < 0 || iA >= tablero.length || jA >= tablero[0].length || tablero[iA][jA].l != p.p[act])
					break;
				actuales.add(tablero[iA][jA]);
				iA += d[0];
				jA += d[1];
			}
			if(actuales.size() == p.p.length)
			{
				for(Letra l : actuales)
					l.palabras.add(p);
				p.letras = actuales;
				return actuales;
			}
		}
		return null;
	}
	
	static void asignarPalabras()
	{
		faltantes.clear();
		for(int i = 0; i < 26; i++)
			faltantes.add(new ArrayList <Palabra> ());
		for(Palabra p : palabras)
			faltantes.get(p.p[0] - 'A').add(p);
		for(int i = 0; i < tablero.length; i++)
			for(int j = 0; j < tablero[0].length; j++)
			{
				for(int k = 0; k < faltantes.get(tablero[i][j].l - 'A').size(); k++)
				{
					ArrayList <Letra> ls = buscar(i, j, faltantes.get(tablero[i][j].l - 'A').get(k));
					if(ls != null)
					{
						faltantes.get(tablero[i][j].l - 'A').remove(k);
						k--;
					}
				}
			}
		for(Palabra p : palabras)
			for(Letra l : p.letras)
				for(Palabra p1 : l.palabras)
					if(p1 != p)
						p.adjacentes.add(p1);
	}
	
	static void bfs(Palabra p)
	{
		if(p.visitada)
			return;
		p.visitada = true;
		for(Palabra p1 : p.adjacentes)
			bfs(p1);
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			int ps = sc.nextInt();
			if(n == 0 && m == 0 && ps == 0)
				return;
			tablero = new Letra[n][m];
			for(int i = 0; i < n; i++)
			{
				String t = sc.nextLine().trim().toUpperCase();
				for(int j = 0; j < m; j++)
					tablero[i][j] = new Letra(t.charAt(j));
			}
			palabras = new Palabra[ps];
			for(int i = 0; i < ps; i++)
				palabras[i] = new Palabra(sc.nextLine().trim().toUpperCase());
			asignarPalabras();
			boolean paila = false;
			for(Palabra p : palabras)
			{
				for(Palabra p1 : palabras)
					p1.visitada = false;
				p.visitada = true;
				for(Palabra p1 : palabras)
					if(!p1.visitada)
					{
						bfs(p1);
						break;
					}
				for(Palabra p1 : palabras)
					if(!p1.visitada)
					{
						paila = true;
						break;
					}
				if(paila)
					break;
			}
			System.out.println(!paila ? "Yes" : "No");
		}
	}
}
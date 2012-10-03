import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Go 
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
	
	static boolean booleanEquals(Boolean a, Boolean b)
	{
		if(a == null)
			return b == null;
		if(b == null)
			return a == null;
		return a.booleanValue() == b.booleanValue();
	}
	
	static class Interseccion
	{
		Boolean deQuien = null;
		Segmento segmento = null;
		ArrayList <Interseccion> adjacentes = new ArrayList <Interseccion> ();
		
		public boolean rodeado(boolean dQ) 
		{
			boolean si = true;
			for(Interseccion i : adjacentes)
			{
				if(i.segmento == segmento)
					continue;
				si = si && booleanEquals(i.deQuien, dQ);
			}
			return si;
		}

		public boolean tieneVecino(boolean dQ)
		{
			int cuenta = 0;
			for(Interseccion i : adjacentes)
			{
				if(i.segmento == segmento)
					continue;
				if(booleanEquals(i.deQuien, dQ))
					cuenta++;
			}
			return cuenta > 0;
		}
	}

	static class Segmento
	{
		Boolean deQuien;
		ArrayList <Interseccion> intersecciones = new ArrayList <Interseccion> ();
		
		public Segmento(Boolean dQ) 
		{
			deQuien = dQ;
		}

		boolean rodeado(boolean deQuien)
		{
			boolean si = true;
			for(Interseccion i : intersecciones)
				si = si && i.rodeado(deQuien);
			int cuenta = 0;
			if(si)
				for(Interseccion i : intersecciones)
					if(i.tieneVecino(deQuien))
						cuenta++;
			return si && cuenta > 0;
		}
	}
	
	static void floodFill(int i, int j, Interseccion[][] tablero, boolean[][] visitados, Boolean deQuien, Segmento actual)
	{
		if(i < 0 || j < 0 || i >= tablero.length || j >= tablero[0].length || visitados[i][j] || !booleanEquals(deQuien, tablero[i][j].deQuien))
			return;
		visitados[i][j] = true;
		tablero[i][j].segmento = actual;
		actual.intersecciones.add(tablero[i][j]);
		floodFill(i + 1, j, tablero, visitados, deQuien, actual);
		floodFill(i - 1, j, tablero, visitados, deQuien, actual);
		floodFill(i, j + 1, tablero, visitados, deQuien, actual);
		floodFill(i, j - 1, tablero, visitados, deQuien, actual);
	}
	
	static ArrayList <Segmento> darSegmentos(Interseccion[][] tablero)
	{
		boolean[][] visitados = new boolean[tablero.length][tablero[0].length];
		ArrayList <Segmento> r = new ArrayList <Segmento> ();
		for(int i = 0; i < tablero.length; i++)
			for(int j = 0; j < tablero[0].length; j++)
				if(!visitados[i][j])
				{
					Segmento actual = new Segmento(tablero[i][j].deQuien);
					floodFill(i, j, tablero, visitados, tablero[i][j].deQuien, actual);
					r.add(actual);
				}
		return r;
	}

	static void organizarTablero(Interseccion[][] tablero, boolean quien) 
	{		
		ArrayList <Segmento> segmentos = darSegmentos(tablero);
		for(Segmento s : segmentos)
		{
			if(booleanEquals(s.deQuien, !quien) && s.rodeado(quien))
			{
				for(Interseccion i : s.intersecciones)
					i.deQuien = null;
				s.deQuien = null;
			}
		}
		for(Segmento s : segmentos)
		{
			if(booleanEquals(s.deQuien, quien) && s.rodeado(!quien))
			{
				for(Interseccion i : s.intersecciones)
					i.deQuien = null;
				s.deQuien = null;
			}
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 0; caso < casos; caso++)
		{
			int n = sc.nextInt();
			int s = sc.nextInt();
			Interseccion[][] tablero = new Interseccion[n][n];
			for(int i = 0; i < n; i++)
				for(int j = 0; j < n; j++)
					tablero[i][j] = new Interseccion();
			for(int i = 0; i < n; i++)
				for(int j = 0; j < n; j++)
				{
					if(i - 1 >= 0 && i - 1 < n && j >= 0 && j < n)
						tablero[i][j].adjacentes.add(tablero[i - 1][j]);
					if(i + 1 >= 0 && i + 1 < n && j >= 0 && j < n)
						tablero[i][j].adjacentes.add(tablero[i + 1][j]);
					if(i >= 0 && i < n && j - 1 >= 0 && j - 1 < n)
						tablero[i][j].adjacentes.add(tablero[i][j - 1]);
					if(i >= 0 && i < n && j + 1 >= 0 && j + 1 < n)
						tablero[i][j].adjacentes.add(tablero[i][j + 1]);
				}
			boolean quien = false;
			int turno = 1;
			boolean termino = false;
			for(int i = 0; i < s; i++)
			{
				int r = sc.nextInt();
				int c = sc.nextInt();
				if(!termino && (r != 0 || c != 0))
				{
					r--;
					c--;
					if(tablero[r][c].deQuien != null)
						termino = true;
					else
					{
						tablero[r][c].deQuien = quien;
						organizarTablero(tablero, quien);
						turno++;
					}
				}
				quien = !quien;
			}
			if(termino)
				System.out.println("Invalid " + turno);
			else
			{
				ArrayList <Segmento> segmentos = darSegmentos(tablero);
				int b = 0;
				int w = 0;
				for(Segmento s1 : segmentos)
				{
					if(booleanEquals(s1.deQuien, false))
						b += s1.intersecciones.size();
					else if(booleanEquals(s1.deQuien, true))
						w += s1.intersecciones.size();
					else if(s1.rodeado(false))
						b += s1.intersecciones.size();
					else if(s1.rodeado(true))
						w += s1.intersecciones.size();
				}
				System.out.println(b + " " + w);
			}
		}
	}
}

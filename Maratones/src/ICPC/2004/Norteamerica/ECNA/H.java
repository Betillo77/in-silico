import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class H 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		String nextLine()
		{
			try
			{
				String s = br.readLine();
				return s;
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
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

		public int nextInt() 
		{
			return Integer.parseInt(next());
		}
	}
	
	static class Palabra implements Comparable <Palabra>
	{
		int p;
		String pS;
		Palabra match;
		int antimatches = 0;
		ArrayList <Palabra> candidatos = new ArrayList <Palabra> ();
		ArrayList <Palabra> matches = new ArrayList <Palabra> ();

		public Palabra(int pp, String ppS) 
		{
			p = pp;
			pS = ppS;
		}

		@Override
		public int compareTo(Palabra otra) 
		{
			if(otra.matches.size() - matches.size() == 0)
				return otra.antimatches - antimatches;
			return otra.matches.size() - matches.size();
		}
	}
	
	static ArrayList <Palabra> l1;
	static ArrayList <Palabra> l2;
	static Integer[][] anteriores = new Integer[50][];
	
	static boolean backtrack(int n)
	{
		if(n == l1.size())
		{
			for(Palabra p : l1)
			{
				if(!concuerdan(p, p.match))
						return false;
			}
			return true;
		}

		Palabra a = l1.get(n);
		for(Palabra p : l1)
		{
			if(p == a)
				break;
			if(!concuerdan2(p))
				return false;
		}
		for(Palabra p : l2)
			if(!concuerdan2(p))
				return false;
		for(Palabra b : a.candidatos)
		{
			if(b.match != null && b.match != a)
				continue;
			a.match = b;
			b.match = a;
			if(backtrack(n + 1))
				return true;
			a.match = null;
			b.match = null;
		}
		return false;
	}

	private static boolean concuerdan(Palabra a, Palabra b) 
	{
		for(Palabra p1 : a.matches)
		{
			Palabra match = p1.match;
			if(!b.matches.contains(match))
				return false;
		}
		return true;
	}

	private static boolean concuerdan2(Palabra a) 
	{
		if(a.match == null)
			return true;
		for(Palabra p1 : a.match.matches)
		{
			Palabra match = p1.match;
			if(match != null && !a.matches.contains(match))
				return false;
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		boolean empezo = false;
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			if(empezo)
				System.out.println();
			empezo = true;
			ArrayList <String> nombresUno = new ArrayList <String> ();
			ArrayList <String> nombresDos = new ArrayList <String> ();
			l1 = new ArrayList <Palabra> ();
			for(int i = 0; i < n; i++)
			{
				String a = sc.next();
				String b = sc.next();
				int aN = 0;
				if(nombresUno.contains(a))
					aN = nombresUno.indexOf(a);
				else
				{
					nombresUno.add(a);
					aN = nombresUno.size() - 1;
					l1.add(new Palabra(aN, a));
				}
				int bN = 0;
				if(nombresUno.contains(b))
					bN = nombresUno.indexOf(b);
				else
				{
					nombresUno.add(b);
					bN = nombresUno.size() - 1;
					l1.add(new Palabra(bN, b));
				}
				l1.get(aN).matches.add(l1.get(bN));
				l1.get(bN).antimatches++;
			}
			Collections.sort(l1);
			l2 = new ArrayList <Palabra> ();
			for(int i = 0; i < n; i++)
			{
				String a = sc.next();
				String b = sc.next();
				int aN = 0;
				if(nombresDos.contains(a))
					aN = nombresDos.indexOf(a);
				else
				{
					nombresDos.add(a);
					aN = nombresDos.size() - 1;
					l2.add(new Palabra(aN, a));
				}
				int bN = 0;
				if(nombresDos.contains(b))
					bN = nombresDos.indexOf(b);
				else
				{
					nombresDos.add(b);
					bN = nombresDos.size() - 1;
					l2.add(new Palabra(bN, b));
				}
				l2.get(aN).matches.add(l2.get(bN));
				l2.get(bN).antimatches++;
			}
			for(Palabra p : l1)
				Collections.sort(p.matches);
			for(Palabra p : l2)
				Collections.sort(p.matches);
			Collections.sort(l2);
			for(Palabra p : l1)
			{
				for(Palabra p2 : l2)
				{
					if(p.antimatches == p2.antimatches && p.matches.size() == p2.matches.size())
					{
						boolean bien = true;
						for(int i = 0; i < p.matches.size(); i++)
							if(p.matches.get(i).matches.size() != p2.matches.get(i).matches.size() || p.matches.get(i).antimatches != p2.matches.get(i).antimatches)
								bien = false;
						if(bien)
							p.candidatos.add(p2);
					}
				}
			}
			backtrack(0);
			ArrayList <String> salida = new ArrayList <String> ();
			for(int i = 0; i < l1.size(); i++)
				salida.add(l1.get(i).pS + "/" + nombresDos.get(l1.get(i).match.p));
			Collections.sort(salida);
			for(String s : salida)
				System.out.println(s);
		}
	}
}
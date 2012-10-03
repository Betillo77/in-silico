import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.StringTokenizer;


public class Words
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
				try 
				{ 
						String linea = br.readLine(); 
						if(linea == null) return null; 
						st = new StringTokenizer(linea); 
				}
				catch(Exception e) { throw new RuntimeException(); }
			}
			return st.nextToken();
		}

		public int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		public Integer nextInteger()
		{
			String next = next();
			if(next == null)
				return null;
			return Integer.parseInt(next);
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
	
	static class Entrada
	{
		String w1;
		String w2;
		
		Entrada(String w1, String w2)
		{
			this.w1 = w1;
			this.w2 = w2;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((w1 == null) ? 0 : w1.hashCode());
			result = prime * result + ((w2 == null) ? 0 : w2.hashCode());
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
			if (w1 == null) {
				if (other.w1 != null)
					return false;
			} else if (!w1.equals(other.w1))
				return false;
			if (w2 == null) {
				if (other.w2 != null)
					return false;
			} else if (!w2.equals(other.w2))
				return false;
			return true;
		}
	}

	static HashSet <Entrada> entradas = new HashSet <Entrada> ();
	static String[] as;
	static String[] bs;
	
	
	static boolean existe(String a, String b)
	{
		Entrada e = new Entrada(a, b);
		if(entradas.contains(e))
			return false;
		entradas.add(e);
		if(a.equals("") && b.equals(""))
			return true;
		if(a.equals(""))
		{
			for(String s : as)
				if(existe(s, b))
					return true;
		}
		else if(b.equals(""))
		{
			for(String s : bs)
				if(existe(a, s))
					return true;
		}
		else
		{
			if(a.startsWith(b))
				return existe(a.substring(b.length()), "");
			else if(b.startsWith(a))
				return existe("", b.substring(a.length()));
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			Integer n1 = sc.nextInteger();
			if(n1 == null)
				return;
			int n2 = sc.nextInt();
			entradas.clear();
			as = new String[n1];
			for(int i = 0; i < n1; i++)
				as[i] = sc.next().trim();
			bs = new String[n2];
			for(int i = 0; i < n2; i++)
				bs[i] = sc.next().trim();
			boolean existe = false;
			for(String s : as)
				for(String s1 : bs)
					existe = existe || existe(s, s1);
			System.out.println(existe ? "S" : "N");
		}
	}
}

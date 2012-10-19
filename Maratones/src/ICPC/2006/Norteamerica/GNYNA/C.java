import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class C 
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
	
	static HashSet <String> hash = new HashSet <String> ();
	
	static int simular(LinkedList <Integer> inicial, String res)
	{
		String s = toString(inicial);
		if(s.equals(res))
			return 0;
		if(hash.contains(s))
			return -1;
		hash.add(s);
		int tam = inicial.size() / 2;
		LinkedList <Integer> as = new LinkedList <Integer> ();
		LinkedList <Integer> bs = new LinkedList <Integer> ();
		for(int i = 0; i < tam; i++)
			as.add(inicial.poll());
		for(int i = 0; i < tam; i++)
			bs.add(inicial.poll());
		for(int i = 0; i < tam * 2; i++)
		{
			if((i & 1) == 0)
				inicial.add(bs.poll());
			else
				inicial.add(as.poll());
		}
		int siguiente = simular(inicial, res);
		if(siguiente == -1)
			return -1;
		return siguiente + 1;
	}
	
	static String toString(LinkedList<Integer> inicial)
	{
		StringBuilder sb = new StringBuilder();
		for(int b : inicial)
		{
			sb.append(b + " ");
		}
		return sb.toString().trim();
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int n = sc.nextInt();
			String a = sc.next();
			String b = sc.next();
			String c = sc.next();
			LinkedList <Integer> aL = toLL(a);
			LinkedList <Integer> bL = toLL(b);
			hash.clear();
			String r = toString(toLL(c));
			LinkedList <Integer> ini = new LinkedList <Integer> ();
			for(int i = 0; i < 2 * n; i++)
			{
				if((i & 1) == 0)
					ini.add(bL.poll());
				else
					ini.add(aL.poll());
			}
			int res = simular(ini, r);
			System.out.println(caso + " " + (res == -1 ? res : res + 1));
		}
	}

	private static LinkedList <Integer> toLL(String a) 
	{
		LinkedList <Integer> l = new LinkedList <Integer> ();
		for(char c : a.toCharArray())
			l.add((int) c);
		return l;
	}
}
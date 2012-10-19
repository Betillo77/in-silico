import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class E
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
	
	static class Random
	{
		long x;
		long a;
		long b;
		long c;
		
		Random(int xx, int aa, int bb, int cc)
		{
			x = xx;
			a = aa;
			b = bb;
			c = cc;
		}
		
		int next()
		{
			long y = (x * a + b) % c;
			x = y;
			return (int) x;
		}
	}
	
	static class Equipo implements Comparable <Equipo>
	{
		int rating;
		String nombre;
		
		Equipo(String n, int r)
		{
			nombre = n;
			rating = r;
		}

		@Override
		public int compareTo(Equipo o)
		{
			return o.rating - rating;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		Random r = new Random(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
		Equipo[] equipos = new Equipo[n];
		for(int i = 0; i < n; i++)
			equipos[i] = new Equipo(sc.next(), sc.nextInt());
		Arrays.sort(equipos);
		int tamG = n / 4;
		@SuppressWarnings("unchecked")
		LinkedList <Equipo> [] baskets = new LinkedList[4];
		for(int i = 0; i < 4; i++)
			baskets[i] = new LinkedList <Equipo> ();
		int actual = 0;
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < tamG; j++)
				baskets[i].add(equipos[actual++]);
		for(int g = 0; g < tamG; g++)
		{
			sc.println("Group " + ((char) (g + 'A')) + ":");
			for(int i = 0; i < 4; i++)
				sc.println(baskets[i].remove(r.next() % baskets[i].size()).nombre);
		}
		try 
		{
			sc.bw.flush();
			sc.bw.close();
		} 
		catch (Exception e) 
		{
		}
	}
}
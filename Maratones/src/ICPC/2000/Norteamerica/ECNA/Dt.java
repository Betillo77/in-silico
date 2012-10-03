import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.StringTokenizer;


public class Dt
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
	
	static String shift(String str, int s) 
	{
		StringBuilder sb = new StringBuilder();
		for(char c : str.toCharArray())
			sb.append((char) ((((c - 'A') + s) % 26) + 'A'));
		return sb.toString();
	}
	
	static String decrypt(String shifted, int m) 
	{
		StringBuilder temp = new StringBuilder();
		StringBuilder result = new StringBuilder();
		for(char c : shifted.toCharArray())
		{
			temp.append(c);
			if(temp.length() == m)
			{
				temp.reverse();
				result.append(temp);
				temp.setLength(0);
			}
		}
		if(temp.length() != 0)
		{
			temp.reverse();
			result.append(temp);
		}
		return result.toString();
	}
	
	static void solve(String str, String crib)
	{
		for(int s = 1; s <= 25; s++)
		{
			String shifted = shift(str, 26 - s);
			for(int m = 5; m <= 20; m++)
			{
				String unenc = decrypt(shifted, m);
				if(unenc.contains(crib))
				{
					System.out.println(s + " " + m);
					return;
				}
			}
		}
		System.out.println("Crib is not encrypted.");
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int i = 0; i < casos; i++)
		{
			int tam = sc.nextInt();
			StringBuilder sb = new StringBuilder();
			int tamN = tam / 5 + (tam % 5 == 0 ? 0 : 1);
			for(int j = 0; j < tamN; j++)
				sb.append(sc.next());
			String crib = sc.next();
			solve(sb.toString(), crib);
		}
	}
}

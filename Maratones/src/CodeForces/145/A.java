import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


public class A 
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
	
	static int[][] dpSol = new int[501][];
	
	static int[] sol(int n)
	{
		if(dpSol[n] != null)
			return dpSol[n];
		int[] mejor = null;
		int mejorCuenta = Integer.MAX_VALUE;
		if(n == 0)
			return dpSol[n] = new int[4];
		if(n >= 25)
		{
			int[] otra = sol(n - 25);
			if(cuenta(otra) + 1 < mejorCuenta)
			{
				mejor = otra.clone();
				mejor[0]++;
				mejorCuenta = cuenta(otra) + 1;
			}
		}
		if(n >= 10)
		{
			int[] otra = sol(n - 10);
			if(cuenta(otra) + 1 < mejorCuenta)
			{
				mejor = otra.clone();
				mejor[1]++;
				mejorCuenta = cuenta(otra) + 1;
			}
		}
		if(n >= 5)
		{
			int[] otra = sol(n - 5);
			if(cuenta(otra) + 1 < mejorCuenta)
			{
				mejor = otra.clone();
				mejor[2]++;
				mejorCuenta = cuenta(otra) + 1;
			}
		}
		if(n >= 1)
		{
			int[] otra = sol(n - 1);
			if(cuenta(otra) + 1 < mejorCuenta)
			{
				mejor = otra.clone();
				mejor[3]++;
				mejorCuenta = cuenta(otra) + 1;
			}
		}
		return dpSol[n] = mejor;
	}
	
	private static int cuenta(int[] otra) 
	{
		int c = 0;
		for(int i : otra)
			c += i;
		return c;
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int nM = n >> 1;
		String s = sc.next();
		for(int i = 0; i < nM; i++)
		{
			int a = i;
			int b = i + nM;
			if(s.charAt(a) == 'R' && s.charAt(b) == 'L')
				sc.println((b + 1) + " " + (a + 1));
			else
				sc.println((a + 1) + " " + (b + 1));
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


public class F
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
	
	static int nTotal;
	static int[] tamAcum;
	static int[] tams;
	static int verdeTot;
	static int rojoTot;
	
	static int calcularAcumR(int n, int acumV)
	{
		int acumAnt = n == 0 ? 0 : tamAcum[n - 1];
		return rojoTot - (acumAnt - (verdeTot - acumV));
	}
	
	static Integer[][][] dp = new Integer[2][201][40001];
	
	static int dp(boolean anterior, int n, int acumV)
	{
		if(dp[anterior ? 1 : 0][n][acumV] != null)
			return dp[anterior ? 1 : 0][n][acumV];
		if(n == nTotal)
			return dp[anterior ? 1 : 0][n][acumV] = 0;
		int acumR = calcularAcumR(n, acumV);
		int mejor = Integer.MAX_VALUE;
		if(acumV >= tams[n])
		{
			int siguiente = dp(true, n + 1, acumV - tams[n]);
			int costoEste = n == 0 || anterior ? 0 : Math.min(tams[n], tams[n - 1]);
			if(siguiente != -1)
				mejor = Math.min(mejor, siguiente + costoEste);
		}
		if(acumR >= tams[n])
		{
			int siguiente = dp(false, n + 1, acumV);
			int costoEste = n == 0 || !anterior ? 0 : Math.min(tams[n], tams[n - 1]);
			if(siguiente != -1)
				mejor = Math.min(mejor, siguiente + costoEste);
		}
		return dp[anterior ? 1 : 0][n][acumV] = mejor == Integer.MAX_VALUE ? -1 : mejor;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		nTotal = n;
		rojoTot = sc.nextInt();
		verdeTot = sc.nextInt();
		tams = new int[n];
		tamAcum = new int[n];
		for(int i = 0; i < n; i++)
		{
			tams[i] = sc.nextInt();
			tamAcum[i] = (i == 0 ? 0 : tamAcum[i - 1]) + tams[i];
		}
		sc.println(dp(false, 0, verdeTot) + "");
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
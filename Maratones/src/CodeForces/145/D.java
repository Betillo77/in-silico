import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;


public class D
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
	
	static boolean[] isFavorite;
	
	static int maxNumber(boolean[] inMovie)
	{
		int count = 0;
		for(int i = 0; i < isFavorite.length; i++)
		{
			if(inMovie[i])
				continue;
			if(isFavorite[i])
				count++;
		}
		return count;
	}
	
	static int minNumber(boolean[] inMovie)
	{
		int count = 0;
		for(int i = 0; i < isFavorite.length; i++)
		{
			if(inMovie[i])
				continue;
			if(!isFavorite[i])
				count++;
		}
		return count;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int m = sc.nextInt();
		int k = sc.nextInt();
		isFavorite = new boolean[m];
		for(int i = 0; i < k; i++)
			isFavorite[sc.nextInt() - 1] = true;
		int n = sc.nextInt();
		int[] bestN = new int[n];
		int[] worstN = new int[n];
		int worstW = -1;
		for(int i = 0; i < n; i++)
		{
			sc.next();
			int d = sc.nextInt();
			int favorites = 0;
			int unknown = 0;
			boolean[] inMovie = new boolean[m];
			for(int j = 0; j < d; j++)
			{
				int current = sc.nextInt();
				if(current == 0)
					unknown++;
				else
				{
					inMovie[current - 1] = true;
					if(isFavorite[current - 1])
						favorites++;
				}
			}
			int maxF = maxNumber(inMovie);
			int minF = unknown - minNumber(inMovie);
			bestN[i] = favorites + Math.min(unknown, maxF);
			worstN[i] = favorites + Math.max(0, Math.min(unknown, minF));
			worstW = Math.max(worstN[i], worstW);
		}
		for(int i = 0; i < n; i++)
		{
			boolean isBest = true;
			for(int j = 0; j < n; j++)
			{
				if(i == j)
					continue;
				isBest = isBest && worstN[i] >= bestN[j];
			}
			boolean notBest = false;
			for(int j = 0; j < n; j++)
			{
				if(i == j)
					continue;
				notBest = notBest || bestN[i] < worstN[j];
			}
			if(isBest)
				sc.println("0");
			else if(notBest)
				sc.println("1");
			else
				sc.println("2");
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
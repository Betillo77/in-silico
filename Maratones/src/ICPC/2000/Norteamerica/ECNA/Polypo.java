import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Polypo
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
	
	static class PNumber
	{
		int k;
		long current = 1;
		long currentChange;
		
		public PNumber(int nextInt) 
		{
			k = nextInt;
			currentChange = k - 1;
		}

		public void iterate() 
		{
			current += currentChange;
			currentChange += (k - 2);
		}
	}
	
	static void iterate(PNumber[] pNumbers)
	{
		long max = minimum(pNumbers);
		for(PNumber p : pNumbers)
		{
			while(p.current <= max)
				p.iterate();
		}
	}
	
	static long maximum(PNumber[] pNumbers)
	{
		long max = 0;
		for(PNumber p : pNumbers)
			max = Math.max(max, p.current);
		return max;
	}
	
	static long minimum(PNumber[] pNumbers)
	{
		long min = Integer.MAX_VALUE;
		for(PNumber p : pNumbers)
			min = Math.min(min, p.current);
		return min;
	}
	
	
	static void simulate(PNumber[] pNumbers, int start)
	{
		while(minimum(pNumbers) < start)
			iterate(pNumbers);
		int total = 0;
		while(true)
		{
			long max = minimum(pNumbers);
			int cuenta = 0;
			for(PNumber p : pNumbers)
				if(p.current == max)
					cuenta++;
			if(cuenta > 1)
			{
				total++;
				System.out.print(max + ":");
				int empezo = 0;
				for(PNumber p : pNumbers)
					if(p.current == max)
						if(empezo++ != 0)
							System.out.print(" " + p.k);
						else
							System.out.print(p.k);
				System.out.println();
				if(total == 5)
					return;
			}
			iterate(pNumbers);
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int empezo = 0;
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			PNumber[] pNumbers = new PNumber[n];
			for(int i = 0; i < n; i++)
				pNumbers[i] = new PNumber(sc.nextInt());
			int s = sc.nextInt();
			if(empezo++ != 0)
				System.out.println();
			simulate(pNumbers, s);
		}
	}
}

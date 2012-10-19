import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G
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
	
	
	static long tresA(int n)
	{
		return (long) Math.pow(3, n);
	}
	
	static long dosA(int n)
	{
		return 1 << n;
	}
	
	static class Descom
	{
		Integer[] doses = new Integer[33];
		
		
		Descom(long n)
		{
			if(n == 0)
				return;
			if(n == 1)
			{
				doses[0] = 0;
				return;
			}
			if((n & 1) == 0)
			{
				Descom inte = new Descom(n / 2);
				for(int i = 0; i < 33; i++)
				{
					if(inte.doses[i] != null)
						agregar(i + 1, inte.doses[i]);
				}
			}
			else
			{
				int respuesta = 0;
				for(int i = 0; i < 33; i++)
				{
					long tresA = tresA(i);
					if(tresA > n)
					{
						respuesta = i - 1;
						break;
					}
				}
				agregar(0, respuesta);
				Descom inte = new Descom(n - tresA(respuesta));
				for(int i = 0; i < 33; i++)
				{
					if(inte.doses[i] != null)
						agregar(i, inte.doses[i]);
				}
			}
		}
		
		void agregar(int dos, int tres)
		{
				doses[dos] = tres;
		}
		
		@Override
		public String toString() 
		{
			long cuenta = 0;
			for(int i = 0; i < 33; i++)
				if(doses[i] != null)
					cuenta += dosA(i) * tresA(doses[i]);
			return cuenta + "";
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int n = sc.nextInt();
			Descom d = new Descom(n);
			int cuantos = 0;
			for(int i = 0; i < 33; i++)
				if(d.doses[i] != null)
					cuantos++;
			System.out.print(caso + " " + cuantos);
			for(int i = 0; i < 33; i++)
				if(d.doses[i] != null)
					System.out.print(" [" + i + "," + d.doses[i] + "]");
			System.out.println();
		}
	}
}
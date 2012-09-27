import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Voting 
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
	
	static class Respuesta
	{
		int r;
		Respuesta siguiente;
		int m;
		
		public Respuesta(int p, Respuesta s)
		{
			r = p;
			siguiente = s;
		}
	}
	
	static int n;
	static Respuesta[][] dp;
	static int[][] info;
	
	public static Respuesta dp(int m, int precinto)
	{
		if(dp[m][precinto] != null)
			return dp[m][precinto];
		Respuesta actual = null;
		for(int i = m; i >= 0; i--)
		{
			int personas = (int) Math.round(((info[precinto][1] + ((i / (10.1 + i)) * info[precinto][2])) / 100) * info[precinto][0]);
			Respuesta nueva = null;
			if(precinto == n - 1)
				nueva = new Respuesta(personas, null);
			else
			{
				Respuesta siguiente = dp(m - i, precinto + 1);
				nueva = new Respuesta(siguiente.r + personas, siguiente);
			}
			nueva.m = i;
			if(actual == null || nueva.r > actual.r)
				actual = nueva;
		}
		return dp[m][precinto] = actual;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			int m = sc.nextInt();
			n = sc.nextInt();
			if(m == 0 && n == 0)
				return;
			info = new int[n][3];
			for(int i = 0; i < n; i++)
			{
				info[i][0] = sc.nextInt();
				info[i][1] = sc.nextInt();
				info[i][2] = sc.nextInt();
			}
			dp = new Respuesta[m + 1][n + 1];
			Respuesta r = dp(m, 0);
			System.out.println("Case " + caso++ + ": " + r.r);
			int p = 0;
			while(r != null)
			{
				if(p == 0)
					System.out.print(p + ":" + r.m);
				else
					System.out.print(" " + p + ":" + r.m);
				p++;
				r = r.siguiente;
			}
			System.out.println();
		}
	}

}

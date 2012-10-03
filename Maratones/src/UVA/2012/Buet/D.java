import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class D
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
	
	static boolean valido(int i, int j, int k)
	{
		return i >= 0 && i < j && j < k;
	}
	
//	static Boolean[][][] dp = new Boolean[100][100][100];
//	
//	static boolean dp(int i, int j, int k)
//	{
//		if(dp[i][j][k] != null)
//			return dp[i][j][k];
//		if(valido(i - 1, j, k) && !dp(i - 1, j, k))
//			return dp[i][j][k] = true;
//		if(valido(i, j - 1, k) && !dp(i, j - 1, k))
//			return dp[i][j][k] = true;
//		if(valido(i, j, k - 1) && !dp(i, j, k - 1))
//			return dp[i][j][k] = true;
//		if(valido(i - 1, j, k - 1) && !dp(i - 1, j, k - 1))
//			return dp[i][j][k] = true;
//		return dp[i][j][k] = false;
//	}
	
	static long pares(long a, long b)
	{
		long numero = b - a + 1;
		if((numero & 1) == 0)
			return numero / 2;
		long menor = numero / 2;
		long mayor = menor + 1;
		return (a & 1) == 1 ? menor : mayor;
	}
	
	static long impares(long a, long b)
	{
		long numero = b - a + 1;
		if((numero & 1) == 0)
			return numero / 2;
		long menor = numero / 2;
		long mayor = menor + 1;
		return (a & 1) == 1 ? mayor : menor;
	}
	
	public static long cuenta(long a1, long a2, long b1, long b2, long c1, long c2)
	{
		long a = (a2 - a1 + 1) * (b2 - b1 + 1) * (c2 - c1 + 1);
		long b = (pares(a1, a2) * (b2 - b1 + 1) * impares(c1, c2));
		long c = (impares(a1, a2) * (b2 - b1 + 1) * (c2 - c1 + 1));
		return a - (b + c);
	}
	
	public static void main(String[] args)
	{
//		int inicio = 1;
//			for(int j = inicio + 1; j < 100; j++)
//				for(int k = j + 1; k < 100; k++)
//					if(dp(inicio, j, k))
//						System.out.println((j - inicio - 1) + " " + (k - j - 1));
		Scanner sc = new Scanner();
		int t = sc.nextInt();
		int caso = 1;
		for(int i = 0; i < t; i++)
			System.out.println("Case " + caso++ + ": " + cuenta(sc.nextLong(), sc.nextLong(), sc.nextLong(), sc.nextLong(), sc.nextLong(), sc.nextLong()));
	}
}

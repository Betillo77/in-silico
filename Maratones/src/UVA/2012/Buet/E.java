import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class E
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
	 
	static int solve(int ciudades, long aristas)
	{
		if(aristas == ciudades - 1)
			return (int) aristas;
		aristas -= ciudades - 1;
		int i;
		for(i = 2; i <= ciudades; i++)
		{
			if((i * (i - 1)) / 2 >= aristas)
				break;
			if(i == ciudades)
				return 0;
		}
		return (int) (ciudades - i - 1);
		
	}
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int tests = sc.nextInt();
		for(int i = 0; i < tests; i++)
		{
			System.out.println(solve(sc.nextInt(), sc.nextLong()));
		}
	}

}

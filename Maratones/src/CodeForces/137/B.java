import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class B 
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
	 
	 public static void main(String[] args)
	 {
		 Scanner sc = new Scanner();
		 int n = sc.nextInt();
		 int m = sc.nextInt();
		 int k = sc.nextInt();
		 int[][] tabla = new int[n][m];
		 for(int i = 0; i < n; i++)
			 for(int j = 0; j < m; j++)
				 tabla[i][j] = sc.nextInt();
		 int[] filas = new int[n];
		 for(int i = 0; i < n; i++)
			 filas[i] = i;
		 int[] columnas = new int[m];
		 for(int i = 0; i < m; i++)
			 columnas[i] = i;
		 for(int i = 0; i < k; i++)
		 {
			 String q = sc.next();
			 int x = sc.nextInt() - 1;
			 int y = sc.nextInt() - 1;
			 if(q.charAt(0) == 'c')
			 {
				 int temp = columnas[x];
				 columnas[x] = columnas[y];
				 columnas[y] = temp;
			 }
			 else if(q.charAt(0) == 'r')
			 {
				 int temp = filas[x];
				 filas[x] = filas[y];
				 filas[y] = temp;
			 }
			 else
				 System.out.println(tabla[filas[x]][columnas[y]]);
		 }
	 }
}
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
	 

	 static int[][] leerTabla(Scanner sc) 
	 {
		 int n = sc.nextInt();
		 int m = sc.nextInt();
		 int[][] tabla = new int[n][m];
		 for(int i = 0; i < n; i++)
		 {
			 String s = sc.next();
			 for(int j = 0; j < m; j++)
				 tabla[i][j] = s.charAt(j) == '1' ? 1 : 0;
		 }
		 return tabla;
	 }
		
	 public static void main(String[] args)
	 {
		 Scanner sc = new Scanner();
		 int[][] a = leerTabla(sc);
		 int[][] b = leerTabla(sc);
		 int bestCount = -1;
		 int bestI = 0;
		 int bestJ = 0;
		 for(int i = -100; i < 100; i++)
		 {
			 for(int j = -100; j < 100; j++)
			 {
				 int count = 0;
				 for(int k = 0; k < a.length; k++)
					 for(int l = 0; l < a[0].length; l++)
					 {
						 if(k + i >= 0 && k + i < b.length && j + l >= 0 && j + l < b[0].length)
							 count += b[k + i][j + l] == 1 && a[k][l] == 1 ? 1 : 0;
					 }
				 if(count > bestCount)
				 {
					 bestCount = count;
					 bestI = i;
					 bestJ = j;
				 }
			 }
		 }
		 System.out.println(bestI + " " + bestJ);
	 }
}

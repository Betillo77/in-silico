import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class A
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
	 
	 public static void main(String[] args)
	 {
		 Scanner sc = new Scanner();
		 TreeSet <Long> vals = new TreeSet <Long> ();
		 for(int i = 0; i < 4; i++)
			 vals.add(sc.nextLong());
		 System.out.print(4 - vals.size());
	 }

}

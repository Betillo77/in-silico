import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class B 
{
	 static class Scanner
     {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        String nextLine()
        {
            String next;
            try 
            {
                next = br.readLine();
            } 
            catch (IOException ex) 
            {
                throw(new RuntimeException(ex));
            }
            return next;
        }
        
        String next()
        {
            while(st == null || !st.hasMoreTokens())
            {
                String linea = nextLine();
                if(linea == null)
                    return null;
                st = new StringTokenizer(linea);
            }
            return st.nextToken();
        }
        
        int nextInt()
        {
            return Integer.parseInt(next());
        }
    }
	 
	 static int n, l, m, tam;
	 static int[][] starts;
	 static Integer[][] dp;

	 static int dp(int i, int j) 
	 {
		 if(j + tam >= l)
			 return 1;
		 if(dp[i][j] != null)
			 return dp[i][j];
		 int cuenta = 0;
		 for(int otro : starts[i])
			 cuenta += dp(otro, j + 1);
		 return dp[i][j] = cuenta;
	 }



	 public static void main(String[] args)
	 {
		 Scanner sc = new Scanner();
		 while(true)
		 {
			 n = sc.nextInt();
			 l = sc.nextInt(); 
			 m = sc.nextInt();
			 if(n == 0 && l == 0 && m == 0)
				 return;
			 String[] t = new String[m];
			 for(int i = 0; i < m; i++)
				 t[i] = sc.next().trim();
			 tam = m == 0 ? 0 : t[0].length();
			 starts = new int[m][];
			 for(int i = 0; i < m; i++)
			 {
				 ArrayList <Integer> aT = new ArrayList <Integer> ();
				 for(int j = 0; j < m; j++)
					 if(t[j].startsWith(t[i].substring(1)))
						 aT.add(j);
				 starts[i] = new int[aT.size()];
				 for(int j = 0; j < aT.size(); j++)
					 starts[i][j] = aT.get(j);
			 }
			 if(m != 0 && l >= tam)
			 {
				 dp = new Integer[m][l];
				 int cuenta = 0;
				 for(int i = 0; i < m; i++)
					 cuenta += dp(i, 0);
				 System.out.println(cuenta);
			 }
			 else
				 System.out.println(0);
		 }
	 }
}

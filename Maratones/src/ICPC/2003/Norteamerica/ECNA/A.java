import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class A
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
	    
	 
	 public static void main(String[] args)
	 {
		 Scanner sc = new Scanner();
		 while(true)
		 {
			 String keyword = sc.next().trim();
			 if(keyword.equals("THEEND"))
				 return;
			 String text = sc.next().trim();
			 int n = text.length() / keyword.length();
			 int m = keyword.length();
			 char[][] mensaje = new char[n][m];
			 char[] keys = keyword.toCharArray();
			 char[] keysO = keyword.toCharArray();
			 Arrays.sort(keysO);
			 for(int i = 0; i < keysO.length; i++)
			 {
				 char actual = keysO[i];
				 int donde = -1;
				 for(int j = 0; j < keys.length; j++)
					 if(keys[j] == actual)
					 {
						 donde = j;
						 keys[j] = 0;
						 break;
					 }
				 for(int j = 0; j < n; j++)
					 mensaje[j][donde] = text.charAt(j);
				 text = text.substring(n);
			 }
			 for(int i = 0; i < n; i++)
				 System.out.print(String.valueOf(mensaje[i]));
			 System.out.println();
		 }
	 }

}

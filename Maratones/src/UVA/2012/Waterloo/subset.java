
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class subset 
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

        private long nextLong() {
            return Long.parseLong(next());
        }
    }
    
    static Long subset(long[] values, long desired)
    {
        int limite = 1 << values.length;
        Long mejor = null;
        for(int i = 0; i < limite; i++)
        {
            long res = sus(i, values);
            if(res >= desired)
            {
                if(mejor == null || res < mejor.longValue())
                    mejor = res;
            }
        }    
        return mejor;
    }

    private static long sus(int i, long[] values) 
    {
        int actual = 0;
        long total = 0;
        while(i != 0)
        {
            if((i & 1) == 1)
                total += values[actual];
            i >>= 1;
            actual++;
        }
        return total;
    }
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner();
        int casos = sc.nextInt();
        for(int caso = 0; caso < casos; caso++)
        {
            long deseado = sc.nextLong();
            int n = sc.nextInt();
            long[] valores = new long[n];
            for(int i = 0; i < n; i++)
                valores[i] = sc.nextInt();
            Long result = subset(valores, deseado);
            if(result == null)
                System.out.println("IMPOSSIBLE");
            else
                System.out.println(result.longValue());
        }
    }
}


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
public class classsch 
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
    
    static int c;
    static int t;
    static int l;
    
    static long[][] e;
    static long[][] lo;
    
    public static long solve()
    {
        final long[][] energy = e;
        final long[][] location = lo;
        long[][] bestPossible = new long[c][t];
        for(int i = 0; i < c; i++)
            for(int j = 0; j < t; j++)
                bestPossible[i][j] = Long.MAX_VALUE;
        for(int i = 0; i < t; i++)
            bestPossible[0][i] = location[0][i];
        for(int i = 0; i < c - 1; i++)
        {
            for(int j = 0; j < t; j++)
                for(int k = 0; k < t; k++)
                    bestPossible[i + 1][k] = Math.min(bestPossible[i + 1][k], bestPossible[i][j] + energy[i][j] + Math.abs(location[i][j] - location[i + 1][k]));
        }
        long best = Long.MAX_VALUE;
        for(int j = 0; j < t; j++)
            best = Math.min(best, bestPossible[c - 1][j] + energy[c - 1][j] + Math.abs(location[c - 1][j] - l));
        return best;
    }
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner();
        int casos = sc.nextInt();
        for(int caso = 0; caso < casos; caso++)
        {
            c = sc.nextInt();
            t = sc.nextInt();
            l = sc.nextInt();
            e = new long[c][t];
            lo = new long[c][t];
            for(int i = 0; i < c; i++)
                for(int j = 0; j < t; j++)
                {
                    lo[i][j] = sc.nextInt();
                    e[i][j] = sc.nextInt();
                }
            System.out.println(solve());
        }
    }   
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class F 
{
    static class Solucion
    {
        LinkedList <Ciudad> nodos = new LinkedList <Ciudad> ();
        Ciudad c;
    }
    
    static class Ciudad
    {
        ArrayList <Ciudad> conectadas = new ArrayList <Ciudad> ();
        boolean visitada = false;
    }
    
    static Double[][] dp = new Double[200][2];
    static double dp(int n, boolean primero)
    {
        if(dp[n][primero ? 1 : 0] != null)
            return dp[n][primero ? 1 : 0];
        if(n == 0)
            return dp[n][primero ? 1 : 0] = primero ? 0.0 : 1.0;
        if(n == 1)
            return dp[n][primero ? 1 : 0] = 0.5;
        if(primero)
            return dp[n][primero ? 1 : 0] = 0.5 / (1 - 0.5 * dp(n - 1, true));
        else
            return dp[n][primero ? 1 : 0] = (0.5 * dp(n - 1, false)) / (1 - 0.5 * dp(n - 1, true));
    }
    
    static Ciudad[] ciudades;
    
    static double bfs(int n, int a, int b, int c)
    {
        LinkedList <Solucion> cola = new LinkedList <Solucion> ();
        Solucion primera = new Solucion();
        primera.c = ciudades[a];
        primera.nodos.add(ciudades[a]);
        primera.c.visitada = true;
        Solucion sB = null;
        Solucion sC = null;
        cola.add(primera);
        while(!cola.isEmpty())
        {
            Solucion actual = cola.poll();
            if(actual.c == ciudades[b])
                sB = actual;
            else if(actual.c == ciudades[c])
                sC = actual;
            for(Ciudad ci : actual.c.conectadas)
            {
                if(!ci.visitada)
                {
                    ci.visitada = true;
                    Solucion nueva = new Solucion();
                    nueva.c = ci;
                    nueva.nodos.addAll(actual.nodos);
                    nueva.nodos.add(ci);
                    cola.add(nueva);
                }
            }
        }
        while(!sB.nodos.isEmpty() && !sC.nodos.isEmpty() && sB.nodos.peekFirst() == sC.nodos.peekFirst())
        {
            sB.nodos.poll();
            sC.nodos.poll();
        }
        if(sB.nodos.isEmpty())
            return 1;
        if(sC.nodos.isEmpty())
            return 0;
        int aristasB = sB.nodos.size();
        int aristasC = sC.nodos.size();
        return (0.5 * dp(aristasB - 1, false)) / (1 - 0.5 * dp(aristasB - 1, true) - 0.5 * dp(aristasC - 1, true));
        
    }
    public static void main(String[] args) throws IOException
    {
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         while(true)
         {
             String li = br.readLine();
             if(li == null)
                 return;
             StringTokenizer st = new StringTokenizer(li);
             int n = Integer.parseInt(st.nextToken());
             int a = Integer.parseInt(st.nextToken());
             int b = Integer.parseInt(st.nextToken());
             int c = Integer.parseInt(st.nextToken());
             ciudades = new Ciudad[n + 1];
             for(int i = 1; i <= n; i++)
                 ciudades[i] = new Ciudad();
             for(int i = 0; i < n - 1; i++)
             {
                 st = new StringTokenizer(br.readLine());
                 int aa = Integer.parseInt(st.nextToken());
                 int bb = Integer.parseInt(st.nextToken());
                 ciudades[aa].conectadas.add(ciudades[bb]);
                 ciudades[bb].conectadas.add(ciudades[aa]);
             }
             System.out.printf("%.6f\n", bfs(n, a, b, c));
         }
    }
    
}

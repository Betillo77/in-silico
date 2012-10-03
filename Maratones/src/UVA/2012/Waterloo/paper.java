
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class paper 
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
    
    static class Arista
    {
        Nodo otro;
        int costo;

        private Arista(Nodo nodo, int c) 
        {
            otro = nodo;
            costo = c;
        }
    }
    
    static class Nodo
    {
        int costoFinal;
        int costo = 0;
        Nodo padre;
        int costoPadre = 0;
        int costoDesde = 0;
        ArrayList <Arista> aristas = new ArrayList <Arista> ();

        private Nodo(int nextInt)
        {
            costoFinal = nextInt;
        }
    }
    
    static int solucionar(Nodo inicial)
    {
        ArrayList <Nodo> topo = new ArrayList <Nodo> ();
        topo.add(inicial);
        for(int i = 0; i < topo.size(); i++)
        {
            Nodo actual = topo.get(i);
            for(Arista a : actual.aristas)
            {
                if(a.otro == actual.padre)
                    continue;
                topo.add(a.otro);
                a.otro.padre = actual;
                a.otro.costoPadre = a.costo;
                a.otro.costoDesde = actual.costoDesde + a.costo;
            }
        }
        for(int i = topo.size() - 1; i >= 0; i--)
        {
            Nodo actual = topo.get(i);
            int costo = 0;
            for(Arista a : actual.aristas)
            {
                if(a.otro == actual.padre)
                    continue;
                costo += a.otro.costo + a.costo * 2;
            }
            actual.costo = costo;
        }
        int costoTotal = topo.get(0).costo;
        int mejorCosto = Integer.MAX_VALUE;
        for(int i = 0; i < topo.size(); i++)
        {
            Nodo actual = topo.get(i);
            int costo = costoTotal - actual.costoDesde + actual.costoFinal;
            mejorCosto = Math.min(mejorCosto, costo);
        }
        return mejorCosto;
    }
    
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner sc = new Scanner();
        int casos = sc.nextInt();
        for(int caso = 0; caso < casos; caso++)
        {
            int n = sc.nextInt() + 1;
            Nodo[] nodos = new Nodo[n];
            for(int i = 0; i < n; i++)
                nodos[i] = new Nodo(sc.nextInt());
            n--;
            for(int i = 0; i < n; i++)
            {
                int a = sc.nextInt();
                int b = sc.nextInt();
                int c = sc.nextInt();
                nodos[a].aristas.add(new Arista(nodos[b], c));
                nodos[b].aristas.add(new Arista(nodos[a], c));
            }
            System.out.println(solucionar(nodos[0]));
        }
    }
}
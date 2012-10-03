
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class buying 
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
    
    static class Entry implements Comparable <Entry>
    {
        double precio;
        int nParadas;
        int numeroGasolina;
        int verdadero;

        private Entry(double p, int nP, int i) 
        {
            precio = p;
            nParadas = nP;
            numeroGasolina = i;
        }

        @Override
        public int compareTo(Entry o)
        {
            if(precio == o.precio)
                return nParadas - o.nParadas;
            return Double.compare(precio, o.precio);
        }

        private Entry clonar() 
        {
            Entry nueva = new Entry(precio, nParadas, numeroGasolina);
            nueva.verdadero = verdadero;
            return nueva;
        }
    }
    
    static long[][] paradas;
    static Entry imposible = new Entry(Double.MAX_VALUE, -1, -1);
    
    static void iterar(int parada, int costo, Entry[] anteriores, Entry[] nuevas, Entry[] temporal)
    {
        for(int i = 0; i < anteriores.length; i++)
        {
            Entry anterior = anteriores[i];
            if(anterior == imposible)
            {
                temporal[i] = imposible;
                continue;
            }
            temporal[i] = new Entry(anterior.precio + ((n - (anterior.numeroGasolina - costo)) * paradas[parada][1]) / 10, anterior.nParadas, anterior.numeroGasolina - costo);
            temporal[i].verdadero = i;
            if(temporal[i].numeroGasolina < 0)
                temporal[i] = imposible;
        }
        Arrays.sort(temporal);
        int actual = 0;
        for(int i = 0; i <= n; i++)
            nuevas[i] = imposible;
        for(int i = n; i >= 0; i--)
        {
            while(actual < temporal.length && (temporal[actual] == imposible || temporal[actual].numeroGasolina > i))
                actual++;
            if(actual == temporal.length)
                break;
            Entry posible = temporal[actual];
            Entry nueva = posible.clonar();
            nueva.precio -= ((n - i) * paradas[parada][1]) / 10;
            nueva.nParadas++;
            if(nueva.numeroGasolina == i)
                nueva.nParadas--;
            nueva.numeroGasolina = i;
            nuevas[i] = nueva;
        }
    }
    
    
    static int simular()
    {
        Entry[] a = new Entry[n + 1];
        Entry[] b = new Entry[n + 1];
        Entry[] temp = new Entry[n + 1];
        for(int i = 0; i <= n; i++)
            a[i] = imposible;
        a[n] = new Entry(0, 0, n);
        for(int i = 0; i < paradas.length; i++)
        {
            iterar(i, (int) (paradas[i][0] - (i == 0 ? 0 : paradas[i - 1][0])), a, b, temp);
            Entry[] t = a;
            a = b;
            b = t;
        }
        Arrays.sort(a);
        return a[0].nParadas;
    }
    
    static int n;
    
    public static void main(String[] args) throws FileNotFoundException
    {
        System.setIn(new FileInputStream("entrada.txt"));
        Scanner sc = new Scanner();
        int casos = sc.nextInt();
        for(int caso = 0; caso < casos; caso++)
        {
            n = sc.nextInt() * 10;
            int m = sc.nextInt();
            int d = sc.nextInt();
            ArrayList <long[]> ps = new ArrayList <long[]> ();
            for(int i = 0; i < m; i++)
            {
                long a = sc.nextLong();
                long b = sc.nextLong();
                if(a >= d)
                    continue;
                ps.add(new long[] {a, b});
            }
            ps.add(new long[] {d, 0});
            paradas = ps.toArray(new long[0][0]);
            Arrays.sort(paradas, new Comparator <long[]> ()
            {
                public int compare(long[] o1, long[] o2) 
                {
                    return Long.valueOf(o1[0]).compareTo(o2[0]);
                }
            });
            try
            {
                System.out.println(simular());
            }
            catch(Throwable t)
            {
                return;
            }
        }
    }
}
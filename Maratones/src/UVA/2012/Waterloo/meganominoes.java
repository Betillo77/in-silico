
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class meganominoes 
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
    
    static TreeMap <Long, TreeMap <Long, Integer>> todos = new  TreeMap <Long, TreeMap <Long, Integer> > ();
    static TreeMap <Long, Integer> [] todosA;

    static long contar(long suma)
    {
        long conteo = 0;
        for(int i = 0; i < todosA.length; i++)
        {
            TreeMap <Long, Integer> actual = todosA[i];
            for(Map.Entry <Long, Integer> e : actual.entrySet())
            {
                if(suma - e.getKey() < e.getKey())
                    continue;
                if(actual.containsKey(suma - e.getKey()))
                {
                    long v = e.getValue();
                    if(suma - e.getKey() == e.getKey())
                        conteo += v * (v - 1);
                    else
                        conteo += actual.get(suma - e.getKey()) * v;
                }
            }
        }
        return conteo;
    }
    
    private static void ingresar(long a, long b) 
    {
        if(todos.containsKey(a))
        {
            if(todos.get(a).containsKey(b))
                todos.get(a).put(b, todos.get(a).get(b) + 1);
            else
                todos.get(a).put(b, 1);
        }
        else
        {
            todos.put(a, new TreeMap <Long, Integer> ());
            todos.get(a).put(b, 1);
        }
    }
    
    public static void llenar(long a, long b)
    {
        ingresar(a, b);
        if(b != a)
            ingresar(b, a);
    }
    
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner();
        int casos = sc.nextInt();
        for(int caso = 0; caso < casos; caso++)
        {
            int n = sc.nextInt();
            int m = sc.nextInt();
            todos.clear();
            for(int i = 0; i < n; i++)
                llenar(sc.nextLong(), sc.nextLong());
            todosA = todos.values().toArray(new TreeMap[0]);
            for(int i = 0; i < m; i++)
                System.out.println(contar(sc.nextLong()));
        }
    } 
}

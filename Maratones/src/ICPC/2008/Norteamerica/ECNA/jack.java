import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class jack
{

    private static void intentar(Nodo inicio, Nodo fin, int profundidad, double numero) 
    {
    	if(profundidad == 10)
    		return;
        if(inicio == fin)
        {
            if(Math.abs(numero - respuesta) < 1e-8)
                cuenta++;
            else if(numero < respuesta)
            {
                respuesta = numero;
                cuenta = 1;
            }
            return;
        }
        for(Arista a : inicio.aristas)
            intentar(a.otro, fin, profundidad + 1, numero / a.costo);
    }
    
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
                
                public boolean endLine()
                {
                        try 
                        {
                                String next = br.readLine();
                                while(next != null && next.trim().isEmpty())
                                        next = br.readLine();
                                if(next == null)
                                        return true;
                                st = new StringTokenizer(next);
                                return st.hasMoreTokens();
                        }
                        catch(Exception e) { throw new RuntimeException(); }
                }
        }
    
    static class Arista 
    {
        double costo;
        Nodo otro;

        public Arista(double c, Nodo o) 
        {
            costo = c;
            otro = o;
        }
    }
        
    static class Nodo
    {
        String nombre;
        ArrayList <Arista> aristas = new ArrayList <Arista> ();
    }
    
    
    private static Nodo darNodo(String s) 
    {
        if(nodos.containsKey(s))
            return nodos.get(s);
        Nodo nuevo = new Nodo();
        nuevo.nombre = s;
        nodos.put(s, nuevo);
        return nuevo;
    }
    
    static HashMap <String, Nodo> nodos = new HashMap <String, Nodo> ();
    
    static double respuesta;
    static int cuenta;
    
    public static void main(String[] args)
    {
        Scanner sc = new Scanner();
        int casos = sc.nextInt();
        for(int caso = 1; caso <= casos; caso++)
        {
            nodos.clear();
            Nodo fin = darNodo(sc.next());
            Nodo inicio = darNodo(sc.next());
            int m = sc.nextInt();
            for(int i = 0; i < m; i++)
            {
                double an = sc.nextInt();
                Nodo a = darNodo(sc.next());
                double bn = sc.nextInt();
                Nodo b = darNodo(sc.next());
                b.aristas.add(new Arista(an / bn, a));
            }
            respuesta = Double.POSITIVE_INFINITY;
            cuenta = 0;
            intentar(inicio, fin, 0, 1);
            BigDecimal bd = new BigDecimal(respuesta);
            int escala = 0;           
            while(bd.compareTo(BigDecimal.ONE) < 0)
            {
                bd = bd.scaleByPowerOfTen(1);
                escala--;
            }
            while(bd.compareTo(BigDecimal.ONE) >= 0)
            {
                bd = bd.scaleByPowerOfTen(-1);
                escala++;
            }
            bd = bd.divide(BigDecimal.ONE, 5, BigDecimal.ROUND_HALF_UP);
            bd = bd.scaleByPowerOfTen(escala);
            String numero = bd.toPlainString();
            int numeroPunto = 0;
            if(numero.contains("."))
                numeroPunto = 1;
            else if(!numero.contains(".") && numero.length() < 5)
            {
                numero += ".";
                numeroPunto = 1;
            }
            while(numero.length() - numeroPunto < 5)
                numero += "0";
            System.out.println("Case " + caso + ": " + numero + " " + cuenta);
        }
    }
    
}

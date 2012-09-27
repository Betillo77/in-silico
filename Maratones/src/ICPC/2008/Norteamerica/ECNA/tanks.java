
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class tanks 
{

    private static void resolver(Entrada[] entradas, boolean uno)
    {
        int[] desdeCero = new int[entradas.length];
        int[] hastaCero = new int[entradas.length];
        int[] menorDesde = new int[entradas.length];
        int[] menorHasta = new int[entradas.length];
        desdeCero[0] = 0;
        menorDesde[0] = 0;
        for(int i = 1; i < entradas.length; i++)
        {
            desdeCero[i] = desdeCero[i - 1] + entradas[i - 1].m - Math.abs(entradas[i].t - entradas[i - 1].t);
            menorDesde[i] = Math.min(menorDesde[i - 1], desdeCero[i]);
        }
        menorHasta[0] = 0;
        for(int i = entradas.length - 1; i >= 1; i--)
        {
        	int distancia = i == entradas.length - 1 ? (c - entradas[i].t) + entradas[0].t : Math.abs(entradas[i].t - entradas[i + 1].t);
            int delta = entradas[i].m - distancia;
        	hastaCero[i] = delta + hastaCero[(i + 1) % entradas.length];
            menorHasta[i] = Math.min(menorHasta[(i + 1) % entradas.length] + delta, delta);
        }
        for(int i = 0; i < entradas.length; i++)
        {
        	boolean valor = i == 0 ? simularUno(entradas) : ((hastaCero[i] >= 0 && menorHasta[i] >= 0)) && ((hastaCero[i] >= (-desdeCero[i])) && (hastaCero[i] >= (-menorDesde[i])));
                if(uno) 
                    entradas[i].uno = valor;
                else 
                    entradas[i].dos = valor;
        }
    }
    
    private static boolean simularUno(Entrada[] entradas)
    {
    	int actual = entradas[0].m;
    	for(int i = 0; i < entradas.length; i++)
    	{
    		int distancia = i == entradas.length - 1 ? (c - entradas[i].t) + entradas[0].t : Math.abs(entradas[i].t - entradas[i + 1].t);
    		actual = actual - distancia;
    		if(actual < 0)
    			return false;
    		if(i != entradas.length - 1)
    		actual += entradas[i + 1].m;
    	}
    	return actual >= 0;
	}

	static class Scanner
        {
                BufferedReader br;
                StringTokenizer st;
                
                public Scanner()
                {
                    System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
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
    
    static boolean orden;
    static class Entrada implements Comparable <Entrada>
    {
        int t;
        int m;
        boolean uno = false;
        boolean dos = false;
        
        public Entrada(int tt, int mm)
        {
            t = tt;
            m = mm;
        }

        @Override
        public int compareTo(Entrada o)
        {
            if(orden)
                return Integer.valueOf(t).compareTo(o.t);
            else
                return Integer.valueOf(o.t).compareTo(t);
        }
    }
    static int c;
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner sc = new Scanner();
        int caso = 1;
        while(true)
        {
            c = sc.nextInt();
            int s = sc.nextInt();
            if(c == 0 && s == 0)
                return;
            Entrada[] entradas = new Entrada[s];
            for(int i = 0; i < s; i++)
                entradas[i] = new Entrada(sc.nextInt(), sc.nextInt());
            orden = true;
            Arrays.sort(entradas);
            Entrada[] originales = entradas.clone();
            resolver(entradas, true);
            for(Entrada e : entradas)
            	e.t = c - e.t;
            Arrays.sort(entradas);
            resolver(entradas, false);
            for(Entrada e : entradas)
            	e.t = -(e.t - c);
            System.out.print("Case " + caso++ + ":");
            for(int i = 0; i < originales.length; i++)
            {
                if(!originales[i].uno && !originales[i].dos)
                    continue;
                System.out.print(" " + originales[i].t);
                if(originales[i].uno && originales[i].dos)
                    System.out.print(" CCC");
                else if(originales[i].uno)
                    System.out.print(" C");
                else if(originales[i].dos)
                    System.out.print(" CC");
            }
            System.out.println();
        }
    }
    
}


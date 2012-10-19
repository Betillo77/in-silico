import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class A 
{
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
    
    static int resolver(int acum, int actual, Integer[] factores, Integer[] veces, char[] numero)
    {
    	if(actual == factores.length)
    	{
    		char[] s = (acum + "").toCharArray();
    		for(char c : numero)
    			for(int i = 0; i < s.length; i++)
    				if(s[i] == c)
    					return 1;
    		return 0;
    	}
    	else
    	{
    		int cuenta = 0;
    		for(int i = 0; i <= veces[actual]; i++)
    		{
    			cuenta += resolver(acum, actual + 1, factores, veces, numero);
    			acum *= factores[actual];
    		}
    		return cuenta;
    	}
    }
    
	static int contar(int numero)
	{
		int ini = numero;
		int raiz = (int) Math.min(numero - 1, Math.ceil(Math.sqrt(numero)));
		ArrayList <Integer> factores = new ArrayList <Integer> ();
		ArrayList <Integer> veces = new ArrayList <Integer> ();
		for(int i = 2; i <= raiz; i++)
		{
			if(numero % i == 0)
			{
				int cuenta = 0;
				while(numero % i == 0)
				{
					cuenta++;
					numero /= i;
				}
				factores.add(i);
				veces.add(cuenta);
			}
		}
		if(numero != 1)
		{
			factores.add(numero);
			veces.add(1);
		}
		return resolver(1, 0, factores.toArray(new Integer[0]), veces.toArray(new Integer[0]), (ini + "").toCharArray());
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		System.out.println(contar(n));
	}

}

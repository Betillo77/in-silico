import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;


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
    
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		TreeMap <Integer, Integer> actuales = new TreeMap <Integer, Integer> ();
		int n = sc.nextInt();
		int k = sc.nextInt();
		int[] numeros = new int[3 * n];
		for(int i = 0; i < n; i++)
		{
			int nuevo = sc.nextInt();
			if(actuales.containsKey(nuevo))
				actuales.put(nuevo, actuales.get(nuevo) + 1);
			else
				actuales.put(nuevo, 1);
			numeros[i] = nuevo;
		}
		int cabeza = 0;
		int cola = n;
		for(int i = 0; i <= k; i++)
		{
			if(actuales.size() == 1)
			{
				System.out.println(i);
				return;
			}
			numeros[cola] = numeros[cabeza + (k - 1)];
			if(actuales.containsKey(numeros[cola]))
				actuales.put(numeros[cola], actuales.get(numeros[cola]) + 1);
			else
				actuales.put(numeros[cola], 1);
			actuales.put(numeros[cabeza], actuales.get(numeros[cabeza]) - 1);
			if(actuales.get(numeros[cabeza]) == 0)
				actuales.remove(numeros[cabeza]);
			cabeza++;
			cola++;
		}
		System.out.println("-1");
	}

}

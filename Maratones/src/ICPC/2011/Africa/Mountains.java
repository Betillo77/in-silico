import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Mountains 
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
    }
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int tests = sc.nextInt();
		for(int test = 0; test < tests; test++)
		{
			int n = sc.nextInt();
			TreeMap <Integer, Integer> mapa = new TreeMap <Integer, Integer> ();
			for(int i = 0; i < n; i++)
				mapa.put(sc.nextInt(), 0);
			n = sc.nextInt();
			int mejorDiferencia = Integer.MAX_VALUE;
			for(int i = 0; i < n; i++)
			{
				int siguiente = sc.nextInt();
				if(mapa.ceilingKey(siguiente) != null)
					mejorDiferencia = Math.min(mejorDiferencia, mapa.ceilingKey(siguiente) - siguiente);
				if(mapa.floorKey(siguiente) != null)
					mejorDiferencia = Math.min(mejorDiferencia, siguiente - mapa.floorKey(siguiente));
			}
			System.out.println(mejorDiferencia);
		}
	}
}

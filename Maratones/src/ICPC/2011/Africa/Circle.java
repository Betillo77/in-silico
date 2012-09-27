import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Circle 
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
		int[] vals = new int[100000];
		long[] sumaA = new long[100000];
		long[] sumaB = new long[100000];
		for(int test = 0; test < tests; test++)
		{
			int n = sc.nextInt();
			long suma = 0;
			for(int i = 0; i < n; i++)
			{
				vals[i] = sc.nextInt();
				suma += vals[i];
				sumaA[i] = suma;
			}
			long sumaT = 0;
			for(int i = n - 1; i >= 0; i--)
			{
				sumaT += vals[i];
				sumaB[i] = sumaT;
			}
			long min = suma - Math.max(vals[0], vals[n - 1]);
			for(int i = 1; i < n - 1; i++)
			{
				min = Math.min(min, 2 * sumaA[i - 1] + sumaB[i + 1]);
				min = Math.min(min, 2 * sumaB[i + 1] + sumaA[i - 1]);
			}
			System.out.println(min);
		}
	}
}

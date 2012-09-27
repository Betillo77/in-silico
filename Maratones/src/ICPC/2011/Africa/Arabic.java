import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Arabic 
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
			int words = sc.nextInt();
			ArrayList <String> antes = new ArrayList <String> ();
			ArrayList <String> despues = new ArrayList <String> ();
			boolean empezo = false;
			for(int i = 0; i < words; i++)
			{
				String word = sc.next();
				if(!word.replace("#", "").isEmpty())
				{
					empezo = true;
					antes.add(0, word);
				}
				else
				{
					if(empezo)
						despues.add(word);
					else
						antes.add(word);
				}
			}
			empezo = false;
			for(String s : despues)
			{
				if(!empezo)
					System.out.print(s);
				else
					System.out.print(" " + s);
				empezo = true;
			}
			for(String s : antes)
			{
				if(!empezo)
					System.out.print(s);
				else
					System.out.print(" " + s);
				empezo = true;
			}
			System.out.println();
		}
	}
}

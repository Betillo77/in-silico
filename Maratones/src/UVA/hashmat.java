package UVA;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class hashmat 
{
	static StringTokenizer st;
	static BufferedReader in;
	public static void main(String [] args)
	{
		eat("");
		in = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			while(true)
			{
				System.out.println(Math.abs(nextLong() - nextLong()));
			}
		}
		catch(Exception e)
		{	
		}
	}
	

	static void eat(String s) 
	{
		st = new StringTokenizer(s);
	}
	
	static String next() throws IOException 
	{
		while (!st.hasMoreTokens()) 
		{
			String line = in.readLine();
			if (line == null)
			{
				return null;
			}
			eat(line);
		}
		return st.nextToken();
	}
	
	static long nextLong() throws IOException 
	{
		return Long.parseLong(next());
	}

}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Web 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		String nextLine()
		{
			try { return br.readLine(); } catch(Exception e) { throw new RuntimeException(e); }
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
		
		Integer nextInteger()
		{
			String next = next();
			if(next == null)
				return null;
			return Integer.parseInt(next);
		}
		
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
	}
	
	public static void main(String[] args)
	{

		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			if(caso != 1)
				System.out.println();
			Stack <String> backward = new Stack <String> ();
			Stack <String> forward = new Stack <String> ();
			String current = "http://www.acm.org/";
			while(true)
			{
				String inst = sc.next();
				if(inst.equals("QUIT"))
					break;
				if(inst.equals("BACK"))
				{
					if(!backward.isEmpty())
					{
						forward.push(current);
						current = backward.pop();
						System.out.println(current);
					}
					else
						System.out.println("Ignored");
				}
				else if(inst.equals("FORWARD"))
				{
					if(!forward.isEmpty())
					{
						backward.push(current);
						current = forward.pop();
						System.out.println(current);
					}
					else
						System.out.println("Ignored");
				}
				else
				{
					backward.push(current);
					current = sc.next();
					forward.clear();
					System.out.println(current);
				}
			}
		}
	}
}

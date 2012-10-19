import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class CodeA 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		
		String nextLine()
		{
			try { return br.readLine(); } catch(Exception e) { throw new RuntimeException(e); }
		}
		
		String next()
		{
			while(!st.hasMoreTokens())
			{
				String line = nextLine();
				if(line == null) return null;
				st = new StringTokenizer(line);
			}
			return st.nextToken();
		}
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		int[] readNextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int x0 = sc.nextInt();
		int y0 = sc.nextInt();
		int x1 = sc.nextInt() - x0;
		int y1 = sc.nextInt() - y0;
		int x2 = sc.nextInt() - x0;
		int y2 = sc.nextInt() - y0;
		double a = Math.atan2(y1, x1);
		double b = Math.atan2(y2, x2);
		if(a < 0)
			a += 2 * Math.PI;
		if(b < 0)
			b += 2 * Math.PI;
		if(Math.abs(a - b) < 10e-8)
			System.out.println("TOWARDS");
		else if(a > b)
		{
			if(Math.abs(a - b) >= Math.PI)
				System.out.println("LEFT");
			else
				System.out.println("RIGHT");
		}
		else
			if(Math.abs(a - b) >= Math.PI)
				System.out.println("RIGHT");
			else
				System.out.println("LEFT");
	}

}

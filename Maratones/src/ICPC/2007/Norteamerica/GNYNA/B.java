import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;



public class B
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
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		double[] conv = new double[] { 2.2046, 0.4536, 0.2642, 3.7854};
		for(int caso = 1; caso <= casos; caso++)
		{
			double n = sc.nextDouble();
			String unit = sc.next();
			int val = 0;
			if(unit.equals("kg"))
				val = 0;
			else if(unit.equals("lb"))
				val = 1;
			else if(unit.equals("l"))
				val = 2;
			else
				val = 3;
			String otro = null;
			if(val == 0)
				otro = "lb";
			else if(val == 1)
				otro = "kg";
			else if(val == 2)
				otro = "g";
			else
				otro = "l";
			System.out.printf("%d %.4f %s\n", caso, n * conv[val], otro);
		}
	}

}

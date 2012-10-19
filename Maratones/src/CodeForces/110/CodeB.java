import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.StringTokenizer;


public class CodeB
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
		int n = sc.nextInt();
		int[] radios = sc.readNextIntArray(n);
		java.util.Arrays.sort(radios);
		long cuenta = 0;
		int c = 0;
		for(int i = n - 1; i >= 0; i--)
		{
			if((c & 1) == 0)
			{
				if(i == 0)
					cuenta += (radios[i] * radios[i]);
				else
					cuenta += (radios[i] * radios[i]) - (radios[i - 1] * radios[i - 1]);
			}
			c++;
		}
		System.out.println(BigDecimal.valueOf(Math.PI).multiply(BigDecimal.valueOf(cuenta)).toPlainString());
	}
}
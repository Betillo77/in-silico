import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class E 
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
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int numero = sc.nextInt();
			char[] vals = sc.next().toCharArray();
			boolean pudo = false;
			for(int i = vals.length - 2; i >= 0; i--)
			{
				if(vals[i] < vals[i + 1])
				{
					char menor = '9';
					int indiceMenor = -1;
					for(int j = i + 1; j < vals.length; j++)
					{
						if(vals[j] <= menor && vals[j] > vals[i])
						{
							menor = vals[j];
							indiceMenor = j;
						}
					}
					char temp = vals[i];
					vals[i] = vals[indiceMenor];
					vals[indiceMenor] = temp;
					Arrays.sort(vals, i + 1, vals.length);
					pudo = true;
					break;
				}
			}
			if(!pudo)
				System.out.println(numero + " BIGGEST");
			else
				System.out.println(numero + " " + new String(vals));
		}
	}
}
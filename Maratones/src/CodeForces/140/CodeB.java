import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;


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
		TreeMap <Integer, Integer> id = new TreeMap <Integer, Integer> ();
		TreeMap <Integer, Integer> di = new TreeMap <Integer, Integer> ();
		int n = sc.nextInt();
		int[] nums = sc.readNextIntArray(n);
		for(int i = 0; i < n; i++)
		{
			int numero = nums[i];
			if(!id.containsKey(numero))
				id.put(numero, i + 1);
			di.put(numero, (n - i));
		}
		int q = sc.nextInt();
		int[] qs = sc.readNextIntArray(q);
		long sId = 0;
		long sDi = 0;
		for(int i : qs)
		{
			if(id.containsKey(i))
			{
				sId += id.get(i);
				sDi += di.get(i);
			}
			else
			{
				sDi += n;
				sId += n;
			}
		}
		System.out.println(sId + " " + sDi);
	}
}

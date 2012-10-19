import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
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
	
	static boolean cumple(char[] a, char[] b)
	{
		for(int i = 0; i < a.length; i++)
			if(a[i] == b[i])
				return false;
		boolean mayor = a[0] < b[0];
		boolean bien = true;
		for(int i = 0; i < a.length; i++)
		{
			if(mayor != (a[i] < b[i]))
				bien = false;
		}
		return bien;
	}
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int nM = sc.nextInt();
		String s = sc.next();
		char[] a = s.substring(0, nM).toCharArray();
		char[] b = s.substring(nM, s.length()).toCharArray();
		Character[] b1 = new Character[b.length];
		for(int i = 0; i < b.length; i++)
			b1[i] = b[i];
		Arrays.sort(a);
		Arrays.sort(b);
		boolean bien = cumple(a, b);
		Arrays.sort(b1, Collections.reverseOrder());
		for(int i = 0; i < b.length; i++)
			b[i] = b1[i];
		bien = bien || cumple(a, b);
		System.out.println(bien ? "YES" : "NO");
	}
}

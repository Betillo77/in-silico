import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
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
		
		int nextInt()
		{
			return Integer.parseInt(next());
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
		int s = sc.nextInt();
		int n = sc.nextInt();
		int[][] dragones = new int[n][2];
		for(int i = 0; i < n; i++)
		{
			dragones[i][0] = sc.nextInt();
			dragones[i][1] = sc.nextInt();
		}
		Arrays.sort(dragones, new Comparator <int[]> ()
		{

			@Override
			public int compare(int[] o1, int[] o2)
			{
				return o1[0] - o2[0];
			}
		});
		boolean paila = false;
		for(int[] d : dragones)
		{
			if(s <= d[0])
			{
				paila = true;
				break;
			}
			s += d[1];
		}
		System.out.println(paila ? "NO" : "YES");
	}
}
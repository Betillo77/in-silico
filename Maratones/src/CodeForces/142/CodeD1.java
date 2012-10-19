import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class CodeD1 
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
	
	static int solve(int[] cities)
	{
		int n = cities.length;
		if(n == 1)
			return 0;
		int currentSum = cities[n - 1];
		int[][] current = new int[n][n + 1];
		current[n - 1][1] = 0;
		for(int i = n - 2; i >= 0; i--)
		{
			current[i][n - i] = n - i - 1;
			int a = n - 1;
			int b = n;
			int sumA = currentSum + cities[i];
			int sumB = 0;
			for(int j = n - i - 1; j >= 1; j--)
			{
				sumA -= cities[a];
				sumB += cities[a];
				a--;
				while(b > a + 1 && sumB >= sumA)
				{
					b--;
					sumB -= cities[b];
					if(b == a || sumB < sumA)
					{
						
						sumB += cities[b];
						b++;
						break;
					}
				}
				if(sumB < sumA)
					current[i][j] = Integer.MAX_VALUE;
				else if(current[a + 1][b - a - 1] == Integer.MAX_VALUE)
					current[i][j] = Integer.MAX_VALUE;
				else
					current[i][j] = (a - i) + current[a + 1][b - a - 1];
			}
			for(int j = n - i - 1; j >= 1; j--)
				current[i][j] = Math.min(current[i][j], current[i][j + 1]);
			currentSum += cities[i];
		}
		return current[0][1];
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		System.out.println(solve(sc.readNextIntArray(n)));
	}
}

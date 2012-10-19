import java.io.BufferedReader;
import java.io.InputStreamReader;
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
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		long nextLong()
		{
			return Long.parseLong(next());
		}
		
		
		private double nextDouble() 
		{
			return Double.parseDouble(next());
		}

		int[] nextIntArray(int n)
		{
			int[] nA = new int[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
		
		Integer[] nextIntegerArray(int n)
		{
			Integer[] nA = new Integer[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextInt();
			return nA;
		}
		
		long[] nextLongArray(int n)
		{
			long[] nA = new long[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextLong();
			return nA;
		}
		
		Long[] nextLongOArray(int n)
		{
			Long[] nA = new Long[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextLong();
			return nA;
		}
		
		double[] nextDoubleArray(int n)
		{
			double[] nA = new double[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextDouble();
			return nA;
		}

		Double[] nextDoubleOArray(int n)
		{
			Double[] nA = new Double[n];
			for(int i = 0; i < n; i++)
				nA[i] = nextDouble();
			return nA;
		}
		
		String[] nextStringArray(int n)
		{
			String[] nA = new String[n];
			for(int i = 0; i < n; i++)
				nA[i] = next();
			return nA;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		int k = sc.nextInt();
		int[] numbers = sc.nextIntArray(n);
		if(k == 1)
		{
			System.out.println("1 1");
			return;
		}
		int[] contained = new int[100010];
		int currentIn = 0;
		for(int i = 0; i < n; i++)
		{
			int current = numbers[i];
			if(contained[current] == 0)
				currentIn++;
			contained[current]++;
			if(currentIn == k)
			{
				int left = 0;
				int right = i + 1;
				while(currentIn == k && left < right)
				{
					contained[numbers[left]]--;
					if(contained[numbers[left]] == 0)
					{
						currentIn--;
						if(currentIn < k)
						{
							contained[numbers[left]]++;
							currentIn++;
							break;
						}
					}
					left++;
				}
				if(left == right)
				{
					left--;
					contained[left]++;
				}
				while(currentIn == k && left < right)
				{
					contained[numbers[right - 1]]--;
					if(contained[numbers[right - 1]] == 0)
					{
						currentIn--;
						if(currentIn < k)
						{
							contained[numbers[right - 1]]++;
							right++;
							currentIn++;
							break;
						}
					}
					right--;
				}
				System.out.println((left + 1) + " " + (right - 1));
				return;
			}
		}
		System.out.println("-1 -1");
	}
}

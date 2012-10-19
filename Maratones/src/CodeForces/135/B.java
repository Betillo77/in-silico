import java.util.Scanner;


public class B 
{
	static long solve(String n, long maxDiff)
	{
		long best = Long.parseLong(n);
		for(int i = 1; i < n.length(); i++)
		{
			String left = "";
			for(int j = 0; j < i; j++)
				left += "9";
			String right = n.substring(0, n.length() - i);
			String lf = n.substring(n.length() - i, n.length());
			if(Long.parseLong(lf) < Long.parseLong(left))
			{
				right = "" + (Long.parseLong(right) - 1);
			}
			long result = Long.parseLong(right + left);
			if(result >= 0 && Long.parseLong(n) - result <= maxDiff)
				best = result;
		}
		return best;
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		long a = sc.nextLong();
		long b = sc.nextLong();
		System.out.println(solve(a + "", b));
	}

}

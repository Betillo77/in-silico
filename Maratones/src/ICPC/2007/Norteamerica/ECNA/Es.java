import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;


public class Es
{
	static boolean[] ones = new boolean[] { true, false, true, false, true, false, true, false, false, false};
	static boolean[] tens = new boolean[] { true, false, false, true, true, true, true, false, false, false};
	
	static boolean possible(int n)
	{
		int t = n / 10;
		int o = n % 10;
		return tens[t] && ones[o];
	}
	
	static int[] currentNumber;
	static Long[][] dp;
	static BigInteger oneT = BigInteger.TEN.pow(2).multiply(BigInteger.TEN);
	
	static long giveCount(BigInteger num)
	{
		ArrayList <Integer> as = new ArrayList <Integer> ();
		while(!num.equals(BigInteger.ZERO))
		{
			as.add(num.mod(oneT).intValue());
			num = num.divide(oneT);
		}
		currentNumber = new int[as.size()];
		for(int i = 0; i < as.size(); i++)
			currentNumber[i] = as.get(as.size() - 1 - i);
		dp = new Long[as.size() + 1][2];
		return count(as.size() - 1, true) - 1;
	}
	
	static long count(int current, boolean top)
	{
		if(dp[current][top ? 1 : 0] != null)
			return dp[current][top ? 1 : 0];
		int place = current;
		if(place == 0)
		{
			int limite = top ? Math.min(currentNumber[currentNumber.length - 1 - current] + 1, 100) : 100; 
			long c = 0;
			for(int i = 0; i < limite; i++)
				if(possible(i))
					c++;
			return dp[current][top ? 1 : 0] = c;
		}
		else if(place == 7 || place == 8)
		{
			return dp[current][top ? 1 : 0] = count(place - 1, top && currentNumber[currentNumber.length - 1 - current] == 0);
		}
		else
		{
			long c = 0;
			int limite = top ? Math.min(currentNumber[currentNumber.length - 1 - current] + 1, 100) : 100; 
			for(int i = 0; i < limite; i++)
				if(possible(i))
					c += count(current - 1, top && i == currentNumber[currentNumber.length - 1 - current]);
			return dp[current][top ? 1 : 0] = c;
		}
	}
	
	public static BigInteger binSearch(long searched, BigInteger a, BigInteger b)
	{
		BigInteger mid = a.add(b).shiftRight(1);
		long cMid = giveCount(mid);
		if(cMid < searched)
			return binSearch(searched, mid.add(BigInteger.ONE), b);
		else if(cMid > searched)
			return binSearch(searched, a, mid.subtract(BigInteger.ONE));
		long cMidL = giveCount(mid.subtract(BigInteger.ONE));
		if(cMid == cMidL)
			return binSearch(searched, a, mid.subtract(BigInteger.ONE));
		return mid;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLong())
		{
			long n = sc.nextLong();
			if(n == 0)
				return;
			System.out.println(convertir(binSearch(n, BigInteger.ZERO, BigInteger.TEN.pow(30))));
		}
	}

	private static String convertir(BigInteger num) 
	{
		ArrayList <Integer> as = new ArrayList <Integer> ();
		while(!num.equals(BigInteger.ZERO))
		{
			as.add(num.mod(oneT).intValue());
			num = num.divide(oneT);
		}
		currentNumber = new int[as.size()];
		for(int i = 0; i < as.size(); i++)
			currentNumber[i] = as.get(as.size() - 1 - i);
		String res = "";
		for(int i = 0; i < as.size(); i++)
			if(i == 0)
				res += currentNumber[i];
			else
				res += "," + (currentNumber[i] < 10 ? "00" : currentNumber[i] < 100 ? "0" : "0") + currentNumber[i];
			return res;
	}

}

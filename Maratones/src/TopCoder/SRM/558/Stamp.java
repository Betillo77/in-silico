

public class Stamp
{	
	static char[] colors;
	
	static Long[][][][] dp = new Long[51][51][51][3];
	
	static long dp(int actual, int l, int cLength, char anterior)
	{
		if(actual == colors.length)
			return dp[actual][l][cLength][anterior == 'R' ? 0 : anterior == 'G' ? 1 : 2] = 0L;
		if(dp[actual][l][cLength][anterior == 'R' ? 0 : anterior == 'G' ? 1 : 2] != null)
			return dp[actual][l][cLength][anterior == 'R' ? 0 : anterior == 'G' ? 1 : 2];
		long minCost = cLength == 0 ? 1000000000L : dp(actual + 1, l, cLength - 1, anterior);
		if(cLength > 0)
		{
			if(posibleColor(actual, l, anterior))
				minCost = Math.min(minCost, 1 + dp(actual + 1, l, l - 1, anterior));
		}
		else
		{
			if(posibleColor(actual, l, 'R'))
				minCost = Math.min(minCost, 1 + dp(actual + 1, l, l - 1, 'R'));
			if(posibleColor(actual, l, 'G'))
				minCost = Math.min(minCost, 1 + dp(actual + 1, l, l - 1, 'G'));
			if(posibleColor(actual, l, 'B'))
				minCost = Math.min(minCost, 1 + dp(actual + 1, l, l - 1, 'B'));
		}
		return dp[actual][l][cLength][anterior == 'R' ? 0 : anterior == 'G' ? 1 : 2] = minCost;
	}

	static Boolean[][][] posDp = new Boolean[51][51][3];
	
	private static boolean posibleColor(int actual, int l, char anterior) 
	{
		if(posDp[actual][l][anterior == 'R' ? 0 : anterior == 'G' ? 1 : 2] != null)
			return posDp[actual][l][anterior == 'R' ? 0 : anterior == 'G' ? 1 : 2];
		if(actual + l > colors.length)
			return posDp[actual][l][anterior == 'R' ? 0 : anterior == 'G' ? 1 : 2] = false;
		for(int i = actual; i < actual + l; i++)
			if(colors[i] != '*' && colors[i] != anterior)
				return posDp[actual][l][anterior == 'R' ? 0 : anterior == 'G' ? 1 : 2] = false;
		return posDp[actual][l][anterior == 'R' ? 0 : anterior == 'G' ? 1 : 2] = true;
	}

	public static int getMinimumCost(String desiredColor, int stampCost, int pushCost)
	{
		colors = desiredColor.toCharArray();
		long minimum = Integer.MAX_VALUE;
		for(int i = 1; i <= colors.length; i++)
		{
			long min = i * ((long) stampCost) + dp(0, i, 0, 'R') * pushCost;
			minimum = Math.min(min, minimum);
		}
		return (int) minimum;
	}
}
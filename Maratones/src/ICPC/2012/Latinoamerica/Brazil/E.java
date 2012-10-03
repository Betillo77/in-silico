
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class E 
{
    static BigInteger binSearch(long ix, BigInteger a, BigInteger b)
    {
        BigInteger m = (a.add(b)).shiftRight(1);
        BigInteger x = f(m).subtract(BigInteger.ONE);
        int cmp = BigInteger.valueOf(ix).compareTo(x);
        if(cmp > 0)
            return binSearch(ix, m.add(BigInteger.ONE), b);
        else if(cmp < 0)
            return binSearch(ix, a, m.subtract(BigInteger.ONE));
        if(valid(m))
            return m;
        return binSearch(ix, a, m.subtract(BigInteger.ONE));
    }
    
    private static boolean valid(BigInteger m) 
    {
        String num = m + "";
        return !num.contains("4") && !num.contains("13");
    }
    
    private static BigInteger f(BigInteger m1) 
    {
        String num = m1 + "";
        m = num.toCharArray();
        for(int i = 0; i <= m.length; i++)
            for(int j = 0; j < 2; j++)
                for(int k = 0; k < 2; k++)
                    dp[i][j][k] = null;
        return dp(0, true, false);
    }
    
    static char[] m;

    static BigInteger[][][] dp = new BigInteger[40][2][2];

    private static BigInteger dp(int dig, boolean tope, boolean ant1)
    {
        if(dp[dig][tope ? 1 : 0][ant1 ? 1 : 0] != null)
            return dp[dig][tope ? 1 : 0][ant1 ? 1 : 0];
        if(dig == m.length)
            return BigInteger.ONE;
        BigInteger ans = BigInteger.ZERO;
        int ntope = tope ? m[dig] - '0' : 9;
        for(int i = 0; i <= ntope; i++)
            if(!(i == 4 || (i == 3 && ant1)))
                ans = ans.add(dp(dig + 1, tope && i == ntope, i == 1));
        return dp[dig][tope ? 1 : 0][ant1 ? 1 : 0] = ans;
    }
    
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            String li = br.readLine();
            if(li == null)
                return;
            long n = Long.parseLong(li.trim());
            System.out.println(binSearch(n, BigInteger.ZERO, BigInteger.TEN.pow(30)));
        }
    }
}

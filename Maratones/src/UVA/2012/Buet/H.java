import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class H
{

	static class Scanner
    {
            BufferedReader br;
            StringTokenizer st;
            
            public Scanner()
            {
                    br = new BufferedReader(new InputStreamReader(System.in));
            }
            
            public String next()
            {

                    while(st == null || !st.hasMoreTokens())
                    {
                            try { st = new StringTokenizer(br.readLine()); }
                            catch(Exception e) { throw new RuntimeException(); }
                    }
                    return st.nextToken();
            }

            public int nextInt()
            {
                    return Integer.parseInt(next());
            }
            
            public long nextLong()
            {
                    return Long.parseLong(next());
            }
            
            public double nextDouble()
            {
                    return Double.parseDouble(next());
            }
            
            public String nextLine()
            {
                    st = null;
                    try { return br.readLine(); }
                    catch(Exception e) { throw new RuntimeException(); }
            }
    }
	
	static long m;
	static int base;
	static int d;
	
	static long solve(long n)
	{
		if(n == 0)
			return 0;
		if(n == 1)
			return d % m;
		if((n & 1) == 1)
		{
			long r = solve(n / 2);
			long right = (r * base + d) % m;
			long shift = n / 2;
			long left = r;
			return (modmul(right, modPow(base, shift), m) + left) % m;
		}
		else
		{
			long r = solve(n / 2);
			return (modmul(r, modPow(base, n / 2), m) + r) % m;
		}
	}
	
	static long modPow(int base, long pow)
	{
		if(pow == 0)
			return 1;
		if(pow == 1)
			return base % m;
		long res = modPow(base, pow >> 1);
		res = modmul(res, res, m);
		if((pow & 1) == 1)
			res = modmul(res, base, m);
		return res;
	}

	static long modmul(long a, long b, long m)
	{
	    if(a>m)
	        a=a%m;
	    if(b>m)
	        b=b%m;
	    long ret = 0;
	    if(a<b)
	    {
	    	long temp = a;
	    	a = b;
	    	b = temp;
	    }
	    while(b > 0)
	    {
	        while(a<m)
	        {
	            if((b&1) != 0)
	                ret += a;
	            a<<=1;
	            b>>=1;
	        }
	        a-=m;
	        while(ret>=m)
	            ret-=m;
	        if(a<b)
		    {
		    	long temp = a;
		    	a = b;
		    	b = temp;
		    }
	    }
	    return ret % m;
	};
	 
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int i = 0; i < casos; i++)
		{
			long n = sc.nextLong();
			base = sc.nextInt();
			d = sc.nextInt();
			m = sc.nextLong();
			System.out.println("Case " + (i + 1) + ": " + solve(n));
		}
	}
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;


public class Message 
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
             
             public boolean endLine()
             {
                     try 
                     {
                             String next = br.readLine();
                             while(next != null && next.trim().isEmpty())
                                     next = br.readLine();
                             if(next == null)
                                     return true;
                             st = new StringTokenizer(next);
                             return st.hasMoreTokens();
                     }
                     catch(Exception e) { throw new RuntimeException(); }
             }
     }
	 
	static int[] keys = new int[4];
	
	static HashMap <Integer, String> mapa = new HashMap <Integer, String> ();
	
	static void agregar(String s)
	{
		String res = "";
		for(char c : s.toCharArray())
		{
			if(c == ' ')
				res += "27";
			else
			{
				int num = c - 'A' + 1;
				if(num < 10)
					res += "0";
				res += num;
			}
		}
		while(res.length() > 1 && res.charAt(0) == '0')
			res = res.substring(1);
		int num = Integer.parseInt(res);
		String key = "";
		for(int i = 0; i < 4; i++)
		{
			int n = num % keys[i];
			if(n < 10)
				key += "0";
			key += n;
		}
		while(key.length() > 1 && key.charAt(0) == '0')
			key = key.substring(1);
		mapa.put(Integer.parseInt(key), s);
	}
 
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		for(int i = 0; i < n; i++)
		{
			int m = sc.nextInt();
			for(int j = 0; j < 4; j++)
				keys[j] = sc.nextInt();
			mapa.clear();
			for(int a = 0; a <= 26; a++)
			{
				String aS = ((char) (a == 26 ? ' ' : a + 'A')) + "";
				agregar(aS);
				for(int b = 0; b <= 26; b++)
				{
					String bS = aS + ((char) (b == 26 ? ' ' : b + 'A')) + "";
					agregar(bS);
					for(int c = 0; c <= 26; c++)
					{
						String cS = bS + ((char) (c == 26 ? ' ' : c + 'A')) + "";
						agregar(cS);
					}
				}
			}
			String result = "";
			for(int j = 0; j < m; j++)
				result += mapa.get(sc.nextInt());
			while(result.charAt(result.length() - 1) == ' ')
				result = result.substring(0, result.length() - 1);
			System.out.println(result);
		}
	}
}
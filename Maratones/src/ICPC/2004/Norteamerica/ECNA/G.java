import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class G 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		String nextLine()
		{
			try
			{
				String s = br.readLine();
				return s;
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
		}
		
		String next()
		{
			while(st == null || !st.hasMoreTokens())
			{
				String linea = nextLine();
				if(linea == null)
					return null;
				st = new StringTokenizer(linea);
			}
			return st.nextToken();
		}

		public int nextInt() 
		{
			return Integer.parseInt(next());
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			String s = sc.next();
			char[][] mensaje = new char[s.length() / n][n];
			int actual = 0;
			for(int i = 0; i < s.length() / n; i++)
				for(int j = 0; j < n; j++)
					mensaje[i][((i & 1) == 0) ? j : n - 1 - j] = s.charAt(actual++);
			String res = "";
			for(int i = 0; i < n; i++)
				for(int j = 0; j < s.length() / n; j++)
					res += mensaje[j][i];
			System.out.println(res);
		}
	}

}

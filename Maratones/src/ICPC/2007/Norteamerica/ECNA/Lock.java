import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class Lock 
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
	 
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			int a = sc.nextInt();
			int b = sc.nextInt();
			int c = sc.nextInt();
			LinkedList <int[]> movimientos = new LinkedList <int[]> ();
			int[] actual = null;
			while(true)
			{
				String d = sc.next();
				if(d.equals("?"))
					break;
				int d1 = d.equals("C") ? 1 : 0;
				if(actual == null || actual[0] != d1)
				{
					if(actual != null)
						movimientos.add(actual);
					actual = new int[2];
					actual[0] = d1;
				}
				actual[1] += sc.nextInt();
			}
			if(actual != null)
				movimientos.add(actual);
			boolean paila = false;
			int curr = 0;
			while(movimientos.size() > 3)
			{
				int[] inicio = movimientos.poll();
				if(inicio[0] == 1)
				{
					curr -= inicio[1];
					while(curr < 0)
						curr += n;
				}
				else
				{
					curr += inicio[1];
					curr %= n;
				}
			}
			if(movimientos.size() != 3 || movimientos.get(0)[0] != 1 || movimientos.get(1)[0] != 0 || movimientos.get(2)[0] != 1 || movimientos.get(0)[1] < n || movimientos.get(1)[1] < n || movimientos.get(1)[1] >= 2 * n || movimientos.get(2)[1] > n)
				paila = true;
			else
			{
				curr -= movimientos.get(0)[1];
				while(curr < 0)
					curr += n;
				if(curr != a)
				{
					paila = true;
				}
				curr += movimientos.get(1)[1];
				curr %= n;
				if(curr != b)
				{
					paila = true;
				}
				curr -= movimientos.get(2)[1];
				while(curr < 0)
					curr += n;
				if(curr != c)
				{
					paila = true;
				}
			}
			System.out.println("Case " + caso++ + ": " + (!paila ? "Open" : "Closed"));
		}
	}
}

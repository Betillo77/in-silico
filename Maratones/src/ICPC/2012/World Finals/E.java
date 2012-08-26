import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class E
{

	
	static int n;
	static long[][] sets;
	static long[][] backtrack;
	static LinkedList <Integer> valores;
	
	private static boolean intentar(long[] ls, int actual, int unos) 
	{
		if(actual == n || unos == 0)
		{
			if((Long.bitCount(ls[0]) + Long.bitCount(ls[1])) == n)
			{
				System.out.print(valores.size());
				for(int i : valores)
					System.out.print(" " + i);
				System.out.println();
				return true;
			}
			else
				return false;
		}
		else
		{
			backtrack[actual][0] = ls[0];
			backtrack[actual][1] = ls[1];
			if(unos + actual + 1 <= n)
				if(intentar(ls, actual + 1, unos))
					return true;
			ls[0] = backtrack[actual][0]; 
			ls[1] = backtrack[actual][1];
			ls[0] |= sets[actual][0];
			ls[1] |= sets[actual][1];
			valores.add(actual + 1);
			if(intentar(ls, actual + 1, unos - 1))
				return true;
			valores.pollLast();
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int caso = 1;
		while(true)
		{
			String linea = br.readLine();
			if(linea == null)
				return;
			n = Integer.parseInt(linea.trim());
			sets = new long[n][2];
			for(int i = 0; i < n; i++)
			{
				int j = 0;
				for(char c : br.readLine().trim().toCharArray())
				{
					if(c == '1')
						sets[i][j >= 64 ? 1 : 0] |= (1L << (j % 64));
					j++;
				}
				sets[i][i >= 64 ? 1 : 0] |= (1L << (i % 64));
			}
			System.out.print("Case " + caso++ + ": ");
			backtrack = new long[n][2];
			valores = new LinkedList <Integer> ();
			for(int i = 1; true; i++)
				if(intentar(new long[2], 0, i))
					break;
		}
	}
}

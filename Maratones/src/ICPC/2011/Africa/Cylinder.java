import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Cylinder 
{
	static class Scanner
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        String nextLine()
        {
            String next;
            try 
            {
                next = br.readLine();
            } 
            catch (IOException ex) 
            {
                throw(new RuntimeException(ex));
            }
            return next;
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
        
        int nextInt()
        {
            return Integer.parseInt(next());
        }
    }
	
	static class Respuesta implements Comparable <Respuesta>
	{
		int sum;
		int currentValue;
		int i;
		int j;
		Respuesta siguiente;
		
		public Respuesta(Respuesta s, int su, int l, int currentRow, int i2) 
		{
			siguiente = s;
			sum = su;
			currentValue = l;
			i = currentRow;
			j = i2;
		}

		@Override
		public int compareTo(Respuesta o) 
		{
			if(sum == o.sum)
			{
				if(currentValue == o.currentValue)
				{
					if(i == o.i)
						return j - o.j;
					else
						return i - o.i;
				}
				else
					return currentValue - o.currentValue;
			}
			return o.sum - sum;
		}
	}
	
	static Respuesta[] temp = new Respuesta[3000];
	
	static Respuesta[] generateBest(Respuesta[] current, int k)
	{
		Respuesta[] respuesta = new Respuesta[current.length];
		Respuesta[] t = temp;
		k = Math.min(k, current.length);
		for(int i = 0; i < current.length; i++)
		{
			t[i] = current[i];
			t[i + current.length] = current[i];
			t[i + current.length + current.length] = current[i];
		}
		PriorityQueue <Respuesta> pq = new PriorityQueue <Respuesta> ();
		pq.add(t[current.length]);
		int limitI = current.length;
		for(int i = current.length - 1; Math.abs(current.length - i) <= k - 1; i--)
		{
			limitI = i;
			pq.add(t[i]);
		}
		int limitJ = current.length;
		for(int i = current.length + 1; Math.abs(current.length - i) <= k - 1; i++)
		{
			limitJ = i;
			pq.add(t[i]);
		}
		for(int i = 0; i < current.length; i++)
		{
			respuesta[i] = pq.peek();
			if(i == current.length - 1)
				break;
			pq.remove(t[limitI++]);
			pq.add(t[++limitJ]);
		}
		return respuesta;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int tests = sc.nextInt();
		int[][] tablero = new int[1000][1000];
		for(int test = 0; test < tests; test++)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			int k = sc.nextInt();
			for(int i = 0; i < n; i++)
				for(int j = 0; j < m; j++)
					tablero[i][j] = sc.nextInt();
			int currentRow = n - 1;
			Respuesta[] current = new Respuesta[m];
			for(int i = 0; i < m; i++)
				current[i] = new Respuesta(null, tablero[currentRow][i], i + 1, currentRow, i);
			Respuesta[] bestLast = generateBest(current, k);
			while(--currentRow != -1)
			{
				for(int i = 0; i < m; i++)
					current[i] = new Respuesta(bestLast[i], bestLast[i].sum + tablero[currentRow][i], i + 1, currentRow, i);
				bestLast = generateBest(current, k);
			}
			Respuesta best = null;
			for(int i = 0; i < m; i++)
				if(best == null || best.compareTo(current[i]) > 0)
					best = current[i];
			System.out.print(best.sum);
			while(best != null)
			{
				System.out.print(" " + best.currentValue);
				best = best.siguiente;
			}
			System.out.println();
		}
	}
}
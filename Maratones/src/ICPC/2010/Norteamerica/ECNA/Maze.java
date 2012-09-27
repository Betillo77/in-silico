import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class Maze 
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
	
	static class Entrada
	{
		int x;
		int y;
		int z;
		String respuesta;
		
		public Entrada(int x, int y, int z, String respuesta)
		{
			this.x = x;
			this.y = y;
			this.z = z;
			this.respuesta = respuesta;
		}
	}
	
	static class Cara
	{
		int[] info = new int[4];
		char[][] cara;
		
		public Cara(int i, int j, int k, int l) 
		{
			info[0] = i;
			info[1] = j;
			info[2] = k;
			info[3] = l;
		}

		public boolean bien(int x, int y, int z)
		{
			int valX = 0;
			int valY = 0;
			if(info[0] == 0)
				valX = x;
			else if(info[0] == 1)
				valX = y;
			else
				valX = z;
			if(info[1] == 0)
				valX = n - 1 - valX;
			if(info[2] == 0)
				valY = x;
			else if(info[2] == 1)
				valY = y;
			else
				valY = z;
			if(info[3] == 0)
				valY = n - 1 - valY;
			return cara[valY][valX] == ' ';
		}
	}
	
	static int n;
	static final int[][] deltas = new int[][] {{0, 0, -1}, {0, 0, 1}, {1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}};
	static final String deltasS = "FBLRUD";
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Cara[] caras = new Cara[6];
		caras[0] = new Cara(0, 0, 1, 0);
		caras[1] = new Cara(2, 1, 1, 0);
		caras[2] = new Cara(0, 1, 1, 0);
		caras[3] = new Cara(2, 0, 1, 0);
		caras[4] = new Cara(0, 0, 2, 0);
		caras[5] = new Cara(0, 0, 2, 1);
		while(true)
		{
			n = Integer.parseInt(br.readLine().trim());
			if(n == 0)
				return;
			for(int i = 0; i < 6; i++)
			{
				char[][] c = new char[n][];
				for(int j = 0; j < n; j++)
					c[j] = br.readLine().trim().toCharArray();
				caras[i].cara = c;
			}
			boolean[][][] visitados = new boolean[n][n][n];
			LinkedList <Entrada> entradas = new LinkedList <Entrada> ();
			Entrada primera = new Entrada(1, 1, 1, "");
			entradas.add(primera);
			visitados[1][1][1] = true;
			while(!entradas.isEmpty())
			{
				Entrada actual = entradas.poll();
				if(actual.x == n - 2 && actual.y == n - 2 && actual.z == n - 2)
				{
					System.out.println(actual.respuesta);
					break;
				}
				outer:
					for(int i = 0; i < deltas.length; i++)
					{
						int xN = actual.x + deltas[i][0];
						int yN = actual.y + deltas[i][1];
						int zN = actual.z + deltas[i][2];
						if(xN < 0 || xN >= n - 1 || yN < 0 || yN >= n - 1 || zN < 0 || zN >= n - 1 || visitados[xN][yN][zN])
							continue;
						for(int j = 0; j < 6; j++)
							if(!caras[j].bien(xN, yN, zN))
								continue outer;
						visitados[xN][yN][zN] = true;
						entradas.add(new Entrada(xN, yN, zN, actual.respuesta + deltasS.charAt(i)));
					}
			}
		}
	}

}

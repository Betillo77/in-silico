import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Lemmings
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
	 
	static class Lemming
	{
		int actual = 0;
		String plan;
		boolean quieto;
		
		public Lemming(String next) 
		{
			plan = next;
		}

		int quiereI(int i)
		{
			if(plan.charAt(actual) == 'N')
				return i + 1;
			else if(plan.charAt(actual) == 'S')
				return i - 1;
			return i;
		}
		
		int quiereJ(int j)
		{			
			if(plan.charAt(actual) == 'E')
				return j + 1;
			else if(plan.charAt(actual) == 'W')
				return j - 1;
			return j;
		}
	}
	
	static int simular(Lemming[][] tablero)
	{
		int rondas = 0;
		int[][] quieren = new int[tablero.length][tablero[0].length];
		Lemming[][] temporal = new Lemming[tablero.length][tablero[0].length];
		Lemming[][] cualQuiere = new Lemming[tablero.length][tablero[0].length];
		int[][][] cualQuierePos = new int[tablero.length][tablero[0].length][2];
		while(true)
		{
			for(int i = 0; i < tablero.length; i++)
				for(int j = 0; j < tablero[0].length; j++)
				{
					quieren[i][j] = 0;
					temporal[i][j] = null;
					cualQuiere[i][j] = null;
					if(tablero[i][j] != null)
						tablero[i][j].quieto = false;
				}
			for(int i = 0; i < tablero.length; i++)
				for(int j = 0; j < tablero[0].length; j++)
				{
					if(tablero[i][j] != null)
					{
						int iN = tablero[i][j].quiereI(i);
						int jN = tablero[i][j].quiereJ(j);
						if(iN < 0 || jN < 0 || iN == tablero.length || jN == tablero[0].length)
							continue;
						quieren[iN][jN]++;
					}
				}
			for(int i = 0; i < tablero.length; i++)
				for(int j = 0; j < tablero[0].length; j++)
				{
					if(tablero[i][j] != null)
					{
						int iN = tablero[i][j].quiereI(i);
						int jN = tablero[i][j].quiereJ(j);
						if(iN < 0 || jN < 0 || iN == tablero.length || jN == tablero[0].length)
							continue;
						if(quieren[iN][jN] > 1)
							tablero[i][j].quieto = true;
						else
						{
							cualQuiere[iN][jN] = tablero[i][j];
							cualQuierePos[iN][jN][0] = i;
							cualQuierePos[iN][jN][1] = j;
						}
					}
				}
			for(int i = 0; i < tablero.length; i++)
				for(int j = 0; j < tablero[0].length; j++)
				{
					if(tablero[i][j] != null && tablero[i][j].quieto)
					{
						int iAct = i;
						int jAct = j;
						tablero[i][j].quieto = false;
						while(cualQuiere[iAct][jAct] != null && !tablero[iAct][jAct].quieto)
						{
							tablero[iAct][jAct].quieto = true;
							int iAct1 = cualQuierePos[iAct][jAct][0];
							jAct = cualQuierePos[iAct][jAct][1];
							iAct = iAct1;
						}
						tablero[iAct][jAct].quieto = true;
					}
				}
			boolean hay = false;
			for(int i = 0; i < tablero.length; i++)
				for(int j = 0; j < tablero[0].length; j++)
				{
					if(tablero[i][j] != null)
					{
						int iN = tablero[i][j].quiereI(i);
						int jN = tablero[i][j].quiereJ(j);
						if(iN < 0 || jN < 0 || iN == tablero.length || jN == tablero[0].length)
							continue;
						hay = true;
						if(tablero[i][j].quieto)
						{
							temporal[i][j] = tablero[i][j];	
							temporal[i][j].actual++;
							temporal[i][j].actual &= 3;
						}
						else
							temporal[iN][jN] = tablero[i][j]; 
					}
				}
			rondas++;
			if(!hay)
				return rondas;
			Lemming[][] t = temporal;
			temporal = tablero;
			tablero = t;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			if(n == 0 && m == 0)
				return;
			Lemming[][] board = new Lemming[n][m];
			for(int i = 0; i < n; i++)
				for(int j = 0; j < m; j++)
					board[i][j] = new Lemming(sc.next());
			System.out.println("Case " + caso++ + ": " + simular(board));
		}
	}
}
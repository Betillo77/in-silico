import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class Dice 
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
	
	static int[] dice;

	static int other(int face)
	{
		switch(face)
		{
			case 0: return 1;
			case 1 : return 0;
			case 2 : return 3;
			case 3 : return 2;
			case 4 : return 5;
			case 5 : return 4;
		}
		return -1;
	}
	
	static int[] nextState(int right, int forward, int top, int direction)
	{
		int[] next = new int[3];
		if(direction == 0)
		{
			next[0] = top;
			next[1] = forward;
			next[2] = other(right);
		}
		else if(direction == 1)
		{
			next[0] = other(top);
			next[1] = forward;
			next[2] = right;
		}
		else if(direction == 2)
		{
			next[0] = right;
			next[1] = other(top);
			next[2] = forward;
		}
		else
		{
			next[0] = right;
			next[1] = top;
			next[2] = other(forward);
		}
		return next;
	}
	
	static class Arista
	{
		Nodo u;
		Nodo v;
		int costo;
		
		public Arista(Nodo origen, Nodo nodo)
		{
			u = origen;
			v = nodo;
			if(v.i == iF && v.j == jF)
				costo = 0;
			else
			{
				if(dice[other(v.m)] == tablero[v.i][v.j])
					costo = -(dice[other(v.m)] + tablero[v.i][v.j]);
				else
					costo = (dice[other(v.m)] + tablero[v.i][v.j]);
			}
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((u == null) ? 0 : u.hashCode());
			result = prime * result + ((v == null) ? 0 : v.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Arista other = (Arista) obj;
			if (u == null) {
				if (other.u != null)
					return false;
			} else if (!u.equals(other.u))
				return false;
			if (v == null) {
				if (other.v != null)
					return false;
			} else if (!v.equals(other.v))
				return false;
			return true;
		}
	}
	
	static class Nodo
	{
		int i, j, k, l, m;
		int distancia = Integer.MAX_VALUE;
		ArrayList <Arista> aristas = new ArrayList <Arista> ();

		public Nodo(int i2, int j2, int k2, int l2, int m2) 
		{
			i = i2;
			j = j2;
			k = k2;
			l = l2;
			m = m2;
		}

		@Override
		public int hashCode() 
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + i;
			result = prime * result + j;
			result = prime * result + k;
			result = prime * result + l;
			result = prime * result + m;
			return result;
		}

		@Override
		public boolean equals(Object obj) 
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Nodo other = (Nodo) obj;
			if (i != other.i)
				return false;
			if (j != other.j)
				return false;
			if (k != other.k)
				return false;
			if (l != other.l)
				return false;
			if (m != other.m)
				return false;
			return true;
		}
		
 	}
	
	static Nodo[][][][][] states;
	static int n, m;
	static int[][] tablero;
	
	private static boolean valido(int k, int l, int m) 
	{
		if(other(k) == l || other(k) == m || other(l) == k || other(l) == m || other(m) == k || other(m) == l)
			return false;
		else
			return true;
	}
	

	private static boolean valido(int i, int j)
	{
		return i >= 0 && i < n && j >= 0 && j < m && tablero[i][j] != -1;
	}
	
	static void construir()
	{
		states = new Nodo[n][m][6][6][6];
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				for(int k = 0; k < 6; k++)
					for(int l = 0; l < 6; l++)
						for(int m = 0; m < 6; m++)
							states[i][j][k][l][m] = new Nodo(i, j, k, l, m);
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				for(int k = 0; k < 6; k++)
					for(int l = 0; l < 6; l++)
						for(int m = 0; m < 6; m++)
							if(valido(i, j) && valido(k, l, m))
							{
								Nodo actual = states[i][j][k][l][m];
								if(valido(i, j + 1))
								{
									int[] siguiente = nextState(k, l, m, 0);
									actual.aristas.add(new Arista(actual, states[i][j + 1][siguiente[0]][siguiente[1]][siguiente[2]]));
								}
								if(valido(i, j - 1))
								{
									int[] siguiente = nextState(k, l, m, 1);
									actual.aristas.add(new Arista(actual, states[i][j - 1][siguiente[0]][siguiente[1]][siguiente[2]]));
								}
								if(valido(i - 1, j))
								{
									int[] siguiente = nextState(k, l, m, 2);
									actual.aristas.add(new Arista(actual, states[i - 1][j][siguiente[0]][siguiente[1]][siguiente[2]]));
								}
								if(valido(i + 1, j))
								{
									int[] siguiente = nextState(k, l, m, 3);
									actual.aristas.add(new Arista(actual, states[i + 1][j][siguiente[0]][siguiente[1]][siguiente[2]]));
								}
							}
	}
	
	static int solve(int iI, int jI, int iF, int jF)
	{
		construir();
		Nodo inicial = states[iI][jI][0][2][4];
		HashSet <Nodo> nVisited = new HashSet <Nodo> ();
		HashSet <Arista> aVisited = new HashSet <Arista> ();
		nVisited.add(inicial);
		LinkedList <Nodo> p = new LinkedList <Nodo> ();
		p.add(inicial);
		boolean posible = false;
		while(!p.isEmpty())
		{
			Nodo actual = p.poll();
			if(actual.i == iF && actual.j == jF)
			{
				posible = true;
				continue;
			}
			for(Arista a : actual.aristas)
			{
				if(a.v.i == iI && a.v.j == jI)
					continue;
				aVisited.add(a);
				if(!nVisited.contains(a.v))
				{
					nVisited.add(a.v);
					p.add(a.v);
				}
			}
		}
		if(!posible)
			return Integer.MAX_VALUE;
		inicial.distancia = 0;
		Nodo[] nodos = nVisited.toArray(new Nodo[0]);
		Arista[] aristas = aVisited.toArray(new Arista[0]);
		for(int i = 0; i < nodos.length; i++)
			for(Arista a : aristas)
			{
				if(a.u.distancia != Integer.MAX_VALUE && a.u.distancia + a.costo < a.v.distancia)
					a.v.distancia = a.u.distancia + a.costo;
			}
		for(Arista a : aristas)
		{
			if(a.u.distancia != Integer.MAX_VALUE && a.u.distancia + a.costo < a.v.distancia)
				return Integer.MIN_VALUE;
		}
		int menor = Integer.MAX_VALUE;
		for(Nodo n : nodos)
		{
			if(n.i == iF && n.j == jF)
				menor = Math.min(menor, n.distancia);
		}
		return -menor;
	}
	
	static int iI, jI, iF, jF;
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int tests = sc.nextInt();
		for(int test = 0; test < tests; test++)
		{
			n = sc.nextInt();
			m = sc.nextInt();
			dice = new int[6];
			int cur = 0;
			for(char c : sc.next().toCharArray())
				dice[cur++] = c - '0';
			int temp = dice[2];
			dice[2] = dice[3];
			dice[3] = temp;
			tablero = new int[n][m];
			for(int i = 0; i < n; i++)
			{
				char[] s = sc.next().toCharArray();
				for(int j = 0; j < m; j++)
				{
					if(s[j] == 'S')
					{
						iI = i;
						jI = j;
					}
					else if(s[j] == 'T')
					{
						iF = i;
						jF = j;
					}
					else if(s[j] != '.')
						tablero[i][j] = s[j] - '0';
					else
						tablero[i][j] = -1;
				}
			}
			int result = solve(iI, jI, iF, jF);
			if(result == Integer.MAX_VALUE)
				System.out.println("Impossible");
			else if(result == Integer.MIN_VALUE)
				System.out.println("Infinity");
			else
				System.out.println(result);
		}
	}

}

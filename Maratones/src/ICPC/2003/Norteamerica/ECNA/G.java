import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;


public class G
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
	 
	static QuadTree negro = new QuadTree(true);
	static QuadTree blanco = new QuadTree(false);
	
	static class QuadTree
	{
		QuadTree[] hijos = new QuadTree[4];
		Boolean valor;
		
		QuadTree()
		{
		}
		
		QuadTree(boolean b)
		{
			valor = b;
		}
		
		int contar()
		{
			if(this == negro || this == blanco)
				return 1;
			int cuenta = 1;
			for(QuadTree q : hijos)
				cuenta += q.contar();
			return cuenta;
		}
		
		static QuadTree darQuadTree(boolean[][] valores, int xi, int yi, int xf, int yf)
		{
			boolean unico = true;
			boolean valor = valores[xi][yi];
			out:
			for(int i = xi; i < xf; i++)
				for(int j = yi; j < yf; j++)
					if(valores[i][j] != valor)
					{
						unico = false;
						break out;
					}
			if(unico)
				return valor ? negro : blanco;
			QuadTree nuevo = new QuadTree();
			int xMit = (xi + xf) / 2;
			int yMit = (yi + yf) / 2;
			nuevo.hijos[0] = darQuadTree(valores, xi, yi, xMit, yMit);
			nuevo.hijos[1] = darQuadTree(valores, xMit, yi, xf, yMit);
			nuevo.hijos[2] = darQuadTree(valores, xi, yMit, xMit, yf);
			nuevo.hijos[3] = darQuadTree(valores, xMit, yMit, xf, yf);
			return nuevo;
		}
	}
	
	static HashMap <QuadTreeN, Integer> arboles = new HashMap <QuadTreeN, Integer> ();
	static int cuenta;
	static class QuadTreeN
	{
		int[] hijos = new int[4];
		
		static int darQuadTreeN(QuadTree q)
		{
			if(q == negro)
				return 0;
			else if(q == blanco)
				return 1;
			QuadTreeN nuevo = new QuadTreeN();
			for(int i = 0; i < 4; i++)
				nuevo.hijos[i] = darQuadTreeN(q.hijos[i]);
			if(arboles.containsKey(nuevo))
				return arboles.get(nuevo);
			else
			{
				arboles.put(nuevo, cuenta);
				return cuenta++;
			}
		}

		@Override
		public int hashCode() 
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(hijos);
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
			QuadTreeN other = (QuadTreeN) obj;
			if (!Arrays.equals(hijos, other.hijos))
				return false;
			return true;
		}
	}

	static int contarArboles(QuadTree o)
	{
		arboles.clear();
		cuenta = 2;
		if(o == negro || o == blanco)
			return 1;
		int raiz = QuadTreeN.darQuadTreeN(o);
		int cuenta = 0;
		HashSet <Integer> contados = new HashSet <Integer> ();
		for(QuadTreeN q : arboles.keySet())
		{
			if(arboles.get(q) == raiz)
				cuenta++;
			for(int i : q.hijos)
				if(i == 0 || i == 1)
					cuenta++;
				else
				{
					if(!contados.contains(i))
					{
						cuenta++;
						contados.add(i);
					}
				}
		}
		return cuenta;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			if(n == 0 && m == 0)
				return;
			int nReal = 1;
			int mReal = 1;
			while(n > nReal || m > mReal)
			{
				nReal *= 2;
				mReal *= 2;
			}
			boolean[][] pixeles = new boolean[nReal][mReal];
			for(int i = 0; i < n; i++)
			{
				String linea = sc.next();
				for(int j = 0; j < m; j++)
					pixeles[i][j] = linea.charAt(j) == '1';
			}
			QuadTree raiz = QuadTree.darQuadTree(pixeles, 0, 0, nReal, mReal);
			System.out.println(raiz.contar() + " " + contarArboles(raiz));
		}
	}
}

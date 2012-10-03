import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class C
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
	
	static class Fractal
	{
		Fractal[][] fractals = new Fractal[2][2];
		int[][] values;
		
		int[][] toArray()
		{
			if(values != null)
				return values;
			else
			{
				int[][][][] vals = new int[2][2][][];
				for(int i = 0; i < 2; i++)
					for(int j = 0; j < 2; j++)
						vals[i][j] = fractals[i][j].toArray();
				int tamHijo = vals[0][0].length;
				int[][] result = new int[tamHijo * 2][tamHijo * 2];
				for(int i = 0; i < tamHijo; i++)
					for(int j = 0; j < tamHijo; j++)
						result[i][j] = vals[0][0][i][j];
				for(int i = 0; i < tamHijo; i++)
					for(int j = 0; j < tamHijo; j++)
						result[i][j + tamHijo] = vals[0][1][i][j];
				for(int i = 0; i < tamHijo; i++)
					for(int j = 0; j < tamHijo; j++)
						result[i + tamHijo][j] = vals[1][0][i][j];
				for(int i = 0; i < tamHijo; i++)
					for(int j = 0; j < tamHijo; j++)
						result[i + tamHijo][j + tamHijo] = vals[1][1][i][j];
				return result;
			}
		}

		public void dividir() 
		{
			if(values != null)
			{
				int[][] valor = values;
				values = null;
				for(int i = 0; i < 2; i++)
					for(int j = 0; j < 2; j++)
					{
						fractals[i][j] = new Fractal();
						if(valor[i][j] == 1)
							fractals[i][j].values = negros;
						else
							fractals[i][j].values = original;
					}
			}
			else
			{
				for(int i = 0; i < 2; i++)
					for(int j = 0; j < 2; j++)
						fractals[i][j].dividir();
			}
		}
	}
	
	static int[][] negros = new int[][] {{1, 1}, {1, 1}};
	static int[][] original;
	
	static int generarFractales(int[][] tabla)
	{
		original = new int[2][2];
		int cuenta = 0;
		for(int i = 0; i < 16; i++)
		{
			int iTemp = i;
			for(int j = 0; j < 2; j++)
				for(int k = 0; k < 2; k++)
				{
					original[j][k] = (iTemp & 1) == 1 ? 1 : 0;
					iTemp >>= 1;
				}
			Fractal inicial = new Fractal();
			inicial.values = original;
			inicial.dividir();
			int[][] actual = inicial.toArray();
			int[][] patrones = new int[tabla.length][tabla[0].length];
			for(int j = 0; j + actual.length - 1 < patrones.length; j++)
				for(int k = 0; k + actual.length - 1 < patrones[0].length; k++)
				{
					boolean negro = true;
					boolean igual = true;
					for(int l = 0; l < actual.length; l++)
						for(int m = 0; m < actual.length; m++)
						{
							negro = negro && tabla[j + l][k + m] == 1;
							igual = igual && tabla[j + l][k + m] == actual[l][m];
						}
					if(igual)
					{
						patrones[j][k] = 1;
						cuenta++;
					}
					if(negro)
						patrones[j][k] = -1;
				}
			int tamActual = actual.length * 2;
			int nActual = 1;
			while(tamActual <= tabla.length && tamActual <= tabla[0].length)
			{
				for(int j = 0; j + tamActual - 1 < patrones.length; j++)
					for(int k = 0; k + tamActual - 1 < patrones[0].length; k++)
					{
						boolean bien = true;
						boolean negro = true;
						for(int l = 0; l < 2; l++)
						{
							for(int m = 0; m < 2; m++)
							{
								if(original[l][m] == 1)
									bien = bien && patrones[j + l * (tamActual / 2)][k + m * (tamActual / 2)] == -nActual;
								else
									bien = bien && patrones[j + l * (tamActual / 2)][k + m * (tamActual / 2)] == nActual;
								negro = negro && patrones[j + l * (tamActual / 2)][k + m * (tamActual / 2)] == -nActual;
							}
						}
						if(bien)
						{
							patrones[j][k] = nActual + 1;
							cuenta++;
						}
						if(negro)
							patrones[j][k] = -(nActual + 1);
					}
				nActual++;
				tamActual *= 2;
			}
		}
		return cuenta;
	}

	static int[][] leerTabla(Scanner sc) 
	 {
		 int n = sc.nextInt();
		 int m = sc.nextInt();
		 int[][] tabla = new int[n][m];
		 for(int i = 0; i < n; i++)
		 {
			 String s = sc.next();
			 for(int j = 0; j < m; j++)
				 tabla[i][j] = s.charAt(j) == '*' ? 1 : 0;
		 }
		 return tabla;
	 }
		
	 public static void main(String[] args)
	 {
		 Scanner sc = new Scanner();
		 int[][] a = leerTabla(sc);
		 System.out.println(generarFractales(a));
	 }
}

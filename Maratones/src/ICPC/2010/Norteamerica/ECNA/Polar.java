import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Polar
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
	static class Celda
	{
		boolean vivo = false;
		boolean siguiente;
		int i;
		int j;
	}
	
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			int x = sc.nextInt();
			int y = sc.nextInt();
			if(x == 0 && y == 0)
				return;
			Celda[][] celdas = new Celda[x][5 * y];
			for(int i = 0; i < x; i++)
				for(int j = 0; j < y; j++)
				{
					Celda nueva = new Celda();
					for(int k = 0; k < 5; k++)
						celdas[i][j + k * y] = nueva;
				}
			int yi = 2 * y;
			int yf = 3 * y;
			int k = sc.nextInt();
			for(int i = 0; i < k; i++)
				celdas[sc.nextInt()][sc.nextInt()].vivo = true;
			int g = sc.nextInt();
			for(int iteracion = 0; iteracion < g; iteracion++)
			{
				for(int i = 0; i < x; i++)
					for(int j = yi; j < yf; j++)
					{
						int vecinosVivos = 0;
						if(celdas[i][j - 1].vivo)
							vecinosVivos++;
						if(celdas[i][j + 1].vivo)
							vecinosVivos++;
						int filaAnterior = i - 1;
						int columnaAnterior = j;
						if(i == 0)
						{
							filaAnterior = i;
							columnaAnterior = j + y / 2;
						}
						if(celdas[filaAnterior][columnaAnterior].vivo)
							vecinosVivos++;
						if(celdas[filaAnterior][columnaAnterior - 1].vivo)
							vecinosVivos++;
						if(celdas[filaAnterior][columnaAnterior + 1].vivo)
							vecinosVivos++;
						filaAnterior = i + 1;
						columnaAnterior = j;
						if(i == x - 1)
						{
							filaAnterior = i;
							columnaAnterior = j + y / 2;
						}
						if(celdas[filaAnterior][columnaAnterior].vivo)
							vecinosVivos++;
						if(celdas[filaAnterior][columnaAnterior - 1].vivo)
							vecinosVivos++;
						if(celdas[filaAnterior][columnaAnterior + 1].vivo)
							vecinosVivos++;
						if(!celdas[i][j].vivo && vecinosVivos == 3)
							celdas[i][j].siguiente = true;
						else if(celdas[i][j].vivo && (vecinosVivos < 2 || vecinosVivos > 3))
							celdas[i][j].siguiente = false;
						else
							celdas[i][j].siguiente = celdas[i][j].vivo;
					}
				for(int i = 0; i < x; i++)
					for(int j = yi; j < yf; j++)
						celdas[i][j].vivo = celdas[i][j].siguiente;
			}
			Celda menor = null;
			Celda mayor = null;
			out:
				for(int i = 0; i < x; i++)
					for(int j = yi; j < yf; j++)
						if(celdas[i][j].vivo)
						{
							menor = celdas[i][j];
							menor.i = i;
							menor.j = j - yi;
							break out;
						}
			out1:
				for(int i = x - 1; i >= 0; i--)
					for(int j = yf - 1; j >= yi; j--)
						if(celdas[i][j].vivo)
						{
							mayor = celdas[i][j];
							mayor.i = i;
							mayor.j = j - yi;
							break out1;
						}
			int cuenta = 0;
			for(int i = 0; i < x; i++)
				for(int j = yi; j < yf; j++)
					if(celdas[i][j].vivo)
						cuenta++;
			System.out.print("Case " + caso++ + ": ");
			if(cuenta == 0)
				System.out.println("0 -1 -1 -1 -1");
			else
				System.out.println(cuenta + " " + menor.i + " "+ menor.j + " " + mayor.i + " " + mayor.j);
		}
	}

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;


public class Flips
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int caso = 1;
		while(true)
		{
			String[] pedazos = br.readLine().split(" ");
			int n = Integer.parseInt(pedazos[0]);
			int m = Integer.parseInt(pedazos[1]);
			if(n == 0 && m == 0)
				return;
			@SuppressWarnings("unchecked")
			LinkedList <Integer> [][] matriz = new LinkedList[n][m];
			for(int i = 0; i < n; i++)
			{
				pedazos = br.readLine().split(" ");
				for(int j = 0; j < m; j++)
				{
					matriz[i][j] = new LinkedList <Integer> ();
					matriz[i][j].add(Integer.parseInt(pedazos[j]));
				}
			}
			String instrucciones = br.readLine().trim();
			int x0 = 0;
			int xf = n;
			int y0 = 0;
			int yf = m;
			while(!instrucciones.isEmpty())
			{
				char letra = instrucciones.charAt(0);
				if(letra == 'T')
				{
					for(int i = y0; i < yf; i++)
						pasar(matriz[x0][i], matriz[x0 + 1][i]);
					x0++;
				}
				else if(letra == 'B')
				{
					for(int i = y0; i < yf; i++)
						pasar(matriz[xf - 1][i], matriz[xf - 2][i]);
					xf--;
				}
				else if(letra == 'L')
				{
					for(int i = x0; i < xf; i++)
						pasar(matriz[i][y0], matriz[i][y0 + 1]);
					y0++;
				}
				else
				{
					for(int i = x0; i < xf; i++)
						pasar(matriz[i][yf - 1], matriz[i][yf - 2]);
					yf--;
				}
				instrucciones = instrucciones.substring(1);
			}
			System.out.print("Case " + caso++ + ":");
			Integer[] finales = matriz[x0][y0].toArray(new Integer[0]);
			for(int i = finales.length - 1; i >= 0; i--)
				if(finales[i] >= 0)
					System.out.print(" " + finales[i]);
			System.out.println();
		}
	}

	private static void pasar(LinkedList<Integer> a, LinkedList<Integer> b)
	{
		while(!a.isEmpty())
			b.addFirst(-a.pollFirst());
	}

}

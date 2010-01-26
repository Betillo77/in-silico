import java.util.Scanner;


public class caminoLargo 
{
	public static void main(String [] args)
	{
		double[][] grafo = new double[100][100];
		Scanner sc = new Scanner(System.in);
		int caso = 0;
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			int inicio = sc.nextInt();
			for(int i = 0; i < n; i++)
				for(int j = 0; j < n; j++)
					grafo[i][j] = Double.POSITIVE_INFINITY;
			while(true)
			{
				int a = sc.nextInt();
				int b = sc.nextInt();
				if(a == b && a == 0)
					break;
				grafo[a - 1][b - 1] = -1;
			}
			for(int k = 0; k < n; k++)
				for(int i = 0; i < n; i++)
					for(int j = 0; j < n; j++)
						grafo[i][j] = Math.min(grafo[i][j], grafo[i][k] + grafo[k][j]);
			double menor = Double.POSITIVE_INFINITY;
			int m = 0;
			for(int i = 0; i < n; i++)
				if(grafo[inicio - 1][i] < menor)
				{
					m = i;
					menor = grafo[inicio - 1][i];
				}
			System.out.println("Case " + ++caso +": The longest path from "+ inicio +" has length " + -(int)menor + ", finishing at " + ++m + ".");
			System.out.println("");
		}
	}
}

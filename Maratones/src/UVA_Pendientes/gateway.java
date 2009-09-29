package UVA_Pendientes;

import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

public class gateway 
{
	
	public static void main(String [] args)
	{
		int [][] pq = new int [2][1000001];
		int [] pre = new int[60];
		Scanner sc = new Scanner(System.in);
		int nCasos = sc.nextInt();
		for(int i = 0; i < nCasos; i++)
		{
			int p = sc.nextInt();
			for(int j = 0; j < p; j++)
			{
				pre[j] = sc.nextInt();
			}
			int n = sc.nextInt();
			int p0 = sc.nextInt();
			int q0 = sc.nextInt();
			int a = sc.nextInt();
			int b = sc.nextInt();
			int c = sc.nextInt();
			int d = sc.nextInt();
			int e = sc.nextInt();
			int f = sc.nextInt();
			int m = sc.nextInt();
			pq[0][0] = p0;
			pq[1][0] = q0;
			int preI = pre[0];
			int minimoInicio = 0;
			int maximoInicio = 0;
			boolean repite = false;
			Hashtable <Long, Integer> tabla = new Hashtable <Long, Integer> (50000);
			int inicioCiclo = 0;
			int finCiclo = 0;
			int finN = n + 1;
			boolean termino = false;
			for(int j = 1; j < finN; j++)
			{
				int pn = (a * pq[0][j - 1] + b * pq[1][j - 1] + c) % m;
				int qn = (d * pq[0][j - 1] + e * pq[1][j - 1] + f) % m;
				
				if(!repite && (pn < pq[0][j - 1] || qn < pq[0][j - 1]))
				{
					repite = true;
				}
				long actual = (((long)pn) | (((long)qn) << 32));
				if(!termino && tabla.containsKey(actual))
				{
					finCiclo = j;
					inicioCiclo = tabla.get(actual);
					finN = Math.min(finN, j + 100);
				}
				tabla.put(actual, j);
				if(qn < pn)
				{
					pq[0][j] = qn;
					pq[1][j] = pn;
				}
				else
				{
					pq[0][j] = pn;
					pq[1][j] = qn;
				}
				if(minimoInicio == 0)
				{
					if(pq[0][j] <= preI && preI <= pq[1][j])
					{
						minimoInicio = j;
					}
				}
				else
				{
					if(maximoInicio == 0)
					{
						if(!(pq[0][j] <= preI && preI <= pq[1][j]))
						{
							maximoInicio = j;
						}
					}
				}
			}

			if(repite && inicioCiclo > 0)
			{
				Stack <Integer> pila = new Stack <Integer> ();
				int acumulado = 0;
				for(int j = 0; j < inicioCiclo; j++)
				{
					Stack <Integer> pilaNueva = new Stack <Integer> ();
					int pn = pq[0][j];
					int qn = pq[1][j];
					for(int numero : pila)
					{
						if(numero == p)
						{
							acumulado++;
						}
						else if(pn <= pre[numero] && pre[numero] <= qn)
						{
							pilaNueva.add(++numero);
						}
					}
					if(pn <= preI && preI <= qn)
						pilaNueva.add(1);
					pila = pilaNueva;
				}
				for(int j = inicioCiclo; j < inicioCiclo + 100; j++)
				{
					Stack <Integer> pilaNueva = new Stack <Integer> ();
					int pn = pq[0][j];
					int qn = pq[1][j];
					for(int numero : pila)
					{
						if(numero == p)
						{
							acumulado++;
						}
						else if(pn <= pre[numero] && pre[numero] <= qn)
						{
							pilaNueva.add(++numero);
						}
					}
					pila = pilaNueva;
				}
				pila = new Stack <Integer> ();
				for(int j = inicioCiclo; j < finCiclo; j++)
				{
					Stack <Integer> pilaNueva = new Stack <Integer> ();
					int pn = pq[0][j];
					int qn = pq[1][j];
					for(int numero : pila)
					{
						if(numero == p)
						{
							acumulado++;
						}
						else if(pn <= pre[numero] && pre[numero] <= qn)
						{
							pilaNueva.add(++numero);
						}
					}
					if(pn <= preI && preI <= qn)
						pilaNueva.add(1);
					pila = pilaNueva;
				}
				for(int numero : pila)
				{
					if(numero == p)
					{
						acumulado++;
					}
				}
				System.out.println("Case " + (i + 1) + ": " + acumulado);
			}
			Stack <Integer> pila = new Stack <Integer> ();
			int acumulado = 0;
			int inicio = (n < m && n > 100) ? minimoInicio : 1;
			int fin = (n < m && n > 100) ? Math.min(maximoInicio + p + 2, n + 1) : n + 1;
			for(int j = inicio; j < fin; j++)
			{
				Stack <Integer> pilaNueva = new Stack <Integer> ();
				int pn = pq[0][j];
				int qn = pq[1][j];
				for(int numero : pila)
				{
					if(numero == p)
					{
						acumulado++;
					}
					else if(pn <= pre[numero] && pre[numero] <= qn)
					{
						pilaNueva.add(++numero);
					}
				}
				if(pn <= preI && preI <= qn)
					pilaNueva.add(1);
				pila = pilaNueva;
			}
			for(int numero : pila)
			{
				if(numero == p)
				{
					acumulado++;
				}
			}
			System.out.println("Case " + (i + 1) + ": " + acumulado);
		}
	}

}

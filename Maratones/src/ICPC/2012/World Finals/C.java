import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;


public class C
{
	static class Posibilidad
	{
		int mascara;
		int[] unos;
		Posibilidad inversa;
		
		public Posibilidad(int m) 
		{
			mascara = m;
			unos = generarUnos(mascara);
			inversa = new Posibilidad();
			inversa.mascara = (~mascara) & ((1 << n) - 1) & (~1) & (~(1 << (n - 1)));
			inversa.unos = generarUnos(inversa.mascara);
		}
		
		public Posibilidad()
		{
		}
		
		static int[] generarUnos(int mascara)
		{
			int cuenta = 0;
			for(int i = 0; i < n; i++)
				if(puesto(mascara, i))
					cuenta++;
			int[] unos = new int[cuenta];
			int temp = 0;
			for(int i = 0; i < n; i++)
				if(puesto(mascara, i))
					unos[temp++] = i;
			return unos;
		}
	}
	
	private static boolean puesto(int mascara, int i) 
	{
		return ((mascara >> i) & 1) == 1;
	}
	


	private static int poner(int mascara, int actual) 
	{
		return mascara | (1 << actual);
	}
	
	static int n;
	static ArrayList <Posibilidad> posibilidades;
	
	public static void generar(int mascara, int actual, int unos)
	{
		if(unos == 0)
		{
			if(!puesto(mascara, 0) && !puesto(mascara, n - 1))
				posibilidades.add(new Posibilidad(mascara));
		}
		else
		{
			if(actual == n)
				return;
			generar(poner(mascara, actual), actual + 1, unos - 1);
			generar(mascara, actual + 1, unos);
		}
	}
	
	static int[][] distancia;
	static HashMap <Integer, Integer> [][] dpTS;
	
	static int dpTS(int mascara, int actual, int fin, int[] ones)
	{
		if(dpTS[actual][fin].containsKey(mascara))
			return dpTS[actual][fin].get(mascara);
		int siguiente = mascara & (~(1 << actual));
		int bits = Integer.bitCount(mascara);
		if(bits == 1)
		{
			dpTS[actual][fin].put(mascara, distancia[actual][fin]);
			return distancia[actual][fin];
		}
		if(ones == null)
		{
			ones = new int[bits - 1];
			int tmp = 0;
			for(int i = 0; i < n; i++)
				if(puesto(siguiente, i))
					ones[tmp++] = i;
		}
		int minimo = Integer.MAX_VALUE;
		for(int i : ones)
			if(puesto(siguiente, i))
				minimo = Math.min(distancia[actual][i] + dpTS(siguiente, i, fin, ones), minimo);
		dpTS[actual][fin].put(mascara, minimo);
		return minimo;
	}
	
	private static int dpPrevio(int partiendo, int mascara, int llegando, Posibilidad p)
	{
		if(mascara == 0)
			return distancia[partiendo][llegando];
		int mejor = Integer.MAX_VALUE;
		for(int i : p.unos)
			if(puesto(mascara, i))
				mejor = Math.min(mejor, distancia[partiendo][i] + dpTS(mascara, i, llegando, null));
		return mejor;
	}

	private static int intentar(Posibilidad p) 
	{
		int mejorPrimero = Integer.MAX_VALUE;
		for(int i : p.inversa.unos)
			mejorPrimero = Math.min(dpPrevio(i, p.mascara, 0, p) + dpPrevio(i, p.inversa.mascara & (~(1 << i)), n - 1, p.inversa), mejorPrimero);
		int mejorSegundo = Integer.MAX_VALUE;
		for(int i : p.inversa.unos)
			mejorSegundo = Math.min(dpPrevio(i, p.mascara, n - 1, p) + dpPrevio(i, p.inversa.mascara & (~(1 << i)), 0, p.inversa), mejorSegundo);
		return mejorPrimero + mejorSegundo;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int caso = 1;
		while(sc.hasNextInt())
		{
			n = sc.nextInt();
			int m = sc.nextInt();
			distancia = new int[n][n];
			for(int i = 0; i < n; i++)
				for(int j = 0; j < n; j++)
					if(i == j)
						distancia[i][j] = 0;
					else
						distancia[i][j] = 100000000;
			for(int i = 0; i < m; i++)
			{
				int u = sc.nextInt();
				int v = sc.nextInt();
				int t = sc.nextInt();
				distancia[u][v] = distancia[v][u] = t;
			}
			for(int k = 0; k < n; k++)
				for(int i = 0; i < n; i++)
					for(int j = 0; j < n; j++)
						distancia[i][j] = Math.min(distancia[i][j], distancia[i][k] + distancia[k][j]);
			posibilidades = new ArrayList <Posibilidad> ();
			generar(0, 0, (n - 2) >> 1);
			dpTS = new HashMap[n][n];
			for(int i = 0; i < n; i++)
				for(int j = 0; j < n; j++)
					dpTS[i][j] = new HashMap <Integer, Integer> ();
			int resultado = Integer.MAX_VALUE;
			HashSet <Integer> procesados = new HashSet <Integer> ();
			for(Posibilidad p : posibilidades)
			{
				if(!procesados.contains(p.inversa.mascara))
					resultado = Math.min(resultado, intentar(p));
				procesados.add(p.mascara);
			}
			System.out.println("Case " + caso++ + ": " + resultado);
		}
	}
}

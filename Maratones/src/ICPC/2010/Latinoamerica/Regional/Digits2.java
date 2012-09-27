import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Conteo 
{
	static char[] limite;
	
	static long[] contar(long num)
	{
		limite = (num + "").toCharArray();
		dp1 = new Long[limite.length][2][2][10];
		dp2 = new Long[limite.length][2];
		long[] resultado = new long[10];
		for(int i = 0; i < 10; i++)
			resultado[i] = cuenta(limite.length - 1, true, false, i);
		return resultado;
	}
	
	static Long[][][][] dp1;
	
	static long cuenta(int indice, boolean tope, boolean pusoAlguno, int numero)
	{
		if(indice == -1)
			return 0;
		if(dp1[indice][tope ? 1 : 0][pusoAlguno ? 1 : 0][numero] != null)
			return dp1[indice][tope ? 1 : 0][pusoAlguno ? 1 : 0][numero];
		int lim = tope ? limite[limite.length - 1 - indice] - '0' : 9;
		long cuenta = 0;
		for(int i = 0; i <= lim; i++)
		{
			cuenta += cuenta(indice - 1, tope && i == lim, pusoAlguno || i != 0, numero);
			if(numero == i)
				if(numero == 0 && !pusoAlguno)
					continue;
				else
					cuenta += cuenta(indice - 1, tope && i == lim);
		}
		return dp1[indice][tope ? 1 : 0][pusoAlguno ? 1 : 0][numero] = cuenta;
	}

	static Long[][] dp2;
	
	static long cuenta(int indice, boolean tope)
	{
		if(indice == -1)
			return 1;
		if(dp2[indice][tope ? 1 : 0] != null)
			return dp2[indice][tope ? 1 : 0];
		int lim = tope ? limite[limite.length - 1 - indice] - '0' : 9;
		long cuenta = 0;
		for(int i = 0; i <= lim; i++)
			cuenta += cuenta(indice - 1, tope && i == lim);
		return dp2[indice][tope ? 1 : 0] = cuenta;
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			StringTokenizer st = new StringTokenizer(br.readLine());
			long a = Long.parseLong(st.nextToken()) - 1;
			long b = Long.parseLong(st.nextToken());
			if(a == -1 && b == 0)
				return;
			long[] as = contar(a);
			long[] bs = contar(b);
			for(int i = 0; i < 10; i++)
				bs[i] -= as[i];
			System.out.println(Arrays.toString(bs).replace(",", "").replace("[", "").replace("]", ""));	
		}
	}
}
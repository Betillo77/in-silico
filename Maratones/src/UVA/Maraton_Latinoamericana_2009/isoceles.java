package UVA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class isoceles 
{
	static BufferedReader is;
	static char [] numero = new char[19];
	
	private static int leerInt() throws IOException 
	{
		char actual;
		while((actual = (char) is.read()) < '0' || actual > '9');
		int digitos = 1;
		numero[0] = actual;
		while((actual = (char) is.read()) >= '0' && actual <= '9')
		{
			numero[digitos++] = actual;
		}
		return Integer.parseInt(new String(numero, 0, digitos));
	}
	
	public static void main(String [] args) throws IOException
	{
		is = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			System.gc();
			int numero = leerInt();
			if(numero == 0)
				return;
			int [][] triangulos = new int[numero][2];
			for(int i = 0; i < numero; i++)
			{
				triangulos[i][0] = leerInt();
				triangulos[i][1] = leerInt();
			}
			long [][] distancias = new long[numero][numero];
			for(int i = 0; i < numero - 1; i++)
			{
				for(int j = i + 1; j < numero; j++)
				{
					long a = triangulos[i][0] - triangulos[j][0];
					a *= a;
					long b = triangulos[i][1] - triangulos[j][1];
					b *= b;
					distancias[i][j] = (a + b);
				}
			}
			int cuenta = 0;
			for(int i = 0; i < numero - 2; i++)
			{
				for(int j = i + 1; j < numero - 1; j++)
				{
					long a = distancias[i][j];
					for(int k = j + 1; k < numero; k++)
					{
						long b = distancias[j][k];
						long c = distancias[i][k];
						if(a == b || a == c || b == c)
							cuenta++;
					}
				}
			}
			System.out.println(cuenta);
		}
	}
}

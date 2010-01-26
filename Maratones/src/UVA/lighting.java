import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;


public class lighting 
{
	
	static StringTokenizer st = new StringTokenizer("");
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	static Scanner sc = new Scanner(System.in);
	static int leerNumero()
	{
		while(!st.hasMoreTokens())
			try {
				st = new StringTokenizer(br.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		return Integer.parseInt(st.nextToken());
	}
	public static void main(String [] args)
	{
		int n = leerNumero();
		for(int i = 0; i < n; i++)
		{
			leerNumero();
			int m = leerNumero();
			for(int j = 0; j < m; j++)
			{
				leerNumero();
				leerNumero();
			}
			System.out.println("llegue");
		}
		
	}
}

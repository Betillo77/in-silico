import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class E 
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
	
	static int calcularCuenta(int[] poly)
	{
		int cuenta = poly.length - 2 + poly.length - 1;
		for(int i = 1; i < poly.length; i++)
		{
			if(poly[i] != 0)
			{
				cuenta++;
				cuenta += (poly[i] + "").length();
			}
		}
		return cuenta + 1;
	}
	
	static int calcularCuenta(int[] poly, boolean uno)
	{
		int valor = 1;
		int total = 0;
		for(int i = poly.length - 1; i >= 0; i--)
		{
			total += poly[i] * valor;
			valor *= uno ? 1 : -1;
		}
		return total;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int cuenta = 1;
		while(true)
		{
			int grado = sc.nextInt();
			int[] poly = new int[grado + 1];
			poly[0] = sc.nextInt();
			if(grado == 0 && poly[0] == 0)
				return;
			for(int i = 1; i < poly.length; i++)
				poly[i] = sc.nextInt();
			boolean uno = sc.nextInt() == 1;
			System.out.println("Polynomial " + cuenta++ + ": " + calcularCuenta(poly, uno) + " " + calcularCuenta(poly));
		}
	}
}
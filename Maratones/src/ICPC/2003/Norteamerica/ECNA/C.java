import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class C 
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
	 
	static int gcd(int a, int b)
	{
		if(b == 0)
			return a;
		return gcd(b, a % b);
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		boolean[] esPrimo = new boolean[1000001];
		int[][] factoresFaltantes = new int[1000001][]; 
		int[] tamFaltantes = new int[1000001];
		for(int i = 0; i < esPrimo.length; i++)
			esPrimo[i] = true;
		esPrimo[0] = false;
		esPrimo[1] = false;
		for(int i = 2; i < esPrimo.length; i++)
		{
			if(esPrimo[i])
			{
				ArrayList <Integer> factoresF = new ArrayList <Integer> ();
				factoresF.add(i);
				for(int j = i + i; j <= 1000000; j += i)
				{
					factoresF.add(j);
					esPrimo[j] = false;
				}
				int[] reales = new int[factoresF.size()];
				for(int j = 0; j < factoresF.size(); j++)
					reales[j] = factoresF.get(j);
				factoresFaltantes[i] = reales;
			}
		}
		int[] donde = new int[300001];
		donde[1] = 1;
		donde[2] = 2;
		int cuenta = 3;
		int anterior = 2;
		boolean[] puesto = new boolean[1000001];
		puesto[1] = true;
		puesto[2] = true;
		int actual = 3;
		while(cuenta != 300001)
		{
			puesto[anterior] = true;
			int nuevo = -1;
			if(esPrimo[anterior])
			{
				while(tamFaltantes[anterior] != factoresFaltantes[anterior].length && puesto[factoresFaltantes[anterior][tamFaltantes[anterior]]])
					tamFaltantes[anterior]++;
				nuevo = factoresFaltantes[anterior][tamFaltantes[anterior]];
			}
			else
			{
				int este = anterior;
				int limite = (int) Math.min(este, Math.ceil(Math.sqrt(este)));
				int menor = Integer.MAX_VALUE;
				for(int i = 2; este != 1 && i <= limite; i++)
				{
					if(este % i == 0)
					{
						while(tamFaltantes[i] != factoresFaltantes[i].length && puesto[factoresFaltantes[i][tamFaltantes[i]]])
							tamFaltantes[i]++;
						if(tamFaltantes[i] != factoresFaltantes[i].length && factoresFaltantes[i][tamFaltantes[i]] == anterior)
							tamFaltantes[i]++;
						if(tamFaltantes[i] != factoresFaltantes[i].length)
							menor = Math.min(menor, factoresFaltantes[i][tamFaltantes[i]]);
						while(este % i == 0)
							este /= i;
						limite = (int) Math.min(este, Math.ceil(Math.sqrt(este)));
					}
				}
				if(este != 1)
				{
					while(tamFaltantes[este] != factoresFaltantes[este].length && puesto[factoresFaltantes[este][tamFaltantes[este]]])
						tamFaltantes[este]++;
					if(tamFaltantes[este] != factoresFaltantes[este].length && factoresFaltantes[este][tamFaltantes[este]] == anterior)
						tamFaltantes[este]++;
					if(tamFaltantes[este] != factoresFaltantes[este].length)
						menor = Math.min(menor, factoresFaltantes[este][tamFaltantes[este]]);
				}
				nuevo = menor;
			}
			if(nuevo <= 300000)
			{
				donde[nuevo] = actual;
				cuenta++;
			}
			actual++;
			if(nuevo == Integer.MAX_VALUE)
				System.out.println("");
			anterior = nuevo;
		}
		while(true)
		{
			int val = sc.nextInt();
			if(val == 0)
				return;
			System.out.println("The number " + val + " appears in location " + donde[val] + ".");
		}
	}

}

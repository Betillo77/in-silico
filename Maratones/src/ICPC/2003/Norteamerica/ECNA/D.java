import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;


public class D 
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

		public double nextDouble() {
			return Double.parseDouble(next());
		}
    }
	 
	static boolean intentar(int[][] vecinos, int[] asignaciones, int colores, int actual)
	{
		if(actual == 0)
			Arrays.fill(asignaciones, -1);
		if(actual == vecinos.length)
			return true;
		for(int i = 0; i < colores; i++)
		{
			boolean paila = false;
			for(int otro : vecinos[actual])
				if(asignaciones[otro] == i)
					paila = true;
			if(paila)
				continue;
			asignaciones[actual] = i;
			if(intentar(vecinos, asignaciones, colores, actual + 1))
				return true;
			asignaciones[actual] = -1;
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			Point2D[] puntos = new Point2D[n];
			for(int i = 0; i < n; i++)
				puntos[i] = new Point2D.Double(sc.nextDouble(), sc.nextDouble());
			int[][] vecinos = new int[n][];
			for(int i = 0; i < n; i++)
			{
				ArrayList <Integer> vs = new ArrayList <Integer> ();
 				for(int j = 0; j < n; j++)
				{
					if(i == j)
						continue;
					if(puntos[i].distance(puntos[j]) <= 20)
						vs.add(j);
				}
 				int[] vsI = new int[vs.size()];
 				for(int j = 0; j < vs.size(); j++)
 					vsI[j] = vs.get(j);
 				vecinos[i] = vsI;
			}
			int colores = 1;
			while(true)
			{
				if(intentar(vecinos, new int[n], colores, 0))
				{
					System.out.println("The towers in case " + caso++ + " can be covered in " + colores + " frequencies.");
					break;
				}
				colores++;
			}
		}
	}

}

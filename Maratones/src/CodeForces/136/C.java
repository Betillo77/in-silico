import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class C 
{
	 static class Scanner
	    {
	            BufferedReader br;
	            StringTokenizer st;
	            
	            public Scanner()
	            { 
	                    br = new BufferedReader(new InputStreamReader(System.in));
	            }
	            
	            public String next()
	            {

	                    while(st == null || !st.hasMoreTokens())
	                    {
	                            try { st = new StringTokenizer(br.readLine()); }
	                            catch(Exception e) { throw new RuntimeException(); }
	                    }
	                    return st.nextToken();
	            }

	            public int nextInt()
	            {
	                    return Integer.parseInt(next());
	            }
	            
	            public double nextDouble()
	            {
	                    return Double.parseDouble(next());
	            }
	            
	            public String nextLine()
	            {
	                    st = null;
	                    try { return br.readLine(); }
	                    catch(Exception e) { throw new RuntimeException(); }
	            }
	            
	            public boolean endLine()
	            {
	                    try 
	                    {
	                            String next = br.readLine();
	                            while(next != null && next.trim().isEmpty())
	                                    next = br.readLine();
	                            if(next == null)
	                                    return true;
	                            st = new StringTokenizer(next);
	                            return st.hasMoreTokens();
	                    }
	                    catch(Exception e) { throw new RuntimeException(); }
	            }
	    }

	 static int idF = 0;
	 static class Numero implements Comparable <Numero>
	 {
		 int n;
		 int id = idF++;
		 
		@Override
		public int compareTo(Numero o) 
		{
			if(n == o.n)
				return id - o.id;
			return n - o.n;
		}
		
		@Override
		public String toString() {
			return n + "";
		}
	 }
	 
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int n = sc.nextInt();
		Integer[] numeros = new Integer[n];
		for(int i = 0; i < n; i++)
			numeros[i] = sc.nextInt();
		if(intentar(numeros, true, true, true) || intentar(numeros, true, true, false) || intentar(numeros, true, false, true) || intentar(numeros, true, false, false) || intentar(numeros, false, true, true) || intentar(numeros, false, true, false) || intentar(numeros, false, false, true) || intentar(numeros, false, false, false))
			System.out.println("YES");
		else
			System.out.println("NO");
	}
	
	static boolean intentar(Integer[] nums, boolean a, boolean b, boolean c)
	{
		Integer[] numeros = nums.clone();
		int indice1 = enOrden(numeros, a, -1, c);
		int indice2 = enOrden(numeros, b, indice1, c);
		if(indice1 != -1 && indice2 != -1)
		{
			Integer temp = numeros[indice1];
			numeros[indice1] = numeros[indice2];
			numeros[indice2] = temp;
		}
		boolean es = enOrden(numeros, true, -1, c) == -1;
		if(es)
			return es;
		if(indice1 != -1 && indice2 != -1)
		{
			Integer temp = numeros[indice1];
			numeros[indice1] = numeros[indice2];
			numeros[indice2] = temp;
		}
		indice2 = dondeIria(numeros, indice1, numeros[indice1], b, c);
		if(indice1 != -1 && indice2 != -1)
		{
			Integer temp = numeros[indice1];
			numeros[indice1] = numeros[indice2];
			numeros[indice2] = temp;
		}
		return enOrden(numeros, true, -1, c) == -1;
	}

	private static int dondeIria(Integer[] numeros, int indice, int n, boolean b, boolean c) 
	{
		for(int i = 0; i < numeros.length; i++)
			if(b && (c ? numeros[i] >= n : numeros[i] > n) && i - 1 != indice && i != indice)
				return i == 0 ? i : i - 1;
			else if(!b && (c ? numeros[i] >= n : numeros[i] > n))
				return i;
		return numeros.length - 1;
	}

	private static int enOrden(Integer[] numeros, boolean cual, int anterior, boolean cual2) 
	{
			for(int i = 1; i < numeros.length; i++)
				if(i == anterior)
					continue;
				else if(numeros[i] < numeros[i - 1])
				{
					if(cual2 && (i - 1 == anterior || i == anterior))
						continue;
					return cual ? i - 1 : i;
				}
			return anterior == -1 ? -1 : 0;
	}
}
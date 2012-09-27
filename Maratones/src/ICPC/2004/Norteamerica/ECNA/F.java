import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class F 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		String nextLine()
		{
			try
			{
				String s = br.readLine();
				return s;
			}
			catch(Exception e)
			{
				throw new RuntimeException(e);
			}
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

		public int nextInt() 
		{
			return Integer.parseInt(next());
		}
	}
	
	static ArrayList <String> candidatos = new ArrayList <String> ();
	
	static void generatePermutations(String actual, String cs, boolean[] usados)
	{
		if(actual.length() == 5)
			candidatos.add(actual);
		else
		{
			for(int i = 0; i < usados.length; i++)
				if(!usados[i])
				{
					usados[i] = true;
					generatePermutations(actual + cs.charAt(i), cs, usados);
					usados[i] = false;
				}
		}
	}
	
	public static int distance(String a, String b)
	{
		int[] rA = new int[a.length()];
		int[] rB = new int[a.length()];
		for(int i = 0; i < a.length(); i++)
		{
			rA[a.charAt(i) - 'A'] = i;
			rB[b.charAt(i) - 'A'] = i;
		}
		int cuenta = 0;
		for(int i = 0; i < a.length(); i++)
			for(int j = i + 1; j < a.length(); j++)
				if((rA[i] < rA[j] && rB[i] > rB[j]) || (rA[i] > rA[j] && rB[i] < rB[j]))
					cuenta++;
		return cuenta;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		generatePermutations("", "ABCDE", new boolean[5]);
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			String[] s = new String[n];
			for(int i = 0; i < n; i++)
				s[i] = sc.next();
			int mejorSuma = Integer.MAX_VALUE;
			String candidatoMejor = null;
			for(String st : candidatos)
			{
				int suma = 0;
				for(String s1 : s)
					suma += distance(s1, st);
				if(suma < mejorSuma)
				{
					mejorSuma = suma;
					candidatoMejor = st;
				}
			}
			System.out.println(candidatoMejor + " is the median ranking with value " + mejorSuma + ".");
		}
	}
}
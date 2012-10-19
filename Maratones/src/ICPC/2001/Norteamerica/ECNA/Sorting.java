import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Sorting
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		String nextLine()
		{
			try { return br.readLine(); } catch(Exception e) { throw new RuntimeException(e); }
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
		
		Integer nextInteger()
		{
			String next = next();
			if(next == null)
				return null;
			return Integer.parseInt(next);
		}
		
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
	}
	
	static class Letra
	{
		int numero;
		ArrayList <Letra> anteriores = new ArrayList <Letra> ();
		Integer tam = null;
		Boolean[] atras = new Boolean[26];
		
		Letra(int n)
		{
			numero = n;
		}
		
		int darTamActual()
		{
			if(tam != null)
				return tam;
			int mejor = 1;
			for(Letra l : anteriores)
				mejor = Math.max(mejor, 1 + l.darTamActual());
			return tam = mejor;
		}
		
		String darSecuencia(int n)
		{
			if(darTamActual() != n)
				return null;
			if(n == 1)
				return "" + (char) (numero + 'A');
			for(Letra l : anteriores)
			{
				String s = l.darSecuencia(n - 1);
				if(s != null)
					return (char) (numero + 'A') + s;
			}
			return null;
		}
		
		boolean tieneAtras(int n)
		{
			if(atras[n] != null)
				return atras[n];
			if(numero == n)
				return atras[n] = true;
			boolean tiene = false;
			for(Letra l : anteriores)
				tiene = tiene || l.tieneAtras(n);
			return atras[n] = tiene;
		}

		public void limpiar() 
		{
			tam = null;
			for(int i = 0; i < 26; i++)
				atras[i] = null;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			if(n == 0 && m == 0)
				return;
			Letra[] letras = new Letra[n];
			for(int i = 0; i < n; i++)
				letras[i] = new Letra(i);
			boolean termino = false;
			for(int i = 0; i < m; i++)
			{
				String s = sc.next();
				if(termino)
					continue;
				int a = s.charAt(0) - 'A';
				int b = s.charAt(2) - 'A';
				for(int j = 0; j < n; j++)
					letras[j].limpiar();
				if(letras[a].tieneAtras(b))
				{
					System.out.println("Inconsistency found after " + (i + 1) + " relations.");
					termino = true;
					continue;
				}
				letras[b].anteriores.add(letras[a]);
				for(int j = 0; j < n; j++)
					if(letras[j].darTamActual() == n)
					{
						System.out.println("Sorted sequence determined after " + (i + 1) + " relations: " + new StringBuilder(letras[j].darSecuencia(n)).reverse().toString() + ".");
						termino = true;
						break;
					}
			}
			if(!termino)
				System.out.println("Sorted sequence cannot be determined.");
		}
	}

}

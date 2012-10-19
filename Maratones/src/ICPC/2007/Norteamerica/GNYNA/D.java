import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class D 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		
		String nextLine()
		{
			try { return br.readLine(); } catch(Exception e) { throw new RuntimeException(e); }
		}
		
		String next()
		{
			while(!st.hasMoreTokens())
			{
				String line = nextLine();
				if(line == null) return null;
				st = new StringTokenizer(line);
			}
			return st.nextToken();
		}
		
		int nextInt()
		{
			return Integer.parseInt(next());
		}
		
		double nextDouble()
		{
			return Double.parseDouble(next());
		}
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
	}
	
	static boolean agregar(LinkedList <Boolean> a, LinkedList<Character> s)
	{
		if(a.size() < 5)
			return false;
		int valor = 0;
		for(int i = 0; i < 5; i++)
		{
			valor <<= 1;
			valor |= a.poll() ? 1 : 0;
		}
		char c;
		if(valor == 0)
			c = ' ';
		else
			c = (char) ('A' + (valor - 1));
		s.add(c);
		return true;
	}
	
	private static String darVals(LinkedList <Boolean> vals) 
	{
		LinkedList <Character> s = new LinkedList <Character> ();
		while(true)
			if(!agregar(vals, s))
				break;
		String sal = "";
		for(char c : s)
			sal += c;
		while(sal.length() > 0 && sal.charAt(sal.length() - 1) == ' ')
			sal = sal.substring(0, sal.length() - 1);
		return sal;
	}
	
	static void llenar(boolean[][] matriz, LinkedList <Boolean> valores)
	{
		boolean[][] visitados = new boolean[matriz.length][matriz[0].length];
		int iAct = 0;
		int jAct = 0;
		int dir = 0;
		while(valores.size() != matriz.length * matriz[0].length)
		{
			valores.add(matriz[iAct][jAct]);
			visitados[iAct][jAct] = true;
			int iSig = iAct;
			int jSig = jAct;
			if(dir == 0)
				jSig++;
			else if(dir == 1)
				iSig++;
			else if(dir == 2)
				jSig--;
			else
				iSig--;
			if(iSig < 0 || iSig >= matriz.length || jSig < 0 || jSig >= matriz[0].length || visitados[iSig][jSig])
			{
				dir++;
				dir %= 4;
				iSig = iAct;
				jSig = jAct;
				if(dir == 0)
					jSig++;
				else if(dir == 1)
					iSig++;
				else if(dir == 2)
					jSig--;
				else
					iSig--;
			}
			iAct = iSig;
			jAct = jSig;
		}
	}
	
	static void leerMatriz(boolean[][] matriz, String palabra) 
	{
		LinkedList <Boolean> todos = new LinkedList <Boolean> ();
		for(char c : palabra.toCharArray())
			todos.add(c == '1');
		for(int i = 0; i < matriz.length; i++)
			for(int j = 0; j < matriz[0].length; j++)
				matriz[i][j] = todos.poll();
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			String linea = sc.nextLine();
			String[] pedazos = linea.split(" ", 3);
			int n = Integer.parseInt(pedazos[0]);
			int m = Integer.parseInt(pedazos[1]);
			String palabra = pedazos[2];
			boolean[][] matriz = new boolean[n][m];
			leerMatriz(matriz, palabra);
			LinkedList <Boolean> vals = new LinkedList <Boolean> ();
			llenar(matriz, vals);
			String res = darVals(vals);
			System.out.println(caso + " " + res);
		}
	}
}
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class C
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
	
	static void agregar(LinkedList <Boolean> a, char letra)
	{
		int valor = 0;
		if(letra == ' ')
			valor = 0;
		else
			valor = letra - 'A' + 1;
		String p = Integer.toBinaryString(valor);
		while(p.length() != 5)
			p = "0" + p;
		for(char c : p.toCharArray())
			a.add(c == '1');
	}
	
	private static LinkedList <Boolean> darVals(String palabra) 
	{
		LinkedList <Boolean> vals = new LinkedList <Boolean> ();
		for(char c : palabra.toCharArray())
			agregar(vals, c);
		return vals;
	}
	
	static void llenar(boolean[][] matriz, LinkedList <Boolean> valores)
	{
		boolean[][] visitados = new boolean[matriz.length][matriz[0].length];
		int iAct = 0;
		int jAct = 0;
		int dir = 0;
		while(!valores.isEmpty())
		{
			matriz[iAct][jAct] = valores.poll();
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
			LinkedList <Boolean> vals = darVals(palabra);
			llenar(matriz, vals);
			System.out.print(caso + " ");
			for(int i = 0; i < n; i++)
				for(int j = 0; j < m; j++)
					System.out.print(matriz[i][j] ? "1" : "0");
			System.out.println();
		}
	}
}
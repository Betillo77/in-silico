import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
		
		Integer nextInteger()
		{
			String n = next();
			if(n == null)
				return null;
			return Integer.parseInt(n);
		}
	}
	
	static class Entrada
	{
		int dia;
		int mes;
		Entrada siguiente;
		
		public Entrada(int i, int j, Entrada e) 
		{
			dia = i;
			mes = j;
			siguiente = e;
		}

		public boolean esValida()
		{
			if(mes < 1 || mes > 12)
				return false;
			if(mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12)
				return dia > 0 && dia <= 31;
			else if(mes == 2)
				return dia > 0 && dia <= 29;
			else
				return dia > 0 && dia <= 30;
		}

		public boolean esMayor(int oMes, int oDia) 
		{
			if(mes > oMes)
				return true;
			if(mes < oMes)
				return false;
			return dia > oDia;
		}
	}
	
	static int[][] fechas;
	static Entrada[][][] dp;
	static Entrada imposible = new Entrada(-1, -1, null);
	
	static Entrada dp(int n, int mes, int dia)
	{
		if(dp[n][mes][dia] != null)
			return dp[n][mes][dia];
		if(n == fechas.length)
			return new Entrada(0, 0, null);
		Entrada a = new Entrada(fechas[n][0], fechas[n][1], null);
		if(a.esValida() && a.esMayor(mes, dia))
		{
			Entrada siguiente = dp(n + 1, a.mes, a.dia);
			if(siguiente == imposible)
				a = imposible;
			else
				a.siguiente = siguiente;
		}
		if(a.siguiente == null)
			a = imposible;
		Entrada b = new Entrada(fechas[n][1], fechas[n][0], null);
		if(b.esValida() && b.esMayor(mes, dia))
		{
			Entrada siguiente = dp(n + 1, b.mes, b.dia);
			if(siguiente == imposible)
				b = imposible;
			else
				b.siguiente = siguiente;
		}
		if(b.siguiente == null)
			b = imposible;
		return dp[n][mes][dia] = mejor(a, b);
	}

	private static Entrada mejor(Entrada a, Entrada b)
	{
		if(a == imposible)
			return b;
		if(b == imposible)
			return a;
		if(a.esMayor(b.mes, b.dia))
			return b;
		else
			return a;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			Integer lineas = sc.nextInteger();
			if(lineas == null)
				break;
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < lineas; i++)
				sb.append(sc.nextLine());
			StringTokenizer st = new StringTokenizer(sb.toString());
			ArrayList <int[]> fs = new ArrayList <int[]> ();
			while(st.hasMoreTokens())
			{
				String[] s = st.nextToken().split("/");
				fs.add(new int[]{parsear(s[0]), parsear(s[1])});
			}
			fechas = fs.toArray(new int[0][0]);
			dp = new Entrada[fechas.length + 2][40][40];
			Entrada r = dp(0, 0, 0);
			if(r == imposible)
				System.out.println();
			else
			{
				System.out.print(convertir(r.mes + "") + "/" + convertir(r.dia + ""));
				r = r.siguiente;
				while(r.dia != 0)
				{
					System.out.print(" " + convertir(r.mes + "") + "/" + convertir(r.dia + ""));
					r = r.siguiente;
				}
				System.out.println();
			}
		}
	}

	static String convertir(String s)
	{
		if(s.length() == 1)
			return "0" + s;
		else
			return s;
	}
	static int parsear(String string) 
	{
		while(string.charAt(0) == '0' && string.length() > 1)
			string = string.substring(1);
		return Integer.parseInt(string);
	}
	
	
	
}

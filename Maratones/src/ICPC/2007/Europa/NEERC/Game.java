import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.StringTokenizer;


public class Game 
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

		public BigDecimal nextBigDecimal()
		{
			return new BigDecimal(next());
		}
	}
	
	static final int scale = 100;
	
	static class Respuesta
	{
		BigDecimal p;
		BigDecimal q;
		
		public Respuesta(double a, double b)
		{
			p = BigDecimal.valueOf(a);
			q = BigDecimal.valueOf(b);
		}

		public Respuesta sumar(Respuesta otra) 
		{
			p = p.add(otra.p);
			q = q.add(otra.q);
			return this;
		}

		public void resolver() 
		{
			p = p.divide(BigDecimal.ONE.subtract(q), scale, BigDecimal.ROUND_HALF_UP);
			q = BigDecimal.ZERO;
		}

		public Respuesta multiplicar(BigDecimal d) 
		{
			p = p.multiply(d);
			q = q.multiply(d);
			return this;
		}
	}
	
	static class Jugador
	{
		Jugador[] adjacentes = new Jugador[2];
		BigDecimal[] ps = new BigDecimal[2];
		
		Respuesta darProbabilidad(Jugador anterior, Jugador objetivo, Respuesta porDefecto, boolean oAnterior, Jugador inicial)
		{
			if(this == objetivo || (oAnterior && adjacentes[0] == objetivo) || (oAnterior && adjacentes[1] == objetivo))
			{
				if(this == objetivo && oAnterior)
					return new Respuesta(0, 0);
				if(porDefecto != null)
					return porDefecto;
				else
				{
					int antN = adjacentes[0] == anterior ? 0 : 1;
					Respuesta r = anterior.darProbabilidad(this, inicial, new Respuesta(1, 0), false, this).multiplicar(ps[antN]);
					r.resolver();
					return r;
				}
			}
			else
			{
				Respuesta parcial = new Respuesta(0, 0);
				int antN = adjacentes[0] == anterior ? 0 : 1;
				int sigN = antN == 0 ? 1 : 0;
				parcial.sumar(adjacentes[sigN].darProbabilidad(this, objetivo, porDefecto, oAnterior, inicial).multiplicar(ps[sigN]));
				BigDecimal coeficienteDiv = BigDecimal.ONE.subtract(parcial.q);
				parcial.q = BigDecimal.ZERO;
				parcial.sumar(new Respuesta(0, 1).multiplicar(ps[antN]));
				parcial.p = parcial.p.divide(coeficienteDiv, scale, BigDecimal.ROUND_HALF_UP);
				parcial.q = parcial.q.divide(coeficienteDiv, scale, BigDecimal.ROUND_HALF_UP);
				return parcial;
			}
		}
	}
	
	static BigDecimal solucionar(Jugador[] todos, int i)
	{
		Jugador inicial = todos[i];
		int ant = i - 1;
		if(ant < 0)
			ant += todos.length;
		Jugador anterior = todos[ant];
		Jugador siguiente = todos[i + 1];
		Jugador objetivo = todos[todos.length - 1];
		Respuesta r = anterior.darProbabilidad(inicial, objetivo, null, true, inicial).multiplicar(inicial.ps[0]).sumar(siguiente.darProbabilidad(inicial, objetivo, new Respuesta(0, 0), false, inicial).multiplicar(inicial.ps[1]));
		r.resolver();
		BigDecimal p1 = anterior == objetivo ? BigDecimal.ONE : r.p;
		r = anterior.darProbabilidad(inicial, objetivo, new Respuesta(0, 0), false, inicial).multiplicar(inicial.ps[0]).sumar(siguiente.darProbabilidad(inicial, objetivo, new Respuesta(1, 0), false, inicial).multiplicar(inicial.ps[1]));
		r.resolver();
		p1 = p1.multiply(r.p);
		r = anterior.darProbabilidad(inicial, objetivo, new Respuesta(0, 0), false, inicial).multiplicar(inicial.ps[0]).sumar(siguiente.darProbabilidad(inicial, objetivo, null, true, inicial).multiplicar(inicial.ps[1]));
		r.resolver();
		BigDecimal p2 = siguiente == objetivo ? BigDecimal.ONE : r.p;
		r = anterior.darProbabilidad(inicial, objetivo, new Respuesta(1, 0), false, inicial).multiplicar(inicial.ps[0]).sumar(siguiente.darProbabilidad(inicial, objetivo, new Respuesta(0, 0), false, inicial).multiplicar(inicial.ps[1]));
		r.resolver();
		p2 = p2.multiply(r.p);
		return p1.add(p2);
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			Integer n = sc.nextInteger();
			if(n == null)
				return;
			int inicio = sc.nextInt() - 1;
			Jugador[] jugadores = new Jugador[n];
			for(int i = 0; i < n; i++)
				jugadores[i] = new Jugador();
			for(int i = 0; i < n - 1; i++)
			{
				jugadores[i].ps[1] = sc.nextBigDecimal();
				jugadores[i].ps[0] = BigDecimal.ONE.subtract(jugadores[i].ps[1]);
			}
			for(int i = 0; i < n; i++)
			{
				int anterior = i - 1;
				if(anterior < 0)
					anterior += n;
				jugadores[i].adjacentes[0] = jugadores[anterior];
				int siguiente = (i + 1) % n;
				jugadores[i].adjacentes[1] = jugadores[siguiente];
			}
			System.out.println(solucionar(jugadores, inicio).divide(BigDecimal.ONE, 10, BigDecimal.ROUND_HALF_UP).toPlainString());
		}
	}
}
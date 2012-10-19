import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;


public class H
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
	
	static String[] jugadas = new String[] {"HHHH", "HHV", "HVH", "VHH", "VV"};
	
	static class Estado
	{
		int w;
		boolean[] acts;
		
		public Estado(int w, boolean[] acts)
		{
			this.w = w;
			this.acts = acts;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(acts);
			result = prime * result + w;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Estado other = (Estado) obj;
			if (!Arrays.equals(acts, other.acts))
				return false;
			if (w != other.w)
				return false;
			return true;
		}
	}
	
	static HashMap <Estado, Long> respuestas = new HashMap <Estado, Long> ();
	
	static long darCuenta(int w, boolean[] actuales)
	{
		Estado e = new Estado(w, actuales);
		if(respuestas.containsKey(e))
			return respuestas.get(e);
		if(w == 1)
		{
			int cuenta = 0;
			ArrayList <Integer> huecos = new ArrayList <Integer> ();
			for(int i = 0; i < 4; i++)
			{
				if(actuales[i])
				{
					if(cuenta != 0)
					{
						huecos.add(cuenta);
						cuenta = 0;
					}
				}
				else
					cuenta++;
			}
			if(cuenta != 0)
				huecos.add(cuenta);
			for(int v : huecos)
				if((v & 1) == 1)
				{
					respuestas.put(e, 0L);
					return 0;
				}
			respuestas.put(e, 1L);
			return 1;
		}
		else
		{
			if(actuales[0] && actuales[1] && actuales[2] && actuales[3])
				return darCuenta(w - 1, new boolean[4]);
			long cuenta = 0;
			ArrayList <boolean[]> puestos = new ArrayList <boolean[]> ();
			for(String s : jugadas)
			{
				boolean[] siguiente = new boolean[4];
				int actual = 0;
				boolean noPudo = false;
				for(char c : s.toCharArray())
				{
					if(c == 'H')
					{
						if(!actuales[actual])
							siguiente[actual] = true;
						actual++;
					}
					if(c == 'V')
					{
						if(actuales[actual] ^ actuales[actual + 1])
						{
							noPudo = true;
							break;
						}
						actual += 2;
					}
				}
				if(!noPudo)
				{
					boolean ya = false;
					for(boolean[] o : puestos)
						if(Arrays.equals(o, siguiente))
						{
							ya = true;
							break;
						}
					if(!ya)
					{
						cuenta += darCuenta(w - 1, siguiente);
						puestos.add(siguiente);
					}
				}
			}
			respuestas.put(e, cuenta);
			return cuenta;
		}
	}
	
	public static void main(String[] args)
	{
		
			Scanner sc = new Scanner();
			int casos = sc.nextInt();
			for(int caso = 1; caso <= casos; caso++)
				System.out.println(caso + " " + darCuenta(sc.nextInt(), new boolean[4]));
	}
}
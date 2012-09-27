import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class C 
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
	
	static class Jugador
	{
		int cuenta = 1;
		LinkedList <Integer> lista = new LinkedList <Integer> ();
		
		Integer simulate(Integer anterior)
		{
			Integer respuesta = null;
			if(!lista.isEmpty())
			{
				int poll = lista.pollFirst();
				if(poll == cuenta++)
					respuesta = poll;
				else
					lista.addLast(poll);
			}
			if(anterior != null)
				lista.addLast(anterior);
			if(cuenta == 14)
				cuenta = 1;
			return respuesta;
		}
	}
	
	static int[] lastDiscards;
	
	static boolean simulate(Jugador[] jugadores)
	{
		int lastChange = 0;
		while(true)
		{
			boolean vacios = true;
			for(int i = 0; i < jugadores.length; i++)
				vacios = vacios && jugadores[i].lista.isEmpty();
			if(vacios)
				return true;
			if(lastChange > 52 * 52)
				return false;
			boolean change = false;
			Integer lastCard = null;
			for(int i = 0; i < jugadores.length; i++)
			{
				lastCard = jugadores[i].simulate(lastCard);
				if(lastCard != null)
				{
					change = true;
					lastDiscards[i] = lastCard;
				}
			}
			if(change)
				lastChange = 0;
			else
				lastChange++;
		}
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int nc = sc.nextInt();
		for(int caso = 1; caso <= nc; caso++)
		{
			int n = sc.nextInt();
			Jugador[] jugadores = new Jugador[n];
			for(int i = 0; i < n; i++)
				jugadores[i] = new Jugador();
			lastDiscards = new int[n];
			for(int i = 0; i < 52; i++)
				jugadores[0].lista.addLast(sc.nextInt());
			System.out.print("Case " + caso + ": ");
			if(simulate(jugadores))
				System.out.println(Arrays.toString(lastDiscards).replace(",", "").replace("[", "").replace("]", ""));
			else
				System.out.println("unwinnable");
		}
	}
}

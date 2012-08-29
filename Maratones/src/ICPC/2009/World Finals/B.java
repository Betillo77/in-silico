import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;


public class B
{
	static class Scanner
	{
		BufferedReader br;
		StringTokenizer st;
		
		public Scanner()
		{
	    	System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
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
				return !st.hasMoreTokens();
			}
			catch(Exception e) { throw new RuntimeException(); }
		}
	}
	
	static abstract class Gate
	{
		Boolean result = null;
		Gate a;
		Gate b;
		int fallo = 0;
		
		boolean calculateResult()
		{
			switch(fallo)
			{
				case 0: return calculateResultI();
				case 1: return !calculateResultI();
				case 2: return false;
				case 3: return true;
			}
			return false;
		}
		
		abstract boolean calculateResultI();
	}
	
	static class Xor extends Gate
	{
		
		@Override
		boolean calculateResultI() 
		{
			
			if(result == null)
				result = a.calculateResult() ^ b.calculateResult();
			return result;
		}
	}
	
	static class And extends Gate
	{
		@Override
		boolean calculateResultI() 
		{
			
			if(result == null)
				result = a.calculateResult() & b.calculateResult();
			return result;
		}
	}
	
	static class Or extends Gate
	{
		@Override
		boolean calculateResultI() 
		{
			
			if(result == null)
				result = a.calculateResult() | b.calculateResult();
			return result;
		}
	}
	
	static class Not extends Gate
	{
		@Override
		boolean calculateResultI() 
		{
			
			if(result == null)
				result = !a.calculateResult();
			return result;
		}
	}
	
	static class Input extends Gate
	{
		@Override
		boolean calculateResultI() 
		{
			return result;
		}
	}
	
	private static void reiniciarTodo(boolean[] bs) 
	{
		for(int i = 0; i < g; i++)
			gates[i].result = null;
		for(int i = 0; i < n; i++)
			inputs[i].result = bs[i];
	}

	private static Gate leerGate(String s) 
	{
		if(s.trim().toLowerCase().charAt(0) == 'i')
			return inputs[Integer.parseInt(s.trim().toLowerCase().substring(1)) - 1];
		else
			return gates[Integer.parseInt(s.trim().toLowerCase().substring(1)) - 1];
	}
	
	static Gate[] inputs;
	static Gate[] gates;
	static Gate[] outputs;
	static int n, g, u;
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			n = sc.nextInt();
			g = sc.nextInt();
			u = sc.nextInt();
			if(n == 0 && g == 0 && u == 0)
				return;
			System.out.print("Case " + caso++ + ": ");
			inputs = new Gate[n];
			gates = new Gate[g];
			outputs = new Gate[u];
			for(int i = 0; i < n; i++)
				inputs[i] = new Input();
			String[][] entradas = new String[g][3];
			for(int i = 0; i < g; i++)
			{
				String tipoG = sc.next();
				int tipo = -1;
				if(tipoG.trim().toLowerCase().contains("a"))
					tipo = 0;
				if(tipoG.trim().toLowerCase().contains("o"))
					tipo = 1;
				if(tipoG.trim().toLowerCase().contains("x"))
					tipo = 2;
				if(tipoG.trim().toLowerCase().contains("n"))
					tipo = 3;
				entradas[i][0] = tipo + "";
				entradas[i][1] = sc.next().trim();
				if(tipo != 3)
					entradas[i][2] = sc.next().trim();
			}
			for(int i = 0; i < g; i++)
			{
				int tipo = Integer.parseInt(entradas[i][0]);
				if(tipo == 0)
					gates[i] = new And();
				else if(tipo == 1)
					gates[i] = new Or();
				else if(tipo == 2)
					gates[i] = new Xor();
				else if(tipo == 3)
					gates[i] = new Not();
			} 
			for(int i = 0; i < g; i++)
			{
				int tipo = Integer.parseInt(entradas[i][0]);
				gates[i].a = leerGate(entradas[i][1]);
				if(tipo != 3)
					gates[i].b = leerGate(entradas[i][2]);
			}
			for(int i = 0; i < u; i++)
				outputs[i] = gates[sc.nextInt() - 1];
			int b = sc.nextInt();
			boolean[][] entradasB = new boolean[b][n];
			boolean[][] salidasB = new boolean[b][u];
			for(int i = 0; i < b; i++)
			{
				for(int j = 0; j < n; j++)
					entradasB[i][j] = sc.nextInt() == 1;
				for(int j = 0; j < u; j++)
					salidasB[i][j] = sc.nextInt() == 1;
			}
			ArrayList <String> matches = new ArrayList <String> ();
			boolean pailaB = false;
			for(int k = 0; k < b; k++)
			{
				reiniciarTodo(entradasB[k]);
				boolean[] salidaEsta = new boolean[u];
				for(int l = 0; l < u; l++)
					salidaEsta[l] = outputs[l].calculateResult();
				if(!Arrays.equals(salidaEsta, salidasB[k]))
				{
					pailaB = true;
					break;
				}
			}
			if(pailaB)
			{
				for(int i = 0; i < g; i++)
				{
					for(int j = 1; j < 4; j++)
					{
						gates[i].fallo = j;
						boolean paila = false;
						for(int k = 0; k < b; k++)
						{
							reiniciarTodo(entradasB[k]);
							boolean[] salidaEsta = new boolean[u];
							for(int l = 0; l < u; l++)
								salidaEsta[l] = outputs[l].calculateResult();
							if(!Arrays.equals(salidaEsta, salidasB[k]))
							{
								paila = true;
								break;
							}
						}
						if(!paila)
							matches.add((i + 1) + " " + j);
					}
					gates[i].fallo = 0;
				}
				if(matches.size() != 1)
					System.out.println("Unable to totally classify the failure");
				else
				{
					StringTokenizer st = new StringTokenizer(matches.get(0));
					System.out.print("Gate " + st.nextToken() + " is failing;");
					switch(Integer.parseInt(st.nextToken()))
					{
						case 1 : System.out.println(" output inverted"); break;
						case 2 : System.out.println(" output stuck at 0"); break;
						case 3 : System.out.println(" output stuck at 1"); break;
					}
				}
			}
			else
				System.out.println("No faults detected");
		}
	}
}

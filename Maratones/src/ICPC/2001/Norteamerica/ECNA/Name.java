import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class Name 
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

	
	static TreeSet <String> nombresSet = new TreeSet <String> ();
	static ArrayList <String> nombres = new ArrayList <String> ();

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int casos = sc.nextInt();
		for(int caso = 1; caso <= casos; caso++)
		{
			int n = sc.nextInt();
			ArrayList <String> usuarios = new ArrayList <String> ();
			for(int i = 0; i < n; i++)
				usuarios.add(sc.next());
			nombresSet.clear();
			nombres.clear();
			ArrayList <String[]> instrucciones = new ArrayList <String[]> ();
 			while(true)
			{
				String inst = sc.next();
				if(inst.equals("Q"))
					break;
				String nombre = sc.next();
				instrucciones.add(new String[]{inst, nombre});
				if(!inst.equals("M"))
					nombresSet.add(nombre);
			}
 			nombres.addAll(nombresSet);
 			BitSet[] mascaras = new BitSet[n];
 			for(int i = 0; i < n; i++)
 			{
 				mascaras[i] = new BitSet();
 				mascaras[i].set(0, nombres.size());
 			}
 			BitSet actual = new BitSet();
 			for(String[] inst : instrucciones)
 			{
 				if(inst[0].equals("E"))
 					actual.set(nombres.indexOf(inst[1]));
 				else if(inst[0].equals("L"))
 					actual.clear(nombres.indexOf(inst[1]));
 				else
 					mascaras[usuarios.indexOf(inst[1])].and(actual);
 			}
 			String[] ans = new String[nombres.size()];
 			boolean[] quitados = new boolean[n];
 			while(true)
 			{
 				boolean cambio = false;
 				for(int i = 0; i < n; i++)
 				{
 					if(quitados[i])
 						continue;
 					int cuenta = mascaras[i].cardinality();
 					if(cuenta == 1)
 						break;
 					int cuantos = 0;
 					for(int j = 0; j < n; j++)
 					{
 						if(quitados[j])
 							continue;
 						if(contenida(mascaras[j], mascaras[i]))
 							cuantos++;
 					}
 					if(cuantos == cuenta)
 					{
 						for(int j = 0; j < n; j++)
 	 					{
 	 						if(quitados[j])
 	 							continue;
 							if(!contenida(mascaras[j], mascaras[i]))
 	 							mascaras[j].andNot(mascaras[i]);
 	 					}
 					}
 				}
 				for(int i = 0; i < n; i++)
 				{
 					if(quitados[i])
 						continue;
 					if(mascaras[i].cardinality() == 1)
 					{
 						int indice = mascaras[i].nextSetBit(0);
 						ans[indice] = usuarios.get(i);
 						for(BitSet b : mascaras)
 							b.clear(indice);
 						quitados[i] = true;
 						cambio = true;
 					}
 				}
 				if(!cambio)
 					break;
 			}
 			if(caso != 1)
 				System.out.println();
 			for(int i = 0; i < nombres.size(); i++)
 				System.out.println(nombres.get(i) + ":" + (ans[i] == null ? "???" : ans[i]));
		}
	}

	private static boolean contenida(BitSet a, BitSet b) 
	{
		BitSet temp = new BitSet();
		temp.or(a);
		temp.andNot(b);
		if(temp.isEmpty())
			return true;
		else
			return false;
	}
}
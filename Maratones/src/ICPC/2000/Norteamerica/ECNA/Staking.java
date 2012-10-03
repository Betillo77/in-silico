import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Staking
{
	static class Scanner
	{
		BufferedReader br;
		StringTokenizer st;
		
		public Scanner()
		{
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
		
		public long nextLong()
		{
			return Long.parseLong(next());
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
	}
	
	static ArrayList < ArrayList <Integer> > left(ArrayList < ArrayList <Integer> > original)
	{
		original = clonar(original);
		ArrayList < ArrayList <Integer> > result = new ArrayList <ArrayList <Integer>> ();
		while(true)
		{
			ArrayList <Integer> actual = new ArrayList <Integer> ();
			for(int k = 0; k < original.size(); k++)
			{
				int cuenta = 0;
				for(int j = 0; j < original.get(k).size(); j++)
				{
					if(original.get(k).get(j) != 0)
					{
						original.get(k).set(j, original.get(k).get(j) - 1);
						cuenta++;
					}
				}
				if(cuenta != 0)
					actual.add(cuenta);
				else
					break;
			}
			if(!actual.isEmpty())
				result.add(actual);
			else
				break;
		}
		return result;
	}
	
	static ArrayList < ArrayList <Integer> > right(ArrayList < ArrayList <Integer> > original)
	{
		original = clonar(original);
		ArrayList < ArrayList <Integer> > result = new ArrayList <ArrayList <Integer>> ();
		int mayor = original.get(0).get(0);
		for(int i = 0; i < original.get(0).size(); i++)
		{
			ArrayList <Integer> actual = new ArrayList <Integer> ();
			for(int k = 0; k < mayor; k++)
			{
				int cuenta = 0;
				for(int j = original.size() - 1; j >= 0; j--)
				{
					if(original.get(j).size() > i && original.get(j).get(i) != 0)
					{
						original.get(j).set(i, original.get(j).get(i) - 1);
						cuenta++;
					}
				}
				if(cuenta != 0)
					actual.add(cuenta);
				else
					break;
			}
			if(!actual.isEmpty())
				result.add(actual);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	static ArrayList<ArrayList<Integer>> clonar(ArrayList<ArrayList<Integer>> original) 
	{
		ArrayList < ArrayList <Integer> > result = new ArrayList <ArrayList <Integer>> ();
		for(ArrayList <Integer> a : original)
			result.add((ArrayList<Integer>) a.clone());
		return result;
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int empezo = 0;
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			ArrayList < ArrayList <Integer> > original = new ArrayList < ArrayList <Integer> > ();
			for(int i = 0; i < n; i++)
			{
				ArrayList <Integer> act = new ArrayList <Integer> ();
				while(true)
				{
					int next = sc.nextInt();
					if(next == 0)
						break;
					act.add(next);
				}
				original.add(act);
			}
			if(empezo++ != 0)
			{
				System.out.println();
				System.out.println();
			}
			for(ArrayList <Integer> a : left(original))
				System.out.println(a.toString().replace("[", "").replace("]", "").replace(",", ""));
			System.out.println();	
			for(ArrayList <Integer> a : right(original))
				System.out.println(a.toString().replace("[", "").replace("]", "").replace(",", ""));
			
		}
	}
}

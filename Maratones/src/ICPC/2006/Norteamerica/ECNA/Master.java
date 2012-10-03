import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Master 
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
	
	static class Solution
	{
		final int[] response = new int[2];
		final int[] ca;
		final int[] cb;
		final boolean[] bt;
		final int n;
		final int[] current;
		final int[][] results;
		final int c;
		final LinkedList <int[]> list;
		
		Solution(int nn, int cc)
		{
			n = nn;
			c = cc;
			current = new int[n];
			list = new LinkedList <int[]> ();
			ca = new int[c];
			cb = new int[c];
			bt = new boolean[c];
			results = new int[n + 1][n + 1];
			add(0);
		}
		
		void count(int[] a, int[] b)
		{
			response[0] = response[1] = 0;
			for(int i = c; i < b.length; i++)
			{
				int aB = a[b[i]];
				int bB = b[b[i]];
				int c = cuenta[aB & bB];
				response[0] += c;
				int x = cuenta[bB] - c;
				int y = cuenta[aB & (~bB)];
				response[1] += x < y ? x : y;
			}
		}
		
		void simulate(int[] guess, int[] r)
		{
			for(Iterator <int[]> it = list.iterator(); it.hasNext();)
			{
				int[] next = it.next();
				count(next, guess);
				if(response[0] != r[0] || response[1] != r[1] || next == guess)
					it.remove();
			}
		}
		
		void countSimulate(int[] guess)
		{
			for(int i = 0; i < n + 1; i++)
				for(int j = 0; j < n + 1; j++)
					results[i][j] = 0;
			for(Iterator <int[]> it = list.iterator(); it.hasNext();)
			{
				int[] next = it.next();
				if(next == guess)
					continue;
				count(next, guess);
				results[response[0]][response[1]]++;
			}
		}
		
		int[][] giveBest(LinkedList <int[]> list)
		{
			int[][] response = new int[2][];
			response[1] = new int[1];
			int currentBest = Integer.MAX_VALUE;
			int[] answerBest = null;
			for(Iterator <int[]> it = list.iterator(); it.hasNext();)
			{
				int[] next = it.next();
				int max = 0;
				countSimulate(next);
				for(int i = 0; i <= n; i++)
					for(int j = 0; j <= n; j++)
					{
						int ans = results[i][j];
						if(ans > max)
							max = ans;
					}
				if(max < currentBest)
				{
					currentBest = max;
					answerBest = next;
				}
			}
			response[0] = answerBest;
			response[1][0] = currentBest;
			return response;
		}
		
		int[] convertir(int[] current)
		{
			int[] newS = new int[c];
			for(int i = 0; i < n; i++)
				newS[current[i]] |= 1 << i;
			ArrayList <Integer> valores = new ArrayList <Integer> ();
			for(int i = 0; i < c; i++)
				if(newS[i] != 0)
					valores.add(i);
			int[] nS = new int[c + valores.size()];
			for(int i = 0; i < c; i++)
				nS[i] = newS[i];
			for(int i = 0; i < valores.size(); i++)
				nS[i + c] = valores.get(i);
			return nS;
		}
		
		void add(final int l)
		{
			if(l == n)
				list.add(convertir(current));
			else
			{
				for(int i = 0; i < c; i++)
				{
					current[l] = i;
					add(l + 1);
				}
			}
		}

		public String imprimir(int[] s)
		{
			char[] respuesta = new char[n];
			for(int i = c; i < s.length; i++)
			{
				char valor = (char) (s[i] + 'A');
				int temp = s[s[i]];
				for(int j = 0; j < n; j++)
				{
					if((temp & 1) == 1)
						respuesta[j] = valor;
					temp >>= 1;
				}
			}
			return String.valueOf(respuesta);
		}
	}
	
	static int[] cuenta = new int[1 << 15];
	public static void main(String[] args)
	{
		for(int i = 0; i < cuenta.length; i++)
			cuenta[i] = Integer.bitCount(i);
		Scanner sc = new Scanner();
		int tc = sc.nextInt();
		for(int caso = 0; caso < tc; caso++)
		{
			int l = sc.nextInt();
			int c = sc.nextInt();
			int gs = sc.nextInt();
			Solution s = new Solution(l, c);
			
			int[] currG = new int[2];
			LinkedList <int[]> list = new LinkedList <int[]> ();
			list.addAll(s.list);
			for(int i = 0; i < gs; i++)
			{
				int[] curr = new int[l];
				char[] guess = sc.next().toCharArray();
				currG[0] = sc.nextInt();
				currG[1] = sc.nextInt();
				for(int j = 0; j < l; j++)
					curr[j] = guess[j] - 'A';
				curr = s.convertir(curr);
				s.simulate(curr, currG);
			}
			int[][] sol = s.giveBest(list);
			System.out.print(s.imprimir(sol[0]));
			System.out.println(" " + sol[1][0]);
		}
	}
}
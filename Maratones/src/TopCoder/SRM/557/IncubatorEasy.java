import java.util.ArrayList;


public class IncubatorEasy
{
	public int maxMagicalGirls(String[] love)
	{
		int n = love.length;
		int limite = 1 << n;
		@SuppressWarnings("unchecked")
		ArrayList <Integer> [] lovesMe = new ArrayList[n];
		for(int i = 0; i < n; i++)
			lovesMe[i] = new ArrayList <Integer> ();
		for(int i = 0; i < n; i++)
		{
			for(int j = 0; j < n; j++)
				if(love[i].charAt(j) == 'Y')
					lovesMe[j].add(i);
		}
		int best = 0;
		for(int i = 0; i < limite; i++)
		{
			boolean[] magical = new boolean[n];
			boolean[] prot = new boolean[n];
			int mascara = i;
			int j = 0;
			while(mascara != 0)
			{
				if((mascara & 1) == 1)
					magical[j] = true;
				j++;
				mascara >>= 1;
			}
			for(int jj = 0; jj < n * n + 1; jj++)
				for(j = 0; j < n; j++)
				{
					for(int k : lovesMe[j])
						if(magical[k] || prot[k])
							prot[j] = true;
				}
			int count = 0;
			for(j = 0; j < n; j++)
				if(magical[j] && !prot[j])
					count++;
			best = Math.max(best, count);
		}
		return best;
	}
}
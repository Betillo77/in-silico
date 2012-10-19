
public class GreatFairyWar
{	
	public int minHP(int[] dps, int[] hp)
	{
		int total = 0;
		for(int i = 0; i < dps.length; i++)
		{
			int cuenta = 0;
			for(int j = i; j < dps.length; j++)
				cuenta += dps[j];
			total += cuenta * hp[i];
		}
		return total;
	}
}
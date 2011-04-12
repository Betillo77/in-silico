package implementacion.reinforcement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Shoe 
{
	Random random = new Random();
	int[] cards;
	int actual = 0;
	
	public Shoe(int nBarajas)
	{
		cards = new int[nBarajas * 52];
		ArrayList <Integer> a = new ArrayList <Integer> ();
		for(int i = 0; i < nBarajas; i++)
			for(int j = 0; j < 4; j++)
				for(int k = 1; k < 14; k++)
					a.add(k > 10 ? 10 : k);
		Collections.shuffle(a, random);
		for(int i = 0; i < cards.length; i++)
			cards[i] = a.get(i);
	}
	
	public double porcentajeFaltante()
	{
		double f = cards.length - actual;
		return  f / cards.length;
	}
	
	public int carta()
	{
		return cards[actual++];
	}
}
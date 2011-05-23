package implementacion.reinforcement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Shoe 
{
	public static Random random = new Random();
	int[] cards;
	int[] strategy = //new int[] {0, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0};
					 //new int[] {0, -1, 1, 1, 1, 1, 1, 1, 0, 0, -1};
	 				 //new int[] {0, -1, 1, 1, 1, 1, 1, 0, 0, 0, -1};
	 				 //new int[] {0, 0, 0, 1, 1, 1, 1, 0, 0, 0, -1};
					 new int[] {0, 0, 1, 1, 2, 2, 1, 1, 0, 0, -2};
					 //new int[] {0, -1, 1, 1, 2, 2, 2, 1, 0, 0, -2};
					 //new int[] {0, 0, 1, 1, 2, 2, 2, 1, 0, -1, -2};
	int actual = 0;
	public int count = 0;
	
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
		return  1 - f / cards.length;
	}
	
	public int card()
	{
		if(actual == cards.length)
			return Math.min(10, random.nextInt(13) + 1);
		int carta = cards[actual++];
		count += strategy[carta];
		return carta;
	}
}
package blackjack;

import java.util.ArrayList;
import java.util.Random;

public class State
{
	private static class VisibleState
	{
		int points;
		int nAces;
		
		static final int VISIBLESTATES = 21 * 32 + 32;
		static VisibleState[] memory = new VisibleState[VISIBLESTATES];
		
		VisibleState(int nP, int nA)
		{
			nAces = nA;
			points = nP;
		}
		
		public int id()
		{
			return nAces * 31 + points;
		}
		
		public static VisibleState fromId(int id)
		{
			if(memory[id] != null)
				return memory[id];
			return memory[id] = new VisibleState(id % 31, id / 31);
		}
		
		public static VisibleState getVisibleState(int points, int aces)
		{
			return fromId(aces * 31 + points);
		}
		
		public VisibleState add(int card)
		{
			if(card == 1)
				return getVisibleState(points + 1, nAces + 1);
			return getVisibleState(points + card, nAces);
		}
	}

	VisibleState visible;
	int hiddenCard;
	
	static State[] memory = new State[VisibleState.VISIBLESTATES * 10];
    static final Random r = new Random();
	
	
	State(int idVs, int hCard)
	{
		visible = VisibleState.fromId(idVs);
		hiddenCard = hCard;
	}
	
	public int id()
	{
		return (hiddenCard - 1) * VisibleState.VISIBLESTATES + visible.id();
	}
	
	public static State fromId(int id)
	{
		if(memory[id] != null)
			return memory[id];
		return  memory[id] = new State(id % VisibleState.VISIBLESTATES, id / VisibleState.VISIBLESTATES);
	}
	
	public static State getStateFromViId(int idVs, int hCard)
	{
		return fromId((hCard - 1) * VisibleState.VISIBLESTATES + idVs);
	}
	
	public static State getState(VisibleState s, int hCard)
	{
		return getStateFromViId(s.id(), hCard);
	}
	
	public int getMinPoints()
	{
		return visible.points + hiddenCard;
	}
	
	public int getBestPoints()
	{
		int points = visible.points + hiddenCard;
		int aces = visible.nAces + (hiddenCard == 1 ? 1 : 0);
		points += aces * 10;
		while(points > 21 && aces > 0)
		{
			points -= 10;
			aces--;
		}
		return points;
	}
	
	public int getMaxPoints()
	{
		int points = visible.points + hiddenCard;
		int aces = visible.nAces + (hiddenCard == 1 ? 1 : 0);
		points += aces * 10;
		return points;
	}
	
	public static int getBestPoints(VisibleState v)
	{
		int points = v.points;
		int aces = v.nAces;
		points += aces * 10;
		while(points > 21 && aces > 0)
		{
			points -= 10;
			aces--;
		}
		return points;
	}
	
	public State add(int card)
	{
		return new State(visible.add(card).id(), hiddenCard);
	}
	 
	public static State drawFirst()
	{
		return new State(0, r.nextInt(10) + 1);
	}
	public State drawCard()
	{
		int next = r.nextInt(10) + 1;
		return add(next);
	}
	
	public ArrayList <State> getNextStates(int hidden)
	{
		int bestPoints = getBestPoints();
		if(bestPoints >= 21)
			return new ArrayList <State> ();
		ArrayList <State> next = new ArrayList <State> ();
		for(int i = 1; i < 11; i++)
		{
			VisibleState possible = visible.add(i);
			next.add(getState(possible, hiddenCard));
		}
		return next;
	}
}
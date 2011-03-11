package blackjack;

import java.util.Random;

public class State
{
	public static class VisibleState
	{
		int points;
		
		static final int VISIBLESTATES = 22;
		static VisibleState[] memory = new VisibleState[VISIBLESTATES];
                static final VisibleState volo = fromId(21);
		
		VisibleState(int nP)
		{
			points = nP;
		}
		
		public int id()
		{
			return points;
		}
		
		public static VisibleState fromId(int id)
		{
                        if(id >= 21)
                            return volo;
			if(memory[id] != null)
				return memory[id];
			return memory[id] = new VisibleState(id);
		}
		
		public static VisibleState getVisibleState(int points)
		{
			return fromId(points);
		}
		
		public VisibleState add(int card)
		{
			return getVisibleState(points + card);
		}
	}
        
        VisibleState opponent;
	VisibleState visible;
	int hiddenCard;
	
	static State[] memory = new State[VisibleState.VISIBLESTATES * VisibleState.VISIBLESTATES * 100];
        static final Random r = new Random();

	State(int idVs, int idOs, int hidden)
	{
		visible = VisibleState.fromId(idVs);
                opponent = VisibleState.fromId(idOs);
		hiddenCard = hidden;
	}
	
	public int id()
	{
		return visible.id() * VisibleState.VISIBLESTATES * VisibleState.VISIBLESTATES + opponent.id() * VisibleState.VISIBLESTATES + hiddenCard;
	}
	
	public static State fromId(int id)
	{
		if(memory[id] != null)
			return memory[id];
		return  memory[id] = new State(id / (VisibleState.VISIBLESTATES * VisibleState.VISIBLESTATES), (id / (VisibleState.VISIBLESTATES)) % VisibleState.VISIBLESTATES, id % VisibleState.VISIBLESTATES);
	}
	
	public static State getStateFromViId(int idVs, int idOs, int hCard)
	{
		return fromId(idVs * VisibleState.VISIBLESTATES * VisibleState.VISIBLESTATES + idOs * VisibleState.VISIBLESTATES + hCard);
	}
	
	public static State getState(VisibleState v, VisibleState o, int hCard)
	{
		return getStateFromViId(v.id(), o.id(), hCard);
	}
	
	public int getPoints()
	{ 
		return visible.points + hiddenCard;
	} 

	public State add(int card, boolean mine)
	{
		if(mine)
                    return getState(visible.add(card), opponent, card);
                else
                    return getState(visible, opponent.add(card), card);
	}
	 
	public static State drawFirst()
	{
		return getState(VisibleState.fromId(0).add(r.nextInt(10) + 1), VisibleState.fromId(0).add(r.nextInt(10) + 1), r.nextInt(10) + 1);
	}
	public State drawCard(boolean mine)
	{
		int next = r.nextInt(10) + 1;
		return add(next, mine);
        }
}
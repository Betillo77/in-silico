package implementacion.reinforcement;

import genericos.reinforcement.Estado;
import java.util.HashMap;

/**
 *
 * @author sebastian, santiago
 */
public class BJState implements Estado 
{
	static HashMap <BJState, BJState> states = new HashMap <BJState, BJState> (4000037);
    int sum;
    int nAces;
    int dealerCard;
    int count;
    boolean twoCards;
    boolean equalCards;
    Integer hash;
    
    public BJState(int s, int nA, int dC, int c, boolean tC, boolean eC)
    {
    	sum = s;
    	nAces = nA;
    	dealerCard = dC;
    	count = c;
    	twoCards = tC;
    	equalCards = eC;
    }
    
    static BJState temp = new BJState(0, 0, 0, 0, false, false);
    
    public static BJState getBJState(int sum, int nAces, int dealerCard, int count, boolean twoCards, boolean equalCards)
    {
    	temp.sum = sum;
    	temp.nAces = nAces;
    	temp.dealerCard = dealerCard;
    	temp.count = count;
    	temp.twoCards = twoCards;
    	temp.equalCards = equalCards;
    	if(states.containsKey(temp))
    		return states.get(temp);
    	states.put(temp, new BJState(sum, nAces, dealerCard, count, twoCards, equalCards));
    	return states.get(temp);
    }
    
    @Override
	public int hashCode() 
    {
    	if(hash != null)
    		return hash.intValue();
		final int prime = 23;
		int result = 1;
		result =   529 * result + (count + 260);
		result = prime * result + nAces;
		result = prime * result + dealerCard;
		result = prime * result + sum;
		result = 11 * result + (twoCards ? 7 : 5);
		result = 11 * result + (equalCards ? 7 : 5);
		return hash = result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		BJState other = (BJState) obj;
		return count == other.count && nAces == other.nAces && sum == other.sum && twoCards == other.twoCards;
	}

	@Override
    public int compareTo(Estado o) 
    {
    	BJState other = (BJState) o;
    	if(sum != other.sum)
    		return sum > other.sum ? 1 : -1;
    	if(nAces != other.nAces)
    		return nAces > other.nAces ? 1 : -1;
    	if(count != other.count)
    		return count > other.count ? 1 : -1;
    	if(twoCards && !other.twoCards)
    		return 1;
    	if(!twoCards && other.twoCards)
    		return -1;
    	return 0;
    }
}
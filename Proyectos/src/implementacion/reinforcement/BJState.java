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
    int count;
    boolean twoCards;
    Integer hash;
    
    public BJState(int s, int nA, int c, boolean tC)
    {
    	sum = s;
    	nAces = nA;
    	count = c;
    	twoCards = tC;
    }
    
    static BJState temp = new BJState(0, 0, 0, false);
    
    public BJState getBJState(int sum, int nAces, int count, boolean twoCards)
    {
    	temp.sum = sum;
    	temp.nAces = nAces;
    	temp.count = count;
    	temp.twoCards = twoCards;
    	if(states.containsKey(temp))
    		return states.get(temp);
    	states.put(temp, new BJState(sum, nAces, count, twoCards));
    	return states.get(temp);
    }
    
    @Override
	public int hashCode() 
    {
    	if(hash != null)
    		return hash.intValue();
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + nAces;
		result = prime * result + sum;
		result = prime * result + (twoCards ? 1231 : 1237);
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
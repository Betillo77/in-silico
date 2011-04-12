package implementacion.reinforcement;

import genericos.reinforcement.Accion;
import genericos.reinforcement.Ambiente;
import genericos.reinforcement.Estado;
import genericos.reinforcement.Par;

import java.util.LinkedList;

/**
 *
 * @author seb
 */
public class BlackJack implements Ambiente 
{   
	Shoe shoe;
	LinkedList <Accion> betActions = new LinkedList <Accion> ();
	LinkedList <Accion> doubleActions = new LinkedList <Accion> ();
	LinkedList <Accion> splitActions = new LinkedList <Accion> ();
	LinkedList <Accion> normalActions = new LinkedList <Accion> ();
	LinkedList <Estado> pendingGames = new LinkedList <Estado> ();
	LinkedList <Estado> finishedGames = new LinkedList <Estado> ();
	int lastBet;
	
    public BlackJack()
    {
    	betActions.add(BJAction.BET1);
    	betActions.add(BJAction.BET2);
    	betActions.add(BJAction.BET3);
    	betActions.add(BJAction.BET4);
    	betActions.add(BJAction.BET5);
    	doubleActions.add(BJAction.DOUBLE);
    	doubleActions.add(BJAction.HIT);
    	doubleActions.add(BJAction.STAND);
    	doubleActions.add(BJAction.SURRENDER);
    	splitActions.add(BJAction.DOUBLE);
    	splitActions.add(BJAction.SPLIT);
    	splitActions.add(BJAction.HIT);
    	splitActions.add(BJAction.STAND);
    	splitActions.add(BJAction.SURRENDER);
    	normalActions.add(BJAction.HIT);
    	normalActions.add(BJAction.STAND);
    }

    public Par <Double,Estado> getRewardAndState(Estado s, Accion ac) 
    {
    	throw(new RuntimeException("Todavia no se ha implementado"));
    }

    public Estado getInitialState() 
    {
    	shoe = new Shoe(8);
    	return BJState.getBJState(0, 0, 0, 0, false, false);
    }

    public LinkedList <Accion> getAcciones(Estado s)
    {
    	BJState b = (BJState) s;
    	if(b.sum == 0)
    		return betActions;
    	else if(b.twoCards && b.equalCards && (pendingGames.size() + finishedGames.size() < 4))
    		return splitActions;
    	else if(b.twoCards)
    		return doubleActions;
    	else
    		return normalActions;
    }

    public boolean isTerminalState(Estado s) 
    {
    	BJState b = (BJState) s;
    	return b.sum == 0 && shoe.porcentajeFaltante() <= 0.1;
    }

    public static void main(String[] args) 
    {
    	throw(new RuntimeException("Todavia no se ha implementado"));
    }
}
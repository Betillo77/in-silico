package implementacion.reinforcement;

import genericos.reinforcement.Accion;
import genericos.reinforcement.Ambiente;
import genericos.reinforcement.Estado;
import genericos.reinforcement.Par;
import genericos.reinforcement.Reinforcement;

import java.util.LinkedList;

public class BlackJack implements Ambiente 
{   
	Shoe shoe = new Shoe(1);
	LinkedList <Accion> betActions = new LinkedList <Accion> ();
	LinkedList <Accion> doubleActions = new LinkedList <Accion> ();
	LinkedList <Accion> splitActions = new LinkedList <Accion> ();
	LinkedList <Accion> normalActions = new LinkedList <Accion> ();
	LinkedList <Accion> onlySplitActions = new LinkedList <Accion> ();
	LinkedList < LinkedList <BJState> > currentSplit = new LinkedList < LinkedList <BJState> > ();
	LinkedList <BJState> currentHand = new LinkedList <BJState> ();
	double lastBet;
	int lastDealerCount;
	int nGames = 0;
	boolean dealerBlackJack;
	boolean inSplit = false;
	public double acum = 0d;
	Estado finalizacion = new BJState(22, 0, 0, 0, false, false);
	
    public BlackJack()
    {
    	betActions.add(BJAction.BET1);
    	betActions.add(BJAction.BET2);
    	betActions.add(BJAction.BET3);
    	betActions.add(BJAction.BET4);
    	betActions.add(BJAction.BET5);
//    	doubleActions.add(BJAction.DOUBLE);
    	doubleActions.add(BJAction.HIT);
    	doubleActions.add(BJAction.STAND);
//    	doubleActions.add(BJAction.SURRENDER);
//     	splitActions.add(BJAction.DOUBLE);
//      splitActions.add(BJAction.SPLIT);
    	splitActions.add(BJAction.HIT);
    	splitActions.add(BJAction.STAND);
//    	splitActions.add(BJAction.SURRENDER);
//    	onlySplitActions.add(BJAction.SPLIT);
    	normalActions.add(BJAction.HIT);
    	normalActions.add(BJAction.STAND);
    }
    
    private Estado getNewGame()
    {
    	nGames++;
    	if(currentSplit.size() != 0)
    	{
    		inSplit = true;
    		currentHand.clear();
    		currentHand.addAll(currentSplit.peek());
    		return currentSplit.peek().poll();
    	}
    	else
    	{
    		inSplit = false;
    		currentHand.clear();
    		return BJState.getBJState(0, 0, 0, shoe.count, false, false);
    	}
    }

    private Estado getNextState()
    {
    	return finalizacion;
    }
    
    private double getResult(int cuenta, boolean blackJack)
    {
    	double res = 0;
		if(cuenta < lastDealerCount)
			res = -lastBet;
		else if(cuenta == lastDealerCount)
			res = dealerBlackJack ? (blackJack ? 0 : -lastBet) : (blackJack ? 3.0 * lastBet / 2.0 : 0);
		else
			res = blackJack ? 3.0 * lastBet / 2.0 : lastBet;
		return res;
    }
    
    private BJState getState(int cartaA, int cartaB, int cartaDealer)
    {
    	int nAces = (cartaA == 1 ? 1 : 0) + (cartaB == 1 ? 1 : 0);
    	return BJState.getBJState(cartaA + cartaB, nAces, cartaDealer, shoe.count, true, cartaA == cartaB);
	}
    
    public Par <Double, Estado> getRewardAndState(Estado s, Accion ac) 
    {
    	BJState e = (BJState) s;
    	BJAction a = (BJAction) ac;
    	if(a == BJAction.SURRENDER)
    	{
    		acum += -lastBet / 2.0;
    		return new Par <Double, Estado> (-lastBet / 2.0, getNextState());
    	}
    	else if(a == BJAction.STAND)
    	{
    		int cuenta = e.getCount();
    		double res = getResult(cuenta, e.blackJack());
    		acum += res;
    		return new Par <Double, Estado> (res, getNextState());
    	}
    	else if(a == BJAction.SPLIT)
    	{
    		if(inSplit && !currentSplit.isEmpty() && currentSplit.peek().size() != 0)
        		return new Par <Double, Estado> (0d, currentSplit.peek().poll());
    		else
    		{
    			if(currentHand.peekLast() != e)
    				currentHand.add(e);
	    		int carta = e.sum >> 1;
	    		int cardA = shoe.card();
	    		int cardB = shoe.card();
	    		BJState eA = BJState.getBJState(carta + cardA, (carta == 1 ? 1 : 0) + (cardA == 1 ? 1 : 0), e.dealerCard, shoe.count, true, carta == cardA);
	    		BJState eB = BJState.getBJState(carta + cardB, (carta == 1 ? 1 : 0) + (cardB == 1 ? 1 : 0), e.dealerCard, shoe.count, true, carta == cardB).duplicate();
    			LinkedList <BJState> bHand = new LinkedList <BJState> ();
    			bHand.addAll(currentHand);
    			bHand.add(eB);
    			currentSplit.add(bHand);
	    		return new Par <Double, Estado> (0d, eA);
    		}
    	}
    	else if(a == BJAction.DOUBLE)
    	{
    		int cartaSiguiente = shoe.card();
    		int suma = e.sum + cartaSiguiente;
    		if(suma > 21)
    		{
    			acum -= lastBet * 2;
    			return new Par <Double, Estado> (-lastBet * 2 + 0d, getNextState());
    		}
    		else
    		{
    			double res = getResult(suma, false);
    			acum += res * 2;
    			return new Par <Double, Estado> (res * 2, getNextState());
    		}
    	}
    	else if(a == BJAction.HIT)
    	{
    		int cartaSiguiente = shoe.card();
    		int suma = e.sum + cartaSiguiente;
    		if(suma > 21)
    		{
    			acum -= lastBet;
    			return new Par <Double, Estado> (-lastBet + 0d, getNextState());
    		}
    		else
    		{
    			BJState siguiente = BJState.getBJState(suma, e.nAces + (cartaSiguiente == 1 ? 1 : 0), e.dealerCard, shoe.count, false, false);
        		return new Par <Double, Estado> (0d, siguiente);
    		}
    	}
    	else
    	{
    		int factor = 0;
    		if(a == BJAction.BET1)
    			factor = 1;
    		else if(a == BJAction.BET2)
    			factor = 2;
    		else if(a == BJAction.BET3)
    			factor = 3;
    		else if(a == BJAction.BET4)
    			factor = 30;
    		else
    			factor = 100;
    		lastBet = factor;
    		int cartaDealer = shoe.card();
    		BJState dealer = getState(cartaDealer, shoe.card(), 0);
    		while(dealer.getCount() < 17)
    		{
    			int cartaSiguiente = shoe.card();
    			int sumaSiguiente = cartaSiguiente + dealer.sum;
    			if(sumaSiguiente > 21)
    			{
        			dealer = BJState.getBJState(0, dealer.nAces + (cartaSiguiente == 1 ? 1 : 0), 1, shoe.count, false, false);
        			break;
    			}
    			dealer = BJState.getBJState(sumaSiguiente, dealer.nAces + (cartaSiguiente == 1 ? 1 : 0), 1, shoe.count, false, false);
    		}
    		lastDealerCount = dealer.getCount();
    		dealerBlackJack = dealer.blackJack();
    		BJState siguiente = getState(shoe.card(), shoe.card(), cartaDealer);
    		return new Par <Double, Estado> (0d, siguiente);
    	}
    }

	public Estado getInitialState() 
    {
    	return getNewGame();
    }

	public LinkedList <Accion> getAcciones(Estado s)
    {
    	BJState b = (BJState) s;
    	if(b.sum == 0)
    		return betActions;
		if(inSplit && !currentSplit.isEmpty() && currentSplit.peek().size() != 0)
			return onlySplitActions;
		else if(inSplit && ! currentSplit.isEmpty())
			currentSplit.poll();
    	if(b.twoCards && b.equalCards)
    		return splitActions;
       	else if(b.twoCards)
    		return doubleActions;
    	else
    		return normalActions;
    }

    public boolean isTerminalState(Estado s) 
    {
    	if(shoe.porcentajeFaltante() <= 0.1 && currentSplit.size() == 0)
    		shoe = new Shoe(2);
    	return s == finalizacion;
    }

    public static void main(String[] args) throws InterruptedException 
    {
    	Reinforcement r = new Reinforcement();
    	BlackJack b = new BlackJack();
		r.amb = b;
		r.r = Shoe.random;
    	for(int i = 1; i <= 2000000000; i++)
    	{
    		r.jugar();
    		if(b.nGames == 1000)
    		{
    			System.out.println(b.acum / b.nGames + " " + b.nGames + " " + r.T);
    			b.acum = 0;
    			b.nGames = 0;
    		}
    	}
    }
}
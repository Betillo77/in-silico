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
    public BlackJack()
    {
    }

    public Par <Double,Estado> getRewardAndState(Estado s, Accion ac) 
    {
    	throw(new RuntimeException("Todavia no se ha implementado"));
    }

    public Estado getInitialState() 
    {
    	throw(new RuntimeException("Todavia no se ha implementado"));
    }

    public LinkedList<Accion> getAcciones()
    {
    	throw(new RuntimeException("Todavia no se ha implementado"));
    }

    public boolean isTerminalState(Estado s) 
    {
    	throw(new RuntimeException("Todavia no se ha implementado"));
    }

    public static void main(String[] args) 
    {
    	throw(new RuntimeException("Todavia no se ha implementado"));
    }
}
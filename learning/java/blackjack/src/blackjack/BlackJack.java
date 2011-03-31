/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

import java.util.*;

/**
 *
 * @author seb
 */
public class BlackJack implements Ambiente {

    LinkedList<Accion> acciones;
    
    public BlackJack() {
        acciones = new LinkedList<Accion> ();
        acciones.add(new BJAction(BJAction.DEAL));
        acciones.add(new BJAction(BJAction.STOP));
    }

    public double getReward(Estado s, Accion a) {
        //TODO
        return 0;
    }

    public Estado nextState(Estado s, Accion ac) {
        BJState e = (BJState) s;
        BJAction a = (BJAction) ac;
        return e.jugar(a);
    }

    public Estado getInitialState() {
        return new BJState(2, true);
    }

    public LinkedList<Accion> getAcciones() {
        return acciones;
    }

    public boolean isTerminalState(Estado s) {
        return ((BJState)s).isTerminal();
    }     
}
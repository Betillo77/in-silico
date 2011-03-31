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
    LinkedList<Integer> cards[];
    int players;

    public BlackJack(int players) {
        this.players = players;
        cards = new LinkedList[players];
    }

    public double getReward(Estado s, Accion a) {
        BJState s1 = (BJState)s;

    }

    public Estado nextState(Estado s, Accion a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Estado getInitialState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LinkedList<Accion> getAcciones() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isTerminalState(Estado s) {
        return ((BJState)s).isTerminal();
    }
       
}
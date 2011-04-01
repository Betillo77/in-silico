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

    public Par<Double,Estado> getRewardAndState(Estado s, Accion ac) {
        BJState e = (BJState) s;
        BJAction a = (BJAction) ac;
        BJState siguiente = (BJState) e.jugar(a);
        double r = 0.0d;
        if(siguiente.isTerminal())
            r = siguiente.getReward();
        return new Par<Double,Estado>(r,siguiente);
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

    public static void main(String[] args) {
        Jugador j = new Jugador();
        j.amb = new BlackJack();
        j.gamma = 0.2;
        j.nabla = 0.4;
        double acum = 0;
        for(int i = 0; i < 1000000; i++) {
            if(i % 10 != 0)
            acum += ((BJState) j.jugar(false)).getReward();
            else {
                if(i == 100000)
                    acum = 0;
                acum += ((BJState) j.jugar(false)).getReward();
                System.out.println(i + " " + acum);
            }
        }
    }
}
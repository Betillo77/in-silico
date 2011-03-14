/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

import java.util.*;

/**
 *
 * @author santiago
 */
public class Jugador {
    Ambiente amb;
    TreeMap<Par<Estado,Accion>, Double> Q;
    double nabla,gamma;

    public Accion chooseAction(Estado s, LinkedList<Accion> actions) {
        double []p = new double[actions.size()];
        double T=1000;
        double sum=0; int i=0;
        for (Accion a : actions){
            Par<Estado,Accion> key = new Par<Estado, Accion>(s, a);
            p[i] = Math.exp(Q.get(key)/T);
            sum += p[i];
            i++;
        }
        double rnd = Math.random()*sum;
        sum=0;
        for (i=0; i<actions.size(); i++){
            sum += p[i];
            if (sum >= rnd){
                return actions.get(i);
            }
        }
        return null;
    }

    public void Jugar(){
        Estado s = amb.getInitialState();
        LinkedList<Accion> actions = amb.getAcciones();
        Accion a = chooseAction(s, actions);
        do {
            double r = amb.getReward(s, a);
            Estado s1 = amb.nextState(s, a);
            actions = amb.getAcciones();
            Accion a1 = chooseAction(s1, actions);
            Par<Estado,Accion> key = new Par<Estado, Accion>(s, a);
            Par<Estado,Accion> key1 = new Par<Estado, Accion>(s1, a1);
            double delta = r + gamma*Q.get(key1) - Q.get(key);
            Q.put(key, Q.get(key) + nabla*delta);
            s = s1; a = a1;
        } while (!amb.isTerminalState(s));
    }
}

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
    TreeMap<Par<Estado,Accion>, Double> Q = new TreeMap<Par<Estado,Accion>, Double>();
    double nabla,gamma, T = 1000, cooling = 0.99;

    public Accion chooseAction(Estado s, LinkedList<Accion> actions,boolean imprimir) {
        double []p = new double[actions.size()];
        if(T > 1)
            T *= cooling;
        double sum=0; int i=0;
        for (Accion a : actions){
            Par<Estado,Accion> key = new Par<Estado, Accion>(s, a);
            Double q = Q.get(key);
            if(q == null)
                q = 0.0d;
            BJState sta = (BJState) s;
            p[i] = Math.exp(q/T);
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

    public Estado jugar(boolean imprimir) {
        Estado s = amb.getInitialState();
        LinkedList<Accion> actions = amb.getAcciones();
        Accion a = chooseAction(s, actions, imprimir);
        do {
            if(imprimir) {
            BJState est = (BJState) s;
            System.out.println("Mia: " + (est.hidden[0] + est.sum[0]));
            System.out.println("Riv: " + " (" +  est.hidden[1] + ") " + est.sum[1]);
             }
            Par<Double,Estado> par = amb.getRewardAndState(s, a);
            double r = par.val1;
            Estado s1 = par.val2;
            actions = amb.getAcciones();
            Accion a1 = chooseAction(s1, actions, true);
            Par<Estado,Accion> key = new Par<Estado, Accion>(s, a);
            Par<Estado,Accion> key1 = new Par<Estado, Accion>(s1, a1);
            Double q1 = Q.get(key1);
            if(q1 == null)
                q1 = 0.0d;
            Double q2 = Q.get(key);
            if(q2 == null)
                q2 = 0.0d;
            if(imprimir) {
                BJAction a11 = (BJAction) a;
                System.out.println("Q: " + q2 + ", " + ((a11.actionType == BJAction.STOP) ? "STOP" : "DEAL"));
            }
            double delta = r + gamma*q1 - q2;
            Q.put(key, q2 + nabla*delta);
            s = s1; a = a1;
        } while (!amb.isTerminalState(s));
                if(imprimir) {
            BJState est = (BJState) s;
            System.out.println("Mia: " + (est.hidden[0] + est.sum[0]));
            System.out.println("Riv: " + " (" +  est.hidden[1] + ") " + est.sum[1]);
        }
        return s;
    }
}

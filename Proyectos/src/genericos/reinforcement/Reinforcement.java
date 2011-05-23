package genericos.reinforcement;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author santiago, sebastian
 */
public class Reinforcement
{
    public Ambiente amb;
    public Random r;
    HashMap <Par <Estado, Accion>, Double> Q = new HashMap <Par <Estado, Accion>, Double> ();
    public double nabla = 0.2, gamma = 0.1269230769230769, T = Double.MAX_VALUE, cooling = 0.999;

    static double[] p = new double[10];
    public Accion chooseAction(Estado s, LinkedList<Accion> actions)
    {
        if(T > 1)
            T *= cooling;
        double sum = 0; int i = 0;
        int mejor = 0;
        double mejorReward = Double.MIN_NORMAL;
        for (Accion a : actions)
        {
            Par <Estado,Accion> key = new Par <Estado, Accion> (s, a);
            Double q = Q.get(key);
            if(q == null)
                q = 0.0d;
            else if(q > mejorReward)
            {
            	mejorReward = q;
            	mejor = i;
            }
            p[i] = Math.exp(q / T);
            sum += p[i];
            i++;
        }
        if(T < 1)
        	return actions.get(mejor);
        double rnd = r.nextDouble() * sum;
        sum = 0;
        for (i = 0; i < actions.size(); i++)
        {
            sum += p[i];
            if (sum >= rnd)
                return actions.get(i);
        }
        return null;
    }

    public Estado jugar() 
    {
        Estado s = amb.getInitialState();
        LinkedList <Accion> actions = amb.getAcciones(s);
        Accion a = chooseAction(s, actions);
        do 
        {
            Par <Double, Estado> par = amb.getRewardAndState(s, a);
            double r = par.val1;
            Estado s1 = par.val2;
            actions = amb.getAcciones(s1);
            Accion a1 = chooseAction(s1, actions);
            Par <Estado,Accion> key = new Par <Estado, Accion> (s, a);
            Par <Estado,Accion> key1 = new Par <Estado, Accion> (s1, a1);
            Double q1 = Q.get(key1);
            if(q1 == null)
                q1 = 0.0d;
            Double q2 = Q.get(key);
            if(q2 == null)
                q2 = 0.0d;
            double delta = r + gamma * q1 - q2;
            Q.put(key, q2 + nabla * delta);
            s = s1; 
            a = a1;
        } 
        while(!amb.isTerminalState(s));
        return s;
    }
}
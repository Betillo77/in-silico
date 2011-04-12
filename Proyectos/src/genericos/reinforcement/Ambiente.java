/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genericos.reinforcement;


import java.util.LinkedList;

/**
 *
 * @author santiago
 */
public interface Ambiente 
{ 
    public Par <Double, Estado> getRewardAndState(Estado s, Accion a);
    public Estado getInitialState();
    public LinkedList<Accion> getAcciones(Estado s1);
    public boolean isTerminalState(Estado s);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

import java.util.LinkedList;

/**
 *
 * @author santiago
 */
public interface Ambiente {
    public Par<Double,Estado> getRewardAndState(Estado s, Accion a);
    public Estado getInitialState();
    public LinkedList<Accion> getAcciones();
    public boolean isTerminalState(Estado s);
}

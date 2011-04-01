/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

/**
 *
 * @author seb
 */
public class BJAction implements Accion {
    
    public static int DEAL=1;
    public static int STOP=2;
    int actionType;

    public int compareTo(Accion o) {
        BJAction a2 = (BJAction)o;
        return actionType - a2.actionType;
    }

    public BJAction(int actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        return actionType == DEAL ? "DEAL" : "STOP";
    }

}

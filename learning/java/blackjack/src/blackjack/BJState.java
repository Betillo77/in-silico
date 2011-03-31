/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

/**
 *
 * @author seb
 */
public class BJState implements Estado {
    int sum[]; //sum of visible cards of player i
    boolean playing[]; //is player i playing?
    int players;

    public BJState(int players) {
        sum = new int[players];
        playing = new boolean[players];
        this.players = players;
    }

    public int cmpBool(boolean a, boolean b) {
        if (a==b) return 0;
        if (a) return 1;
        else return 0;
    }

    public int compareTo(Estado o) {
        BJState st2 = (BJState) o;
        for (int i=0; i<players; i++) {
            int c = sum[i]-st2.sum[i];
            if (c != 0) return c;
            c = cmpBool(playing[i], st2.playing[i]);
            if (c != 0) return c;
        }
        return 0;
    }

    public boolean isTerminal() {
        boolean tmp=false;
        for (int i=0; i<players; i++) {
            tmp = tmp || playing[i];
        }
        return !tmp;
    }

}

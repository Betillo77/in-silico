/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author sebastian, santiago
 */
public class BJState implements Estado {

    final static Random random = new Random(); // random number generator
    int hidden[]; // hidden card of the player i (player 0 is the main player)
    int sum[]; // sum of visible cards of player i (player 0 is the main player)
    boolean playing[]; // is player i playing?
    int players; // number of players

    public BJState(int players, boolean init) {
        sum = new int[players];
        playing = new boolean[players];
        hidden = new int[players];
        this.players = players;
        if(init) {
            for(int i = 0; i < players; i++) {
                playing[i] = true;
                sum[i] = randomCard();
                hidden[i] = randomCard();
            }
        }
    }
    
    final int randomCard() {
        int card = random.nextInt(13) + 1;
        if(card > 10)
            card = 10;
        return card;
    }

    public int compareTo(Estado o) {
        BJState st2 = (BJState) o;
        if(players != st2.players)
            return Integer.valueOf(players).compareTo(st2.players);
        if(!Arrays.equals(sum, st2.sum))
            for(int i = 0; i < players; i++)
                if(sum[i] != st2.sum[i])
                    return Integer.valueOf(sum[i]).compareTo(st2.sum[i]);
        if(!Arrays.equals(playing, st2.playing))
            for(int i = 0; i < players; i++)
                if(playing[i] != st2.playing[i])
                    return Boolean.valueOf(playing[i]).compareTo(st2.playing[i]);
        return Integer.valueOf(hidden[0]).compareTo(st2.hidden[0]);
    }

    public boolean isTerminal() {
        boolean tmp = false;
        for (int i = 0; i < players; i++)
            tmp |= playing[i];
        return !tmp;
    }

    Estado jugar(BJAction a) {
        BJState nuevo = new BJState(players, false);
        for(int i = 0; i < players; i++) {
            nuevo.hidden[i] = hidden[i];
            nuevo.playing[i] = playing[i];
            nuevo.sum[i] = sum[i];
        }
        if(a.actionType == BJAction.STOP) {
            nuevo.playing[0] = false;
            while(!nuevo.isTerminal()) {
                for(int i = 1; i < players; i++) {
                    if(nuevo.playing[i]) {
                        if(random.nextBoolean())
                            nuevo.playing[i] = false;
                        else {
                            nuevo.sum[i] += randomCard();
                            if(nuevo.sum[i] + nuevo.hidden[i] > 21)
                                nuevo.playing[i] = false;
                        }
                    }
                }
            }
        }
        else {
            nuevo.sum[0] += randomCard();
            if(nuevo.sum[0] + nuevo.hidden[0] > 21)
                nuevo.playing[0] = false;
            if(!nuevo.playing[0]) {
                while(!nuevo.isTerminal()) {
                    for(int i = 1; i < players; i++) {
                        if(nuevo.playing[i]) {
                            if(random.nextBoolean())
                                nuevo.playing[i] = false;
                            else {
                                nuevo.sum[i] += randomCard();
                                if(nuevo.sum[i] + nuevo.hidden[i] > 21)
                                    nuevo.playing[i] = false;
                            }
                        }
                    }
                }
            }
            else {
                for(int i = 1; i < players; i++) {
                    if(nuevo.playing[i]) {
                        if(random.nextBoolean())
                            nuevo.playing[i] = false;
                        else {
                            nuevo.sum[i] += randomCard();
                            if(nuevo.sum[i] + nuevo.hidden[i] > 21)
                                nuevo.playing[i] = false;
                        }
                    }
                }
            }
        }
        return nuevo;
    }
}

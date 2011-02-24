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
public class BlackJack {
    LinkedList<Integer>[] players;
    boolean []playing;
    int nPlayers;
    int turn;
    Random r;

    public BlackJack(int nPlayers) {
        this.nPlayers = nPlayers;
        playing = new boolean[nPlayers];
        players = new LinkedList[nPlayers];
        r = new Random();
        for (int i=0; i<nPlayers; i++) {
            players[i] = new LinkedList<Integer>();
            newCard(i); newCard(i);
            playing[i]=true;
        }
    }   

    public final void newCard(int player) {
        players[player].add(r.nextInt(11)+1);
    }

    //Deals a card according to the rules and returns winner if game over,
    // returns -2 if draw(tablas), otherwise -1
    public int anotherCard() {
        newCard(turn);
        if (getSum(turn, true) >= 20)
            playing[turn] = false;
        /*turn = (turn+1)%nPlayers;
        int i=0;
        while(!playing[turn] && i<nPlayers) {
            turn++; i++;
        }
        if (i==nPlayers) {
            return winner();
        }*/
        return nextTurn();
    }

    private int nextTurn() {
        int i=0;
        do {
            turn = (turn+1)%nPlayers;
            i++;
        } while(!playing[turn] && i<=nPlayers);
        if (i>nPlayers) {
            return winner();
        }
        return -1;
    }

    private int winner() {
        int win=-2, max=0;
        for (int player=0; player < nPlayers; player++) {
            int res = getSum(player, false);
            if (res <= 21 && res>max) {
                win = player;
                max = res;
            } else if (res <= 21 && res==max) {
                win = -2;
                max = res;
            }
        }
        return win;
    }

    public int noMoreCards() {
        playing[turn] = false;
        return nextTurn();
    }

    public int getTurn() {
        return turn;
    }

    public String verCartas(int player, boolean hidden) {
        StringBuilder sb = new StringBuilder();
        int count = 0, n = players[player].size();
        for (Integer card : players[player]) {
            if (count==0 && hidden)
                sb.append("*,");
            else {
                sb.append(card);
                if ((count+1) < n) sb.append(",");
            }
            count++;
        }
        return sb.toString();
    }

    public int getSum(int player, boolean hidden) {
        int sum=0, count=0;
        for (Integer card : players[player]) {
            if (count>0 || !hidden) {
                sum += card;
            }
            count++;
        }
        return sum;
    }
    
}

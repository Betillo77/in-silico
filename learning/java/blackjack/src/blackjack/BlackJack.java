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
    LinkedList<State> [] players;
    boolean [] playing;
    int nPlayers;
    int turn;

    @SuppressWarnings("unchecked")
	public BlackJack(int nPlayers) {
        this.nPlayers = nPlayers;
        playing = new boolean[nPlayers];
        players = new LinkedList[nPlayers];
        for (int i=0; i<nPlayers; i++) {
            players[i] = new LinkedList<State>();
            players[i].add(State.drawFirst());
            players[i].add(players[i].peekLast().drawCard());
            playing[i]=true;
        }
    }   

    public final void newCard(int player) {
    	if(players[player].peekLast().getMinPoints() > 21)
    		return;
    	players[player].add(players[player].peekLast().drawCard());
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
        int lastCount = 0;
        for (State card : players[player]) {
            if (count==0 && hidden)
                sb.append("*, ");
            else {
            	int carta = card.getMinPoints() - lastCount;
                sb.append(carta == 1 ? "A" : carta);
                if ((count+1) < n) sb.append(", ");
            }
            lastCount = card.getMinPoints();
            count++;
        }
        if(hidden)
        	sb.append(" -> " + State.getBestPoints(players[player].peekLast().visible));
        else
        	sb.append(" -> " + players[player].peekLast().getBestPoints());
        return sb.toString();
    }

    public int getSum(int player, boolean hidden) {
    	return hidden ? State.getBestPoints(players[player].peekLast().visible) : players[player].peekLast().getBestPoints();
    }
    
}

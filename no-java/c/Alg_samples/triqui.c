/* 
 * File:   triqui.c
 * Author: sebastian
 *
 * This is a C program to play tic-tac-toe against a computer, modelling a
 * state like an integer where bits 17 downto 0 represent the board 
 * (17-16:Position 1) (1-0: Position 9), each group of two bits represents  
 * a position ("00": empty, "01": X and "10" : O).
 * The bit 18 represents who is going to play ("1": X, "0" : 0)
 */

#include <stdio.h>
#include <stdlib.h>

#define COMP 0x00040000
#define HUMAN 0x00000000

/*
 * This function check if someone has already won given the board and returns:
 * 0: anyone has won, 1: X won, -1: O won
 */
int whoWon(int board) {
    int filter;
    // check the row 1
    filter = board & 0x0003f000;
    if (filter == 0x00015000) return 1;
    if (filter == 0x0002A000) return -1;
    // check the row 2
    filter = board & 0x00000FC0;
    if (filter == 0x00000540) return 1;
    if (filter == 0x00000A80) return -1;
    // check the row 3
    filter = board & 0x0000003f;
    if (filter == 0x00000015) return 1;
    if (filter == 0x0000002A) return -1;
    
    // check the col 1
    filter = board & 0x00030c30;
    if (filter == 0x00010410) return 1;
    if (filter == 0x00020820) return -1;
    // check the col 2
    filter = board & 0x0000c30c;
    if (filter == 0x00004104) return 1;
    if (filter == 0x00008208) return -1;
    // check the col 3
    filter = board & 0x000030c3;
    if (filter == 0x00001041) return 1;
    if (filter == 0x00002082) return -1;
    
    // check the dia 1
    filter = board & 0x00030303;
    if (filter == 0x00010101) return 1;
    if (filter == 0x00020202) return -1;
    // check the dia 2
    filter = board & 0x00003330;
    if (filter == 0x00001110) return 1;
    if (filter == 0x00002220) return -1;
    
    // if no one has won return 0
    return 0;
}

/**
 * Given a 9 size array and the current state (tablero), returns the number
 * of possible moves and put that new states in the array
 */
int genMoves(int* posMov, int tablero) {
    int mask; //the current position to check
    int turn, i, numMoves;
    mask = 0x00030000;
    turn = tablero & 0x00040000;
    if (turn == 0x00040000) {
        tablero &= 0x0003ffff; //next turn of O
        turn = 0x00010000; //current turn is X
    } else {
        tablero |= 0x00040000; //next turn of x
        turn = 0x00020000; //current turn is o
    }
    
    numMoves = 0;
    for (i=0; i<9; i++) {
        if ((tablero & mask) == 0) {
            // Let current turn make it move
            posMov[numMoves] = (tablero | turn) | ((i+1) << 20);
            numMoves++;            
        }
        mask >>= 2;
        turn >>= 2;
    }
    
    return numMoves;
}

int min(int* array, int numMoves) {
    int m = 10000, i, index;
    for (i=0; i<numMoves; i++) {
        if (array[i] < m) {
            m = array[i];
            index = i;
        }
    }
    return index;
}

int max(int* array, int numMoves) {
    int m = -10000, i, index;
    for (i=0; i<numMoves; i++) {
        if (array[i] > m) {
            m = array[i];
            index = i;
        }
    }
    return index;
}

/**
 * Acording to MinMax algorithm, it plays for each player as if he was a
 * perfect player. Returns -1 if wins Human o 1 if COMP does
 */
int minMax(int state, int* bestNewState) {
    int ww, numMoves, turn, index, i;
    int moves[9], puntaje[9];
    ww = whoWon(state);
    if (ww != 0) return ww;    
    numMoves = genMoves(moves,state);
    if (numMoves == 0) return 0;
    for (i=0; i<numMoves; i++) {
        puntaje[i] = minMax(moves[i], &index);
    }
    turn = state & 0x00040000;
    if (turn == COMP) {
        index = max(puntaje, numMoves);
        *bestNewState = moves[index];
        return puntaje[index];
    } else {
        index = min(puntaje, numMoves);
        *bestNewState = moves[index];
        return puntaje[index];
    }
}

/**
 * Makes the user move in the given state, returns 0 if success
 * if move is not possible return 1, assume "10" for human;
 */
int makeMove(int *tablero, int move) {
    if (move < 1 || move > 9) return 1;
    int shift = (2*(move-1));
    int trans = 0x00020000 >> shift;
    int mask = 0x00030000 >> shift;
    if ((*tablero & mask) != 0) // la casilla esta ocupada
        return 1;
    *tablero |= COMP; //next turn for computer
    *tablero |= trans;
    return 0;
}


/*
 * 
 */
int main(int argc, char** argv) {
    int currState = COMP, jugada, i;
    for (i=0; i<9; i++) {
        minMax(currState, &currState);
        printf("\nEstado actual: %x", currState);
        if (whoWon(currState) == 1) {
            printf("\nGanó el computador!!!\n");
            break;
        }
        i++;
        if (i==9) break;
        while (1) {
            printf("\nTu jugada: ");
            scanf("%i", &jugada);
            if (makeMove(&currState, jugada) == 1) {
                printf("Jugada incorrecta, tu jugada: ");
            } else
                break;
        }        
        if (whoWon(currState) == -1) {
            printf("\nGanaste!!!"); // no va a pasar, pero por ética
            break;
        }
    }
    return (EXIT_SUCCESS);
}


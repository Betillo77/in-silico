
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <list>
#include "btree.h"

#define rep(i,n) for (int i=0; i<(n); i++)

//Board size and side size
#define BSIZE 9
#define SSIZE 3

using namespace std;

int cmpkeys(const char *k1, const char *k2) {
    rep(i,BSIZE) {
        if (k1[i] != k2[i]) return k1[i]-k2[i];
    }
    return 0;
}

class PState {
public:
    char key[BSIZE+1];
    char lastMove;
    char parent[BSIZE+1];
    
    bool operator<(const PState& s) {
        return cmpkeys(key,s.key) < 0;
    }
    
    bool operator>(const PState& s) {
        return cmpkeys(key,s.key) > 0;
    }
    
    bool operator==(const PState& s) {
        return cmpkeys(key,s.key) == 0;
    }
    
    void operator=(const PState& s) {
        strcpy(key,s.key);
        strcpy(parent,s.parent);
        lastMove = s.lastMove;
    }
};

//Valid moves
int delta[4][2] = {{-1,0},{1,0},{0,-1},{0,1}};
char valid[5] = "UDRL";
BTree<PState,60> *used;

void findHole(const char *k, int &row, int &col) {
    rep(i,BSIZE) {
        if (k[i] == '0') {
            row = i/SSIZE;
            col = i%SSIZE;
            return;
        }
    }
    throw "Not valid board exception";
}

void makeMove(PState &ans, const PState &src, int porg, int pdest, char move) {
    strcpy(ans.parent,src.key);
    strcpy(ans.key,src.key);
    ans.key[pdest] = ans.parent[porg];
    ans.key[porg] = ans.parent[pdest];
    ans.lastMove = move;
}

int genChildren(PState &s, PState *children) {
    int n=0,row,col;
    int nrow,ncol;
    findHole(s.key,row,col);
    rep(i,4) {
        nrow = row + delta[i][0]; ncol = col + delta[i][1];
        if (nrow>=0 && nrow<SSIZE && ncol>=0 && ncol<SSIZE) {
            makeMove(children[n++],s,row*SSIZE + col,nrow*SSIZE + ncol,valid[i]);
        }
    }
    return n;
}

void bfs() {
    PState org; dir tmp;
    strcpy(org.key,"123456780"); //original state
    org.lastMove = '\0';
    
    //BFS data structures
    list<PState> tovisit;
    tovisit.push_front(org);
    //used[string(org.key)] = org;
    used->insert(org);
    
    //To store children
    PState children[4];
    int nchildren;
    while (!tovisit.empty()) {
        PState tmp = tovisit.back(); tovisit.pop_back();
        nchildren = genChildren(tmp,children);
        rep(i,nchildren) {
            //if (used.find(string(children[i].key)) == used.end()) {
            if (!used->contains(children[i])) {
                //used[ string(children[i].key) ] = children[i];
                used->insert(children[i]);
                tovisit.push_front(children[i]);
            }
        }
    }
}

void pboard(const char *board) {
    rep(i,SSIZE) {
        rep(j,SSIZE) {
            int p = SSIZE*i + j;
            putc((board[p]=='0')?' ':board[p],stdout);
        }
        putc('\n',stdout);
    }
    putc('\n',stdout);
    //printf("%s\n",board);
}

int main() {
    char board[BSIZE+1];
    used = new BTree<PState,60>(100,"puzzle.bin",APP_TREE | MEM_TREE);
    PState tmp;
    try {
        if (used->flags & REP_TREE) bfs();
        while (scanf("%s",board) != EOF) {
            //if (used.find(string(board)) == used.end()) {
            strcpy(tmp.key, board);
            if (!used->contains(tmp)) {
                printf("Board without solution\n");
                continue;
            }
            pboard(board);
            strcpy(tmp.key, board);
            //PState ps = used[string(board)];
            PState ps = used->find(tmp);
            while (ps.lastMove != '\0') {                
                strcpy(board,ps.parent);
                printf("%c\n",ps.lastMove);
                pboard(board);
                strcpy(tmp.key, board);
                //ps = used[string(board)];
                ps = used->find(tmp);
            }
        }
        used->save();
    } catch (const char *c) {
        printf("Exception: %s\n",c);
    }
}

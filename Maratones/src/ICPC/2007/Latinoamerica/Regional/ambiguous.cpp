#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <algorithm>
#include <ctype.h>
#include <map>
#include <string>
#include <queue>
#include <algorithm>
#include <vector>

using namespace std;

int n;
char words[110][70];
char no[5010][70];


map < string , int > mapa;

int read(){
    scanf("%d",&n);
    if( n == 0 ) return 0;
    int i;
    for(i=0;i<n;i++) scanf("%s",words[i]);    
    return 1; 
}

char mark[5010][2];

struct state{
    int id;
    int ini;
    int cost;
    int pai;
    state(int id,int pai,int ini,int cost):id(id),pai(pai),ini(ini),cost(cost){}
    
    friend bool operator < ( state a, state b ){
        return a.cost > b.cost;
    }
};

void process(){
    
    mapa.clear();
    
    int i,j,k;
    
    int ID = 1;
    
    memset(mark,0,sizeof(mark));
    priority_queue < state > pq;
    
    for(i=0;i<n;i++){
        for(j=0;words[i][j];j++){
            if( j == 0 ){
                if( mapa[words[i]] )   pq.push( state( mapa[words[i]],i,1,0) );
                else pq.push( state( ID,i,1,0) );

            }
            if( !mapa[ words[i] + j ] ){
                strcpy(no[ID],words[i]+j);
                mapa[ words[i]+j ] = ID++;
            }
        }
    }
    
    while(!pq.empty()){
        state top = pq.top();
        pq.pop();
        
        if( top.id == -1 ){
            printf("%d\n",top.cost);
            return ;
        }
        if( mark[top.id][top.ini] ) continue;
        mark[top.id][top.ini] = 1;
        
        int i,j,k;
        k = top.id;
        
        for(i=0;i<n;i++){
            
            if( top.ini && top.pai == i ) continue;
            
            j = 0;
            while(no[k][j] && words[i][j]){
                if( no[k][j] != words[i][j] ) break;
                j++;
            }
            
            if( no[k][j] == 0 ){
                if( words[i][j] == 0 ){
                    pq.push( state(-1,i,0,top.cost+j) );
                }
                else{
                    if( !mark[ mapa[words[i]+j] ][0] ){
                        pq.push( state( mapa[words[i]+j] ,i,0 , top.cost + j ) );
                    }
                }
            }
            else if( words[i][j] == 0 ){
                if( !mark[ mapa[no[k]+j] ][0] ){
                        pq.push( state( mapa[no[k]+j] ,i,0 , top.cost + j ) );
                }
            }
        }
    }
    
    printf("-1\n");        
}

int main(){

    while(read())process();

    return 0;
}

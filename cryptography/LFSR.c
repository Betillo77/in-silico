#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define N 16 

typedef unsigned char uchar;

struct LFSR{
    uchar *reg;
    uchar *poly;
    int size;
}LFSR;


void init(struct LFSR *LFSR){
    uchar poly[N/4 + 1], reg[N/4 + 1];
    uchar tmp1[3];
    int i;
    
    scanf("%s", poly);
    scanf("%s", reg);
    
    for (i=0; i<(N/4); i+=2) {
        int tmp;
        
        memcpy(tmp1, poly+i, 2);
        sscanf(tmp1, "%x", &tmp);
        LFSR->poly[i/2] = (uchar)tmp;
        
        memcpy(tmp1, reg+i, 2);
        sscanf(tmp1, "%x", &tmp);
        LFSR->reg[i/2] = (uchar)tmp;
    }
    
    return;
}


int step(struct LFSR *LFSR){
    int i, c;
    uchar tmp,c2;
    
    c2 = 0;
    for(i=0; i < LFSR->size; i++){
        tmp = LFSR->reg[i] & LFSR->poly[i];
        while(tmp){
            c2 = c2^1;
            tmp &= tmp-1;
        }
    }     

    for(i=0; i < LFSR->size; i++){
        c = (LFSR->reg[i] >> 7)&1;
        LFSR->reg[i] <<= 1;
        LFSR->reg[i] |= c2;
        c2 = c;
    }  
    //printf("\n%x\n",LFSR->reg[0]);  
    return c2;
}

int main(){
    int i, t, n = N;    
    freopen("in", "r", stdin);
    scanf("%d", &t);
    
    
    LFSR.size = (n+7)/8;
    LFSR.reg = (uchar*)malloc(n*sizeof(uchar));
    LFSR.poly = (uchar*)malloc(n*sizeof(uchar));

    init(&LFSR);
    
    for(i=0; i < t; i++){
        printf("%d", step(&LFSR));
    }
    printf("\n");
    

    return 0;
}

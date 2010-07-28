/* 
 * File:   test_lib.c
 * Author: seb
 *
 * Created on 24 de julio de 2010, 11:15 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "BigInt.h"

void testSum() {
    BigInt* a = bnNewBigInt(32,0);
    BigInt* b = bnNewBigInt(32,0);
    a->d[0] = 0xcfab18e4;
    a->d[1] = 0xffa01172;
    a->size=2;
    bnAddInt(b,b,a);
    printf("a: "); printHex(b);
    bnAddInt(b,a,a);
    printf("2*a: "); printHex(b);
    bnSubInt(b,b,a);
    printf("2*a-a: "); printHex(b);
    bnSubInt(b,b,a);
    printf("a-a: "); printHex(b);
    bnSubInt(b,b,a);    
    printf("-a: "); printHex(b);
    bnShiftLBits(b,a,64);
    printf("a<<64: "); printHex(b);
    bnShiftLBits(b,a,36);
    printf("a<<36: "); printHex(b);
    bnMulInt(b,a,a);
    printf("a*a: "); printHex(b);
    bnMulInt(b,b,b);
    printf("a^4: "); printHex(b);
    bnDelBigInt(a);
    bnDelBigInt(b);
}

void fac(int n) {
    BigInt *r = bnNewBigInt(500,1);
    BigInt *f = bnNewBigInt(1,1);
    char ans[2000];
    int *i = &(f->d[0]);
    for ( (*i)=1; (*i)<=n; (*i)++ ) {
        bnMulInt(r,r,f);
    }
    bnIntToStr(ans,r);
    printf("%s\n",ans);
}

/*
 * 
 */
int main(int argc, char** argv) {
    clock_t t = clock();
    fac(256);
    t = clock()-t;
    printf("Time elapsed: %i tks\n", t);
    return (EXIT_SUCCESS);
}


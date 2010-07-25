/* 
 * File:   test_lib.c
 * Author: seb
 *
 * Created on 24 de julio de 2010, 11:15 PM
 */

#include <stdio.h>
#include <stdlib.h>
#include "BigInt.h"

void printHex(BigInt *a) {
    int i;
    if (a->sign == BN_NEG)
        printf("-");
    REPB(i,a->size) {
        printf("%x ",a->d[i]);
    }
    printf("\n");
}

void testSum() {
    BigInt* a = bnNewBigInt(32,0);
    BigInt* b = bnNewBigInt(32,0);
    a->d[0] = 0xcfab18e4;
    a->d[1] = 0xffa01172;
    a->size=2;
    printf("a: "); printHex(a);
    bnAddInt(b,a,a);
    printf("2*a: "); printHex(b);
    bnSubInt(b,b,a);
    printf("2*a-a: "); printHex(b);
    bnSubInt(b,b,a);
    printf("a-a: "); printHex(b);
    bnSubInt(b,b,a);
    printf("-a: "); printHex(b);
    bnDelBigInt(a);
    bnDelBigInt(b);
}

/*
 * 
 */
int main(int argc, char** argv) {
    testSum();
    return (EXIT_SUCCESS);
}


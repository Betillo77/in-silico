/*
 * File:   BigInt.c
 * Author: seb
 *
 * Created on 25 de mayo de 2010, 03:40 PM
 */

#include "BigInt.h"

/**
 * Computes res = a+b for BigInts.
 */
void bnAddInt(BigInt* res, BigInt* a, BigInt* b) {
    word i,n,carry;
    dword r;
    n = MAX(a->size,b->size);
    REP(i,n) {
        r = a->d[i];
        r += b->d[i];
        r += carry;
        res->d[i] = (word)r;
        carry = (word)r>>WBITS;
    }
}
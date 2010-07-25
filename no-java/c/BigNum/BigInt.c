/*
 * File:   BigInt.c
 * Author: seb
 *
 * Created on 25 de mayo de 2010, 03:40 PM
 */

#include "BigInt.h"

//Removes leading ceros
void bnRemCeros(BigInt *a) {
    while (a->size > 0 && a->d[ a->size-1 ] == 0)
        a->size--;
}

//Suma a y b, independiente de que signos tenga
void bnUAddInt(BigInt *res, BigInt *a, BigInt *b) {
    word i,n,carry;
    dword r;
    n = MAX(a->size,b->size);
    carry=0;
    REP(i,n) {
        r = ((dword)a->d[i]) + b->d[i];
        r += carry;
        res->d[i] = (word)r;
        carry = (word)(r>>WBITS);
    }
    if (carry != 0) {
        res->d[n++] = carry;
    }
    res->size=n;
}

//resta a y b, independiente del signo; Se debe cumplir que a >= b
void bnUSubInt(BigInt *res, BigInt *a, BigInt *b) {
    word borrow,i,n;
    dword s,m,base;
    n = a->size;
    borrow=0;
    base = ((dword)1 << WBITS);
    REP(i,n) {
        s = a->d[i];
        m = ((dword)b->d[i]) + borrow;
        if (s < m) {
            s += base;
            borrow=1;
        }
        res->d[i] = (word)(s-m);
    }
    res->size=n;
    bnRemCeros(res);
}

/**
 * Returns positive if a is greater than b, 0 if a is equal to b
 * or negative if a is less than b
 */
int bnCompareInt(BigInt* a, BigInt* b) {
    if (a->size != b->size)
        return a->size - b->size;
    int i, r;
    REPB(i,a->size) {
        r = a->d[i] - b->d[i];
        if (r!=0) return r;
    }
    return 0;
}

void bnNegInt(BigInt* a) {
    if (a->sign = BN_NEG)
        a->sign=BN_POS;
    else
        a->sign=BN_NEG;
}

/**
 * Computes res = a+b for BigInts.
 */
void bnAddInt(BigInt* res, BigInt* a, BigInt* b) {
    if (a->sign == b->sign) {
        bnUAddInt(res,a,b);
        res->sign = a->sign;
    } else {
        if (bnCompareInt(a,b) > 0) {
            bnUSubInt(res,a,b);
            res->sign = a->sign;
        } else {
            bnUSubInt(res,b,a);
            res->sign = b->sign;
        }
    }
}

/**
 * Computes res = a-b for BigInts
 */
void bnSubInt(BigInt* res, BigInt* a, BigInt* b) {
    bnNegInt(b);
    bnAddInt(res,a,b);
    bnNegInt(b);
}

BigInt* bnNewBigInt(word maxSize, word initVal) {
    BigInt *a = (BigInt*) malloc(sizeof(BigInt));
    a->size=1;
    a->maxSize = maxSize;
    a->sign=BN_POS;
    a->d = malloc(maxSize*sizeof(word));
    a->d[0] = initVal;
    return a;
}

void bnDelBigInt(BigInt* a) {
    free(a->d);
    free(a);
}
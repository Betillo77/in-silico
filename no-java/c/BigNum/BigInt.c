/*
 * File:   BigInt.c
 * Author: seb
 *
 * Created on 25 de mayo de 2010, 03:40 PM
 */

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

//Removes leading ceros
void bnRemCeros(BigInt *a) {
    while (a->size > 0 && a->d[ a->size-1 ] == 0)
        a->size--;
}

//Suma a y b, independiente de que signos tenga; Se debe cumplir a>=b
void bnUAddInt(BigInt *res, BigInt *a, BigInt *b) {
    word i,n,carry;
    dword r;
    n = a->size;
    for (i=b->size; i<n; i++) b->d[i]=0;
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
    for (i=b->size; i<n; i++) b->d[i]=0;
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
    if (a->sign == BN_NEG)
        a->sign=BN_POS;
    else
        a->sign=BN_NEG;
}

void bnShiftLBits(BigInt* res, BigInt* a, word bits) {
    word rdig[res->maxSize];
    word carry, shdig, shbits, i;
    dword lshift;
    carry = 0;
    shdig = bits/WBITS; shbits = bits%WBITS;
    REP(i,shdig) {
        rdig[i]=0;
    }
    REP(i,a->size) {
        lshift = a->d[i];
        lshift <<= shbits;
        lshift |= carry;
        rdig[i+shdig] = (word)lshift;
        carry = (word)(lshift >> WBITS);
    }
    rdig[i+shdig] = carry;
    res->size = a->size + shdig + 1;
    res->sign = a->sign;
    copyw(res->d, rdig, res->size);
    bnRemCeros(res);
}

/**
 * Computes res = sum1+sum2 for BigInts.
 */
void bnAddInt(BigInt* res, BigInt* sum1, BigInt* sum2) {
    BigInt *a, *b;
    if (bnCompareInt(sum1, sum2) > 0) {
        a=sum1; b=sum2;
    } else {
        a=sum2; b=sum1;
    }
    if (a->sign == b->sign) {
        bnUAddInt(res,a,b);
        res->sign = a->sign;
    } else {
        bnUSubInt(res,a,b);
        res->sign = a->sign;        
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
    a->d = 0;
    a->d = (word*) malloc(maxSize * sizeof(word));
    a->d[0] = initVal;
    return a;
}

void bnDelBigInt(BigInt* a) {
    word *d = a->d;
    free(a);
    free(d);    
}

void bnMulInt(BigInt* res, BigInt* a, BigInt* b) {
    BigInt *tmp=0, *sum=0;
    word i, j, carry;
    dword m;
    tmp = bnNewBigInt(res->maxSize,0);
    sum = bnNewBigInt(res->maxSize,0);
    REP(i, b->size) {
        carry = 0;
        REP(j, a->size) {
            m = ((dword)b->d[i])*a->d[j];
            m += carry;
            tmp->d[j] = (word)m;
            carry = (word)(m >> WBITS);
        }
        tmp->d[j] = carry;
        tmp->size = a->size+1;
        bnRemCeros(tmp);
        bnShiftLBits(tmp,tmp,WBITS*i);
        bnAddInt(sum,sum,tmp);        
    }
    res->size = sum->size;
    res->sign = (a->sign == b->sign) ? BN_POS : BN_NEG;
    word *swap=res->d; res->d=sum->d; sum->d=swap;
    bnDelBigInt(tmp);
    bnDelBigInt(sum);
}

//ans = a / b ; res = a % b #División sin signo
void bnDivIntWord(BigInt* ans, BigInt* a, word b, word *res) {
    dword num;
    word carry; int i;
    carry = 0;
    REPB(i, a->size) {
        num = a->d[i] | ((dword)carry)<<WBITS;
        ans->d[i] = (word)(num / b);
        carry = (word)(num % b);
    }
    ans->size = a->size; bnRemCeros(a);
    ans->sign = a->sign;
    *res = carry;
}

void bnIntToStr(char* ans, BigInt* a) {
    word i=0, res;
    while (a->size > 1 || a->d[0] > 10) {
        bnDivIntWord(a,a,10,&res);
        ans[i++] = (char)(res + 0x30);
    }
    ans[i] = '\0';
    invStr(ans, i);
}
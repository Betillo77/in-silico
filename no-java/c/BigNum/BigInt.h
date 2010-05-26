/* 
 * File:   BigInt.h
 * Author: seb
 *
 * Created on 25 de mayo de 2010, 03:40 PM
 */

#include "Macros.h"

#define WBITS 32
#define WMASK ( ((dword)1 << WBITS) - 1 )

typedef unsigned int word;
typedef unsigned long dword;

/**
 * Structure that represents big integers, d[0] is the least significant
 * digit while d[size-1] is the most significant digit
 */
typedef struct BigInt {
    word maxSize; //Maximum number of digits that d can hold
    word size; //number of digits
    word sign; //zero->negative, other->positive
    word *d; //value of each digits
};

void bnAddInt(BigInt *res, BigInt *a, BigInt *b);
void bnSubInt(BigInt *res, BigInt *a, BigInt *b);
void bnMulInt(BigInt *res, BigInt *a, BigInt *b);
void bnDivInt(BigInt *res, BigInt *a, BigInt *b);

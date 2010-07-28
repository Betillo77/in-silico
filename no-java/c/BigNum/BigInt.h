/* 
 * File:   BigInt.h
 * Author: seb
 *
 * Created on 25 de mayo de 2010, 03:40 PM
 */

#include <stdlib.h>
#include <stdio.h>
#include "bnUtils.h"

#define WBITS 32
#define WMASK ( ((dword)1 << WBITS) - 1
#define BN_NEG 0
#define BN_POS 1

/**
 * Structure that represents big integers, d[0] is the least significant
 * digit while d[size-1] is the most significant digit
 */
typedef struct {
    word maxSize; //Maximum number of digits that d can hold
    word size; //number of digits
    char sign; //zero->negative, other->positive
    word *d; //value of each digits
} BigInt;

void printHex(BigInt *a);
BigInt* bnNewBigInt(word size, word initVal);
void bnDelBigInt(BigInt *a);
int bnCompareInt(BigInt *a, BigInt *b);
void bnNegInt(BigInt *a);
void bnShiftLBits(BigInt *res, BigInt *a, word bits);
void bnAddInt(BigInt *res, BigInt *a, BigInt *b);
void bnSubInt(BigInt *res, BigInt *a, BigInt *b);
void bnMulInt(BigInt *res, BigInt *a, BigInt *b);
void bnDivIntWord(BigInt *ans, BigInt *a, word b, word *res);
//void bnDivInt(BigInt *res, BigInt *a, BigInt *b);
void bnIntToStr(char *ans, BigInt *a);

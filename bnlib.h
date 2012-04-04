
#ifndef BNLIB_H
#define BNLIB_H

#define WBITS 32
#define BASE ( ((bn_dword)1) << WBITS )
#define WMASK ( BASE - 1 )
#define BN_NEG 0
#define BN_POS 1

typedef unsigned int bn_word;
typedef unsigned long long bn_dword;

/**
 * Structure that represents big integers, d[0] is the least significant
 * digit while d[size-1] is the most significant digit
 */
typedef struct {
    bn_word maxSize; /*Maximum number of digits that d can hold*/
    bn_word size; /*number of digits*/
    char sign; /*zero->negative, other->positive*/
    bn_word *d; /*value of each digits*/
} BigInt;

BigInt* bnNewBigInt(bn_word size, bn_word initVal);
void bnDelBigInt(BigInt *a);
int bnUCompareInt(BigInt *a, BigInt *b);
void bnNegInt(BigInt *a);
void bnShiftLBits(BigInt *res, BigInt *a, bn_word bits);
void bnShiftRBits(BigInt *res, BigInt *a, bn_word bits);
void bnAddInt(BigInt *res, BigInt *a, BigInt *b);
void bnSubInt(BigInt *res, BigInt *a, BigInt *b);
void bnMulInt(BigInt *res, BigInt *a, BigInt *b);
void bnMulIntWord(BigInt* res, BigInt *a, bn_word b);
void bnDivIntWord(BigInt *ans, BigInt *a, bn_word b, bn_word *res);
void bnDivInt(BigInt *ans, BigInt *a, BigInt *b, BigInt *res);
void bnPowInt(BigInt *ans, BigInt *a, int b);
void bnPowModInt(BigInt *ans, BigInt *a, BigInt* b, BigInt *mod);
void bnIntToStr(char *ans, BigInt *a);
void bnCopyInt(BigInt *res, BigInt *a);
void bnStrToInt(BigInt *ans, const char *input);
void bnMultIntK(BigInt *res, BigInt *a, BigInt *b);

#endif

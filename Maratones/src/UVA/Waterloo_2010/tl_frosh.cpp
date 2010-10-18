
#include <cstdlib>
#include <cstring>
#include <cstdio>
#include <cmath>

#define REP(i,N) for((i)=0; (i)<(N); (i)++)
#define REPB(i,N) for((i)=(N)-1; (i)>=0; (i)--)
#define min(X,Y) ( ((X)<(Y)) ? (X) : (Y) )
#define max(X,Y) ( ((X)>(Y)) ? (X) : (Y) )
#define PREC 1E-6
#define WBITS 32
#define WMASK ( ((dword)1 << WBITS) - 1
#define BN_NEG 0
#define BN_POS 1
#define MAX(x,y) ( ((x)>(y)) ? (x) : (y) )

typedef unsigned int word;
typedef unsigned long dword;

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

//Copia size words de a a res
void copyw(word* res, word* a, word size) {
    int i;
    REP(i,size) {
        res[i] = a[i];
    }
}

//Invierte la cadena dada de longitud n
void invStr(char* str, int n) {
    word i=0;
    word j=n-1;
    char tmp;
    while (i < j) {
        tmp = str[i];
        str[i] = str[j];
        str[j] = tmp;
        i++; j--;
    }
}

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
    while (a->size > 1 && a->d[ a->size-1 ] == 0)
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
int bnCompareInt(const BigInt* a, const BigInt* b) {
    if (a->size != b->size)
        return ((int)a->size) - ((int)b->size);
    int i;
    REPB(i,a->size) {
        if (a->d[i] > b->d[i]) return 1;
        else if (a->d[i] < b->d[i]) return -1;
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
    if (res != 0) *res = carry;
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

void bnCopyInt(BigInt* res, BigInt* a) {
    copyw(res->d,a->d,a->size);
    res->size = a->size;
    res->sign = a->sign;
}

void bnDivInt(BigInt* ans, BigInt* a, BigInt* b, BigInt* res) {
    BigInt *tmp=0, *cos=0;
    int i,j,bit;
    char sign = (a->sign == b->sign) ? BN_POS : BN_NEG;

    cos = bnNewBigInt(a->size,0); tmp=bnNewBigInt(a->size, 0);
    tmp->sign=sign;  cos->sign = sign;

    if (b->size == 1) {
        bnDivIntWord(cos,a,b->d[0],&(tmp->d[0]));
        tmp->size=1;
    } else {
        if (bnCompareInt(a,b) < 0) {
            cos->d[0]=0; cos->size=1; tmp->size=a->size;
            copyw(tmp->d,a->d,a->size);
        } else {
            REPB(i,b->size-1) {
                tmp->d[i] = a->d[a->size-i-1];
            }
            tmp->size = b->size-1; cos->d[0]=0; cos->size=1;
            REPB(i,a->size - b->size + 1) {
                REPB(j,WBITS) {
                    bit = (a->d[i]>>j) & 1;
                    bnShiftLBits(tmp,tmp,1);
                    tmp->d[0] |= bit;
                    bnShiftLBits(cos,cos,1);
                    if (bnCompareInt(tmp,b) >= 0) {
                        cos->d[0] |= 1;
                        bnSubInt(tmp,tmp,b);
                    }
                }
            }
            bnRemCeros(tmp); bnRemCeros(cos);
        }
    }
    if (ans != 0) bnCopyInt(ans, cos);
    if (res != 0) bnCopyInt(res, tmp);
    bnDelBigInt(tmp); bnDelBigInt(cos);
}

void strToBN(BigInt *ans, char *cad) {
    ans->size=1;
    ans->d[0]=0;
    int n = strlen(cad);
    BigInt *ten2i = bnNewBigInt(32, 1);
    BigInt *tmp = bnNewBigInt(32, 0);
    int i;
    REPB(i,n) {
        tmp->d[0] = cad[i]-'0';
        tmp->size=1;
        bnMulInt(tmp, tmp, ten2i);
        bnAddInt(ans, ans, tmp);
        bnShiftLBits(tmp, ten2i, 1);
        bnShiftLBits(ten2i, ten2i, 3);
        bnAddInt(ten2i, ten2i, tmp);
    }
    bnDelBigInt(ten2i);
    bnDelBigInt(tmp);
}

int N;
BigInt* A[1000001];

int compareTo(const void *a, const void *b) {
    return bnCompareInt((const BigInt*)a, (const BigInt*)b);
}

long minSwap(int i, int j) {
    if (i >= j) return 0;
    int m = (i+j)/2;
    int s1 = minSwap(i,m);
    int s2 = minSwap(m+1,j);
    int a = i, b=m+1;
    int swaps=0;
    while (a<=m && b<=j) {
        while (a<=m && bnCompareInt(A[a],A[b])<0) a++;
        while (b<=j && bnCompareInt(A[b],A[a])<0) {
            swaps += m+1-a;
            b++;
        }
    }
    qsort(A+i, j-i+1, sizeof(BigInt*), compareTo);
    return swaps+s1+s2;
}

int main() {
    int i;
    char buf[1000];
    REP(i,1000001) {
        A[i] = bnNewBigInt(8,0);
    }
    while (scanf("%i",&N) != EOF) {
        REP(i,N) {
            scanf("%s", buf);
            strToBN(A[i], buf);
        }
        printf("%li\n", minSwap(0,N-1));
    }
}


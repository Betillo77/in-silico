
#include "bnUtils.h"

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



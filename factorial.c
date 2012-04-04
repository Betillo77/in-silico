
#include <stdio.h>
#include "bnlib.h"

char buf[10000];

int main() {
	int n,i;
	printf("Escriba un numero menor o igual a 1000: ");
	scanf("%d",&n);
	if (n > 1000) return 1;
	BigInt *f = bnNewBigInt(1000,1);
	for (i=2; i<=n; i++) {
		bnMulIntWord(f, f, i);
	}
	bnIntToStr(buf,f);
	printf("El factorial de %d es:\n", n);
	printf("%s\n",buf);
}

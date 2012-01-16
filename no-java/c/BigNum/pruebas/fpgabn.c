
#include <stdio.h>
#include <string.h>
#include "bnlib.c"

char *tokbuf;

char *mytok(char *buf, char delim) {
	if (buf != 0) tokbuf = buf;
	if (tokbuf[0] == '\0') return 0;
	int i=0;
	while (tokbuf[i]!='\0' && tokbuf[i]!=delim) i++;
	char *tmp = tokbuf;
	if (tokbuf[i] == delim) tokbuf += i+1;
	else tokbuf += i;
	tmp[i] = '\0';
	return tmp;
}

void procesar(char *result, char *x, int len) {
	char buf[2000];
	memcpy(buf, x, len);
	buf[len]='\0';
	char *a = mytok(buf,' ');
	char *b = mytok(0,' ');
	char *n = mytok(0,' ');
	BigInt *aa=bnNewBigInt(100,0);
    BigInt *bb=bnNewBigInt(100,0);
    BigInt *cc=bnNewBigInt(100,0);
    BigInt *ans=bnNewBigInt(100,0);
    
    bnStrToInt(aa, a);
    bnStrToInt(bb, b);
    bnStrToInt(cc, n);
    bnPowModInt(ans, aa, bb, cc);
    bnIntToStr(result,ans);
    
    bnDelBigInt(aa);
    bnDelBigInt(bb);
    bnDelBigInt(cc);
    bnDelBigInt(ans);
}

int main() {
	char buf[1000];
	char ans[1000];
	fgets(buf, 1000, stdin);
	procesar(ans, buf, strlen(buf)-1);
	printf("%s\n",ans);
}


#include "bnlib.c"
#include <stdio.h>
#include <time.h>

char a[1000];
char b[1000];
char c[1000];
char op;

void testbn() {
    int t;
    BigInt *aa=bnNewBigInt(100,0);
    BigInt *bb=bnNewBigInt(100,0);
    BigInt *cc=bnNewBigInt(100,0);
    BigInt *ans=bnNewBigInt(200,0);
    BigInt *ans1=bnNewBigInt(200,0);
    while(scanf("%c",&op)!=EOF)
    {
        switch(op)
        {
            case '+':
                scanf("%s %s",a,b);
                //printf("ok\n");
                bnStrToInt(aa, a);
                bnStrToInt(bb, b);
                //printf("%d %d \n",aa->d[0],bb->d[0]);
                bnAddInt(ans, aa, bb);
                bnIntToStr(a,ans);
                printf("%s\n",a);
                break;
            case '-':
                scanf("%s %s",a,b);
                bnStrToInt(aa, a);
                bnStrToInt(bb, b);
                bnSubInt(ans, aa, bb);
                bnIntToStr(a,ans);
                printf("%s\n",a);
                break;
            case '*':
                scanf("%s %s",a,b);
                bnStrToInt(aa, a);
                bnStrToInt(bb, b);
                time_t seconds,seconds2;
                double elapsed;
  				seconds = time (0);	
                bnMultIntK(ans, aa, bb);
                seconds2 = time (0);
                elapsed=difftime(seconds, seconds2);	
                //bnMulInt(ans, aa, bb);
                bnIntToStr(a,ans);
                printf("%s\n",a);
                //printf("%lf\n",elapsed);
                break;
            case '/':
                scanf("%s %s",a,b);
                bnStrToInt(aa, a);
                bnStrToInt(bb, b);
                bnDivInt(ans, aa, bb,ans1);
                bnIntToStr(a,ans);
                //bnIntToStr(b,ans1);
                printf("%s\n",a);
                //printf("%s\n",a);                
                break;
            case '%':
            	scanf("%s %s",a,b);
                bnStrToInt(aa, a);
                bnStrToInt(bb, b);
                bnDivInt(ans, aa, bb,ans1);
                bnIntToStr(a,ans1);
                printf("%s\n",a);
                break;
            case 'l':
                scanf("%s %d",a,&t);
                bnStrToInt(aa, a);
                bnShiftRBits(ans, aa, t);
                bnIntToStr(a,ans);
                printf("%s\n",a);
                break;  
            case '^':
            	scanf("%s %s %s",a,b,c);
            	bnStrToInt(aa, a);
                bnStrToInt(bb, b);
                bnStrToInt(cc, c);
                bnPowModInt(ans, aa, bb, cc);
                bnIntToStr(a,ans);
                printf("%s\n",a);
                break;
        }   
    }
    bnDelBigInt(aa);
    bnDelBigInt(bb);
    bnDelBigInt(ans);
    bnDelBigInt(ans1);
}

void printBN(BigInt *x) {
    char tmp[x->size*10];
    bnIntToStr(tmp,x);
    printf("%s\n",tmp);
}

//Return 1 (true) if a_in and b_in are coprime, 0 (false) otherwise
char extEuclidAlg(BigInt *a_in, BigInt *b_in, BigInt *x, BigInt *y) {
    x->size=1; x->d[0]=0;
    y->size=1; y->d[0]=1;
    BigInt *lastx = bnNewBigInt(x->maxSize,1);
    BigInt *lasty = bnNewBigInt(y->maxSize,0);
    BigInt *tmp1 = bnNewBigInt(a_in->maxSize,0);
    BigInt *quotient = bnNewBigInt(a_in->maxSize,0);
    BigInt *a = bnNewBigInt(a_in->maxSize,0); bnCopyInt(a,a_in);
    BigInt *b = bnNewBigInt(b_in->maxSize,0); bnCopyInt(b,b_in);
    while (b->size > 1 || b->d[0] != 0) {
        bnDivInt(quotient,a,b,tmp1); //quotient = a div b
        bnCopyInt(a,b); 
        bnCopyInt(b,tmp1); //(a, b) = (b, a mod b)
        
        bnMulInt(tmp1,quotient,x);
        bnSubInt(tmp1, lastx, tmp1); //tmp1 = lastx - quotient*x
        bnCopyInt(lastx, x); 
        bnCopyInt(x, tmp1); //(x,lastx)=(tmp1,x)
        
        bnMulInt(tmp1, quotient, y); 
        bnSubInt(tmp1, lasty, tmp1); //tmp1 = lasty - quotient*y
        bnCopyInt(lasty, y);
        bnCopyInt(y, tmp1); //(y,lasty)=(tmp1,y)
    }
    char ans = (a->size==1 && a->d[0]=1);
    bnCopyInt(x,lastx); bnCopyInt(y, lasty);
    bnDelBigInt(a); bnDelBigInt(b); 
    bnDelBigInt(tmp1); bnDelBigInt(quotient);
    bnDelBigInt(lastx); bnDelBigInt(lasty);
    return ans;
}

void testModInv() {
    BigInt *aa = bnNewBigInt(200,0);
    BigInt *bb = bnNewBigInt(200,0);
    BigInt *x = bnNewBigInt(200,0);
    BigInt *y = bnNewBigInt(200,0);
    scanf("%s %s", a, b);
    bnStrToInt(aa,a);
    bnStrToInt(bb,b);
    extEuclidAlg(aa,bb,x,y);
    bnIntToStr(c,x);
    printf("%s\n",c);
    bnDelBigInt(aa); bnDelBigInt(bb);
    bnDelBigInt(x); bnDelBigInt(y);
}

int main() {
	testModInv();
    return 0;
}

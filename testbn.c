
#include <bnlib.h>
#include <stdio.h>
#include <time.h>

char a[1000];
char b[1000];
char c[1000];
char op;

void printBN(BigInt *x) {
    char tmp[x->size*10];
    bnIntToStr(tmp,x);
    printf("%s\n",tmp);
}

int main()
{
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
    return 0;
}

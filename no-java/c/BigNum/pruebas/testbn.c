
#include "bnlib.c"
#include <stdio.h>


char a[1000];
char b[1000];
char c[1000];
char op;

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
                bnMulInt(ans, aa, bb);
                bnIntToStr(a,ans);
                printf("%s\n",a);
                break;
            case '/':
                scanf("%s %s",a,b);
                bnStrToInt(aa, a);
                bnStrToInt(bb, b);
                bnDivIntF(ans, aa, bb,ans1);
                bnIntToStr(a,ans);
                //bnIntToStr(b,ans1);
                printf("%s\n",a);
                //printf("%s\n",a);                
                break;
            case '%':
            	scanf("%s %s",a,b);
                bnStrToInt(aa, a);
                bnStrToInt(bb, b);
                bnDivIntF(ans, aa, bb,ans1);
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
    printf("Llamados while: %d\n", nLlamadosWhile);
    bnDelBigInt(aa);
    bnDelBigInt(bb);
    bnDelBigInt(ans);
    bnDelBigInt(ans1);


    return 0;
}

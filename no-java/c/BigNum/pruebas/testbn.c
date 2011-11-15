
#include "bnlib.c"



char a[1000];
char b[1000];
char op;

int main()
{
    BigInt *aa=bnNewBigInt(100,0);
    BigInt *bb=bnNewBigInt(100,0);
    BigInt *ans=bnNewBigInt(100,0);
    BigInt *ans1=bnNewBigInt(100,0);
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
                bnDivInt(ans, aa, bb,ans1);
                bnIntToStr(a,ans);
                bnIntToStr(b,ans1);
                /*printf("%s mod:%s\n",a,b);*/
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


#include "bnlib.c"

//Private method to multiply with karatsuba algorithm, this method supposes that
//both a and b have a maxSize greater or equal than max(a.size,b.size) and that putting
//values in res is NOT going to alter a or b. Don't call with a=res or b=res.
void bnPrivKaratsuba(BigInt *res, BigInt *a, BigInt *b) {
    int n = MAX(a->size,b->size);
    if (n==1) {
        res->size=2;
        bn_dword x = ((bn_dword)a->d[0])*b->d[0];
        res->d[0] = (bn_word)(x & WMASK);
        res->d[1] = (bn_word)(x >> WBITS);
        return;
    }
    int mid = (n>>1) + (n&1); //(n/2) + (n%2)
    BigInt x0,x1,y0,y1;
    x0.d = a->d; x0.size = mid; x0.sign = BN_POS;
    x1.d = &(a->d[mid]); x1.size = n-mid; x1.sign = BN_POS;
    y0.d = b->d; y0.size = mid; y0.sign = BN_POS;
    y1.d = &(b->d[mid]); y1.size = n-mid; y1.sign = BN_POS;
    BigInt *p0 = bnNewBigInt(n,0);
    BigInt *p1 = bnNewBigInt(2*n,0);
    BigInt *p2 = bnNewBigInt(n,0);
    bnAddInt(p0,&x0,&x1);
    bnAddInt(p2,&y0,&y1);
    bnPrivKaratsuba(p1,p0,p2);  //p1 = (x0+x1)(y0+y1)
    bnPrivKaratsuba(p0,&x0,&y0); //p0 = x0*y0;
    bnPrivKaratsuba(p2,&x1,&y1); //p2 = x1*y1;
    bnAddInt(res,p0,p2);
    bnSubInt(p1,p1,res); //p1 = p1 - p0 - p2
    bnShiftLBits(p1,p1,mid*WBITS); //p1 = p1*BASE^(n/2)
    bnShiftLBits(res,p2,n*WBITS); //ans += p2*BASE^n
    bnAddInt(res,res,p1); //ans += p1
    bnAddInt(res,res,p0); //ans += p0
    bnDelBigInt(p0);bnDelBigInt(p1);bnDelBigInt(p2);
}

void bnMultIntK(BigInt *res, BigInt *a, BigInt *b) {
    BigInt *ans; bn_word i;
    if (a==res || b==res) ans = bnNewBigInt(res->maxSize,0);
    else ans = res;
    
    if (a->size == b->size) {
        bnPrivKaratsuba(ans,a,b);
    } else if (a->size > b->size) {
        BigInt *tmp = bnNewBigInt(a->size,0);
        REP(i,a->size) tmp->d[i]=0;
        bnCopyInt(tmp,b);
        bnPrivKaratsuba(ans,a,tmp);
        bnDelBigInt(tmp);
    } else {
        BigInt *tmp = bnNewBigInt(b->size,0);
        bnCopyInt(tmp,a);
        bnPrivKaratsuba(ans,tmp,b);
        bnDelBigInt(tmp);
    }    
    
    if (a==res || b==res) {
        bnCopyInt(res,ans);
        bnDelBigInt(ans);
    }
}

void printBN(BigInt *x) {
    char tmp[x->size*10];
    bnIntToStr(tmp,x);
    printf("%s\n",tmp);
}

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
                bnMultIntK(ans, aa, bb);
                bnIntToStr(a,ans);
                printf("%s\n",a);
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

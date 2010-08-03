#include <iostream>
#include <cstdio>
using namespace std;
#define MOD 1000000007

long long re(long long a,long long b,long long m)
{
    if (b==0) return 1;
    if (b==1) return a%m;
    if (b&1) return (a*(re(a,b-1,m)))%m;
    long long t=re(a,b/2,m);
    return (t*t)%m;
}
long long c(int n,int m)
{
    long long i,ret=1,tmp=1;
    for (i=m;i>m-n;i--) ret=(ret*i)%MOD;
    for (i=1;i<=n;i++) tmp=(tmp*i)%MOD;
    return (ret*re(tmp,MOD-2,MOD))%MOD;
}
long long cc[51][51];
long long dp[250][51]={};
int main()
{
    int t,ca,i,j,n,m,k,x,y;
    for (i=1;i<51;i++)
        for (j=0;j<=i;j++)
            cc[j][i]=c(j,i);
    for (i=1;i<=240;i++)
        for (j=1;j<51;j++)
        {
            if (j==1) dp[i][j]=1;
            else dp[i][j]=dp[i-1][j]*j+dp[i-1][j-1]*j;
            dp[i][j]%=MOD;
        }


    scanf("%d",&t);
    for (ca=1;ca<=t;ca++)
    {
        scanf("%d%d%d",&n,&m,&k);
        n++,m++;
        long long ans=0;
        x=(n+1)/2*((m+1)/2)+n/2*(m/2);
        y=n*m-x;
        for (i=1;i<k;i++)
            for (j=1;i+j<=k;j++)
                ans=(ans+ (cc[i][k]*cc[j][k-i])%MOD*((dp[x][i]*dp[y][j])%MOD) )%MOD;
        if(n==1&&m==1) ans=k;
        printf("Case %d: %lld\n",ca,ans);
    }
    return 0;
}

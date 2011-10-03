#include <cstdlib>
#include <cstdio>
#include <cstring>
#include <algorithm>
#include <cmath>

using namespace std;

long long totient[15];
bool debug=false;

long long multmod(long long a, long long b, long long mod) {
    long long ans=0,c=1,tmp,mod2;
    mod2 = mod;
    while (b > 0) {
        tmp = (a*(b%10)) % mod2;
        ans = (ans + tmp*c) % mod;
        c *= 10; mod2/=10; b/=10;
    }
    return ans;
}

long long mypow(long long a, int b) {
    if (b==0) return 1;
    long long t = mypow(a,b/2);
    return t*t*((b%2)==1 ? a : 1);
}

long long powmod(long long a, long long b, long long mod) {
    if (b==0) return 1;

    long long t = powmod(a,b/2,mod);
    t = multmod(t,t,mod);
    if (b%2 == 1) t = multmod(t,a,mod);
    return t%mod;
}

//totient of 10^a
void precompute() {
    for (int i=1; i<15; i++) {
        totient[i] = mypow(2,i-1)*4*mypow(5,i-1);
    }
}

void extgcd(long long *ans, long long a, long long b) {
    if (b==0) {
        ans[0]=1; ans[1]=0;
    } else {
        long long tmp[2];
        long long q = a/b, r = a%b;
        extgcd(tmp, b, r);
        ans[0]=tmp[1]; ans[1]=tmp[0]-q*tmp[1];
    }
}

long long inverse(long long a, long long mod) {
    long long tmp[2];
    extgcd(tmp, a, mod);
    return ((tmp[0]%mod)+mod)%mod;
}

int main() {
    int N,e;
    long long a,x,m,inv3;
    precompute();
    scanf("%d",&N);
    for (int i=0; i<N; i++) {
        scanf("%lld",&a);
        if (a==1) {
            printf("1\n"); continue;
        }
        e = (int)ceil(log10(a));
        m = mypow(10,e);
        inv3 = inverse(3, totient[e]); //inverse of cube with respect totient
        if (debug) printf("inv3=%lld, a^%lld=%lld\n",inv3, totient[e], powmod(a,totient[e],m));
        x = powmod(a, inv3, m);
        printf("%lld",x);
        if (i!=(N-1)) printf("\n");
    }
}

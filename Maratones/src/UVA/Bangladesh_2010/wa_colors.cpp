/* 
 * File:   main.cpp
 * Author: seb
 *
 * Created on 22 de mayo de 2010, 04:43 PM
 */

#include <cstdio>
#include <cstring>
#include <cmath>
#include <cstdlib>

#define REP(i,n) for((i)=0; (i)<(n); (i)++)
#define REPB(i,n) for((i)=(n)-1; (i)>=0; (i)--)

#define min(X,Y) ( ((X)<(Y)) ? (X) : (Y) )
#define max(X,Y) ( ((X)>(Y)) ? (X) : (Y) )

#define MAX 50
#define MOD 1000000007

int M,N,K;

long mypow(int b, int exp) {
    if (exp == 0) return 1;

    long ans = mypow(b,exp/2);
    ans = (ans*ans) % MOD;
    if (exp%2 == 1) ans = (ans*b) % MOD;
    return ans;
}

int solve() {
    if (N*M == 1) return K;

    int a2 = M*N/2;
    int a1 = M*N - a2;
    long res=0, coef=1, term;
    int i;
    for (i=1; i<K; i++) {
        coef = (K-i+1)*coef/i;
        term = (mypow(i,a1) * mypow(K-i,a2)) % MOD;
        res += ((coef % MOD)*term) % MOD;        
    }
    return (int)(res % MOD);
}

int main(int argc, char** argv) {
    int T,tc=0;
    scanf("%i",&T);
    REP(tc,T) {
        scanf("%i %i %i",&M,&N,&K);
        M++; N++;
        printf("Case %i: %i\n",tc+1, solve());
    }
}


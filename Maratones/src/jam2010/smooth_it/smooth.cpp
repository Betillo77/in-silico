
#include <cstdio>
#include <cstring>
#include <cmath>
#include <cstdlib>

#define REP(i,n) for((i)=0; (i)<(n); (i)++)
#define REPB(i,n) for((i)=(n)-1; (i)>=0; (i)--)

#define min(X,Y) ( ((X)<(Y)) ? (X) : (Y) )
#define max(X,Y) ( ((X)>(Y)) ? (X) : (Y) )

#define MAXN 100
#define MAXC 256
#define INF 1000000

int T[MAXN][MAXC];
int A[MAXN+1];
int I,D,M,N;


int minv(int *a, int n) {
    int mv = 0x3fffffff;
    for (int i=0; i<n; i++)
        mv = min(mv,a[i]);
    return mv;
}

int icost(int a, int b) {
    int d = abs(a-b);
    if (d<=M) return 0;
    if (M==0) return INF;
    int ins = d/M;
    if (d%M == 0) ins--;
    return I*ins;
}

void fill() {
    int i,j,k,cv;
    int c[3];
    REP(j,MAXC) {
        c[0]=D;
        c[1]=abs(A[0]-j);
        c[2]=I+icost(A[0],j);
        T[0][j] = minv(c,3);
    }
    for (i=1; i<N; i++) {
        REP(j,MAXC) {
            c[0]=T[i-1][j] + D;
            REP(k,MAXC) {
                cv = T[i-1][k];
                c[1]= abs(A[i]-j) + icost(k,j) + cv;
                c[2]= icost(k,A[i]) + icost(A[i],j) + I + cv;
                c[0] = minv(c,3);
            }
            T[i][j] = c[0];
        }
    }
}

int main(int argc, char** argv) {
    int TC,tc,i;
    scanf("%i",&TC);
    REP(tc,TC) {
        scanf("%i %i %i %i",&D,&I,&M,&N);
        REP(i,N) {
            scanf("%i",&A[i]);
        }
        fill();
        printf("Case #%i: %i\n",tc+1,minv(T[N-1],MAXC));
    }
}



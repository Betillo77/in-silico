
#include "bnlib.cpp"

int M,K;
int N[55];
BigInt* t[55][55];
BigInt* comb[55][55];
bool comp[55][55];
bool debug=false;

void fillcomb() {
	BigInt *tmp = bnNewBigInt(5,1);
	for (int n=0; n<55; n++) {
		for (int r=0; r<=n; r++) {
			BigInt *ans = comb[n][r];
			ans->d[0]=1; ans->size=1;
			int fac1 = MAX(r,(n-r));
			int fac2 = MIN(r, (n-r));
			for (int i=fac1+1; i<=n; i++) {
				tmp->d[0]=i;
				bnMulInt(ans, ans, tmp);
			}
			for (int i=2; i<=fac2; i++) {
				bnDivIntWord(ans, ans, i, 0);
			}
		}
	}
	bnDelBigInt(tmp);
}

/*
 * k: in which color am i
 * m: how many buttons have been put
 */
BigInt* solve(int k, int m) {
	BigInt *tmp = bnNewBigInt(20,1);
	BigInt *ans = t[k][m];

	if (comp[k][m]) return ans;
	ans->size=1; ans->d[0]=0;
	if (m==M) {
		ans->d[0]=1;
		comp[k][m] = true; return ans;
	}
	if (k==K) {
		comp[k][m] = true; return ans;
	}

	for (int i=0; i<=N[k]; i++) {
		if (i <= (M-m)) {
			BigInt *x = solve(k+1, m+i);
			bnMulInt(tmp, comb[(M-m)][i], x);
			bnAddInt(ans, ans, tmp);
			if (debug) {
				char t1[100], t2[100];
				bnIntToStr(t1, ans); bnIntToStr(t2, tmp);
				printf("ans+=%s -> ans=%s\n", t2, t1);
			}
		}
	}

	comp[k][m]=true;
	bnDelBigInt(tmp);
	return ans;
}

void clean() {
	for (int i=0; i<=K; i++) {
		for (int j=0; j<=M; j++) {
			comp[i][j]=false;
		}
	}
}

int main() {
	char buf[100];
	for (int i=0; i<55; i++) {
		for (int j=0; j<55; j++) {
			t[i][j] = bnNewBigInt(20, 0);
			comb[i][j] = bnNewBigInt(20,1);
		}
	}
	fillcomb();
	while (1) {
		scanf("%d %d", &M, &K);
		if (M==0 && K==0) break;
		for (int i=0; i<K; i++) {
			scanf("%d", &N[i]);
		}
		BigInt *x = solve(0,0);
		bnIntToStr(buf, x);
		printf("%s\n", buf);
		clean();
	}
}


#include <cstdlib>
#include <cstdio>
#include <cstring>

#define MAXKEY 20

using namespace std;

int keylen;
char key[100];
char fname[50];
char buffer[20000];
int bufsize;

//spanish
//int corr[] = {1269,141,393,558,1315,46,112,124,625,56,0,594,265,663,949,243,116,625,760,391,463,107,0,13,106,35};
//english
int corr[] = {781,128,293,411,1305,288,139,585,677,23,42,360,262,728,0,821,215,14,664,646,902,277,100,149,30,151,9};
int frecAlpha[MAXKEY][30];
int N = 'Z'-'A'+1;

long correlation(int alpha, int k) {
    long ans=0;
    for (int i=0; i<N; i++) {
		ans += ((long)corr[i])*frecAlpha[alpha][(i+k)%N];
    }
    return ans;
}

void analize() {
    memset(frecAlpha, 0, keylen*30);
    for (int i=0; i<bufsize; i++) {
    	frecAlpha[(i%keylen)][buffer[i]-'A']++;
    }
    for (int i=0; i<keylen; i++) {
    	int maxIx=-1; long maxVal=-1;
    	for (int k=0; k<N; k++) {
            long act = correlation(i,k);
            if (maxVal < act) {
                maxVal = act;
                maxIx = k;
            }
        }
    	key[i] = 'A' + maxIx;
    }
}

int main(int argc, char **argv) {
    if (argc < 3) {
        printf("Usage:\n%s <keyleng> <fname>\n", argv[0]);
        return 1;
    }
    keylen = atoi(argv[1]);
    strcpy(fname, argv[2]);
    FILE *f = fopen(fname, "r");
    bufsize = fread(buffer, 1, 100000, f);
    analize();
    printf("%s\n", key);
    return 0;
}


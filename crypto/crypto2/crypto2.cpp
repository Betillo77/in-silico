
#include <cstdlib>
#include <cstdio>
#include <cstring>
#include <algorithm>

#define MAXL 1000000

using namespace std;

unsigned char text[MAXL];
int key, N;

void encriptar() {
    char ci;
    srand(key);
    int tmp = key;
    for(int i=0;i<N;i++)
    {
        text[i]=text[i]^(tmp & 0xff);
        tmp = rand();
    }
}

int hex(char x) {
    if (x>'0' && x<='9')
        return x-'0';
    else
        return 10 + (x-'a');
}

void hexaToNum() {
    for (int i=0; i<N; i+=2) {
        int x = hex(text[i]) + (hex(text[i+1]) << 4);
        text[i/2] = x;
    }
}

int main(int argc, char** argv) {
    N = fread(text, 1,MAXL, stdin);
    N--;
    hexaToNum();
    N/=2;
    if (argc<2) {
        key = 1234;
    } else {
        key = atoi(argv[0]);
    }
    encriptar();
    //fwrite(text, 1, MAXL, stdout);
    for (int i=0; i<N; i++) {
        printf("%x", 0xf & text[i]);
        printf("%x", 0xf & text[i]>>4);
    }
    printf("\n");
}

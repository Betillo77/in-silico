
#include <cstdlib>
#include <cstdio>
#include <cstring>

using namespace std;

int keylen;
char key[100];
char fname[50];
char buffer[20000];
int bufsize;

void encriptar() {
    for (int i=0; i<bufsize; i++) {
        buffer[i] = (buffer[i]+key[(i%keylen)])-2*'A';
        buffer[i] %= 26;
        buffer[i]+='A';
    }
    //buffer[bufsize-1] = '\n';
}

int main(int argc, char **argv) {
    if (argc < 3) {
        printf("Usage:\n%s <key> <fname>\n", argv[0]);
        return 1;
    }
    strcpy(key, argv[1]);
    keylen = strlen(key);
    strcpy(fname, argv[2]);
    FILE *f = fopen(fname, "r");
    bufsize = fread(buffer, 1, 100000, f);
    encriptar();
    f = fopen("out.bin", "w");
    fwrite(buffer, 1, bufsize, f);
}



#include <iostream>
#define MAX 300

using namespace std;

int tab[MAX][MAX]; // cantidad de sillas vacias de 0,0 a r,c

//retorna cantidad de sillas vacias de 0,0 a r,c
int get(int i, int j) {
    if (i<0 || j<0)
        return 0;
    return tab[i][j];
}

int contar(int i1, int j1, int i2, int j2) {
    return get(i2,j2)-get(i1-1,j2)-get(i2,j1-1)+get(i1-1,j1-1);
}

int resolver(int rows, int cols, int k) {
    int area = 0x3FFFFFFF, narea, sillas;
    for (int i1=0; i1<rows; i1++) {
        for (int j1=0; j1<cols; j1++) {
            if (contar(i1,j1,rows,cols) > k) return area;
            for (int i2=i1; i2 < rows; i2++) {
                for (int j2=j1; j2<cols; j2++) {
                    narea = (i2-i1+1) * (j2-j1+1);
                    if (narea >= area) break;
                    if (narea >= k) {
                        sillas = contar(i1,j1,i2,j2);
                        if (sillas>=k) {
                            area = narea;
                            if (area==k) return area;
                            break;
                        }
                    }
                }
            }
        }
    }
    return area;
}

void prueba(int r, int c) {
    for (int i=0; i<r; i++) {
        for (int j=0; j<c; j++)
            cout << get(i,j) << "\t";
        cout << "\n";
    }
}

int main() {
    int r,c,k;
    char linea[MAX];
    while (true) {
        cin >> r >> c >> k;
        if (r==0 && c==0 && k==0) break;        
        for (int i=0; i<r; i++) {
            int acfila = 0; //acumulado de la fila
            cin >> linea;
            for (int j=0; j<c; j++) {
                acfila += (linea[j]=='.') ? 1 : 0;
                tab[i][j] = acfila + ((i==0) ? 0 : get(i-1,j));
            }            
        }
        //prueba(r,c);
        cout << resolver(r,c,k) << "\n";
    }
}
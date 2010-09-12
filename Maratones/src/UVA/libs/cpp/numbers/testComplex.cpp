#include <iostream>
#include "complex.cpp"

#define PI  3.141592

/**
 * Recibe el vector con imágenes en el tiempo y la cantidad de elementos
 * en este vector y retorna un vector de complejos con las imágenes de
 * la transformada de fourier del mismo tamaño.
 */
Complex* fft(double ft[], int n) {
    Complex fw[n];
    for (int i=0; i<n; i++) {
        Complex sum (0,0);
        for (int j=0; j<n; j++) {
            Complex c (0,(2*PI*i*j)/n);
            sum = sum + c.expC()*ft[j];
        }
        fw[i] = sum;
    }    
}


void prueba() {
    double t[100];
    double ft[100];
    for (int i=0; i<100; i++) {
        t[i] = i/10.0;
        ft[i] = sin(t[i]);
    }
    Complex* fw = fft(ft,100);
    for (int i=0; i<100; i++) {
        cout << fw[i].abs() << " ";
        if ((i%10)==0) cout << "\n";
    }
}

int main() {
    cout << "hola\n";
    prueba();
}

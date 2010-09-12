/* 
 * File:   polynom.h
 * Author: seb
 *
 * Created on 4 de febrero de 2010, 09:58 PM
 */

#include <cstdio>
#include <complex>
#include <cmath>

#define PREC 1e-8
#define ERR 1e-7

using namespace std;

class Polynom {
public:
    int grado, max;
    double *coef;

    Polynom(int grado, int max);
    ~Polynom();

    Polynom* operator+= (Polynom *b);
    Polynom* operator-= (Polynom *b);
    Polynom* operator*= (Polynom *b);
    Polynom* operator/= (Polynom *b);
    Polynom* operator%= (Polynom *b);

    complex<double> eval(complex<double> x);
    void derivate();
    void clear();
    void print();
    void simplify();
    void div(Polynom *b, Polynom *cos, Polynom *residuo);
    void copyFrom(Polynom *p);
    void remMultRoot();
    void laguerre(complex<double>* roots);

    bool isZero();
};

void mcd(Polynom *a, Polynom *b, Polynom *res);






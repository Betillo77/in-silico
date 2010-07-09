/* 
 * File:   lineal.h
 * Author: seb
 *
 * Created on 6 de julio de 2010, 04:30 PM
 */

#include <cmath>

#define min(X,Y) ( ((X)<(Y)) ? (X) : (Y) )
#define max(X,Y) ( ((X)>(Y)) ? (X) : (Y) )

#define PI 3.141592
#define DIMS 2

void diff(double *r, double *a, double *b);

void sumv(double *r, double *a, double *b);

double norm(double *a);

double dotp(double *a, double *b);

void multv(double *res, double *v, double scalar);

double vect_ang(double *a, double *b);

double dist(double *a, double *b);

void cp_v(double *dest, double *org);




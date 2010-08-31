/* 
 * File:   features.h
 * Author: seb
 *
 * Created on 15 de julio de 2010, 03:09 PM
 */

#include <cstdlib>
#include <cstdio>
#include <cstring>
#include <cxtypes.h>
#include <highgui.h>
#include <cv.h>
#ifndef ANDROID
#include "lineal.h"
#else
#include "lineal.cpp"
#endif
#include <list>

#define CAM 0
#define WIN "Ventana1"
#define MAXL 10
#define MINL 2
#define COLS 4

typedef struct {
    char fig;
    int lados;
    double d_lados, d_ang;
    double max_ang;
} Figura;

extern bool training;
extern bool mostrar;
extern char fig; //figura del test case
extern CvCapture* cap;
extern CvMat *t_data; //Training data
extern CvMat *t_resp; //Training responses

void init_params(int cam=CAM);

void destroy_params();

void getFeatures(IplImage *predict=0, float *features=0);

void getFeaturesFN(char *filename);

void fillMatrix();




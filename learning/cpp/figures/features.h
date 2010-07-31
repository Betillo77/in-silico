/* 
 * File:   features.h
 * Author: seb
 *
 * Created on 15 de julio de 2010, 03:09 PM
 */
#include <cstring>
#include <cstdlib>
#include <cstdio>
#ifndef ANDROID
#include <cxtypes.h>
#include <highgui.h>
#include <cv.h>
#else
#include <opencv2/core/core.hpp>
#include <opencv2/core/types_c.h>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/calib3d/calib3d.hpp>
#include <opencv2/ml/ml.hpp>
#endif
#include "lineal.cpp"
#include <list>

#define CAM 1
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
extern char fig; //figura del test case
#ifndef ANDROID 
extern CvCapture* cap;
#else
extern int cap;
#endif
extern CvMat *t_data; //Training data
extern CvMat *t_resp; //Training responses

void init_params();

void destroy_params();

void getFeatures(IplImage *predict=0, float *features=0);

void getFeaturesFN(char *filename);

void fillMatrix();




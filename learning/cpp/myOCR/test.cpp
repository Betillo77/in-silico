/* 
 * File:   test.cpp
 * Author: seb
 *
 * Created on 10 de agosto de 2010, 11:58 AM
 */

#include <string.h>


#include <stdlib.h>
#include <cstring>
#include "transform.h"
#include "highgui.h"

using namespace MyOCR;

bool debug=false;

Matrix *loadImage(const char *fn) {
    IplImage *img = cvLoadImage(fn);
    int w = img->width, h = img->height, k = img->nChannels;
    Matrix *m = new Matrix(w, h, k);
    memcpy(m->getData(), img->imageData, w*h*k);
    cvReleaseImage(&img);
    return m;
}

void showMatrix(Matrix *m) {
    if (debug) return;
    int w=m->getWidth(), h=m->getHeight(), k=m->getChannels();
    IplImage *img = cvCreateImage(cvSize(w,h),IPL_DEPTH_8U, k);
    memcpy(img->imageData, m->getData(), w*h*k);
    cvShowImage("Test",img);
    cvWaitKey(0);
    cvReleaseImage(&img);
}

void testTransform(const char *fn) {
    Matrix *color = loadImage(fn);
    Matrix gray(color->getWidth(), color->getHeight(), 1);
    //Matrix out(color->getWidth(), color->getHeight(), 1);
    Transform t;
    t.toGrayScale(&gray,color);
    showMatrix(color);
    showMatrix(&gray);
    t.binarize(&gray, &gray);
    showMatrix(&gray);
}

/*
 * 
 */
int main(int argc, char** argv) {
    if (!debug) cvNamedWindow("Test");
    testTransform("text1.png");
    cvDestroyWindow("Test");
    return (EXIT_SUCCESS);
}



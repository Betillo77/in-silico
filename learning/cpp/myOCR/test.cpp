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
#include "documentLayout.h"
#include <mysql.h>
#include "config.h"
#include "test.h"
#include "chrfeatures.h"

using namespace MyOCR;

Matrix *loadImage(const char *fn) {
    IplImage *img = cvLoadImage(fn);
    int w = img->width, h = img->height, k = img->nChannels;
    Matrix *m = new Matrix(w, h, k);
    memcpy(m->getData(), img->imageData, w*h*k);
    cvReleaseImage(&img);
    return m;
}

void showMatrix(Matrix *m) {
    int w=m->getWidth(), h=m->getHeight(), k=m->getChannels();
    IplImage *img = cvCreateImage(cvSize(w,h),IPL_DEPTH_8U, k);
    memcpy(img->imageData, m->getData(), w*h*k);
    if (!debug) {
        cvShowImage("Test",img);
        cvWaitKey(0);
    }
    cvSaveImage("out.png",img);
    cvReleaseImage(&img);
}


void testTransform(const char *fn) {
    Matrix *color = loadImage(fn);
    Matrix gray(color->getWidth(), color->getHeight(), 1);
    Transform t;
    t.toGrayScale(&gray,color);
    t.binarize(&gray, &gray);
    showMatrix(&gray);
}

double testO(int i, int j, double x[][7], int tam, double u[])
{
    double sum = 0;
    for(int k = 0; k < tam; k++) {
        sum += ((x[k][i] - u[i]) * (x[k][j] - u[j]));
    }
    return sum / tam;
}

void test1() {
    double x[500][7];
    double u[7];
    double s[7][7];
    int acum = 0;
    for (int i=7021; i<=7514; i++) {
        ConComponent* c = ConComponent::loadComponent(i);
        if (c != NULL) {
            ChrMoments m(c);
            m.getHuMoments(x[acum++]);
            for(int i = 0; i < 7; i++)
                u[i] += x[acum - 1][i];
        }
        delete c;
    }
    printf("Cantidad de elementos: %i, media: {", acum);
    for(int i = 0; i < 7; i++) {
        u[i] /= acum;
        printf("%E;", u[i]);
    }
    printf("}\n");
    for(int i = 0; i < 7; i++) {
        for(int j = 0; j < 7; j++) {
            s[i][j] = testO(i, j, x, 7, u);
            printf("%E ", s[i][j]);
        }
        printf("\n");
    }
}

int mainTest(int argc, char** argv) {
    //if (!debug) cvNamedWindow("Test");
//    char *fn = "text2.jpg";
    const char *fn = "prueba.jpg";
    if (argc>1) fn=argv[1];
    //testTransform(fn);
    test1();
 //   ConComponent::loadComponent(1502)->printComponent();
    return (EXIT_SUCCESS);
}



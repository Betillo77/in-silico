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
#include "multivariate.h"

using namespace MyOCR;

ImgMatrix *loadImage(const char *fn) {
    IplImage *img = cvLoadImage(fn);
    int w = img->width, h = img->height, k = img->nChannels;
    ImgMatrix *m = new ImgMatrix(w, h, k);
    memcpy(m->getData(), img->imageData, w*h*k);
    cvReleaseImage(&img);
    return m;
}

void showMatrix(ImgMatrix *m) {
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
    ImgMatrix *color = loadImage(fn);
    ImgMatrix gray(color->getWidth(), color->getHeight(), 1);
    Transform t;
    t.toGrayScale(&gray,color);
    t.binarize(&gray, &gray);
    showMatrix(&gray);
}

void printVec(double *vec, int size) {
    if (size==0) {
        printf("{}");
        return;
    }
    printf("{%E",vec[0]);
    for (int i=1; i<size; i++) {
        printf(",%E",vec[i]);
    }
    printf("}\n");
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
    double x[2504][7];
    double u[7], nu[7];
    double s[7][7];
    int acum = 0;
    //for (int i=2505; i<=4967; i++) {
    for (int i=1; i<=2500; i++) {
        ConComponent* c = ConComponent::loadComponent(i);
        if (c != NULL) {            
            ChrMoments m(c);
            m.getHuMoments(x[acum++]);
            for(int j = 0; j < 7; j++)
                u[j] += x[acum - 1][j];
            delete c;
        }        
    }
    printf("Cantidad de elementos: %i, media: ", acum);
    for(int i = 0; i < 7; i++) {
        u[i] /= acum;
    }
    printVec(u,7);
    for(int i = 0; i < 7; i++) {
        for(int j = 0; j < 7; j++) {
            s[i][j] = testO(i, j, x, 7, u);
            printf("%E ", s[i][j]);
        }
        printf("\n");
        nu[i] = u[i]/sqrt(s[i][i]);
    }
    printVec(nu,7);
}

void printMat(CvMat * mat) {
    for (int i=0; i<mat->rows; i++) {
        double *vec = mat->data.db + i*mat->cols;
        for (int j=0; j<mat->cols; j++) {
            printf("%e\t", *vec);
            vec++;
        }
        printf("\n");
    }
}

void testMulti() {
    Multivariate mult(1);
    //printMat( mult.getData() );
    for (char c='0'; c<='9'; c++) {
        SymbolParams* s = mult.getSymbolParams(c);
        printf("\nDatos de simbolo: %c\n", c); // </editor-fold>
        printf("Media: "); printMat( s->getMean() );
        printf("Varianza: \n"); printMat( s->getCovar() );
        printf("Inv Covar: \n"); printMat( s->getInvCovar() );
    }
}

int mainTest(int argc, char** argv) {
    //if (!debug) cvNamedWindow("Test");
//    char *fn = "text2.jpg";
    const char *fn = "prueba.jpg";
    if (argc>1) fn=argv[1];
    //testTransform(fn);
    //test1();
    testMulti();
 //   ConComponent::loadComponent(1502)->printComponent();
    return (EXIT_SUCCESS);
}



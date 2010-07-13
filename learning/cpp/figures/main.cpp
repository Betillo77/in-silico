/*
 * File:   main.cpp
 * Author: seb
 *
 * Created on 25 de junio de 2010, 02:59 PM
 */

#include <cstdlib>
#include <cstdio>
#include <cxtypes.h>
#include <highgui.h>
#include <cv.h>
#include "lineal.h"
#include <ml.h>

#define CAM 1
#define WIN "Ventana1"
#define MAXL 10
#define MINL 2
#define COLS 4


CvCapture* cap;
CvMemStorage *seq, *poly;
char fig; //figura del test case
char *fname;
bool mostrar=false;

CvDTree* ptree=0;

void init_params() {
    if (mostrar) cvNamedWindow(WIN, CV_WINDOW_AUTOSIZE);
    cap = cvCreateCameraCapture(CAM);
    seq = cvCreateMemStorage();
    poly = cvCreateMemStorage();
}

void destroy_params() {
    cvReleaseCapture(&cap);
    //cvDestroyWindow(WIN);
    cvReleaseMemStorage(&seq);
    cvReleaseMemStorage(&poly);
}

IplImage* captureAndFilter(IplImage *predict=0) {
    IplImage* frame = 0;
    if (predict != 0)
        frame = predict;
    else if (fig == '\0')
        frame = cvQueryFrame(cap);
    else
        frame = cvLoadImage( fname );
    if (mostrar) {
        cvShowImage(WIN, frame);
        cvWaitKey();
    }
    IplImage *img = cvCreateImage(cvSize(frame->width,frame->height),IPL_DEPTH_8U,1);
    cvCvtColor(frame,img,CV_RGB2GRAY);
    cvSmooth(img,img);
    cvAdaptiveThreshold(img, img, 255, CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY,15);
    cvDilate(img,img);
    cvErode(img,img);
    if (mostrar) {
        cvShowImage(WIN, img);
        cvWaitKey();
    }
    return img;
}

double mean(double *data, int n) {
    double u=0;
    for (int i=0; i<n; i++) {
        u += data[i];
    }
    return u/n;
}

double desv_est(double * data, double media, int n) {
    double u=0, tmp;
    for (int i=0; i<n; i++) {
        tmp = (data[i]-media);
        u += tmp*tmp;
    }
    return sqrt(u/n);
}

void printFeatures(CvSeq* poly, float *ans=0) {
    int nlados = poly->total;
        
    double lados[MAXL];
    double ang[MAXL];
    double points[MAXL][DIMS];
    double vects[MAXL][DIMS];
    double maxang=0;
    for (int j=0; j<nlados; j++) {
        CvPoint* p = CV_GET_SEQ_ELEM( CvPoint, poly, j );
        points[j][0] = p->x;
        points[j][1] = p->y;
    }
    for (int j=0; j<nlados; j++) {
        diff(vects[j], points[j], points[(j+1) % nlados]);
        lados[j] = norm(vects[j]);
    }
    for (int j=0; j<nlados; j++) {
        ang[j] = 180-vect_ang(vects[j],vects[(j+1)%nlados]);
        maxang = max(maxang,ang[j]);
    }
    double m_lados,m_ang,d_lados,d_ang;
    m_lados=mean(lados,nlados); m_ang=mean(ang,nlados);
    d_lados=desv_est(lados,m_lados,nlados); d_ang=desv_est(ang,m_ang,nlados);
    
    if ( ans!=0 ) {
        ans[0]=nlados;
        ans[1]=d_lados/m_lados;
        ans[2]=d_ang/m_ang;
        ans[3]=maxang;
    } else {
        printf("%c %i %lf %lf %lf\n",fig,nlados,d_lados/m_lados,d_ang/m_ang,maxang);
    }
}

void getFeatures(IplImage *predict=0, float *features=0) {
    IplImage *img = captureAndFilter(predict);
    cvClearMemStorage(seq);
    CvSeq* cont = 0, *p2=0;
    int Nc = cvFindContours(img,seq,&cont,sizeof(CvContour),CV_RETR_LIST);
    //p2 = cvApproxPoly(cont,sizeof(CvContour),poly,CV_POLY_APPROX_DP,20);
    if ( cont ) {
        for (CvSeq *i=cont; i!=NULL; i=i->h_next) {
            cvZero(img);
            p2 = cvApproxPoly(i,sizeof(CvContour),poly,CV_POLY_APPROX_DP,10);

            int nlados = p2->total;
            if (nlados >= MAXL || nlados <= MINL) continue;

            if (mostrar) {
                cvDrawContours(img,p2,cvScalarAll(255),cvScalarAll(255),0);
                cvShowImage(WIN,img);
                cvWaitKey();
            }

            printFeatures(p2, features);
            if (features != 0) break;
        }
    }
    //printf("El número de contornos es: %i\n",Nc);
}

void train() {
    FILE* fin = fopen("train.txt","r");
    int N,i;
    fscanf(fin,"%i",&N);
    CvMat *data = cvCreateMat(N,COLS,CV_32F);
    CvMat *resp = cvCreateMat(N,1,CV_32F);
    char c[10];
    float *fdata,*fresp;
    for(i=0; i<N; i++) {
        fdata = data->data.fl + i*COLS;
        fresp = resp->data.fl + i;
        fscanf(fin,"%s",c);
        for (int j=0; j<COLS; j++) {
            fscanf(fin,"%f",&fdata[j]);
        }
        *fresp = (float)c[0];
    }

    CvMat *vartype = cvCreateMat( data->cols + 1, 1, CV_8U );
    ptree = new CvDTree;
    ptree->train(data,CV_ROW_SAMPLE,resp,0,0,vartype,0,CvDTreeParams());
    
}

void predict() {
    
    IplImage* frame;
    while (1) {
        frame = cvQueryFrame(cap);
        if (!frame) break;
        cvShowImage(WIN, frame);
        char c = cvWaitKey(200);
        if (c == 27) break;
        if (c == '\n') {
            CvMat *features = cvCreateMat(1,COLS,CV_32F);
            getFeatures(frame, features->data.fl);
            char p = (char)ptree->predict(features)->value;
            if (p=='T') printf("Triángulo\n",p);
            else if (p=='S') printf("Cuadrado\n",p);
            else if (p=='R') printf("Rectángulo\n",p);
            else printf("Figura no reconocida\n",p);
            fflush(stdout);
            cvReleaseMat(&features);
        }
    }
}

/*
 *
 */
int main(int argc, char** argv) {
    //destroy_params();
    init_params();
    argc--; argv++;
    if (argc > 0) {
        fig = argv[0][0];
        fname = argv[1];
        getFeatures();
    } else {
        train();
        predict();
    }

    destroy_params();
    return (EXIT_SUCCESS);
}

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

CvCapture* cap;
CvMemStorage *seq, *poly;

void init_params() {
    cvNamedWindow("Image", CV_WINDOW_AUTOSIZE);
    cap = cvCreateCameraCapture(1);
    seq = cvCreateMemStorage();
    poly = cvCreateMemStorage();
}

void destroy_params() {
    cvReleaseCapture(&cap);
    cvDestroyWindow("Example2");
    cvReleaseMemStorage(&seq);
    cvReleaseMemStorage(&poly);
}

IplImage* captureAndFilter(bool show=false) {
    IplImage* frame;
    frame = cvQueryFrame(cap);
    IplImage *img = cvCreateImage(cvSize(frame->width,frame->height),IPL_DEPTH_8U,1);
    cvCvtColor(frame,img,CV_RGB2GRAY);
    cvSmooth(img,img);
    cvAdaptiveThreshold(img, img, 255, CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY,15);
    cvDilate(img,img);
    cvErode(img,img);
    if (show) {
        cvShowImage("Image", img);
        cvWaitKey();
    }
    return img;
}

void getFeatures() {
    IplImage *img = captureAndFilter(true);
    cvClearMemStorage(seq);
    CvSeq* cont = 0, *p2=0;
    int Nc = cvFindContours(img,seq,&cont,sizeof(CvContour),CV_RETR_CCOMP);
    //p2 = cvApproxPoly(cont,sizeof(CvContour),poly,CV_POLY_APPROX_DP,20);
    if ( cont ) {
        for (CvSeq *i=cont; i!=NULL; i=i->h_next) {
            cvZero(img);
            p2 = cvApproxPoly(i,sizeof(CvContour),poly,CV_POLY_APPROX_DP,10);
            cvDrawContours(img,p2,cvScalarAll(255),cvScalarAll(255),0);
            cvShowImage("Image",img);
            printf("%d elements\n",p2->total);
            for (int j=0; j<p2->total; j++) {
                CvPoint* p = CV_GET_SEQ_ELEM( CvPoint, p2, j );
                printf( "(%i,%i)\n",p->x,p->y );
            }
            cvWaitKey();
        }
    }
    printf("Number of contours: %i\n",Nc);
}

/*
 * 
 */
int main(int argc, char** argv) {
    init_params();
    getFeatures();
    destroy_params();
    return (EXIT_SUCCESS);
}


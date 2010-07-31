
#include "features.h"
#include <sstream>

using namespace std;

CvMemStorage *seq, *poly;
bool mostrar=false;

bool training=false;
char fig; //figura del test case
#ifndef ANDROID 
CvCapture* cap;
#endif
list<Figura> figuras;
CvMat *t_data; //Training data
CvMat *t_resp; //Training responses


void init_params() {
#ifndef ANDROID 
    if (mostrar) cvNamedWindow(WIN, CV_WINDOW_AUTOSIZE);
    cap = cvCreateCameraCapture(CAM);
#endif
    seq = cvCreateMemStorage();
    poly = cvCreateMemStorage();
}

void destroy_params() {
#ifndef ANDROID
    cvReleaseCapture(&cap);
    //cvDestroyWindow(WIN);
#endif
    cvReleaseMemStorage(&seq);
    cvReleaseMemStorage(&poly);
}

//predict = NULL cuando no se esta entrenando
IplImage* captureAndFilter(IplImage *predict=0) {
    IplImage* frame = 0;
    if (predict != 0)
        frame = predict;
#ifndef ANDROID
    if (mostrar) {
        cvShowImage(WIN, frame);
        cvWaitKey();
    }
#endif
    IplImage *img = cvCreateImage(cvSize(frame->width,frame->height),IPL_DEPTH_8U,1);
    cvCvtColor(frame,img,CV_RGB2GRAY);
    cvSmooth(img,img);
    cvAdaptiveThreshold(img, img, 255, CV_ADAPTIVE_THRESH_MEAN_C, CV_THRESH_BINARY,15);
    cvDilate(img,img);
    cvErode(img,img);
#ifndef ANDROID  
    if (mostrar) {
        cvShowImage(WIN, img);
        cvWaitKey();
    }
#endif
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
    if ( ! training ) {
        ans[0]=nlados;
        ans[1]=d_lados/m_lados;
        ans[2]=d_ang/m_ang;
        ans[3]=maxang;
    } else {
        //printf("%c %i %lf %lf %lf\n",fig,nlados,d_lados/m_lados,d_ang/m_ang,maxang);
        Figura inst_fig;
        inst_fig.fig = fig;
        inst_fig.lados = nlados;
        inst_fig.d_lados = d_lados/m_lados;
        inst_fig.d_ang = d_ang/m_ang;
        inst_fig.max_ang = maxang;
        figuras.push_back(inst_fig);
    }
}

void fillMatrix() {
    int N = figuras.size(), i=0;
    t_data = cvCreateMat(N,COLS,CV_32F);
    t_resp = cvCreateMat(N,1,CV_32F);
    list<Figura>::iterator it;
    float *fdata, *fresp;
    for (it = figuras.begin(); it != figuras.end(); it++, i++) {
        fdata = t_data->data.fl + i*COLS;
        fresp = t_resp->data.fl + i;
        fdata[0] = it->lados;
        fdata[1] = it->d_lados;
        fdata[2] = it->d_ang;
        fdata[3] = it->max_ang;
        *fresp = (float)it->fig;
    }
}

void getFeaturesFN(char *filename) {
    IplImage *img = cvLoadImage(filename);
    getFeatures(img);
}

// predict nunca es = NULL y features=NULL cuando no esta en estrenamiento
void getFeatures(IplImage *predict, float *features) {
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
#ifndef ANDROID
            if (mostrar) {
                cvDrawContours(img,p2,cvScalarAll(255),cvScalarAll(255),0);
                cvShowImage(WIN,img);
                cvWaitKey();
            }
#endif
            printFeatures(p2, features);
            if (! training ) break;
        }
        if (training) figuras.pop_back();
    }
    //printf("El número de contornos es: %i\n",Nc);
}

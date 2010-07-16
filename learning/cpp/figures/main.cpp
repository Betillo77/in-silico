/*
 * File:   main.cpp
 * Author: seb
 *
 * Created on 25 de junio de 2010, 02:59 PM
 */


#include "io_utils.h"
#include "features.h"
#include <ml.h>

#define DIR_TR "./training/"


CvDTree* ptree=0;

void printMatrix(CvMat * mat, int type=CV_32F) {
    int N = mat->rows;
    int M = mat->cols;
    if (type == CV_32F) {
        float *fdata;
        for (int i=0; i<N; i++) {
            fdata = mat->data.fl + i*M;
            for (int j=0; j<M; j++) {
                printf("%f\t", fdata[j]);
            }
            printf("\n");
        }
    } else if (type == CV_32S) {
        int *idata;
        for (int i=0; i<N; i++) {
            idata = mat->data.i + i*M;
            for (int j=0; j<M; j++) {
                printf("%i\t", idata[j]);
            }
            printf("\n");
        }
    }
}

void trainFromTxt() {
    FILE* fin = fopen("train.txt","r");
    int N,i;
    training = true;
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

    printMatrix(data);
    printMatrix(resp);

    CvMat *vartype = cvCreateMat( data->cols + 1, 1, CV_8U );
    unsigned char *vtype = vartype->data.ptr;

    //Tipos de variables de entrada al árbol
    vtype[0]=CV_VAR_NUMERICAL;
    vtype[1]=CV_VAR_NUMERICAL;
    vtype[2]=CV_VAR_NUMERICAL;
    vtype[3]=CV_VAR_NUMERICAL;

    vtype[4]=CV_VAR_CATEGORICAL; //Tipo de la salida del árbol

    ptree = new CvDTree;
    ptree->train(data,CV_ROW_SAMPLE,resp,0,0,vartype,0,CvDTreeParams());
    
}

bool str_ends_with(const char *cad, const char *end) {
    int n = strlen(cad), m = strlen(end);
    if (n<m) return false;
    const char *end2 = cad+(n-m);
    return ( strcmp(end,end2)==0 );
}

void training_image(const char *filename) {
    char fname[256];
    if (str_ends_with(filename,".jpg")) {
        fig = filename[0];
        strcpy(fname,DIR_TR);
        strcat(fname, filename);
        getFeaturesFN( fname );
    }
}


/**
 * Creates a matrix for training but feeds it from images taken of folder
 * ./training; Only JPG images are taken into account and all shapes in those
 * images are clasified according to the first letter of the picture.
 */
void train() {
    training = true;
    listFiles(DIR_TR,training_image);
    fillMatrix();

    //printMatrix(t_data);
    //printMatrix(t_resp);

    CvMat *vartype = cvCreateMat( t_data->cols + 1, 1, CV_8U );
    unsigned char *vtype = vartype->data.ptr;

    //Tipos de variables de entrada al árbol
    vtype[0]=CV_VAR_NUMERICAL;
    vtype[1]=CV_VAR_NUMERICAL;
    vtype[2]=CV_VAR_NUMERICAL;
    vtype[3]=CV_VAR_NUMERICAL;

    vtype[4]=CV_VAR_CATEGORICAL; //Tipo de la salida del árbol

    ptree = new CvDTree;
    ptree->train(t_data,CV_ROW_SAMPLE,t_resp,0,0,vartype,0,CvDTreeParams());
}

void predict() {
    training=false;
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
    train();
    //trainFromTxt();
    predict();
    destroy_params();
    return (EXIT_SUCCESS);
}

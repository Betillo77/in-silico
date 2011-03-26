/*
 * File:   main.cpp
 * Author: seb
 *
 * Created on 25 de junio de 2010, 02:59 PM
 */

#ifndef ANDROID
#include "io_utils.h"
#include "features.h"
#include <cstring>
#include <ctime>
#endif
#include <ml.h>
#ifndef ANDROID
#define DIR_TR "./training/"
#endif

#define SH_P 1 //Show process on prediction
#define SH_T 2 //Show process on training
#define F_CHK 4 //Check the model with the validation set

CvDTree* ptree=0;
CvMat *trainMask=0;
int flags;
int cam=0; //Default camera
double probTrain=0.7;

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
#ifndef ANDROID
    if (str_ends_with(filename,".jpg")) {
#endif
        fig = filename[0];
        strcpy(fname,DIR_TR);
        strcat(fname, filename);
        getFeaturesFN( fname );
#ifndef ANDROID
    }
#endif
}


/**
 * Creates a matrix for training but feeds it from images taken of folder
 * ./training; Only JPG images are taken into account and all shapes in those
 * images are clasified according to the first letter of the picture.
 */
void train() {
    training = true;
    mostrar = (flags & SH_T) != 0;
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

    if ((flags & F_CHK) != 0) {
        trainMask = cvCreateMat(t_data->rows, 1, CV_8U);
        unsigned char *x = trainMask->data.ptr;
        CvRNG seed = cvRNG(time(0));
        for (int i=0; i<t_data->rows; i++,x++) {
            double p = cvRandReal(&seed);
            if (p < probTrain) {
                *x = 1;
            } else {
                *x=0;
            }
        }
    }

    ptree = new CvDTree;
    ptree->train(t_data,CV_ROW_SAMPLE,t_resp,0,trainMask,vartype,0,CvDTreeParams());
}

void predict() {
    training=false;
    mostrar = (flags & SH_P) != 0;
    IplImage* frame;
    while (1) {
        frame = cvQueryFrame(cap);
        if (!frame) break;
        cvShowImage(WIN, frame);
        char c = cvWaitKey(200);
        if (c == 27) break;
        else if (c == '\n') {
            CvMat *features = cvCreateMat(1,COLS,CV_32F);
            getFeatures(frame, features->data.fl);
            char p = (char)ptree->predict(features)->value;
            if (p=='T') printf("Triángulo\n",p);
            else if (p=='S') printf("Cuadrado\n",p);
            else if (p=='R') printf("Rectángulo\n",p);
            else if (p=='H') printf("Rombo\n");
            else printf("Figura no reconocida\n",p);
            fflush(stdout);
            cvReleaseMat(&features);
        } else if (c=='s') {
            cvSaveImage("snap.jpg",frame);
        }
    }
}

void readParams(int argc, char** argv) {
    char *param; int len;
    for (int i=0; i<argc; i++) {
        param = argv[i]; len = strlen(param);
        if (len>1 && param[0]=='-' && param[1]!='-') {
            for (char *c=(param+1); *c!='\0'; c++) {
                switch (*c) {
                    case 's':
                        //Show prediction
                        flags |= SH_P;
                        break;
                    case 't':
                        //Show training
                        flags |= SH_T;
                        break;
                    case 'c':
                        //Check with validation set
                        flags |= F_CHK;
                        break;
                    default:
                        printf("Opción no reconocida %c\n",*c);
                }
            }
        } else if (len>1 && param[0]=='c') {
            cam = atoi(param+1);
        }
    }
}

void runTest() {
    const char* valid="TRSHN";
    int errMatrix[256][4]; //0->truePositive, 1->trNeg, 2->falsePos, 3->fNeg
    memset(errMatrix, 0, 256*4*sizeof(int));
    CvMat *features = cvCreateMat(1,COLS,CV_32F);
    unsigned char *x= trainMask->data.ptr;
    for (int i=0; i<t_data->rows; i++,x++) {
        if (*x == 0) {
            float *fdata = t_data->data.fl + i*t_data->cols;
            float *fresp = t_resp->data.fl + i*t_resp->cols;
            memcpy(features->data.fl, fdata, 32*t_data->cols);
            char p = (char)ptree->predict(features)->value;
            char r = (char)fresp[0];
            for (int j=1; j<200; j++) {
                if (strchr(valid,j) != NULL) {
                    if (j==r) {
                        if (j==p)
                            errMatrix[j][0]++;
                        else
                            errMatrix[j][3]++;
                    } else {
                        if (j==p)
                            errMatrix[j][2]++;
                        else
                            errMatrix[j][1]++;
                    }
                }
            }
        }
    }
    for (int c=1; c<200; c++) {
        if (strchr(valid,c) != NULL)
            printf("Class %c: TP=%i, TN=%i, FP=%i, FN=%i\n", c, errMatrix[c][0],
                    errMatrix[c][1], errMatrix[c][2], errMatrix[c][3]);
    }
}

/*
 *
 */
int main(int argc, char** argv) {
    //destroy_params();
    argc--; argv++;
    flags = 0;
    if (argc > 0) {
        readParams(argc, argv);
    }
    init_params(cam);
    train();
    if ((flags & F_CHK) != 0)
        runTest();
    else {        
        predict();
        destroy_params();
    }    
    return (EXIT_SUCCESS);
}

/*
 * File:   main.cpp
 * Author: seb
 *
 * Created on 7 de diciembre de 2010, 08:01 PM
 */

#include <cstdlib>
#include <cstring>
#include <cstdio>
#include <ctime>
#include <mysql.h>
#include "transform.h"
#include "highgui.h"
#include "documentLayout.h"
#include "config.h"
#include "test.h"
#include "page.h"
#include "chrfeatures.h"
#include "multivariate.h"

using namespace MyOCR;

bool debug=false;
CvRNG seed;

void addToDB(const char *fn)
{
    ImgMatrix *color = loadImage(fn);
    ImgMatrix gray(color->getWidth(), color->getHeight(), 1);
    Transform t;
    t.toGrayScale(&gray,color);
    t.binarize(&gray, &gray);
    const char *imgId = strrchr(fn, '/');
    imgId = (imgId == NULL) ? fn : imgId + 1;
    Page newPage(gray);
    for(int i = 0; i < newPage.lenght(); i++)
        newPage[i].saveComponent(imgId);
    delete color;
}

void insertMoment(int id, ConComponent *cc, double training, double validation) {
    double r = cvRandReal(&seed);
    MYSQL* conn = Configuration::getInstance()->connectDB();
    const char* set = (r<training) ? "tr" : (((r-training)<validation) ?
        "val" : "test");

    ChrMoments mm(cc);
    double hu[7];
    mm.getHuMoments(hu);
    char hustr[100];
    mysql_real_escape_string(conn, hustr, (char*)hu, 7*sizeof(double));
    
    char sql[1000];
    int tam = sprintf(sql, "insert into Moments (ComponentId,MomentType,\
TrainingSet,Vector) values (%i,%i,'%s','%s')", id, HU, set, hustr);
    mysql_real_query(conn, sql, tam);
}

void recomputeMoments(double training, double validation) {
    MYSQL *conn = Configuration::getInstance()->connectDB();
    mysql_query(conn, "truncate table Moments");
    mysql_query(conn, "select * from Components");
    MYSQL_RES *result = mysql_store_result(conn);
    MYSQL_ROW row;
    while ((row = mysql_fetch_row(result)) != NULL) {
        int l = atoi( row[2] );
        int r = atoi( row[3] );
        int u = atoi( row[4] );
        int d = atoi( row[5] );
        ImgMatrix *imagen = new ImgMatrix(r - l + 1, d - u + 1, 1);
        char *datos = (char*) imagen->getData();
        memcpy( datos, row[6], (r - l + 1)*(d - u + 1) );
        ConComponent cc(l,r,u,d,imagen);

        insertMoment(atoi(row[0]), &cc, training, validation);
    }
}

void testRecognition(int momentType) {
    Multivariate mult(momentType);
    MYSQL* conn = Configuration::getInstance()->connectDB();
    char text[200];
    sprintf(text, "select id,imageId from Moments inner join Components\
 on id=ComponentId where MomentType=%i and TrainingSet='%s';", momentType, "val");
    mysql_query(conn, text);
    MYSQL_RES *result = mysql_store_result(conn);
    MYSQL_ROW row;
    int right=0,total=0;
    if (result != NULL) {
        while ( (row = mysql_fetch_row(result)) != NULL ) {
            int cid = atoi( row[0] );
            ConComponent* cc = ConComponent::loadComponent(cid);
            double dist;
            int rsym = mult.recognizeTest(cc, dist);
            int sym = Multivariate::mySymbol(row[1]);
            printf("%i\t%c\t%c\t%lf\n", cid, (char)rsym, (char)sym, dist);
            total++;
            if (rsym == sym) right++;
            delete cc;
        }
        printf( "Total: %i\tCorrectos: %i\n Porcentaje: %f\n",total,right,
                ((float)right) / total );
    }
}

/*
 * Archivo principal para "producción"
 */
int main(int argc, char** argv) {
    if (debug)
        mainTest(argc, argv);
    argc--; argv++;
    seed = cvRNG( time(0) );
    if (argc > 1 && strcmp(argv[0], "add") == 0) {
        addToDB( argv[1] );
    } else if (argc>0 && strcmp(argv[0], "truncate") == 0) {
        MYSQL* conn = Configuration::getInstance()->connectDB();
        const char *query = "truncate table Components";
        mysql_query(conn, query);
    } else if (argc > 1 && strcmp(argv[0], "show") == 0) {
        ConComponent* comp = ConComponent::loadComponent( atoi(argv[1]) );
        comp->printComponent();
        ChrMoments *momm = new ChrMoments(comp);
        double *prueba = new double[7];
        momm->getHuMoments(prueba);
        printf("%E vsa %E", imag(momm->getComplexMoment(3, 0) * pow(momm->getComplexMoment(1, 2), 3)), prueba[6]);
        printf("\n");
        delete [] prueba;
    } else if (argc > 1 && strcmp(argv[0], "binarize") == 0) {
        testTransform(argv[1]);
    } else if (argc > 2 && strcmp(argv[0], "moments") == 0) {
        recomputeMoments( atof(argv[1]), atof(argv[2]) );
    } else if (argc > 1 && strcmp(argv[0], "testRecognition")==0) {
        int momentType = atoi(argv[1]);
        testRecognition( momentType );
    } else {
        printf("Error en la linea de comando:\n");
        printf("myocr add <filename>\tPara adicionar a la base de datos\n");
        printf("myocr truncate\tPara eliminar todo la información de OCR\n");
        printf("myocr show <imgId>\tPara mostrar un caracter en especial\n");
        printf("myocr binarize <imgId>\tPara binarizar una imagen\n");
        printf("myocr moments <tr> <val>\tPara recalcular los momentos de las imágenes\n");
        printf("myocr testRecognition <momentType>\tProbar reconocimiento\n");
    }
    return (EXIT_SUCCESS);
}

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

#define HU 1

using namespace MyOCR;

bool debug=true;
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
    } else {
        printf("Error en la linea de comando:\n");
        printf("myocr add <filename>\tPara adicionar a la base de datos\n");
        printf("myocr truncate\tPara eliminar todo la información de OCR\n");
        printf("myocr show <imgId>\tPara mostrar un caracter en especial\n");
        printf("myocr binarize <imgId>\tPara binarizar una imagen\n");
        printf("myocr moments <tr> <val>\tPara recalcular los momentos de las imágenes\n");
    }
    return (EXIT_SUCCESS);
}

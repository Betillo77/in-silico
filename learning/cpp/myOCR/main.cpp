/* 
 * File:   main.cpp
 * Author: seb
 *
 * Created on 7 de diciembre de 2010, 08:01 PM
 */

#include <cstdlib>
#include <cstring>
#include "transform.h"
#include "highgui.h"
#include "documentLayout.h"
#include "config.h"
#include "test.h"

using namespace MyOCR;

bool debug=false;

void addToDB(const char *fn)
{
    Matrix *color = loadImage(fn);
    Matrix gray(color->getWidth(), color->getHeight(), 1);
    Transform t;
    t.toGrayScale(&gray,color);
    t.binarize(&gray, &gray);
    DocumentLayout dl;
    list <ConComponent*> res = dl.connectedComponents(&gray);
    const char *imgId = strrchr(fn, '/');
    imgId = (imgId == NULL) ? fn : imgId+1;
    for(list<ConComponent*>::iterator it = res.begin(); it != res.end(); it++)
    {
        (*it)->saveComponent(imgId);
    }
}

/*
 * Archivo principal para "producción"
 */
int main(int argc, char** argv) {
    if (debug)
        mainTest(argc, argv);
    
    argc--; argv++;
    if (argc > 1 && strcmp(argv[0], "add") == 0) {
        addToDB( argv[1] );
    } else if (argc>0 && strcmp(argv[0], "truncate") == 0) {
        MYSQL* conn = Configuration::getInstance()->connectDB();
        const char *query = "truncate table Components";
        mysql_query(conn, query);
    } else if (argc > 1 && strcmp(argv[0], "show") == 0) {
        ConComponent::loadComponent( atoi(argv[1]) )->printComponent();
    } else {
        printf("Error en la linea de comando:\n");
        printf("myocr add <filename>\tPara adicionar a la base de datos\n");
        printf("myocr truncate\tPara eliminar todo la información de OCR\n");
        printf("myocr show <imgId>\tPara mostrar un caracter en especial\n");
    }
    return (EXIT_SUCCESS);
}



#include "multivariate.h"
#include "config.h"
#include "mysql.h"
#include <cstdio>
#include <cstdlib>

using namespace MyOCR;

Multivariate::Multivariate(int momentType) {
    MYSQL* conn = Configuration::getInstance()->connectDB();
    char text[200];
    sprintf(text,"select VectorSize from MomentTypes where MomentType=%i",momentType);
    Configuration::executeEscalar(text,text);
    int cols = atoi( text );
    sprintf(text, "select imageId, Vector from Moments inner join Components\
 on id=ComponentId where MomentType=%i and TrainingSet='%s';", momentType, "tr");
    mysql_query(conn, text);
    MYSQL_RES *result = mysql_store_result(conn);
    MYSQL_ROW row;

    if (result != NULL) {
        int rows = (int)mysql_num_rows(result);
        data = cvCreateMat( rows, cols, CV_64F );
        resp = cvCreateMat( rows, 1, CV_32S );

        int i=0;
        double *vec;
        int *ans;
        while ( (row = mysql_fetch_row(result)) != NULL ) {
            vec = data->data.db + i*cols;
            memcpy(vec, row[1], cols*sizeof(double));
            ans = resp->data.i + i;
            ans[0] = mySymbol(row[0]);
            symbols[*ans].increaseN();
            i++;            
        }

        calcSymbolParams();
    }
}

void Multivariate::calcSymbolParams() {
    map<int,SymbolParams>::iterator it;
    int cols = data->cols;
    for (it=symbols.begin(); it!=symbols.end(); it++) {
        SymbolParams *s = &(it->second);
        s->initParams(it->first, cols);
    }
    map<int,int> symCount;
    for (int i=0; i<data->rows; i++) {
        int *s = resp->data.i + i;
        int sym = *s;
        double *vec = data->data.db + i*cols;
        double *dest = symbols[sym].getData()->data.db + symCount[sym]*cols;
        memcpy( dest, vec, cols*sizeof(double));
        //printf("symbol: %c, count: %i, data0:%e\n",(char)sym,symCount[sym],dest[0]);
        symCount[sym]++;
    }
}

CvMat* Multivariate::getData() {
    return data;
}

int Multivariate::mySymbol(const char* fname) {
    if (fname[0] == 't') {
        return fname[1];
    }
}

SymbolParams* Multivariate::getSymbolParams(int symbol) {
    map<int,SymbolParams>::iterator it;
    it = symbols.find(symbol);
    if (it == symbols.end())
        return NULL;
    return &(it->second);
}

Multivariate::~Multivariate() {
    cvReleaseMat(&data);
    cvReleaseMat(&resp);
}

SymbolParams::SymbolParams() {
    ndata = 0;
    symbol = 0;
    data = NULL;
}

SymbolParams::~SymbolParams() {
    if (data != NULL)
        cvReleaseMat(&data);
}

int SymbolParams::GetNdata() const {
    return ndata;
}

void SymbolParams::SetSymbol(int symbol) {
    this->symbol = symbol;
}

int SymbolParams::GetSymbol() const {
    return symbol;
}

void SymbolParams::increaseN() {
    ndata++;
}

void SymbolParams::initParams(int symbol, int cols) {
    this->symbol = symbol;
    this->cols = cols;
    if (data != NULL) cvReleaseMat( &data);
    data = cvCreateMat( ndata, cols, CV_64F );
}

CvMat* SymbolParams::getData() {
    return this->data;
}
/* 
 * File:   multivariate.h
 * Author: seb
 *
 * Created on 14 de diciembre de 2010, 06:51 AM
 */

#ifndef _MULTIVARIATE_H
#define	_MULTIVARIATE_H

#include "cv.h"
#include <map>

using namespace std;

namespace MyOCR {

    class SymbolParams {
        int symbol;
        int ndata, cols;
        CvMat* data;
    public:
        SymbolParams();
        ~SymbolParams();
        int GetNdata() const;
        void SetSymbol(int symbol);
        int GetSymbol() const;
        CvMat* getData();
        void increaseN();
        void initParams(int symbol, int cols);
    };

    class Multivariate {
        CvMat *data;
        CvMat *resp;
        map<int,SymbolParams> symbols;

        void calcSymbolParams();
        int mySymbol(const char *fname);
    public:
        Multivariate(int momentType);
        ~Multivariate();
        SymbolParams *getSymbolParams(int symbol);
        CvMat *getData();
    };

}

#endif	/* _MULTIVARIATE_H */


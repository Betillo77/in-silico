/* 
 * File:   multivariate.h
 * Author: seb
 *
 * Created on 14 de diciembre de 2010, 06:51 AM
 */

#ifndef _MULTIVARIATE_H
#define	_MULTIVARIATE_H

#include "cv.h"
#include "component.h"
#include <map>

#define HU 1
#define CPLX 2

using namespace std;

namespace MyOCR {

    class SymbolParams {
        int symbol;
        int ndata, cols;
        int momentType;
        CvMat* data;
        CvMat* mean;
        CvMat* covar;
        CvMat* invCovar;
        double covarDet;
        double densCoeff;

        void computeStat();
    public:
        SymbolParams();
        ~SymbolParams();
        int GetNdata() const;
        void SetSymbol(int symbol);
        void SetMomentType(int momentType);
        int GetSymbol() const;
        CvMat* getData();
        void increaseN();
        void initParams(int symbol, int cols);
        CvMat* getInvCovar();
        CvMat* getCovar();
        CvMat* getMean();
        double mahalanobis(ConComponent *c);
        double pDensity(ConComponent *c);
    };

    class Multivariate {
        CvMat *data;
        CvMat *resp;
        map<int,SymbolParams> symbols;
        int momentType;

        void calcSymbolParams();        
    public:
        Multivariate(int momentType);
        ~Multivariate();
        SymbolParams *getSymbolParams(int symbol);
        CvMat *getData();
        static int mySymbol(const char *fname);
        int recognize(ConComponent* cc, double &prob);
        int recognizeTest(ConComponent *cc, double &dist);
    };

}

#endif	/* _MULTIVARIATE_H */


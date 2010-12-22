/* 
 * File:   chrfeatures.h
 * Author: seb
 *
 * Created on 12 de diciembre de 2010, 10:49 AM
 */

#ifndef _CHRFEATURES_H
#define	_CHRFEATURES_H

#include "component.h"
#include <cmath>
#include <complex>

namespace MyOCR {

    class ChrMoments {
        ConComponent *component;
        double cx, cy; //centroid
        double u00;
    public:
        ChrMoments(ConComponent *component);
        double getRawMoment(int p, int q);
        complex<double> getComplexMoment(int p, int q);
        double getCenterMoment(int p, int q);
        double getScaleInvMoment(int p, int q);
        void getHuMoments(double *ans);
        void getFlusserMoments(double *ans);
        void getCentroid(double *x, double *y);
    };

}


#endif	/* _CHRFEATURES_H */


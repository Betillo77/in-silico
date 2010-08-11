/* 
 * File:   transform.h
 * Author: seb
 *
 * Created on 10 de agosto de 2010, 02:23 PM
 */

#ifndef _TRANSFORM_H
#define	_TRANSFORM_H

#include "matrix.h"
#include <cmath>
#include <cassert>

namespace MyOCR {

    class Transform {
        float cvtWeight[4];
        int wsize; //Binarization window size
        float kfac; //Sauvola k factor
    public:
        Transform();
        void toGrayScale(Matrix *res, Matrix *m);

        /**
         * Binarize the image by Sauvola Method
         */
        void binarize(Matrix *res, Matrix *m);
    };
}

#endif	/* _TRANSFORM_H */


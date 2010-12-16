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

    typedef long long int ocr_int64;

    class Transform {
        float cvtWeight[4];
        int wsize; //Binarization window size
        float kfac; //Sauvola k factor
    public:
        Transform();
        void toGrayScale(ImgMatrix *res, ImgMatrix *m);

        /**
         * Binarize the image by Sauvola Method
         */
        void binarize(ImgMatrix *res, ImgMatrix *m);
    };
}

#endif	/* _TRANSFORM_H */


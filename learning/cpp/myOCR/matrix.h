/* 
 * File:   image.h
 * Author: seb
 *
 * Created on 10 de agosto de 2010, 11:59 AM
 */

#ifndef _IMAGE_H
#define	_IMAGE_H

namespace MyOCR {

    typedef unsigned char pixel;
    typedef long long int int64;

    class Matrix {
        int width, height;
        int channels;
        pixel *data;
    public:
        Matrix(int width, int height, int channels);
        ~Matrix();
        int getWidth();
        int getHeight();
        int getChannels();
        pixel *getData();
        pixel get(int i, int j);
    };

}
#endif	/* _IMAGE_H */


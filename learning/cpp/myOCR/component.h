/* 
 * File:   component.h
 * Author: seb
 *
 * Created on 6 de diciembre de 2010, 03:13 PM
 */

#ifndef _COMPONENT_H
#define	_COMPONENT_H

#include "matrix.h"
#include <cstdlib>
#include <vector>
#include <iostream>
#include "config.h"
#include <limits>

namespace MyOCR {

    struct Point {
        int i, j;

        Point(int x, int y) {
            i = x;
            j = y;
        }

        Point() {
            i = 0;
            j = 0;
        }
    };
    
    //Class that represents connected component
    class ConComponent {
        Matrix *comp; //component itself
        int top, left, down, right;
        double dpN[4][4], dpM[4][4], dpU[4][4], *dpI;

        //Returns the number of neighbors (Stored in ans), of the point act
        //that are "turned on" in the image
        int getNeighbors(Point *ans, Point act, Matrix *img);
        double pow(double a, int b);
        double u(int i, int j);
        double n(int i, int j);
        double m(int i, int j);
     public:
        ConComponent(int i, int j, Matrix *binImg);
        ConComponent(int l, int r, int u, int d, Matrix *imagen);
        ~ConComponent();
        void printComponent();
        void saveComponent(const char *imageId);
        double *huMoments();
        static ConComponent *loadComponent(int componentId);
    };

}
#endif	/* _COMPONENT_H */


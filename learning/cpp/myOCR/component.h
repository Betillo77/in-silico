/* 
 * File:   component.h
 * Author: seb
 *
 * Created on 6 de diciembre de 2010, 03:13 PM
 */

#ifndef _COMPONENT_H
#define	_COMPONENT_H

#include "matrix.h"

namespace MyOCR {

    //Class that represents connected component
    class ConComponent {
        Matrix *comp; //component itself

        //Returns the number of neighbors (Stored in ans), of the point act
        //that are "turned on" in the image
        int getNeighbors(Point *ans, Point act, Matrix *img);
    public:
        ConComponent(int i, int j, Matrix *binImg);
        ~ConComponent();
        void printComponent();
    };

}

#endif	/* _COMPONENT_H */


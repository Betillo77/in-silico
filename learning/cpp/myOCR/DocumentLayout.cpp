/* 
 * File:   DocumentLayout.cpp
 * Author: santiago
 * 
 * Created on December 6, 2010, 4:33 PM
 */


#include "DocumentLayout.h"

using namespace MyOCR;

DocumentLayout::DocumentLayout() {
}

DocumentLayout::~DocumentLayout() {

}

list <ConComponent*> DocumentLayout::connectedComponents(Matrix *img)
{
    list <ConComponent*> ans;
    Matrix & mat = *img;
    for(int i = 0; i < mat.getHeight(); i++)
        for(int j = 0; j < mat.getWidth(); j++)
            if(mat(i, j) != 255)
                ans.push_back(new ConComponent(i, j, img));
    ConComponent *primero = *ans.begin();
    primero->printComponent();
    return ans;
}

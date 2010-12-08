#include "documentLayout.h"

using namespace MyOCR;

DocumentLayout::DocumentLayout() {
}

DocumentLayout::~DocumentLayout() {
}

void DocumentLayout::connectedComponents(list <ConComponent*> &ans, Matrix &mat) {
    for(int i = 0; i < mat.getHeight(); i++)
        for(int j = 0; j < mat.getWidth(); j++)
            if(mat(i, j) != 255)
                ans.push_back(new ConComponent(i, j, &mat));
}


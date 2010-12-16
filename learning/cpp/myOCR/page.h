/* 
 * File:   Page.h
 * Author: santiago
 *
 * Created on December 8, 2010, 11:11 AM
 */

#ifndef _PAGE_H
#define	_PAGE_H

#include <vector>
#include <list>
#include "component.h"
#include "documentLayout.h"
using namespace std;

namespace MyOCR {

class Page {
    vector <ConComponent*> *components;
    int size;
public:
    Page(ImgMatrix &grayScale);
    ~Page();
    int lenght();
    ConComponent &operator[](int index);
};

}
#endif	/* _PAGE_H */


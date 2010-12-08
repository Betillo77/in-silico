/* 
 * File:   DocumentLayout.h
 * Author: santiago
 *
 * Created on December 6, 2010, 4:33 PM
 */
#ifndef _DOCUMENTLAYOUT_H
#define	_DOCUMENTLAYOUT_H

#include <list>
#include "component.h"
using namespace std;

namespace MyOCR {

    class DocumentLayout {
    public:
        DocumentLayout();
        ~DocumentLayout();
        void connectedComponents(list <ConComponent*> &res, Matrix &img);
    };

}
#endif	/* _DOCUMENTLAYOUT_H */


/* 
 * File:   test.h
 * Author: seb
 *
 * Created on 7 de diciembre de 2010, 09:19 PM
 */

#ifndef _TEST_H
#define	_TEST_H

#include "matrix.h"

using namespace MyOCR;

extern bool debug;

Matrix* loadImage(const char *fn);
int mainTest(int argc, char** argv);
void testTransform(const char *fn);

#endif	/* _TEST_H */


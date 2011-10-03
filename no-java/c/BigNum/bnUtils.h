/* 
 * File:   bnUtils.h
 * Author: seb
 *
 * Created on 28 de julio de 2010, 10:24 AM
 */

#ifndef _BNUTILS_H
#define	_BNUTILS_H

#ifdef	__cplusplus
extern "C" {
#endif

#define MAX(x,y) ( ((x)>(y)) ? (x) : (y) )

#define REP(i,N) for((i)=0; (i)<(N); (i)++)
#define REPB(i,N) for((i)=(N)-1; (i)>=0; (i)--)

typedef unsigned int word;
typedef unsigned long long dword;

void copyw(word* res, word* a, word size);

void invStr(char *str, int n);


#ifdef	__cplusplus
}
#endif

#endif	/* _BNUTILS_H */


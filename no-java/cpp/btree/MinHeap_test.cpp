
#include "MinHeap.h"
#include <cstdio>
#include <cstdlib>

int main(){
    MyHeap mh(100,MIN_HEAP);
    
    Dato tmp(25, 1, 15);
    mh.insert(tmp);
    mh.insert(26,10,1);
    mh.insert(27,2,2);
    mh.insert(28,11,5);
    mh.insert(29,12,21);
    mh.insert(30,3,223);
    mh.insert(31,4,81);

	/*
	This part of test is correct
	mh.minDate(tmp);
    tmp.print();    
    map<long, int> mp = mh.getMap();
    map<long, int>::iterator it;
    for(it = mp.begin();it!=mp.end(); it++)
        printf("%ld %d\n", it->first, it->second);
    printf("%d\n", mh.contains(15));
    printf("%d\n", mh.getMemDir(25)); // 28 -> 5 , 25->15
    */

    //mh.remMinDate(tmp);
	//tmp.print();
    //mh.minDate(tmp);
    //tmp.print();
    mh.deleteDir(29);
    map<dir, int> mp = mh.getMap();
	printf("%d \n",mp[31]);
	printf("%d \n",mp[25]);
	printf("%d \n",mp[26]);
    //mh.minDate(tmp);
    //tmp.print();
	/*printf("mapa\n");
	map<long, int> mp = mh.getMap();
    map<long, int>::iterator it;
    for(it = mp.begin();it!=mp.end(); it++)
        printf("%ld %d\n", it->first, it->second);
	*/
    //mh.updateDate(25,13);
    //mh.minDate(tmp);
    //tmp.print();

    return 0;
}


/**
Respuesta a mano:

indice(pos)     date
0               26
1               29
2               27
3               28
4               25

*/

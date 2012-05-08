#include "MinHeap.cpp"

int main(){
    MyHeap mh(100,MIN_HEAP);
    
    Dato tmp(25, 10, 15);
    mh.insert(tmp);
    mh.insert(26,1,1);
    mh.insert(27,15,2);
    mh.insert(28,28,5);
    mh.insert(29,2,20);
    mh.minDate(tmp);
    tmp.print();
    
    /*map<long, int> mp = mh.getMap();
    map<long, int>::iterator it;
    for(it = mp.begin();it!=mp.end(); it++){
        printf("%ld %d\n", it->first, it->second);
    }*/
    
    printf("%d\n", mh.contains(15));
    
    printf("%d\n", mh.getMemDir(25)); // 28 -> 5 , 25->15
    
    mh.updateDate(25,13);
    mh.minDate(tmp);

    mh.remMinDate(tmp);
    mh.deleteDir(29);
    mh.minDate(tmp);
    tmp.print();
    
    tmp.print();

    /*mp = mh.getMap();
    
    for(it = mp.begin();it!=mp.end(); it++){
        printf("%ld %d\n", it->first, it->second);
    }*/
    
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

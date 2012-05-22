
#ifndef MINHEAPBT_H
#define MINHEAPBT_H

#include <map>

using namespace std;

//heap flags
#define MIN_HEAP 0
#define MAX_HEAP 1

//heap macros
#define PARENT(i) ((i)/2)
#define LEFT(i) (2*(i))
#define RIGHT(i) (2*(i) + 1)

typedef unsigned long long int dir; //Tipo de dato de direcciones en el disco

class Dato{
    dir dire;
    dir date;
    int memdir;

    public:
    Dato(dir dire, dir date, int memdir);
    Dato();
    dir& getDate();
    dir& getDir();
    int& getMem();
	void setDate(dir date);
	void setDir(dir dire);
	void setMem(int memdir);
    void print();
    Dato& operator = (const Dato &a);
    bool operator < (const Dato &a);
    bool operator > (const Dato &a);
    bool operator == (const Dato &a);
};

class MyHeap {
    map<dir, int> mapa;
    char flags;
    Dato *A;
    int maxSize;
    int size;    int getMem();
    bool isGreatest(int i, int j);
    void swapDir(dir a,dir b);
    void swap(int i, int j);
public:
	MyHeap();
    MyHeap(int maxSize,char flags=0);
    ~MyHeap();
    void top(Dato &ans);
    void pop(Dato &ans);
    void minDate(Dato &ans);
    void remMinDate(Dato &ans);
    bool contains(dir dire);
    void insert(Dato key);
    void insert(dir dire, long date, int memdir);
    int getMemDir(dir dire);
    void deleteDir(dir dire);
    void updateDate(dir dire,long date);
    int getSize();
    void heapify(int i);
    //the new key must be greater or equal for max_heap, and smaller or equal for min_heap
    void updateKey(int i, Dato k); 
    map<dir, int> getMap();
    MyHeap& operator = (const MyHeap &a);
};

#endif

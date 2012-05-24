
#ifndef BTREE_H
#define BTREE_H

#include "MinHeap.h"
#include <cstdio>
#include <cstdlib>

#ifndef BTDEG
#define BTDEG 60
#endif

/*********** BTree flags section ***********/
//Replace BTree
#define REP_TREE 1
//Append to BTree
#define APP_TREE 0

//True to save only in pageswap
#define MEM_TREE 2
/********* End BTree flags section *********/

template<class T>
class Page {
public:
    int n;
    char leaf;
    T key[2*BTDEG - 1];
    dir c[2*BTDEG];
};

template<class T>
class PageSwap {
    //Numero maximo de paginas a guardar en memoria
    int cacheSize;
    int cacheUsed;
    
    //Para decidir que pagina bajar de la memoria
    long long mydate;
    
    //Cache compuesta por las paginas cargadas en memoria
    Page<T> *cache; //cache of min size 10 pages for btree
    Page<T> blank; //blank page
    
    //Diccionario que indica que páginas hay en memoria y en que posicion del arreglo cache
	MyHeap *tlb;
	
    char flags;	
    
    FILE *f;
    void realDiskWrite(dir x, int pos);
public:
    PageSwap(int cacheSize, const char *fname, char flags=0);
    ~PageSwap();
    Page<T> *diskRead(dir x);
    void diskWrite(dir x);
    dir allocateNode();
    void debug1(dir x);
    void setRoot(dir x);

	int c_date;
};

template<class T>
class BTree {
    dir droot;
    Page<T> *root;
    FILE *f;
    int pSearch(dir x, T k, dir *p);
public:
	PageSwap<T> *ps;
    BTree(int cacheSize, const char *fname, char flags=0);
    ~BTree();
    Page<T> *getRoot();
    //void setRoot(Page<T> *root);
    void splitChild(dir x, int i);
    void insert(T k);
    void insertNonFull(dir x, T k);
    int search(T k, dir *p);
};


#endif

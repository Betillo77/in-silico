
#ifndef BTREE_H
#define BTREE_H

#include <cstdio>
#include <cstdlib>
#include <map>

using namespace std;

#ifndef BTDEG
#define BTDEG 60
#endif

//heap flags
#define MIN_HEAP 0
#define MAX_HEAP 1

typedef unsigned long long int dir; //Tipo de dato de direcciones en el disco

class Dato{
    dir dire;
    dir date;
    int memdir;
public:
    char changed; //used only when flag MEM_TREE is used
    
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
    void insert(dir dire, dir date, int memdir);
    int getMemDir(dir dire);
    void deleteDir(dir dire);
    void updateDate(dir dire, dir date);
    int getSize();
    void heapify(int i);
    //the new key must be greater or equal for max_heap, and smaller or equal for min_heap
    void updateKey(int i, Dato k); 
    map<dir, int> getMap();
    MyHeap& operator = (const MyHeap &a);
    char& changed(dir dire);
};

/*********** BTree flags section ***********/
//Replace BTree
#define REP_TREE 1
//Append to BTree
#define APP_TREE 0

//True to save only in pageswap
#define MEM_TREE 2
/********* End BTree flags section *********/

template<class T, int btdeg=BTDEG>
class Page {
public:
    int n;
    char leaf;
    T key[2*btdeg - 1];
    dir c[2*btdeg];
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

#include <cstdio>
#include <cstdlib>

//heap macros
#define PARENT(i) ((i)/2)
#define LEFT(i) (2*(i))
#define RIGHT(i) (2*(i) + 1)

Dato::Dato(){
    this->dire = 0;
    this->date = 0;
    this->memdir = 0;
}

Dato::Dato(dir dire, dir date, int memdir){
    this->dire = dire;
    this->date = date;
    this->memdir = memdir;
}

dir& Dato::getDate(){
    return this->date;
}

dir& Dato::getDir(){
    return this->dire;
}

int& Dato::getMem(){
    return this->memdir;
}

void Dato::setDate(dir date){
	this->date = date;
}

void Dato::setDir(dir dire){
	this->dire = dire;
}
void Dato::setMem(int memdir){
	this->memdir = memdir;
}

void Dato::print(){
    printf("%lld\t%lld\t%d\n",this->dire,this->date,this->memdir);
}

Dato& Dato::operator = (const Dato &a){
    if(this!=&a){ 
        this->dire = a.dire;
        this->date = a.date;
        this->memdir = a.memdir;
        this->changed = a.changed;
    }
    return *this;
}

bool Dato::operator < (const Dato &a){
    return this->date < a.date;
}

bool Dato::operator > (const Dato &a){
    return this->date > a.date;
}

bool Dato::operator == (const Dato &a){
    return this->date == a.date;
}

// Start of MinHeap class implementation

MyHeap::MyHeap() {

}

MyHeap::MyHeap(int maxSize, char flags) {
    this->maxSize = maxSize;
    this->flags = flags;
    this->A = new Dato[maxSize];
    this->size = 0;
}

MyHeap::~MyHeap() {
    delete A;
}

bool MyHeap::isGreatest(int i, int j) {
    if (flags & MAX_HEAP)
        return A[i-1] > A[j-1];
    else
        return A[j-1] > A[i-1];
}

void MyHeap::swapDir(dir a,dir b){
    int tmp = mapa[a];
    mapa[a] = mapa[b];
    mapa[b] = tmp;
}

void MyHeap::swap(int i, int j) {
    swapDir(A[i-1].getDir(),A[j-1].getDir());
    Dato tmp = A[i-1];
    A[i-1] = A[j-1];
    A[j-1] = tmp;
}

void MyHeap::top(Dato &ans) {
    ans = A[0];
}

void MyHeap::pop(Dato &ans) {
    if (size == 0) throw "Heap datastructure underflow";
    ans = A[0];
    swap(1,size);
    size--;
    this->mapa.erase(ans.getDir());
    heapify(1);    
}

int MyHeap::getSize() {
    return size;
}

void MyHeap::minDate(Dato &ans){
    top(ans);
}

void MyHeap::remMinDate(Dato &ans){
    pop(ans);
}

bool MyHeap::contains(dir dire){
    map<dir, int>::iterator  it = mapa.find(dire);
    return (it!=mapa.end())?true:false;
}

int MyHeap::getMemDir(dir dire){
    //si no está en el mapa retorna -1;
    map<dir, int>::iterator it = mapa.find(dire);
    return (it!=mapa.end())?A[mapa[dire]].getMem():-1;
}

char& MyHeap::changed(dir dire){
    map<dir, int>::iterator it = mapa.find(dire);
    if (it==mapa.end()) throw "Disk address not found exception";
    return A[it->second].changed;
}

void MyHeap::deleteDir(dir dire){
	map<dir, int>::iterator it = mapa.find(dire);
	if(it == mapa.end()) throw "the direction is not in memory";
    int pos = mapa[dire];
	mapa[A[size-1].getDir()] = pos;
    if(A[pos] < A[size-1]){
        A[pos] = A[size-1];
        size--;
        heapify(pos+1);
    }else if(A[pos] > A[size-1]){
        updateKey(pos+1,A[size-1]);
        size--;
    }
    mapa.erase(dire);
}

void MyHeap::updateDate(dir dire,dir date){
    int mem = this->getMemDir(dire);
    if(mem == -1) throw "the dirtection no exists";
	int pos = mapa[dire];
	dir prevdate = A[pos].getDate();
    A[pos].setDate(date);
	if(prevdate < date){
        heapify(pos+1);
    }else if(A[pos].getDate() > date){
        updateKey(pos+1,A[pos]);
    }
}

void MyHeap::heapify(int i) {
    int l = LEFT(i);
    int r = RIGHT(i);
    int largest;
    if (l <= size && isGreatest(l,i))
        largest = l;
    else
        largest = i;
    if (r<=size && isGreatest(r,largest))
        largest = r;
    if (largest != i) {
        swap(i,largest);
        heapify(largest);
    }
}

void MyHeap::updateKey(int i, Dato key) {
    if (flags & MAX_HEAP) {
        if (key < A[i-1]) throw "New key is smaller than current key";
        A[i-1] = key;
        while (i>1 && A[PARENT(i)-1]<A[i-1]) {
            swap(i,PARENT(i));
            i = PARENT(i);
        }
    } else  {
        if (key > A[i-1]) throw "New key is greater than current key";
        A[i-1] = key;
        while (i>1 && A[PARENT(i)-1]>A[i-1]) {
            swap(i,PARENT(i));
            i = PARENT(i);
        }
    }
}

void MyHeap::insert(Dato key) {
    size++;
    A[size-1] = key;
    this->mapa[key.getDir()] = size-1;
    updateKey(size,key);
}

void MyHeap::insert(dir dire, dir date, int memdir){
    Dato tmp(dire,date,memdir);
    tmp.changed=0;
    this->insert(tmp);
}

MyHeap& MyHeap::operator = (const MyHeap &a){
    if(this!=&a){ 
        this->maxSize = a.maxSize;
        this->flags = a.flags;
        this->A = a.A;
        this->size = a.size;
    }
    return *this;
}

map<dir, int> MyHeap::getMap(){
    return this->mapa;
}

template<class T>
PageSwap<T>::PageSwap(int cacheSize, const char *fname, char flags) {
    this->cacheSize = cacheSize;
    this->cacheUsed = 0;
    this->cache = new Page<T>[cacheSize];
    //this->tlbinv = new dir[cacheSize];
	this->tlb = new MyHeap(cacheSize,MIN_HEAP);
	this->c_date  = 0;
	this->flags = flags;
    if (flags & REP_TREE)
        f = fopen(fname, "wb+");
    else
        f = fopen(fname, "rb+");
        
    if (f == NULL) throw "Error al abrir el archivo";
}

template<class T>
PageSwap<T>::~PageSwap() {
    delete cache;
    delete tlb;
    fclose(f);
}


template<class T>
Page<T>* PageSwap<T>::diskRead(dir x) {
    if (tlb->contains(x)) {
        tlb->updateDate(x,c_date++);
        return &cache[tlb->getMemDir(x)];
    }
    Page<T> *p;
    if (cacheUsed < cacheSize) {
        //Load a page in a new memory position
        p = &(cache[cacheUsed]);
        tlb->insert(x, c_date++, cacheUsed++);        
    } else {
        //Load a page in a used memory position
		Dato tmp;
		tlb->remMinDate(tmp);
		if ( ((flags & MEM_TREE)!=0) && tmp.changed) {
        	realDiskWrite(tmp.getDir(), tmp.getMem());        	
        }
        p = &(cache[tmp.getMem()]);
		tlb->insert(x, c_date++ , tmp.getMem());		
    }
    fseek(f,x,SEEK_SET);
    fread(p,sizeof(Page<T>),1,f);
    return p;
}

template<class T>
dir PageSwap<T>::allocateNode() {
    fseek(f,0,SEEK_END);
    dir x = ftell(f);
    fwrite(&blank, sizeof(Page<T>), 1, f);
    return x;
}

template<class T>
void PageSwap<T>::realDiskWrite(dir x, int pos) {
    fseek(f,x,SEEK_SET);
    fwrite(&cache[pos],sizeof(Page<T>),1,f);
}

template<class T>
void PageSwap<T>::diskWrite(dir x) {
    if (flags & MEM_TREE) {
    	//If the tree is meant to be on RAM, then we will write it later
    	tlb->changed(x) = 1;
    } else {
    	//If the tree is not to be on RAM write now to disk
    	int pos = tlb->getMemDir(x);
    	realDiskWrite(x,pos);
    }
}

template<class T>
BTree<T>::BTree(int cacheSize, const char *fname, char flags){
    cacheSize += 10; //Min recomended for BTree
    ps = new PageSwap<T>(cacheSize,fname,flags);
    dir xdir = ps->allocateNode();
    Page<T> *x = ps->diskRead(xdir);
    x->leaf = 1;
    x->n = 0;
    ps->diskWrite(xdir);
    root = x;
    droot = xdir;
}

template<class T>
BTree<T>::~BTree() {
    delete ps;
}

template<class T>
Page<T> *BTree<T>::getRoot(){
    return root;
}
/*
template<class T>
void BTree<T>::setRoot(Page<T> *root){
    this->root = root;
}*/

template<class T>
void BTree<T>::splitChild(dir xdir, int i){
    int t = BTDEG;
    Page<T> *x = ps->diskRead(xdir);
    dir zdir = ps->allocateNode();
    dir ydir = x->c[i-1];
    Page<T> *z = ps->diskRead(zdir);
    Page<T> *y = ps->diskRead(ydir);
    z->leaf = y->leaf;
    z->n = t - 1; 
    for(int j = 1; j < t; j++){
        z->key[j-1] = y->key[j+t-1];
    }
    if ( ! y->leaf ) {
        for (int j=1; j<=t; j++) {
            z->c[j-1] = y->c[j+t-1];
        }
    }
    y->n = t-1;
    for (int j=x->n + 1; j>=(i+1); j--)
        x->c[j] = x->c[j-1];
    x->c[i] = zdir;
    for(int j = x->n ; j>=i ; j--)
        x->key[j] = x->key[j-1];
    x->key[i-1] = y->key[t-1];
    x->n = x->n + 1;
    ps->diskWrite(ydir);
    ps->diskWrite(zdir);
    ps->diskWrite(xdir);
}

template<class T>
void BTree<T>::insert(T k) {
    int t = BTDEG;
    dir dr = this->droot;
    Page<T> *r = ps->diskRead(dr);    
    if (r->n == (2*t-1)) {
        dir ds = ps->allocateNode();
        Page<T> *s = ps->diskRead(ds);
        this->root = s;
        this->droot = ds;
        s->leaf = 0;
        s->n = 0;
        s->c[0] = dr;
        splitChild(ds, 1);
        insertNonFull(ds,k);
    }else{
        insertNonFull(dr,k);
    }
}

template<class T>
void BTree<T>::insertNonFull(dir xdir,T k){
    int t = BTDEG;
    Page<T> *x = ps->diskRead(xdir);
    int i = x->n;
    if(x->leaf){
        while(i >= 1 and k < x->key[i-1]){
            x->key[i] = x->key[i-1];
            i = i-1;
        }
        x->key[i] = k;
        x->n = x->n + 1;
        ps->diskWrite(xdir);
    }else{
        while(i >= 1 and k < x->key[i-1])
            i = i-1;
        i = i + 1;
        Page<T> *xci = ps->diskRead(x->c[i-1]);
        if(xci->n == 2*t -1){
            splitChild(xdir, i);
            if( k > x->key[i-1])
                i = i +1;
        }
        insertNonFull(x->c[i-1],k);
    }
}

template<class T>
int BTree<T>::pSearch(dir xdir, T k, dir *p){
    int i = 1;
    Page<T> *x = ps->diskRead(xdir);
    while( i <= x->n and k > x->key[i-1])
        i = i + 1;
    if( i <= x->n and k == x->key[i-1]) {
        *p = xdir;
        return i;
    }else if(x->leaf){
        *p = 0;
        return -1;// NIL
    }else{
        Page<T> * xci = ps->diskRead(x->c[i-1]);
        *p = x->c[i-1];
        return pSearch(x->c[i-1], k , p);
    }
}

template<class T>
int BTree<T>::search(T k, dir *p){
    return pSearch(this->droot,k,p);
}
#endif

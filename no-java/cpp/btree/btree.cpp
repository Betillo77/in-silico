
#include "btree.h"


template<class T>
PageSwap<T>::PageSwap(int cacheSize, char *fname, char flags) {
    this->cacheSize = cacheSize;
    this->cacheUsed = 0;
    this->cache = new Page<T>[cacheSize];
    //this->tlbinv = new dir[cacheSize];
	this->tlb = new MyHeap(cacheSize,MIN_HEAP);
	this->c_date  = 0;
    this->mroot = -1; //no page is root unless otherwise stated
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
    if (tlb.contains(x))
        return &cache[tlb.getMemDir(x)];
    Page<T> *p;
    if (cacheUsed < cacheSize) {
        //Load a page in a new memory position
        p = &(cache[cacheUsed]);
        //mytlb[x] = cacheUsed;
		tlb.insert(x, c_date++, cacheUsed++);
        //tlbinv[cacheUsed] = x;
        //cacheUsed++;
    } else {
        //Load a page in a used memory position
		Dato tmp;
		tlb.remMinDate(tmp);
		//pos = tmp.memdir; // use priority queue to choose position to put down of memory
        //diskWrite(tlbinv[pos]); //Not necessary because is updated all time
        p = &(cache[tmp.getMem()]);
        //mytlb.erase(tlbinv[pos]);
        //mytlb[x] = pos;
        //tlbinv[pos] = x;
		tlb.insert(x, c_date++ , tmp.getMem());
        
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
void PageSwap<T>::diskWrite(dir x) {
    int pos = tlb.getMemDir(x);
    fseek(f,x,SEEK_SET);
    fwrite(&cache[pos],sizeof(Page<T>),1,f);
}

template<class T>
void PageSwap<T>::setRoot(dir x) {
    diskRead(x);
    mroot = tlb.getMemDir(x);
}

//Erase this later
template<class T>
void PageSwap<T>::debug1(dir x) {
    Page<T> p;
    fseek(f,x,SEEK_SET);
    fread(&p,sizeof(Page<T>),1,f);
    printf("%d %c\n",p.n,p.leaf);
}

template<class T>
BTree<T>::BTree(int cacheSize, char *fname, char flags){
    cacheSize += 10; //Min recomended for BTree
    ps = new PageSwap<T>(cacheSize,fname,flags);
    dir xdir = ps->allocateNode();
    Page<T> *x = ps->diskRead(xdir);
    x->leaf = 1;
    x->n = 0;
    ps->diskWrite(xdir);
    root = x;
}

template<class T>
BTree<T>::~BTree() {
    delete ps;
}

template<class T>
Page<T> *BTree<T>::getRoot(){
    return root;
}

template<class T>
void BTree<T>::splitChild(dir xdir, int i){
    int t = BTDEG;
    Page<T> *x = ps.diskRead(xdir);
    dir zdir = ps.allocateNode();
    dir ydir = x->c[i];
    Page<T> *z = ps.diskRead(xdir);
    Page<T> *y = ps.diskRead(ydir);
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
        x->key[j] = x->key[j-1];
    x->key[i-1] = y->key[t-1];
    x->n = x->n + 1;
    ps.diskWrite(ydir);
    ps.diskWrite(zdir);
    ps.diskWrite(xdir);
}

template<class T>
void BTree<T>::insert(T k) {
    /*int t = BTDEG;
    if (root->n = (2*t-1)) {
        dir ds = ps.allocateNode();
        root = ps.readDisk(ds);
        root->leaf = 0;
        root->n=0;
        root->c[0] = 
    }*/
}

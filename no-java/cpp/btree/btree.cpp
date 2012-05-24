
#include "btree.h"


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


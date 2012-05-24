
#include "MinHeap.h"
#include <cstdio>
#include <cstdlib>

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

void MyHeap::updateDate(dir dire,long date){
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

void MyHeap::insert(dir dire, long date, int memdir){
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

/*
int main(){
    MyHeap mh(100,MIN_HEAP);
    srand(0);
    for (int i=0; i<100; i++) {
        Dato tmp(i,rand()%10000,5);
        mh.insert(tmp);
    }
	Dato nuevo;
    //mh.updateKey(20,9999);
    for (int i=0; i<20; i++) {
		mh.pop(nuevo);
        printf("%d\t",nuevo.getDate());
        printf("%d\n",mh.getMap()[i]);
    }
    return 0;
}*/

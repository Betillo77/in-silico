
#include <cstdio>
#include <cstdlib>
#include <map>

using namespace std;

//heap flags
#define MIN_HEAP 0
#define MAX_HEAP 1

//heap macros
#define PARENT(i) ((i)/2)
#define LEFT(i) (2*(i))
#define RIGHT(i) (2*(i) + 1)


class Dato{
    long dir;
    long date;
    int memdir;

    public:
    Dato(long dir, long date, int memdir);
    Dato();
    long& getDate();
    long& getDir();
    int& getMem();
	void setDate(long date);
	void setDir(long dir);
	void setMem(int memdir);
    void print();
    Dato& operator = (const Dato &a);
    bool operator < (const Dato &a);
    bool operator > (const Dato &a);
    bool operator == (const Dato &a);
};

Dato::Dato(){
    this->dir = 0;
    this->date = 0;
    this->memdir = 0;
}

Dato::Dato(long dir, long date, int memdir){
    this->dir = dir;
    this->date = date;
    this->memdir = memdir;
}

long& Dato::getDate(){
    return this->date;
}

long& Dato::getDir(){
    return this->dir;
}

int& Dato::getMem(){
    return this->memdir;
}

void Dato::setDate(long date){
	this->date = date;
}

void Dato::setDir(long dir){
	this->dir = dir;
}
void Dato::setMem(int memdir){
	this->memdir = memdir;
}

void Dato::print(){
    printf("%ld\t%ld\t%d\n",this->dir,this->date,this->memdir);
}

Dato& Dato::operator = (const Dato &a){
    if(this!=&a){ 
        this->dir = a.dir;
        this->date = a.date;
        this->memdir = a.memdir;
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


class MyHeap {
    map<long, int> mapa;
    char flags;
    Dato *A;
    int maxSize;
    int size;    int getMem();
    
    bool isGreatest(int i, int j);
    void swapDir(long a,long b);
    void swap(int i, int j);
public:
    MyHeap(int maxSize,char flags=0);
    ~MyHeap();
    void top(Dato &ans);
    void pop(Dato &ans);
    void minDate(Dato &ans);
    void remMinDate(Dato &ans);
    bool contains(long dir);
    void insert(Dato key);
    void insert(long dir, long date, int memdir);
    int getMemDir(long dir);
    void deleteDir(long dir);
    void updateDate(long dir,long date);
    int getSize();
    void heapify(int i);
    //the new key must be greater or equal for max_heap, and smaller or equal for min_heap
    void updateKey(int i, Dato k); 
    map<long, int> getMap();
    MyHeap& operator = (const MyHeap &a);
};

MyHeap::MyHeap(int maxSize, char flags) {
    this->maxSize = maxSize;
    this->flags = flags;
    this->A = new Dato[maxSize];
    this->size = 0;
}


MyHeap::~MyHeap() {
    delete [] A;
}

bool MyHeap::isGreatest(int i, int j) {
    if (flags & MAX_HEAP)
        return A[i-1] > A[j-1];
    else
        return A[j-1] > A[i-1];
}

void MyHeap::swapDir(long a,long b){
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

bool MyHeap::contains(long dir){
    map<long, int>::iterator  it = mapa.find(dir);
    return (it!=mapa.end())?true:false;
}

int MyHeap::getMemDir(long dir){
    //si no está en el mapa retorna -1;
    map<long, int>::iterator it = mapa.find(dir);
    return (it!=mapa.end())?A[mapa[dir]].getMem():-1;
}

void MyHeap::deleteDir(long dir){
	map<long, int>::iterator it = mapa.find(dir);
	if(it == mapa.end()) throw "the direction is not in memory";
    int pos = mapa[dir];
	mapa[A[size-1].getDir()] = pos;
    if(A[pos] < A[size-1]){
        A[pos] = A[size-1];
        size--;
        heapify(pos+1);
    }else if(A[pos] > A[size-1]){
        updateKey(pos+1,A[size-1]);
        size--;
    }
    mapa.erase(dir);
}


void MyHeap::updateDate(long dir,long date){
    int mem = this->getMemDir(dir);
    if(mem == -1) throw "the dirtection no exists";
	int pos = mapa[dir];
    A[pos].setDate(date);
	if(A[pos].getDate() < date){
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

void MyHeap::insert(long dir, long date, int memdir){
    Dato tmp(dir,date,memdir);
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

map<long, int> MyHeap::getMap(){
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
    //mh.updateKey(20,9999);
    for (int i=0; i<20; i++) {
        printf("%d\t",mh.pop().getDate());
        printf("%d\n",mh.getMap()[i]);
    }
    return 0;
}*/

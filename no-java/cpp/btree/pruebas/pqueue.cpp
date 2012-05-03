
#include <cstdio>
#include <cstdlib>

//heap flags
#define MIN_HEAP 0
#define MAX_HEAP 1

//heap macros
#define PARENT(i) ((i)/2)
#define LEFT(i) (2*(i))
#define RIGHT(i) (2*(i) + 1)

template<class T>
class MyHeap {
    char flags;
    T *A;
    int maxSize;
    int size;
    
    bool isGreatest(int i, int j);
    void swap(int i, int j);
public:
    MyHeap(int maxSize,char flags=0);
    ~MyHeap();
    T top();
    T pop();
    void insert(T key);
    int getSize();
    void heapify(int i);
    
    //the new key must be greater or equal for max_heap, and smaller or equal for min_heap
    void updateKey(int i, T k); 
};

template<class T>
MyHeap<T>::MyHeap(int maxSize, char flags) {
    this->maxSize = maxSize;
    this->flags = flags;
    this->A = new T[maxSize];
    this->size = 0;
}

template<class T>
MyHeap<T>::~MyHeap() {
    delete [] A;
}

//compares elements 1 indexed as in Cormen
template<class T>
bool MyHeap<T>::isGreatest(int i, int j) {
    if (flags & MAX_HEAP)
        return A[i-1] > A[j-1];
    else
        return A[j-1] > A[i-1];
}

template<class T>
void MyHeap<T>::swap(int i, int j) {
    T tmp = A[i-1];
    A[i-1] = A[j-1];
    A[j-1] = tmp;
}

template<class T>
T MyHeap<T>::top() {
    return A[0];
}

template<class T>
int MyHeap<T>::getSize() {
    return size;
}

template<class T>
void MyHeap<T>::heapify(int i) {
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

template<class T>
T MyHeap<T>::pop() {
    if (size == 0) throw "Heap datastructure underflow";
    T max1 = A[0];
    A[0] = A[size-1];
    size--;
    heapify(1);
    return max1;
}

template<class T>
void MyHeap<T>::updateKey(int i, T key) {
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

template<class T>
void MyHeap<T>::insert(T key) {
    size++;
    A[size-1] = key;
    updateKey(size,key);
}

int main() {
    MyHeap<int> mh(100,MIN_HEAP);
    srand(0);
    for (int i=0; i<100; i++) {
        mh.insert(rand()%10000);
    }
    //mh.updateKey(20,9999);
    for (int i=0; i<20; i++) {
        printf("%d\n",mh.pop());
    }
}


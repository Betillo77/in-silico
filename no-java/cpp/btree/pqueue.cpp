
#include "pqueue.h"

//heap macros
#define PARENT(i) ((i)/2)
#define LEFT(i) (2*(i))
#define RIGHT(i) (2*(i) + 1)

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

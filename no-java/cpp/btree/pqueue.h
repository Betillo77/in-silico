
#ifndef PQUEUE_SEB
#define PQUEUE_SEB

//heap flags
#define MIN_HEAP 0
#define MAX_HEAP 1

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

#endif

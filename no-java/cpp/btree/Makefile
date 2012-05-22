FLAGS=-g

testSwap: testSwap.o btree.o MinHeap.o
	g++ $(FLAGS) btree.o MinHeap.o testSwap.o -o testSwap

testSwap.o: testSwap.cpp
	g++ $(FLAGS) -c testSwap.cpp

btree.o: btree.cpp
	g++ $(FLAGS) -c btree.cpp

minHeapTest: MinHeap_test.o MinHeap.o
	g++ $(FLAGS) MinHeap_test.o MinHeap.o -o minHeapTest

MinHeap_test.o: MinHeap_test.cpp
	g++ $(FLAGS) -c MinHeap_test.cpp

MinHeap.o: MinHeap.cpp
	g++ $(FLAGS) -c MinHeap.cpp 

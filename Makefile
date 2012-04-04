VERSION=1
RELEASE=0.1

all: libutpbn.so

bnlib.o: bnlib.c
	gcc -fpic -g -c -Wall bnlib.c
	
libutpbn.so: bnlib.o
	gcc -shared -Wl,-soname,libutpbn.so.1 -o libutpbn.so.$(VERSION).$(RELEASE) bnlib.o -lc
	
factorial: factorial.c libutpbn.so
	gcc -o factorial factorial.c -lutpbn
	
clean:
	rm *.o
	rm *.so.*

install: libutpbn.so bnlib.h
	cp libutpbn.so.$(VERSION).$(RELEASE) /usr/local/lib/libutpbn.so.$(VERSION)
	ln -s libutpbn.so.$(VERSION) libutpbn.so
	ln -s libutpbn.so.$(VERSION) libutpbn.so.$(VERSION).$(RELEASE)
	cp bnlib.h /usr/local/include

uninstall:
	rm /usr/local/lib/libutpbn.so*
	rm /usr/local/include/bnlib.h

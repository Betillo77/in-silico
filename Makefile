VERSION=1
RELEASE=0.1
DIR=/usr/local/lib
FLAGS=-O2

all: libutpbn.so

bnlib.o: bnlib.c
	gcc -fpic $(FLAGS) -c -Wall bnlib.c
	
libutpbn.so: bnlib.o
	gcc -shared -Wl,-soname,libutpbn.so.$(VERSION) -o libutpbn.so.$(VERSION).$(RELEASE) bnlib.o -lc
	
factorial: factorial.c libutpbn.so
	gcc -o factorial factorial.c -lutpbn
	
clean:
	rm *.o
	rm *.so.*

install: libutpbn.so bnlib.h
	cp libutpbn.so.$(VERSION).$(RELEASE) $(DIR)/libutpbn.so.$(VERSION)
	ln -s $(DIR)/libutpbn.so.$(VERSION) $(DIR)/libutpbn.so
	ln -s $(DIR)/libutpbn.so.$(VERSION) $(DIR)/libutpbn.so.$(VERSION).$(RELEASE)
	cp bnlib.h /usr/local/include
	ldconfig

uninstall:
	rm $(DIR)/libutpbn.so*
	rm /usr/local/include/bnlib.h
	ldconfig

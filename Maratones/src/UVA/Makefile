CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		check.o

LIBS =

TARGET =	check.exe

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)

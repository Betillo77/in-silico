CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		ballots.o

LIBS =

TARGET =	ballots.exe

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)

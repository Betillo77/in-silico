CXXFLAGS =	-O2 -g -Wall -fmessage-length=0

OBJS =		scoreboard.o

LIBS =

TARGET =	scoreboard.exe

$(TARGET):	$(OBJS)
	$(CXX) -o $(TARGET) $(OBJS) $(LIBS)

all:	$(TARGET)

clean:
	rm -f $(OBJS) $(TARGET)

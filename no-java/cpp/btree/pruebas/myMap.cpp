
#include <cstdio>
#include <cstdlib>
#include <map>

using namespace std;

class Dato{
    long dir;
    long date;
    int memdir;
    
    public:
    Dato();
    Dato(long dir, long date, int memdir);
    bool operator < (const Dato &a);
    bool operator > (const Dato &a);
    bool operator == (const Dato &a);
    long getDate();
};

Dato::Dato(){}

Dato::Dato(long dir, long date, int memdir){
    this->dir = dir;
    this->date = date;
    this->memdir = memdir;
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

long Dato::getDate(){
    return this->date;
}


int main(){
    Dato mio(10, 50, 20);
    Dato array[10];
    printf("%d\n", mio.getDate());
    return 0;
}

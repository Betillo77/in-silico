#include <iostream>
#include <string>

using namespace std;

int dos[13];

void generarSiguientes(int *actuales, int *nuevos)
{
     int vecinoAct = 0;
     while(vecinoAct = *actuales++)
     {                  
              for(int i = 0; i < 13; i++)
              {
                     if((vecinoAct % dos[i]))
                     {
                          *nuevos++ = vecinoAct + dos[i];
                     }        
              }
     }
     *nuevos = 0;
}

int main()
{
    dos[0] = 1;
    for(int i = 1; i < 13; i++)
    {
          dos[i] = dos[i - 1] * 2;
    }
    int *nuevos = new int[dos[2]];
    generarSiguientes(dos, nuevos);
    int abb[13];
    int i = 12;
    cout << endl;
    while(*nuevos)
    {
           i = 12;
           while(*nuevos != 0)
           {
               abb[i--] = *nuevos % 2;
               *nuevos /= 2;
           }
           for(; i > -1; i--)
                 abb[i] = 0;
           for(int i = 0; i < 13; i++)
                      cout << abb[i];
           cout << endl;
           nuevos++;
    }
}

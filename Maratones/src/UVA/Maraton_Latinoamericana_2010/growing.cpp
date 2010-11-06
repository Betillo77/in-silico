#include<iostream>
#include<algorithm>
#include<cstring>
#define MAX(X,Y) ((X) > (Y) ? (X) : (Y))

using namespace std;

struct Palabra
{
   char *s;
   int tam;
   int dp;

   Palabra()
   {
       s = new char[1001];
   }

   bool operator<(const Palabra otra) const
   {
       return tam < otra.tam;
   }


};

   int indexOf(char *source, int sourceOffset, int sourceCount,
                      char *target, int targetOffset, int targetCount,
                      int fromIndex) {
       if (fromIndex >= sourceCount) {
           return (targetCount == 0 ? sourceCount : -1);
       }
       if (fromIndex < 0) {
           fromIndex = 0;
       }
       if (targetCount == 0) {
           return fromIndex;
       }

       char first  = target[targetOffset];
       int max = sourceOffset + (sourceCount - targetCount);

       for (int i = sourceOffset + fromIndex; i <= max; i++) {
           /* Look for first character. */
           if (source[i] != first) {
               while (++i <= max && source[i] != first);
           }

           /* Found first character, now look at the rest of v2 */
           if (i <= max) {
               int j = i + 1;
               int end = j + targetCount - 1;
               for (int k = targetOffset + 1; j < end && source[j] ==
                        target[k]; j++, k++);

               if (j == end) {
                   /* Found whole string. */
                   return i - sourceOffset;
               }
           }
       }
       return -1;
   }

bool comparar(const Palabra *a, const Palabra *b)
{
   return a->tam < b->tam;
}

Palabra *palabras[10000];

int main()
{
   int n;
   for(int i = 0; i < 10000; i++)
   {
       palabras[i] = new Palabra();
   }
   while(true)
   {
       cin >> n;
       if(n == 0)
           break;
       for(int i = 0; i < n; i++)
       {
           cin >> palabras[i]->s;
           palabras[i]->tam = strlen(palabras[i]->s);
           palabras[i]->dp = 1;
       }
       sort(palabras, palabras + n, comparar);
       int mejor = 0;
       for(int i = 0; i < n; i++)
       {
           int siguienteDp = palabras[i]->dp + 1;
           int tamActual = palabras[i]->tam;
           char *palabraAct = palabras[i]->s;
           for(int j = i + 1; j < n; j++)
           {
               if(tamActual >= palabras[j]->tam || palabras[j]->dp >= siguienteDp)
                   continue;
               if(indexOf(palabras[j]->s, 0, palabras[j]->tam, palabraAct, 0, tamActual, 0) != -1)
                   palabras[j]->dp = siguienteDp;

           }
           mejor = MAX(mejor, palabras[i]->dp);
       }
       cout << mejor << endl;
   }
}

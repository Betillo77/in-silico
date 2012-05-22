 
#include <cstdio>
#include <cstdlib>
#include <set>
 
using namespace std;
 
int main() {
    set<int> s;
    int n,tmp,ans=0;
    scanf("%d",&n);
    while (n--) {
        scanf("%d",&tmp);
        if (s.insert(tmp).second) ans++;        
    }
    printf("%d\n",ans);
}
 

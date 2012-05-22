#include "MinHeap.cpp"
#include "btree.cpp"

int main(){
    int num, tmp;
    BTree<int> tree(100,"hola.bt",REP_TREE);
    scanf("%d",&num);
    dir tmpdir;
    int ans=0;
    while(num--){
        scanf("%d",&tmp);
        if(tree.search(tmp,&tmpdir) == -1){
            ans++;
            tree.insert(tmp);
        }
    }
    printf("%d\n",ans);
    return 0;
}

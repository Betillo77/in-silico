#include "btree.h"

int main(){
	try {
		int num, tmp;
		BTree<int,30> tree(0,"hola.bt",APP_TREE | MEM_TREE);
		scanf("%d",&num);
		//num=100000;
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
		tree.save();
    } catch (const char *ex) {
    	printf("Error: %s\n",ex);
    }
    return 0;
}


#include "btree.cpp"

dir pdirs[10];

void testSwap() {
    PageSwap<int> ps(3,"hola.bt");
    
    pdirs[0] = ps.allocateNode();
    Page<int> *p1 = ps.diskRead(pdirs[0]);
    p1->n=2; p1->leaf='1';
    ps.diskWrite(pdirs[0]);
    ps.debug1(pdirs[0]);
    
    pdirs[1] = ps.allocateNode();
    p1 = ps.diskRead(pdirs[1]);
    p1->n=1; p1->leaf='2';
    ps.diskWrite(pdirs[1]);
    ps.debug1(pdirs[1]);
    
    pdirs[2] = ps.allocateNode();
    p1 = ps.diskRead(pdirs[2]);
    p1->n=3; p1->leaf='a';
    ps.diskWrite(pdirs[2]);
    ps.debug1(pdirs[2]);
    
    ps.debug1(pdirs[0]);
    ps.debug1(pdirs[1]);
    ps.debug1(pdirs[2]);
    
    Page<int> *p2 = ps.diskRead(pdirs[1]);
    printf("%d %c\n",p2->n,p2->leaf);
    p2 = ps.diskRead(pdirs[0]);
    printf("%d %c\n",p2->n,p2->leaf);
    p2 = ps.diskRead(pdirs[2]);
    printf("%d %c\n",p2->n,p2->leaf);
    
    pdirs[3] = ps.allocateNode();
    p1 = ps.diskRead(pdirs[3]);
    p1->n=10; p1->leaf='b';
    ps.diskWrite(pdirs[3]);
    
    p2 = ps.diskRead(pdirs[1]);
    printf("%d %c\n",p2->n,p2->leaf);
    p2 = ps.diskRead(pdirs[0]);
    printf("%d %c\n",p2->n,p2->leaf);
    p2 = ps.diskRead(pdirs[2]);
    printf("%d %c\n",p2->n,p2->leaf);
    p2 = ps.diskRead(pdirs[3]);
    printf("%d %c\n",p2->n,p2->leaf);
}

int main() {
    
    BTree<int> tree(3,"hola.bt",REP_TREE);
    printf("%d %d\n",tree.getRoot()->n,tree.getRoot()->leaf);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genericos.reinforcement;

/**
 *
 * @author santiago
 */
public class Par<T1 extends Comparable<? super T1>,
        T2 extends Comparable<? super T2>> implements Comparable<Par<T1,T2>>{
    T1 val1;
    T2 val2;

    public Par(T1 v1, T2 v2){
        val1=v1;
        val2=v2;
    }

    public int compareTo(Par<T1,T2> o) {
        int cmp = val1.compareTo(o.val1);
        if (cmp == 0){
            return val2.compareTo(o.val2);
        }
        return cmp;
    }

}

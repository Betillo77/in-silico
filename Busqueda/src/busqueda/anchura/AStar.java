/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package busqueda.anchura;

import java.util.*;

/**
 * Clase que encapsula el algoritmo de busqueda en primero anchura con
 * heurísticas A*, se genera un arbol parcial y se retorna el estado que
 * objetivo puediendo seguir el camino al padre
 */
public class AStar {
    
    /**
     * Retorna el nodo de destino luego de estar ubicado en el arbol
     * para devolverse al nodo de origen y conocer el camino.
     * @param org
     * @param dest
     * @return
     */
    AStarState resolver(AStarState org, AStarState dest) {
        MyList v = new MyList();
        MyList nv = new MyList();
        nv.add(new MyState(org));
        while (!nv.isEmpty()) {
            MyState act = nv.mejor();
            v.add(act);
            LinkedList<AStarState> hijos = act.state.sigEstados();
            for (AStarState x : hijos) {
                x.setPadre(act.state);
                if (x.getKey().compareTo(dest.getKey()) == 0)
                    return x;
                if (!nv.contains(x) && !v.contains(x))
                    nv.add(new MyState(x));
            }
        }
        return null;
    }
    
    class MyState implements Comparable<MyState> {
        public AStarState state; 
        public double g; // cuanto costo lleva
        
        public MyState(AStarState state) {
            this.state = state;
        }
        
        /**
         * Costo total estimado del origen al destino pasando por el
         * camino y el estado actual
         * @return
         */
        public double getF() {
            return g + state.getH();
        }

        public int compareTo(MyState o) {
            if (this.getF() == o.getF()) return 0;
            return ((this.getF() - o.getF()) > 0) ? 1 : -1;
        }
    }
    
    class MyList {
    
        PriorityQueue<MyState> pq;
        TreeMap<Comparable, AStarState> arbol;

        public MyList() {
            pq = new PriorityQueue<MyState>();
            arbol = new TreeMap<Comparable, AStarState>();
        }

        /**
         * Adicona al final de la lista
         * @param key Identificador unico del estado a agregar
         * @param valor Estado a agregar
         */
        public void add(MyState s) {
            AStarState e = s.state;
            pq.add(s);
            arbol.put(e.getKey(), e);
        }

        /**
         * Retorna el mejor elemento de la lista y lo elimina
         * @return
         */
        public MyState mejor() {
            MyState e = pq.poll();
            if (e==null) return null;
            arbol.remove(e.state.getKey());
            return e;
        }

        public AStarState getEstado(Object key) {
            return arbol.get(key);
        }

        public boolean contains(AStarState e) {
            return arbol.containsKey(e.getKey());
        }

        public boolean isEmpty() {
            return pq.isEmpty();
        }
    }
  
}

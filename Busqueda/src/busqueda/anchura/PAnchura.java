/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package busqueda.anchura;

import java.util.*;

/**
 * Clase que encapsula el algoritmo de busqueda en primero anchura, se puede
 * generar el árbol una única vez y luego buscar en el.
 */
public class PAnchura {
    
    MyList v; //estados visitados
    MyList sv; //estados sin visitar
    
    public Estado getNodo(Object key) {
        return v.getEstado(key);
    }    
    
    public void genArbol(Estado org) {
        v = new MyList();
        sv = new MyList();
        sv.add(org);
        while (!sv.isEmpty()) {
            Estado act = sv.primero();
            LinkedList<Estado> sigEst = act.sigEstados();
            v.add(act);
            for (Estado e : sigEst) {
                if (!v.contains(e) && !sv.contains(e))
                    sv.add(e);
            }
        }
    }
    
    class MyList {
    
        LinkedList<Estado> lista;
        TreeMap<Object, Estado> arbol;

        public MyList() {
            lista = new LinkedList<Estado>();
            arbol = new TreeMap<Object, Estado>();
        }

        /**
         * Adicona al final de la lista
         * @param key Identificador unico del estado a agregar
         * @param valor Estado a agregar
         */
        public void add(Estado e) {
            lista.add(e);
            arbol.put(e.getKey(), e);
        }

        /**
         * El primer elemento de la lista y lo elimina
         * @return
         */
        public Estado primero() {
            Estado e = lista.pollFirst();
            if (e==null) return null;
            arbol.remove(e.getKey());
            return e;
        }

        public Estado getEstado(Object key) {
            return arbol.get(key);
        }

        public boolean contains(Estado e) {
            return arbol.containsKey(e.getKey());
        }

        public boolean isEmpty() {
            return lista.isEmpty();
        }
    }

    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package busqueda.anchura;

import java.util.LinkedList;

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
    
}

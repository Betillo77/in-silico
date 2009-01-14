/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package busqueda.anchura;

import java.util.*;

/**
 *
 * @author sebastian
 */
public class MyList {
    
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

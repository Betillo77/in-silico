/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package busqueda.anchura;

import java.util.*;

/**
 * Interfaz que define que debe características mínimas debe tener un estado
 * para ser resuelto por el algoritmo de primero anchura
 */
public interface Estado {

    /**
     * Retorna una lista con los estado siguientes al estado actual
     * @return
     */
    public LinkedList<Estado> sigEstados();
    
    /**
     * Retorna un valor que sea único para cada estado, su clave primaria
     * @return
     */
    public Object getKey();
    
    
    public Estado getPadre();
    public void setPadre(Estado padre);
}

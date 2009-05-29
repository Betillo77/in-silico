/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package busqueda.anchura;

import java.util.*;

/**
 * Interfaz que define que debe características mínimas debe tener un estado
 * para ser resuelto por el algoritmo A*
 */
public interface AStarState extends Estado {

    /**
     * Retorna una lista con los estado siguientes al estado actual
     * @return
     */
    public LinkedList sigEstados();
    
    /**
     * Retorna un valor que sea único para cada estado, su clave primaria
     * @return
     */
    public Comparable getKey();
    
    /**
     * Funcion heurística, retorna un valor estimado de cuanto le hace falta
     * al estado actual para llegar al estado objetivo. No se debe sobreestimar,
     * es decir, sea H* la heuristica y H la función exacta de cuanto falta,
     * H* sea menor o igual que H.
     * @return
     */
    public double getH();
    
    public AStarState getPadre();
    public void setPadre(AStarState padre);
}

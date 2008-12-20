/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 * Esta interfaz encapsula el funcionamiento requerido de todo lo que estamos
 * llamando función para los propositos de derivar y evaluar la función en
 * un punto.
 */
public interface Funcion {
    
    /**
     * Retorna el valor resultado de remplazar todas las variables de la funcion
     * por los valores pasados como argumento
     * @param Table Pares Nombre de variable, Valor de la variable
     * @return Un valor real resultado de el reemplazamiento
     */
    double evaluar(Hashtable<String,Integer> Table);
    
    
    Funcion derivar(String var);
    
}

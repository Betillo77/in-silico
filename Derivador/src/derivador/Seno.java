/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 *
 */
public class Seno implements Funcion {
    
    Funcion arg;
    
    public Seno(Funcion argumento) {
        arg = argumento;
    }

    public double evaluar(Hashtable<String, Integer> Table) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Funcion derivar(String var) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

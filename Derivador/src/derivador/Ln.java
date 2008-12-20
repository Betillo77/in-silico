/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 *
 * @author sebastian
 */
public class Ln implements Funcion{
    Funcion v;
    public Ln(Funcion v){
        this.v=v;
    }

    public double evaluar(Hashtable<String, Integer> Table) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Funcion derivar(String var) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

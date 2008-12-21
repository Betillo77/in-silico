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
public class Numero implements Funcion {
    //atributos
    double num;
    
    public Numero(double num) {
        this.num=num;
    }

    public double evaluar(Hashtable<String, Double> Table) {
        return num;
    }

    public Funcion derivar(String var) {
        return new Numero(0);
    }
    
    @Override
    public String toString() {
        return num+"";
    }

}


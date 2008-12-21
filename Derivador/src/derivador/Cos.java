/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 * @autores daniela, juan david y german
 */
public class Cos implements Funcion {
    
    Funcion arg;
    
    public Cos(Funcion argumento) {
        arg = argumento;
    }

    
    public double evaluar(Hashtable<String, Double> Table) {
        
        double evaluado = arg.evaluar(Table);
        return Math.cos(evaluado);
    }

    public Funcion derivar(String var) {
        
        Funcion derArgumento = arg.derivar(var);
        Funcion SenNeg = new Mult(new Seno(arg),new Numero(-1.0));
        Funcion derivado = new Mult(SenNeg,derArgumento);
        return derivado;
    }

    @Override
    public String toString() {
        return "Cos(" + arg.toString() + ")";
    }

}

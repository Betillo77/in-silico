/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 * @autores daniela, juan david y german
 */
public class Seno implements Funcion {
    
       
    Funcion arg;
    
    public Seno(Funcion argumento) {
        arg = argumento;
    }

    
    public double evaluar(Hashtable<String, Double> Table) {
        
        double evaluado = arg.evaluar(Table);
        return Math.sin(evaluado);
    }

    public Funcion derivar(String var) {
        
        Funcion derArgumento = arg.derivar(var);
        Funcion derivado = new Mult(new Cos(arg),derArgumento);
        return derivado;
    }
    
    @Override
    public String toString() {
        return "Sen(" + arg.toString() + ")";
    }

}
   

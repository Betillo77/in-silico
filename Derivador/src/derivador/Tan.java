/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 * @autores daniela, juan david y german
 */
public class Tan implements Funcion {

    Funcion arg;
    
    public Tan(Funcion argumento) {
        arg = argumento;
    }

    
    public double evaluar(Hashtable<String, Double> Table) {
        
        double evaluado = arg.evaluar(Table);
        return Math.tan(evaluado);
    }

    public Funcion derivar(String var) {
        
        Funcion derArgumento = arg.derivar(var);
        Funcion Sec2 = new Potencia(new Div(new Numero(1), new Cos(arg)), new Numero(2));
        Funcion derivada = new Mult(Sec2, derArgumento);
        return derivada;
    }
    
    @Override
    public String toString() {
        return "Tan(" + arg.toString() + ")";
    }

    public Funcion simplificar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 *
 * @author JUAN DAVID
 */
public class Ln implements Funcion{
    Funcion arg;
    public Ln(Funcion arg){
        this.arg=arg;
    }

    public double evaluar(Hashtable<String, Double> Table) {
        double evaluado = arg.evaluar(Table);
        return Math.log(evaluado);          // Ln(evaluado)
    }

    public Funcion derivar(String var) {
        Funcion derArgumento = arg.derivar(var);  //derivada de var
        Funcion dln = new Potencia(arg, new Numero(-1)); //  1/var
        Funcion derivada = new Mult(dln, derArgumento); 
        return derivada;  
    }
    
    @Override
      public String toString() {
        return "Ln(" + arg.toString() + ")";
    }

    public Funcion simplificar() {
        arg=arg.simplificar();
        if(arg instanceof Numero){
            Numero a=(Numero)arg;
            if(a.num == 1)
                return new Numero(0.0);
        }
        return this;
    }

}

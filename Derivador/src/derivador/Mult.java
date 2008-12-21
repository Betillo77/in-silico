/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 * @autores daniela, juan david y german
 */
public class Mult implements Funcion{
    Funcion izq,der;
     
    public Mult(Funcion izq,Funcion der){
        this.izq=izq;
        this.der=der;
    }  
    
    public double evaluar(Hashtable<String, Double> Table) {
        
        double evalArg1  = izq.evaluar(Table);
        double evalArg2 = der.evaluar(Table);
        return evalArg1 * evalArg2;
    }

    public Funcion derivar(String var) {
        
        Funcion LadoIzq = new Mult(izq.derivar(var), der);
        Funcion LadoDer = new Mult(izq, der.derivar(var));
        Funcion derivada = new Suma(LadoIzq, LadoDer);
        return derivada;
    }
    
     @Override
    public String toString() {
        return  "(" + izq.toString() + ")" + "*" + "(" + der.toString() + ")";
    }


}
 
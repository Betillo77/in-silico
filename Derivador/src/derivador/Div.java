/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 * @autores daniela, juan david y german
 */
public class Div implements Funcion{
    
    Funcion Num,Den;
     
     public Div(Funcion Num,Funcion Den){
         this.Num = Num;
         this.Den = Den;
     }

    public double evaluar(Hashtable<String, Double> Table) {
        
        double evalArg1 = Num.evaluar(Table);
        double evalArg2 = Den.evaluar(Table);
        return evalArg1 / evalArg2;
    }

    public Funcion derivar(String var) {
        
        Funcion LadoIzq = new Mult(Num.derivar(var), Den);
        Funcion LadoDer = new Mult(Num, Den.derivar(var));
        Funcion Abajo = new Potencia(Num, new Numero(2));
        Funcion Arriba = new Suma(LadoIzq, new Mult(new Numero(-1.0), LadoDer));
        Funcion derivada = new Mult(Arriba, new Potencia(Abajo, new Numero(-1)));
        return derivada;
    }
    
     @Override
    public String toString() {
        return  "(" + Num.toString() + ")" + "/" + "(" + Den.toString() + ")";
    }

}

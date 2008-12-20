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
 class Potencia implements Funcion{
     
     Funcion izq,der;
     
     public Potencia(Funcion izq,Funcion der){
         this.izq=izq;
         this.der=der;
     }

    public double evaluar(Hashtable<String, Integer> Table) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Funcion derivar(String var) {
        Funcion A1=new Potencia(izq,der);
        Funcion A2=der.derivar(var);
        Funcion A3=new Mult(A1,new Mult(A2,new Ln(izq)));
        Funcion A4=new Mult(new Potencia(izq,der),new Mult(new Div(der,izq),izq.derivar(var)));
        return new Suma(A3,A4);        
    }


 
}

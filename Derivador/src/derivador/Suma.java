/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 * @autores daniela, juan david y german
 */
public class Suma implements Funcion{
    Funcion izq,der;
     
     public Suma(Funcion izq,Funcion der){
         this.izq=izq;
         this.der=der;
     }

    public double evaluar(Hashtable<String, Double> Table) {
        
        double evalArg1 = izq.evaluar(Table);
        double evalArg2 = der.evaluar(Table);
        return evalArg1 + evalArg2;
    }

    public Funcion derivar(String var) {
        
        Funcion derivada = new Suma(izq.derivar(var), der.derivar(var));
        return derivada;
    }
    
     @Override
    public String toString() {
        return  "(" + izq.toString() + ")" + "+" + "(" + der.toString() + ")";
    }

    public Funcion simplificar() {
        izq=izq.simplificar();
        der=der.simplificar();
        if(izq instanceof Numero && der instanceof Numero)
            return new Numero(((Numero)izq).num+((Numero)der).num);
        if (izq instanceof Numero) {
	    Numero i = (Numero)izq;
	    if (i.num == 0) return der;
	} else if (der instanceof Numero) {
	    Numero d = (Numero)der;
	    if (d.num == 0) return izq;
	} 
	return this;						
    }
    

}

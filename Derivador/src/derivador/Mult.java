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

    public Funcion simplificar() {
	izq = izq.simplificar();
	der = der.simplificar();
	if (izq instanceof Numero && der instanceof Numero)
	    return new Numero(((Numero)izq).num * ((Numero)der).num);
	if (izq instanceof Numero) {
	    Numero i = (Numero)izq;
	    if (i.num == 0) return new Numero(0.0);
            else if (i.num == 1) return der;
	} else if (der instanceof Numero) {
	    Numero d = (Numero)der;
	    if (d.num == 0) return new Numero(0.0);
	    else if (d.num == 1) return izq;
	} 
	return this;						
    }
}
 

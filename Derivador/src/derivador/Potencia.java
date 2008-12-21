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

    public double evaluar(Hashtable<String,Double> Table) {
        
        double r=0.0;
        double ln=new Ln(izq).evaluar(Table);
        for(int i=0;i<20;i++){
            double p1 = Math.pow(ln, i);
            double p2 = new Potencia(der, new Numero(i)).evaluar(Table);
            double fac=Factorial(i);
            double aux=(p1*p2)/fac;
            r=r+aux; 
        }
        
        return r;      
      
       
    }
    
   private double Factorial(int n){
       double fac=1.0;
       for(int i=1;i<=n;i++)
           fac=fac*i;
       
       if(n==0)
            return 1;
       else
            return fac;
   } 
    
    

    public Funcion derivar(String var) {
        Funcion A1 = new Potencia(izq,der);
        Funcion A2 = der.derivar(var);
        Funcion A3 = new Mult(A1,new Mult(A2,new Ln(izq)));
        Funcion A4 = new Mult(new Potencia(izq,der),new Mult(new Div(der,izq),izq.derivar(var)));
        return new Suma(A3,A4);        
    }
    
    
      @Override
    public String toString() {
        return  "(" + izq.toString() + ")" + "^" + "(" + der.toString() + ")";
    }



 
}


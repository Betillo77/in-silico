/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.Hashtable;

/**
 *
 * @author luiscar
 */
public class Variable implements Funcion {
    //atributos
    String nameVar;
    
    public Variable(String nameVar){
        this.nameVar=nameVar;
    }

    public double evaluar(Hashtable<String, Double> Table) {
        return Table.get(nameVar);        
    }

    public Funcion derivar(String var) {
        if (nameVar.equals(var)) {
            return new Numero(1);
        } else {
            return new Numero(0);
        }        
    }   
      @Override
    public String toString() {
        return nameVar;
    }

    public Funcion simplificar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}


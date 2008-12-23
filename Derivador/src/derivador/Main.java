/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.util.*;
/**
 *
 * @author JUAN DAVID
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Potencia a=new Potencia(new Variable("x"), new Numero(8));
        
        Hashtable<String,Double> p=new Hashtable<String, Double>();
        p.put("x", 3.0);
        System.out.print(a.evaluar(p));
        
    }

}

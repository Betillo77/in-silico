/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.io.*;
import java.util.*;
import compiler.*;



/**
 *
 * @author JUAN DAVID
 */
public class Main {

    static void prcomp() {
        try {
            parser p = new parser(new scanner(new FileReader("hola.txt")));
            p.parse();
        } catch (Error er) {
            System.out.println("Error sintáctico: " + er.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        prcomp();
    }

    static void deriv() {
        Potencia a=new Potencia(new Variable("x"), new Numero(8));

        Hashtable<String,Double> p=new Hashtable<String, Double>();
        p.put("x", 3.0);
        System.out.print(a.evaluar(p));
    }

}

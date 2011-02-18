/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package derivador;

import java.io.*;
import java.util.*;
import compiler.*;
import java_cup.runtime.Symbol;



/**
 *
 * @author JUAN DAVID
 */
public class Main {

    static void prcomp() {
        try {
            Hashtable<String,Double> tab=new Hashtable<String, Double>();
            tab.put("x", 2.0);
            parser p = new parser(new scanner(new FileReader("hola.txt")));
            p.parse();
            LinkedList<Funcion> funciones = p.getFunciones();
            System.out.println("Evaluado en x=2:");
            for (Funcion f : funciones) {
                System.out.println(f.evaluar(tab));
            }
            System.out.println("Evaluando la derivada en x=2:");
            for (Funcion f : funciones) {
                System.out.println(f.derivar("x").evaluar(tab));
            }
        } catch (Error er) {
            System.out.println("Error sintáctico: " + er.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //ex.printStackTrace();
        }
    }

    static void prlex() {
        try {
            scanner s = new scanner(new FileReader("hola.txt"));
            while (true) {
                Symbol sym = s.next_token();
                if (sym.sym == 0) break;
                System.out.println(sym.sym + ", " + sym.value);
            }
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
        //prlex();
    }

    static void deriv() {
        Potencia a=new Potencia(new Variable("x"), new Numero(8));

        Hashtable<String,Double> p=new Hashtable<String, Double>();
        p.put("x", 3.0);
        System.out.print(a.evaluar(p));
    }

}

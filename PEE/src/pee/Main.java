/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import pee.compilador.*;


/**
 *
 * @author sebas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            scanner s = new scanner(new FileInputStream("test.pee"));
            parser p = new parser(s);
            p.parse();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

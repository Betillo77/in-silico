/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package appsbusqueda;

import apps.anchura.*;
import busqueda.anchura.*;

/**
 *
 * @author sebastian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PAnchura pa = new PAnchura();
        PuzzleState org = new PuzzleState(new byte[]{1,2,3,4,5,6,7,8,9});
        pa.genArbol(org);
        PuzzleState dest = new PuzzleState(new byte[]{2,3,1,4,5,6,8,9,7});
        PuzzleState act = (PuzzleState)pa.getNodo(dest.getKey());
        if (act == null)
            System.out.println("No tiene sol");
        else {
            while (act.compareTo(org) != 0) {
                System.out.println(act);
                act = (PuzzleState)act.getPadre();
            }
        }
    }

}

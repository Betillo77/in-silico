/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.Arbol;

import pee.intermedio.Intermedio;
import java.util.LinkedList;

/**
 * Cualquier cosa que retorne un valor
 * @author sebas
 */
public interface Operacion extends Statment {

    public void genCod(int sp, LinkedList<Intermedio> cod);

}

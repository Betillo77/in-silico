/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.Arbol;

import java.util.LinkedList;
import pee.intermedio.*;

/**
 *
 * @author sebas
 */
public class Binaria implements Operacion {
    Operacion izq, der;
    int operacion;

    public void genCod(int sp, LinkedList<Intermedio> cod) {
        izq.genCod(sp, cod);
        der.genCod(sp+1, cod);
        Registro dest = new Registro(Tipo.Temporal, sp);
        Registro r1 = new Registro(Tipo.Temporal, sp);
        Registro r2 = new Registro(Tipo.Temporal, sp+1);
        cod.add(new IBinaria(dest, r1, r2, operacion));
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.intermedio;

/**
 *
 * @author sebas
 */
public class IBinaria implements Intermedio {
    Registro dest, izq, der;
    int operacion;

    public IBinaria(Registro dest, Registro izq, Registro der, int operacion) {
        this.dest = dest;
        this.izq = izq;
        this.der = der;
        this.operacion = operacion;
    }
}

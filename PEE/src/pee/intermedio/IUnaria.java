/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.intermedio;

/**
 *
 * @author sebas
 */
public class IUnaria implements Intermedio {
    Registro dest;
    Registro org;
    int operacion;

    public IUnaria(Registro dest, Registro org, int operacion) {
        this.dest = dest;
        this.operacion = operacion;
        this.org = org;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.Arbol;

import java.util.*;
import pee.Constantes;
import pee.intermedio.*;

/**
 *
 * @author sebas
 */
public class Variable implements Operacion {
    int tipo;
    String nombre;
    Registro reg;

    //Static part
    static Contexto context;

    public static Variable get(String var) {
        return context.get(var);
    }

    public Variable(String nombre, Registro reg, int tipo) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.reg = reg;
    }

    public void genCod(int sp, LinkedList<Intermedio> cod) {
        Registro dest = new Registro(Tipo.Temporal, sp);
        IUnaria op = new IUnaria(dest, reg, Constantes.asignacion);
        cod.add(op);
    }

}

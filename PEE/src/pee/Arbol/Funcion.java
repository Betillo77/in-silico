/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.Arbol;

import java.util.*;
import pee.intermedio.Intermedio;

/**
 *
 * @author sebas
 */
public class Funcion {
    int tipo;
    String nombre;
    Hashtable<String,Variable> vars; //locales, parametros
    public LinkedList<Statment> stmt;
    LinkedList<Intermedio> cod;
    int sp;

    public Funcion(String nombre, int tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

}

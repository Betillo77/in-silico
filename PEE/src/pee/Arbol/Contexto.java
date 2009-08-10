/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.Arbol;

import java.util.*;
import pee.intermedio.Registro;
import pee.intermedio.Tipo;

/**
 *
 * @author sebas
 */
public class Contexto {
    public LinkedList<Hashtable<String, Variable>> pila;
    public Funcion act;
    int []regnum = new int[10];


    //Static
    public static Contexto context;

    static {
        context = new Contexto();
    }

    public static void setFuncion(String nom, int tipo) {
        context.act = new Funcion(nom, tipo);
        context.pila.push(new Hashtable<String, Variable>());
    }

    public static Funcion popFuncion() {
        Funcion x = context.act;
        context.act=null;
        context.pila.pop();
        context.regnum[Tipo.LocalVar.ordinal()] = 0;
        context.regnum[Tipo.Param.ordinal()] = 0;
        return x;
    }

    private Contexto() {
        pila = new LinkedList<Hashtable<String, Variable>>();
        //añadir el contexto global
        pila.add(new Hashtable<String, Variable>());
    }

    /**
     * Retorna la instancia de la clase variable si esta fue previamente
     * declarada o null si no.
     * @param varName Nombre de la variable
     * @return Instancia de var
     */
    public Variable get(String varName) {
        for (Hashtable<String, Variable> h : pila) {
            if (h.contains(varName)) {
                return h.get(varName);
            }
        }
        return null;
    }

    public Variable nueva(String varName, int tipo, Tipo tipoReg) {
        int rt = tipoReg.ordinal();
        Registro r = new Registro(tipoReg, regnum[rt]);
        Variable var = new Variable(varName, r, tipo);
        regnum[rt]++;
        return var;
    }
}

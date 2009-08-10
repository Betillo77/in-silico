/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.Arbol;

import java.util.LinkedList;
import pee.Constantes;
import pee.intermedio.*;

/**
 * Clase para representar literales enteros en el árbol de análisis sintáctico
 * @author sebas
 */
public class LitEnt implements Operacion {
    int valor;

    public LitEnt(int valor) {
        this.valor = valor;
    }

    public void genCod(int sp, LinkedList<Intermedio> cod) {
        Registro dest = new Registro(Tipo.Temporal, sp);
        cod.add(new IUnaria(dest, new Registro(Tipo.Inmediato, valor),
                Constantes.asignacion));
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.compilador;

/**
 * Clase que contiene metodos estaticos de uso general para todas las etapas del
 * compilador
 */
public class General {

    public static int parseInt(String entero) throws Error {
        long res = 0;
        char cad[] = entero.toCharArray();
        int i=0;
        if (cad[0] == '-') i=1;
        for (; i<cad.length; i++) {
            res *= 10;
            res += cad[i]-0x30;
            if (res > Integer.MAX_VALUE) {
                throw new Error("Se ha sobrepasado el límite máximo del " +
                        "entero: 2^32");
            }
        }
        if (cad[0] == '-') return (int)(-res);
        return (int)res;
    }
}

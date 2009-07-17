/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pee.compilador;

/**
 *
 * @author sebas
 */
public class ErrorLexico {

    int linea;
    String mensaje;
    String token;

    public ErrorLexico(int linea, String mensaje, String token) {
        this.linea = linea; this.mensaje = mensaje; this.token = token;
    }

    @Override
    public String toString() {
        return mensaje + ": " + token + "\nLinea: " + linea;
    }
}

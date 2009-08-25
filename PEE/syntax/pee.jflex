/* JFlex example: part of Java language lexer specification */

import java_cup.runtime.*;
import java.util.*;


/**
* This class is a simple example lexer.
*/



%%

%class scanner
%public
%unicode
%cup
%line
%column

%{

StringBuffer string = new StringBuffer();
LinkedList<ErrorLexico> errores = new LinkedList<ErrorLexico>();

private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
}

private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
}

private Symbol lexError(String mensaje) {
    ErrorLexico el = new ErrorLexico(yyline, mensaje, yytext());
    errores.add(el);
    return symbol(sym.ERRTKN, el);
}


%}

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Cualquiera = .|\n

NatLit = 0 | [1-9][0-9]*
EntLit = {NatLit} | "-" {NatLit}
DobLit = {EntLit} \. [0-9]+

Letra = [a-zA-ZñÑáéíóúÁÉÍÓÚ]
Identifier = {Letra}({Letra}|[0-9_])*

%%

/* keywords */

<YYINITIAL> "+" { return symbol(sym.MAS); }
<YYINITIAL> "-" { return symbol(sym.MENOS); }
<YYINITIAL> "*" { return symbol(sym.POR); }
<YYINITIAL> "/" { return symbol(sym.DIV); }
<YYINITIAL> "%" { return symbol(sym.MOD); }
<YYINITIAL> ";" { return symbol(sym.SEMI); }
<YYINITIAL> "," { return symbol(sym.COMA); }
<YYINITIAL> "(" { return symbol(sym.IPAR); }
<YYINITIAL> ")" { return symbol(sym.DPAR); }
<YYINITIAL> "<" { return symbol(sym.MENOR); }
<YYINITIAL> ">" { return symbol(sym.MAYOR); }
<YYINITIAL> "<=" { return symbol(sym.MENI); }
<YYINITIAL> ">=" { return symbol(sym.MAYI); }
<YYINITIAL> "==" { return symbol(sym.IGUAL); }
<YYINITIAL> "!=" { return symbol(sym.DIF); }
<YYINITIAL> "&&" { return symbol(sym.YLOG); }
<YYINITIAL> "||" { return symbol(sym.OLOG); }
<YYINITIAL> "!" { return symbol(sym.NOLOG); }
<YYINITIAL> "<<" { return symbol(sym.ROTI); }
<YYINITIAL> ">>" { return symbol(sym.ROTD); }
<YYINITIAL> "&" { return symbol(sym.YBIT); }
<YYINITIAL> "|" { return symbol(sym.OBIT); }
<YYINITIAL> "^" { return symbol(sym.XORBIT); }
<YYINITIAL> "si" { return symbol(sym.SI); }
<YYINITIAL> "entonces" { return symbol(sym.ENTONCES); }
<YYINITIAL> "mientras" { return symbol(sym.MIENTRAS); }
<YYINITIAL> "inicio" { return symbol(sym.INICIO); }
<YYINITIAL> "fin" { return symbol(sym.FIN); }
<YYINITIAL> "retornar" { return symbol(sym.RETORNAR); }
<YYINITIAL> "entero" { return symbol(sym.ENTERO); }
<YYINITIAL> "vacio" { return symbol(sym.VACIO); }

<YYINITIAL> {

/* literals */
{EntLit} 	{ return symbol(sym.ENTLIT,General.parseInt(yytext())); }
{Identifier} { return symbol(sym.ID, yytext()); }
{WhiteSpace}    { /* ignore */ }

/* Errores */
{EntLit}{Identifier} { return lexError("No es un identificador válido");}
{DobLit}        { return lexError("No se aceptan números de punto flotante"); }

}

/* error fallback */

/* error fallback */
.|\n    { return lexError("Caracter ilegal "); }


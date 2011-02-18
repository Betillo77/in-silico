/* JFlex example: part of Java language lexer specification */

import java_cup.runtime.*;


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

private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
}

private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
}

%}

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Cualquiera = .|\n

DecIntegerLiteral = 0 | [1-9][0-9]*
DecDoubleLiteral = {DecIntegerLiteral} \. [0-9]+ | {DecIntegerLiteral}

Letra = [a-zA-ZñÑáéíóúÁÉÍÓÚ]
Identifier = {Letra}({Letra}|[0-9_])*

%%

/* keywords */

<YYINITIAL> "/*" {Cualquiera}* "*/" { /*Ignore*/ }
<YYINITIAL> "+" { return symbol(sym.PLUS); }
<YYINITIAL> "-" { return symbol(sym.MINUS); }
<YYINITIAL> "*" { return symbol(sym.TIMES); }
<YYINITIAL> "/" { return symbol(sym.DIVIDE); }
<YYINITIAL> ";" { return symbol(sym.SEMI); }
<YYINITIAL> "(" { return symbol(sym.LPAREN); }
<YYINITIAL> ")" { return symbol(sym.RPAREN); }
<YYINITIAL> "sen" { return symbol(sym.SEN); }
<YYINITIAL> "cos" { return symbol(sym.COS); }
<YYINITIAL> "tan" { return symbol(sym.TAN); }
<YYINITIAL> "ln" { return symbol(sym.LN); }
<YYINITIAL> "^" { return symbol(sym.POW); }

<YYINITIAL> {

/* literals */
//{DecIntegerLiteral}	{ return symbol(sym.NUMBER,Double.parseDouble(yytext())); }
{DecDoubleLiteral} 	{ return symbol(sym.NUMBER,Double.parseDouble(yytext())); }
{Identifier} { return symbol(sym.VAR, yytext()); }
{WhiteSpace}    { /* ignore */ }

}

/* error fallback */

/* error fallback */
.|\n    { throw new Error("Caracter ilegal <"+
        yytext()+">"); }


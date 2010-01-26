print 'as'
from MJ.Lexer.Lexer import Lexer
from MJ.Parser.Parser import Parser
from MJ.SymTab.SymTab import *
from compilador.modelo import Compilador

# Funcion principal que se encarga de llamar los modulos, visitantes, y de
# generar la entrada salida

class Compiler(Compilador):

    def empezar(self, entrada):
        try:
            analizadorLexico = Lexer()
            analizadorLexico.input(entrada)
            analizadorSintactico = Parser(analizadorLexico.tokens)
            raiz = analizadorSintactico.parse(entrada)
            analizadorSemantico = VisitanteTabla()
            analizadorSemantico.visitarProgram(raiz)
            return raiz
        except BaseException, ex:
            try:
                linea = ex.mensaje
                return linea
            except BaseException:
                return 'Error desconocido'
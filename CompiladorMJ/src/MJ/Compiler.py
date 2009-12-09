import ply.lex
import ply.yacc
import re
import Error.LexicalError
import Error.SyntacticalError
import Error.SemanticError
from Lexer.Lexer import Lexer
from Parser.Parser import Parser
from SymTab.SymTab import *
from LIR.LIR import *

# Funcion principal que se encarga de llamar los modulos, visitantes, y de
# generar la entrada salida

def empezar():
    import sys
    archivoEntrada = open(sys.argv[1], 'r')
    analizadorLexico = Lexer()
    entrada = archivoEntrada.read()
    analizadorLexico.input(entrada)
    lineatokens = ''
    try:
        for token in iter(analizadorLexico.lexer.token, None):
            lineatokens += repr(token.type) + ' ' + repr(token.value) + ' ' + repr(token.lineno)
            lineatokens += '\n'
    except Error.LexicalError.LexicalError as error:
        lineatokens = error.mensaje
    archivoEntrada.close()
    analizadorSintactico = Parser(analizadorLexico.tokens)
    analizadorLexico.lexer.lineno = 1
    try:
        raiz = analizadorSintactico.parse(entrada)
        Parser.linea += 'Programa:\n'
        for clase in raiz:
            clase.imprimir()  
    except Error.SyntacticalError.SyntacticalError as error:
        Parser.linea = error.mensaje
    analizadorSemantico = VisitanteTabla()
    try:
        analizadorSemantico.visitarProgram(raiz)
    except Error.SemanticError.SemanticError as error:
        try:
            analizadorSemantico.linea = error.mensaje
            analizadorSemantico.linea += '\nEl error se dio en la clase: ' + analizadorSemantico.metodoActual.tabla.padre.nombre + ', metodo: ' + analizadorSemantico.metodoActual.tabla.nombre
        except BaseException:
            pass
    analizadorCodigo = VisitanteLir(analizadorSemantico.tablaPrincipal)
    analizadorCodigo.visitarProgram(raiz)
    dump_tokens = 0
    dump_ast = 0
    dump_symtab = 0
    dump_lir = 0
    for parametro in sys.argv:
        if parametro[0] == '-':
            if parametro == '-dump-tokens':
                dump_tokens = 1
            if parametro == '-dump-ast':
                dump_ast = 1
            if parametro == '-dump-symtab':
                dump_symtab = 1
            if parametro == '-dump-lir':
                dump_lir = 1   
    if(dump_tokens):
        archivoSalida = open(sys.argv[1][:-3] + '.tok', 'w')
        archivoSalida.write(lineatokens)
        print "Tokens fueron escritos a: %s" % (str(sys.argv[1][:-3] + '.tok'))
        archivoSalida.close()
    if(dump_ast):
        archivoSalida = open(sys.argv[1][:-3] + '.ast', 'w')
        archivoSalida.write(Parser.linea)
        print "Salida del AST fue escrita a: %s" % (str(sys.argv[1][:-3] + '.ast'))
        archivoSalida.close()
    if(dump_symtab):
        archivoSalida = open(sys.argv[1][:-3] + '.sym', 'w')
        archivoSalida.write(analizadorSemantico.linea)
        print "Salida de la tabla de simbolos fue escrita a: %s" % (str(sys.argv[1][:-3] + '.sym'))
        archivoSalida.close()
    if(dump_lir):
        archivoSalida = open(sys.argv[1][:-3] + '.lir', 'w')
        archivoSalida.write(analizadorCodigo.linea)
        print "Codigo intermedio escrito a: %s" % (str(sys.argv[1][:-3] + '.lir'))
        archivoSalida.close()
    listabat = analizadorCodigo.linea.split('\n')
    lineabat = '@ECHO OFF\n'
    if listabat[0] == '':
        listabat = listabat[1:]
    lineabat += 'echo ' + listabat[0] + '>codigo.tmp\n'
    for linea in listabat[1:]:
        if linea == '':
            lineabat += 'echo+>>codigo.tmp\n'
        else:
            lineabat += 'echo ' + linea + '>>codigo.tmp\n'
        
    lineabat += 'java -jar microLIR.jar codigo.tmp\necho+\npause\ndel codigo.tmp'
    archivoSalida = open(sys.argv[1][:-3] + '.bat', 'w')
    archivoSalida.write(lineabat)
    print 'Archivo batch ejecutable escrito a: ' + sys.argv[1][:-3] + '.bat'
    archivoSalida.close()
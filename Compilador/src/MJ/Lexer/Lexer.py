import ply.lex as lex
from Error.LexicalError import LexicalError

#
class Lexer(object):


    # Se inicializa un Lexer, se crea un atributo llamado lexer que es del tipo lex, y se
    # le pasa esta clase como parametro.
    def __init__(self):
        self.lexer = lex.lex(object = self)


    # Para ingresar un string al analizador
    def input(self, text):
        self.lexer.input(text)

    # Lista de tokens de la clase
    tokens = (
        'CLASS',
        'EXTENDS',
        'STATIC',
        'VOID',
        'INT',
        'BOOLEAN',
        'STRING',
        'RETURN',
        'IF',
        'ELSE',
        'WHILE',
        'BREAK',
        'CONTINUE',
        'THIS',
        'NEW',
        'LENGTH',
        'TRUE',
        'FALSE',
        'NULL',
        'AND',
        'DIFERENTE',
        'IGUAL',
        'MAYOROIGUAL',
        'MENOROIGUAL',
        'BRACKETS',
        'OR',
        'ID',
        'NUMERO',
        'STRINGID'
        )

    # Lista de literales
    literals = (
        '=',
        ',',
        '[',
        ']',
        '/',
        '{',
        '}',
        '+',
        '>',
        '<',
        '-',
        '%',
        '*',
        '!',
        '(',
        ')',
        '.',
        ';'
    )

    # Los literales de 2 caracteres no se pueden poner en la lista de literales, por lo que
    # se debe hacer una declaracion de cada expresion regular.
    t_AND = r'\&&'
    t_DIFERENTE = r'\!='
    t_IGUAL = r'\=='
    t_MAYOROIGUAL = r'\>='
    t_MENOROIGUAL = r'\<='
    t_OR = r'\|\|'
    t_BRACKETS = r'\[\]'
    
    # Un identificador se compone de cualquier letra seguido de cualquier letra o numero, o 
    # un guion bajo.
    def t_ID(self, token):
        r'[A-Za-z][\w]*'
        for palabra in Lexer.tokens[0:19]:
            if token.value == palabra.lower():
                token.type = palabra
        return token
    
    # Cualquier identificador que empiece por un numero o un guion bajo, no es valido.
    def t_ERRORID(self, token):
        r'\d+[a-zA-Z_]\w*|_\w*'
        raise LexicalError('Linea %d, "%s" : Error lexico, identificador no permitido' % (self.lexer.lineno, token.value))

    # Un string se compone de comillas, seguido de cualquier caracter que no sea salto de
    # linea, seguido de otras comillas.
    def t_STRINGID(self, token):
        r'"[^\n]*?(?<!\\)"'
        valor = token.value.replace('\\\\', '')
        import re
        lista = re.search(r'\\[^n"t]', valor)
        if lista != None:
            raise LexicalError('Linea %d, "%s" : Error lexico, secuencia de escape no perimitida en un string' % (self.lexer.lineno, token.value))
        return token
    
    # Cualquier string que no sea cerrado apropiadamente, no es valido.
    def t_ERRORSTRINGID(self, token):
        r'"((\\")|[^"])*'
        raise LexicalError('Linea %d, "%s" : Error lexico, string no cerrado' % (self.lexer.lineno, token.value))

    # Cualquier numero que empiece por cero, o contenga un punto, no es valido.
    def t_ERRORNUMERO(self, token):
        r'(0\d+|\d+\.\d*|\.\d+)\w*'
        raise LexicalError('Linea %d, "%s" : Error lexico, numero no permitido' % (self.lexer.lineno, token.value))

    # Un numero se compone de un digito del 1-9, seguido por varios digitos, o un solo cero.
    def t_NUMERO(self, token):
        r'0(?!\d)|([1-9]\d*)'
        token.value = int(token.value)
        if int(token.value) > (2**31 - 1) or int(token.value) < (-2**31):
            raise LexicalError('Linea %d, "%s" : Error lexico, numero no se puede representar en 32 bits' % (self.lexer.lineno, token.value))
        return token

    # Pueden haber varios caracteres de nueva linea.
    def t_NUEVALINEA(self, token):
        r'\n+'
        self.lexer.lineno += len(token.value)
        pass

    # Un comentario de linea se compone de dos /, y despues varios caracteres distintos a nueva linea.
    def t_COMENTARIO(self, token):
        r'(/){2}[^\n]*'
        pass

    # Un comentario multiple, se compone de /*, seguido de muchos caracteres incluido nueva linea, seguido
    # de */
    def t_COMENTARIOMULTIPLE(self, token):
        r'/\*[\w\W]*?\*/'
        token.value = token.value.replace('\\n', '')
        self.lexer.lineno += token.value.count('\n')
        pass
    
    # Cualquier comentario que no sea cerrado o abierto, no es valido.
    def t_ERRORCOMENTARIOMULTIPLE(self, token):
        r'/\*|\*/'
        raise LexicalError('Linea %d : Error lexico, comentario no valido' % self.lexer.lineno) 
    
    # Un caracter que no haya sido definido en las reglas anteriores, no es valido.
    def t_error(self, token):
        raise LexicalError('Linea %d, "%s" : Error lexico, caracter no valido' % (self.lexer.lineno, token.value)) 

    # Se ignoran los espacios y los tabs.
    t_ignore  = ' \t'
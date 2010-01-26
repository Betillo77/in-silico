import MJ.ply.yacc as yacc
from MJ.Error.SyntacticalError import SyntacticalError
from compilador.modelo import NodoAst

# Clases usadas en el AST, la mayoria de los nombres fueron tomados de los dados en 
# clase. Las listas no representan objetos en nuestro AST sino que son guardadas en
# la clase que las contiene. La impresion se hace sin usar el patron visitante segun
# lo hablado en clase

class AstNode(NodoAst):
           
    def accept(self, visitante):
        metodo = getattr(visitante, "visitar%s" % self.__class__.__name__, None)
        self.tieneReturn = False
        return metodo(self)
    
    def esFieldSt(self):
        self.esFieldS = isinstance(self, FieldS)
        self.inicio = isinstance(self, Assigment) and self.inicio
        
    def darNombre(self):
        return self.__class__.__name__
    
    def darAtributo(self, nombreAtributo):
        return self.__dict__[nombreAtributo]

class MJClass(AstNode):
   
    def __init__(self, nombre, campoMetodos, extends = False, claseExtends = None):
        self.nombre = nombre
        self.campos = []
        self.metodos = []
        self.extends = extends
        self.claseExtends = claseExtends
        for campoMetodo in campoMetodos:
            if isinstance(campoMetodo, Field):
                self.campos.append(campoMetodo)
            else:
                self.metodos.append(campoMetodo)
                                
class Field(AstNode):
    
    def __init__(self, nombre, tipo, parametro = False):
        self.nombre = nombre
        self.tipo = tipo
        self.parametro = parametro

class Method(AstNode):
    
    def __init__(self, tipoRetorno, nombre, formales, bloque, estatico = False):
        self.tipoRetorno = tipoRetorno
        self.nombre = nombre
        self.formales = formales
        self.bloque = bloque
        self.estatico = estatico 
        
class Type(AstNode):
    
    def __init__(self, nombre, primitivo, tipo):
        self.nombre = nombre
        self.primitivo = primitivo
        self.tipo = tipo
            
class Statement(AstNode):
    pass

class Bloque(Statement):
    
    def __init__(self, lista):
        self.lista = lista
        
class Continue(Statement):
    pass

class Return(Statement):
    
    def __init__(self, retorno):
        self.retorno = retorno
           
class Break(Statement):
    pass

class While(Statement):
    
    def __init__(self, condicion, bloque):
        self.condicion = condicion
        self.bloque = bloque

class If(Statement):
    
    def __init__(self, condicion, bloqueIf, tieneElse = False, bloqueElse = None):
        self.condicion = condicion
        self.bloqueIf = bloqueIf
        self.tieneElse = tieneElse
        self.bloqueElse = bloqueElse     
                          
class Assigment(Statement):
    
    def __init__(self, location, expr, inicio = False):
        self.location = location
        self.expr = expr
        self.inicio = inicio
        
class FieldS(Statement):
    
    def __init__(self, campo):
        self.campo = campo
     
class Expression(Statement):
    pass

class Literal(Expression):
    
    def __init__(self, tipoLiteral, valor):
        self.tipoLiteral = tipoLiteral
        self.valor = valor    

class Location(Expression):
    
    def __init__(self, tipoLocacion, valorUno, valorDos = None):
        self.tipoLocacion = tipoLocacion
        self.valorUno = valorUno
        self.valorDos = valorDos
        
class UnaryOp(Expression):
    
    def __init__(self, operador, valor):
        self.operador = operador
        self.valor = valor
        
class BinaryOp(Expression):
    
    def __init__(self, operador, valorUno, valorDos):
        self.operador = operador
        self.valorUno = valorUno
        self.valorDos = valorDos
        
class Length(Expression):
    
    def __init__(self, valor):
        self.valor = valor
        
class New(Expression):
    
    def __init__(self, tipoNew, valorUno, valorDos = None):
        self.tipoNew = tipoNew
        self.valorUno = valorUno
        self.valorDos = valorDos
 
class This(Expression):
    pass
        
class Call(Expression):
    pass

class StaticCall(Call):
    
    def __init__(self, clase, nombreFuncion, parametros):
        self.clase = clase
        self.nombreFuncion = nombreFuncion
        self.parametros = parametros

class VirtualCall(Call):
    
    def __init__(self, nombreFuncion, parametros, expresion = False, valorExpresion = None):
        self.nombreFuncion = nombreFuncion
        self.parametros = parametros
        self.expresion = expresion
        self.valorExpresion = valorExpresion
        self.statement = False  
 
# Clase Parser encargada de la construccion del AST 
            
class Parser(object):
    
    # Tabla de precedencia dada en las especificaciones, el ELSE fue una adicion para evitar
    # ambiguedad
    
    precedence = (
                 ('right', '='),
                 ('left', 'OR'),
                 ('left', 'AND'),
                 ('left', 'IGUAL', 'DIFERENTE'),
                 ('left', '<', 'MENOROIGUAL', '>', 'MAYOROIGUAL'),
                 ('left', '''+''', '''-'''),
                 ('left', '''*''', '''/''', '''%'''),
                 ('right', '''!''', 'NEW'),
                 ('left', '''[''', ''']''', '''(''', ''')''', '''.'''),
                 ('right', 'ELSE')
                 )
    
    # Se inicializa un Parser, se crea un atributo llamado yacc que es del tipo yacc, y se
    # le pasa este modulo como parametro. Adicionalmente se importan los tokens de lexer
    
    def __init__(self, tokens):
        self.tokens = tokens
        self.yacc = yacc.yacc(module = self)
        
    # Para ingresar una entrada al analizador sintactico
    
    def parse(self, entrada):
        return self.yacc.parse(entrada)
          
    # Producciones del lenguaje, todas las que no corresponden exactamente a las dadas en las
    # especificaciones (modificadas para BNF) estan comentadas
          
    def p_program(self, p):
        '''PROGRAM : LISTACLASES'''
        p[1].reverse()
        p[0] = p[1]

    def p_ListaClases(self, p):
        '''LISTACLASES : CLASESIMPLE LISTACLASES'''
        p[2].append(p[1])
        p[0] = p[2]
    
    def p_ListaClases1(self, p):
        '''LISTACLASES : CLASEEXTENDS LISTACLASES'''
        p[2].append(p[1])
        p[0] = p[2]
        
    def p_ListaClases2(self, p):
        '''LISTACLASES : empty''' 
        p[0] = []
        
    def p_ClaseSimple(self, p):
        '''CLASESIMPLE : CLASS ID '{' CAMPOMETODO '}' '''
        p[4].reverse()
        p[0] = MJClass(p[2], p[4])
        
    def p_ClaseExtends(self, p):
        '''CLASEEXTENDS : CLASS ID EXTENDS ID '{' CAMPOMETODO '}' '''
        p[6].reverse()
        p[0] = MJClass(p[2], p[6], True, p[4])
        
    def p_CampoMetodo(self, p):
        '''CAMPOMETODO : FIELD CAMPOMETODO'''
        for campo in p[1][1]:
            p[2].append(Field(campo, p[1][0]))
        p[0] = p[2]
        
    def p_CampoMetodo1(self, p):
        '''CAMPOMETODO : METHOD CAMPOMETODO'''
        p[2].append(p[1])
        p[0] = p[2]
        
    def p_campoMetodo2(self, p):
        '''CAMPOMETODO : empty''' 
        p[0] = []
        
    def p_Field(self, p):                                     
        '''FIELD : TYPE ID CAMPOS ';' '''
        p[3].append(p[2])
        p[0] = (p[1], p[3])
    
    def p_Campos(self, p):                                    
        '''CAMPOS : ',' ID CAMPOS'''
        p[3].append(p[2])
        p[0] = p[3]
    
    def p_Campos1(self, p):                                    
        '''CAMPOS : empty'''
        p[0] = []    
                                            
    def p_Method(self, p):
        '''METHOD : STATIC TYPE ID '(' FORMALES ')' BLOQUE'''
        p[5].reverse()
        p[0] = Method(p[2], p[3], p[5], p[7], True)
        
        
    def p_Method1(self, p):
        '''METHOD : STATIC VOID ID '(' FORMALES ')' BLOQUE'''
        p[5].reverse()
        p[0] = Method(None, p[3], p[5], p[7], True)
        
    def p_Method2(self, p):
        '''METHOD : TYPE ID '(' FORMALES ')' BLOQUE'''
        p[4].reverse()
        p[0] = Method(p[1], p[2], p[4], p[6])
        
    def p_Method3(self, p):
        '''METHOD : VOID ID '(' FORMALES ')' BLOQUE'''
        p[4].reverse()
        p[0] = Method(None, p[2], p[4], p[6])
        
    def p_Formales(self, p):
        '''FORMALES : TYPE ID MASFORMALES'''
        p[3].append(Field(p[2], p[1], True))
        p[0] = p[3]        
        
    def p_Formales1(self, p):
        '''FORMALES : empty'''
        p[0] = []
    
    def p_MasFormales(self, p):
        '''MASFORMALES : ',' TYPE ID MASFORMALES'''
        p[4].append(Field(p[3], p[2], True))
        p[0] = p[4]
        
    def p_MasFormales1(self, p):
        '''MASFORMALES : empty'''
        p[0] = []
                        
    def p_Type(self, p):
        '''TYPE : INT'''
        p[0] = Type(p[1], True, 'int')
    
    def p_Type1(self, p):
        '''TYPE : BOOLEAN'''
        p[0] = Type(p[1], True, 'boolean')
    
    def p_Type2(self, p):                 
        '''TYPE : STRING'''
        p[0] = Type(p[1], True, 'string')
    
    def p_Type3(self, p):
        '''TYPE : ID'''
        p[0] = Type(p[1], False, p[1])
        
    # Se creo un token especial BRACKETS para evitar ambiguedad
    
    def p_Type4(self, p):
        '''TYPE : TYPE BRACKETS'''
        p[1] = Type(p[1].nombre, p[1].primitivo, p[1].tipo + '[]')
        p[0] = p[1]
        
    def p_Bloque(self, p):
        '''BLOQUE : '{' DECLARACION '}' '''
        p[2].reverse()
        p[0] = Bloque(p[2])
    
    def p_Declaracion(self, p):
        '''DECLARACION : STMT DECLARACION'''
        if(isinstance(p[1], Statement)):
            p[2].append(p[1])
        else:
            p[2].append(p[1][0])
            p[2].append(p[1][1])
        p[0] = p[2]
    
    def p_Declaracion1(self, p):
        '''DECLARACION : empty'''
        p[0] = []

    # El no-terminal Call fue reemplazado directamente para evitar ambiguedad
    
    def p_Stmt(self, p):
        '''STMT : ID '.' ID '(' EXPRESIONCOMA ')' ';' '''
        p[0] = StaticCall(p[1], p[3], p[5])
        p[0].statement = True
    
    def p_Stmt1(self, p):
        '''STMT : ID '.' ID '(' ')' ';' '''
        p[0] = StaticCall(p[1], p[3], [])
        p[0].statement = True
    
    def p_Stmt2(self, p):
        '''STMT : EXPR '.' ID '(' EXPRESIONCOMA ')' ';' '''
        p[0] = VirtualCall(p[3], p[5], True, p[1])
        p[0].statement = True
    
    def p_Stmt3(self, p):
        '''STMT : EXPR '.' ID '(' ')' ';' '''
        p[0] = VirtualCall(p[3], [], True, p[1])
        p[0].statement = True
      
    def p_Stmt4(self, p):
        '''STMT : ID '(' EXPRESIONCOMA ')' ';' '''
        p[0] = VirtualCall(p[1], p[3])
        p[0].statement = True
    
    def p_Stmt5(self, p):
        '''STMT : ID '(' ')' ';' '''
        p[0] = VirtualCall(p[1], [])
        p[0].statement = True
        
    def p_Stmt6(self, p):
        '''STMT : LOCATION '=' EXPR ';' '''
        p[0] = Assigment(p[1], p[3])
        
    def p_Stmt7(self, p):
        '''STMT : RETURN ';' '''
        p[0] = Return(None)
        
    def p_Stmt8(self, p):
        '''STMT : RETURN EXPR ';' '''
        p[0] = Return(p[2])
      
    def p_Stmt9(self, p):
        '''STMT : IF '(' EXPR ')' STMT ELSE STMT'''
        p[0] = If(p[3], p[5], True, p[7])
        
    def p_Stmt10(self, p):
        '''STMT : IF '(' EXPR ')' STMT'''
        p[0] = If(p[3], p[5])
    
    def p_Stmt11(self, p):
        '''STMT : WHILE '(' EXPR ')' STMT'''
        p[0] = While(p[3], p[5])
        
    def p_Stmt12(self, p):
        '''STMT : BREAK ';' '''
        p[0] = Break()
        
    def p_Stmt13(self, p):
        '''STMT : CONTINUE ';' '''
        p[0] = Continue()
        
    def p_Stmt14(self, p):
        '''STMT : BLOQUE'''
        p[0] = p[1]
        
    def p_Stmt15(self, p):
        '''STMT : TYPE ID '=' EXPR ';' '''
        p[0] = (Assigment(Location(0, p[2]), p[4], True), FieldS(Field(p[2], p[1])))
        
    def p_Stmt16(self, p):
        '''STMT : TYPE ID ';' ''' 
        p[0] = FieldS(Field(p[2], p[1])) 
           
    # El no-terminal Call fue reemplazado directamente para evitar ambiguedad
     
    def p_Expr(self, p):
        '''EXPR : ID '.' ID '(' EXPRESIONCOMA ')' '''
        p[0] = StaticCall(p[1], p[3], p[5])
    
    def p_Expr1(self, p):
        '''EXPR : ID '.' ID '(' ')' '''
        p[0] = StaticCall(p[1], p[3], [])
    
    def p_Expr2(self, p):
        '''EXPR : EXPR '.' ID '(' EXPRESIONCOMA ')' '''
        p[0] = VirtualCall(p[3], p[5], True, p[1])
    
    def p_Expr3(self, p):
        '''EXPR : EXPR '.' ID '(' ')' '''
        p[0] = VirtualCall(p[3], [], True, p[1])
      
    def p_Expr4(self, p):
        '''EXPR : ID '(' EXPRESIONCOMA ')' '''
        p[0] = VirtualCall(p[1], p[3])
    
    def p_Expr5(self, p):
        '''EXPR : ID '(' ')' '''
        p[0] = VirtualCall(p[1], [])
        
    def p_Expr6(self, p):
        '''EXPR : LOCATION'''
        p[0] = p[1]
         
    def p_Expr7(self, p):
        '''EXPR : THIS'''
        p[0] = This()
    
    def p_Expr8(self, p):
        '''EXPR : NEW ID '(' ')' '''
        p[0] = New(0, p[2])
    
    def p_Expr9(self, p):
        '''EXPR : NEW TYPE '[' EXPR ']' '''
        p[0] = New(1, p[2], p[4])
    
    def p_Expr10(self, p):
        '''EXPR : EXPR '.' LENGTH'''
        p[0] = Length(p[1])
       
    # Produccion creada para evitar un reduce potencialmente peligroso
    
    def p_Expr11(self, p):
        '''EXPR : ID '.' LENGTH'''
        p[0] = Length(Location(0, p[1]))
        
    # El no-terminal BinOp fue reemplazado directamente para facilitar la precedencia
    
    def p_Expr12(self, p):
        '''EXPR : EXPR '+' EXPR'''
        p[0] = BinaryOp(p[2], p[1], p[3])
        
    def p_Expr13(self, p):
        '''EXPR : EXPR '-' EXPR'''
        p[0] = BinaryOp(p[2], p[1], p[3])
        
    def p_Expr14(self, p):
        '''EXPR : EXPR '*' EXPR'''
        p[0] = BinaryOp(p[2], p[1], p[3])
    
    def p_Expr15(self, p):
        '''EXPR : EXPR '/' EXPR'''
        p[0] = BinaryOp(p[2], p[1], p[3])
        
    def p_Expr16(self, p):
        '''EXPR : EXPR '%' EXPR''' 
        p[0] = BinaryOp(p[2], p[1], p[3])
    
    def p_Expr17(self, p):
        '''EXPR : EXPR AND EXPR''' 
        p[0] = BinaryOp(p[2], p[1], p[3])
        
    def p_Expr18(self, p):
        '''EXPR : EXPR OR EXPR'''
        p[0] = BinaryOp(p[2], p[1], p[3]) 
    
    def p_Expr19(self, p):
        '''EXPR : EXPR '<' EXPR''' 
        p[0] = BinaryOp(p[2], p[1], p[3])
    
    def p_Expr20(self, p):
        '''EXPR : EXPR MENOROIGUAL EXPR'''
        p[0] = BinaryOp(p[2], p[1], p[3])
    
    def p_Expr21(self, p):
        '''EXPR : EXPR '>' EXPR'''  
        p[0] = BinaryOp(p[2], p[1], p[3])
    
    def p_Expr22(self, p):
        '''EXPR : EXPR MAYOROIGUAL EXPR''' 
        p[0] = BinaryOp(p[2], p[1], p[3])
        
    def p_Expr23(self, p):
        '''EXPR : EXPR IGUAL EXPR''' 
        p[0] = BinaryOp(p[2], p[1], p[3])
    
    def p_Expr24(self, p):
        '''EXPR : EXPR DIFERENTE EXPR''' 
        p[0] = BinaryOp(p[2], p[1], p[3])
        
    def p_Expr25(self, p):
        '''EXPR : '-' EXPR'''
        p[0] = UnaryOp(p[1], p[2])
        
    def p_Expr26(self, p):
        '''EXPR : '!' EXPR'''
        p[0] = UnaryOp(p[1], p[2])
        
    def p_Expr27(self, p):
        '''EXPR : LITERAL'''
        p[0] = p[1]
        
    def p_Expr28(self, p):
        '''EXPR : '(' EXPR ')' '''
        p[0] = p[2]
        
    def p_ExpresionComa(self, p):
        '''EXPRESIONCOMA : EXPR EXPRESIONCOMAP'''
        p[2].reverse()
        p[2].insert(0, p[1])
        p[0] = p[2]
        
    def p_ExpresionComaP(self, p):
        '''EXPRESIONCOMAP : ',' EXPR EXPRESIONCOMAP'''
        p[3].append(p[2])
        p[0] = p[3]
    
    def p_ExpresionComaP1(self, p):
        '''EXPRESIONCOMAP : empty'''
        p[0] = []

    def p_Location(self, p):
        '''LOCATION : ID'''
        p[0] = Location(0, p[1])

    def p_Location1(self, p):
        '''LOCATION : EXPR '.' ID'''
        p[0] = Location(1, p[1], p[3])

    def p_Location2(self, p):
        '''LOCATION : EXPR '[' EXPR ']' '''
        p[0] = Location(2, p[1], p[3])
    
    def p_Literal(self, p):
        '''LITERAL : NUMERO'''
        p[0] = Literal(0, p[1])
        
    def p_Literal1(self, p):
        '''LITERAL : STRINGID'''
        p[0] = Literal(1, p[1])
    
    def p_Literal2(self, p):
        '''LITERAL : TRUE'''
        p[0] = Literal(2, p[1])
        
    def p_Literal3(self, p):
        '''LITERAL : FALSE'''
        p[0] = Literal(2, p[1])
        
    def p_Literal4(self, p):
        '''LITERAL : NULL'''
        p[0] = Literal(2, p[1])
        
    def p_empty(self, p):
        'empty : '
        pass
    
    # Manejo de errores
    
    def p_error(self, p):
        raise SyntacticalError('Linea %d, "%s" : Error sintactico, token no valido' % (p.lineno, p.value)) 

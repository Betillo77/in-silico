from Error.SemanticError import SemanticError
from types import StringType, NoneType

class Entrada:
    
    def __init__(self, nombre, clase, tipo, tabla, informacion = None):
        self.nombre = nombre
        self.clase = clase
        self.tipo = tipo
        self.tabla = tabla
        self.informacion = informacion

class Tabla:
    
    def __init__(self, nombre, padre, tipo):
        self.nombre = nombre
        self.tabla = {}
        self.hijos = []
        self.padre = padre
        self.tipo = tipo
        
    def agregar(self, entrada):
        if entrada.nombre in self.tabla:
            raise SemanticError('Error semantico, identificador redefinido: ' + (entrada.nombre))
        self.tabla[entrada.nombre] = entrada
        
    def buscar(self, nombre):
        if nombre in self.tabla:
            return self.tabla[nombre]
        else:
            if self.padre != None:
                return self.padre.buscar(nombre)
            raise SemanticError('Error semantico, identificador no declarado o tipo incompatible: ' + (nombre))
        
    def buscarCampoRepetido(self, nombre):
        if nombre in self.tabla:
            raise SemanticError('Error semantico, campo redeclarado: ' + (nombre))
        else:
            if self.padre != None:
                self.padre.buscarCampoRepetido(nombre)
                    
    def agregarHijo(self, hijo):
        self.hijos.append(hijo)
    
    def buscarHijo(self, nombre):
        for objeto in self.hijos:
            if objeto.nombre == nombre:
                return objeto
        raise SemanticError('Error semantico, identificador no declarado: ' + (nombre))
    
    def buscarFuncionRedefinida(self, entradaMetodo):
        if entradaMetodo.nombre in self.tabla:
            entradaPosible = self.tabla[entradaMetodo.nombre]
            if entradaPosible.clase != 'metodo' or entradaPosible.tipo != entradaMetodo.tipo or len(entradaMetodo.informacion) != len(entradaPosible.informacion):
                raise SemanticError('Error semantico, funcion redefinida erroneamente: ' + (entradaMetodo.nombre))           
            for formal1, formal2 in zip(entradaMetodo.informacion, entradaPosible.informacion):
                if formal1.tipo.tipo != formal2.tipo.tipo:
                    raise SemanticError('Error semantico, funcion redefinida erroneamente: ' + (entradaMetodo.nombre)) 
        elif self.padre != None:
            self.padre.buscarFuncionRedefinida(entradaMetodo)
            
    def buscarNumeroFuncion(self, nombreFuncion):
        numeroFuncion = self.padre.buscarFuncionExtendida(nombreFuncion)
        return numeroFuncion
        
    def buscarFuncionExtendida(self, nombreFuncion):
        if nombreFuncion in self.tabla:
            return self.tabla[nombreFuncion].numero
        else:
            if self.padre == None:
                return -1
            else:
                return self.padre.buscarFuncionExtendida(nombreFuncion)
            
    def buscarDespachoFuncion(self, numeroFuncion):
        for entrada in self.tabla.itervalues():
            if entrada.numero == numeroFuncion and entrada.clase == 'metodo':
                return entrada.despacho
        return self.padre.buscarDespachoFuncion(numeroFuncion)
        

class Type:
    pass

class VisitanteTabla:
    
    def __init__(self):
        self.tablaPrincipal = Tabla('Principal', None, 'programa')
        self.tablaActual = self.tablaPrincipal
        self.metodoActual = None
        self.enWhile = False
        self.linea = ''
        self.main = False
    
    def visitarProgram(self, program):
        for clase in program:
            self.generarTablaClase(clase)
        for clase in program:
            self.generarMetodosYCampos(clase)
        for clase in program:
            clase.accept(self)
        if not(self.main):
            raise SemanticError('Error semantico, el programa debe contener un unico main de la forma: static void main (string[] args) { ... }')
        self.tabActual = 0
        tablaTipos = Tabla('tipos', None, 'Tabla de')
        for entrada in self.tablaPrincipal.tabla.values():
            if entrada.clase == 'clase':
                tablaTipos.agregar(entrada)
        self.imprimirTabla(tablaTipos)
        tablaMetodos = Tabla('metodos estaticos', None, 'Tabla de')
        for entrada in self.tablaPrincipal.tabla.values():
            if entrada.clase == 'metodo':
                tablaMetodos.agregar(entrada)
        self.imprimirTabla(tablaMetodos)
        self.linea += '\n\nClases\n\n'
        self.tabActual = 1
        for clase in self.tablaPrincipal.hijos:
            self.imprimirTabla(clase)
            self.linea += '\n\n' 
            
    def generarTablaClase(self, clase):
        if clase.extends:
            extendida = self.tablaPrincipal.buscar(clase.claseExtends).tabla
            tablaNueva = Tabla(clase.nombre, extendida, 'clase')
            extendida.agregarHijo(tablaNueva)
            entradaNueva = Entrada(clase.nombre, 'clase', 'class', tablaNueva)
            self.tablaPrincipal.agregar(entradaNueva)
        else:       
            tablaNueva = Tabla(clase.nombre, self.tablaPrincipal, 'clase')
            self.tablaPrincipal.agregarHijo(tablaNueva)
            entradaNueva = Entrada(clase.nombre, 'clase', 'class', tablaNueva)
            self.tablaPrincipal.agregar(entradaNueva)
            
    def generarMetodosYCampos(self, clase):
        self.tablaActual = self.tablaPrincipal.buscar(clase.nombre).tabla
        for campo in clase.campos:
            campo.accept(self)
        for metodo in clase.metodos:
            if not(isinstance(metodo.tipoRetorno, NoneType)):
                metodo.tipoRetorno.accept(self)
            else:
                metodo.tipoRetorno = Type()
                metodo.tipoRetorno.tipo = None
            if metodo.estatico or clase.nombre == 'Library':
                metodo.estatico = True
                tablaNueva = Tabla(clase.nombre + '.' + metodo.nombre, self.tablaPrincipal, 'metodo')
                self.tablaPrincipal.agregarHijo(tablaNueva)
                entradaNueva = Entrada(clase.nombre + '.' + metodo.nombre, 'metodo', metodo.tipoRetorno.tipo, tablaNueva, metodo.formales)
                self.tablaPrincipal.agregar(entradaNueva)
            else:
                tablaNueva = Tabla(metodo.nombre, self.tablaActual, 'metodo')
                self.tablaActual.agregarHijo(tablaNueva)
                entradaNueva = Entrada(metodo.nombre, 'metodo', metodo.tipoRetorno.tipo, tablaNueva, metodo.formales)
                self.tablaActual.padre.buscarFuncionRedefinida(entradaNueva)
                self.tablaActual.agregar(entradaNueva)
        self.tablaActual = self.tablaPrincipal
        
    def visitarMJClass(self, clase):
        self.tablaActual = self.tablaPrincipal.buscar(clase.nombre).tabla
        for metodo in clase.metodos:
            metodo.accept(self)
        self.tablaActual = self.tablaPrincipal
        
    def visitarMethod(self, metodo):
        tablaTemp = self.tablaActual
        if metodo.estatico:
            self.tablaActual = self.tablaPrincipal.buscar(tablaTemp.nombre + '.' + metodo.nombre).tabla
        else:
            self.tablaActual = self.tablaActual.buscar(metodo.nombre).tabla
        self.metodoActual = metodo
        metodo.tabla = self.tablaActual
        for formal in metodo.formales:
            formal.accept(self)
        if metodo.nombre == 'main':
            if not(metodo.estatico) or not(metodo.tipoRetorno.tipo == None) or not(len(metodo.formales) == 1) or not(metodo.formales[0].tipo.tipo == 'string[]') or self.main:
                raise SemanticError('Error semantico, el programa debe contener un unico main de la forma: static void main (string[] args) { ... }')
            self.main = True
        metodo.bloque.accept(self)
        if metodo.tipoRetorno.tipo == None:
            for declaracion in metodo.bloque.lista:
                if declaracion.tieneReturn:
                    raise SemanticError('Error semantico, retorno no permitido en metodo: ' + metodo.nombre)
        else:
            termino = False
            for declaracion in metodo.bloque.lista:
                if declaracion.tieneReturn:
                    termino = True
                    break;
            if not(termino) and metodo.tabla.nombre[:7] != 'Library':
                raise SemanticError('Error semantico, fin de metodo encontrado sin retornar, en metodo: ' + metodo.nombre)               
        self.tablaActual = tablaTemp
            
    def visitarField(self, campo):
        campo.tipo.accept(self)
        self.tablaActual.buscarCampoRepetido(campo.nombre)
        if campo.parametro:
            entradaNueva = Entrada(campo.nombre, 'parametro', campo.tipo.tipo, self.tablaActual)
        else:
            entradaNueva = Entrada(campo.nombre, 'campo', campo.tipo.tipo, self.tablaActual)
        campo.entrada = entradaNueva
        self.tablaActual.agregar(entradaNueva)
        
    def visitarBloque(self, bloque):
        tablaTemp = self.tablaActual
        self.tablaActual = Tabla('', self.tablaActual, 'bloque')
        tablaTemp.agregarHijo(self.tablaActual)
        bloque.tabla = self.tablaActual
        termino = False
        inicio = True
        for declaracion in bloque.lista:
            declaracion.accept(self)
            if declaracion.tieneReturn:
                termino = True
            declaracion.esFieldSt()
            if declaracion.esFieldS or declaracion.inicio:
                if not(inicio):
                    raise SemanticError('Error semantico, campo declarado tardiamente')
            else:
                inicio = False
        bloque.tieneReturn = termino            
        self.tablaActual = tablaTemp
        
    def visitarContinue(self, cont):
        if not(self.enWhile):
            raise SemanticError('Error semantico, continue/break no permitido en metodo: ' + self.metodoActual.nombre)
        tablaTemp = self.tablaActual
        
    def visitarBreak(self, bre):
        self.visitarContinue(bre)
        
    def visitarReturn(self, ret):
        if ret.retorno != None:
            ret.retorno.accept(self)
            if not(self.esHeredero(ret.retorno.tipo, self.metodoActual.tipoRetorno.tipo)):
                raise SemanticError('Error semantico, tipo de retorno invalido, en metodo: ' + self.metodoActual.nombre)
            ret.tieneReturn = True
    
    def visitarWhile(self, whi):
        whi.condicion.accept(self)
        if not(whi.condicion.tipo == 'boolean'):
            raise SemanticError('Error semantico, condicion de while no es booleana, en metodo: ' + self.metodoActual.nombre)
        if isinstance(whi.bloque, tuple):
            raise SemanticError('Error semantico, declaracion inocua como unico statement de un while, en metodo: ' + self.metodoActual.nombre)
        whi.bloque.esFieldSt()
        if whi.bloque.esFieldS:
            raise SemanticError('Error semantico, declaracion inocua como unico statement de un while, en metodo: ' + self.metodoActual.nombre)
        whileTemp = self.enWhile
        self.enWhile = True
        whi.bloque.accept(self)
        self.enWhile = whileTemp
        whi.tieneReturn = whi.bloque.tieneReturn
        
    def visitarIf(self, i):
        i.condicion.accept(self)
        if not(i.condicion.tipo == 'boolean'):
            raise SemanticError('Error semantico, condicion de if no es booleana, en metodo: ' + self.metodoActual.nombre)
        if isinstance(i.bloqueIf, tuple):
            raise SemanticError('Error semantico, declaracion inocua como unico statement de un if, en metodo: ' + self.metodoActual.nombre)
        i.bloqueIf.esFieldSt()
        if i.bloqueIf.esFieldS:
            raise SemanticError('Error semantico, declaracion inocua como unico statement de un if, en metodo: ' + self.metodoActual.nombre)
        i.bloqueIf.accept(self)
        if i.tieneElse:
            if isinstance(i.bloqueElse, tuple):
                raise SemanticError('Error semantico, declaracion inocua como unico statement de un else, en metodo: ' + self.metodoActual.nombre)
            i.bloqueElse.esFieldSt()
            if i.bloqueElse.esFieldS:
                raise SemanticError('Error semantico, declaracion inocua como unico statement de un else, en metodo: ' + self.metodoActual.nombre)
            i.bloqueElse.accept(self)
        i.tieneReturn = i.bloqueIf.tieneReturn and i.tieneElse and i.bloqueElse.tieneReturn
        
    def visitarAssigment(self, assigment):
        assigment.location.accept(self)
        tablaTemp = self.tablaActual
        if assigment.inicio:
            self.tablaActual = self.tablaActual.padre
        assigment.expr.accept(self)
        self.tablaActual = tablaTemp
        if not(self.esHeredero(assigment.expr.tipo, assigment.location.tipo)):
            if isinstance(assigment.location.valorUno, str):
                raise SemanticError('Error semantico, asignacion de tipo erronea, en metodo: ' + self.metodoActual.nombre + ' locacion: ' + assigment.location.valorUno)
            else:
                raise SemanticError('Error semantico, asignacion de tipo erronea, en metodo: ' + self.metodoActual.nombre + ' entre tipos ' + assigment.expr.tipo + ' y ' + assigment.location.tipo)
        
    def visitarFieldS(self, fieldS):
        fieldS.campo.tipo.accept(self)
        self.metodoActual.tabla.buscarCampoRepetido(fieldS.campo.nombre)
        entradaNueva = Entrada(fieldS.campo.nombre, 'variable', fieldS.campo.tipo.tipo, self.tablaActual)
        self.tablaActual.agregar(entradaNueva)  
        fieldS.entrada = entradaNueva  

    def visitarType(self, type):
        if not(type.primitivo):
            self.tablaPrincipal.buscar(type.nombre)
        if type.nombre == 'Library':
            raise SemanticError('Error semantico, llamado a tipo Library')
            
    def visitarLiteral(self, literal):
        if literal.tipoLiteral == 0:
            literal.tipo = 'int'
        elif literal.tipoLiteral == 1:
            literal.tipo = 'string'
        else:
            if literal.valor == 'null':
                literal.tipo = 'null'
            else:
                literal.tipo = 'boolean'
    
    def visitarLocation(self, location):
        if location.tipoLocacion == 0:
            location.entrada = self.tablaActual.buscar(location.valorUno)
            if location.entrada.clase == 'metodo':
                raise SemanticError('Error semantico, Locacion invalida: ' + location.entrada.nombre + ' en metodo: ' + self.metodoActual.nombre)
            location.tabla = location.entrada.tabla
            location.tipo = location.entrada.tipo
        elif location.tipoLocacion == 1:
            location.valorUno.accept(self)
            location.entrada = self.tablaPrincipal.buscar(location.valorUno.tipo).tabla.buscar(location.valorDos)
            location.tabla = location.entrada.tabla
            if location.entrada.clase == 'metodo':
                raise SemanticError('Error semantico, Locacion invalida: ' + location.entrada.nombre + ' en metodo: ' + self.metodoActual.nombre)
            location.tipo = location.entrada.tipo
        elif location.tipoLocacion == 2:
            location.valorUno.accept(self)
            location.valorDos.accept(self)
            if location.valorDos.tipo != 'int':
                raise SemanticError('Error semantico, posicion dentro del vector debe ser int en metodo: ' + self.metodoActual.nombre)
            if location.valorUno.tipo[-2:] != '[]':
                raise SemanticError('Error semantico, intentando accesar a posicion dentro de objeto que no es vector en metodo: ' + self.metodoActual.nombre)
            location.tipo = location.valorUno.tipo[:-2]
            
    def visitarUnaryOp(self, unaryOp):
        unaryOp.valor.accept(self)
        if unaryOp.operador == '!' and unaryOp.valor.tipo != 'boolean':
            raise SemanticError('Error semantico, negacion de valor que no es booleano, en metodo: ' + self.metodoActual.nombre)
        elif unaryOp.operador == '-' and unaryOp.valor.tipo != 'int':
            raise SemanticError('Error semantico, multiplicacion por -1 de valor que no es int, en metodo: ' + self.metodoActual.nombre)
        unaryOp.tipo = unaryOp.valor.tipo
    
    def visitarBinaryOp(self, binaryOp):
        binaryOp.valorUno.accept(self)
        binaryOp.valorDos.accept(self)
        if binaryOp.valorUno.tipo != 'null' and binaryOp.valorDos.tipo != 'null' and binaryOp.valorUno.tipo != binaryOp.valorDos.tipo:
            raise SemanticError('Error semantico, operacion binaria entre tipos incompatibles, en metodo: ' + self.metodoActual.nombre)
        if binaryOp.operador in ['==', '!=']:
            pass
        elif binaryOp.valorUno.tipo == 'null' or binaryOp.valorDos.tipo == 'null':
            raise SemanticError('Error semantico, operacion binaria entre tipos incompatibles, en metodo: ' + self.metodoActual.nombre)
        elif binaryOp.operador in ['&&', '||']:
            if binaryOp.valorUno.tipo != 'boolean':
                raise SemanticError('Error semantico, operacion binaria entre tipos incompatibles, en metodo: ' + self.metodoActual.nombre)
        else:
            if binaryOp.operador == '+':
                if binaryOp.valorUno.tipo != 'string' and binaryOp.valorUno.tipo != 'int':
                    raise SemanticError('Error semantico, operacion binaria entre tipos incompatibles, en metodo: ' + self.metodoActual.nombre)
            elif binaryOp.valorUno.tipo != 'int':
                raise SemanticError('Error semantico, operacion binaria entre tipos incompatibles, en metodo: ' + self.metodoActual.nombre)
        if binaryOp.operador in ['==', '!=', '<=', '<', '>', '>=']:
            binaryOp.tipo = 'boolean'
        else:
            binaryOp.tipo = binaryOp.valorUno.tipo
    
    def visitarLength(self, length):
        length.valor.accept(self)
        if (length.valor.tipo[-2:] != '[]') and (length.valor.tipo != 'string'):
            raise SemanticError('Error semantico, intentando acceder a longitud de objeto que no es arreglo, en metodo: ' + self.metodoActual.nombre)
        length.tipo = 'int'

    def visitarNew(self, new):
        if new.tipoNew == 0:
            self.tablaPrincipal.buscar(new.valorUno)
            if new.valorUno == 'Library':
                raise SemanticError('Error semantico, llamado a tipo Library')
            new.tipo = new.valorUno
            
        else:
            new.valorUno.accept(self)
            new.valorDos.accept(self)
            if new.valorDos.tipo != 'int':
                raise SemanticError('Error semantico, tamano de arreglo no es entero, en metodo: ' + self.metodoActual.nombre)
            new.tipo = new.valorUno.tipo + '[]'
    
    def visitarThis(self, this):
        if self.metodoActual.estatico:
            raise SemanticError('Error semantico, llamada a this en metodo: ' + self.metodoActual.nombre + ' que es estatico')
        this.tipo = self.metodoActual.tabla.padre.nombre
    
    def visitarStaticCall(self, staticCall):
        staticCall.entrada = self.tablaPrincipal.buscar(staticCall.clase + '.' + staticCall.nombreFuncion)
        staticCall.tabla = staticCall.entrada.tabla
        for expr in staticCall.parametros:
            expr.accept(self)       
        for parametro, expr in zip(staticCall.entrada.informacion, staticCall.parametros):
            if not(self.esHeredero(expr.tipo, parametro.tipo.tipo)):
                raise SemanticError('Error semantico, parametros no corresponden en llamado a metodo: ' + staticCall.nombreFuncion)
        staticCall.tipo = staticCall.entrada.tipo
    
    def visitarVirtualCall(self, virtualCall):
        if virtualCall.expresion:
            virtualCall.valorExpresion.accept(self)
            if virtualCall.valorExpresion.tipo == 'class':
                virtualCall.clase = virtualCall.valorExpresion.valorUno
                virtualCall.estatico = True
                self.visitarStaticCall(virtualCall)
                return None
            virtualCall.entrada = self.tablaPrincipal.buscar(virtualCall.valorExpresion.tipo).tabla.buscar(virtualCall.nombreFuncion)
            virtualCall.estatico = False
        else:
            virtualCall.estatico = False
            virtualCall.entrada = self.metodoActual.tabla.padre.buscar(virtualCall.nombreFuncion)
        for expr in virtualCall.parametros:
            expr.accept(self) 
        for parametro, expr in zip(virtualCall.entrada.informacion, virtualCall.parametros):
            if not(self.esHeredero(expr.tipo, parametro.tipo.tipo)):
                raise SemanticError('Error semantico, parametros no corresponden en llamado a metodo: ' + virtualCall.nombreFuncion)
        virtualCall.tipo = virtualCall.entrada.tipo
        
    def esHeredero(self, a, b):
        if a == b:
            return True
        if (a == 'null') and not(b in ['int', 'string', 'boolean']):
            return True
        if a[-2:] == '[]' or b[-2:] == '[]':
            return a == b
        tablaH = self.tablaPrincipal.buscar(a).tabla
        while(tablaH.padre != None):
            if tablaH.nombre == b:
                return True
            tablaH = tablaH.padre
        return False
    
    def imprimirTabla(self, tabla):
        if len(tabla.tabla.values()) == 0:
            self.linea += (self.tabActual * '    ') + 'tabla vacia\n\n'
        else:
            self.linea += (self.tabActual * '    ') + tabla.tipo + ' ' + tabla.nombre + '\n\n'
            self.linea += (self.tabActual * '    ') + 'NOMBRE        CLASE         TIPO\n'
            for entrada in tabla.tabla.values():
                if len(entrada.nombre) > 12:
                    self.linea += (self.tabActual * '    ') + entrada.nombre[:12] + '  '
                else:
                    self.linea += (self.tabActual * '    ') + entrada.nombre  + ((14 - len(entrada.nombre)) * ' ')
                if len(entrada.clase) > 12:
                    self.linea += entrada.clase[:12] + '  '
                else:
                    self.linea += entrada.clase + ((14 - len(entrada.clase)) * ' ')
                if entrada.tipo == None:
                    self.linea += 'void\n'
                elif len(entrada.tipo) > 12:
                    self.linea += entrada.tipo[:12] + '\n'
                else:
                    self.linea += entrada.tipo + ((14 - len(entrada.tipo)) * ' ') + '\n'
        if len(tabla.hijos) > 0:
            self.linea += '\n\n' + (self.tabActual * '    ') + 'hijos' + '\n\n'
            self.tabActual += 1
            for hijo in tabla.hijos:
                self.imprimirTabla(hijo)
            self.tabActual -= 1
        elif len(tabla.tabla.values()) != 0:
            self.linea += '\n\n'      
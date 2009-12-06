class InstruccionesMetodo:
    
    def __init__(self, clase, nombre):
        self.listainstrucciones = []
        self.clase = clase
        self.nombre = nombre
        
    def agregar(self, instruccion):
        self.listainstrucciones.append(instruccion)

class VisitanteLir:
    
    def __init__(self, tablaPrincipal):
        self.tablaPrincipal = tablaPrincipal
        self.vectorDespacho = ''
        self.strings = ''
        self.numerostring = 0
        self.listametodos = []
        self.registroactual = 1
        self.whileactual = -1
        self.numerowhile = -1
        self.numeroif = -1
        self.numerootros = -1
        self.linea = ''
        
    def visitarProgram(self, program):
        for clase in program:
            self.numerarClase(clase)
            self.vectorDespacho += '_DV_' + clase.nombre + ': [' 
            tabla = self.tablaPrincipal.buscar(clase.nombre).tabla
            for i in range(0, tabla.numeroMetodos + 1):
                self.vectorDespacho += tabla.buscarDespachoFuncion(i)
                if i != tabla.numeroMetodos:
                    self.vectorDespacho += ','
            self.vectorDespacho += ']\n'
        for clase in program:
            clase.accept(self)
        self.linea = self.strings + '\n' + self.vectorDespacho
        for listametodo in self.listametodos:
            self.linea += '\n' + '# ' + listametodo.clase + '.' + listametodo.nombre + '\n'
            if listametodo.nombre == 'main':
                self.linea += '_ic_main:\n'
            else:
                self.linea += '_' + listametodo.clase + '_' + listametodo.nombre + ':\n'
            for instruccion in listametodo.listainstrucciones:
                self.linea += instruccion + '\n'
            self.linea += '# Fin de ' + listametodo.clase + '.' + listametodo.nombre + '\n'
        
    def numerarClase(self, clase):
        if clase.extends:
            numeroActualCampo = self.tablaPrincipal.buscar(clase.claseExtends).tabla.numeroCampos
            tabla = self.tablaPrincipal.buscar(clase.nombre).tabla
            numeroActualMetodo = self.tablaPrincipal.buscar(clase.claseExtends).tabla.numeroMetodos
            for entrada in tabla.tabla.itervalues():
                if entrada.clase == 'campo':
                    numeroActualCampo += 1
                    entrada.numero = numeroActualCampo
                if entrada.clase == 'metodo':
                    numeroFuncion = tabla.buscarNumeroFuncion(entrada.nombre)
                    if numeroFuncion == -1:
                        numeroActualMetodo += 1
                        entrada.numero = numeroActualMetodo
                    else:
                        entrada.numero = numeroFuncion
                    entrada.despacho = '_' + clase.nombre + '_' + entrada.nombre
                    print entrada
            tabla.numeroCampos = numeroActualCampo
            tabla.numeroMetodos = numeroActualMetodo
        else:
            numeroActualCampo = 0
            numeroActualMetodo = -1
            tabla = self.tablaPrincipal.buscar(clase.nombre).tabla
            for entrada in tabla.tabla.itervalues():
                if entrada.clase == 'campo':
                    numeroActualCampo += 1
                    entrada.numero = numeroActualCampo
                if entrada.clase == 'metodo':
                    numeroActualMetodo += 1
                    entrada.numero = numeroActualMetodo
                    entrada.despacho = '_' + clase.nombre + '_' + entrada.nombre
            tabla.numeroCampos = numeroActualCampo
            tabla.numeroMetodos = numeroActualMetodo
            print tabla.numeroMetodos
            
    def visitarMJClass(self, clase):
        self.claseactual = clase
        for metodo in clase.metodos:
            self.listaactual = InstruccionesMetodo(clase.nombre, metodo.nombre)
            metodo.accept(self)
            self.listametodos.append(self.listaactual)
    
    def visitarMethod(self, metodo):
        self.metodoactual = metodo
        self.registroactual = 1
        i = 1
        for formal in metodo.formales:
            formal.entrada.registro = 'p' + str(i)
            i += 1
        metodo.bloque.accept(self)
        if metodo.nombre == 'main':
            self.listaactual.agregar('Library __exit(0), Rdummy')
        elif metodo.tipoRetorno.tipo == None:
            self.listaactual.agregar('Return 999')
        
    def visitarBloque(self, bloque):
        for statement in bloque.lista:
            statement.accept(self)
            
    def visitarContinue(self, cont):
        self.listaactual.agregar('Jump _while_' + str(self.whileactual))
        
    def visitarBreak(self, bre):
        self.listaactual.agregar('Jump _salida_while_' + str(self.whileactual))
        
    def visitarReturn(self, ret):
        if ret.retorno == None:
            self.listaactual.agregar('Return 999')
        else:
            registroanterior = self.registroactual
            registroretorno = ret.retorno.accept(self)
            self.registroactual = registroanterior
            self.listaactual.agregar('Return ' + registroretorno)
        
    def visitarWhile(self, whi):
        self.numerowhile += 1
        whileanterior = self.whileactual
        self.whileactual = self.numerowhile
        self.listaactual.agregar('_while_' + str(self.numerowhile) + ':')
        registroanterior = self.registroactual
        registroexpression = whi.condicion.accept(self)
        self.registroactual = registroanterior
        self.listaactual.agregar('Compare 0, ' + registroexpression)
        self.listaactual.agregar('JumpTrue _salida_while_' + str(self.numerowhile))
        whi.bloque.accept(self)
        self.listaactual.agregar('Jump _while_' + str(self.whileactual))
        self.listaactual.agregar('_salida_while_' + str(self.whileactual) + ':')
        self.whileactual = whileanterior
            
    def visitarIf(self, i):
        self.numeroif += 1
        esteif = self.numeroif
        if i.tieneElse:
            etiqueta = '_else_' + str(self.numeroif)
        else:
            etiqueta = '_salida_if_' + str(self.numeroif)
        registroanterior = self.registroactual            
        registroexpression = i.condicion.accept(self)
        self.registroactual = registroanterior
        self.listaactual.agregar('Compare 0, ' + registroexpression)
        self.listaactual.agregar('JumpTrue ' + etiqueta)
        i.bloqueIf.accept(self)
        if i.tieneElse:
            self.listaactual.agregar('Jump _salida_if_' + str(self.numeroif))
            self.listaactual.agregar(etiqueta + ':')
            i.bloqueElse.accept(self)
        self.listaactual.agregar('_salida_if_' + str(self.numeroif) + ':')
    
    def visitarAssigment(self, assigment):
        registroanterior = self.registroactual          
        registroexpression = assigment.expr.accept(self)
        location = assigment.location
        if location.tipoLocacion == 0:
            if location.entrada.clase == 'campo':
                self.listaactual.agregar('Move this, R' + str(self.registroactual))
                self.listaactual.agregar('MoveField ' + registroexpression + ', R' + str(self.registroactual) + '.' + str(location.entrada.numero))
            elif location.entrada.clase == 'parametro':
                self.listaactual.agregar('Move ' + registroexpression + ', ' + location.entrada.registro)
            else:
                self.listaactual.agregar('Move ' + registroexpression + ', R' + str(location.entrada.registro))
        elif location.tipoLocacion == 1:
            registroclase = location.valorUno.accept(self)
            self.listaactual.agregar('MoveField ' + registroexpression + ', ' + registroclase + '.' + str(location.entrada.numero))
        else:
            registroarreglo = location.valorUno.accept(self)
            registropos = location.valorDos.accept(self)
            self.listaactual.agregar('MoveArray ' + registroexpression + ', ' + registroarreglo + '[' + registropos + ']')
        self.registroactual = registroanterior
    
    def visitarFieldS(self, fieldS):
        self.listaactual.agregar('Move 0, R' + str(self.registroactual))
        fieldS.entrada.registro = self.registroactual
        self.registroactual += 1
            
    def visitarLiteral(self, literal):
        registro = 'R' + str(self.registroactual)
        self.registroactual += 1
        if literal.tipoLiteral == 0:
            self.listaactual.agregar('Move ' + str(literal.valor) + ', ' + registro)
        elif literal.tipoLiteral == 1:
            self.strings += 'str' + str(self.numerostring) + ': ' + literal.valor.replace('\\', '/') + '\n' 
            self.listaactual.agregar('Move str' + str(self.numerostring) + ', ' + registro)
            self.numerostring += 1
        elif literal.valor == 'true':
            self.listaactual.agregar('Move 1, ' + registro)
        else:
            self.listaactual.agregar('Move 0, ' + registro)
        return registro
    
    def visitarLocation(self, location):
        if location.tipoLocacion == 0:
            if location.entrada.clase == 'campo':
                registro = 'R' + str(self.registroactual)
                self.registroactual += 1
                self.listaactual.agregar('Move this, R' + str(self.registroactual))
                self.listaactual.agregar('MoveField R' + str(self.registroactual) + '.' + str(location.entrada.numero) + ', ' + registro)
                return registro
            else:
                registro = 'R' + str(self.registroactual)
                self.registroactual += 1
                if location.entrada.clase == 'parametro':
                    self.listaactual.agregar('Move ' + location.entrada.registro + ', ' + registro)
                else:
                    self.listaactual.agregar('Move R' + str(location.entrada.registro) + ', ' + registro)
                return registro
        if location.tipoLocacion == 1:
            registro = 'R' + str(self.registroactual)
            self.registroactual += 1
            registroanterior = self.registroactual
            registroexpression = location.valorUno.accept(self)
            self.registroactual = registroanterior
            self.listaactual.agregar('MoveField ' + registroexpression + '.' + str(location.entrada.numero) + ', ' + registro)
            return registro
        if location.tipoLocacion == 2:
            registro = 'R' + str(self.registroactual)
            self.registroactual += 1
            registroanterior = self.registroactual
            registroarreglo = location.valorUno.accept(self)
            registropos = location.valorDos.accept(self)
            self.registroactual = registroanterior
            self.listaactual.agregar('MoveArray ' + registroarreglo + '[' + registropos + '], ' + registro)
            return registro
          
    def visitarUnaryOp(self, unaryOp):
        if unaryOp.operador == '-':
            registroexpression = unaryOp.valor.accept(self)
            self.listaactual.agregar('Move 0, R' + str(self.registroactual))
            self.listaactual.agregar('Sub ' + registroexpression + ', R' + str(self.registroactual))
            self.listaactual.agregar('Move R' + str(self.registroactual) + ', ' + registroexpression)
            return registroexpression
        else:
            registroexpression = unaryOp.valor.accept(self)
            self.listaactual.agregar('Neg ' + registroexpression)
            return registroexpression
            
    def visitarBinaryOp(self, binaryOp):
        if binaryOp.operador in ['&&', '||']:
            if binaryOp.operador == '&&':
                condicion = 0
                instruccion = 'And '
            else:
                condicion = 1
                instruccion = 'Or '
            registroizquierda = binaryOp.valorUno.accept(self)
            self.numerootros += 1
            self.listaactual.agregar('Compare ' + str(condicion) + ', ' + registroizquierda)
            self.listaactual.agregar('JumpTrue _otro_' + str(self.numerootros))
            registroanterior = self.registroactual
            registroderecha = binaryOp.valorUno.accept(self)
            self.registroactual = registroanterior
            self.listaactual.agregar(instruccion + registroderecha + ', ' + registroizquierda)
            self.listaactual.agregar('Jump _salida_otro_' + str(self.numerootros))
            self.listaactual.agregar('_otro_' + str(self.numerootros) + ':')
            self.listaactual.agregar('Move ' + str(condicion) + ', ' + registroizquierda)
            self.listaactual.agregar('_salida_otro_' + str(self.numerootros) + ':')
            return registroizquierda
        else:
            registroizquierda = binaryOp.valorUno.accept(self)
            registroanterior = self.registroactual
            registroderecha = binaryOp.valorDos.accept(self)
            self.registroactual = registroanterior
            if binaryOp.operador == '+':
                self.listaactual.agregar('Add ' + registroderecha + ', ' + registroizquierda)
            elif binaryOp.operador == '-':
                self.listaactual.agregar('Sub ' + registroderecha + ', ' + registroizquierda)
            elif binaryOp.operador == '*':
                self.listaactual.agregar('Mul ' + registroderecha + ', ' + registroizquierda)
            elif binaryOp.operador == '/':
                self.listaactual.agregar('Div ' + registroderecha + ', ' + registroizquierda)
            elif binaryOp.operador == '%':
                self.listaactual.agregar('Mod ' + registroderecha + ', ' + registroizquierda)
            else:
                operador = binaryOp.operador
                if operador == '==':
                    jump = 'JumpFalse '
                elif operador == '!=':
                    jump = 'JumpTrue '
                elif operador == '<':
                    jump = 'JumpGE '
                elif operador == '>':
                    jump = 'JumpLE '
                elif operador == '<=':
                    jump = 'JumpG '
                elif operador == '>=':
                    jump = 'JumpL '
                self.numerootros += 1
                self.listaactual.agregar('Compare ' + registroderecha + ', ' + registroizquierda)
                self.listaactual.agregar(jump + '_otro_' + str(self.numerootros))
                self.listaactual.agregar('Move 1, ' + registroizquierda)
                self.listaactual.agregar('Jump _salida_otro_' + str(self.numerootros))
                self.listaactual.agregar('_otro_' + str(self.numerootros) + ':')
                self.listaactual.agregar('Move 0, ' + registroizquierda)
                self.listaactual.agregar('_salida_otro_' + str(self.numerootros) + ':')
            return registroizquierda
                
                
    
    def visitarLength(self, length):
        registroexpression = length.valor.accept(self)
        self.listaactual.agregar('ArrayLength ' + registroexpression + ', ' + registroexpression)
        return registroexpression
        
    def visitarNew(self, new):
        if new.tipoNew == 0:
            registro = 'R' + str(self.registroactual)
            self.registroactual += 1
            tabla = self.tablaPrincipal.buscar(new.tipo).tabla
            numerobytes = tabla.numeroCampos * 4 + 4
            self.listaactual.agregar('Library __allocateObject(' + str(numerobytes) + '), ' + registro)
            self.listaactual.agregar('MoveField _DV_' + new.tipo + ', ' + registro + '.0')
            return registro
        else:
            registroexpression = new.valorDos.accept(self)
            self.listaactual.agregar('Mul 4, ' + registroexpression)
            self.listaactual.agregar('Library __allocateArray(' + registroexpression + '), ' + registroexpression)
            return registroexpression
        
    def visitarThis(self, this):
        registro = 'R' + str(self.registroactual)
        self.registroactual += 1
        self.listaactual.agregar('Move this, ' + registro)
        return registro
    
    def visitarStaticCall(self, staticCall):
        registrostatement = self.registroactual
        if staticCall.tipo == None:
            registro = 'Rdummy'
        else:
            registro = 'R' + str(self.registroactual)
            self.registroactual += 1
        registroanterior = self.registroactual
        if staticCall.clase == 'Library':
            if staticCall.parametros == []:
                self.listaactual.agregar('Library __' + staticCall.nombreFuncion + '(), ' + registro)
            else:
                self.listaactual.agregar('Library __' + staticCall.nombreFuncion + '(' + staticCall.parametros[0].accept(self) + '), ' + registro)
            if staticCall.statement:
                self.registroactual = registrostatement
            return registro
        parametros = []
        for expresion in staticCall.parametros:
            parametros.append(expresion.accept(self))
        llamado = 'StaticCall _' + staticCall.clase + '_' + staticCall.nombreFuncion + '('
        i = 1
        for parametro in parametros:
            if i == 1:
                llamado += 'p' + str(i) + ' = ' + parametro
            else:
                llamado += ', p' + str(i) + ' = ' + parametro
            i += 1
        llamado += '), ' + registro
        self.registroactual = registroanterior
        self.listaactual.agregar(llamado)
        if staticCall.statement:
            self.registroactual = registrostatement
        return registro
    
    def visitarVirtualCall(self, virtualCall):
        registrostatement = self.registroactual
        if virtualCall.estatico:
            return self.visitarStaticCall(virtualCall)
        if virtualCall.tipo == None:
            registro = 'Rdummy'
        else:
            registro = 'R' + str(self.registroactual)
            self.registroactual += 1
        registroanterior = self.registroactual
        if virtualCall.expresion == True:
            registroexpression = virtualCall.valorExpresion.accept(self)
        else:
            registroexpression = 'R' + str(self.registroactual)
            self.registroactual += 1
            self.listaactual.agregar('Move this, ' + registroexpression)
        parametros = []
        for expresion in virtualCall.parametros:
            parametros.append(expresion.accept(self))
        llamado = 'VirtualCall ' + registroexpression + '.' + str(virtualCall.entrada.numero) + '(this = ' + registroexpression
        i = 1
        for parametro in parametros:
            llamado += ', p' + str(i) + ' = ' + parametro
            i += 1
        llamado += '), ' + registro
        self.registroactual = registroanterior
        self.listaactual.agregar(llamado)
        if virtualCall.statement:
            self.registroactual = registrostatement
        return registro
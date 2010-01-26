from MJ.Error.CompilerError import CompilerError

# Error sintactico

class SyntacticalError(CompilerError):

    def __init__(self, args):
        print(args)
        self.mensaje = args
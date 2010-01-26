from MJ.Error.CompilerError import CompilerError

# Error semantico

class SemanticError(CompilerError):

    def __init__(self, args):
        print(args)
        self.mensaje = args
from MJ.Error.CompilerError import CompilerError

# Error lexico

class LexicalError(CompilerError):

    def __init__(self, args):
        print(args)
        self.mensaje = args
        

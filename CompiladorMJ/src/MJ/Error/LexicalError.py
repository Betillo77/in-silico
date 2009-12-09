
# Error lexico

class LexicalError(BaseException):

    def __init__(self, args):
        print(args)
        self.mensaje = args
        

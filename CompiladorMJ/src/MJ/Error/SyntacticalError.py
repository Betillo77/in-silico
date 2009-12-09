
# Error sintactico

class SyntacticalError(BaseException):

    def __init__(self, args):
        print(args)
        self.mensaje = args
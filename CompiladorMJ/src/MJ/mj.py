import Compiler

# Chequeos iniciales

if __name__ == "__main__":
    import sys
    if len(sys.argv) < 2:
        print 'Llamar por consola: mj.py <Archivo.mj>'  
    elif sys.argv[1][-3:] != '.mj':
        print 'Error archivo invalido'
    else:
        Compiler.empezar()
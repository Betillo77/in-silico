





import sys

for i in sys.stdin:
    lista = i.split()
    expr= "pow("+str(lista[1])+","+str(lista[2])+","+str(lista[3])+")"
#    print expr
    print eval(expr)

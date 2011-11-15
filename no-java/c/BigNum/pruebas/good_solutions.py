





import sys

for i in sys.stdin:
    lista = i.split()
    expr= str(lista[1])+" "+str(lista[0])+" "+str(lista[2])
    #print expr
    print eval(expr)







import sys

for i in sys.stdin:
	lista = i.split()
	base = int(lista[1])
	exponent = int(lista[2])
	if(lista[0] == "%"):
		print base % exponent
	if(lista[0] == "/"):
		print base / exponent

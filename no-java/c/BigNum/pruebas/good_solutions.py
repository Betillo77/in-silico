





import sys

def modulo(a, b):
	q = a / b
	return a - q * b
	
for i in sys.stdin:
	lista = i.split()
	base = int(lista[1])
	exponent = int(lista[2])
	mod = int(lista[3])
	ans = 1
	while(exponent > 0): 
		if((exponent & 1) == 1):
			ans = modulo((ans * base), mod)
		exponent >>= 1
		base = modulo((base * base), mod)			
	print ans

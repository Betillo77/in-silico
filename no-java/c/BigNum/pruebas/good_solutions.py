





import sys

for i in sys.stdin:
	lista = i.split()
	base = int(lista[1])
	exponent = int(lista[2])
	if(lista[0] == "%"):
		print base % exponent
	elif(lista[0] == "/"):
		print base / exponent
	elif(lista[0] == "*"):
		print base * exponent
	elif(lista[0] == "^"):
		mod = int(lista[3])
		ans = 1
		while(exponent != 0):
			if((exponent & 1) == 1):
				ans = (ans * base) % mod
			base = (base * base) % mod
			exponent = exponent >> 1
		print ans







import sys

for i in sys.stdin:
	lista = i.split()
	base = int(lista[1], 16)
	exponent = int(lista[2], 16)
	if(lista[0] == "%"):
		print hex(base % exponent)[2:-1]
	elif(lista[0] == "/"):
		print hex(base / exponent)[2:-1]
	elif(lista[0] == "*"):
		print hex(base * exponent)[2:-1]
	elif(lista[0] == "^"):
		mod = int(lista[3], 16)
		ans = 1
		while(exponent != 0):
			if((exponent & 1) == 1):
				ans = (ans * base) % mod
			base = (base * base) % mod
			exponent = exponent >> 1
		print hex(ans)[2:-1]

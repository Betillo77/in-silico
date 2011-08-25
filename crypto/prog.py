


archivo= open("texto.txt","r")

s=""
for i in archivo:
	s+=i.strip()
	
cad=""	
for i in s:	
	if i.isalpha():
		cad+=i.upper()
		
		
print cad
	
	

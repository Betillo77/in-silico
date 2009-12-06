Analizador lexico, sintactico, y semantico Minijava
Noviembre 9 de 2009

Este analizador está diseñado para el lenguaje Minijava.
La entrega actual es el analizador lexico, sintactico y semantico.

Se utilizo Python y la herramienta PLY

JERARQUIA DE ARCHIVOS:
 
	test/ -contiene las pruebas a ejecutar para el anàlisis lexico.
	src/MJ/ -directorio que contiene las clases principales, estas son:
		-mj.py: Es la clase principal y se encarga de recibir el archivo que se va a analizar.
		-Compiler.py: Ejecuta el metodo principal.
	src/MJ/Lexer/ -directorio que contiene la clase Lexer:
		      -Lexer.py: Es la clase que se encarga del analisis lexico.
	src/MJ/Parser/ -directorio que contiene la clase Parser:
		       -Parser.py: Es la clase que se encarga del analisis sintactico.
	src/MJ/SymTab/ -directorio que contiene la clase SymTab:
		       -SymTab.py: Es la clase que se encarga del analisis semantico.
	src/MJ/Error/ -directorio que contiene las clases de errores:
		      -LexicalError.py: Es la clase que se encarga de los errores lexicos.
		      -SyntacticalError.py: Es la clase que se encarga de los errores sintacticos.
		      -SemanticError.py: Es la clase que se encarga de los errores semanticos.
	src/MJ/ply/ -directorio que contiene las clases de PLY.
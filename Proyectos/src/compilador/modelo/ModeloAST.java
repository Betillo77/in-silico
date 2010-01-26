package compilador.modelo;

import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class ModeloAST 
{
	public static void main(String [] args)
	{
		 PythonInterpreter interpreter = new PythonInterpreter();
	     interpreter.exec("import Inicio");
	     interpreter.exec("from Inicio import Compiler");
	     PyObject compiler = interpreter.get("Compiler");
	     Compilador compilador = (Compilador) compiler.__call__().__tojava__(Compilador.class);
	     PyObject listaClases = compilador.empezar(new PyString("class prueba { static void main(string[] args){} }"));
	     if(listaClases instanceof PyString)
	    	 System.out.println((PyString)listaClases);
	     else
	     {
	    	 PyList lista = (PyList) listaClases;
	    	 System.out.println(lista);
	    	 NodoAst nodo = (NodoAst) lista.pop(0).__tojava__(NodoAst.class);
	    	 System.out.println(nodo.darAtributo(new PyString("nombre")));
	    	 System.out.println(nodo.darNombre());
	     }
	}
}

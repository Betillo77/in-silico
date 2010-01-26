package compilador.modelo;

import org.python.core.PyObject;
import org.python.core.PyString;

public interface NodoAst 
{
	public PyString darNombre();
	public PyObject darAtributo(PyString nombreAtributo);
}

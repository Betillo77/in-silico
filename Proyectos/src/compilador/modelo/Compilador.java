package compilador.modelo;

import org.python.core.PyObject;
import org.python.core.PyString;

public interface Compilador
{
	public PyObject empezar(PyString entrada);
}

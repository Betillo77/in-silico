package control;
import java.lang.reflect.Method;
import java.util.ArrayList;


public abstract class SistemaEstrategias
{
	static final String pathPersistencia = "";
	
	Escritor escritor;
	
	Method metodoLectura;
	
	public abstract void cargarEstrategias();

	public abstract void verificarConsistencia();
	
	protected abstract ArrayList <Senal> leer(String [] lecturas);
	
	protected abstract void procesar(ArrayList <Senal> senalesLeidas);
	
	public void iniciarProcesamiento()
	{
		try
		{
			procesar(leer((String[]) metodoLectura.invoke(null)));
		}
		catch(Exception e)
		{
    		Error.agregar(e.getMessage() + "Error en Iniciar procesamiento al procesar");
		}
	}

	public abstract void persistir();

	public abstract Estrategia darEstrategia(IdEstrategia estrategia);
}

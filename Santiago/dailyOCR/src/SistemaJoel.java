import java.io.File;
import java.util.ArrayList;


public class SistemaJoel extends SistemaEstrategias 
{
	static final String pathPrincipal = "";
	
	Estrategia joel;
	File j = new File(pathPrincipal + "joel.o");
	
	public void cargarEstrategias() 
	{
		if(j.exists())
		{
			joel = Estrategia.leer(j);
		}
		else
		{
			joel = new Estrategia(IdEstrategia.JOEL);
		}
		joel.escritor = escritor;
		try
		{
			metodoLectura = ConexionServidor.class.getMethod("leerServidorJoel");
		}
		catch (Exception e)
		{
    		Error.agregar(e.getMessage() + " Error metodo invalido en Sistema Joel");
		}
	}
	
	public void verificarConsistencia() 
	{
		if(joel == null)
		{
			cargarEstrategias();
		}
	}
	
	protected ArrayList<Senal> leer(String[] lecturas) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void procesar(ArrayList<Senal> senalesLeidas) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void persistir() 
	{
		joel.escribir(j);
	}
	
	public Estrategia darEstrategia(IdEstrategia id)
	{
		if(id == IdEstrategia.JOEL)
		{
			return joel;
		}
		return null;
	}
}

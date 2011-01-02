package modelo;

import java.util.ArrayList;

import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
import control.Error;


public abstract class SistemaEstrategias
{	
	protected IdEstrategia[] estrategias;

	protected abstract void verificarConsistencia();
	
	protected abstract ArrayList <SenalEstrategia> leer(String [] lecturas);
	
	protected abstract void procesar();
	
	public void iniciarProcesamiento()
	{
		try
		{
			procesar();
			for(IdProveedor id : IdProveedor.values())
				id.darProveedor().terminarCiclo(estrategias);
		}
		catch(Exception e)
		{
    		Error.agregar(e.getMessage() + ", error en Iniciar procesamiento al procesar en: " + getClass().getCanonicalName());
		}
	}
	
	public abstract void iniciarHilo();
	
	public abstract void persistir();
}

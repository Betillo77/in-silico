package dailyBot.modelo;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import dailyBot.control.Error;
import dailyBot.modelo.Estrategia.IdEstrategia;
import dailyBot.modelo.Proveedor.IdProveedor;

public abstract class SistemaEstrategias
{	
	protected IdEstrategia[] estrategias;
	protected ReentrantLock lockSistema = new ReentrantLock(true);

	protected abstract void verificarConsistencia();
	
	protected abstract ArrayList <SenalEstrategia> leer(String [] lecturas);
	
	protected abstract void procesar(String[] lectura);
	
	public void lockSistema()
	{
		lockSistema.lock();
	}
	
	public void unlockSistema()
	{
		lockSistema.unlock();
	}
	
	public void iniciarProcesamiento(String[] lectura)
	{
		try
		{
			procesar(lectura);
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
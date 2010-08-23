package modelo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

import control.Error;
import control.IdEstrategia;


public abstract class SistemaEstrategias
{
	protected Method metodoLectura;
	
	boolean mensajeEnviado = false, chequeoRealizado = false;
	
	public abstract void cargarEstrategias();

	public abstract void verificarConsistencia();
	
	public abstract void chequearSenales(boolean enviarMensaje);
	
	protected abstract ArrayList <Senal> leer(String [] lecturas);
	
	protected abstract void procesar(ArrayList <Senal> senalesLeidas);
	
	public void iniciarProcesamiento()
	{
		try
		{
			Calendar c = Calendar.getInstance();
			int hora = c.get(Calendar.HOUR_OF_DAY);
			int minuto = c.get(Calendar.MINUTE);
			int dia = c.get(Calendar.DAY_OF_WEEK);
			if(minuto > 10)
			{
				mensajeEnviado = chequeoRealizado = false;
			}
			else
			{
				if(!mensajeEnviado && (hora == 4 || hora == 10 || hora == 16 || hora == 22))
				{
					chequearSenales(true);
					mensajeEnviado = true;
				}
				if(!chequeoRealizado)
				{
					chequearSenales(false);
					chequeoRealizado = true;
				}
			}
			if((dia == Calendar.FRIDAY && hora > 16) || (dia == Calendar.SATURDAY))
			{
				if(dia == Calendar.FRIDAY)
					Runtime.getRuntime().exec("/home/santiago/backup");
				Thread.sleep(60000);
				Calendar actual = Calendar.getInstance();
				Error.agregar("Apagando equipo automaticamente: " + actual.get(Calendar.DAY_OF_MONTH) + "/" + (actual.get(Calendar.MONTH) + 1) + "/" + actual.get(Calendar.YEAR) + " " + actual.get(Calendar.HOUR_OF_DAY) + ":" + actual.get(Calendar.MINUTE) + ":" + actual.get(Calendar.SECOND) + "." + actual.get(Calendar.MILLISECOND));
				Thread.sleep(60000);
				Runtime.getRuntime().exec("shutdown now -P");
				System.exit(0);
			}
			procesar(leer((String[]) metodoLectura.invoke(null)));
		}
		catch(Exception e)
		{
    		Error.agregar(e.getMessage() + "Error en Iniciar procesamiento al procesar en: " + getClass().getCanonicalName());
		}
	}

	public abstract void persistir();

	public abstract Estrategia darEstrategia(IdEstrategia estrategia);

	public abstract void iniciarHilo();
}

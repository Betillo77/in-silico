package dailyBot.analisis;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import dailyBot.control.conexion.ConexionMySql;
import dailyBot.modelo.Par;
import dailyBot.modelo.Estrategia.IdEstrategia;

public class AnalisisLogica 
{
	static AtomicReference < List <RegistroHistorial> > todas = new AtomicReference < List<RegistroHistorial> > (ConexionMySql.darEntradas());
	static AtomicInteger ultimaActualizacion = new AtomicInteger(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

	public static void recargarRegistros()
	{
		todas.set(ConexionMySql.darEntradas());
	}
	
	public static List <RegistroHistorial> darRegistros()
	{
		if(ultimaActualizacion.get() != Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
		{
			recargarRegistros();
			ultimaActualizacion.set(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		}
		return todas.get();
	}
	
	public static List <RegistroHistorial> darRegistrosEstrategia(IdEstrategia id, Par par)
	{
		if(ultimaActualizacion.get() != Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
		{
			recargarRegistros();
			ultimaActualizacion.set(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		}
		LinkedList <RegistroHistorial> aDevolver = new LinkedList <RegistroHistorial> ();
		for(RegistroHistorial r : todas.get())
			if(r.id == id && !r.par.esDistinto(par))
				aDevolver.add(r);
		return aDevolver;
	}
}
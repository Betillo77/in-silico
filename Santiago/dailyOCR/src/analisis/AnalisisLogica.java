package analisis;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import modelo.Par;
import modelo.Estrategia.IdEstrategia;
import control.conexion.ConexionMySql;

public class AnalisisLogica 
{
	static AtomicReference < List <RegistroHistorial> > todas = new AtomicReference < List<RegistroHistorial> > ();
	
	static
	{
		recargarRegistros();
	}
	
	public static void recargarRegistros()
	{
		todas.set(ConexionMySql.darEntradas());
		Collections.sort(todas.get());
	}
	
	public static List <RegistroHistorial> darRegistros()
	{
		return todas.get();
	}
	
	public static List <RegistroHistorial> darRegistrosEstrategia(IdEstrategia id, Par par)
	{
		LinkedList <RegistroHistorial> aDevolver = new LinkedList <RegistroHistorial> ();
		for(RegistroHistorial r : todas.get())
			if(r.id == id && !r.par.esDistinto(par))
				aDevolver.add(r);
		return aDevolver;
	}
}
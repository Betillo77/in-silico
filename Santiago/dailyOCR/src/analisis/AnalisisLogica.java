package analisis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
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
		todas.set(ConexionMySql.darEntradas());
		Collections.sort(todas.get());
	}
	
	public static void recargarRegistros()
	{
		todas.set(ConexionMySql.darEntradas());
		Collections.sort(todas.get());
	}
	
	public static List <RegistroHistorial> darRegistrosEstrategia(IdEstrategia id, Par par)
	{
		LinkedList <RegistroHistorial> aDevolver = new LinkedList <RegistroHistorial> ();
		for(RegistroHistorial r : todas.get())
			if(r.id == id && r.par == par && r.SSI1 != 0)
				aDevolver.add(r);
		return aDevolver;
	}
	
	public static List <RegistroHistorial> Buscar(IdEstrategia historialEstrategia, long fecha, Par par)
	{	
		RegistroHistorial buscado = new RegistroHistorial();
		buscado.fechaApertura = fecha;
		int indice = Collections.binarySearch(todas.get(), buscado);
		if(indice < 0)
		{
			indice++;
			indice *= -1;
		}
		List <RegistroHistorial> nueva = new LinkedList <RegistroHistorial> ();
		List <RegistroHistorial> temporal = todas.get().subList(indice, todas.get().size());
		for(Iterator <RegistroHistorial> it = temporal.iterator(); it.hasNext();)
		{
			RegistroHistorial e = it.next();
			if(e.par.esDistinto(par) || e.id != historialEstrategia)
				continue;
			else
				nueva.add(e);
		}
		return nueva;
	}
	
	public static ArrayList <Object> retornar(IdEstrategia estrategia, Par par, int timeFrame)
	{	
		double promedio;
		long [][] datos;
		double [][] PromedioPips;
		int transacciones;
		List <RegistroHistorial> temporal = darHistorialTiempo(estrategia, par, timeFrame);
		if(temporal.size() == 0)
		{
			ArrayList <Object> objetos = new ArrayList <Object> ();
			objetos.add(new long[1][0]);
			objetos.add(0.0D);
			int a = 1;
			objetos.add(a);
			objetos.add(1L);
			objetos.add(0.0D);
			objetos.add(new double[1][0]);
			objetos.add(new ArrayList <String> ());
			objetos.add(temporal);
			return objetos;
		}
		int ganancia = 0;
		datos = new long[2][temporal.size()];
		PromedioPips = new double[2][temporal.size()];
		double desviacion = 0;
		for(int i = 0; i < temporal.size(); i++)
		{	
			ganancia += temporal.get(i).ganancia;
			datos[0][i] = ganancia;
			datos[1][i] = temporal.get(i).fechaApertura;
			PromedioPips[0][i] = ganancia / (i + 1);
			PromedioPips[1][i] = temporal.get(i).fechaApertura;	
		}
		promedio = ganancia / temporal.size();
		transacciones = temporal.size();
		double temporal1 = 0;
		for(int j = 0; j < temporal.size(); j++)
		{
			temporal1 += Math.pow(temporal.get(j).ganancia - promedio, 2);
		}
		temporal1 = temporal1 / temporal.size();
		temporal1 = Math.sqrt(temporal1);
		desviacion = temporal1;		
		List <RegistroHistorial> temporal2 = Buscar(estrategia, 0, par);
		Calendar fecha1 = Calendar.getInstance();
		fecha1.setTimeInMillis(temporal2.get(0).fechaApertura);
		Calendar actual = fecha1;
		int acumuladoActual = 0;
		ArrayList <String> meses = new ArrayList <String> ();
		for(int i = 0; i < temporal2.size(); i++)
		{
			Calendar temp = Calendar.getInstance();
			temp.setTimeInMillis(temporal2.get(i).fechaApertura);
			if(temp.get(Calendar.MONTH) != actual.get(Calendar.MONTH))
			{
				meses.add(acumuladoActual + " " + mesComoString(actual) + " " + actual.get(Calendar.YEAR));
				actual = temp;
				acumuladoActual = temporal2.get(i).ganancia;
			}
			else
				acumuladoActual += temporal2.get(i).ganancia;
		}
		meses.add(acumuladoActual + " " + mesComoString(actual) + " " + actual.get(Calendar.YEAR));
		ArrayList <Object> retornar = new ArrayList <Object> ();
		retornar.add(datos);
		retornar.add(promedio);
		retornar.add(transacciones);
		retornar.add(datos[0][temporal.size() - 1]);
		retornar.add(desviacion);
		retornar.add(PromedioPips);
		retornar.add(meses);
		retornar.add(temporal);
		return retornar;
	}

	public static List <RegistroHistorial> darHistorialTiempo(IdEstrategia historialEstrategia, Par par, int timeFrame) 
	{
		Calendar fecha = Calendar.getInstance();
		List <RegistroHistorial> temporal;
		final long semana = 1000 * 60 * 60 * 24 * 7;
		final long mes = 1000L * 60 * 60 * 24 * 30;
		switch(timeFrame)
		{
			case 0:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - semana, par);
					 break;
			case 1:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 2 * semana, par);
					 break;		
			case 2:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 3 * semana, par);
					 break;
			case 3:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - mes, par);
					 break;
			case 4:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 2 * mes, par);
					 break;
			case 5:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 3 * mes, par);
					 break;
			case 6:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 6 * mes, par);
					 break;
			case 7:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 12 * mes, par);
					 break;
			case 8:  temporal = Buscar(historialEstrategia, 0, par);
					 break;
			default: temporal = null;
		}
		return temporal;
	}


	public static String mesComoString(Calendar calendario)
	{
		switch(calendario.get(Calendar.MONTH))
		{
			case Calendar.JANUARY:   return "Enero";
			case Calendar.FEBRUARY:  return "Febrero";
			case Calendar.MARCH: 	 return "Marzo";
			case Calendar.APRIL: 	 return "Abril";
			case Calendar.MAY: 		 return "Mayo";
			case Calendar.JUNE:	 	 return "Junio";
			case Calendar.JULY: 	 return "Julio";
			case Calendar.AUGUST: 	 return "Agosto";
			case Calendar.SEPTEMBER: return "Septiembre";
			case Calendar.OCTOBER: 	 return "Octubre";
			case Calendar.NOVEMBER:  return "Noviembre";
			case Calendar.DECEMBER:  return "Diciembre";
			default:                 return "ERROR";
		}
	}
}


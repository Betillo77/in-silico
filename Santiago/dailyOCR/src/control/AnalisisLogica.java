package control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class AnalisisLogica 
{
	public static  ArrayList <Entrada> Buscar(HistorialEstrategia historialEstrategia, long fecha, Par par)
	{	
		List <Entrada> temporal;
		int indice = Collections.binarySearch(historialEstrategia.darHistorial(), new Entrada(Par.AUDJPY, fecha, 0));
		if(indice < 0)
		{
			indice++;
			indice *= -1;
		}	
		temporal = clonarArrayList(historialEstrategia.darHistorial()).subList(indice, historialEstrategia.darHistorial().size());
		for(int i = 0; i < temporal.size(); i++)
		{
			if(temporal.get(i).par.esDistinto(par))
			{
				temporal.remove(i);
				i--;
			}
		}
		return new ArrayList <Entrada> (temporal);
	}

	private static ArrayList <Entrada> clonarArrayList(ArrayList <Entrada> aClonar)
	{
		ArrayList <Entrada> clon = new ArrayList <Entrada> ();
		for(Entrada e : aClonar)
			clon.add(e);
		return clon;
	}
	
	public static ArrayList <Object> retornar(HistorialEstrategia historialEstrategia, Par par,int timeFrame)
	{
		double promedio;
		long [][] datos;
		double [][] PromedioPips;
		int transacciones;
		ArrayList <Entrada> temporal = new ArrayList <Entrada> ();
		temporal = darHistorialTiempo(historialEstrategia, par, timeFrame);
		int ganancia = 0;
		datos = new long[2][temporal.size()];
		PromedioPips = new double[2][temporal.size()];
		double desviacion = 0;
		for(int i = 0; i < temporal.size(); i++)
		{	
			ganancia += temporal.get(i).ganancia;
			datos[0][i] = ganancia;
			datos[1][i] = temporal.get(i).fecha;
			PromedioPips[0][i] = ganancia / (i + 1);
			PromedioPips[1][i] = temporal.get(i).fecha;	
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
		ArrayList<Entrada> temporal2 = Buscar(historialEstrategia, 0, par);
		Calendar fecha1 = Calendar.getInstance();
		fecha1.setTimeInMillis(temporal2.get(0).fecha);
		Calendar actual = fecha1;
		int acumuladoActual = 0;
		ArrayList <String> meses = new ArrayList <String> ();
		for(int i = 0; i < temporal2.size(); i++)
		{
			Calendar temp = Calendar.getInstance();
			temp.setTimeInMillis(temporal2.get(i).fecha);
			if(temp.get(Calendar.MONTH) != actual.get(Calendar.MONTH))
			{
				meses.add(acumuladoActual + " " + mesComoString(actual) + " " + actual.get(Calendar.YEAR));
				actual = temp;
				acumuladoActual = 0;
			}
			else
			{
				acumuladoActual += temporal2.get(i).ganancia;
			}
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
		return retornar;
	}

	public static ArrayList <Entrada> darHistorialTiempo(HistorialEstrategia historialEstrategia, Par par, int timeFrame) 
	{
		Calendar fecha = Calendar.getInstance();
		ArrayList <Entrada> temporal;
		final long semana = 1000 * 60 * 60 * 24 * 7;
		switch(timeFrame)
		{
			case 0:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - semana, par);
					 break;
			case 1:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 2 * semana, par);
					 break;		
			case 2:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 3 * semana, par);
					 break;
			case 3:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 4 * semana, par);
					 break;
			case 4:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 8 * semana, par);
					 break;
			case 5:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 13 * semana, par);
					 break;
			case 6:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 26 * semana, par);
					 break;
			case 7:  temporal = Buscar(historialEstrategia, fecha.getTimeInMillis() - 52 * semana, par);
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

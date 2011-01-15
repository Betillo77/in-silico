package dailyBot.analisis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedMap;

import dailyBot.control.conexion.ConexionMySql;
import dailyBot.modelo.Par;

public class Estadistica 
{
	public static class EntradaHistoriaPares
	{
		public Date fecha;
		public double high;
		public double low;
		public double open;
		public double close;
		
		public EntradaHistoriaPares(Date f, double l, double h, double o, double c)
		{
			fecha = f;
			low = l;
			high = h;
			open = o;
			close = c;
		}
	}

	public static double calcularEMA(ArrayList <Double> valores, int periodo)
	{
		double acumulado = 0;
		for(int i = 0; i < 14; i++)
			acumulado += valores.get(i);
		acumulado /= 14;
		double a = 2.0d / (periodo + 1.0d);
		for(int i = 14; i < valores.size(); i++)
			acumulado = a * valores.get(i) + (1 - a) * acumulado;
		return acumulado;
	}
	
	public static double calcularATR(Par par, int periodo, long fechaCierre)
	{
		ArrayList <Double> valores = new ArrayList <Double> ();
		SortedMap <Date, EntradaHistoriaPares> mapa = ConexionMySql.darHistoriaPares(par, new Date(fechaCierre));
		double cierreAnterior = 0;
		int i = 0;
		for(EntradaHistoriaPares actual : mapa.values())
		{
			if(i++ == 0)
			{
				cierreAnterior = actual.close;
				continue;
			}
			valores.add(Math.max((actual.high - actual.low) * par.multiplicador(), Math.max(Math.abs(actual.high - cierreAnterior) * par.multiplicador(), Math.abs(actual.low - cierreAnterior) * par.multiplicador())));
			cierreAnterior = actual.close;
		}
		return Estadistica.calcularEMA(valores, periodo);
	}
	
	public static double calcularATR(Par par, int periodo)
	{
		return calcularATR(par, periodo, Calendar.getInstance().getTimeInMillis());
	}
	
	public static double calcularRSI(Par par, int periodo, long fechaCierre)
	{
		ArrayList <Double> valores = new ArrayList <Double> ();
		SortedMap <Date, EntradaHistoriaPares> mapa = ConexionMySql.darHistoriaPares(par, new Date(fechaCierre));
		double cierreAnterior = 0;
		int i = 0;
		for(EntradaHistoriaPares actual : mapa.values())
		{
			if(i++ == 0)
			{
				cierreAnterior = actual.close;
				continue;
			}
			double nuevo = actual.close - cierreAnterior;
			if(nuevo > 0)
				valores.add(nuevo);
			else
				valores.add(0d);
			cierreAnterior = actual.close;
		}
		double emaU = Estadistica.calcularEMA(valores, periodo);
		cierreAnterior = 0;
		valores.clear();
		i = 0;
		for(EntradaHistoriaPares actual : mapa.values())
		{
			if(i++ == 0)
			{
				cierreAnterior = actual.close;
				continue;
			}
			double nuevo = actual.close - cierreAnterior;
			if(nuevo < 0)
				valores.add(-nuevo);
			else
				valores.add(0d);
			cierreAnterior = actual.close;
		}
		double emaD = Estadistica.calcularEMA(valores, periodo);
		double rS = emaU / emaD;
		return emaD < 1e-8 ? 100 : 100d - 100d / (1d + rS);
	}

}

package dailyBot.analisis;

import java.util.Calendar;
import java.util.Hashtable;

import javax.swing.JLabel;

import dailyBot.analisis.Rangos.Rango;


public enum Indicador 
{
	COMPRA(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial registro)
		{
			return registro.compra ? 1 : 0;
		}
	}, new Rango(0, 2, 0, 2), 1, true, new Object[][] {{0, "venta"}, {1, "compra"}, {2, "ambos"}}),
	
	TIEMPO(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial registro) 
		{
			Calendar fecha = Calendar.getInstance();
			final long semana = 1000L * 60 * 60 * 24 * 7;
			final long mes = 1000L * 60 * 60 * 24 * 30;
			if(registro.fechaApertura > fecha.getTimeInMillis() - semana)
				return 0;
			if(registro.fechaApertura > fecha.getTimeInMillis() - 2 * semana)
				return 1;
			if(registro.fechaApertura > fecha.getTimeInMillis() - 3 * semana)
				return 2;
			if(registro.fechaApertura > fecha.getTimeInMillis() - mes)
				return 3;
			if(registro.fechaApertura > fecha.getTimeInMillis() - 2 * mes)
				return 4;
			if(registro.fechaApertura > fecha.getTimeInMillis() - 3 * mes)
				return 5;
			if(registro.fechaApertura > fecha.getTimeInMillis() - 6 * mes)
				return 6;
			if(registro.fechaApertura > fecha.getTimeInMillis() - 12 * mes)
				return 7;
			return 8;
		}
	}, new Rango(0, 8, 0, 8), 1, true, new Object[][] {{0, "1s"}, {1, "2s"},
												 {2, "3s"}, {3, "1m"}, 
												 {4, "2m"}, {5, "3m"}, 
												 {6, "6m"}, {7, "1a"}, 
												 {8, "t"}}),

	VIX(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial registro) 
		{
			return registro.VIX;
		}
		
	}, new Rango(0, 50, 0, 50), 2, false),
	
	SSI(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial r)
		{
			if(r.par.darPadreUno() == r.par.darPadreDos())
				return r.compra ? -r.SSI1 * 100 : r.SSI1 * 100;
			double ssi1 = r.SSI1;
			double ssi2 = r.SSI2;
			switch(r.par.darPadreUno())
			{
				case USDCAD:
				case USDCHF:
				case USDJPY: ssi1 = -ssi1; break;
			}
			switch(r.par.darPadreDos())
			{
				case USDCAD:
				case USDCHF:
				case USDJPY: ssi2 = -ssi2; break;
			}
			double ssi = ssi1 - ssi2;
			if(r.compra)
				ssi = -ssi;
			return 100 * ssi;
		}
		
	}, new Rango(-500, 500, -500, 500), 50, false, new Object[][] {{-500, "-500"}, {500, "500"}}),
	
	ATR(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial registro)
		{
			return registro.ATR;
		}
		
	}, new Rango(0, 400, 0, 400), 25, false),
	
	RSI(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial registro)
		{
			return registro.RSI;
		}
		
	}, new Rango(0, 100, 0, 100), 5, false);
	
	private interface Calculable
	{
		double calcular(RegistroHistorial registro);
	}
	
	Calculable calculo;
	Rango rango;
	int espaciado;
	boolean esInfo;
	boolean tieneLabels;
	Hashtable <Integer, JLabel> labels;
	
	private Indicador(Calculable c, Rango r, int es, boolean info)
	{
		calculo = c;
		rango = r;
		espaciado = es;
		esInfo = info;
		tieneLabels = false;
	}
	
	private Indicador(Calculable c, Rango r, int es, boolean info, Object[][] aLabels)
	{
		this(c, r, es, info);
		tieneLabels = true;
		labels = new Hashtable <Integer, JLabel> ();
		for(Object[] l : aLabels)
			labels.put(new Integer((Integer) l[0]), new JLabel((String) l[1]));
	}
	
	public Rango darRango()
	{
		return rango;
	}
	
	public int darEspaciado() 
	{
		return espaciado;
	}
	
	public Hashtable <Integer, JLabel> darLabels()
	{
		return labels;
	}
	
	public boolean tieneLabels()
	{
		return tieneLabels;
	}
	
	public double calcular(RegistroHistorial registro)
	{
		double resultado = calculo.calcular(registro);
		double minimo = registro.compra ? rango.getMinimoCompra() : rango.getMinimoVenta();
		double maximo = registro.compra ? rango.getMaximoCompra() : rango.getMaximoVenta();
		if(resultado < minimo)
			resultado = minimo;
		if(resultado > maximo)
			resultado = maximo;
		return resultado;
	}
}
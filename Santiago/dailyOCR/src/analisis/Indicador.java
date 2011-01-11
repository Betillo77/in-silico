package analisis;

import analisis.Rangos.Rango;

public enum Indicador 
{
	VIX(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial registro) 
		{
			return registro.VIX;
		}
		
	}, new Rango(0, 50), 2),
	
	SSI(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial r)
		{
			if(r.par.darPadreUno() == r.par.darPadreDos())
				return r.compra ? -r.SSI1 : r.SSI1;
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
			return 2 * ssi;
		}
		
	}, new Rango(-10, 22), 1),
	
	ATR(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial registro)
		{
			return registro.ATR;
		}
		
	}, new Rango(0, 400), 25),
	
	RSI(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial registro)
		{
			return registro.RSI;
		}
		
	}, new Rango(0, 100), 5);
	
	interface Calculable
	{
		double calcular(RegistroHistorial registro);
	}
	
	Calculable calculo;
	Rango rango;
	int espaciado;
	
	private Indicador(Calculable c, Rango r, int es)
	{
		calculo = c;
		rango = r;
		espaciado = es;
	}
	
	
	public Rango darRango()
	{
		return rango;
	}
	
	public int darEspaciado() 
	{
		return espaciado;
	}
	
	public double calcular(RegistroHistorial registro)
	{
		return calculo.calcular(registro);
	}
}

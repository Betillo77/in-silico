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
		
	}, new Rango(0, 100)),
	
	SSI(new Calculable()
	{
		@Override
		public double calcular(RegistroHistorial registro)
		{
			return registro.SSI1;
		}
		
	}, new Rango(-20, 20));
	
	interface Calculable
	{
		double calcular(RegistroHistorial registro);
	}
	
	Calculable calculo;
	Rango rango;
	
	private Indicador(Calculable c, Rango r)
	{
		calculo = c;
		rango = r;
	}
	
	public double calcular(RegistroHistorial registro)
	{
		return calculo.calcular(registro);
	}
}

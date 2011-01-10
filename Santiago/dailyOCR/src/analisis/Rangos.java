package analisis;

import java.util.EnumMap;

public class Rangos 
{
	static public class Rango
	{
		double minimo;
		double maximo;
		
		public Rango(double min, double max)
		{
			minimo = min;
			maximo = max;
		}
		
		public Rango duplicar()
		{
			return new Rango(minimo, maximo);
		}
		
		public boolean estaDentro(double resultado)
		{
			return minimo <= resultado && resultado <= maximo;
		}
	}
	
	EnumMap <Indicador, Rango> rangos = new EnumMap <Indicador, Rango> (Indicador.class);

	public Rangos()
	{
		for(Indicador i : Indicador.values())
			rangos.put(i, i.rango.duplicar());
	}
	
	public EnumMap <Indicador, Rango> getRangos()
	{
		return rangos;
	}

	public void setRangos(EnumMap <Indicador, Rango> rangos) 
	{
		this.rangos = rangos;
		for(Indicador i : Indicador.values())
			if(!rangos.containsKey(i))
				rangos.put(i, i.rango.duplicar());
	}
	
	public Rango darRango(Indicador i)
	{
		if(!rangos.containsKey(i))
			rangos.put(i, i.rango.duplicar());
		return i.rango;
	}
	
	public boolean cumple(RegistroHistorial registro)
	{
		for(Indicador i : Indicador.values())
		{
			if(!rangos.containsKey(i))
				rangos.put(i, i.rango.duplicar());
			if(!rangos.get(i).estaDentro(i.calcular(registro)))
				return false;
		}
		return true;
	}
}

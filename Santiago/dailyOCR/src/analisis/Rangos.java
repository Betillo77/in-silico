package analisis;

import java.util.EnumMap;

public class Rangos 
{
	static public class Rango
	{
		public boolean invertido;
		public double minimo;
		public double maximo;
		
		public Rango(double min, double max, boolean invertido)
		{
			minimo = min;
			maximo = max;
		}
		
		public Rango(double min, double max)
		{
			this(min, max, false);
		}
		
		public Rango duplicar()
		{
			return new Rango(minimo, maximo, invertido);
		}
		
		public boolean estaDentro(double resultado)
		{
			return !invertido ? minimo <= resultado && resultado <= maximo : minimo >= resultado || resultado >= maximo;
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
		return rangos.get(i);
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

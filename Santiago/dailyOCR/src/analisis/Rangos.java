package analisis;

import java.util.EnumMap;

public class Rangos 
{
	static public class Rango
	{
		private boolean invertido;
		private double minimo;
		private double maximo;
		
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

		public synchronized void setInvertido(boolean invertido) 
		{
			this.invertido = invertido;
		}
		
		public synchronized boolean isInvertido() 
		{
			return invertido;
		}

		public synchronized void setMinimo(double minimo) 
		{
			this.minimo = minimo;
		}
		
		public synchronized double getMinimo() 
		{
			return minimo;
		}

		public synchronized void setMaximo(double maximo) 
		{
			this.maximo = maximo;
		}
		
		public synchronized double getMaximo() 
		{
			return maximo;
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
	
	public boolean cumple(RegistroHistorial registro, boolean ignorarInfo)
	{
		for(Indicador i : Indicador.values())
		{
			if(!rangos.containsKey(i))
				rangos.put(i, i.rango.duplicar());
			if(ignorarInfo && i.esInfo)
				continue;
			if(!rangos.get(i).estaDentro(i.calcular(registro)))
				return false;
		}
		return true;
	}
}

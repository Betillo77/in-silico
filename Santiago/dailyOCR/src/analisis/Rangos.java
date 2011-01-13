package analisis;

import java.io.Serializable;

import java.util.EnumMap;
import control.Error;

public class Rangos implements Serializable
{
	private static final long serialVersionUID = -6461964459317876445L;

	static public class Rango implements Serializable
	{
		private static final long serialVersionUID = 3013772711096500473L;
		
		private boolean invertido;
		private double minimo;
		private double maximo;
		
		public Rango(double min, double max, boolean in)
		{
			minimo = min;
			maximo = max;
			invertido = in;
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
		
		public String toString(double valor) 
		{
			if(invertido)
				return valor + " <= " + minimo + " or " + valor + " >= " + maximo;
			else
				return minimo + " <= " + valor + " <= " + maximo;
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
	
	public void cambiarRango(Indicador i, Rango rango)
	{
		if(!rangos.containsKey(i))
			rangos.put(i, i.rango.duplicar());
		Rango aCambiar = rangos.get(i);
		aCambiar.setMinimo(rango.getMinimo());
		aCambiar.setMaximo(rango.getMaximo());
		aCambiar.setInvertido(rango.isInvertido());
	}
	
	public boolean cumple(RegistroHistorial registro, boolean ignorarInfo)
	{
		String mensaje = registro.toString() + "\n";
		for(Indicador i : Indicador.values())
		{
			mensaje += i.toString();
			if(!rangos.containsKey(i))
				rangos.put(i, i.rango.duplicar());
			if(ignorarInfo && i.esInfo)
			{
				mensaje += ", ignorando: " + rangos.get(i).toString(i.calcular(registro)) + "\n";
				continue;
			}
			if(!rangos.get(i).estaDentro(i.calcular(registro)))
			{
				mensaje += ", no cumple: " + rangos.get(i).toString(i.calcular(registro)) + ", terminando con false\n";
				Error.agregarInfo(mensaje);
				return false;
			}
			else
			{
				mensaje += ", cumple: " + rangos.get(i).toString(i.calcular(registro)) + "\n";
			}
		}
		Error.agregarInfo(mensaje);
		return true;
	}
}

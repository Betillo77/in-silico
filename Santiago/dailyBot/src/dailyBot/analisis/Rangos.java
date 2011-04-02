package dailyBot.analisis;

import java.io.Serializable;

import java.util.EnumMap;

import dailyBot.control.Error;

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
		
		public synchronized Rango duplicar()
		{
			return new Rango(minimo, maximo, invertido);
		}
		
		public synchronized boolean estaDentro(double resultado)
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
		
		public synchronized String toString(double valor) 
		{
			if(invertido)
				return valor + " <= " + minimo + " or " + valor + " >= " + maximo;
			else
				return minimo + " <= " + valor + " <= " + maximo;
		}
	}
	
	EnumMap <Indicador, Rango> rangosCompra = new EnumMap <Indicador, Rango> (Indicador.class);
	EnumMap <Indicador, Rango> rangosVenta = new EnumMap <Indicador, Rango> (Indicador.class);

	public Rangos()
	{
		for(Indicador i : Indicador.values())
		{
			rangosCompra.put(i, i.rango.duplicar());
			rangosVenta.put(i, i.rango.duplicar());
		}
	}
	
	public EnumMap <Indicador, Rango> getRangosCompra()
	{
		return rangosCompra;
	}
	
	public EnumMap <Indicador, Rango> getRangosVenta()
	{
		return rangosVenta;
	}

	public void setRangosCompra(EnumMap <Indicador, Rango> rangos) 
	{
		rangosCompra = rangos;
		for(Indicador i : Indicador.values())
			if(!rangosCompra.containsKey(i))
				rangosCompra.put(i, i.rango.duplicar());
	}
	
	public void setRangosVenta(EnumMap <Indicador, Rango> rangos) 
	{
		rangosVenta = rangos;
		for(Indicador i : Indicador.values())
			if(!rangosVenta.containsKey(i))
				rangosVenta.put(i, i.rango.duplicar());
	}
	
	public Rango darRangoCompra(Indicador i)
	{
		if(!rangosCompra.containsKey(i))
			rangosCompra.put(i, i.rango.duplicar());
		return rangosCompra.get(i);
	}
	
	public Rango darRangoVenta(Indicador i)
	{
		if(!rangosVenta.containsKey(i))
			rangosVenta.put(i, i.rango.duplicar());
		return rangosVenta.get(i);
	}
	
	public void cambiarRangoCompra(Indicador i, Rango rango)
	{
		if(!rangosCompra.containsKey(i))
			rangosCompra.put(i, i.rango.duplicar());
		Rango aCambiar = rangosCompra.get(i);
		aCambiar.setMinimo(rango.getMinimo());
		aCambiar.setMaximo(rango.getMaximo());
		aCambiar.setInvertido(rango.isInvertido());
	}
	
	public void cambiarRangoVenta(Indicador i, Rango rango)
	{
		if(!rangosVenta.containsKey(i))
			rangosVenta.put(i, i.rango.duplicar());
		Rango aCambiar = rangosVenta.get(i);
		aCambiar.setMinimo(rango.getMinimo());
		aCambiar.setMaximo(rango.getMaximo());
		aCambiar.setInvertido(rango.isInvertido());
	}
	
	public boolean cumple(RegistroHistorial registro, boolean ignorarInfo, String enviarMensaje)
	{
		String mensaje = enviarMensaje + "\n\n" + registro.toString() + "\n\n";
		for(Indicador i : Indicador.values())
		{
			mensaje += i.toString();
			if(!rangosCompra.containsKey(i))
				rangosCompra.put(i, i.rango.duplicar());
			if(!rangosVenta.containsKey(i))
				rangosVenta.put(i, i.rango.duplicar());
			EnumMap <Indicador, Rango> rangos = registro.compra ? rangosCompra : rangosVenta;
			if(ignorarInfo && i.esInfo)
			{
				mensaje += ", ignorando: " + rangos.get(i).toString(i.calcular(registro)) + "\n";
				continue;
			}
			if(!rangos.get(i).estaDentro(i.calcular(registro)))
			{
				mensaje += ", no cumple: " + rangos.get(i).toString(i.calcular(registro)) + ", terminando con false\n";
				if(!enviarMensaje.equals(""))
					Error.agregarConTitulo("rangos", mensaje);
				return false;
			}
			else
			{
				mensaje += ", cumple: " + rangos.get(i).toString(i.calcular(registro)) + "\n";
			}
		}
		if(!enviarMensaje.equals(""))
			Error.agregarConTitulo("rangos", mensaje);
		return true;
	}
	
	public Rangos duplicar()
	{
		Rangos nuevos = new Rangos();
		for(Indicador i : Indicador.values())
		{
			if(!rangosCompra.containsKey(i))
				rangosCompra.put(i, i.rango.duplicar());
			if(!rangosVenta.containsKey(i))
				rangosVenta.put(i, i.rango.duplicar());
			Rango este = rangosCompra.get(i);
			nuevos.cambiarRangoCompra(i, este);
			este = rangosVenta.get(i);
			nuevos.cambiarRangoVenta(i, este);
		}
		return nuevos;
	}
}
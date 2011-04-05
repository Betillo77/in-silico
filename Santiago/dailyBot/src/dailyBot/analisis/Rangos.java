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
		private double minimoCompra;
		private double maximoCompra;
		private double minimoVenta;
		private double maximoVenta;
		
		public Rango()
		{
		}
		
		public Rango(double minC, double maxC, double minV, double maxV, boolean in)
		{
			minimoCompra = minC;
			maximoCompra = maxC;
			minimoVenta = minV;
			maximoVenta = maxV;
			invertido = in;
		}
		
		public Rango(double minC, double maxC, double minV, double maxV)
		{
			this(minC, maxC, minV, maxV, false);
		}
		
		public synchronized Rango duplicar()
		{
			return new Rango(minimoCompra, maximoCompra, minimoVenta, maximoVenta, invertido);
		}
		
		public synchronized boolean estaDentro(double resultado, boolean compra)
		{
			double minimo = compra ? minimoCompra : minimoVenta;
			double maximo = compra ? maximoCompra : maximoVenta;
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

		public synchronized void setMinimoCompra(double minimo) 
		{
			this.minimoCompra = minimo;
		}
		
		public synchronized double getMinimoCompra() 
		{
			return minimoCompra;
		}

		public synchronized void setMinimoVenta(double minimo) 
		{
			this.minimoVenta = minimo;
		}
		
		public synchronized double getMinimoVenta() 
		{
			return minimoVenta;
		}

		public synchronized void setMaximoCompra(double maximo) 
		{
			this.maximoCompra = maximo;
		}
		
		public synchronized double getMaximoCompra() 
		{
			return maximoCompra;
		}

		public synchronized void setMaximoVenta(double maximo) 
		{
			this.maximoVenta = maximo;
		}
		
		public synchronized double getMaximoVenta() 
		{
			return maximoVenta;
		}
		
		public synchronized String toString(double valor, boolean compra) 
		{
			double minimo = compra ? minimoCompra : minimoVenta;
			double maximo = compra ? maximoCompra : maximoVenta;
			if(invertido)
				return valor + " <= " + minimo + " or " + valor + " >= " + maximo;
			else
				return minimo + " <= " + valor + " <= " + maximo;
		}

		public double getMinimo(Rangos rangos) 
		{
			boolean compra = ((int) rangos.darRango(Indicador.COMPRA).getMinimoCompra()) == 1;
			return compra ? minimoCompra : minimoVenta;
		}

		public double getMaximo(Rangos rangos) 
		{
			boolean compra = ((int) rangos.darRango(Indicador.COMPRA).getMinimoCompra()) == 1;
			return compra ? maximoCompra : maximoVenta;
		}

		public void setMinimo(double valor, Rangos rangos)
		{
			boolean compra = ((int) rangos.darRango(Indicador.COMPRA).getMinimoCompra()) == 1;
			if(compra)
				minimoCompra = valor;
			else
				minimoVenta = valor;
				
		}

		public void setMaximo(double valor, Rangos rangos) 
		{
			boolean compra = ((int) rangos.darRango(Indicador.COMPRA).getMinimoCompra()) == 1;
			if(compra)
				maximoCompra = valor;
			else
				maximoVenta = valor;
		}
	}
	
	EnumMap <Indicador, Rango> rangos = new EnumMap <Indicador, Rango> (Indicador.class);

	public Rangos()
	{
		for(Indicador i : Indicador.values())
		{
			rangos.put(i, i.rango.duplicar());
		}
	}
	
	public EnumMap <Indicador, Rango> getRangos()
	{
		return rangos;
	}

	public void setRangos(EnumMap <Indicador, Rango> rangosE) 
	{
		rangos = rangosE;
		rangos.put(Indicador.COMPRA, Indicador.COMPRA.darRango().duplicar());
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
		aCambiar.setMinimoCompra(rango.getMinimoCompra());
		aCambiar.setMaximoCompra(rango.getMaximoCompra());
		aCambiar.setMinimoVenta(rango.getMinimoVenta());
		aCambiar.setMaximoVenta(rango.getMaximoVenta());
		aCambiar.setInvertido(rango.isInvertido());
	}
	
	public boolean cumple(RegistroHistorial registro, boolean ignorarInfo, String enviarMensaje)
	{
		String mensaje = enviarMensaje + "\n\n" + registro.toString() + "\n\n";
		for(Indicador i : Indicador.values())
		{
			mensaje += i.toString();
			if(!rangos.containsKey(i))
				rangos.put(i, i.rango.duplicar());
			if(ignorarInfo && i.esInfo)
			{
				mensaje += ", ignorando: " + rangos.get(i).toString(i.calcular(registro), registro.compra) + "\n";
				continue;
			}
			if(i == Indicador.COMPRA)
			{
				int valor = (int) rangos.get(Indicador.COMPRA).getMinimoCompra();
				if(valor != 2 && valor == 0 && registro.compra)
					return false;
				if(valor != 2 && valor == 1 && !registro.compra)
					return false;
			}
			else if(!rangos.get(i).estaDentro(i.calcular(registro), registro.compra))
			{
				mensaje += ", no cumple: " + rangos.get(i).toString(i.calcular(registro), registro.compra) + ", terminando con false\n";
				if(!enviarMensaje.equals(""))
					Error.agregarConTitulo("rangos", mensaje);
				return false;
			}
			else
			{
				mensaje += ", cumple: " + rangos.get(i).toString(i.calcular(registro), registro.compra) + "\n";
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
			if(!rangos.containsKey(i))
				rangos.put(i, i.rango.duplicar());
			Rango este = rangos.get(i);
			nuevos.cambiarRango(i, este.duplicar());
		}
		return nuevos;
	}
}
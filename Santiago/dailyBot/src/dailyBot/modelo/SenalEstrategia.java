package dailyBot.modelo;

import java.io.Serializable;

import dailyBot.control.conexion.dailyFx.ConexionServidorDailyFx;
import dailyBot.modelo.Estrategia.IdEstrategia;

public class SenalEstrategia implements Serializable
{	
	private static final long serialVersionUID = 771057177601632118L;
	
	private IdEstrategia estrategia;
	private boolean compra;
	private boolean compraPuesta;
	private Par par;
	private int numeroLotes;
	private double precioEntrada;
	private double VIX;
	private double SSI1;
	private double SSI2;
	private long fechaInicio;
	private int low = Short.MAX_VALUE;
	private int high = Short.MIN_VALUE;
	private boolean tocoStop = false;
	private double stop;
	private double stopDaily = -1;
	
	public SenalEstrategia()
	{
		estrategia = null;
		compra = false;
		par = null;
		precioEntrada = Double.NEGATIVE_INFINITY;
		VIX = Double.NEGATIVE_INFINITY;
		SSI1 = Double.NEGATIVE_INFINITY;
		SSI2 = Double.NEGATIVE_INFINITY;
		fechaInicio = Long.MIN_VALUE;
		compraPuesta = false;
	}
    
	public SenalEstrategia(IdEstrategia estrategia, boolean compra, Par par, int numeroLotes, double precioEntrada, double stop)
	{
		this.estrategia = estrategia;
		this.compra = compra;
		this.par = par;
		this.numeroLotes = numeroLotes;
		this.precioEntrada = precioEntrada;
		this.VIX = ConexionServidorDailyFx.darVIX();
		this.SSI1 = par.darPadreUno().darSSI();
		this.SSI2 = par.darPadreDos().darSSI();
		this.fechaInicio = System.currentTimeMillis();
		this.stop = stop;
		this.compraPuesta = true;
	}
	
	public void setEstrategia(IdEstrategia estrategia) 
	{
		if(this.estrategia != null)
			throw new UnsupportedOperationException("Campo estrategia de SenalEstrategia es inmutable");
		this.estrategia = estrategia;
	}
    
	public IdEstrategia getEstrategia()
	{
		return estrategia;
	}

	public void setCompra(boolean compra) 
	{
		if(compraPuesta)
			throw new UnsupportedOperationException("Campo compra de SenalEstrategia es inmutable");
		this.compra = compra;
		this.compraPuesta = true;
	}
	
	public boolean isCompra() 
	{
		return compra;
	}

	public void setPar(Par par) 
	{
		if(this.par != null)
			throw new UnsupportedOperationException("Campo par de SenalEstrategia es inmutable");
		this.par = par;
	}

	public Par getPar() 
	{
		return par;
	}

	public void setPrecioEntrada(double precioEntrada) 
	{
		if(this.precioEntrada != Double.NEGATIVE_INFINITY)
			throw new UnsupportedOperationException("Campo precioEntrada de SenalEstrategia es inmutable");
		this.precioEntrada = precioEntrada;
	}
	
	public double getPrecioEntrada() 
	{
		return precioEntrada;
	}

	public void setVIX(double vIX) 
	{
		if(this.VIX != Double.NEGATIVE_INFINITY)
			throw new UnsupportedOperationException("Campo VIX de SenalEstrategia es inmutable");
		VIX = vIX;
	}
	
	public double getVIX() 
	{
		return VIX;
	}

	public void setSSI1(double sSI1) 
	{
		if(this.SSI1 != Double.NEGATIVE_INFINITY)
			throw new UnsupportedOperationException("Campo SSI1 de SenalEstrategia es inmutable");
		SSI1 = sSI1;
	}
	
	public double getSSI1() 
	{
		return SSI1;
	}

	public void setSSI2(double sSI2) 
	{
		if(this.SSI2 != Double.NEGATIVE_INFINITY)
			throw new UnsupportedOperationException("Campo SSI2 de SenalEstrategia es inmutable");
		SSI2 = sSI2;
	}

	public double getSSI2() 
	{
		return SSI2;
	}

	public void setFechaInicio(long fechaInicio) 
	{
		if(this.fechaInicio != Long.MIN_VALUE)
			throw new UnsupportedOperationException("Campo fechaInicio de SenalEstrategia es inmutable");
		this.fechaInicio = fechaInicio;
	}

	public long getFechaInicio() 
	{
		return fechaInicio;
	}
	
	public int darGanancia() 
	{
		return par.diferenciaPips(precioEntrada, compra);
	}
	
	public synchronized void setNumeroLotes(int numeroLotes) 
	{
		this.numeroLotes = numeroLotes;
	}
	
	public synchronized int getNumeroLotes() 
	{
		return numeroLotes;
	}
	
	public synchronized void setLow(int low) 
	{
		this.low = low;
	}

	public synchronized int getLow() 
	{
		return low;
	}

	public synchronized void setHigh(int high) 
	{
		this.high = high;
	}

	public synchronized int getHigh() 
	{
		return high;
	}

	public synchronized void setTocoStop(boolean tocoStop) 
	{
		this.tocoStop = tocoStop;
	}

	public synchronized boolean isTocoStop() 
	{
		return tocoStop;
	}
	
	public synchronized void ponerStop(double stop) 
	{
		this.stop = stop;
	}

	public synchronized double darStop() 
	{
		return stop;
	}
	
	public synchronized void ponerStopDaily(double stopDaily) 
	{
		this.stopDaily = stopDaily;
	}

	public synchronized double darStopDaily() 
	{
		return stopDaily;
	}
	
	@Override
	public String toString()
	{
		int numeroLotes;
		double stop;
		synchronized(this)
		{
			numeroLotes = this.numeroLotes;
			stop = this.stop;
		}
		return estrategia + " " + (compra ? "Compra" : "Venta") + " " + numeroLotes + " Lotes de " + par + " a: " + precioEntrada + " Stop: " + stop; 
	}
}
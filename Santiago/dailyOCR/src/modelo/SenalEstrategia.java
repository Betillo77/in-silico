package modelo;

import modelo.Estrategia.IdEstrategia;
import control.conexion.dailyFx.ConexionServidorDailyFx;

public class SenalEstrategia
{	
	private IdEstrategia estrategia;
	private boolean compra;
	private Par par;
	private int numeroLotes;
	private double precioEntrada;
	private double VIX = 0.0d;
	private double SSI1 = 0.0d;
	private double SSI2 = 0.0d;
	private long fechaInicio = 0;
	public transient int numeroTrailing = 0;
	private int low = Short.MAX_VALUE;
	private int high = Short.MIN_VALUE;
	private boolean tocoStop = false;
	private transient double stop;
	private transient double stopDaily = -1;
	
	
	public SenalEstrategia()
	{
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
	}
    
	public IdEstrategia getEstrategia()
	{
		return estrategia;
	}

	public void setEstrategia(IdEstrategia estrategia) 
	{
		this.estrategia = estrategia;
	}

	public boolean isCompra() 
	{
		return compra;
	}

	public void setCompra(boolean compra) 
	{
		this.compra = compra;
	}

	public Par getPar() 
	{
		return par;
	}

	public void setPar(Par par) 
	{
		this.par = par;
	}

	public int getNumeroLotes() 
	{
		return numeroLotes;
	}

	public void setNumeroLotes(int numeroLotes) 
	{
		this.numeroLotes = numeroLotes;
	}

	public double getPrecioEntrada() 
	{
		return precioEntrada;
	}

	public void setPrecioEntrada(double precioEntrada) 
	{
		this.precioEntrada = precioEntrada;
	}

	public double getVIX() 
	{
		return VIX;
	}

	public void setVIX(double vIX) 
	{
		VIX = vIX;
	}

	public double getSSI1() 
	{
		return SSI1;
	}

	public void setSSI1(double sSI1) 
	{
		SSI1 = sSI1;
	}

	public double getSSI2() 
	{
		return SSI2;
	}

	public void setSSI2(double sSI2) 
	{
		SSI2 = sSI2;
	}

	public void setFechaInicio(long fechaInicio) 
	{
		this.fechaInicio = fechaInicio;
	}

	public long getFechaInicio() 
	{
		return fechaInicio;
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
	
	public int darGanancia() 
	{
		return par.diferenciaPips(precioEntrada, compra);
	}
	
	public void setTocoStop(boolean tocoStop) 
	{
		this.tocoStop = tocoStop;
	}

	public boolean isTocoStop() 
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
		return estrategia + " " + (compra ? "Compra" : "Venta") + " " + numeroLotes + " Lotes de " + par + " a: " + precioEntrada + " Stop: " + stop; 
	}
}
package modelo;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import control.Error;
import control.HiloDaily;
import control.conexion.ConexionMySql;

public enum Par
{
	EURUSD(10000), USDJPY(100), GBPUSD(10000), USDCHF(10000), EURCHF(EURUSD, USDCHF, 10000), AUDUSD(10000), USDCAD(10000),
	NZDUSD(10000), EURJPY(EURUSD, USDJPY, 100), GBPJPY(100), CHFJPY(USDCHF, USDJPY, 100), 
	GBPCHF(GBPUSD, USDCHF, 10000), EURAUD(EURUSD, AUDUSD, 10000), AUDJPY(AUDUSD, USDJPY, 100),
	TODOS(0);
	
	private final Par padreUno;
	private final Par padreDos;
	private final int multiplicador;
	private double bidActual = 0;
	private double askActual = 0;
	private double ssiActual = 0;
	private double high = Double.NEGATIVE_INFINITY;
	private double low = Double.POSITIVE_INFINITY;
	private double open = Double.NEGATIVE_INFINITY;
	private LinkedList <SenalEstrategia> senales = new LinkedList <SenalEstrategia> ();
	private static String mensaje = "";
	private static int numeroIniciados = 0;
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
	private final Lock read = rwl.readLock();
	private final Lock write = HiloDaily.darWriteLockSeguro(rwl);
	private final ReentrantReadWriteLock rwlS = new ReentrantReadWriteLock(true);
	private final Lock readS = rwlS.readLock();
	private final Lock writeS = HiloDaily.darWriteLockSeguro(rwlS);
	
	private Par(int multi)
	{
		padreUno = this;
		padreDos = this;
		multiplicador = multi;
	}
	
	private Par(Par p1, Par p2, int multi)
	{
		padreUno = p1;
		padreDos = p2;
		multiplicador = multi;
	}

	public int multiplicador()
	{
		return multiplicador;
	}
	
	public Par darPadreUno()
	{
		return padreUno;
	}
	
	public Par darPadreDos()
	{
		return padreDos;
	}
	
	public boolean estaBien(double precio)
	{
		if(multiplicador == 100)
			return precio > 5;
		return precio < 5;
	}
	
	public boolean esDistinto(Par par) 
	{
		if(par == TODOS)
			return false;
		return !equals(par);
	}
	
	public int diferenciaPips(double otro, boolean compra)
	{
		double precioActual = darPrecioActual(compra);
		double precioParActual = compra ? precioActual - otro : otro - precioActual;
		return (int) Math.round((precioParActual) * multiplicador);
	}
	
	public void ponerPrecioActual(double bid, double ask) 
	{
		String mensajeError = null;
		boolean bidCero = false;
		write.lock();
		try
		{
			bidCero = bidActual == 0;
			if(bidCero)
			{
				if(estaBien(bid) && estaBien(ask) && spread(bid, ask, this) <= 75)
				{
					bidActual = bid;
					askActual = ask;
					mensaje += "\nInicializando par " + toString() + ", bid nuevo: " + bid + ", ask nuevo: " + ask;
				}
				else
					mensajeError = "Error en Par inicializando " + toString() + ", bid anterior: " + bidActual + ", bid nuevo: " + bid + ", ask anterior: " + askActual + ", ask nuevo: " + ask;
				numeroIniciados++;
			}
			else
			{
				if(Math.abs(diferenciaPips(bid, true)) <= 200 && Math.abs(diferenciaPips(ask, false)) <= 200 && spread(bid, ask, this) <= 75)
				{
					bidActual = bid;
					askActual = ask;
				}
				else
				{
					mensajeError = "Error en par " + toString() + ", bid anterior: " + bidActual + ", bid nuevo: " + bid + ", ask anterior: " + askActual + ", ask nuevo: " + ask;
				}
			}
		}
		finally
		{
			write.unlock();
		}
		if(bidCero && numeroIniciados >= values().length - 1)
			Error.agregarInfo(mensaje);
		if(mensajeError != null)
			Error.agregar(mensajeError);
	}
	
	public double darPrecioMenos(int pips, boolean compra)
	{
		double precioActual = darPrecioActual(compra);
		double pipsD = pips;
		pipsD /= multiplicador;
		if(compra)
			return precioActual - pipsD;
		else
			return precioActual + pipsD;
	}
	
	public double darPrecioActual(boolean compra)
	{
		read.lock();
		try
		{
			return compra ? bidActual : askActual;
		}
		finally
		{
			read.unlock();
		}
	}
	
	public void ponerSSI(double ssi)
	{
		write.lock();
		try
		{
			ssiActual = ssi;
		}
		finally
		{
			write.unlock();
		}
	}
	
	public double darSSI()
	{
		read.lock();
		try
		{
			return ssiActual;
		}
		finally
		{
			read.unlock();
		}
	}
	
	private int diferenciaPips(SenalEstrategia s)
	{
		double precioActual = darPrecioActual(s.isCompra());
		double precioParActual = s.isCompra() ? precioActual - s.getPrecioEntrada() : s.getPrecioEntrada() - precioActual;
		return (int) Math.round((precioParActual) * multiplicador);
	}
	
	public void agregarSenal(SenalEstrategia s)
	{
		writeS.lock();
		try
		{
			senales.add(s);
		}
		finally
		{
			writeS.unlock();
		}
	}
	
	public void eliminarSenal(SenalEstrategia s)
	{
		writeS.lock();
		try
		{
			for(Iterator <SenalEstrategia> it = senales.iterator(); it.hasNext();)
				if(s == it.next())
					it.remove();
		}
		finally
		{
			writeS.unlock();
		}
	}
	
	public void procesarSenales()
	{
		if(this == TODOS)
			return;
		writeS.lock();
		try
		{
			if(Math.abs(darPrecioActual(true) - 0.0d) < 10e-4d  || Math.abs(darPrecioActual(false) - 0.0d) < 10e-4d)
				return;
			for(SenalEstrategia s : senales)
			{
				s.setLow(Math.min(s.getLow(), diferenciaPips(s)));
				s.setHigh(Math.max(s.getHigh(), diferenciaPips(s)));
			}
			low = Math.min(low, darPrecioActual(true));
			high = Math.min(high, darPrecioActual(true));
			Calendar actual = Calendar.getInstance();
			int hora = actual.get(Calendar.HOUR_OF_DAY);
			int minuto = actual.get(Calendar.MINUTE);
			if(minuto > 5 && open == Double.NEGATIVE_INFINITY)
				open = darPrecioActual(true);
			if(hora == 19 && minuto < 5 && open != Double.NEGATIVE_INFINITY)
				cerrarDia();
		}
		finally
		{
			writeS.unlock();
		}
	}
	
	public void cerrarDia()
	{
		writeS.lock();
		try
		{
			ConexionMySql.agregarATR(this, open, darPrecioActual(true), low, high);
			high = Double.NEGATIVE_INFINITY;
			low = Double.POSITIVE_INFINITY;
			open = Double.NEGATIVE_INFINITY;
		}
		finally
		{
			writeS.unlock();
		}
	}
	
	public String debugSenales()
	{
		readS.lock();
		try
		{
			String debug = "";
			for(SenalEstrategia s : senales)
				debug += s.getEstrategia().toString() + " " + s.getPar().toString() + " " + s.getPrecioEntrada() + " " + s.isCompra() + " " + s.getLow() + " " + s.getHigh() + "\n";
			return debug;
		}
		finally
		{
			readS.unlock();
		}
	}
	
	public int spread()
	{
		double bidActual = 0;
		double askActual = 0;
		read.lock();
		try
		{
			bidActual = this.bidActual;
			askActual = this.askActual;
		}
		finally
		{
			read.unlock();
		}
		return spread(bidActual, askActual, this);
	}
	
	public static int spread(double bid, double ask, Par par)
	{
		double precioParActual = ask - bid;
		return (int) Math.round((precioParActual) * par.multiplicador);
	}
	
	public static Par stringToPar(String string) 
	{
		for(Par a : values())
			if(string.equals(a.toString()))
				return a;
		return TODOS;
	}
	
	public static Par convertirPar(String cuerpo)
	{
		Par par = null;
		for(Par a : values())
			if(!a.equals(TODOS) && cuerpo.contains(a.toString()))
				par = a;
		return par;
	}
}
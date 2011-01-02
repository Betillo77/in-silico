package modelo;

import java.util.Iterator;
import java.util.LinkedList;
import control.Error;

public enum Par
{
	EURUSD, USDJPY, GBPUSD, USDCHF, EURCHF(EURUSD, USDCHF), AUDUSD, USDCAD,
	NZDUSD, EURJPY(EURUSD, USDJPY), GBPJPY, CHFJPY(USDCHF, USDJPY), 
	GBPCHF(GBPUSD, USDCHF), EURAUD(EURUSD, AUDUSD), AUDJPY(AUDUSD, USDJPY),
	TODOS;
	
	private static final Par[] crucesYen = new Par[] {USDJPY, EURJPY, GBPJPY, CHFJPY, AUDJPY};
	
	private final Par padreUno;
	private final Par padreDos;
	private double bidActual = 0;
	private double askActual = 0;
	private double ssiActual = 0;
	private LinkedList <SenalEstrategia> senales = new LinkedList <SenalEstrategia> ();
	private static String mensaje = "";
	private static int numeroIniciados = 0;
	
	private Par()
	{
		padreUno = this;
		padreDos = this;
	}
	
	private Par(Par p1, Par p2)
	{
		padreUno = p1;
		padreDos = p2;
	}

	private boolean esCruceYen()
	{
		for(Par p : crucesYen)
			if(p.equals(this))
				return true;
		return false;
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
		if(esCruceYen())
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
		return esCruceYen() ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
	}
	
	public synchronized void ponerPrecioActual(double bid, double ask) 
	{
		if(bidActual == 0)
		{
			if(estaBien(bid) && estaBien(ask) && spread(bid, ask, this) <= 75)
			{
				bidActual = bid;
				askActual = ask;
				mensaje += "\nInicializando par " + toString() + ", bid nuevo: " + bid + ", ask nuevo: " + ask;
			}
			else
				mensaje += "\nError en Par inicializando " + toString() + ", bid anterior: " + bidActual + ", bid nuevo: " + bid + ", ask anterior: " + askActual + ", ask nuevo: " + ask;
			numeroIniciados++;
			if(numeroIniciados >= values().length - 1)
				Error.agregarInfo(mensaje);
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
				Error.agregar("Error en par " + toString() + ", bid anterior: " + bidActual + ", bid nuevo: " + bid + ", ask anterior: " + askActual + ", ask nuevo: " + ask);
			}
		}
	}
	
	public synchronized double darPrecioMenos(int pips, boolean compra)
	{
		double precioActual = darPrecioActual(compra);
		double pipsD = pips;
		pipsD /= esCruceYen() ? 100 : 10000;
		if(compra)
			return precioActual - pipsD;
		else
			return precioActual + pipsD;
	}
	
	public synchronized double darPrecioActual(boolean compra)
	{
    	return compra ? bidActual : askActual;
	}
	
	public synchronized void ponerSSI(double ssi)
	{
		ssiActual = ssi;
	}
	
	public synchronized double darSSI()
	{
		return ssiActual;
	}
	
	private int diferenciaPips(SenalEstrategia s)
	{
		double precioActual = darPrecioActual(s.isCompra());
		double precioParActual = s.isCompra() ? precioActual - s.getPrecioEntrada() : s.getPrecioEntrada() - precioActual;
		return esCruceYen() ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
	}
	
	public synchronized void agregarSenal(SenalEstrategia s)
	{
		senales.add(s);
	}
	
	public synchronized void eliminarSenal(SenalEstrategia s)
	{
		for(Iterator <SenalEstrategia> it = senales.iterator(); it.hasNext();)
		{
			SenalEstrategia s1 = it.next();
			if(s == s1)
				it.remove();
		}
	}
	
	public synchronized void procesarSenales()
	{
		if(Math.abs(darPrecioActual(true) - 0.0d) < 10e-4d  || Math.abs(darPrecioActual(false) - 0.0d) < 10e-4d)
			return;
		for(SenalEstrategia s : senales)
		{
			s.setLow(Math.min(s.getLow(), diferenciaPips(s)));
			s.setHigh(Math.max(s.getHigh(), diferenciaPips(s)));
		}
	}
	
	public synchronized String debugSenales()
	{
		String debug = "";
		for(SenalEstrategia s : senales)
			debug += s.getEstrategia().toString() + " " + s.getPar().toString() + " " + s.getPrecioEntrada() + " " + s.isCompra() + " " + s.getLow() + " " + s.getHigh() + "\n";
		return debug;
	}
	
	public int spread()
	{
		return spread(bidActual, askActual, this);
	}
	
	public static int spread(double bid, double ask, Par par)
	{
		double precioParActual = ask - bid;
		return par.esCruceYen() ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
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
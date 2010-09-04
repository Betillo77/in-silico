package modelo;

public enum Par
{
	EURUSD, USDJPY, GBPUSD, USDCHF, EURCHF, AUDUSD, USDCAD,
	NZDUSD, EURJPY, GBPJPY, CHFJPY, GBPCHF, EURAUD, AUDJPY, 
	TODOS;
	
	private static Par[][] padres = new Par[values().length][];

	static
	{
		padres[EURUSD.ordinal()] = new Par[]{EURUSD, EURUSD};
		padres[USDJPY.ordinal()] = new Par[]{USDJPY, USDJPY};
		padres[GBPUSD.ordinal()] = new Par[]{GBPUSD, GBPUSD};
		padres[USDCHF.ordinal()] = new Par[]{USDCHF, USDCHF};
		padres[EURCHF.ordinal()] = new Par[]{EURUSD, USDCHF};
		padres[AUDUSD.ordinal()] = new Par[]{AUDUSD, AUDUSD};
		padres[USDCAD.ordinal()] = new Par[]{USDCAD, USDCAD};
		padres[NZDUSD.ordinal()] = new Par[]{NZDUSD, NZDUSD};
		padres[EURJPY.ordinal()] = new Par[]{EURUSD, USDJPY};
		padres[GBPJPY.ordinal()] = new Par[]{GBPJPY, GBPJPY};
		padres[CHFJPY.ordinal()] = new Par[]{USDCHF, USDJPY};
		padres[GBPCHF.ordinal()] = new Par[]{GBPUSD, USDCHF};
		padres[EURAUD.ordinal()] = new Par[]{EURUSD, AUDUSD};
		padres[AUDJPY.ordinal()] = new Par[]{AUDUSD, USDJPY};
	}
	
	private static double[][] preciosActuales = new double[values().length][2];
	
	private static double[] ssiActual = new double[values().length];
	
	private static Par[] crucesYen = new Par[] {USDJPY, EURJPY, GBPJPY, CHFJPY, AUDJPY};

	public boolean esDistinto(Par par) 
	{
		if(par == TODOS)
			return false;
		return !equals(par);
	}

	private boolean esCruceYen()
	{
		for(Par p : crucesYen)
			if(p.equals(this))
				return true;
		return false;
	}
	
	public boolean estaBien(double precio)
	{
		if(esCruceYen())
			return precio > 5;
		return precio < 5;
	}
	
	public int diferenciaPips(double otro, boolean compra)
	{
		double precioActual = darPrecioActual(compra);
		double precioParActual = compra ? precioActual - otro : otro - precioActual;
		return esCruceYen() ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
	}
	
	public Par darPadreUno()
	{
		return padres[ordinal()][0];
	}
	
	public Par darPadreDos()
	{
		return padres[ordinal()][1];
	}
	
	public synchronized void ponerPrecioActual(double bid, double ask) 
	{
		if(preciosActuales[ordinal()][0] == 0)
		{
			if(estaBien(bid) && estaBien(ask))
			{
				preciosActuales[ordinal()][0] = bid;
				preciosActuales[ordinal()][1] = ask;
			}
		}
		else
		{
			if(Math.abs(diferenciaPips(bid, true)) <= 500 || Math.abs(diferenciaPips(ask, false)) <= 500)
			{
				preciosActuales[ordinal()][0] = bid;
				preciosActuales[ordinal()][1] = ask;
			}
		}
	}
	
	public synchronized double darPrecioActual(boolean compra)
	{
    	if(compra == true)
    	{
    		return preciosActuales[ordinal()][0];
    	}
    	else
    	{
    		return preciosActuales[ordinal()][1];
    	}
	}
	
	public synchronized void ponerSSI(double ssi)
	{
		ssiActual[ordinal()] = ssi;
	}
	
	public synchronized double darSSI()
	{
		return ssiActual[ordinal()];
	}
	
	public static Par stringToPar(String string) 
	{
		for(Par a : values())
		{
			if(string.equals(a.toString()))
				return a;
		}
		return TODOS;
	}
	
	public static Par convertirPar(String cuerpo)
	{
		Par par;
		if(cuerpo.contains("EURUSD"))
		{
			par = Par.EURUSD;
		}
		else if(cuerpo.contains("USDJPY"))
		{
			par = Par.USDJPY;
		}
		else if(cuerpo.contains("GBPUSD"))
		{
			par = Par.GBPUSD;
		}
		else if(cuerpo.contains("USDCHF"))
		{
			par = Par.USDCHF;
		}
		else if(cuerpo.contains("EURCHF"))
		{
			par = Par.EURCHF;
		}
		else if(cuerpo.contains("AUDUSD"))
		{
			par = Par.AUDUSD;
		}
		else if(cuerpo.contains("USDCAD"))
		{
			par = Par.USDCAD;
		}
		else if(cuerpo.contains("NZDUSD"))
		{
			par = Par.NZDUSD;
		}
		else if(cuerpo.contains("EURJPY"))
		{
			par = Par.EURJPY;
		}
		else if(cuerpo.contains("GBPJPY"))
		{
			par = Par.GBPJPY;
		}
		else if(cuerpo.contains("CHFJPY"))
		{
			par = Par.CHFJPY;
		}
		else if(cuerpo.contains("GBPCHF"))
		{
			par = Par.GBPCHF;
		}
		else if(cuerpo.contains("EURAUD"))
		{
			par = Par.EURAUD;
		}
		else if(cuerpo.contains("AUDJPY"))
		{
			par = Par.AUDJPY;
		}
		else
		{
			par = null;
		}
		return par;
	}
	
	public String toString()
	{
		switch(this)
		{
			case EURUSD: return "EURUSD";
			case USDJPY: return "USDJPY";
			case GBPUSD: return "GBPUSD";
			case USDCHF: return "USDCHF";
			case EURCHF: return "EURCHF";
			case AUDUSD: return "AUDUSD";
			case USDCAD: return "USDCAD";
			case NZDUSD: return "NZDUSD";
			case EURJPY: return "EURJPY";
			case GBPJPY: return "GBPJPY";
			case CHFJPY: return "CHFJPY";
			case GBPCHF: return "GBPCHF";
			case EURAUD: return "EURAUD";
			case AUDJPY: return "AUDJPY";
			default: return "TODOS";
		}
	}
}
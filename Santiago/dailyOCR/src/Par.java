import java.io.Serializable;

public enum Par implements Serializable
{
	EURUSD, USDJPY, GBPUSD, USDCHF, EURCHF, AUDUSD, USDCAD,
	NZDUSD, EURJPY, GBPJPY, CHFJPY, GBPCHF, EURAUD, AUDJPY, 
	TODOS;
		
	@Override
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

	public boolean esDistinto(Par par) 
	{
		if(par == TODOS)
			return false;
		return this != par;
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
			Error.agregar("El par no se encuentra dentro de los pares: " + cuerpo);
			par = null;
		}
		return par;
	}
}
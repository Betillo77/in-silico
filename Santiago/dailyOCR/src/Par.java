import java.io.Serializable;

public enum Par implements Serializable
{
	EURUSD, USDJPY, GBPUSD, USDCHF, EURCHF, AUDUSD, USDCAD,
	NZDUSD, EURJPY, GBPJPY, CHFJPY, GBPCHF, EURAUD, AUDJPY;
		
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
			default: return "";
		}
	}
}
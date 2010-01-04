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
			case EURUSD: return "EUR/USD";
			case USDJPY: return "USD/JPY";
			case GBPUSD: return "GBP/USD";
			case USDCHF: return "USD/CHF";
			case EURCHF: return "EUR/CHF";
			case AUDUSD: return "AUD/USD";
			case USDCAD: return "USD/CAD";
			case NZDUSD: return "NZD/USD";
			case EURJPY: return "EUR/JPY";
			case GBPJPY: return "GBP/JPY";
			case CHFJPY: return "CHF/JPY";
			case GBPCHF: return "GBP/CHF";
			case EURAUD: return "EUR/AUD";
			case AUDJPY: return "AUD/JPY";
			default: return "";
		}
	}
}
public class Senal 
{
	public static final int ERROR = 0;
	
	public static final int BREAKOUT = 1;
	public static final int RANGE = 2;
	public static final int MOMENTUM = 3;
	public static final int BREAKOUT2 = 4;
	
	public static final int EURUSD = 1;
	public static final int USDJPY = 2;
	public static final int GBPUSD = 3;
	public static final int USDCHF = 4;
	public static final int EURCHF = 5;
	public static final int AUDUSD = 6;
	public static final int USDCAD = 7;
	public static final int NZDUSD = 8;
	public static final int EURJPY = 9;
	public static final int GBPJPY = 10;
	public static final int CHFJPY = 11;
	public static final int GBPCHF = 12;
	public static final int EURAUD = 13;
	public static final int AUDJPY = 14;
	
	public static final int HIT = 1;
	public static final int TRADE = 2;
	public static final int NOIMPORTA = 3;
	
	public int par;
	public int tipo;
	public boolean compra;
	public int estrategia;
	public int magico;
	public boolean range2 = false;
	
	public Senal(int estrategia, int par, int tipo, boolean compra) 
	{
		this.estrategia = estrategia;
		this.par = par;
		this.tipo = tipo;
		this.compra = compra;
		this.magico = 0;
	}
	
	public Senal(int estrategia, int par, int tipo, boolean compra, int magico)
	{
		this.estrategia = estrategia;
		this.par = par;
		this.tipo = tipo;
		this.compra = compra;
		this.magico = magico;
	}

	@Override
	public boolean equals(Object senal2)
	{
		Senal otra = (Senal) senal2;
		if(otra.tipo == ERROR || tipo == ERROR)
		{
			return true;
		}
		else if(estrategia == otra.estrategia && par == otra.par && tipo == otra.tipo && compra == otra.compra)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

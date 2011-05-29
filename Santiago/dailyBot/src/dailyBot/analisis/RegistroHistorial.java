package dailyBot.analisis;

import dailyBot.control.conexion.dailyFx.ConexionServidorDailyFx;
import dailyBot.modelo.Par;
import dailyBot.modelo.Estrategia.IdEstrategia;

public class RegistroHistorial implements Comparable <RegistroHistorial>
{
	public IdEstrategia id;
	public Par par;
	public boolean compra;
	public long fechaApertura;
	public long fechaCierre;
	public int ganancia;
	public double VIX;
	public double SSI1;
	public double SSI2;
	public double ATR;
	public double RSI;
	public int low;
	public int high;

	RegistroHistorial() 
	{
	}
	
	public RegistroHistorial(Par par, boolean compra)
	{
		this.par = par;
		this.compra = compra;
		this.fechaApertura = System.currentTimeMillis();
		this.VIX = ConexionServidorDailyFx.darVIX();
		this.SSI1 = par.darPadreUno().darSSI();
		this.SSI2 = par.darPadreDos().darSSI();
		this.ATR = Estadistica.calcularATR(this.par, 14, this.fechaApertura);
		this.RSI = Estadistica.calcularRSI(par, 27, this.fechaApertura);
		if(!compra)
			RSI = 100 - RSI;
	}
	
	public RegistroHistorial(IdEstrategia id, Par par, boolean compra, long fechaApertura, long fechaCierre, int ganancia, double vIX, double sSI1, double sSI2, int low, int high) 
	{
		this.id = id;
		this.par = par;
		this.compra = compra;
		this.fechaApertura = fechaApertura;
		this.fechaCierre = fechaCierre;
		this.ganancia = ganancia;
		this.low = low;
		if(low <= -170 || this.ganancia <= -170)
		{
			this.ganancia = -170;
			this.low = -170;
		}	
		this.high = high;
		this.VIX = vIX;
		this.SSI1 = sSI1;
		this.SSI2 = sSI2;
		this.ATR = Estadistica.calcularATR(this.par, 14, this.fechaApertura);
		this.RSI = Estadistica.calcularRSI(par, 27, this.fechaApertura);
		if(!compra)
			RSI = 100 - RSI;
	}
	
	@Override
	public int compareTo(RegistroHistorial o) 
	{
		return new Long(fechaApertura).compareTo(o.fechaApertura);
	}
	
	@Override
	public String toString() 
	{
		return "ATR = " + ATR + ", RSI = " + RSI + ", SSI1 = "
				+ SSI1 + ", SSI2 = " + SSI2 + ", VIX = " + VIX + ", par = "
				+ par + ", compra = " + compra; 
	}
}
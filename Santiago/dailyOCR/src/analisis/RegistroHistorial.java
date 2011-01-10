package analisis;

import control.conexion.ConexionMySql;
import modelo.Par;
import modelo.Estrategia.IdEstrategia;

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
	
	public RegistroHistorial(IdEstrategia id, Par par, boolean compra, long fechaApertura, long fechaCierre, int ganancia, double vIX, double sSI1, double sSI2, int low, int high) 
	{
		this.id = id;
		this.par = par;
		this.compra = compra;
		this.fechaApertura = fechaApertura;
		this.fechaCierre = fechaCierre;
		this.ganancia = ganancia;
		this.VIX = vIX;
		this.SSI1 = sSI1;
		this.SSI2 = sSI2;
		this.low = low;
		this.high = high;
		this.ATR = ConexionMySql.darATR(this.par, 14, this.fechaCierre);
		this.RSI = ConexionMySql.darRSI(par, 27, this.fechaCierre);
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
		return par + " " + compra;
	}
}

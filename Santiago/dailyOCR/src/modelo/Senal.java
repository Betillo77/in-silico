package modelo;

import control.IdEstrategia;
import control.conexion.dailyFx.ConexionServidorDailyFx;

public class Senal
{	
	private IdEstrategia estrategia;
	private boolean compra;
	private Par par;
	private int numeroLotes;
	private double precioEntrada;
	private int[] magico = {0};
    private double limite;
	private double VIX = 0.0d;
	private double SSI1 = 0.0d;
	private double SSI2 = 0.0d;
	private long fechaInicio = 0;
	private transient int gananciaReal = Short.MIN_VALUE;
	private int low = Short.MAX_VALUE;
	private int high = Short.MIN_VALUE;
	private transient double[] stops;
	
	public Senal()
	{
	}
    
	public Senal(IdEstrategia estrategia, boolean compra, Par par, int numeroLotes, double precioEntrada)
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
	}
    
	public IdEstrategia getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(IdEstrategia estrategia) {
		this.estrategia = estrategia;
	}

	public boolean isCompra() {
		return compra;
	}

	public void setCompra(boolean compra) {
		this.compra = compra;
	}

	public Par getPar() {
		return par;
	}

	public void setPar(Par par) {
		this.par = par;
	}

	public int getNumeroLotes() {
		return numeroLotes;
	}

	public void setNumeroLotes(int numeroLotes) {
		this.numeroLotes = numeroLotes;
	}

	public double getPrecioEntrada() {
		return precioEntrada;
	}

	public void setPrecioEntrada(double precioEntrada) {
		this.precioEntrada = precioEntrada;
	}

	public synchronized int[] getMagico() {
		return magico;
	}
	
	public synchronized int[] darMagicoCopy() {
		int[] magicoCopy = new int[magico.length];
		for(int i = 0; i < magico.length; i++)
			magicoCopy[i] = magico[i];
		return magicoCopy;
	}
	
	public synchronized int darMagico(int pos) {
		return magico[pos];
	}

	public synchronized void ponerMagico(int pos, int m) {
		magico[pos] = m;
	}
	
	public synchronized void setMagico(int[] magico) {
		this.magico = magico;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}
	
	public double getVIX() {
		return VIX;
	}

	public void setVIX(double vIX) {
		VIX = vIX;
	}

	public double getSSI1() {
		return SSI1;
	}

	public void setSSI1(double sSI1) {
		SSI1 = sSI1;
	}

	public double getSSI2() {
		return SSI2;
	}

	public void setSSI2(double sSI2) {
		SSI2 = sSI2;
	}

	public void setFechaInicio(long fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public long getFechaInicio() {
		return fechaInicio;
	}
	
	public synchronized void ponerGananciaReal(int gananciaReal) {
		this.gananciaReal = gananciaReal;
	}
	
	public synchronized int darGananciaReal() {
		return gananciaReal;
	}

	public synchronized void setLow(int low) {
		this.low = low;
	}

	public synchronized int getLow() {
		return low;
	}

	public synchronized void setHigh(int high) {
		this.high = high;
	}

	public synchronized int getHigh() {
		return high;
	}
	
	@Override
	public String toString()
	{
		return estrategia + " " + (compra ? "Compra" : "Venta") + " " + numeroLotes + " Lotes de " + par + " a: " + precioEntrada; 
	}

	public void ponerStops(double[] stops) {
		this.stops = stops;
	}

	public double[] darStops() {
		return stops;
	}
}
package modelo;

import control.IdEstrategia;
import control.Par;

public class SenalJoel extends Senal 
{
	private static final long serialVersionUID = -8940769909212189794L;
	private double stop;
	private double limite;
	private boolean recomendado;
	private long tiempoEntrada;

	public SenalJoel(IdEstrategia estrategia, boolean compra, Par par, int numeroLotes, double precioEntrada, double stop,boolean recomendado, double limite)
	{
		super(estrategia, compra, par, numeroLotes, precioEntrada);
		this.stop = stop;
		this.recomendado = recomendado;
		this.limite = limite;
		tiempoEntrada = System.currentTimeMillis();
	}
	
	public double getStop() {
		return stop;
	}

	public void setStop(double stop) {
		this.stop = stop;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public boolean isRecomendado() {
		return recomendado;
	}

	public void setRecomendado(boolean recomendado) {
		this.recomendado = recomendado;
	}

	public long getTiempoEntrada() {
		return tiempoEntrada;
	}

	public void setTiempoEntrada(long tiempoEntrada) {
		this.tiempoEntrada = tiempoEntrada;
	}

	@Override
	public String toString()
	{
		return getEstrategia() + " " + (isCompra() ? "Compra" : "Venta") + " " + getNumeroLotes() + " Lotes de " + getPar() + " a: " + getPrecioEntrada() + " " + (recomendado? "Recomendado" : "Normal") + " " + "precio de parada: " + stop; 
	}
}

package modelo;

import control.IdEstrategia;
import control.Par;



public class SenalJoel extends Senal 
{
	private static final long serialVersionUID = -8940769909212189794L;
	double stop;
	double limite;
	public boolean recomendado;
	public long tiempoEntrada;

	public SenalJoel(IdEstrategia estrategia, boolean compra, Par par, int numeroLotes, double precioEntrada, double stop,boolean recomendado, double limite)
	{
		super(estrategia, compra, par, numeroLotes, precioEntrada);
		this.stop = stop;
		this.recomendado = recomendado;
		this.limite = limite;
		tiempoEntrada = System.currentTimeMillis();
	}
	
	public String toString()
	{
		return estrategia + " " + (compra ? "Compra" : "Venta") + " " + numeroLotes + " Lotes de " + par + " a: " + precioEntrada + " " + (recomendado? "Recomendado" : "Normal") + " " + "precio de parada: " + stop; 
	}
}

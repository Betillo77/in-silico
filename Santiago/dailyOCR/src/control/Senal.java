package control;
import java.io.Serializable;


public class Senal implements Serializable
{
	private static final long serialVersionUID = -9035153664488602362L;
	
	public IdEstrategia estrategia;
	public boolean compra;
	public Par par;
	public int numeroLotes;
	public int lotesCerradosManualmente = 0;
	public double precioEntrada;
	public int[] magico = {0};
    public boolean manual = false;
    public double limite;
	
	public Senal(IdEstrategia estrategia, boolean compra, Par par, int numeroLotes, double precioEntrada)
	{
		this.estrategia = estrategia;
		this.compra = compra;
		this.par = par;
		this.numeroLotes = numeroLotes;
		this.precioEntrada = precioEntrada;
	}
	
	public String toString()
	{
		return estrategia + " " + (compra ? "Compra" : "Venta") + " " + numeroLotes + " Lotes de " + par + " a: " + precioEntrada; 
	}
}

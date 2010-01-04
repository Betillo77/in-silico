public class Senal 
{
	public IdEstrategia estrategia;
	public boolean compra;
	public Par par;
	public int numeroLotes;
	public double precioEntrada;
	public int[] magico = {0};
	
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

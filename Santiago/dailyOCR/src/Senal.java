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
		this.compra = compra;
		this.par = par;
		this.numeroLotes = numeroLotes;
		this.precioEntrada = precioEntrada;
	}
}

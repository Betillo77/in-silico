package control;

public class SenalEntrada
{
	public Par par;
	public TipoSenal tipo;
	public boolean compra;
	public int numeroLotes = 1;
	public double precioEntrada;
	public double limite;
	
	public SenalEntrada(Par par, TipoSenal tipo, boolean compra, int numeroLotes, double precioEntrada) 
	{
		this.par = par;
		this.tipo = tipo;
		this.compra = compra;
		this.numeroLotes = numeroLotes;
		this.precioEntrada = precioEntrada;
	}

	@Override
	public boolean equals(Object otra1)
	{
		SenalEntrada otra = (SenalEntrada) otra1;
		return par == otra.par && tipo == otra.tipo && compra == otra.compra;
	}
}

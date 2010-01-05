public class SenalEntrada
{
	public Par par;
	public TipoSenal tipo;
	public boolean compra;
	public int numero = 1;
	public double precioEntrada;
	
	public SenalEntrada(Par par, TipoSenal tipo, boolean compra, int numero, double precioEntrada) 
	{
		this.par = par;
		this.tipo = tipo;
		this.compra = compra;
		this.numero = numero;
		this.precioEntrada = precioEntrada;
	}

	@Override
	public boolean equals(Object otra1)
	{
		SenalEntrada otra = (SenalEntrada) otra1;
		return par == otra.par && tipo == otra.tipo && compra == otra.compra;
	}
}

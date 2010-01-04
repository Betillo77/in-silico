public class SenalEntrada
{
	public Par par;
	public TipoSenal tipo;
	public boolean compra;
	public int numero = 1;
	
	public SenalEntrada(Par par, TipoSenal tipo, boolean compra) 
	{
		this.par = par;
		this.tipo = tipo;
		this.compra = compra;
	}
	
	public SenalEntrada(Par par, TipoSenal tipo, boolean compra, int numero) 
	{
		this.par = par;
		this.tipo = tipo;
		this.compra = compra;
		this.numero = numero;
	}

	@Override
	public boolean equals(Object otra1)
	{
		SenalEntrada otra = (SenalEntrada) otra1;
		return par == otra.par && tipo == otra.tipo && compra == otra.compra;
	}
}

package modelo;

import control.IdEstrategia;


public class SenalEntrada
{
	private Par par;
	private TipoSenal tipo;
	private boolean compra;
	private int numeroLotes = 1;
	private double precioEntrada;
	private double limite;
	private IdEstrategia estrategia;
	
	public SenalEntrada(IdEstrategia estrategia, Par par, TipoSenal tipo, boolean compra, int numeroLotes, double precioEntrada) 
	{
		this.estrategia = estrategia;
		this.par = par;
		this.tipo = tipo;
		this.compra = compra;
		this.numeroLotes = numeroLotes;
		this.precioEntrada = precioEntrada;
	}

	public Par getPar() {
		return par;
	}

	public void setPar(Par par) {
		this.par = par;
	}

	public TipoSenal getTipo() {
		return tipo;
	}

	public void setTipo(TipoSenal tipo) {
		this.tipo = tipo;
	}

	public boolean isCompra() {
		return compra;
	}

	public void setCompra(boolean compra) {
		this.compra = compra;
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

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public void setEstrategia(IdEstrategia estrategia) {
		this.estrategia = estrategia;
	}

	public IdEstrategia getEstrategia() {
		return estrategia;
	}
	
	@Override
	public boolean equals(Object otra1)
	{
		SenalEntrada otra = (SenalEntrada) otra1;
		return par == otra.par && tipo == otra.tipo && compra == otra.compra;
	}
}

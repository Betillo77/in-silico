package modelo;

import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
import control.Error;

public class SenalProveedor 
{
	private IdProveedor proveedor;
	private IdEstrategia estrategia;
	private Par par;
	private boolean compra;
	private int magico;
	
	public SenalProveedor()
	{
	}
	
	public SenalProveedor(IdProveedor i, IdEstrategia e, Par p, boolean c)
	{
		proveedor = i;
		estrategia = e;
		par = p;
		compra = c;
		magico = 0;
	}
	
	public IdProveedor getProveedor() 
	{
		return proveedor;
	}
	
	public void setProveedor(IdProveedor proveedor) 
	{
		this.proveedor = proveedor;
	}
	
	public IdEstrategia getEstrategia() 
	{
		return estrategia;
	}
	
	public void setEstrategia(IdEstrategia estrategia) 
	{
		this.estrategia = estrategia;
	}
	
	public boolean isCompra() 
	{
		return compra;
	}
	
	public void setCompra(boolean compra) 
	{
		this.compra = compra;
	}
	
	public Par getPar() 
	{
		return par;
	}
	
	public void setPar(Par par) 
	{
		this.par = par;
	}
	
	public int getMagico() 
	{
		return magico;
	}
	
	public void setMagico(int magico) 
	{
		this.magico = magico;
	}

	private SenalEstrategia darSenalEstrategia()
	{
		SenalEstrategia posible = estrategia.darEstrategia().tienePar(par);
		if(posible == null)
			Error.agregar("Senal proveedor no coincide con la estrategia: " + proveedor + ", " + estrategia + ", " + par);
		return posible;
	}
	
	public int darGanancia()
	{
		SenalEstrategia esta = darSenalEstrategia();
		if(esta == null)
			return Integer.MIN_VALUE;
		return esta.darGanancia();
	}
	public double darPrecioEntrada()
	{
		SenalEstrategia esta = darSenalEstrategia();
		if(esta == null)
			return 0;
		return esta.getPrecioEntrada();
	}

	public double darStop() {
		SenalEstrategia esta = darSenalEstrategia();
		if(esta == null)
			return 0;
		return esta.darStop();
	}
	
	public double darStopDaily() {
		SenalEstrategia esta = darSenalEstrategia();
		if(esta == null)
			return 0;
		return esta.darStopDaily();
	}

	public boolean darTocoStop() {
		SenalEstrategia esta = darSenalEstrategia();
		if(esta == null)
			return false;
		return esta.isTocoStop();
	}
}

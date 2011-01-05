package modelo;

import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
import control.Error;

public class SenalProveedor 
{
	private IdProveedor proveedor;
	private IdEstrategia estrategia;
	private Boolean compra;
	private Par par;
	private int magico;
	
	public SenalProveedor()
	{
		proveedor = null;
		estrategia = null;
		compra = null;
		par = null;
		magico = 0;
	}
	
	public SenalProveedor(IdProveedor i, IdEstrategia e, Par p, boolean c)
	{
		proveedor = i;
		estrategia = e;
		compra = c;
		par = p;
		magico = 0;
	}
	
	public void setProveedor(IdProveedor proveedor) 
	{
		if(this.proveedor != null)
			throw new UnsupportedOperationException("Campo proveedor de SenalProveedor es inmutable");
		this.proveedor = proveedor;
	}
	
	public IdProveedor getProveedor() 
	{
		return proveedor;
	}
	
	public void setEstrategia(IdEstrategia estrategia) 
	{
		if(this.estrategia != null)
			throw new UnsupportedOperationException("Campo estrategia de SenalProveedor es inmutable");
		this.estrategia = estrategia;
	}
	
	public IdEstrategia getEstrategia() 
	{
		return estrategia;
	}
	
	public void setCompra(boolean compra) 
	{
		if(this.compra != null)
			throw new UnsupportedOperationException("Campo compra de SenalProveedor es inmutable");
		this.compra = compra;
	}
	
	public boolean isCompra() 
	{
		return compra;
	}
	
	public void setPar(Par par) 
	{
		if(this.par != null)
			throw new UnsupportedOperationException("Campo par de SenalProveedor es inmutable");
		this.par = par;
	}
	
	public Par getPar() 
	{
		return par;
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

	public double darStop() 
	{
		SenalEstrategia esta = darSenalEstrategia();
		if(esta == null)
			return 0;
		return esta.darStop();
	}
	
	public double darStopDaily() 
	{
		SenalEstrategia esta = darSenalEstrategia();
		if(esta == null)
			return 0;
		return esta.darStopDaily();
	}

	public boolean darTocoStop()
	{
		SenalEstrategia esta = darSenalEstrategia();
		if(esta == null)
			return false;
		return esta.isTocoStop();
	}
	
	public synchronized void setMagico(int magico) 
	{
		this.magico = magico;
	}

	public synchronized int getMagico() 
	{
		return magico;
	}
}
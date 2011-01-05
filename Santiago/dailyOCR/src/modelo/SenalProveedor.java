package modelo;

import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
import control.Error;

public class SenalProveedor 
{
	private IdProveedor proveedor;
	private IdEstrategia estrategia;
	private boolean compra;
	private boolean compraPuesta;
	private Par par;
	private int magico;
	
	public SenalProveedor()
	{
		proveedor = null;
		estrategia = null;
		compra = false;
		par = null;
		magico = 0;
		compraPuesta = false;
	}
	
	public SenalProveedor(IdProveedor i, IdEstrategia e, Par p, boolean c)
	{
		proveedor = i;
		estrategia = e;
		compra = c;
		par = p;
		magico = 0;
		compraPuesta = true;
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
		if(compraPuesta)
			throw new UnsupportedOperationException("Campo compra de SenalProveedor es inmutable");
		this.compra = compra;
		this.compraPuesta = true;
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
		return esta.isTocoStop() || esta.getLow() < -150;
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
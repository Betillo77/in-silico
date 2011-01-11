package analisis;

import modelo.Proveedor.IdProveedor;

public class RangosProveedor extends Rangos
{
	IdProveedor id;
	
	public RangosProveedor(IdProveedor i)
	{
		id = i;
	}
	@Override
	public boolean cumple(RegistroHistorial registro, boolean ignorarInfo) 
	{
		if(id.darProveedor().darActivo(registro.id, registro.par))
			return registro.id.darEstrategia().getRangos()[registro.par.ordinal()].cumple(registro, true);
		else
			return false;
	}
}

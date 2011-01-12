package control.conexion;

import java.rmi.RemoteException;
import java.util.List;

import modelo.Par;
import modelo.SenalEstrategia;
import modelo.SenalProveedor;
import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
import analisis.Indicador;
import analisis.Rangos;

public class ConexionServidorRMI extends ConexionServidor implements ConexionRMI
{
	@Override
	public void cambiarActivoProveedor(int idProveedor, int idEstrategia, int idPar, boolean activo) throws RemoteException 
	{
		IdProveedor.values()[idProveedor].darProveedor().cambiarActivo(IdEstrategia.values()[idEstrategia], Par.values()[idPar], activo);
	}
	
	@Override
	public boolean darActivoProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException
	{
		return IdProveedor.values()[idProveedor].darProveedor().darActivo(IdEstrategia.values()[idEstrategia], Par.values()[idPar]);
	}

	@Override
	public void cambiarRangosEstrategia(int idEstrategia, int idPar, Rangos rangos) throws RemoteException 
	{
		Rangos aPoner = IdEstrategia.values()[idEstrategia].darEstrategia().getRangos()[idPar];
		for(Indicador i : Indicador.values())
			aPoner.cambiarRango(i, rangos.darRango(i));
	}
	
	@Override
	public Rangos darRangosEstrategia(int idEstrategia, int idPar) throws RemoteException
	{
		return IdEstrategia.values()[idEstrategia].darEstrategia().getRangos()[idPar];
	}

	@Override
	public List <SenalEstrategia> darSenalesEstrategia(int id) throws RemoteException 
	{
		return IdEstrategia.values()[id].darEstrategia().darSenales();
	}

	@Override
	public List <SenalProveedor> darSenalesProveedor(int id) throws RemoteException
	{
		return IdProveedor.values()[id].darProveedor().darSenales();
	}

	@Override
	public int darGananciaSenalEstrategia(int idEstrategia, int idPar) throws RemoteException 
	{
		return IdEstrategia.values()[idEstrategia].darEstrategia().tienePar(Par.values()[idPar]).darGanancia();
	}

	@Override
	public SenalEstrategia darSenalEstrategia(int idEstrategia, int idPar) throws RemoteException
	{
		return IdEstrategia.values()[idEstrategia].darEstrategia().tienePar(Par.values()[idPar]);
	}
}

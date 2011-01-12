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
	public static class Local implements ConexionRMI
	{
		ConexionRMI servidor;
		
		static volatile Boolean[][][] dpActivos = new Boolean[IdProveedor.values().length][IdEstrategia.values().length][Par.values().length];
		static volatile Rangos[][] dpRangos = new Rangos[IdEstrategia.values().length][Par.values().length];
	
		public Local(ConexionRMI s)
		{
			servidor = s;
		}
		
		@Override
		public void cambiarActivoProveedor(int idProveedor, int idEstrategia, int idPar, boolean activo) throws RemoteException 
		{
			servidor.cambiarActivoProveedor(idProveedor, idEstrategia, idPar, activo);
			dpActivos[idProveedor][idEstrategia][idPar] = activo;
		}
		
		@Override
		public boolean darActivoProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException
		{
			if(dpActivos[idProveedor][idEstrategia][idPar] != null)
				return dpActivos[idProveedor][idEstrategia][idPar];
			return dpActivos[idProveedor][idEstrategia][idPar] = servidor.darActivoProveedor(idProveedor, idEstrategia, idPar);
		}

		@Override
		public void cambiarRangosEstrategia(int idEstrategia, int idPar, Rangos rangos) throws RemoteException 
		{
			servidor.cambiarRangosEstrategia(idEstrategia, idPar, rangos);
			dpRangos[idEstrategia][idPar] = rangos;
		}
		
		@Override
		public Rangos darRangosEstrategia(int idEstrategia, int idPar) throws RemoteException
		{
			if(dpRangos[idEstrategia][idPar] != null)
				return dpRangos[idEstrategia][idPar];
			return dpRangos[idEstrategia][idPar] = servidor.darRangosEstrategia(idEstrategia, idPar);
		}

		@Override
		public List <SenalEstrategia> darSenalesEstrategia(int id) throws RemoteException 
		{
			return servidor.darSenalesEstrategia(id);
		}

		@Override
		public List <SenalProveedor> darSenalesProveedor(int id) throws RemoteException
		{
			return servidor.darSenalesProveedor(id);
		}

		@Override
		public int darGananciaSenalEstrategia(int idEstrategia, int idPar) throws RemoteException 
		{
			return servidor.darGananciaSenalEstrategia(idEstrategia, idPar);
		}

		@Override
		public SenalEstrategia darSenalEstrategia(int idEstrategia, int idPar) throws RemoteException
		{
			return servidor.darSenalEstrategia(idEstrategia, idPar);
		}
	}
	
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

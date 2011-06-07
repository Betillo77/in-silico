package dailyBot.control.conexion;

import java.rmi.RemoteException;
import java.util.List;

import dailyBot.analisis.Indicador;
import dailyBot.analisis.Rangos;
import dailyBot.modelo.Par;
import dailyBot.modelo.SenalEstrategia;
import dailyBot.modelo.SenalProveedor;
import dailyBot.modelo.Estrategia.IdEstrategia;
import dailyBot.modelo.Proveedor.IdProveedor;


public class ConexionServidorRMI extends ConexionServidor implements ConexionRMI
{
	public static class Local implements ConexionRMI
	{
		ConexionRMI servidor;
		
		static Boolean[][][] dpActivos = new Boolean[IdProveedor.values().length][IdEstrategia.values().length][Par.values().length];
		static Rangos[][][] dpRangos = new Rangos[IdProveedor.values().length][IdEstrategia.values().length][Par.values().length];
	
		public Local(ConexionRMI s)
		{
			servidor = s;
		}
		
		@Override
		public synchronized void cambiarActivoProveedor(int idProveedor, int idEstrategia, int idPar, boolean activo, boolean abierto) throws RemoteException 
		{
			servidor.cambiarActivoProveedor(idProveedor, idEstrategia, idPar, activo, abierto);
			dpActivos[idProveedor][idEstrategia][idPar] = activo;
		}
		
		@Override
		public boolean darAbiertoProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException 
		{
			return servidor.darAbiertoProveedor(idProveedor, idEstrategia, idPar);
		}
		
		@Override
		public synchronized boolean darActivoProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException
		{
			if(dpActivos[idProveedor][idEstrategia][idPar] != null)
				return dpActivos[idProveedor][idEstrategia][idPar];
			return dpActivos[idProveedor][idEstrategia][idPar] = servidor.darActivoProveedor(idProveedor, idEstrategia, idPar);
		}

		@Override
		public synchronized void cambiarRangosProveedor(int idProveedor, int idEstrategia, int idPar, Rangos rangos) throws RemoteException 
		{
			servidor.cambiarRangosProveedor(idProveedor, idEstrategia, idPar, rangos);
			dpRangos[idProveedor][idEstrategia][idPar] = rangos;
		}
		
		@Override
		public synchronized Rangos darRangosProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException
		{
			if(dpRangos[idProveedor][idEstrategia][idPar] != null)
				return dpRangos[idProveedor][idEstrategia][idPar];
			return dpRangos[idProveedor][idEstrategia][idPar] = servidor.darRangosProveedor(idProveedor, idEstrategia, idPar);
		}
		
		@Override
		public synchronized List <SenalEstrategia> darSenalesEstrategia(int id) throws RemoteException 
		{
			return servidor.darSenalesEstrategia(id);
		}

		@Override
		public synchronized List <SenalProveedor> darSenalesProveedor(int id) throws RemoteException
		{
			return servidor.darSenalesProveedor(id);
		}
		
		@Override
		public synchronized int darGananciaSenalEstrategia(int idEstrategia, int idPar) throws RemoteException 
		{
			return servidor.darGananciaSenalEstrategia(idEstrategia, idPar);
		}

		@Override
		public synchronized SenalEstrategia darSenalEstrategia(int idEstrategia, int idPar) throws RemoteException
		{
			return servidor.darSenalEstrategia(idEstrategia, idPar);
		}

		@Override
		public boolean darActivo(int idProveedor)
		{
			return servidor.darActivo(idProveedor);
		}

		@Override
		public void cambiarActivo(int idProveedor, boolean activo)
		{
			servidor.cambiarActivo(idProveedor, activo);
		}
	}
	
	@Override
	public void cambiarActivoProveedor(int idProveedor, int idEstrategia, int idPar, boolean activo, boolean abrir) throws RemoteException 
	{
		IdProveedor.values()[idProveedor].darProveedor().cambiarActivo(IdEstrategia.values()[idEstrategia], Par.values()[idPar], activo);
		if(activo && abrir)
			IdProveedor.values()[idProveedor].darProveedor().abrirActivo(IdEstrategia.values()[idEstrategia], Par.values()[idPar]);
	}
	
	@Override
	public boolean darActivoProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException
	{
		return IdProveedor.values()[idProveedor].darProveedor().darActivo(IdEstrategia.values()[idEstrategia], Par.values()[idPar]);
	}
	
	@Override
	public boolean darAbiertoProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException 
	{
		return IdProveedor.values()[idProveedor].darProveedor().darAbierto(IdEstrategia.values()[idEstrategia], Par.values()[idPar]);
	}

	@Override
	public void cambiarRangosProveedor(int idProveedor, int idEstrategia, int idPar, Rangos rangos) throws RemoteException 
	{
		Rangos aPoner = IdProveedor.values()[idProveedor].darProveedor().getRangos()[idEstrategia][idPar];
		for(Indicador i : Indicador.values())
			aPoner.cambiarRango(i, rangos.darRango(i));
	}
	
	@Override
	public Rangos darRangosProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException
	{
		return IdProveedor.values()[idProveedor].darProveedor().getRangos()[idEstrategia][idPar];
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

	@Override
	public boolean darActivo(int idProveedor)
	{
		return IdProveedor.values()[idProveedor].darProveedor().isActivo();
	}

	@Override
	public void cambiarActivo(int idProveedor, boolean activo) 
	{
		IdProveedor.values()[idProveedor].darProveedor().setActivo(activo);
	}
}

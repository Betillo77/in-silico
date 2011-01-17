package dailyBot.control.conexion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import dailyBot.analisis.Rangos;
import dailyBot.modelo.SenalEstrategia;
import dailyBot.modelo.SenalProveedor;

public interface ConexionRMI extends Remote
{
	public List <SenalEstrategia> darSenalesEstrategia(int id) throws RemoteException;
	public List <SenalProveedor> darSenalesProveedor(int id) throws RemoteException;
	public boolean darActivoProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException;
	public void cambiarActivoProveedor(int idProveedor, int idEstrategia, int idPar, boolean activo) throws RemoteException;
	public Rangos darRangosEstrategia(int idEstrategia, int idPar) throws RemoteException;
	public void cambiarRangosEstrategia(int idEstrategia, int idPar, Rangos rangos) throws RemoteException;
	public SenalEstrategia darSenalEstrategia(int idEstrategia, int idPar) throws RemoteException;
	public int darGananciaSenalEstrategia(int idEstrategia, int idPar) throws RemoteException;
}
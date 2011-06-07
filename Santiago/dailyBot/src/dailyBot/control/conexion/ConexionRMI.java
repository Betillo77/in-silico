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
	public boolean darAbiertoProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException;
	public void cambiarActivoProveedor(int idProveedor, int idEstrategia, int idPar, boolean activo, boolean abrir) throws RemoteException;
	public Rangos darRangosProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException;
	public void cambiarRangosProveedor(int idProveedor, int idEstrategia, int idPar, Rangos rangos) throws RemoteException;
	public SenalEstrategia darSenalEstrategia(int idEstrategia, int idPar) throws RemoteException;
	public boolean darActivo(int idProveedor);
	public void cambiarActivo(int idProveedor, boolean activo);
	public int darGananciaSenalEstrategia(int idEstrategia, int idPar) throws RemoteException;
}
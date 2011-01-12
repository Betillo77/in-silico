package control.conexion;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import modelo.SenalEstrategia;
import modelo.SenalProveedor;
import analisis.Rangos;

public interface ConexionRMI extends Remote
{
	public List <SenalEstrategia> darSenalesEstrategia(int id) throws RemoteException;
	public List <SenalProveedor> darSenalesProveedor(int id) throws RemoteException;
	public boolean darActivoProveedor(int idProveedor, int idEstrategia, int idPar) throws RemoteException;
	public void cambiarActivoProveedor(int idProveedor, int idEstrategia, int idPar, boolean activo) throws RemoteException;
	public Rangos darRangosEstrategia(int idEstrategia, int idPar) throws RemoteException;
	public void cambiarRangosEstrategia(int idEstrategia, int idPar, Rangos rangos) throws RemoteException;
}
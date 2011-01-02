package control.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import modelo.Par;
import modelo.SenalEstrategia;
import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;

import com.mysql.jdbc.Driver;

import control.Error;
import control.AnalisisLogica.Entrada;

public class ConexionMySql
{
	static Connection conexion = dbConnect("jdbc:mysql://192.168.0.105:3306/DailyFX", "root", "CalidadIngesis", 0);
	
	public synchronized static void agregarEntrada(IdEstrategia id, SenalEstrategia afectada) 
	{
		long fechaLong = System.currentTimeMillis();
		int ganancia = afectada.darGanancia();
		if(ganancia > 2000)
		{
			Error.agregar("Entrada sospechosa: " + id.name() + ", " + afectada.getPar().name() + ", " + fechaLong + ", �ganancia: " + ganancia + "?");
			return;
		}
		try
		{
			double VIX = afectada.getVIX();
			double SSI1 = afectada.getSSI1();
			double SSI2 = afectada.getSSI2();
			Statement st = conexion.createStatement();
		    st.executeUpdate("INSERT Historial (IdEstrategia,Fecha,Par,Ganancia,VIX,SSI1,SSI2,EsCompra,FechaA,GananciaReal,High,Low) VALUES(" + id.ordinal() + "," + convertirFecha(fechaLong) + "," + afectada.getPar().ordinal() + "," + ganancia + "," + VIX + "," + SSI1 + "," + SSI2 + "," + (afectada.isCompra() ? 1 : 0) + "," + convertirFecha(afectada.getFechaInicio()) + "," + ganancia + "," + afectada.getHigh() + "," + afectada.getLow() + ")");
		}
		catch (SQLException s)
		{
			Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + afectada.getPar().toString() + ", " + fechaLong + ", " + ganancia); 
		}
	}
	
	private static String convertirFecha(long fechaLong)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(fechaLong);
		String fecha = "'" + calendar.get(Calendar.YEAR);
		fecha += "-" + (calendar.get(Calendar.MONTH) + 1);
		fecha += "-" + calendar.get(Calendar.DATE);
		fecha += " " + calendar.get(Calendar.HOUR_OF_DAY);
		fecha += ":" + calendar.get(Calendar.MINUTE);
		fecha += ":" + calendar.get(Calendar.SECOND);
		fecha += "'";
		return fecha;
	}
	
	public synchronized static void guardarPersistencia(IdEstrategia id, String xml) 
	{
		for(int i = 0; i < 100; i++)
		{
			try
			{
				Statement st = conexion.createStatement();
			    st.executeUpdate("UPDATE Estrategias set Datos='" + xml + "' where IdEstrategia=" + (id.ordinal() + 1));
			    return;
			}
			catch (SQLException s)
			{
				Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + xml + " " + i); 
			}
		}
	}
	
	public synchronized static String cargarPersistencia(IdEstrategia id) 
	{
		for(int i = 0; i < 10; i++)
		{
			try 
			{
				ResultSet rs = conexion.createStatement().executeQuery("select * from Estrategias where IdEstrategia=" + (id.ordinal() + 1));
				if(rs.next())
					return rs.getString(2);
				else
					return "";
			} 
			catch (SQLException e) 
			{
				Error.agregar("Error haciendo la lectura de la persistencia de la base de datos en estrategia " + id + ": " + e.getMessage() + " " + i);
			}
		}
		Error.reiniciarSinPersistir();
		return "Error haciendo la lectura de la persistencia de la base de datos en estrategia " + id;
	}
	
	public synchronized static void guardarPersistencia(IdProveedor id, String xml) 
	{
		for(int i = 0; i < 100; i++)
		{
			try
			{
				Statement st = conexion.createStatement();
			    st.executeUpdate("UPDATE Proveedores set Datos='" + xml + "' where IdProveedor=" + (id.ordinal() + 1));
			    return;
			}
			catch (SQLException s)
			{
				Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + xml + " " + i); 
			}
		}
	}
	
	public synchronized static String cargarPersistencia(IdProveedor id) 
	{
		for(int i = 0; i < 10; i++)
		{
			try 
			{
				ResultSet rs = conexion.createStatement().executeQuery("select * from Proveedores where IdProveedor=" + (id.ordinal() + 1));
				if(rs.next())
					return rs.getString(2);
				else
					return "";
			} 
			catch (SQLException e) 
			{
				Error.agregar("Error haciendo la lectura de la persistencia de la base de datos en proveedor " + id + ": " + e.getMessage() + " " + i);
			}
		}
		Error.reiniciarSinPersistir();
		return "Error haciendo la lectura de la persistencia de la base de datos en proveedor " + id;
	}
	
    private static Connection dbConnect(String db_connect_string, String db_userid, String db_password, int intento)
    {
        try
        {
            new Driver();
            Connection conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
            return conn;  
        }
        catch (Exception e)
        {
        	if(intento == 10)
        	{
        		Error.agregar("No se pudo conectar a la base de datos en 10 intentos");
        		Error.reiniciarSinPersistir();
        	}
        	return dbConnect(db_connect_string, db_userid, db_password, intento + 1);
        }
    }

	public synchronized static LinkedList <Entrada> darEntradas(IdEstrategia estrategia) 
	{
		try 
		{
			ResultSet rs = conexion.createStatement().executeQuery("select * from Historial where IdEstrategia=" + estrategia.ordinal());
			LinkedList <Entrada> entradasNuevas = new LinkedList <Entrada> ();
			while(rs.next())
			{
				entradasNuevas.add(new Entrada(Par.values()[rs.getInt("Par")], rs.getDate("Fecha").getTime(), rs.getInt("Ganancia")));
			}
			return entradasNuevas;
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "Error haciendo la lectura de la base de datos");
			return new LinkedList <Entrada> ();
		}
	}
}
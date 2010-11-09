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
import modelo.Senal;
import control.Error;
import control.IdEstrategia;
import control.AnalisisLogica.Entrada;

public class ConexionMySql
{
	static Connection conexion = dbConnect("jdbc:mysql://192.168.0.105:3306/DailyFX", "root", "CalidadIngesis");
	
	public synchronized static void agregarEntrada(IdEstrategia id, Senal afectada) 
	{
		long fechaLong = System.currentTimeMillis();
		int ganancia = afectada.getPar().diferenciaPips(afectada.getPrecioEntrada(), afectada.isCompra());
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
		    st.executeUpdate("INSERT Historial (IdEstrategia,Fecha,Par,Ganancia,VIX,SSI1,SSI2,EsCompra,FechaA,GananciaReal,High,Low) VALUES(" + id.ordinal() + "," + convertirFecha(fechaLong) + "," + afectada.getPar().ordinal() + "," + ganancia + "," + VIX + "," + SSI1 + "," + SSI2 + "," + (afectada.isCompra() ? 1 : 0) + "," + convertirFecha(afectada.getFechaInicio()) + "," + afectada.darGananciaReal() + "," + afectada.getHigh() + "," + afectada.getLow() + ")");
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
		try
		{
			Statement st = conexion.createStatement();
		    st.executeUpdate("UPDATE Persistencia set Datos='" + xml + "' where IdEstrategia=" + (id.ordinal() + 1));
		}
		catch (SQLException s)
		{
			Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + xml); 
		}
	}
	
	public synchronized static String cargarPersistencia(IdEstrategia id) 
	{
		try 
		{
			ResultSet rs = conexion.createStatement().executeQuery("select * from Persistencia where IdEstrategia=" + (id.ordinal() + 1));
			rs.next();
			return rs.getString(2);
		} 
		catch (SQLException e) 
		{
			Error.agregar("Error haciendo la lectura de la persistencia de la base de datos en estrategia " + id + ": " + e.getMessage());
			return "";
		}
	}
	
    private static Connection dbConnect(String db_connect_string, String db_userid, String db_password)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
            return conn;
                
        }
        catch (Exception e)
        {
                return null;
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
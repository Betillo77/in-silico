package control.conexion;

import java.sql.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Par;
import modelo.Senal;

import control.AnalisisLogica.Entrada;
import control.IdEstrategia;
import control.Error;

public class ConexionMySql
{
	static Connection conexion = dbConnect("jdbc:mysql://localhost:3306/DailyFX", "root", "CalidadIngesis");
	
	public synchronized static void agregarEntrada(IdEstrategia id, Senal afectada, long fechaLong, int ganancia) 
	{
		try
		{
			if(ganancia > 2000)
			{
				Error.agregar("Entrada sospechosa: " + id.name() + ", " + afectada.getPar().name() + ", " + fechaLong + ", �ganancia: " + ganancia + "?");
				return;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(fechaLong);
			String fecha = "'" + calendar.get(Calendar.YEAR);
			fecha += "-" + (calendar.get(Calendar.MONTH) + 1);
			fecha += "-" + calendar.get(Calendar.DATE);
			fecha += " " + calendar.get(Calendar.HOUR);
			fecha += ":" + calendar.get(Calendar.MINUTE);
			fecha += ":" + calendar.get(Calendar.SECOND);
			fecha += "'";
			double VIX = afectada.getVIX();
			double SSI1 = afectada.getSSI1();
			double SSI2 = afectada.getSSI2();
			Statement st = conexion.createStatement();
		    st.executeUpdate("INSERT Historial (IdEstrategia,Fecha,Par,Ganancia,VIX,SSI1,SSI2) VALUES(" + id.ordinal() + "," + fecha + "," + afectada.getPar().ordinal() + "," + ganancia + "," + VIX + "," + SSI1 + "," + SSI2 + ")");
		}
		catch (SQLException s)
		{
			Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + afectada.getPar().toString() + ", " + fechaLong + ", " + ganancia); 
		}
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
			JOptionPane.showMessageDialog(null, "Error haciendo la lectura de la persistencia de la base de datos");
			return "";
		}
	}
	
    public synchronized static Connection dbConnect(String db_connect_string, String db_userid, String db_password)
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

	public synchronized static List <Entrada> darEntradas(IdEstrategia estrategia) 
	{
		try 
		{
			ResultSet rs = conexion.createStatement().executeQuery("select * from Historial where IdEstrategia=" + estrategia.ordinal());
			ArrayList <Entrada> entradasNuevas = new ArrayList <Entrada> ();
			while(rs.next())
			{
				entradasNuevas.add(new Entrada(Par.values()[rs.getInt("Par")], rs.getDate("Fecha").getTime(), rs.getInt("Ganancia")));
			}
			return entradasNuevas;
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "Error haciendo la lectura de la base de datos");
			return new ArrayList <Entrada> ();
		}
	}
}
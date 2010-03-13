package control.conexion;

import java.sql.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import control.Entrada;
import control.IdEstrategia;
import control.Par;
import control.Error;


public class ConexionMySql
{
	static Connection conexion = dbConnect("jdbc:mysql://localhost:3306/DailyFX", "root", "CalidadIngesis");
	
	public synchronized static void agregarEntrada(IdEstrategia id, Par par, long fechaLong, int ganancia) 
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(fechaLong);
			String fecha = "'" + calendar.get(Calendar.YEAR);
			fecha += "-" + (calendar.get(Calendar.MONTH) + 1);
			fecha += "-" + calendar.get(Calendar.DATE);
			fecha += " " + calendar.get(Calendar.HOUR);
			fecha += ":" + calendar.get(Calendar.MINUTE);
			fecha += ":" + calendar.get(Calendar.SECOND);
			fecha += "'";
			Statement st = conexion.createStatement();
		    st.executeUpdate("INSERT Historial (IdEstrategia,Fecha,Par,Ganancia) VALUES(" + id.ordinal() + "," + fecha + "," + par.ordinal() + "," + ganancia + ")");
		}
		catch (SQLException s)
		{
			Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + par.toString() + ", " + fechaLong + ", " + ganancia); 
		}
	}
	
    public static Connection dbConnect(String db_connect_string, String db_userid, String db_password)
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

	public static List <Entrada> darEntradas(IdEstrategia estrategia) 
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

class DB
{

}

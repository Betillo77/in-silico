package control.conexion;

import java.sql.*;
import java.util.Calendar;

import control.IdEstrategia;
import control.Par;


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
			System.out.println("SQL statement is not executed!");
		}
	}
	
    public static Connection dbConnect(String db_connect_string, String db_userid, String db_password)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
            System.out.println("connected");
            return conn;
                
        }
        catch (Exception e)
        {
                return null;
        }
    }
}

class DB
{

}

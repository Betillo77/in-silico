package dailyBot.control.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import dailyBot.analisis.RegistroHistorial;
import dailyBot.analisis.Estadistica.EntradaHistoriaPares;
import dailyBot.control.Error;
import dailyBot.control.HiloDaily;
import dailyBot.modelo.Par;
import dailyBot.modelo.SenalEstrategia;
import dailyBot.modelo.Estrategia.IdEstrategia;
import dailyBot.modelo.Proveedor.IdProveedor;

import com.mysql.jdbc.Driver;

public class ConexionMySql
{
	private static AtomicInteger numeroConexiones = new AtomicInteger();
	private static LinkedBlockingQueue <Connection> poolConexiones = iniciarPool();

	private static void cerrar(ResultSet resultSet)
	{
		try
		{
			if(resultSet != null)
				resultSet.close();
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error cerrando un result set");
		}
	}
	
	private static void cerrar(Statement statement)
	{
		try
		{
			if(statement != null)
				statement.close();
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error cerrando un statement");
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
	
	private static String darFechaCalendar(Calendar actual)
	{
		int y = actual.get(Calendar.YEAR);
		int m = actual.get(Calendar.MONTH) + 1;
		int d = actual.get(Calendar.DAY_OF_MONTH);
		return y + "-" + (m < 10 ? "0" : "") + m + "-" + (d < 10 ? "0" : "") + d;
	}
	
	public static void agregarEntrada(IdEstrategia id, SenalEstrategia afectada) 
	{
		Connection conexion = darConexion();
		try
		{
			long fechaLong = System.currentTimeMillis();
			int ganancia = afectada.darGanancia();
			if(ganancia > 2000)
			{
				Error.agregar("Entrada sospechosa: " + id.name() + ", " + afectada.getPar().name() + ", " + fechaLong + ", ganancia: " + ganancia + "?");
				return;
			}
			double VIX = afectada.getVIX();
			double SSI1 = afectada.getSSI1();
			double SSI2 = afectada.getSSI2();
			if(SSI1 > 0)
			{
				SSI1 -= 1;
				if(Math.abs(SSI1) < 0.02)
					SSI1 = 0.01;
			}
			else
			{
				SSI1 += 1;
				if(Math.abs(SSI1) < 0.02)
					SSI1 = -0.01;
			}
			if(SSI2 > 0)
			{
				SSI2 -= 1;
				if(Math.abs(SSI2) < 0.02)
					SSI2 = 0.01;
			}
			else
			{
				SSI2 += 1;
				if(Math.abs(SSI2) < 0.02)
					SSI2 = -0.01;
			}
			Statement st = null;
			try
			{
				st = conexion.createStatement();
				st.setQueryTimeout(60);
			    st.executeUpdate("INSERT Historial (IdEstrategia,Fecha,Par,Ganancia,VIX,SSI1,SSI2,EsCompra,FechaA,High,Low) VALUES(" + id.ordinal() + "," + convertirFecha(fechaLong) + "," + afectada.getPar().ordinal() + "," + ganancia + "," + VIX + "," + SSI1 + "," + SSI2 + "," + (afectada.isCompra() ? 1 : 0) + "," + convertirFecha(afectada.getFechaInicio()) + "," + afectada.getHigh() + "," + afectada.getLow() + ")");
			}
			catch (SQLException s)
			{
				Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + afectada.getPar().toString() + ", " + fechaLong + ", " + ganancia); 
			}
			finally
			{
				cerrar(st);
			}
		}
		finally
		{
			regresarConexion(conexion);
		}
	}

	public static void guardarPersistencia(IdEstrategia id, String xml) 
	{
		Connection conexion = darConexion();
		try
		{
			for(int i = 0; i < 100; i++)
			{
				Statement st = null;
				try
				{
					st = conexion.createStatement();
					st.setQueryTimeout(60);
				    st.executeUpdate("UPDATE Estrategias set Datos='" + xml + "' where IdEstrategia=" + (id.ordinal() + 1));
				    return;
				}
				catch (SQLException s)
				{
					Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + xml + " " + i); 
					HiloDaily.sleep(6000L);
				}
				finally
				{
					cerrar(st);
				}
			}
		}
		finally
		{
			regresarConexion(conexion);
		}
	}
	
	public static String cargarPersistencia(IdEstrategia id) 
	{
		Connection conexion = darConexion();
		try
		{
			for(int i = 0; i < 100; i++)
			{
				Statement st = null;
				ResultSet rs = null;
				try 
				{
					st = conexion.createStatement();
					st.setQueryTimeout(60);
					rs = st.executeQuery("select * from Estrategias where IdEstrategia=" + (id.ordinal() + 1));
					if(rs.next())
						return rs.getString(2);
					else
						return "";
				}
				catch (SQLException e) 
				{
					Error.agregar("Error haciendo la lectura de la persistencia de la base de datos en estrategia " + id + ": " + e.getMessage() + " " + i);
					HiloDaily.sleep(6000L);
				}
				finally
				{
					cerrar(rs);
					cerrar(st);
				}
			}
			Error.reiniciarSinPersistir();
			return null;
		}
		finally
		{
			regresarConexion(conexion);
		}
	}
	
	public static void guardarPersistencia(IdProveedor id, String xml) 
	{
		Connection conexion = darConexion();
		try
		{
			for(int i = 0; i < 100; i++)
			{
				Statement st = null;
				try
				{
					st = conexion.createStatement();
					st.setQueryTimeout(60);
					st.executeUpdate("UPDATE Proveedores set Datos='" + xml + "' where IdProveedor=" + (id.ordinal() + 1));
			    	return;
				}
				catch (SQLException s)
				{
					Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + xml + " " + i);
					HiloDaily.sleep(6000L);
				}
				finally
				{
					cerrar(st);
				}
			}
		}
		finally
		{
			regresarConexion(conexion);
		}
	}
	
	public static void agregarDatosPar(Par par, String fecha, double open, double close, double low, double high) 
	{
		Connection conexion = darConexion();
		Statement st = null;
		try
		{
			st = conexion.createStatement();
			st.setQueryTimeout(60);
			st.executeUpdate("delete from ATR where Par=" + par.ordinal() + " and Fecha='" + fecha + "'");		
			st.executeUpdate("INSERT ATR (Par,Fecha,Open,Close,Low,High) VALUES(" + par.ordinal() + ",'" + fecha + "'," + open + "," + close + "," + low + "," + high + ")");		
		}
		catch (SQLException s)
		{
			Error.agregar(s.getMessage() + " Error agregando datos par");
		}
		finally
		{
			cerrar(st);
			regresarConexion(conexion);
		}
	}
	
	public static void agregarDatosPar(Par par, double open, double close, double low, double high)
	{
		Calendar actual = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		String fecha = darFechaCalendar(actual);
		if(actual.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && actual.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
			agregarDatosPar(par, fecha, open, close, low, high);
	}
	
	public static double[] darDatosPar(Par par, Calendar fechaC)
	{
		String fecha = darFechaCalendar(fechaC);
		Connection conexion = darConexion();
		Statement st = null;
		ResultSet rs = null;
		try
		{
			st = conexion.createStatement();
			st.setQueryTimeout(60);
			rs = st.executeQuery("select * from ATR where Par=" + par.ordinal() + " and Fecha='" + fecha + "'");
			if(rs.next())
				return new double[] {rs.getDouble("Low"), rs.getDouble("High"), rs.getDouble("Open"), rs.getDouble("Close")};
			else
				return new double[] {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY};
		}
		catch (SQLException s)
		{
			Error.agregar(s.getMessage() + ": error cargando datos par " + par.toString() + ", fecha " + fecha); 
			return null;
		}
		finally
		{
			cerrar(rs);
			cerrar(st);
	 		regresarConexion(conexion);	
		}
	}
	
	static class CacheHistoriaPares
	{		
		private static EnumMap < Par, TreeMap <Date, EntradaHistoriaPares> > cache = darCache();
		
		static synchronized EnumMap < Par, TreeMap <Date, EntradaHistoriaPares> > darCache()
		{
			EnumMap < Par, TreeMap <Date, EntradaHistoriaPares> > cache = new EnumMap < Par, TreeMap <Date, EntradaHistoriaPares> >(Par.class);
			if(cache.isEmpty())
				for(Par par : Par.values())
					cache.put(par, new TreeMap <Date, EntradaHistoriaPares> ());
			Connection conexion = darConexion();
			Statement st = null;
			ResultSet rs = null;
			try
			{
				st = conexion.createStatement();
				st.setQueryTimeout(60);
				rs = st.executeQuery("select * from ATR");
				while(rs.next())
				{
					Date fecha = rs.getDate("Fecha");
					Calendar temp = Calendar.getInstance();
					temp.setTime(fecha);
					temp.set(Calendar.HOUR_OF_DAY, 19);
					cache.get(Par.values()[rs.getInt("Par")]).put(temp.getTime(), new EntradaHistoriaPares(rs.getDate("Fecha"), rs.getDouble("Low"), rs.getDouble("High"), rs.getDouble("Open"), rs.getDouble("Close")));
				}
			}
			catch (SQLException s)
			{
				Error.agregar(s.getMessage() + ": error cargando la cache en CacheHistoriaPares"); 
			}
			finally
			{
				cerrar(rs);
				cerrar(st);
				regresarConexion(conexion);
			}
			return cache;
		}
		
		static synchronized void recargarCache()
		{
			cache = darCache();
		}
		
		static synchronized SortedMap <Date, EntradaHistoriaPares> darHasta(Par par, Date fecha)
		{
			return cache.get(par).subMap(java.sql.Date.valueOf("1900-01-01"), fecha);
		}
	}
	
	public static void iniciarDia()
	{
		CacheHistoriaPares.recargarCache();
	}
	
	public static SortedMap <Date, EntradaHistoriaPares> darHistoriaPares(Par par, Date date) 
	{
		return CacheHistoriaPares.darHasta(par, date);
	}
	
	public static String cargarPersistencia(IdProveedor id) 
	{
		Connection conexion = darConexion();
		try
		{
			for(int i = 0; i < 10; i++)
			{
				Statement st = null;
				ResultSet rs = null;
				try 
				{
					st = conexion.createStatement();
					st.setQueryTimeout(60);
					rs = st.executeQuery("select * from Proveedores where IdProveedor=" + (id.ordinal() + 1));
					if(rs.next())
						return rs.getString(2);
					else
						return "";
				} 
				catch (SQLException e) 
				{
					Error.agregar("Error haciendo la lectura de la persistencia de la base de datos en proveedor " + id + ": " + e.getMessage() + " " + i);
				}
				finally
				{
					cerrar(rs);
					cerrar(st);
				}
			}
			Error.reiniciarSinPersistir();
			return "Error haciendo la lectura de la persistencia de la base de datos en proveedor " + id;
		}
		finally
		{
			regresarConexion(conexion);
		}
	}
	
	public static List <RegistroHistorial> darEntradas() 
	{
		Connection conexion = darConexion();
		Statement st = null;
		ResultSet rs = null;
		try 
		{
			st = conexion.createStatement();
			st.setQueryTimeout(60);
			rs = st.executeQuery("select * from Historial");
			LinkedList <RegistroHistorial> entradasNuevas = new LinkedList <RegistroHistorial> ();
			while(rs.next())
				entradasNuevas.add(new RegistroHistorial(IdEstrategia.values()[rs.getInt("IdEstrategia")], Par.values()[rs.getInt("Par")], rs.getInt("EsCompra") == 1, rs.getTimestamp("FechaA").getTime(), rs.getTimestamp("Fecha").getTime(), rs.getInt("Ganancia"), rs.getDouble("VIX"), rs.getDouble("SSI1"), rs.getDouble("SSI2"), rs.getInt("Low"), rs.getInt("High")));
			Collections.sort(entradasNuevas);
			return entradasNuevas;
		} 
		catch (SQLException e) 
		{
			Error.agregar("Error haciendo la lectura de la base de datos");
			return new LinkedList <RegistroHistorial> ();
		}
		finally
		{
			cerrar(rs);
			cerrar(st);
			regresarConexion(conexion);
		}
	}
	
    private static Connection nuevaConexion()
    {
    	String db_connect_string = "jdbc:mysql://192.168.0.105:3306/DailyFX";
    	String db_userid = "root";
    	String db_password = "CalidadIngesis";
    	for(int intento = 0; intento < 11; intento++)
    	{
	        try
	        {
	        	if(numeroConexiones.getAndIncrement() == 0)
	        		DriverManager.registerDriver(new Driver());
	            Connection conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
	            return conn;  
	        }
	        catch (Exception e)
	        {
	        	if(!numeroConexiones.compareAndSet(1, 1))
	        		numeroConexiones.getAndDecrement();
	        	if(intento == 10)
	        	{
	        		Error.agregar("No se pudo conectar a la base de datos en 10 intentos, reiniciando");
	        		Error.reiniciarSinPersistir();
	        	}
	        }
    	}
    	return null;
    }

	private static LinkedBlockingQueue <Connection> iniciarPool() 
	{
		LinkedBlockingQueue <Connection> nueva = new LinkedBlockingQueue <Connection> ();
		for(int i = 0; i < 10; i++)
			try 
			{
				nueva.put(nuevaConexion());
			}
			catch (InterruptedException e) 
			{
				Error.agregar(e.getMessage() + " Error de interrupcion en ConexionMySql");
			}
		return nueva;
	}
	
	private static Connection darConexion()
	{
		Connection posible = poolConexiones.peek();
		if(posible == null)
		{
			if(numeroConexiones.getAndAdd(0) >= 20)
			{
				boolean excepcion = false;
				try 
				{
					posible = poolConexiones.poll(60000L, TimeUnit.MILLISECONDS);
				} 
				catch (InterruptedException e)
				{
					excepcion = true;
				}
				if(posible == null || excepcion == true)
				{
	        		Error.agregar("No se pudo obtener una conexion de la base de datos en 60 segundos, reiniciando");
	        		Error.reiniciarSinPersistir();
				}
				return posible;
			}
			else
				return nuevaConexion();
		}
		else
			return posible;
	}
	
	private static void regresarConexion(Connection conexion)
	{
		boolean excepcion = false;
		try 
		{
			if(conexion != null)
				poolConexiones.put(conexion);
			else
				excepcion = true;
		}
		catch (InterruptedException e)
		{
			Error.agregar(e.getMessage() + " Error de interrupcion en ConexionMySql");
			excepcion = true;
		}
		if(excepcion)
			Error.agregar("Error, una conexion fue descartada sin ser valida");
	}
}
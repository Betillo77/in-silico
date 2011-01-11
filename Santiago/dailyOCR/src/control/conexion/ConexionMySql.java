package control.conexion;

import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JOptionPane;

import modelo.Par;
import modelo.SenalEstrategia;
import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
import analisis.Estadistica;
import analisis.RegistroHistorial;

import com.mysql.jdbc.Driver;

import control.Error;
import control.conexion.ConexionMySql.CacheHistoriaPares.EntradaHistoriaPares;

public class ConexionMySql
{
	private static Connection conexion = dbConnect("jdbc:mysql://192.168.0.105:3306/DailyFX", "root", "CalidadIngesis");
	private static ReentrantLock lock = new ReentrantLock(true);
	
	public static void agregarEntrada(IdEstrategia id, SenalEstrategia afectada) 
	{
		lock.lock();
		try
		{
			long fechaLong = System.currentTimeMillis();
			int ganancia = afectada.darGanancia();
			if(ganancia > 2000)
			{
				Error.agregar("Entrada sospechosa: " + id.name() + ", " + afectada.getPar().name() + ", " + fechaLong + ", ganancia: " + ganancia + "?");
				return;
			}
			try
			{
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
				Statement st = conexion.createStatement();
			    st.executeUpdate("INSERT Historial (IdEstrategia,Fecha,Par,Ganancia,VIX,SSI1,SSI2,EsCompra,FechaA,High,Low) VALUES(" + id.ordinal() + "," + convertirFecha(fechaLong) + "," + afectada.getPar().ordinal() + "," + ganancia + "," + VIX + "," + SSI1 + "," + SSI2 + "," + (afectada.isCompra() ? 1 : 0) + "," + convertirFecha(afectada.getFechaInicio()) + "," + afectada.getHigh() + "," + afectada.getLow() + ")");
			    st.close();
			}
			catch (SQLException s)
			{
				Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + afectada.getPar().toString() + ", " + fechaLong + ", " + ganancia); 
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public static String convertirFecha(long fechaLong)
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
	
	public static void guardarPersistencia(IdEstrategia id, String xml) 
	{
		lock.lock();
		try
		{
			for(int i = 0; i < 100; i++)
			{
				try
				{
					Statement st = conexion.createStatement();
				    st.executeUpdate("UPDATE Estrategias set Datos='" + xml + "' where IdEstrategia=" + (id.ordinal() + 1));
				    st.close();
				    return;
				}
				catch (SQLException s)
				{
					Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + xml + " " + i); 
				}
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public static String cargarPersistencia(IdEstrategia id) 
	{
		lock.lock();
		try
		{
			for(int i = 0; i < 10; i++)
			{
				try 
				{
					Statement st = conexion.createStatement();
					ResultSet rs = st.executeQuery("select * from Estrategias where IdEstrategia=" + (id.ordinal() + 1));
					try
					{
						if(rs.next())
							return rs.getString(2);
						else
							return "";
					}
					finally
					{
						rs.close();
						st.close();
					}
				} 
				catch (SQLException e) 
				{
					Error.agregar("Error haciendo la lectura de la persistencia de la base de datos en estrategia " + id + ": " + e.getMessage() + " " + i);
				}
			}
			Error.reiniciarSinPersistir();
			return "Error haciendo la lectura de la persistencia de la base de datos en estrategia " + id;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public static void guardarPersistencia(IdProveedor id, String xml) 
	{
		lock.lock();
		try
		{
			for(int i = 0; i < 100; i++)
			{
				try
				{
					Statement st = conexion.createStatement();
				    try
				    {
						st.executeUpdate("UPDATE Proveedores set Datos='" + xml + "' where IdProveedor=" + (id.ordinal() + 1));
					    return;
				    }
				    finally
				    {
				    	st.close();
				    }
				}
				catch (SQLException s)
				{
					Error.agregar("Error escribiendo a la base de datos: " + id.toString() + ", " + xml + " " + i); 
				}
			}
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public static void agregarATR(Par par, String fecha, double open, double close, double low, double high) 
	{
		lock.lock();
		try
		{
			Statement st = conexion.createStatement();
			try
			{
				st.executeUpdate("INSERT ATR (Par,Fecha,Open,Close,Low,High) VALUES(" + par.ordinal() + ",'" + fecha + "'," + open + "," + close + "," + low + "," + high + ")");		
			}
			finally
			{
				st.close();
			}
		}
		catch (SQLException s)
		{
			System.out.println(s.getMessage());
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public static void agregarATR(Par par, double open, double close, double low, double high)
	{
		Calendar actual = Calendar.getInstance();
		int y = actual.get(Calendar.YEAR);
		int m = actual.get(Calendar.MONTH) + 1;
		int d = actual.get(Calendar.DAY_OF_MONTH);
		String fecha = y + "-" + (m < 10 ? "0" : "") + m + "-" + (d < 10 ? "0" : "") + d;
		if(actual.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && actual.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
			agregarATR(par, fecha, open, close, low, high);
	}
	
	static class CacheHistoriaPares
	{
		static class EntradaHistoriaPares
		{
			Date fecha;
			double high;
			double low;
			double open;
			double close;
			
			EntradaHistoriaPares(Date f, double l, double h, double o, double c)
			{
				fecha = f;
				low = l;
				high = h;
				open = o;
				close = c;
			}
		}
		
		@SuppressWarnings("unchecked")
		static TreeMap <Date, EntradaHistoriaPares> [] cache = new TreeMap[Par.values().length];
		
		static
		{
			for(int i = 0; i < Par.values().length; i++)
				cache[i] = new TreeMap <Date, EntradaHistoriaPares> ();
		}
		
		public static void cargarCache()
		{
			lock.lock();
			try
			{
				Statement st = conexion.createStatement();
				try
				{
					ResultSet rs = st.executeQuery("select * from ATR");
					while(rs.next())
					{
						Date fecha = rs.getDate("Fecha");
						Calendar temp = Calendar.getInstance();
						temp.setTime(fecha);
						temp.set(Calendar.HOUR_OF_DAY, 19);
						cache[rs.getInt("Par")].put(temp.getTime(), new EntradaHistoriaPares(rs.getDate("Fecha"), rs.getDouble("Low"), rs.getDouble("High"), rs.getDouble("Open"), rs.getDouble("Close")));
					}
				}
				finally
				{
					st.close();
				}
			}
			catch (SQLException s)
			{
				Error.agregar(s.getMessage() + ": error cargando la cache en CacheHistoriaPares"); 
			}
			finally
			{
				lock.unlock();
			}
		}
		
		public static SortedMap <Date, EntradaHistoriaPares> darHasta(Par par, Date fecha)
		{
			return cache[par.ordinal()].subMap(java.sql.Date.valueOf("1900-01-01"), fecha);
		}
	}
	
	static ArrayList <Double> valores = new ArrayList <Double> ();
	
	public static double darATR(Par par, int periodo, long fechaCierre)
	{
		lock.lock();
		try
		{
			Statement st = conexion.createStatement();
			try
			{
				SortedMap <Date, EntradaHistoriaPares> mapa = CacheHistoriaPares.darHasta(par, new Date(fechaCierre));
				double cierreAnterior = 0;
				valores.clear();
				int i = 0;
				for(EntradaHistoriaPares actual : mapa.values())
				{
					if(i++ == 0)
					{
						cierreAnterior = actual.close;
						continue;
					}
					valores.add(Math.max((actual.high - actual.low) * par.multiplicador(), Math.max(Math.abs(actual.high - cierreAnterior) * par.multiplicador(), Math.abs(actual.low - cierreAnterior) * par.multiplicador())));
					cierreAnterior = actual.close;
				}
				return Estadistica.calcularEMA(valores, periodo);
			}
			finally
			{
				st.close();
			}
		}
		catch (SQLException s)
		{
			Error.agregar(s.getMessage() + ": error leyendo ATR de la base de datos, par: " + par + ", periodo: " + periodo); 
			return 0;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public static double darATR(Par par, int periodo)
	{
		return darATR(par, periodo, Calendar.getInstance().getTimeInMillis());
	}
	
	public static double darRSI(Par par, int periodo, long fechaCierre)
	{
		lock.lock();
		try
		{
			Statement st = conexion.createStatement();
			try
			{
				SortedMap <Date, EntradaHistoriaPares> mapa = CacheHistoriaPares.darHasta(par, new Date(fechaCierre));
				double cierreAnterior = 0;
				valores.clear();
				int i = 0;
				for(EntradaHistoriaPares actual : mapa.values())
				{
					if(i++ == 0)
					{
						cierreAnterior = actual.close;
						continue;
					}
					double nuevo = actual.close - cierreAnterior;
					if(nuevo > 0)
						valores.add(nuevo);
					else
						valores.add(0d);
					cierreAnterior = actual.close;
				}
				double emaU = Estadistica.calcularEMA(valores, periodo);
				cierreAnterior = 0;
				valores.clear();
				i = 0;
				for(EntradaHistoriaPares actual : mapa.values())
				{
					if(i++ == 0)
					{
						cierreAnterior = actual.close;
						continue;
					}
					double nuevo = actual.close - cierreAnterior;
					if(nuevo < 0)
						valores.add(-nuevo);
					else
						valores.add(0d);
					cierreAnterior = actual.close;
				}
				double emaD = Estadistica.calcularEMA(valores, periodo);
				double rS = emaU / emaD;
				return emaD < 1e-8 ? 100 : 100d - 100d / (1d + rS);
			}
			finally
			{
				st.close();
			}
		}
		catch (SQLException s)
		{
			Error.agregar(s.getMessage() + ": error leyendo ATR de la base de datos, par: " + par + ", periodo: " + periodo); 
			return 0;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public static String cargarPersistencia(IdProveedor id) 
	{
		lock.lock();
		try
		{
			for(int i = 0; i < 10; i++)
			{
				try 
				{
					Statement st = conexion.createStatement();
					try
					{
						ResultSet rs = st.executeQuery("select * from Proveedores where IdProveedor=" + (id.ordinal() + 1));
						if(rs.next())
							return rs.getString(2);
						else
							return "";
					}
					finally
					{
						st.close();
					}
				} 
				catch (SQLException e) 
				{
					Error.agregar("Error haciendo la lectura de la persistencia de la base de datos en proveedor " + id + ": " + e.getMessage() + " " + i);
				}
			}
			Error.reiniciarSinPersistir();
			return "Error haciendo la lectura de la persistencia de la base de datos en proveedor " + id;
		}
		finally
		{
			lock.unlock();
		}
	}
	
	public static LinkedList <RegistroHistorial> darEntradas() 
	{
		CacheHistoriaPares.cargarCache();
		lock.lock();
		try 
		{
			Statement st = conexion.createStatement();
			try
			{
				ResultSet rs = st.executeQuery("select * from Historial");
				LinkedList <RegistroHistorial> entradasNuevas = new LinkedList <RegistroHistorial> ();
				while(rs.next())
					entradasNuevas.add(new RegistroHistorial(IdEstrategia.values()[rs.getInt("IdEstrategia")], Par.values()[rs.getInt("Par")], rs.getInt("EsCompra") == 1, rs.getTimestamp("FechaA").getTime(), rs.getTimestamp("Fecha").getTime(), rs.getInt("Ganancia"), rs.getDouble("VIX"), rs.getDouble("SSI1"), rs.getDouble("SSI2"), rs.getInt("Low"), rs.getInt("High")));
				return entradasNuevas;
			}
			finally
			{
				st.close();
			}
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "Error haciendo la lectura de la base de datos");
			return new LinkedList <RegistroHistorial> ();
		}
		finally
		{
			lock.unlock();
		}
	}
	
    private static Connection dbConnect(String db_connect_string, String db_userid, String db_password)
    {
    	for(int intento = 0; intento < 11; intento++)
    	{
	        try
	        {
	            DriverManager.registerDriver(new Driver());
	            Connection conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
	            return conn;  
	        }
	        catch (Exception e)
	        {
	        	if(intento == 10)
	        	{
	        		Error.agregar("No se pudo conectar a la base de datos en 10 intentos, reiniciando");
	        		Error.reiniciarSinPersistir();
	        	}
	        }
    	}
    	return null;
    }
}
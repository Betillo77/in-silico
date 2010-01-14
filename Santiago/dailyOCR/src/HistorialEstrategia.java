import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class HistorialEstrategia implements Serializable
{
	private static final long serialVersionUID = -300574046206128357L;
	
	IdEstrategia estrategia;
	private ArrayList <Entrada> historial;
	
	public HistorialEstrategia(IdEstrategia estrategia)
	{
		this.estrategia = estrategia;
		historial = new ArrayList <Entrada> ();
	}
	
	public synchronized void agregarEntrada(Par par, long fecha, int ganancia)
	{
		historial.add(new Entrada(par, fecha, ganancia));
	}
	
	public synchronized ArrayList <Entrada> darHistorial() 
	{
		return historial;
	}
	
	public void escribir(File archivo)
	{
		try 
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo));
			oos.writeObject(this);	
			oos.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Manejar errores
		}
		catch (IOException e) 
		{
			// TODO Manejar errores
		}	
	}
	
	public void escribirCSV(File archivo)
	{
		try 
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
			for(Entrada entrada : historial)
			{
				bw.write(entrada + System.getProperty("line.separator"));
			}
			bw.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Manejar errores
		}
		catch (IOException e) 
		{
			// TODO Manejar errores
		}	
	}
	
	public static HistorialEstrategia leer(File archivo)
	{
		try 
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
			HistorialEstrategia he = (HistorialEstrategia) ois.readObject();
			ois.close();
			return he;
		} 
		catch (ClassNotFoundException e) 
		{
			return null;
			// TODO Manejar errores
		}
		catch (IOException e) 
		{
			return null;
			// TODO Manejar errores
		}	
	}
	
	//TODO Funciones de analisis del historial
	
}

class Entrada implements Serializable, Comparable <Entrada>
{
	private static final long serialVersionUID = 2596947460811823087L;
	
	Par par;
	long fecha;
	int ganancia;
	
	public Entrada(Par par, long fecha, int ganancia)
	{
		this.par = par;
		this.fecha = fecha;
		this.ganancia = ganancia;
	}
	
	@Override
	public String toString()
	{
		Calendar fecha = Calendar.getInstance();
		fecha.setTimeInMillis(this.fecha);
		String fechaS = fecha.get(Calendar.DAY_OF_MONTH) + "/"  + (1 + fecha.get(Calendar.MONTH)) + "/" + fecha.get(Calendar.YEAR) + " " + fecha.get(Calendar.HOUR_OF_DAY) + ":" + fecha.get(Calendar.MINUTE); 
		return par + ";" + fechaS + ";" + ganancia;
	}

	@Override
	public int compareTo(Entrada o) 
	{
		return new Long(fecha).compareTo(o.fecha);
	}
}
package modelo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import modelo.Proveedor.IdProveedor;
import control.Error;
import control.conexion.ConexionMySql;

public class Estrategia
{	
	public enum IdEstrategia
	{
		BREAKOUT1, BREAKOUT2, RANGE1, RANGE2, MOMENTUM1, MOMENTUM2, TECHNICAL, JOEL;
		
		Estrategia esta = null;
		
		public Estrategia darEstrategia()
		{
			if(esta == null)
				Error.agregar("Estrategia " + this + " fue llamada antes de ser registrada.");
			return esta;
		}
		
		public void iniciarEstrategia()
		{
			esta = Estrategia.leer(this);
			if(esta == null)
				esta = new Estrategia(this);
		}
	}
	
	protected IdEstrategia id;
	protected List <SenalEstrategia> senales = new LinkedList <SenalEstrategia> ();

	public Estrategia()
	{
	}
	
	public Estrategia(IdEstrategia id)
	{
		this.id = id;
	}
	
	public synchronized void agregar(Par par, boolean hit, boolean compra, int numeroLotes, double precioEntrada, SenalEstrategia afectada) 
	{
		if(hit)
		{
			afectada.setNumeroLotes(afectada.getNumeroLotes() - numeroLotes);
			for(int i = 0; i < numeroLotes; i++)
				ConexionMySql.agregarEntrada(id, afectada);
			if(afectada.getNumeroLotes() <= 0)
			{
				for(IdProveedor id : IdProveedor.values())
					id.darProveedor().agregar(afectada, hit);
				senales.remove(afectada);
				afectada.getPar().eliminarSenal(afectada);
			}
		}
		else
		{
			SenalEstrategia nueva = new SenalEstrategia(id, compra, par, numeroLotes, precioEntrada, 0);
			if(tienePar(par) != null)
			{
	    		Error.agregar("Error, par: " + par + ", ya existe en esta estrategia " + id.toString());
	    		return;
			}
			for(IdProveedor id : IdProveedor.values())
				id.darProveedor().agregar(nueva, hit);
			senales.add(nueva);
			nueva.getPar().agregarSenal(nueva);
		}
	}
	
	public synchronized void chequearStops()
	{
		for(SenalEstrategia afectada : senales)
		{
			if(Math.abs(afectada.darStop()) < 10e-4d)
				continue;
			if(afectada.isCompra())
			{
				if(afectada.getPar().darPrecioActual(true) <= afectada.darStop() && (afectada.getNumeroLotes() < 4 || afectada.darStop() > afectada.darStopDaily()))
				{
					if(!afectada.isTocoStop())
						Error.agregarInfo(afectada.toString() + " toco stop: precio actual -> " + afectada.getPar().darPrecioActual(true) + ", stop -> " + afectada.darStop());
					for(IdProveedor p : IdProveedor.values())
						if(!afectada.isTocoStop())
							p.darProveedor().tocoStop(afectada);
					afectada.setTocoStop(true);
				}
			}
			else
			{
				if(afectada.getPar().darPrecioActual(false) >= afectada.darStop() && (afectada.getNumeroLotes() < 4 || afectada.darStop() < afectada.darStopDaily()))
				{
					if(!afectada.isTocoStop())
						Error.agregarInfo(afectada.toString() + " toco stop: precio actual -> " + afectada.getPar().darPrecioActual(false) + ", stop -> " + afectada.darStop());
					for(IdProveedor p : IdProveedor.values())
						if(!afectada.isTocoStop())
							p.darProveedor().tocoStop(afectada);
					afectada.setTocoStop(true);
				}
			}
		}
	}
	
	public synchronized SenalEstrategia tienePar(Par par) 
	{
		for(SenalEstrategia senal : senales)
		{
			if(senal.getPar().equals(par))
				return senal;
		}
		return null;
	}

	public synchronized boolean verificarConsistencia() 
	{
			return senales == null || id == null;
	}
	
    public synchronized void escribir()
    {
    	try
    	{
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        XMLEncoder encoder = new XMLEncoder(baos);
	        encoder.writeObject(this);
	        encoder.close();
	        String salida = new String(baos.toByteArray());
	        ConexionMySql.guardarPersistencia(id, salida);
    	}
    	catch(Exception e)
    	{
    		Error.agregar("Error en la escritura en la base de datos: " + id.name() + " " + e.getMessage());
    	}
    }
    
    public static Estrategia leer(IdEstrategia id)
    {
    	try
    	{
	    	String entrada = ConexionMySql.cargarPersistencia(id);
	    	if(entrada.length() < 10)
	    		return null;
	    	char[] entradaChar = entrada.toCharArray();
	    	byte[] entradaByte = new byte[entradaChar.length];
	    	int i = 0;
	    	for(char c : entradaChar)
	    	{
	    		entradaByte[i++] = (byte) c;
	    	}
	    	XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(entradaByte));
	    	Estrategia e = (Estrategia) (decoder.readObject());
	    	decoder.close();
	    	return e;
    	}
    	catch(Exception e)
    	{
    		Error.agregar("Error de lectura de base de datos: " + id.name());
    		Error.reiniciarSinPersistir();
    		return null;
    	}
    }

	public IdEstrategia getId() 
	{
		return id;
	}

	public void setId(IdEstrategia id)
	{
		this.id = id;
	}

	public synchronized List <SenalEstrategia> darSenales() 
	{
		LinkedList <SenalEstrategia> senalesNuevas = new LinkedList <SenalEstrategia> ();
		if(senales == null)
			return null;
		for(SenalEstrategia s : senales)
		{
			senalesNuevas.add(s);
		}
		return senalesNuevas;
	}
	
	public List <SenalEstrategia> getSenales()
	{
		return senales;
	}

	public void setSenales(List <SenalEstrategia> senales)
	{
		this.senales = new LinkedList <SenalEstrategia> ();
		for(SenalEstrategia s : senales)
		{
			this.senales.add(s);
			s.getPar().agregarSenal(s);
		}
	}
}
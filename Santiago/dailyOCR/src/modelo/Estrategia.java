package modelo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import control.Error;
import control.IdEstrategia;
import control.conexion.ConexionMySql;

public class Estrategia
{	
	protected IdEstrategia id;
	protected List <Senal> senales;
	protected List <Senal> senalesNoSync = new ArrayList <Senal> ();
	private boolean[] activos = new boolean[Par.values().length];
	protected Escritor escritor;
	protected ArrayList < ArrayList <EntradaEscritor> > entradasEscritor = null;

	public Estrategia()
	{
		senales = Collections.synchronizedList(senalesNoSync);
	}
	
	public Estrategia(IdEstrategia id)
	{
		this.id = id;
		senales = Collections.synchronizedList(senalesNoSync);
	}
	
	public void cambiarActivo(Par par, boolean activo)
	{
		if(activos == null)
			activos = new boolean[Par.values().length];
		synchronized(activos)
		{
			int i = 0;
			for(Par p : Par.values())
			{
				if(p.equals(par))
				{
					activos[i] = activo;
					return;
				}
				i++;
			}
		}
	}
	
	public boolean darActivo(Par par)
	{
		if(activos == null)
			activos = new boolean[Par.values().length];
		synchronized(activos)
		{
			int i = 0;
			for(Par p : Par.values())
			{
				if(p.equals(par))
				{
					return activos[i];
				}
				i++;
			}
			return false;
		}
	}
	
	public void agregar(SenalEntrada entrada, Senal afectada) 
	{
		if(entrada.getTipo().equals(TipoSenal.HIT))
		{
			hit(entrada, afectada);
		}
		else
		{
			trade(entrada);
		}
	}
	
	protected void hit(SenalEntrada entrada, Senal afectada) 
	{
		synchronized(senales)
		{
			escritor.cerrar(entrada, afectada);
			if(afectada.getNumeroLotes() <= 0)
			{
				senales.remove(afectada);
			}
		}
	}
	
	protected void trade(SenalEntrada entrada) 
	{
		synchronized(senales)
		{
			Senal nueva = new Senal(id, entrada.isCompra(), entrada.getPar(), entrada.getNumeroLotes(), entrada.getPrecioEntrada());
			nueva.setLimite(entrada.getLimite());
			if(tienePar(entrada.getPar()) != null)
			{
	    		Error.agregar("Par ya exite en esta estrategia " + id.toString());
	    		return;
			}
			escritor.abrir(entrada, nueva);
			senales.add(nueva);
			nueva.getPar().agregarSenal(nueva);
		}
	}
	
	public Senal tienePar(Par par) 
	{
		synchronized(senales)
		{
			for(Senal senal : senales)
			{
				if(senal.getPar().equals(par))
					return senal;
			}
		}
		return null;
	}

	public boolean verificarConsistencia() 
	{
		synchronized(senales)
		{
			synchronized(activos)
			{
				return senales == null || id == null || activos == null || escritor == null;
			}
		}
	}
	
    public void escribir()
    {
    	synchronized(senales)
    	{
    		synchronized(activos)
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
		    		Error.agregar("Error en la escritura en la base de datos: " + id.name());
		    	}
    		}
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
    		return null;
    	}
    }

	public IdEstrategia getId() {
		return id;
	}

	public void setId(IdEstrategia id) {
		this.id = id;
	}

	public List <Senal> getSenales() {
		synchronized(senales)
		{
			return senalesNoSync;
		}
	}
	
	public List <Senal> getSenalesSync() {
		synchronized(senales)
		{
			return senales;
		}
	}
	
	public List <Senal> getSenalesCopy() 
	{
		ArrayList <Senal> senalesNuevas = new ArrayList <Senal> ();
		if(senales == null)
			return null;
		synchronized(senales)
		{
			for(Senal s : senales)
			{
				senalesNuevas.add(s);
			}
		}
		return senalesNuevas;
	}

	public void setSenales(List <Senal> senales) {
		this.senalesNoSync = senales;
		this.senales = Collections.synchronizedList(senales);
	}

	public boolean[] getActivos() {
		synchronized(activos)
		{
			return activos;
		}
	}

	public void setActivos(boolean[] activos) {
		this.activos = activos;
	}
	
	public ArrayList < ArrayList <EntradaEscritor> > getEntradasEscritor() {
		return entradasEscritor;
	}

	public void setEntradasEscritor(ArrayList < ArrayList <EntradaEscritor> > entradasEscritor) {
		this.entradasEscritor = entradasEscritor;
	}
	
	public void ponerEscritor(Escritor e) {
		escritor = e;
		for(Senal s : senales)
			s.getPar().agregarSenal(s);
	}
}
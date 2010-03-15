package modelo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import control.Error;
import control.Escritor;
import control.IdEstrategia;
import control.Par;
import control.TipoSenal;
import control.dailyOCR;
import control.conexion.ConexionMySql;

public class Estrategia
{	
	private IdEstrategia id;
	private List <Senal> senales;
	private boolean[] activos = new boolean[Par.values().length];
	public Escritor escritor;
	
	public Estrategia()
	{
	}
	
	public Estrategia(IdEstrategia id)
	{
		this.id = id;
		senales = Collections.synchronizedList(new ArrayList <Senal> ());
		if(id == IdEstrategia.BREAKOUT2)
			for(int i = 0; i < activos.length; i++)
				activos[i] = true;
	}
	
	public synchronized void cambiarActivo(Par par, boolean activo)
	{
		if(activos == null)
			activos = new boolean[Par.values().length];
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
	
	public synchronized boolean darActivo(Par par)
	{
		if(activos == null)
			activos = new boolean[Par.values().length];
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
	
	public void agregar(SenalEntrada entrada, Senal afectada, boolean dejarLista) 
	{
		synchronized(senales)
		{
			if(entrada.getTipo().equals(TipoSenal.HIT))
			{
				hit(entrada, afectada, dejarLista);
			}
			else
			{
				trade(entrada, dejarLista);
			}
		}
	}
	
	private void hit(SenalEntrada entrada, Senal afectada, boolean dejarLista) 
	{
		int lotesAnteriores = afectada.getNumeroLotes();
		escritor.cerrar(entrada, afectada);
		BidAsk parActual = null;
		for(BidAsk ba : dailyOCR.preciosActuales)
		{
			if(ba.getCurrency().equals(afectada.getPar()))
			{
				parActual = ba;
				break;
			}
		}
		if(parActual == null)
		{
    		Error.agregar("Par no encontrado");
			return;
		}
		int resultado = 0;
		if(!afectada.isCompra())
		{
			if(afectada.getPrecioEntrada() > 10)
			{
				resultado = (int) Math.round((afectada.getPrecioEntrada() - parActual.getAsk()) * 100);
			}
			else
			{
				resultado = (int) Math.round((afectada.getPrecioEntrada() - parActual.getAsk()) * 10000);
			}
		}
		else
		{
			if(afectada.getPrecioEntrada() > 10)
			{
				resultado = (int) Math.round((parActual.getBid() - afectada.getPrecioEntrada()) * 100);
			}
			else
			{
				resultado = (int) Math.round((parActual.getBid() - afectada.getPrecioEntrada()) * 10000);
			}
		}
		if(afectada.getNumeroLotes() == 0)
		{
			if(!dejarLista)
				senales.remove(afectada);
			else
			{
				afectada.setLotesCerradosManualmente(lotesAnteriores);
				afectada.setMagico(new int[1]);
			}
			if(!dejarLista)
				for(int i = 0; i < entrada.getNumeroLotes(); i++)
					ConexionMySql.agregarEntrada(id, afectada.getPar(), System.currentTimeMillis(), resultado);
		}
		else if(afectada.getNumeroLotes() < 0)
		{
			senales.remove(afectada);
			for(int i = 0; i < afectada.getLotesCerradosManualmente(); i++)
				ConexionMySql.agregarEntrada(id, afectada.getPar(), System.currentTimeMillis(), resultado);
		}
		else
		{
			for(int i = 0; i < entrada.getNumeroLotes(); i++)
				ConexionMySql.agregarEntrada(id, afectada.getPar(), System.currentTimeMillis(), resultado);
		}
	}
	
	private void trade(SenalEntrada entrada, boolean dejarLista) 
	{
		Senal nueva = new Senal(id, entrada.isCompra(), entrada.getPar(), entrada.getNumeroLotes(), entrada.getPrecioEntrada());
		nueva.setLimite(entrada.getLimite());
		if(dejarLista)
		{
			nueva.setManual(true);
		}
		if(tienePar(entrada.getPar()) != null)
		{
    		Error.agregar("Par ya exite en esta estrategia " + id.toString());
    		return;
		}
		escritor.abrir(entrada, nueva);
		senales.add(nueva);
	}
	
	public Senal tienePar(Par par) 
	{
		for(Senal senal : senales)
		{
			if(senal.getPar() == par)
				return senal;
		}
		return null;
	}
	

	public boolean verificarConsistencia() 
	{
		return senales == null || id == null || activos == null || escritor == null;
	}
	
    public void escribir()
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
		if(senales == null)
			return null;
		ArrayList <Senal> senalesNuevas = new ArrayList <Senal> ();
		for(Senal s : senales)
		{
			senalesNuevas.add(s);
		}
		return senalesNuevas;
	}

	public void setSenales(List <Senal> senales) {
		this.senales = Collections.synchronizedList(senales);
	}

	public boolean[] getActivos() {
		return activos;
	}

	public void setActivos(boolean[] activos) {
		this.activos = activos;
	}
}

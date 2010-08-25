package modelo.dailyFx;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import modelo.Estrategia;
import modelo.Par;
import modelo.Senal;
import modelo.SenalEntrada;
import modelo.TipoSenal;
import control.Error;
import control.IdEstrategia;
import control.conexion.ConexionMySql;

public class EstrategiaElite extends Estrategia 
{
	private boolean[][] activosElite = new boolean[6][Par.values().length];
	
	public EstrategiaElite()
	{
		super();
	}
	
	public EstrategiaElite(IdEstrategia elite) 
	{
		super(elite);
	}

	public synchronized void agregar(SenalEntrada entrada, IdEstrategia id) 
	{
		if(activosElite[id.ordinal()][entrada.getPar().ordinal()])
		{
			synchronized(senales)
			{
				if(entrada.getTipo().equals(TipoSenal.HIT))
				{
					for(Senal s : senales)
					{
						if(s.getEstrategia().equals(id) && s.getPar().equals(entrada.getPar()))
						{
							hit(entrada, s, false);
							return;
						}
					}
					Error.agregar("No se pudo encontrar senal en DailyFx-Elite " + id + " " + entrada.getPar());
				}
				else
				{
					Senal nueva = new Senal(id, entrada.isCompra(), entrada.getPar(), entrada.getNumeroLotes(), entrada.getPrecioEntrada());
					for(Senal s : senales)
					{
						if(s.getEstrategia().equals(id) && s.getPar().equals(nueva.getPar()))
						{
							Error.agregar("Par ya exite en esta estrategia " + this.id.toString());
							return;
						}
					}
					escritor.abrir(entrada, nueva);
					senales.add(nueva);
				}
			}
		}
	}
	
    public synchronized void escribir()
    {
    	synchronized(senales)
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
    
    public static EstrategiaElite leer(IdEstrategia id)
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
	    	EstrategiaElite e = (EstrategiaElite) (decoder.readObject());
	    	decoder.close();
	    	return e;
    	}
    	catch(Exception e)
    	{
    		Error.agregar("Error de lectura de base de datos: " + id.name());
    		return null;
    	}
    }
    
    public synchronized boolean verificarConsistencia() 
    {
    	synchronized(senales)
    	{
			boolean borro = true;
			while(borro)
			{
				borro = false;
		    	for(Senal s : senales)
		    	{
		    		if(s.getNumeroLotes() == 0)
		    		{
		    	    	senales.remove(s);
		    	    	borro = true;
		    	    	break;
		    		}
		    	}
			}
    	}
    	return super.verificarConsistencia();
    }
	
	public synchronized void setActivosElite(boolean[][] activosElite) 
	{
		this.activosElite = activosElite;
	}

	public synchronized boolean[][] getActivosElite() 
	{
		return activosElite;
	}

	public synchronized boolean darActivo(IdEstrategia id, Par par) 
	{
		return activosElite[id.ordinal()][par.ordinal()];
	}

	public synchronized void cambiarActivo(Par par, boolean selected, IdEstrategia id) 
	{
		activosElite[id.ordinal()][par.ordinal()] = selected;
	}
}

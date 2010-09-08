package modelo.dailyFx;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;

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

	public void agregar(SenalEntrada entrada, IdEstrategia id) 
	{
		synchronized(senales)
		{
			if(darActivo(id, entrada.getPar()))
			{
				if(entrada.getTipo().equals(TipoSenal.HIT))
				{
					for(Senal s : senales)
					{
						if(s.getEstrategia().equals(id) && s.getPar().equals(entrada.getPar()))
						{
							entrada.setNumeroLotes(s.getNumeroLotes() - entrada.getNumeroLotes());
							hit(entrada, s);
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
					nueva.getPar().agregarSenal(nueva);
				}
			}
		}
	}
	
    @Override
	public void escribir()
    {
    	synchronized(senales)
    	{
    		synchronized(activosElite)
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
    
    @Override
	public boolean verificarConsistencia() 
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
		    	    	s.getPar().eliminarSenal(s);
		    	    	borro = true;
		    	    	break;
		    		}
		    	}
			}
	    	return super.verificarConsistencia();
    	}
    }
	
	public void setActivosElite(boolean[][] activosElite) 
	{
		this.activosElite = activosElite;
	}

	public boolean[][] getActivosElite() 
	{
		return activosElite;
	}

	public boolean darActivo(IdEstrategia id, Par par) 
	{
		synchronized(activosElite)
		{
			return activosElite[id.ordinal()][par.ordinal()];
		}
	}

	public void cambiarActivo(Par par, boolean selected, IdEstrategia id) 
	{
		synchronized(activosElite)
		{
			activosElite[id.ordinal()][par.ordinal()] = selected;
		}
	}

	public void cerrar(Par par, IdEstrategia estrategia) 
	{
    	synchronized(senales)
    	{
    		for(Iterator <Senal> it = senales.iterator(); it.hasNext();)
    		{
    			Senal s = it.next();
    			if(s.getEstrategia().equals(estrategia)  && s.getPar().equals(par))
    				it.remove();
    		}
    	}
	}

	public Senal tienePar(IdEstrategia id, Par par) 
	{
    	synchronized(senales)
    	{
    		for(Iterator <Senal> it = senales.iterator(); it.hasNext();)
    		{
    			Senal s = it.next();
    			if(s.getEstrategia().equals(id) && s.getPar().equals(par))
    				return s;
    		}
    	}
    	return null;
	}
}
package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Estrategia implements Serializable
{	
	private static final long serialVersionUID = 5641887914135732067L;
	
	public IdEstrategia id;
	public List <Senal> senales;
	public HistorialEstrategia historial;
	public boolean[] activos = new boolean[Par.values().length];
	public Escritor escritor;
	
	public Estrategia(IdEstrategia id)
	{
		this.id = id;
		senales = Collections.synchronizedList(new ArrayList <Senal> ());
		historial = new HistorialEstrategia(id);
	}
	
	public synchronized void cambiarActivo(Par par, boolean activo)
	{
		if(activos == null)
			activos = new boolean[Par.values().length];
		int i = 0;
		for(Par p : Par.values())
		{
			if(p == par)
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
			if(p == par)
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
			if(entrada.tipo.equals(TipoSenal.HIT))
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
		int lotesAnteriores = afectada.numeroLotes;
		escritor.cerrar(entrada, afectada);
		BidAsk parActual = null;
		for(BidAsk ba : dailyOCR.preciosActuales)
		{
			if(ba.currency.equals(afectada.par))
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
		if(!afectada.compra)
		{
			if(afectada.precioEntrada > 10)
			{
				resultado = (int) Math.round((afectada.precioEntrada - parActual.ask) * 100);
			}
			else
			{
				resultado = (int) Math.round((afectada.precioEntrada - parActual.ask) * 10000);
			}
		}
		else
		{
			if(afectada.precioEntrada > 10)
			{
				resultado = (int) Math.round((parActual.bid - afectada.precioEntrada) * 100);
			}
			else
			{
				resultado = (int) Math.round((parActual.bid - afectada.precioEntrada) * 10000);
			}
		}
		if(afectada.numeroLotes == 0)
		{
			if(!dejarLista)
				senales.remove(afectada);
			else
			{
				afectada.lotesCerradosManualmente = lotesAnteriores;
				afectada.magico = new int[1];
			}
			if(!dejarLista)
				for(int i = 0; i < entrada.numeroLotes; i++)
					historial.agregarEntrada(afectada.par, System.currentTimeMillis(), resultado);
		}
		else if(afectada.numeroLotes < 0)
		{
			senales.remove(afectada);
			for(int i = 0; i < afectada.lotesCerradosManualmente; i++)
				historial.agregarEntrada(afectada.par, System.currentTimeMillis(), resultado);
		}
		else
		{
			for(int i = 0; i < entrada.numeroLotes; i++)
				historial.agregarEntrada(afectada.par, System.currentTimeMillis(), resultado);
		}
	}
	
	private void trade(SenalEntrada entrada, boolean dejarLista) 
	{
		Senal nueva = new Senal(id, entrada.compra, entrada.par, entrada.numeroLotes, entrada.precioEntrada);
		if(id.equals(IdEstrategia.TECHNICAL))
			nueva.limite = entrada.limite;
		if(dejarLista)
		{
			nueva.manual = true;
		}
		if(tienePar(entrada.par) != null)
		{
    		Error.agregar("Par ya exite en esta estrategia " + id.toString());
		}
		escritor.abrir(entrada, nueva);
		senales.add(nueva);
	}
	
	public Senal tienePar(Par par) 
	{
		for(Senal senal : senales)
		{
			if(senal.par == par)
				return senal;
		}
		return null;
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
    		Error.agregar(e.getMessage() + " Error no se encontro el archivo en escribir estrategia");
		}
		catch (IOException e) 
		{
    		Error.agregar(e.getMessage() + " Error de entrada/salida en estrategia");
		}	
	}
	
	public static Estrategia leer(File archivo)
	{
		try 
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo));
			Estrategia e = (Estrategia) ois.readObject();
			ois.close();
			return e;
		} 
		catch (ClassNotFoundException e) 
		{
    		Error.agregar(e.getMessage() + " Error al leer estrategia");
			return null;
		}
		catch (IOException e) 
		{
    		Error.agregar(e.getMessage() + " Error de entrada salida al leer estrategia");
			return null;
			
		}	
	}
}

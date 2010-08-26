package modelo.technical;

import java.io.IOException;
import java.util.ArrayList;

import modelo.Escritor;
import modelo.Estrategia;
import modelo.Par;
import modelo.Senal;
import modelo.SenalEntrada;
import modelo.SistemaEstrategias;
import modelo.TipoSenal;
import control.Error;
import control.IdEstrategia;
import control.dailyOCR;
import control.conexion.technical.ConexionServidorTechnical;

public class SistemaTechnical extends SistemaEstrategias 
{
	Escritor escritor;
	Estrategia technical;
	
	public void cargarEstrategias() 
	{
		escritor = new Escritor("technical/");
		technical = Estrategia.leer(IdEstrategia.TECHNICAL);
		if(technical == null)
		{
			technical = new Estrategia(IdEstrategia.TECHNICAL);
		}
		technical.escritor = escritor;
		try
		{
			metodoLectura = ConexionServidorTechnical.class.getMethod("leerServidorTechnical");
		}
		catch (Exception e)
		{
    		Error.agregar(e.getMessage() + " Error metodo invalido en Sistema technical");
		}
		persistir();
	}

	public void verificarConsistencia() 
	{
		if(technical == null || technical.verificarConsistencia())
		{
			cargarEstrategias();
		}
	}
	
	public void iniciarHilo() 
	{
		new Thread(new Runnable()
		{
			@Override
			public void run() 
			{
				int numeroErrores = 0;
				while(true)
				{
					try
					{
						verificarConsistencia();
						Thread.sleep(10000);
						iniciarProcesamiento();
						synchronized(este())
						{
						    escritor.escribir();
							escritor.leerMagicos();
							verificarConsistencia();
							persistir();
						}
					}
					catch(Exception e)
					{	
						try
						{
							numeroErrores++;
				    		Error.agregar(e.getMessage() + " Error en el ciclo Technical");
				    		Thread.sleep(60000);
							if(numeroErrores == 60)
							{
								Error.agregar(e.getMessage() + " Error de lectura, intentando reiniciar.");
								Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
								{
									@Override
									public void run() 
									{
										try 
										{
											Runtime.getRuntime().exec("java -jar dailyOCR.jar");
										} 
										catch (IOException e)
										{
								    		Error.agregar(e.getMessage() + " Error reiniciando");
										}
									}
								}));
								System.exit(0);
							}
						}
						catch(Exception e1)
						{
				    		Error.agregar(e.getMessage() + " Error en el ciclo Technical");
						}
					}
				}
			}
		}).start();
	}
	
	protected ArrayList <Senal> leer(String[] contenidos)
	{
		ArrayList <Senal> nuevas = new ArrayList <Senal> (10);
		try
		{
			for(int i = 0; i < contenidos.length; i++)
			{
				String actual = contenidos[i];
				String par = actual.substring(0, actual.indexOf(" "));
				Par estePar = Par.convertirPar(par);
				int indice = actual.indexOf("Our preference:");
				if(indice == -1)
				{
					Error.agregar("Mensaje invalido: " + actual);
					continue;
				}
				actual = actual.substring(indice);
				boolean compra = actual.contains("Long") || actual.contains("long") || actual.contains("buy") || actual.contains("Buy");
				ArrayList <Double> valores = new ArrayList <Double> (10);
				String [] partido = actual.split(" ");
				for(String s : partido)
				{
					try
					{
						double prueba = Double.parseDouble(s);
						valores.add(prueba);
					}
					catch(Exception e)
					{
					}
				}
				try
				{
					double limite = valores.get(2);
					nuevas.add(new Senal(IdEstrategia.TECHNICAL, compra, estePar, 1, limite));
				}
				catch(Exception e)
				{
		    		Error.agregar(e.getMessage() + " Error al a�adir nuevas senales en sistema technical");
				}
			}
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error al a�adir nuevas senales en sistema technical");
		}
		return nuevas;
	}
	
	protected void procesar(ArrayList <Senal> senalesLeidas)
	{
		try
		{
			for(Senal senal : senalesLeidas)
			{
				boolean esta = false;
				synchronized(technical.getSenalesSync())
				{
					for(Senal otra : technical.getSenalesSync())
					{
						if(senal.getPar() == otra.getPar())
						{
							esta = true;
							if(senal.isCompra() != otra.isCompra())
							{
								technical.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, 1, 0), otra, false);
							}
						}
					}
				}
				if(!esta)
				{
			    	SenalEntrada nueva = new SenalEntrada(senal.getPar(), TipoSenal.TRADE, senal.isCompra(), 1, dailyOCR.precioPar(senal.getPar(), senal.isCompra()));
			    	nueva.setLimite(senal.getPrecioEntrada());
					double precioActual = dailyOCR.precioPar(senal.getPar(), !senal.isCompra());
			    	double cambio = nueva.getLimite() > 10 ?	0.8 : 0.08;
			    	double anteriorLimite;
			    	if(senal.isCompra())
			    		anteriorLimite = nueva.getLimite() - cambio;
			    	else
			    		anteriorLimite = nueva.getLimite() + cambio;
			    	if(senal.isCompra() && precioActual > anteriorLimite)
			    		continue;
			    	if(!senal.isCompra() && precioActual < anteriorLimite)
			    		continue;
					technical.agregar(nueva, senal, false);
				}
			}
			synchronized(technical.getSenalesSync())
			{
				for(Senal otra : technical.getSenalesSync())
				{
					double precioActual = dailyOCR.precioPar(otra.getPar(), !otra.isCompra());
					if(otra.isCompra() && otra.getLimite() < precioActual)
					{
						technical.agregar(new SenalEntrada(otra.getPar(), TipoSenal.HIT, false, 1, 0), otra, false);
					}
					if(!otra.isCompra() && otra.getLimite() > precioActual)
					{
						technical.agregar(new SenalEntrada(otra.getPar(), TipoSenal.HIT, false, 1, 0), otra, false);
					}
				}
			}
		}
		catch(Exception e)
		{
			Error.agregar(e.getMessage() + " Error al a�adir nuevas senales en sistema technical");
		}
	}
	
	public void persistir() 
	{
		technical.escribir();
	}
	
	public Estrategia darEstrategia(IdEstrategia id)
	{
		if(id == IdEstrategia.TECHNICAL)
		{
			return technical;
		}
		return null;
	}
	
	public void chequearSenales(boolean enviarMensaje) 
	{
		String mensaje = this.getClass().getCanonicalName() + " OK";
		if(enviarMensaje)
			Error.agregar(mensaje);
	}
}

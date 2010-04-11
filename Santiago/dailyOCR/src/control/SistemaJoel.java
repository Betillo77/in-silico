package control;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.Estrategia;
import modelo.Senal;
import modelo.SenalEntrada;
import control.conexion.ConexionServidor;

public class SistemaJoel extends SistemaEstrategias 
{
	Escritor escritor;
	Estrategia joel;

	public void cargarEstrategias() 
	{
		escritor = new Escritor("joel/experts/files/", SistemaJoel.class);
		joel = Estrategia.leer(IdEstrategia.JOEL);
		if(joel == null)
		{
			joel = new Estrategia(IdEstrategia.JOEL);
		}
		joel.escritor = escritor;
		try
		{
			metodoLectura = ConexionServidor.class.getMethod("leerServidorJoel");
		}
		catch (Exception e)
		{
    		Error.agregar(e.getMessage() + " Error metodo invalido en Sistema Joel");
		}
		persistir();
	}
	
	public void verificarConsistencia() 
	{
		if(joel == null || joel.verificarConsistencia())
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
						Thread.sleep(1200000);
						iniciarProcesamiento();
					    escritor.escribir();
					    escritor.leerMagicos();
						verificarConsistencia();
						persistir();
					}
					catch(Exception e)
					{	
						try
						{
							numeroErrores++;
				    		Error.agregar(e.getMessage() + " Error en el ciclo Joel");
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
				    		Error.agregar(e.getMessage() + " Error en el ciclo Joel");
						}
					}
				}
			}
		}).start();
	}
	
	public static Senal deducir(String subject)
    {
    	boolean recomendado = false;
    	boolean compra = false;
    	Par par = null;
    	double precioDeEntrada = -1;
    	if(subject.contains("@") || subject.contains("at"))
    	{
    		if(subject.contains("RECOMMENDATION"))
    		{
    			recomendado = true;	
    		}
    		if(subject.contains("BUY")||subject.contains("BOUGHT")||subject.contains("LONG"))
    		{
    			compra = true;	
    		}
    		else if(subject.contains("SELL")||subject.contains("SOLD")||subject.contains("SHORT"))
    		{
    			compra = false;
    		}
    		else
    		{
    			return null;
    		}
    		Pattern BuscaPares = Pattern.compile("[A-Z]*/[A-Z]*");
    		Matcher match = BuscaPares.matcher(subject);
    		if(match.find())
    		{	
    			String ConSlash = match.group();
    			String SinSlash = ConSlash.replace("/" , "");
    			Par a = Par.convertirPar(SinSlash);
    			if(a != null)
    			{
    				par = a;
    			}
    			else
    			{
    				return null;
    			}
    		}
    		Pattern At = Pattern.compile("@\\d+.?\\d*");
    		Matcher match1 = At.matcher(subject);
    		Pattern At1 = Pattern.compile("at \\d+.?\\d*");
    		Matcher match2 = At1.matcher(subject);
    		if(match1.find())
    		{
    			String conAt = match1.group();
    			String sinAt = conAt.substring(1);
    			precioDeEntrada = Double.parseDouble(sinAt);
    		}
    		else if(match2.find())
    		{
    			String conAt = match2.group();
    			String sinAt = conAt.substring(3);
    			precioDeEntrada = Double.parseDouble(sinAt);
    		}
    		else
    		{
    			return null;
    		}
    		if(par != null && precioDeEntrada > 0)
    		{
        		if(!recomendado && precioDeEntrada > 0)
        			precioDeEntrada = -1;
        		else if(recomendado && precioDeEntrada < 0)
        			return null;
    	    	Senal nueva = new Senal(IdEstrategia.JOEL, compra, par, 1, precioDeEntrada); 
    			return nueva;
    		}
    	}
    	return null;	
    }
	
	protected ArrayList <Senal> leer(String[] lecturas) 
	{
		if(lecturas.length > 0)
		{
			try
			{
				if(!new File("joel/emails.txt").exists())
					new File("joel/emails.txt").createNewFile();
	            BufferedWriter bw = new BufferedWriter(new FileWriter("joel/emails.txt", true));
	            for(String s : lecturas)
	            {
	            	bw.write(s + "\n");
	            }
	            bw.close();
			}
			catch(Exception e)
			{
			}
		}
		ArrayList <Senal> senalesLeidas = new ArrayList <Senal> ();
		for(String titulo : lecturas)
		{
			Senal sj = deducir(titulo);
			if(sj != null)
				senalesLeidas.add(sj);
		}
		return senalesLeidas;
	}
	
	protected void procesar(ArrayList <Senal> senalesLeidas) 
	{
		try
		{
			for(Senal senal : senalesLeidas)
			{
				try
				{
					joel.agregar(new SenalEntrada(senal.getPar(), TipoSenal.TRADE, senal.isCompra(), 1, senal.getPrecioEntrada()), senal, false);
				}
				catch(Exception e)
				{
					Error.agregar("Error procesando senal de Joel: " + e.getMessage());
				}
			}
		}
		catch(Exception e)
		{
			Error.agregar("Se produjo un error en el sistema Joel: " + e.getMessage());
		}
	}
	
	public void persistir() 
	{
		joel.escribir();
	}
	
	public Estrategia darEstrategia(IdEstrategia id)
	{
		if(id == IdEstrategia.JOEL)
		{
			return joel;
		}
		return null;
	}
	
	public static Collection <String> metodoMeta(SenalEntrada entrada, Senal afectada)
	{
		ArrayList <String> lineas = new ArrayList <String> ();
		if(entrada.getTipo().equals(TipoSenal.HIT))
			lineas.add(entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";CLOSE;" + afectada.getMagico()[0]);
		else
			lineas.add(entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";OPEN;" + afectada.getPrecioEntrada());
		return lineas;
	}
}

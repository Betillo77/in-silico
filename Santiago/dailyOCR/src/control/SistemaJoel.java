package control;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.Estrategia;
import modelo.Senal;
import modelo.SenalEntrada;
import modelo.SenalJoel;
import control.conexion.ConexionServidor;




public class SistemaJoel extends SistemaEstrategias 
{
	static final String pathPrincipal = "joel";
	public static int numeroCorreosAnterior = 0;
	Estrategia joel;
	Estrategia joelRecomendaciones;

	public void cargarEstrategias() 
	{
		escritor = new Escritor("joel/");
		joel = Estrategia.leer(IdEstrategia.JOEL);
		if(joel == null)
		{
			joel = new Estrategia(IdEstrategia.JOEL);
		}
		joel.escritor = escritor;
		joelRecomendaciones = Estrategia.leer(IdEstrategia.JOELRECOMENDACIONES);
		if(joelRecomendaciones == null)
		{
			joelRecomendaciones = new Estrategia(IdEstrategia.JOELRECOMENDACIONES);
		}
		try
		{
			metodoLectura = ConexionServidor.class.getMethod("leerServidorJoel");
		}
		catch (Exception e)
		{
    		Error.agregar(e.getMessage() + " Error metodo invalido en Sistema Joel");
		}
	}
	
	public void verificarConsistencia() 
	{
		if(joel == null || joelRecomendaciones == null || joel.verificarConsistencia() || joelRecomendaciones.verificarConsistencia())
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
	
	public static SenalJoel deducir(String subject)
    {
    	boolean recomendado = false;
    	boolean compra = false;
    	Par par = null;
    	int numeroDeLotes = 2;
    	double precioDeEntrada = 0.0;
    	double parada = 0.0;
    	double limite = Double.NaN;
    	if(subject.contains("@"))
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
    		}
    		Pattern At = Pattern.compile("@\\d+.?\\d*");
    		Matcher match1 = At.matcher(subject);
    		if(match1.find())
    		{
    			String conAt = match1.group();
    			String sinAt = conAt.substring(1);
    			precioDeEntrada = Double.parseDouble(sinAt);
    		}
    		Pattern Stop = Pattern.compile("STOP\\s\\d+.?\\d*");
    		Matcher match2 = Stop.matcher(subject);
    		if(match2.find())
    		{
    			String conStop = match2.group();
    			String sinStop = conStop.substring(5);
    			parada = Double.parseDouble(sinStop);
    		}
    		if(subject.contains("LIMIT"))
    		{
    			Pattern Limit = Pattern.compile("LIMIT\\s\\d+.?\\d*");
    			Matcher match3 = Limit.matcher(subject);
    			if(match3.find())
    			{
    				String conLimite = match3.group();
    				String sinLimite = conLimite.substring(6);
    				limite = Double.parseDouble(sinLimite);
    			}
    		}
    		
    		if(par != null && precioDeEntrada > 0 && parada > 0)
    		{
    	    	SenalJoel nueva = new SenalJoel(IdEstrategia.JOEL, compra, par, numeroDeLotes, precioDeEntrada, parada, recomendado, limite); 
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
			SenalJoel sj = deducir(titulo);
			if(sj != null)
				senalesLeidas.add(sj);
		}
		return senalesLeidas;
	}
	
	protected void procesar(ArrayList <Senal> senalesLeidas) 
	{
		try
		{
//			Calendar c = Calendar.getInstance();
//			for(Iterator <Senal> it = joelRecomendaciones.senales.iterator(); it.hasNext();)
//			{
//				try
//				{
//					SenalJoel senalJoel = (SenalJoel) it.next();
//					Calendar c1 = Calendar.getInstance();
//					c1.setTimeInMillis(senalJoel.tiempoEntrada);
//					if(c.get(Calendar.DATE) == c1.get(Calendar.DATE))
//					{
//						if(c.get(Calendar.HOUR) > 17)
//						{
//							it.remove();
//						}
//						else
//						{
//							double precioActual = dailyOCR.precioPar(senalJoel.par, senalJoel.compra);
//							if((senalJoel.compra && senalJoel.precioEntrada <= precioActual) || (!senalJoel.compra && senalJoel.precioEntrada >= precioActual))
//							{
//								joel.agregar(new SenalEntrada(senalJoel.par, TipoSenal.TRADE, senalJoel.compra, 1, precioActual), senalJoel, false);
//								it.remove();
//							}
//						}
//					}
//					else
//					{
//						it.remove();
//					}
//				}
//				catch(Exception e)
//				{
//					Error.agregar("Se produjo un error manejando las recomendaciones de Joel: " + e.getMessage());
//				}
//			}
			for(Senal senalSimple : senalesLeidas)
			{
				try
				{
					SenalJoel senalJoel = (SenalJoel) senalSimple;
//					if(senalJoel.recomendado)
//					{
//						joelRecomendaciones.senales.add(senalJoel);
//					}
//					else
//					{
						joel.agregar(new SenalEntrada(senalJoel.par, TipoSenal.TRADE, senalJoel.compra, 1, senalJoel.precioEntrada), senalJoel, false);
//					}
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
		joelRecomendaciones.escribir();
	}
	
	public Estrategia darEstrategia(IdEstrategia id)
	{
		if(id == IdEstrategia.JOEL)
		{
			return joel;
		}
		if(id == IdEstrategia.JOELRECOMENDACIONES)
		{
			return joelRecomendaciones;
		}
		return null;
	}
}

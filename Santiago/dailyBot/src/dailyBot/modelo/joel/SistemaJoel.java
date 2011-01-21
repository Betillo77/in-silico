package dailyBot.modelo.joel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dailyBot.control.AdministradorHilos;
import dailyBot.control.Error;
import dailyBot.control.HiloDaily;
import dailyBot.control.RunnableDaily;
import dailyBot.modelo.Escritor;
import dailyBot.modelo.Estrategia;
import dailyBot.modelo.Par;
import dailyBot.modelo.SenalEstrategia;
import dailyBot.modelo.SistemaEstrategias;
import dailyBot.modelo.Estrategia.IdEstrategia;

public class SistemaJoel extends SistemaEstrategias 
{
	Escritor escritor;
	Estrategia joel;

	public void cargarEstrategias() 
	{
/*		escritor = new Escritor("joel/experts/files/", null);
		joel = Estrategia.leer(IdEstrategia.JOEL);
		if(joel == null)
		{
			joel = new Estrategia(IdEstrategia.JOEL);
		}
		joel.ponerEscritor(escritor);
		IdEstrategia.JOEL.registrarEstrategia(joel);
		try
		{
			metodoLectura = ConexionServidorJoel.class.getMethod("leerServidorJoel");
		}
		catch (Exception e)
		{
    		Error.agregar(e.getMessage() + " Error metodo invalido en Sistema Joel");
		}
		persistir(); 
*/	}
	
	@Override
	public void verificarConsistencia() 
	{
		if(joel == null || joel.verificarConsistencia())
		{
			cargarEstrategias();
		}
	}
	
	@Override
	public void iniciarHilo() 
	{
		HiloDaily hiloPrincipal = new HiloDaily(new RunnableDaily()
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
						HiloDaily.sleep(1200000);
						iniciarProcesamiento(null);
//						synchronized(this)
//						{
//						    escritor.terminarCiclo();
//							verificarConsistencia();
//							persistir();
//						}
					}
					catch(Exception e)
					{	
						try
						{
							numeroErrores++;
				    		Error.agregar(e.getMessage() + " Error en el ciclo Joel");
				    		HiloDaily.sleep(60000);
							if(numeroErrores == 60)
							{
								Error.agregar(e.getMessage() + " Error de lectura, intentando reiniciar.");
							}
						}
						catch(Exception e1)
						{
				    		Error.agregar(e.getMessage() + " Error en el ciclo Joel");
						}
					}
					ponerUltimaActulizacion(System.currentTimeMillis());
				}
			}
		}, 2400000L);
		hiloPrincipal.setName("Principal " + getClass().getCanonicalName());
		AdministradorHilos.agregarHilo(hiloPrincipal);
	}
	
	public static SenalEstrategia deducir(String subject)
    {
    	boolean recomendado = false;
    	boolean compra = false;
    	Par par = null;
    	double precioDeEntrada = -1;
    	if(subject.contains("@") || subject.contains("at"))
    	{
    		if(subject.toUpperCase().contains("RECOMMENDATION"))
    		{
    			recomendado = true;	
    		}
    		if(subject.toUpperCase().contains("BUY")||subject.toUpperCase().contains("BOUGHT")||subject.toUpperCase().contains("LONG"))
    		{
    			compra = true;	
    		}
    		else if(subject.toUpperCase().contains("SELL")||subject.toUpperCase().contains("SOLD")||subject.toUpperCase().contains("SHORT"))
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
    	    	SenalEstrategia nueva = new SenalEstrategia(IdEstrategia.JOEL, compra, par, 1, precioDeEntrada, 0); 
    			return nueva;
    		}
    	}
    	return null;	
    }
	
	@Override
	protected ArrayList <SenalEstrategia> leer(String[] lecturas) 
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
		ArrayList <SenalEstrategia> senalesLeidas = new ArrayList <SenalEstrategia> ();
		for(String titulo : lecturas)
		{
			SenalEstrategia sj = deducir(titulo);
			if(sj != null)
				senalesLeidas.add(sj);
		}
		return senalesLeidas;
	}
	
	@Override
	protected void procesar(String[] entrada) 
	{
/*		try
		{
			for(Senal senal : senalesLeidas)
			{
				try
				{
					joel.agregar(new SenalEntrada(joel.getId(), senal.getPar(), TipoSenal.TRADE, senal.isCompra(), 1, senal.getPrecioEntrada()), senal);
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
*/	}
	
	@Override
	public void persistir() 
	{
		joel.escribir();
	}

	public void chequearSenales(boolean enviarMensaje) 
	{
		String mensaje = this.getClass().getCanonicalName() + " OK";
		if(enviarMensaje)
			Error.agregar(mensaje);
	}
}

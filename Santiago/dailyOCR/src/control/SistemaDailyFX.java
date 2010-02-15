package control;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import servidor.ConexionServidor;



public class SistemaDailyFX extends SistemaEstrategias
{
	Estrategia breakout1;
	File b1 = new File(pathPersistencia + "breakout1.o");
	
	Estrategia breakout2;
	File b2 = new File(pathPersistencia + "breakout2.o");
	
	Estrategia range1;
	File r1 = new File(pathPersistencia + "range1.o");

	Estrategia range2;
	File r2 = new File(pathPersistencia + "range2.o");
	
	Estrategia momentum1;
	File m1 = new File(pathPersistencia + "momentum1.o");
	
	Estrategia momentum2;
	File m2 = new File(pathPersistencia + "momentum2.o");
	
	Estrategia technical;
	File t = new File(pathPersistencia + "technical.o");
	
	ArrayList <Estrategia> estrategias;
	
	public void cargarEstrategias()
	{
		escritor = new Escritor("C:/Program Files (x86)/dailyFX/experts/files/");
		if(b1.exists())
		{
			breakout1 = Estrategia.leer(b1);
		}
		else
		{
			breakout1 = new Estrategia(IdEstrategia.BREAKOUT1);
		}
		breakout1.escritor = escritor;
		if(b2.exists())
		{
			breakout2 = Estrategia.leer(b2);
		}
		else
		{
			breakout2 = new Estrategia(IdEstrategia.BREAKOUT2);
		}
		breakout2.escritor = escritor;
		if(r1.exists())
		{
			range1 = Estrategia.leer(r1);
		}
		else
		{
			range1 = new Estrategia(IdEstrategia.RANGE1);
		}
		range1.escritor = escritor;
		if(r2.exists())
		{
			range2 = Estrategia.leer(r2);
		}
		else
		{
			range2 = new Estrategia(IdEstrategia.RANGE2);
		}
		range2.escritor = escritor;
		if(m1.exists())
		{
			momentum1 = Estrategia.leer(m1);
		}
		else
		{
			momentum1 = new Estrategia(IdEstrategia.MOMENTUM1);
		}
		momentum1.escritor = escritor;
		if(m2.exists())
		{
			momentum2 = Estrategia.leer(m2);
		}
		else
		{
			momentum2 = new Estrategia(IdEstrategia.MOMENTUM2);
		}
		momentum2.escritor = escritor;
		estrategias = new ArrayList <Estrategia> ();
		estrategias.add(breakout1);
		estrategias.add(breakout2);
		estrategias.add(momentum1);
		estrategias.add(momentum2);
		estrategias.add(range1);
		estrategias.add(range2);
		try
		{
			metodoLectura = ConexionServidor.class.getMethod("leerServidorDailyFX");
		}
		catch (Exception e)
		{
    		Error.agregar(e.getMessage() + " Error en metodo lectura de sistemaDailyFx");
		}
	}

	public void verificarConsistencia()
	{
		if(breakout1 == null || breakout2 == null || range1 == null || range2 == null || momentum1 == null || momentum2 == null)
		{
			cargarEstrategias();
		}
	}

	protected ArrayList <Senal> leer(String [] entradas)
	{
		String entrada = entradas[0];
		String entrada1 = entrada.substring(entrada.indexOf("\"Signal\":["));
		ArrayList <Integer> curoplots = new ArrayList <Integer> ();
		ArrayList <Integer> strategyid = new ArrayList <Integer> ();
		ArrayList <String> symbol = new ArrayList <String> ();
		ArrayList <String> direction = new ArrayList <String> ();
		ArrayList <Double> entryprice = new ArrayList <Double> ();
		ArrayList <Double> bid = new ArrayList <Double> ();
		ArrayList <Double> ask = new ArrayList <Double> ();
		ArrayList <String> currency = new ArrayList <String> ();
		Pattern pattern = Pattern.compile("\\d+,\"strategyId\":\\d+");
		Pattern pattern2 = Pattern.compile("\"strategyId\":\\d+");
		Pattern pattern3 = Pattern.compile("\"symbol\":\"\\w+\"");
		Pattern pattern4 = Pattern.compile("\"direction\":\"\\w+\"");
		Pattern pattern5 = Pattern.compile("\"entryPrice\":\\d+.\\d+");
		Pattern pattern6 = Pattern.compile("\"bid\":\\d+.\\d+");
		Pattern pattern7 = Pattern.compile("\"ask\":\\d+.\\d+");
		Pattern pattern8 = Pattern.compile("\"currency\":\"\\w+\"");
		Matcher matcher = pattern.matcher(entrada1);
		Matcher matcher2 = pattern2.matcher(entrada1);
		Matcher matcher3 = pattern3.matcher(entrada1);
		Matcher matcher4 = pattern4.matcher(entrada1);
		Matcher matcher5 = pattern5.matcher(entrada1);
		Matcher matcher6 = pattern6.matcher(entrada);
		Matcher matcher7 = pattern7.matcher(entrada);
		Matcher matcher8 = pattern8.matcher(entrada);
		while(matcher.find()) 
		{  
			String S = matcher.group();
			S = S.substring(0, 1);
			curoplots.add(Integer.parseInt(S));
		} 
		while(matcher2.find())
		{
			String S = matcher2.group();
			S = S.substring(13);
			strategyid.add(Integer.parseInt(S));  
		}
		while(matcher3.find()) 
		{
			String S = matcher3.group();
			S = S.substring(10);
			S = S.replace("\"", "");
			symbol.add(S);
  		}
		while(matcher4.find()) 
		{
	  		String S = matcher4.group();
	  		S = S.substring(13);
	  		S = S.replace("\"", "");
	  		direction.add(S);
		}
		while (matcher5.find()) 
		{
			String S = matcher5.group();
			S = S.substring(13);
			entryprice.add(Double.parseDouble(S));	
		}
		while (matcher6.find())
		{
			String S = matcher6.group();
			S = S.substring(6);
			bid.add(Double.parseDouble(S));	
		}
		while (matcher7.find())
		{
			String S = matcher7.group();
			S = S.substring(6);
			ask.add(Double.parseDouble(S));	
		}
		while (matcher8.find())
		{
			String S = matcher8.group();
			S = S.substring(12);
	  		S = S.replace("\"", "");
	  		currency.add(S);
		}
		ArrayList <Senal> nuevasSenales = new ArrayList <Senal> ();
		for(int i = 0; i<curoplots.size(); i++)
		{
			Senal actual = new Senal(IdEstrategia.darEstrategia(strategyid.get(i)), direction.get(i).equals("Buy"), Par.convertirPar(symbol.get(i)), curoplots.get(i), entryprice.get(i));
			nuevasSenales.add(actual);
		}
		ArrayList <BidAsk> precio = new ArrayList <BidAsk>();
		for(int i=0;i<bid.size();i++)
		{
			if(Par.convertirPar(currency.get(i)) == null)
			{
				continue;
			}
			BidAsk actual = new BidAsk(bid.get(i), ask.get(i), Par.convertirPar(currency.get(i)));
			precio.add(actual);
		}
		dailyOCR.preciosActuales = precio;
		return nuevasSenales;
	}

	protected void procesar(ArrayList <Senal> senalesLeidas)
	{
		try
		{
			for(Senal senal : senalesLeidas)
			{
				Estrategia actual = darEstrategia(senal.estrategia);
				Senal afectada = null;
				if((afectada = actual.tienePar(senal.par)) != null)
				{
					if(senal.compra != afectada.compra)
					{
						actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, afectada.numeroLotes, 0), afectada, false);
						actual.agregar(new SenalEntrada(senal.par, TipoSenal.TRADE, senal.compra, senal.numeroLotes, senal.precioEntrada), afectada, false);
					}
					if(afectada.numeroLotes > senal.numeroLotes)
					{
						actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, afectada.numeroLotes - senal.numeroLotes, 0), afectada, false);
					}
					else if(afectada.numeroLotes < senal.numeroLotes)
					{
			    		Error.agregar("La señal entrante tiene mas lotes que la que ya existio");

					}
				}
				else
				{
					actual.agregar(new SenalEntrada(senal.par, TipoSenal.TRADE, senal.compra, senal.numeroLotes, senal.precioEntrada), afectada, false);
				}
			}
			for(Estrategia actual : estrategias)
			{
				Thread.sleep(1000);
				synchronized(actual.senales)
				{
					for(int i = 0; i < actual.senales.size(); i++)
					{
						Senal senal = null;
						try
						{
							senal = actual.senales.get(i);
						}
						catch(Exception e)
						{
							break;
						}
						boolean encontrada = false;
						for(Senal nueva : senalesLeidas)
						{
							if(actual.id.equals(nueva.estrategia) && senal.par.equals(nueva.par))
							{
								encontrada = true;
								break;
							}
						}
						if(!encontrada)
						{
							if(!senal.manual)
								actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, senal.numeroLotes, 0), senal, false);
							if(senal.manual && senal.numeroLotes == 0)
								actual.senales.remove(senal);
							i = -1;
						}
					}
				}
			}
		}
		catch(Exception e)
		{
    		Error.agregar(e.getMessage() + "Error al procesar señales probablemente en thread.sleep");
		}
	}

	public void persistir() 
	{
		breakout1.escribir(b1);
		breakout2.escribir(b2);
		range1.escribir(r1);
		range2.escribir(r2);
		momentum1.escribir(m1);
		momentum2.escribir(m2);
		technical.escribir(t);
	}

	public Estrategia darEstrategia(IdEstrategia id)
	{
		switch(id)
		{
			case BREAKOUT1 : return breakout1;
			case BREAKOUT2 : return breakout2;
			case RANGE1 : return range1;
			case RANGE2 : return range2;
			case MOMENTUM1 : return momentum1;
			case MOMENTUM2 : return momentum2;
			default : return null;
		}
	}
}

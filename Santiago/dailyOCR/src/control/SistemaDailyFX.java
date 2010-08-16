package control;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.BidAsk;
import modelo.Estrategia;
import modelo.Senal;
import modelo.SenalEntrada;
import control.conexion.ConexionServidor;

public class SistemaDailyFX extends SistemaEstrategias
{
	Escritor escritorBreakout2;
	Escritor escritorOtros;
	Estrategia breakout1;
	Estrategia breakout2;
	Estrategia range1;
	Estrategia range2;
	Estrategia momentum1;
	Estrategia momentum2;
	
	ArrayList <Estrategia> estrategias;
	
	public void cargarEstrategias()
	{
		escritorBreakout2 = new Escritor("dailyFX/experts/files/", SistemaDailyFX.class);
		escritorOtros = new Escritor("dailyOtros/experts/files/", SistemaDailyFX.class);
		breakout1 = Estrategia.leer(IdEstrategia.BREAKOUT1);
		if(breakout1 == null)
		{
			breakout1 = new Estrategia(IdEstrategia.BREAKOUT1);
		}
		breakout1.escritor = escritorOtros;
		breakout2 = Estrategia.leer(IdEstrategia.BREAKOUT2);
		if(breakout2 == null)
		{
			breakout2 = new Estrategia(IdEstrategia.BREAKOUT2);
		}
		breakout2.escritor = escritorBreakout2;
		range1 = Estrategia.leer(IdEstrategia.RANGE1);
		if(range1 == null)
		{
			range1 = new Estrategia(IdEstrategia.RANGE1);
		}
		range1.escritor = escritorOtros;
		range2 = Estrategia.leer(IdEstrategia.RANGE2);
		if(range2 == null)
		{
			range2 = new Estrategia(IdEstrategia.RANGE2);
		}
		range2.escritor = escritorOtros;
		momentum1 = Estrategia.leer(IdEstrategia.MOMENTUM1);
		if(momentum1 == null)
		{
			momentum1 = new Estrategia(IdEstrategia.MOMENTUM1);
		}
		momentum1.escritor = escritorOtros;
		momentum2 = Estrategia.leer(IdEstrategia.MOMENTUM2);
		if(momentum2 == null)
		{
			momentum2 = new Estrategia(IdEstrategia.MOMENTUM2);
		}
		momentum2.escritor = escritorOtros;
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
		persistir();
	}

	public void verificarConsistencia()
	{
		if(breakout1 == null || breakout2 == null || range1 == null || range2 == null || momentum1 == null || momentum2 == null ||
		   breakout1.verificarConsistencia() || breakout2.verificarConsistencia() || range1.verificarConsistencia() ||
		   range2.verificarConsistencia() || momentum1.verificarConsistencia() || momentum2.verificarConsistencia())
		{
			cargarEstrategias();
		}
	}
	
	public void chequearSenales(boolean enviarMensaje) 
	{
		try
		{
			ConexionServidor.cargarSSI();
			ArrayList <Senal> senalesBreakout2 = new ArrayList <Senal> (breakout2.getSenales());
			ArrayList <Senal> senalesOtros = new ArrayList <Senal> (breakout1.getSenales());
			senalesOtros.addAll(range1.getSenales());
			senalesOtros.addAll(range2.getSenales());
			senalesOtros.addAll(momentum1.getSenales());
			senalesOtros.addAll(momentum2.getSenales());
			String mensaje = this.getClass().getCanonicalName() + " OK";
			
			class ParMagico
			{
				Par par;
				int magico;
				IdEstrategia id;
				double precioEntrada;
				boolean esCompra;
				
				public ParMagico(Par p, int m)
				{
					par = p;
					magico = m;
				}
				
				public ParMagico(Par p, int m, IdEstrategia i, double pE, boolean eC)
				{
					par = p;
					magico = m;
					id = i;
					precioEntrada = pE;
					esCompra = eC;
				}
	
				public boolean equals(Object obj) 
				{
					ParMagico otro = (ParMagico) obj;
					return par.equals(otro.par) && magico == otro.magico;
				}
			}
			
			ArrayList <ParMagico> parMagicosBreakout2 = new ArrayList <ParMagico> (14);
			ArrayList <ParMagico> parMagicosOtros = new ArrayList <ParMagico> (70);
			ArrayList <ParMagico> parMagicosBreakout2NoAbiertos = new ArrayList <ParMagico> (14);
			ArrayList <ParMagico> parMagicosOtrosNoAbiertos = new ArrayList <ParMagico> (70);
			for(Senal s : senalesBreakout2)
			{
				if(s.getMagico()[0] != 0)
					parMagicosBreakout2.add(new ParMagico(s.getPar(), s.getMagico()[0], s.getEstrategia(), s.getPrecioEntrada(), s.isCompra()));
				else
					parMagicosBreakout2NoAbiertos.add(new ParMagico(s.getPar(), s.getMagico()[0], s.getEstrategia(), s.getPrecioEntrada(), s.isCompra()));
			}
			for(Senal s : senalesOtros)
			{
				if(s.getMagico()[0] != 0)
					parMagicosOtros.add(new ParMagico(s.getPar(), s.getMagico()[0], s.getEstrategia(), s.getPrecioEntrada(), s.isCompra()));
				else
					parMagicosOtrosNoAbiertos.add(new ParMagico(s.getPar(), s.getMagico()[0], s.getEstrategia(), s.getPrecioEntrada(), s.isCompra()));
			}
			ArrayList <ParMagico> parMagicosRealesBreakout2 = new ArrayList <ParMagico> (14);
			ArrayList <ParMagico> parMagicosRealesOtros = new ArrayList <ParMagico> (14);
			for(String s : breakout2.escritor.chequearSenales())
			{
				Scanner sc = new Scanner(s);
				sc.useDelimiter("\\Q;\\E");
				Par par = Par.convertirPar(sc.next());
				int magico = sc.nextInt();
				sc.close();
				parMagicosRealesBreakout2.add(new ParMagico(par, magico));
			}
			for(String s : breakout1.escritor.chequearSenales())
			{
				Scanner sc = new Scanner(s);
				sc.useDelimiter("\\Q;\\E");
				Par par = Par.convertirPar(sc.next());
				int magico = sc.nextInt();
				sc.close();
				parMagicosRealesOtros.add(new ParMagico(par, magico));
			}
			ArrayList <ParMagico> parMagicosBreakout2Copia = new ArrayList <ParMagico> (parMagicosBreakout2);
			ArrayList <ParMagico> parMagicosOtrosCopia = new ArrayList <ParMagico> (parMagicosOtros);
			for(ParMagico pm : parMagicosRealesBreakout2)
			{
				parMagicosBreakout2Copia.remove(pm);
			}
			for(ParMagico pm : parMagicosRealesOtros)
			{
				parMagicosOtrosCopia.remove(pm);
			}
			for(ParMagico pm : parMagicosBreakout2)
			{
				if(parMagicosRealesBreakout2.remove(pm))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + "Breakout2 " + pm.par + " " + pm.magico + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " OK";
				}
			}
			for(ParMagico pm : parMagicosOtros)
			{
				if(parMagicosRealesOtros.remove(pm))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + pm.id + " " + pm.par + " " + pm.magico + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " OK";
				}
			}
			for(ParMagico pm : parMagicosBreakout2Copia)
			{
				if(darEstrategia(pm.id).darActivo(pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + "Breakout2 " + pm.par + " " + pm.magico + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " CERRADO_PREMATURAMENTE";
				}
			}
			for(ParMagico pm : parMagicosOtrosCopia)
			{
				if(darEstrategia(pm.id).darActivo(pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + pm.id + " " + pm.par + " " + pm.magico + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " CERRADO_PREMATURAMENTE";
				}
			}
			for(ParMagico pm : parMagicosBreakout2NoAbiertos)
			{
				if(darEstrategia(pm.id).darActivo(pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + "Breakout2 " + pm.par + " " + pm.magico + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " NO_ABIERTO";
				}
			}
			for(ParMagico pm : parMagicosOtrosNoAbiertos)
			{
				if(darEstrategia(pm.id).darActivo(pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + pm.id + " " + pm.par + " " + pm.magico + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " NO_ABIERTO";
				}
			}
			String mensaje2 = "";
			breakout2.escritor.lineas = new ArrayList <String> ();
			for(ParMagico pm : parMagicosRealesBreakout2)
			{
				breakout2.escritor.lineas.add(pm.par + ";SELL;CLOSE;" + pm.magico);
				mensaje2 += "\n" + "Breakout2 " + pm.par + " " + pm.magico + " no existe en la bd, eliminado";
			}
			breakout2.escritor.escribir();
			for(ParMagico pm : parMagicosRealesOtros)
			{
				breakout1.escritor.lineas.add(pm.par + ";SELL;CLOSE;" + pm.magico);
				mensaje2 += "\n" + "Otros " + pm.par + " " + pm.magico + " no existe en la bd, eliminado";
			}
			breakout1.escritor.escribir();
			mensaje += mensaje2;
			if(!mensaje2.equals(""))
				Error.agregar(mensaje2);
			if(enviarMensaje)
				Error.agregar(mensaje);
		}
		catch(Exception e)
		{
			Error.agregar("Error en el metodo " + e.getMessage());
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
						System.gc();
						verificarConsistencia();
						Thread.sleep(1000);
						iniciarProcesamiento();
					    escritorBreakout2.escribir();
						escritorBreakout2.leerMagicos();
					    escritorOtros.escribir();
						escritorOtros.leerMagicos();
						verificarConsistencia();
						persistir();
					}
					catch(Exception e)
					{	
						try
						{
							System.gc();
							numeroErrores++;
				    		Error.agregar(e.getMessage() + " Error en el ciclo dailyFX");
				    		Thread.sleep(600000);
							if(numeroErrores == 30)
							{
								Error.agregar(e.getMessage() + " Error de lectura, intentando reiniciar.");
								Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
								{
									@Override
									public void run() 
									{
										try 
										{
											Runtime.getRuntime().exec("java -jar dailyOCR.jar -Xmx1024m -Xms512m");
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
				    		Error.agregar(e.getMessage() + " Error en el ciclo de error DailyFX");
						}
					}
				}
			}
		}).start();
	}

	protected ArrayList <Senal> leer(String [] entradas)
	{
		try
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
			ArrayList <BidAsk> precio = new ArrayList <BidAsk> ();
			for(int i=0; i < bid.size(); i++)
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
		catch(Exception e)
		{
			Error.agregar("Error leyendo las senales de DailyFX " + e.getMessage());
			throw(new RuntimeException("Error de lectura"));
		}
	}

	protected void procesar(ArrayList <Senal> senalesLeidas)
	{
		try
		{
			for(Senal senal : senalesLeidas)
			{
				Estrategia actual = darEstrategia(senal.getEstrategia());
				Senal afectada = null;
				if((afectada = actual.tienePar(senal.getPar())) != null)
				{
					if(senal.isCompra() != afectada.isCompra())
					{
						actual.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, afectada.getNumeroLotes(), 0), afectada, false);
						actual.agregar(new SenalEntrada(senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), afectada, false);
					}
					if(afectada.getNumeroLotes() > senal.getNumeroLotes())
					{
						actual.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, afectada.getNumeroLotes() - senal.getNumeroLotes(), 0), afectada, false);
					}
				}
				else
				{
					actual.agregar(new SenalEntrada(senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), afectada, false);
				}
			}
			for(Estrategia actual : estrategias)
			{
				Thread.sleep(1000);
				synchronized(actual.getSenales())
				{
					for(int i = 0; i < actual.getSenales().size(); i++)
					{
						Senal senal = null;
						try
						{
							senal = actual.getSenales().get(i);
						}
						catch(Exception e)
						{
							break;
						}
						boolean encontrada = false;
						for(Senal nueva : senalesLeidas)
						{
							if(actual.getId().equals(nueva.getEstrategia()) && senal.getPar().equals(nueva.getPar()))
							{
								encontrada = true;
								break;
							}
						}
						if(!encontrada)
						{
							if(!senal.isManual())
								actual.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, senal.getNumeroLotes(), 0), senal, false);
							if(senal.isManual() && senal.getNumeroLotes() == 0)
							{
								actual.getSenales().remove(senal);
								i = -1;
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
    		Error.agregar(e.getMessage() + ": Error al procesar senales de dailyFX.");
		}
	}

	public void persistir() 
	{
		breakout1.escribir();
		breakout2.escribir();
		range1.escribir();
		range2.escribir();
		momentum1.escribir();
		momentum2.escribir();
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
	
	public static Collection <String> metodoMeta(SenalEntrada entrada, Senal afectada)
	{
		ArrayList <String> lineas = new ArrayList <String> ();
		if(entrada.getTipo().equals(TipoSenal.HIT) && afectada.getNumeroLotes() == 0)
			lineas.add(entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";" + "CLOSE;" + afectada.getMagico()[0]);
		else if(entrada.getTipo().equals(TipoSenal.TRADE))
			lineas.add(entrada.getPar() + ";" + (entrada.isCompra() ? "BUY" : "SELL") + ";" + "OPEN;" + (afectada.getEstrategia().equals(IdEstrategia.BREAKOUT2) ? "1" : "0"));
		return lineas;
	}
}

package modelo.dailyFx;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.BidAsk;
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
import control.conexion.dailyFx.ConexionServidorDailyFx;

public class SistemaDailyFX extends SistemaEstrategias
{
	Escritor escritorBreakout2;
	Escritor escritorOtros;
	Escritor escritorElite;
	Estrategia breakout1;
	Estrategia breakout2;
	Estrategia range1;
	Estrategia range2;
	Estrategia momentum1;
	Estrategia momentum2;
	EstrategiaElite elite;
	
	ArrayList <Estrategia> estrategias;
	
	public void cargarEstrategias()
	{
		escritorBreakout2 = new Escritor("dailyFX/");
		escritorOtros = new Escritor("dailyOtros/");
		escritorElite = new Escritor("dailyElite/");
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
		elite = EstrategiaElite.leer(IdEstrategia.ELITE);
		if(elite == null)
		{
			elite = new EstrategiaElite(IdEstrategia.ELITE);
		}
		elite.escritor = escritorElite;
		estrategias = new ArrayList <Estrategia> ();
		estrategias.add(breakout1);
		estrategias.add(breakout2);
		estrategias.add(momentum1);
		estrategias.add(momentum2);
		estrategias.add(range1);
		estrategias.add(range2);
		try
		{
			metodoLectura = ConexionServidorDailyFx.class.getMethod("leerServidorDailyFX");
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
		   elite == null || breakout1.verificarConsistencia() || breakout2.verificarConsistencia() || range1.verificarConsistencia() ||
		   range2.verificarConsistencia() || momentum1.verificarConsistencia() || momentum2.verificarConsistencia() || elite.verificarConsistencia())
		{
			cargarEstrategias();
		}
	}
	
	public void chequearSenales(boolean enviarMensaje) 
	{
		try
		{
			ArrayList <Senal> senalesBreakout2 = new ArrayList <Senal> (breakout2.getSenalesCopy());
			ArrayList <Senal> senalesOtros = new ArrayList <Senal> (breakout1.getSenalesCopy());
			ArrayList <Senal> senalesElite = new ArrayList <Senal> (elite.getSenalesCopy());
			senalesOtros.addAll(range1.getSenalesCopy());
			senalesOtros.addAll(range2.getSenalesCopy());
			senalesOtros.addAll(momentum1.getSenalesCopy());
			senalesOtros.addAll(momentum2.getSenalesCopy());
			for(Senal s : senalesElite)
			{
				Senal encontrada;
				boolean bien = true;
				if((encontrada = darEstrategia(s.getEstrategia()).tienePar(s.getPar())) != null)
				{
					if(encontrada.isCompra() != s.isCompra() || encontrada.getNumeroLotes() != s.getNumeroLotes())
					{
						bien = false;
					}
				}
				else
				{
					bien = false;
				}
				if(!bien)
				{
					elite.escritor.agregarLinea(s.getPar() + ";SELL;CLOSE;" + s.getMagico()[0]);
					Error.agregar("Inconsistencia en Elite: " + s.getEstrategia() + " " + s.getPar() + " " + s.getMagico()[0] + " no existe, eliminando");
					elite.cerrar(s.getPar(), s.getEstrategia());
				}
			}
			String mensaje = this.getClass().getCanonicalName() + " OK";
			
			class ParMagico
			{
				Par par;
				int magico;
				IdEstrategia id;
				double precioEntrada;
				boolean esCompra;
				
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
					return par.equals(otro.par) && magico == otro.magico && esCompra == otro.esCompra;
				}
			}
			
			ArrayList <ParMagico> parMagicosBreakout2 = new ArrayList <ParMagico> (14);
			ArrayList <ParMagico> parMagicosOtros = new ArrayList <ParMagico> (70);
			ArrayList <ParMagico> parMagicosElite = new ArrayList <ParMagico> (84);
			ArrayList <ParMagico> parMagicosBreakout2NoAbiertos = new ArrayList <ParMagico> (14);
			ArrayList <ParMagico> parMagicosOtrosNoAbiertos = new ArrayList <ParMagico> (70);
			ArrayList <ParMagico> parMagicosEliteNoAbiertos = new ArrayList <ParMagico> (70);
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
			for(Senal s : senalesElite)
			{
				if(s.getMagico()[0] != 0)
					parMagicosElite.add(new ParMagico(s.getPar(), s.getMagico()[0], s.getEstrategia(), s.getPrecioEntrada(), s.isCompra()));
				else
					parMagicosEliteNoAbiertos.add(new ParMagico(s.getPar(), s.getMagico()[0], s.getEstrategia(), s.getPrecioEntrada(), s.isCompra()));
			}
			ArrayList <ParMagico> parMagicosRealesBreakout2 = new ArrayList <ParMagico> (14);
			ArrayList <ParMagico> parMagicosRealesOtros = new ArrayList <ParMagico> (70);
			ArrayList <ParMagico> parMagicosRealesElite = new ArrayList <ParMagico> (84);
			for(String s : breakout2.escritor.chequearSenales())
			{
				Scanner sc = new Scanner(s);
				sc.useDelimiter("\\Q;\\E");
				Par par = Par.convertirPar(sc.next());
				int magico = sc.nextInt();
				boolean compra = sc.nextInt() == 1;
				sc.close();
				parMagicosRealesBreakout2.add(new ParMagico(par, magico, null, 0.0d, compra));
			}
			for(String s : breakout1.escritor.chequearSenales())
			{
				Scanner sc = new Scanner(s);
				sc.useDelimiter("\\Q;\\E");
				Par par = Par.convertirPar(sc.next());
				int magico = sc.nextInt();
				boolean compra = sc.nextInt() == 1;
				sc.close();
				parMagicosRealesOtros.add(new ParMagico(par, magico, null, 0.0d, compra));
			}
			for(String s : elite.escritor.chequearSenales())
			{
				Scanner sc = new Scanner(s);
				sc.useDelimiter("\\Q;\\E");
				Par par = Par.convertirPar(sc.next());
				int magico = sc.nextInt();
				boolean compra = sc.nextInt() == 1;
				sc.close();
				parMagicosRealesElite.add(new ParMagico(par, magico, null, 0.0d, compra));
			}
			ArrayList <ParMagico> parMagicosBreakout2Copia = new ArrayList <ParMagico> (parMagicosBreakout2);
			ArrayList <ParMagico> parMagicosOtrosCopia = new ArrayList <ParMagico> (parMagicosOtros);
			ArrayList <ParMagico> parMagicosEliteCopia = new ArrayList <ParMagico> (parMagicosElite);
			for(ParMagico pm : parMagicosRealesBreakout2)
			{
				parMagicosBreakout2Copia.remove(pm);
			}
			for(ParMagico pm : parMagicosRealesOtros)
			{
				parMagicosOtrosCopia.remove(pm);
			}
			for(ParMagico pm : parMagicosRealesElite)
			{
				parMagicosEliteCopia.remove(pm);
			}
			for(ParMagico pm : parMagicosBreakout2)
			{
				if(parMagicosRealesBreakout2.remove(pm))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + "Breakout2 " + pm.par + " " + pm.magico + " " + pm.esCompra + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " OK";
				}
			}
			for(ParMagico pm : parMagicosOtros)
			{
				if(parMagicosRealesOtros.remove(pm))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + pm.id + " " + pm.par + " " + pm.magico + " " + pm.esCompra + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " OK";
				}
			}
			for(ParMagico pm : parMagicosElite)
			{
				if(parMagicosRealesElite.remove(pm))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\nElite " + pm.id + " " + pm.par + " " + pm.magico + " " + pm.esCompra + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " OK";
				}
			}
			for(ParMagico pm : parMagicosBreakout2Copia)
			{
				if(darEstrategia(pm.id).darActivo(pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + "Breakout2 " + pm.par + " " + pm.magico + " " + pm.esCompra + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " CERRADO_PREMATURAMENTE";
				}
			}
			for(ParMagico pm : parMagicosOtrosCopia)
			{
				if(darEstrategia(pm.id).darActivo(pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + pm.id + " " + pm.par + " " + pm.magico + " " + pm.esCompra + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " CERRADO_PREMATURAMENTE";
				}
			}
			for(ParMagico pm : parMagicosEliteCopia)
			{
				if(elite.darActivo(pm.id, pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\nElite " + pm.id + " " + pm.par + " " + pm.magico + " " + pm.esCompra + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " CERRADO_PREMATURAMENTE";
				}
			}
			for(ParMagico pm : parMagicosBreakout2NoAbiertos)
			{
				if(darEstrategia(pm.id).darActivo(pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + "Breakout2 " + pm.par + " " + pm.magico + " " + pm.esCompra + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " NO_ABIERTO";
				}
			}
			for(ParMagico pm : parMagicosOtrosNoAbiertos)
			{
				if(darEstrategia(pm.id).darActivo(pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\n" + pm.id + " " + pm.par + " " + pm.magico + " " + pm.esCompra + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " NO_ABIERTO";
				}
			}
			for(ParMagico pm : parMagicosEliteNoAbiertos)
			{
				if(elite.darActivo(pm.id, pm.par))
				{
					double precioActual = dailyOCR.precioPar(pm.par, pm.esCompra);
					double precioParActual = pm.esCompra ? precioActual - pm.precioEntrada : pm.precioEntrada - precioActual;
					int resultado = pm.precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000);
					mensaje += "\nElite " + pm.id + " " + pm.par + " " + pm.magico + " " + pm.esCompra + " Entrada: " + pm.precioEntrada + " Actual: " + precioActual + " P/L: " + resultado + " NO_ABIERTO";
				}
			}
			String mensaje2 = "";
			breakout2.escritor.limpiarLineas();
			for(ParMagico pm : parMagicosRealesBreakout2)
			{
				Senal s;
				if((s = breakout2.tienePar(pm.par)) != null && breakout2.darActivo(pm.par) && s.getMagico()[0] == 0 && pm.esCompra == s.isCompra())
				{
					s.getMagico()[0] = pm.magico;
					Error.agregar("Asignando magico tentativamente: " + breakout2.getId() + " " + s.getPar() + " " + pm.magico);
				}
				else
				{
					breakout2.escritor.agregarLinea(pm.par + ";SELL;CLOSE;" + pm.magico);
					mensaje2 += "\n" + "Breakout2 " + pm.par + " " + pm.magico + " " + pm.esCompra + " no existe en la bd, eliminado";	
				}
			}
			breakout2.escritor.escribir();
			for(ParMagico pm : parMagicosRealesOtros)
			{
				boolean cambio = false;
				for(Estrategia e : estrategias)
				{
					if(cambio)
						break;
					if(e == breakout2)
						continue;
					for(Senal s : e.getSenalesCopy())
					{
						if(s.getPar().equals(pm.par) && e.darActivo(s.getPar()) && s.getMagico()[0] == 0 && pm.esCompra == s.isCompra())
						{
							cambio = true;
							s.getMagico()[0] = pm.magico;
							Error.agregar("Asignando magico tentativamente: " + e.getId() + " " + s.getPar() + " " + pm.magico);
							break;
						}
					}
				}
				if(!cambio)
				{
					breakout1.escritor.agregarLinea(pm.par + ";SELL;CLOSE;" + pm.magico);
					mensaje2 += "\n" + "Otros " + pm.par + " " + pm.magico + " " + pm.esCompra + " no existe en la bd, eliminado";
				}
			}
			breakout1.escritor.escribir();
			for(ParMagico pm : parMagicosRealesElite)
			{
				elite.escritor.agregarLinea(pm.par + ";SELL;CLOSE;" + pm.magico);
				mensaje2 += "\n" + "Elite " + pm.par + " " + pm.magico + " " + pm.esCompra + " no existe en la bd, eliminado";
			}
			elite.escritor.escribir();
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
			volatile boolean terminoBreakout2 = false;
			volatile boolean terminoOtros = false;
			volatile boolean terminoElite = false;
			
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
						synchronized(este())
						{
						    escritorBreakout2.escribir();
						    escritorOtros.escribir();
							escritorElite.escribir();
							terminoBreakout2 = false;
							terminoOtros = false;
							terminoElite = false;
							new Thread(new Runnable() 
							{
								public void run() 
								{
									escritorBreakout2.leerMagicos();
									terminoBreakout2 = true;
								}
							}).start();
							new Thread(new Runnable() 
							{
								public void run() 
								{
									escritorOtros.leerMagicos();
									terminoOtros = true;
								}
							}).start();
							new Thread(new Runnable() 
							{
								public void run() 
								{
									escritorElite.leerMagicos();
									terminoElite = true;
								}
							}).start();
							while(!terminoBreakout2 || !terminoOtros || !terminoElite)
								Thread.sleep(1000);
							terminoBreakout2 = false;
							terminoOtros = false;
							terminoElite = false;
							verificarConsistencia();
							persistir();
						}
					}
					catch(Exception e)
					{	
						try
						{
							System.gc();
							numeroErrores++;
				    		Error.agregar(e.getMessage() + " Error en el ciclo dailyFX");
				    		Thread.sleep(60000);
							if(numeroErrores == 30)
							{
								Error.agregar(e.getMessage() + " Error de lectura, intentando reiniciar.");
								Runtime.getRuntime().exec("shutdown now -r");
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
		new Thread(new Runnable()
		{
			public void run() 
			{
				while(true)
				{
					boolean diezYMedia = false;
					boolean diezYNueveYMedia = false;
					try
					{
						Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
						int hora = calendar.get(Calendar.HOUR_OF_DAY);
						int minuto = calendar.get(Calendar.MINUTE);
						if((hora == 8 && minuto >= 30) || (hora > 9 && hora < 17))
						{
							diezYNueveYMedia = false;
							if(!diezYMedia)
							{
								diezYMedia = ConexionServidorDailyFx.cargarSSI();
							}
						}
						else
						{
							diezYMedia = false;
							if(!diezYNueveYMedia)
							{
								 diezYNueveYMedia = ConexionServidorDailyFx.cargarSSI();
							}
						}
						ConexionServidorDailyFx.cargarVIX();
						Thread.sleep(600000);
						
					}
					catch(Exception e)
					{
						Error.agregar("Error en el hilo monitor de ConexionServidor");
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
						elite.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, 0, 0), actual.getId());
						elite.agregar(new SenalEntrada(senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), actual.getId());
					}
					if(afectada.getNumeroLotes() > senal.getNumeroLotes())
					{
						actual.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, afectada.getNumeroLotes() - senal.getNumeroLotes(), 0), afectada, false);
						elite.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, senal.getNumeroLotes(), 0), actual.getId());
					}
				}
				else
				{
					actual.agregar(new SenalEntrada(senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), afectada, false);
					elite.agregar(new SenalEntrada(senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), actual.getId());
				}
			}
			for(Estrategia actual : estrategias)
			{
				synchronized(actual.getSenalesSync())
				{
					for(int i = 0; i < actual.getSenalesSync().size(); i++)
					{
						Senal senal = null;
						try
						{
							senal = actual.getSenalesSync().get(i);
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
							{
								actual.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, senal.getNumeroLotes(), 0), senal, false);
								elite.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, senal.getNumeroLotes(), 0), actual.getId());
							}
							if(senal.isManual() && senal.getNumeroLotes() == 0)
							{
								actual.getSenalesSync().remove(senal);
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
		elite.escribir();
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
			case ELITE : return elite;
			default : return null;
		}
	}
}

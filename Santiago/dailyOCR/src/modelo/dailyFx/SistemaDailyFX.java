package modelo.dailyFx;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;

import modelo.EntradaEscritor;
import modelo.Escritor;
import modelo.Estrategia;
import modelo.Par;
import modelo.Senal;
import modelo.SenalEntrada;
import modelo.SistemaEstrategias;
import modelo.TipoSenal;
import control.AdministradorHilos;
import control.Error;
import control.HiloDaily;
import control.IdEstrategia;
import control.RunnableDaily;
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
	private AtomicBoolean cambio = new AtomicBoolean(false);
	 
	ArrayList <Estrategia> estrategias;
	
	@Override
	public void cargarEstrategias()
	{
		breakout1 = Estrategia.leer(IdEstrategia.BREAKOUT1);
		if(breakout1 == null)
		{
			breakout1 = new Estrategia(IdEstrategia.BREAKOUT1);
			escritorOtros = new Escritor("dailyOtros/", new ArrayList < ArrayList <EntradaEscritor> > ());
		}
		else
		{
			if(breakout1.getEntradasEscritor() != null)
				escritorOtros = new Escritor("dailyOtros/", breakout1.getEntradasEscritor());
			else
				escritorOtros = new Escritor("dailyOtros/", new ArrayList < ArrayList <EntradaEscritor> > ());
		}
		breakout1.ponerEscritor(escritorOtros);
		IdEstrategia.BREAKOUT1.registrarEstrategia(breakout1);
		breakout2 = Estrategia.leer(IdEstrategia.BREAKOUT2);
		if(breakout2 == null)
		{
			breakout2 = new Estrategia(IdEstrategia.BREAKOUT2);
			escritorBreakout2 = new Escritor("dailyFX/", new ArrayList < ArrayList <EntradaEscritor> > ());
		}
		else
		{
			if(breakout2.getEntradasEscritor() != null)
				escritorBreakout2 = new Escritor("dailyFX/", breakout2.getEntradasEscritor());
			else
				escritorBreakout2 = new Escritor("dailyFX/", new ArrayList < ArrayList <EntradaEscritor> > ());
		}
		breakout2.ponerEscritor(escritorBreakout2);
		IdEstrategia.BREAKOUT2.registrarEstrategia(breakout2);
		elite = EstrategiaElite.leer(IdEstrategia.ELITE);
		if(elite == null)
		{
			elite = new EstrategiaElite(IdEstrategia.ELITE);
			escritorElite = new EscritorElite("dailyElite/", new ArrayList < ArrayList <EntradaEscritor> > ());
		}
		else
		{
			if(elite.getEntradasEscritor() != null)
				escritorElite = new EscritorElite("dailyElite/", elite.getEntradasEscritor());
			else
				escritorElite = new EscritorElite("dailyElite/", new ArrayList < ArrayList <EntradaEscritor> > ());
		}
		elite.ponerEscritor(escritorElite);
		IdEstrategia.ELITE.registrarEstrategia(elite);
		range1 = Estrategia.leer(IdEstrategia.RANGE1);
		if(range1 == null)
		{
			range1 = new Estrategia(IdEstrategia.RANGE1);
		}
		range1.ponerEscritor(escritorOtros);
		IdEstrategia.RANGE1.registrarEstrategia(range1);
		range2 = Estrategia.leer(IdEstrategia.RANGE2);
		if(range2 == null)
		{
			range2 = new Estrategia(IdEstrategia.RANGE2);
		}
		range2.ponerEscritor(escritorOtros);
		IdEstrategia.RANGE2.registrarEstrategia(range2);
		momentum1 = Estrategia.leer(IdEstrategia.MOMENTUM1);
		if(momentum1 == null)
		{
			momentum1 = new Estrategia(IdEstrategia.MOMENTUM1);
		}
		momentum1.ponerEscritor(escritorOtros);
		IdEstrategia.MOMENTUM1.registrarEstrategia(momentum1);
		momentum2 = Estrategia.leer(IdEstrategia.MOMENTUM2);
		if(momentum2 == null)
		{
			momentum2 = new Estrategia(IdEstrategia.MOMENTUM2);
		}
		momentum2.ponerEscritor(escritorOtros);
		IdEstrategia.MOMENTUM2.registrarEstrategia(momentum2);
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

	@Override
	protected void verificarConsistencia()
	{
		if(breakout1 == null || breakout2 == null || range1 == null || range2 == null || momentum1 == null || momentum2 == null ||
		   elite == null || breakout1.verificarConsistencia() || breakout2.verificarConsistencia() || range1.verificarConsistencia() ||
		   range2.verificarConsistencia() || momentum1.verificarConsistencia() || momentum2.verificarConsistencia() || elite.verificarConsistencia())
		{
			cargarEstrategias();
		}
	}
	
	@Override
	protected void chequearSenales(boolean enviarMensaje) 
	{
		try 
		{ 
			escritorBreakout2.debug = false; 
			escritorOtros.debug = false; 
			escritorElite.debug = false; 
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
				if((encontrada = s.getEstrategia().darEstrategia().tienePar(s.getPar())) != null) 
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
					escritorElite.agregarLinea(s.getPar() + ";SELL;CLOSE;" + s.darMagico(0)); 
					Error.agregar("Inconsistencia en Elite: " + s.getEstrategia() + " " + s.getPar() + " " + s.darMagico(0) + " no existe, eliminando"); 
					elite.cerrar(s.getPar(), s.getEstrategia()); 
				} 
			} 
			String mensaje = this.getClass().getCanonicalName() + " OK\n"; 

			class ParMagico 
			{ 
				Par par; 
				int magico; 
				IdEstrategia id; 
				double precioEntrada; 
				boolean esCompra;
				double trailingStop = -1;
				double stopDaily = -1;

				public ParMagico(Par p, int m, IdEstrategia i, double pE, boolean eC) 
				{ 
					par = p; 
					magico = m; 
					id = i; 
					precioEntrada = pE; 
					esCompra = eC; 
				} 
				
				public ParMagico(Par p, int m, IdEstrategia i, double pE, boolean eC, double stopD, double trailing) 
				{ 
					this(p, m, i, pE, eC);
					stopDaily = stopD;
					trailingStop = trailing;
				} 

				@Override
				public boolean equals(Object obj)  
				{ 
					ParMagico otro = (ParMagico) obj; 
					return par.equals(otro.par) && magico == otro.magico && esCompra == otro.esCompra; 
				} 
				
				@Override
				public String toString() 
				{
					double precioActual = par.darPrecioActual(esCompra);
					double precioParActual = esCompra ? precioActual - precioEntrada : precioEntrada - precioActual; 
					int resultado = precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000); 
					String salida = id + " " + par + " " + magico + " " + esCompra + " Entrada: " + precioEntrada + " Actual: " + precioActual + " P/L: " + resultado;
					if(stopDaily != -1)
						salida += " Trailing stop: " + trailingStop + " Stop daily: " + stopDaily;
					return salida;
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
				if(s.darMagico(0) != 0) 
					parMagicosBreakout2.add(new ParMagico(s.getPar(), s.darMagico(0), s.getEstrategia(), s.getPrecioEntrada(), s.isCompra(), s.darStopDaily(), s.darStop())); 
				else 
					parMagicosBreakout2NoAbiertos.add(new ParMagico(s.getPar(), s.darMagico(0), s.getEstrategia(), s.getPrecioEntrada(), s.isCompra(), s.darStopDaily(), s.darStop())); 
			} 
			for(Senal s : senalesOtros) 
			{ 
				if(s.darMagico(0) != 0) 
					parMagicosOtros.add(new ParMagico(s.getPar(), s.darMagico(0), s.getEstrategia(), s.getPrecioEntrada(), s.isCompra(), s.darStopDaily(), s.darStop())); 
				else 
					parMagicosOtrosNoAbiertos.add(new ParMagico(s.getPar(), s.darMagico(0), s.getEstrategia(), s.getPrecioEntrada(), s.isCompra(), s.darStopDaily(), s.darStop())); 
			} 
			for(Senal s : senalesElite) 
			{ 
				if(s.darMagico(0) != 0) 
					parMagicosElite.add(new ParMagico(s.getPar(), s.darMagico(0), s.getEstrategia(), s.getPrecioEntrada(), s.isCompra(), s.darStopDaily(), s.darStop())); 
				else 
					parMagicosEliteNoAbiertos.add(new ParMagico(s.getPar(), s.darMagico(0), s.getEstrategia(), s.getPrecioEntrada(), s.isCompra(), s.darStopDaily(), s.darStop())); 
			} 
			ArrayList <ParMagico> parMagicosRealesBreakout2 = new ArrayList <ParMagico> (14); 
			ArrayList <ParMagico> parMagicosRealesOtros = new ArrayList <ParMagico> (70); 
			ArrayList <ParMagico> parMagicosRealesElite = new ArrayList <ParMagico> (84); 
			synchronized(escritorBreakout2)
			{
				for(String s : escritorBreakout2.chequearSenales()) 
				{ 
					Scanner sc = new Scanner(s); 
					sc.useDelimiter("\\Q;\\E"); 
					Par par = Par.convertirPar(sc.next()); 
					int magico = sc.nextInt(); 
					boolean compra = sc.nextInt() == 1; 
					sc.close(); 
					parMagicosRealesBreakout2.add(new ParMagico(par, magico, null, 0.0d, compra)); 
				}
			}
			synchronized(escritorOtros)
			{
				for(String s : escritorOtros.chequearSenales()) 
				{ 
					Scanner sc = new Scanner(s); 
					sc.useDelimiter("\\Q;\\E"); 
					Par par = Par.convertirPar(sc.next()); 
					int magico = sc.nextInt(); 
					boolean compra = sc.nextInt() == 1; 
					sc.close(); 
					parMagicosRealesOtros.add(new ParMagico(par, magico, null, 0.0d, compra)); 
				}
			}
			synchronized(escritorElite)
			{
				for(String s : escritorElite.chequearSenales()) 
				{ 
					Scanner sc = new Scanner(s); 
					sc.useDelimiter("\\Q;\\E"); 
					Par par = Par.convertirPar(sc.next()); 
					int magico = sc.nextInt(); 
					boolean compra = sc.nextInt() == 1; 
					sc.close(); 
					parMagicosRealesElite.add(new ParMagico(par, magico, null, 0.0d, compra)); 
				}
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
					mensaje += pm + " OK\n"; 
			} 
			for(ParMagico pm : parMagicosOtros) 
			{ 
				if(parMagicosRealesOtros.remove(pm)) 
					mensaje += pm + " OK\n";
			} 
			for(ParMagico pm : parMagicosElite) 
			{ 
				if(parMagicosRealesElite.remove(pm)) 
					mensaje += pm + " OK\n";
			} 
			for(ParMagico pm : parMagicosBreakout2Copia) 
			{ 
				if(pm.id.darEstrategia().darActivo(pm.par))
					mensaje += pm + " CERRADO_PREMATURAMENTE\n";
			} 
			for(ParMagico pm : parMagicosOtrosCopia) 
			{ 
				if(pm.id.darEstrategia().darActivo(pm.par)) 
					mensaje += pm + " CERRADO_PREMATURAMENTE\n";
			} 
			for(ParMagico pm : parMagicosEliteCopia) 
			{ 
				if(elite.darActivo(pm.id, pm.par)) 
					mensaje += "Elite " + pm + " CERRADO_PREMATURAMENTE\n"; 
			} 
			for(ParMagico pm : parMagicosBreakout2NoAbiertos) 
			{ 
				if(pm.id.darEstrategia().darActivo(pm.par)) 
					mensaje += pm + " NO_ABIERTO\n";
			} 
			for(ParMagico pm : parMagicosOtrosNoAbiertos) 
			{ 
				if(pm.id.darEstrategia().darActivo(pm.par)) 
					mensaje += pm + " NO_ABIERTO\n";
			} 
			for(ParMagico pm : parMagicosEliteNoAbiertos) 
			{ 
				if(elite.darActivo(pm.id, pm.par)) 
					mensaje += "Elite " + pm + " NO_ABIERTO\n";
			} 
			String mensaje2 = ""; 
			for(ParMagico pm : parMagicosRealesBreakout2) 
			{ 
				Senal s; 
				if((s = breakout2.tienePar(pm.par)) != null && breakout2.darActivo(pm.par) && (s.darMagico(0) == 0 || s.darMagico(0) == pm.magico) && pm.esCompra == s.isCompra()) 
				{ 
					s.ponerMagico(0, pm.magico); 
					Error.agregar("Asignando magico tentativamente: " + breakout2.getId() + " " + s.getPar() + " " + pm.magico); 
				} 
				else 
				{ 
					escritorBreakout2.agregarLinea(pm.par + ";SELL;CLOSE;" + pm.magico); 
					mensaje2 += pm + " no existe en la bd, eliminado\n";       
				} 
			} 
			escritorBreakout2.terminarCiclo(); 
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
						if(s.getPar().equals(pm.par) && e.darActivo(s.getPar()) && (s.darMagico(0) == 0 || s.darMagico(0) == pm.magico) && pm.esCompra == s.isCompra()) 
						{ 
							cambio = true; 
							s.ponerMagico(0, pm.magico); 
							Error.agregar("Asignando magico tentativamente: " + e.getId() + " " + s.getPar() + " " + pm.magico); 
							break; 
						} 
					} 
				} 
				if(!cambio) 
				{ 
					escritorOtros.agregarLinea(pm.par + ";SELL;CLOSE;" + pm.magico); 
					mensaje2 += pm + " no existe en la bd, eliminado\n"; 
				} 
			} 
			escritorOtros.terminarCiclo(); 
			for(ParMagico pm : parMagicosRealesElite) 
			{ 
				mensaje2 += pm + " no existe en la bd\n"; 
			} 
			escritorElite.terminarCiclo();
			for(Par p : Par.values())
			{
				mensaje += p.debugSenales();
			}
			mensaje += mensaje2; 
			if(!mensaje2.equals("")) 
				Error.agregar(mensaje2); 
			if(enviarMensaje) 
				Error.agregar(mensaje); 
			escritorBreakout2.debug = true; 
			escritorOtros.debug = true; 
			escritorElite.debug = true; 
		} 
		catch(Exception e) 
		{ 
			Error.agregar("Error en el metodo " + e.getMessage()); 
		} 
	}
	
	@Override
	public void iniciarHilo() 
	{
		HiloDaily hiloPrincipal = new HiloDaily(new RunnableDaily()
		{
			public void run() 
			{
				try
				{
					Thread.sleep(120000);
					Error.agregar("Iniciando hilo " + este().getClass().getCanonicalName());
				}
				catch(InterruptedException e)
				{
					Error.agregar("Error de interrupcion en " + getClass().getCanonicalName());
				}
				int numeroErrores = 0;
				while(true)
				{
					try
					{
						System.gc();
						verificarConsistencia();
						Thread.sleep(1000);
						synchronized(este())
						{
							cambio.set(false);
							iniciarProcesamiento();
							escritorBreakout2.terminarCiclo();
							escritorOtros.terminarCiclo();
							escritorElite.terminarCiclo();
							verificarConsistencia();
							if(cambio.get())
								synchronized(cambio)
								{
									cambio.notifyAll();
								}
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
					ultimaActualizacion = System.currentTimeMillis();
				}
			}
		}, 600000L);
		hiloPrincipal.setName("Principal " + getClass().getCanonicalName());
		AdministradorHilos.agregarHilo(hiloPrincipal);
		HiloDaily hiloPersistencia = new HiloDaily(new RunnableDaily()
		{
			public void run() 
			{
				while(true)
				{
					synchronized(cambio)
					{
						long tiempoAnterior = System.currentTimeMillis();
						cambio.set(false);
						while(!cambio.get() && ((System.currentTimeMillis() - tiempoAnterior) < 30000))
						{
							try 
							{	
								cambio.wait(60000);
							} 
							catch (InterruptedException e) 
							{
								Error.agregar("Excepcion de interrupcion esperando para grabar en: " + este().getClass().getCanonicalName());
							}
						}
						if((System.currentTimeMillis() - tiempoAnterior) > 300000)
							Error.agregar("Error, ultima persistencia fue hace mas de 5 minutos");
					}
					persistir();
					ultimaActualizacion = System.currentTimeMillis();
				}
			}
			
		}, 600000L);
		hiloPersistencia.setName("Presistencia " + getClass().getCanonicalName());
		AdministradorHilos.agregarHilo(hiloPersistencia);
		HiloDaily hiloSSIVix = new HiloDaily(new RunnableDaily()
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
					ultimaActualizacion = System.currentTimeMillis();
				}
			}
		}, 1200000);
		hiloSSIVix.setName("Monitor VIX-SSI");
		AdministradorHilos.agregarHilo(hiloSSIVix);
		HiloDaily hiloPares = new HiloDaily(new RunnableDaily()
		{
			public void run() 
			{
				try
				{
					Thread.sleep(180000);
				}
				catch(InterruptedException e)
				{
					System.out.println("Error de interrupcion en hilo pares");
				}
				while(true)
				{
					try
					{
						Thread.sleep(30000);
						for(Par p : Par.values())
							p.procesarSenales();
					}
					catch(Exception e)
					{
						Error.agregar("Error en el monitor de pares " + e.getMessage());
					}
					ultimaActualizacion = System.currentTimeMillis();
				}
				
			}
		}, 360000L);
		hiloPares.setName("Monitor pares");
		AdministradorHilos.agregarHilo(hiloPares);
	}

	@Override
	protected ArrayList <Senal> leer(String [] entradas)
	{
		try
		{
			return dailyJSON.leer(entradas[0]);
		}
		catch(Exception e)
		{
			Error.agregar("Error leyendo las senales de DailyFX " + e.getMessage());
			throw(new RuntimeException("Error de lectura"));
		}
	}

	@Override
	protected void procesar(ArrayList <Senal> senalesLeidas)
	{
		try
		{
			for(Senal senal : senalesLeidas)
			{
				Estrategia actual = senal.getEstrategia().darEstrategia();
				Senal afectada = null;
				if((afectada = actual.tienePar(senal.getPar())) != null)
				{
					if(senal.isCompra() != afectada.isCompra())
					{
						actual.agregar(new SenalEntrada(actual.getId(), senal.getPar(), TipoSenal.HIT, false, afectada.getNumeroLotes(), 0), afectada);
						actual.agregar(new SenalEntrada(actual.getId(), senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), afectada);
						elite.agregar(new SenalEntrada(elite.getId(), senal.getPar(), TipoSenal.HIT, false, 0, 0), actual.getId());
						elite.agregar(new SenalEntrada(elite.getId(), senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), actual.getId());
						cambio.set(true);
					}
					if(afectada.getNumeroLotes() > senal.getNumeroLotes())
					{
						actual.agregar(new SenalEntrada(actual.getId(), senal.getPar(), TipoSenal.HIT, false, afectada.getNumeroLotes() - senal.getNumeroLotes(), 0), afectada);
						elite.agregar(new SenalEntrada(elite.getId(), senal.getPar(), TipoSenal.HIT, false, senal.getNumeroLotes(), 0), actual.getId());
						cambio.set(true);
						if(afectada.isCompra())
						{
							if(afectada.darStop() < afectada.getPrecioEntrada() && afectada.darStop() != afectada.getPrecioEntrada())
								afectada.ponerStop(0d);
						}
						else
						{
							if(afectada.darStop() > afectada.getPrecioEntrada() && afectada.darStop() != afectada.getPrecioEntrada())
								afectada.ponerStop(0d);
						}
					}
					else
					{
						int ganancia = afectada.getPar().diferenciaPips(afectada.getPrecioEntrada(), afectada.isCompra());
						if(Math.abs(afectada.darStop()) < 10e-4d)
							afectada.ponerStop(senal.darStop());
						else
						{
							int diferenciaPips = afectada.getPar().diferenciaPips(afectada.darStop(), afectada.isCompra());
							if(ganancia > 150 && diferenciaPips > 150)
							{
								afectada.ponerStop(afectada.getPar().darPrecioMenos(150, afectada.isCompra()));
								if(afectada.numeroTrailing++ % 50 == 0)
									Error.agregar("Actualizando trailing stop, actual: " + afectada.getPar().darPrecioActual(afectada.isCompra()) + ", stop daily: " + senal.darStop() + ", trailing stop: " + afectada.darStop());
							}
							if(ganancia > 100)
							{
								double mejorStop = afectada.isCompra() ? Math.max(afectada.darStop(), afectada.getPrecioEntrada()) : Math.min(afectada.darStop(), afectada.getPrecioEntrada());
								if(mejorStop != afectada.darStop())
								{
									afectada.ponerStop(mejorStop);
									if(afectada.numeroTrailing++ % 50 == 0)
										Error.agregar("Actualizando trailing stop a los 100 pips, actual: " + afectada.getPar().darPrecioActual(afectada.isCompra()) + ", stop daily: " + senal.darStop() + ", trailing stop: " + afectada.darStop());	
								}
							}
						}
						if(afectada.isCompra())
						{
							if(afectada.darStop() < senal.darStop())
								afectada.ponerStop(senal.darStop());
							afectada.ponerStopDaily(senal.darStop());
							if(afectada.getPar().darPrecioActual(true) <= afectada.darStop() && (afectada.getNumeroLotes() < 4 || afectada.darStop() > senal.darStop()))
							{
								actual.tocoStop(afectada);
								elite.tocoStop(afectada);
								cambio.set(true);
								if(!afectada.isTocoStop())
									Error.agregar(afectada.toString() + " toco stop: precio actual -> " + afectada.getPar().darPrecioActual(true) + ", stop -> " + afectada.darStop());
							}
						}
						else
						{
							if(afectada.darStop() > senal.darStop())
								afectada.ponerStop(senal.darStop());
							afectada.ponerStopDaily(senal.darStop());
							if(afectada.getPar().darPrecioActual(false) >= afectada.darStop() && (afectada.getNumeroLotes() < 4 || afectada.darStop() < senal.darStop()))
							{
								actual.tocoStop(afectada);
								elite.tocoStop(afectada);
								cambio.set(true);
								if(!afectada.isTocoStop())
									Error.agregar(afectada.toString() + " toco stop: precio actual -> " + afectada.getPar().darPrecioActual(false) + ", stop -> " + afectada.darStop());

							}
						}
					}
				}
				else
				{
					actual.agregar(new SenalEntrada(actual.getId(), senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), afectada);
					elite.agregar(new SenalEntrada(elite.getId(), senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), actual.getId());
					cambio.set(true);
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
							actual.agregar(new SenalEntrada(actual.getId(), senal.getPar(), TipoSenal.HIT, false, senal.getNumeroLotes(), 0), senal);
							elite.agregar(new SenalEntrada(elite.getId(), senal.getPar(), TipoSenal.HIT, false, senal.getNumeroLotes(), 0), actual.getId());
							cambio.set(true);
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

	@Override
	public void persistir() 
	{
		synchronized(este())
		{
			breakout1.setEntradasEscritor(escritorOtros.darCopiaEntradas());
			breakout1.escribir();
			breakout2.setEntradasEscritor(escritorBreakout2.darCopiaEntradas());
			breakout2.escribir();
			range1.escribir();
			range2.escribir();
			momentum1.escribir();
			momentum2.escribir();
			elite.setEntradasEscritor(escritorElite.darCopiaEntradas());
			elite.escribir();
		}
	}
}

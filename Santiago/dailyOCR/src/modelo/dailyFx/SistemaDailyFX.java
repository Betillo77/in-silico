package modelo.dailyFx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import modelo.Estrategia;
import modelo.SenalEstrategia;
import modelo.SistemaEstrategias;
import modelo.Estrategia.IdEstrategia;
import control.AdministradorHilos;
import control.Error;
import control.HiloDaily;
import control.RunnableDaily;
import control.conexion.dailyFx.ConexionServidorDailyFx;

public class SistemaDailyFX extends SistemaEstrategias
{
	private AtomicBoolean cambio = new AtomicBoolean(false);
	 
	public SistemaDailyFX()
	{
		estrategias = new IdEstrategia[] {IdEstrategia.BREAKOUT1, IdEstrategia.BREAKOUT2, IdEstrategia.RANGE1, IdEstrategia.RANGE2, IdEstrategia.MOMENTUM1, IdEstrategia.MOMENTUM2};
	}
		
	@Override
	protected void verificarConsistencia()
	{
		for(IdEstrategia e : estrategias)
			if(e.darEstrategia() == null || e.darEstrategia().verificarConsistencia())
				e.iniciarEstrategia();
	}

	@Override
	public void iniciarHilo() 
	{
		final SistemaDailyFX este = this;
		HiloDaily hiloPrincipal = new HiloDaily(new RunnableDaily()
		{
			public void run() 
			{
				try
				{
					Thread.sleep(120000);
					Error.agregarInfo("Iniciando hilo " + este.getClass().getCanonicalName());
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
						synchronized(este)
						{
							cambio.set(false);
							iniciarProcesamiento();
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
								Error.reiniciar();
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
								Error.agregar("Excepcion de interrupcion esperando para grabar en: " + este.getClass().getCanonicalName());
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
	}

	@Override
	protected ArrayList <SenalEstrategia> leer(String [] entradas)
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
	protected void procesar()
	{
		try
		{
			ArrayList <SenalEstrategia> senalesLeidas = leer(ConexionServidorDailyFx.leerServidorDailyFX());
			for(SenalEstrategia senal : senalesLeidas)
			{
				Estrategia actual = senal.getEstrategia().darEstrategia();
				SenalEstrategia afectada = null;
				if((afectada = actual.tienePar(senal.getPar())) != null)
				{
					if(senal.isCompra() != afectada.isCompra())
					{
						actual.agregar(senal.getPar(), true, false, afectada.getNumeroLotes(), 0, afectada);
						actual.agregar(senal.getPar(), false, senal.isCompra(), senal.getNumeroLotes(), afectada.getPrecioEntrada(), afectada);
						cambio.set(true);
					}
					if(afectada.getNumeroLotes() > senal.getNumeroLotes())
					{
						actual.agregar(senal.getPar(), true, false, afectada.getNumeroLotes() - senal.getNumeroLotes(), afectada.getPrecioEntrada(), afectada);
						cambio.set(true);
						if(afectada.isCompra())
						{
							if(afectada.darStop() < afectada.getPrecioEntrada())
								afectada.ponerStop(0d);
						}
						else
						{
							if(afectada.darStop() > afectada.getPrecioEntrada())
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
									Error.agregarInfo("Actualizando trailing stop, actual: " + afectada.getPar().darPrecioActual(afectada.isCompra()) + ", stop daily: " + senal.darStop() + ", trailing stop: " + afectada.darStop());
							}
							if(ganancia > 100)
							{
								double mejorStop = afectada.isCompra() ? Math.max(afectada.darStop(), afectada.getPrecioEntrada()) : Math.min(afectada.darStop(), afectada.getPrecioEntrada());
								if(mejorStop != afectada.darStop())
								{
									afectada.ponerStop(mejorStop);
									if(afectada.numeroTrailing++ % 50 == 0)
										Error.agregarInfo("Actualizando trailing stop a los 100 pips, actual: " + afectada.getPar().darPrecioActual(afectada.isCompra()) + ", stop daily: " + senal.darStop() + ", trailing stop: " + afectada.darStop());	
								}
							}
						}
						if(afectada.isCompra())
						{
							if(afectada.darStop() < senal.darStop())
								afectada.ponerStop(senal.darStop());
							afectada.ponerStopDaily(senal.darStop());
						}
						else
						{
							if(afectada.darStop() > senal.darStop())
								afectada.ponerStop(senal.darStop());
							afectada.ponerStopDaily(senal.darStop());
						}
					} 
				}
				else
				{
					actual.agregar(senal.getPar(), false, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada(), afectada);
					cambio.set(true);
				}
			}
			for(IdEstrategia id : estrategias)
			{
				Estrategia actual = id.darEstrategia();
				actual.chequearStops();
				List <SenalEstrategia> lista = actual.darSenales();
				for(SenalEstrategia senal : lista)
				{
					boolean encontrada = false;
					for(SenalEstrategia nueva : senalesLeidas)
						if(actual.getId().equals(nueva.getEstrategia()) && senal.getPar().equals(nueva.getPar()))
						{
							encontrada = true;
							break;
						}
					if(!encontrada)
					{
						actual.agregar(senal.getPar(), true, false, senal.getNumeroLotes(), 0, senal);
						cambio.set(true);
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
	public synchronized void persistir() 
	{
		verificarConsistencia();
		for(IdEstrategia e : estrategias)
			e.darEstrategia().escribir();
	}
}

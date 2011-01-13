package control;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import modelo.Par;

public class AdministradorHilos 
{
	private static LinkedBlockingQueue <HiloDaily> hilos = new LinkedBlockingQueue <HiloDaily> ();
	
	public static void agregarHilo(HiloDaily hilo)
	{
		if(hilos.size() == 0)
		{
			HiloDaily monitorHilos = new HiloDaily(new RunnableDaily()
			{
				public void run()
				{
					boolean mensajeEnviado = false;
					while(true)
					{
						try 
						{
							HiloDaily.sleep(300000);
							Calendar c = Calendar.getInstance();
							int hora = c.get(Calendar.HOUR_OF_DAY);
							int minuto = c.get(Calendar.MINUTE);
							if(minuto > 40)
							{
								mensajeEnviado = false;
							}
							else
							{
								if(!mensajeEnviado && (hora % 2 == 1))
								{
									String mensaje = "";
									for(HiloDaily h : hilos)
									{
										StackTraceElement[] stack = h.getStackTrace();
										mensaje += h.getName() + " " + h.getState() + " Stack:\n";
										for(StackTraceElement ste : stack)
											mensaje += ste + " * \n";
										mensaje += "Ultima actualizacion hace: " + (System.currentTimeMillis() - h.runnable.darUltimaActualizacion()) + " milisegundos, limite espera: " + h.runnable.darIntervaloActualizacion() + "\n";
										mensaje += "\n";
									}
									for(Par p : Par.values())
										mensaje += p.debugSenales();
									Error.agregarInfo(mensaje);
									mensajeEnviado = true;
								}
							}
							for(HiloDaily h : hilos)
							{
								if(!h.isAlive())
								{
									Error.agregar("Error, hilo termino su ejecucion inesperadamente, reiniciando");
									Error.reiniciar();
								}
								if((System.currentTimeMillis() - h.runnable.darUltimaActualizacion()) > h.runnable.darIntervaloActualizacion())
								{
									Error.agregar("Error, hilo: " + h.getName() + " no se actualizo en mucho tiempo, intervalo aceptable: " + h.runnable.darIntervaloActualizacion() + ", ultima actualizacion hace: " + (System.currentTimeMillis() - h.runnable.darUltimaActualizacion() + " haciendo debug"));
									String mensaje = "";
									long inicio = System.currentTimeMillis();
									Random random = new Random();
									for(int i = 0; i < 100; i++)
									{
										HiloDaily.sleep(random.nextInt(1000));
										StackTraceElement[] stack = h.getStackTrace();
										mensaje += "+ " + (System.currentTimeMillis() - inicio) + " ms :" + (stack.length == 0 ? " null" : " " + stack[0]) + "\n"; 
									}
									for(int i = 0; i < 50; i++)
									{
										HiloDaily.sleep(random.nextInt(5000));
										StackTraceElement[] stack = h.getStackTrace();
										mensaje += "+ " + (System.currentTimeMillis() - inicio) + " ms :" + (stack.length == 0 ? " null" : " " + stack[0]) + "\n"; 
									}
									Error.agregar("Debug :\n" + mensaje);
									Error.reiniciar();
								}
							}
						}
						catch(Exception e) 
						{
							Error.agregar("Error en el monitor de hilos " + e.getMessage());
						}
						ponerUltimaActulizacion(System.currentTimeMillis());
					}
				}
			}, 600000L);
			monitorHilos.setName("Monitor hilos");
			if(hilos.size() == 0)
			{
				hilos.add(monitorHilos);
				monitorHilos.start();
				if(hilos.size() != 1)
					Error.agregar("Error en el monitor de hilos, se agregaron varios monitores");
			}
		}
		hilos.add(hilo);
		hilo.start();
	}
}

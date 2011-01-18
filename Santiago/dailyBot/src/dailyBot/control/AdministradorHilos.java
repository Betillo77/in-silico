package dailyBot.control;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import dailyBot.modelo.Par;


public class AdministradorHilos 
{
	private static LinkedBlockingQueue <HiloDaily> hilos = new LinkedBlockingQueue <HiloDaily> ();
	private static HiloDaily monitorHilos = iniciarMonitorHilos();
	
	private static HiloDaily iniciarMonitorHilos()
	{
		HiloDaily monitor = new HiloDaily(new RunnableDaily()
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
						int minuto = c.get(Calendar.MINUTE);
						if(minuto > 40)
						{
							mensajeEnviado = false;
						}
						else
						{
							if(!mensajeEnviado)
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
								Runtime runtime = Runtime.getRuntime();
								long kb = 1024L;
								mensaje += "\nMemoria usada: " + ((runtime.totalMemory() - runtime.freeMemory()) / kb) + " kb";
								mensaje += "\nMemoria libre: " + (runtime.freeMemory() / kb) + " kb";
								mensaje += "\nMemoria total: " + (runtime.totalMemory() / kb) + " kb";
								mensaje += "\nMemoria limite: " + (runtime.maxMemory() / kb) + " kb";
								Error.agregarInfo(mensaje);
								mensajeEnviado = true;
							}
						}
						for(HiloDaily h : hilos)
						{
							if(h == monitorHilos)
								continue;
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
									mensaje += "+ " + (System.currentTimeMillis() - inicio) + " ms :" + (stack.length == 0 ? " null" : " " + Arrays.toString(stack)) + "\n"; 
								}
								for(int i = 0; i < 50; i++)
								{
									HiloDaily.sleep(random.nextInt(5000));
									StackTraceElement[] stack = h.getStackTrace();
									mensaje += "+ " + (System.currentTimeMillis() - inicio) + " ms :" + (stack.length == 0 ? " null" : " " + Arrays.toString(stack)) + "\n"; 
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
		}, Long.MAX_VALUE);
		monitor.setName("Monitor hilos");
		hilos.add(monitor);
		monitor.start();
		return monitor;
	}
	
	public static void agregarHilo(HiloDaily hilo)
	{
		hilos.add(hilo);
		hilo.start();
	}
}
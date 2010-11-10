package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AdministradorHilos 
{
	private static List <HiloDaily> hilos = Collections.synchronizedList(new ArrayList <HiloDaily> ());
	
	public static synchronized void agregarHilo(HiloDaily hilo)
	{
		synchronized(hilos)
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
								Thread.sleep(300000);
								Calendar c = Calendar.getInstance();
								int hora = c.get(Calendar.HOUR_OF_DAY);
								int minuto = c.get(Calendar.MINUTE);
								synchronized(hilos)
								{
									if(minuto > 40)
									{
										mensajeEnviado = false;
									}
									else
									{
										if(!mensajeEnviado && (hora == 1 || hora == 3 || hora == 5 || hora == 7 || hora == 9 || hora == 11 || hora == 13 || hora == 15 || hora == 17 || hora == 19 || hora == 21 || hora == 23))
										{
											String mensaje = "";
											for(HiloDaily h : hilos)
											{
												StackTraceElement[] stack = h.getStackTrace();
												mensaje += h.getName() + " " + h.getState() + " Stack:\n";
												for(StackTraceElement ste : stack)
													mensaje += ste + " * \n";
												mensaje += "Ultima actualizacion hace: " + (System.currentTimeMillis() - h.runnable.ultimaActualizacion) + " milisegundos, limite espera: " + h.runnable.intervalorActualizacion + "\n";
												mensaje += "\n";
											}
											Error.agregar(mensaje);
											mensajeEnviado = true;
										}
									}
									for(HiloDaily h : hilos)
									{
										if(!h.isAlive())
										{
											Error.agregar("Error, hilo termino su ejecucion inesperadamente, reiniciando");
											try 
											{
												Runtime.getRuntime().exec("shutdown now -r");
												System.exit(0);
											} 
											catch (IOException e) 
											{
												Error.agregar("Error reiniciando equipo " + e.getMessage());
												System.exit(0);
											}
										}
										if((System.currentTimeMillis() - h.runnable.ultimaActualizacion) > h.runnable.intervalorActualizacion)
										{
											Error.agregar("Error, hilo: " + h.getName() + " no se actualizo en mucho tiempo, intervalo aceptable: " + h.runnable.intervalorActualizacion + ", ultima actualizacion hace: " + (System.currentTimeMillis() - h.runnable.ultimaActualizacion));
											try 
											{
												Runtime.getRuntime().exec("shutdown now -r");
												System.exit(0);
											} 
											catch (IOException e) 
											{
												Error.agregar("Error reiniciando equipo " + e.getMessage());
												System.exit(0);
											}
										}
									}
								}
							}
							catch(Exception e) 
							{
								Error.agregar("Error en el monitor de hilos " + e.getMessage());
							}
							ultimaActualizacion = System.currentTimeMillis();
						}
					}
				}, 600000L);
				monitorHilos.setName("Monitor hilos");
				hilos.add(monitorHilos);
				monitorHilos.start();
			}
			hilos.add(hilo);
		}
		hilo.start();
	}
}

package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AdministradorHilos 
{
	private static List <Thread> hilos = Collections.synchronizedList(new ArrayList <Thread> ());
	
	public static synchronized void agregarHilo(Thread hilo)
	{
		synchronized(hilos)
		{
			if(hilos.size() == 0)
			{
				Thread monitorHilos = new Thread(new Runnable()
				{
					public void run()
					{
						boolean mensajeEnviado = false;
						while(true)
						{
							try 
							{
								Thread.sleep(60000);
								Calendar c = Calendar.getInstance();
								int hora = c.get(Calendar.HOUR_OF_DAY);
								int minuto = c.get(Calendar.MINUTE);
								synchronized(hilos)
								{
									if(minuto > 30)
									{
										mensajeEnviado = false;
									}
									else
									{
										if(!mensajeEnviado && (hora == 5 || hora == 11 || hora == 17 || hora == 23))
										{
											String mensaje = "";
											for(Thread h : hilos)
											{
												StackTraceElement[] stack = h.getStackTrace();
												mensaje += h.getName() + " " + h.getState() + " ";
												if(stack.length > 0)
													mensaje += stack[0].toString();
												
											}
											Error.agregar(mensaje);
											mensajeEnviado = true;
										}
									}
									for(Thread h : hilos)
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
									}
								}
							}
							catch(Exception e) 
							{
								Error.agregar("Error en el monitor de hilos " + e.getMessage());
							}
						}
					}
				});
				monitorHilos.setName("Monitor hilos");
				hilos.add(monitorHilos);
				monitorHilos.start();
			}
			hilos.add(hilo);
		}
		hilo.start();
	}
}

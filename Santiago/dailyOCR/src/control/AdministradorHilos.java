package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
						while(true)
						{
							try 
							{
								Thread.sleep(60000);
								synchronized(hilos)
								{
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

	public static synchronized void eliminarHilo(String nombreHilo) 
	{
		synchronized(hilos)
		{
			Iterator <Thread> it = hilos.iterator();
			while(it.hasNext())
			{
				Thread actual = it.next();
				if(actual.getName().equals(nombreHilo))
					it.remove();
			}
		}
	}
}

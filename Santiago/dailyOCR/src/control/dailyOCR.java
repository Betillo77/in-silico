package control;

import java.awt.Dimension;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import modelo.BidAsk;
import modelo.SenalEntrada;


import vista.ParteGrafica;

public class dailyOCR
{
	public static final String pathPrincipal = "";
	public static final String pathSecundario = "\\\\SANTIAGO-PC\\Users\\Public\\dailyOCR\\";
	
	public static long tiempo = 0;
	public static ArrayList <Senal> actuales = new ArrayList <Senal> ();
	public static ArrayList <BidAsk> preciosActuales;
	public static int errores = 0;
	
	static File log = new File(pathPrincipal + "log.txt");
	
	static SistemaEstrategias [] sistemas;
	
	public static void cargarSistemasEstrategias()
	{
		sistemas = new SistemaEstrategias[1];
		SistemaEstrategias sdfx = new SistemaDailyFX();
		sdfx.cargarEstrategias();
		sistemas[0] = sdfx;
//		SistemaEstrategias joel = new SistemaJoel();
//		joel.cargarEstrategias();
//		sistemas[1] = joel;	
//		SistemaEstrategias technical = new SistemaTechnical();
//		technical.cargarEstrategias();
//		sistemas[2] = technical;
	}
	
	public static void inicio()
	{
		int numeroErrores = 0;
		while(true)
		{
			try
			{
				for(SistemaEstrategias se : sistemas)
				{
					se.verificarConsistencia();
				}
				Thread.sleep(10000);
				try
				{
					for(SistemaEstrategias se : sistemas)
					{
						se.iniciarProcesamiento();
						se.escritor.escribir();
						se.escritor.leerMagicos();
						se.verificarConsistencia();
						se.persistir();
					}
				}
				catch(Exception e)
				{	
					numeroErrores++;
					if(numeroErrores == 60)
					{
						Error.agregar(e.getMessage() + " Error de lectura, intentando reiniciar.");
						Runtime.getRuntime().exec("reiniciar.bat");
						Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
						{
							@Override
							public void run() 
							{
								try 
								{
									Runtime.getRuntime().exec("reiniciar.bat");
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
			}
			catch(Exception e)
			{
	    		Error.agregar(e.getMessage() + " Error en el ciclo general");
			}
		}
	}
	
	public static boolean chequearConexion()
	{
		try
		{
			URL url = new URL("http://www.dailyfx.com");
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String s = "";
			String contents = "";
			while((s = bufferedReader.readLine()) != null)
			{
				contents = contents.concat(s);
			}
			inputStream.close();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	} 
	
	public static void arreglarConexion()
	{
		try
		{
			Error.agregar("Error de conexion, intentando arreglar");
			Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "primero.bat");
			Thread.sleep(60000);
			if(!chequearConexion())
			{
				Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "segundo.bat");
				Thread.sleep(60000);
			}
		} 
		catch (Exception e)
		{
		}
	}
	
	public static void apagarEquipo()
	{
		try
		{
			Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "tercero.bat");
			Runtime.getRuntime().exit(0);
		}
		catch(Exception e)
		{
			Error.agregar("Error apagando el equipo");
		}
	}

	public static List <Senal> darSenalesEstrategia(IdEstrategia estrategia) 
	{
		for(SistemaEstrategias se : sistemas)
		{
			Estrategia posible = se.darEstrategia(estrategia);
			if(posible != null)
			{
				return posible.senales;
			}
		}
		Error.agregar("No se encontraron las se�ales de: " + estrategia.toString());
		return null;
		
	}

	public static Estrategia darEstrategiaSenal(Senal senal)
	{
		for(SistemaEstrategias se : sistemas)
		{
			Estrategia posible = se.darEstrategia(senal.estrategia);
			if(posible != null)
			{
				return posible;
			}
		}
		Error.agregar("No se encontraron las se�ales de: " + senal.estrategia.toString());
		return null;
	}
	
	public static synchronized void cerrarSenalManual(Senal senal) 
	{
		synchronized(darEstrategiaSenal(senal).senales)
		{
			Estrategia estrategiaSenal = darEstrategiaSenal(senal);
			estrategiaSenal.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, senal.numeroLotes, 0), senal, !senal.manual && senal.estrategia != IdEstrategia.JOEL);
			estrategiaSenal.escritor.escribir();
			estrategiaSenal.escritor.leerMagicos();
		}
	}
	
	public static synchronized void abrirSenalManual(Senal senal) 
	{
		synchronized(darEstrategiaSenal(senal).senales)
		{
			Estrategia estrategiaSenal = darEstrategiaSenal(senal);
			estrategiaSenal.agregar(new SenalEntrada(senal.par, TipoSenal.TRADE, senal.compra, senal.numeroLotes, senal.precioEntrada), senal, true);
			estrategiaSenal.escritor.escribir();
			estrategiaSenal.escritor.leerMagicos();
		}
	}
	
	public static double precioPar(Par par, boolean compra)
	{
    	int i = 0;
    	while(!(dailyOCR.preciosActuales.get(i).currency.equals(par)))
    	{
    		i++;
    	}
    	if(compra == true)
    	{
    		return dailyOCR.preciosActuales.get(i).bid;
    	}
    	else
    	{
    		return dailyOCR.preciosActuales.get(i).ask;
    	}
	}
	
	public static void main(String [] args)
	{
		ParteGrafica pg = new ParteGrafica();
		JFrame framePrincipal = new JFrame();
		framePrincipal.setMinimumSize(new Dimension(259, 244));
		framePrincipal.setSize(259, 244);
		framePrincipal.add(pg);
		framePrincipal.pack();
		framePrincipal.setVisible(true);
		framePrincipal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		cargarSistemasEstrategias();
		new Thread(new Runnable()
					{

						@Override
						public void run() 
						{
							inicio();
						}
						
					}).start();
	}
}	

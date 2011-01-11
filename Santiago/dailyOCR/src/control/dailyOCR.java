package control;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import control.conexion.dailyFx.ConexionServidorDailyFx;

import modelo.Par;
import modelo.SistemaEstrategias;
import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
import modelo.dailyFx.SistemaDailyFX;
import vista.ParteGrafica;

public class dailyOCR
{
	private static ArrayList <SistemaEstrategias> sistemas;
	private static Class <?> [] clasesSistemas = {
										    SistemaDailyFX.class
											//SistemaJoel.class,
										  //SistemaTechnical.class
	};
	
	private static void cargarSistemasEstrategias()
	{
		sistemas = new ArrayList <SistemaEstrategias> ();
		for(Class <?> clase : clasesSistemas)
		{
			try 
			{
				sistemas.add((SistemaEstrategias) (clase.getConstructor(new Class <?> [0]).newInstance(new Object[0])));
			} 
			catch (Exception e)
			{
				Error.agregar("Error inicializando la clase: " + clase.getCanonicalName());
			}
		}
	}
	
	private static void cargarEstrategias()
	{
		for(IdEstrategia e : IdEstrategia.values())
			e.iniciarEstrategia();
	}
	
	private static void cargarProveedores()
	{
		for(IdProveedor p : IdProveedor.values())
			p.iniciarProveedor();
	}

	private static void iniciarHilos()
	{
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
						int dia = calendar.get(Calendar.DAY_OF_WEEK);
						if((hora == 8 && minuto >= 30) || (hora > 9 && hora < 17))
						{
							diezYNueveYMedia = false;
							if(!diezYMedia)
								diezYMedia = ConexionServidorDailyFx.cargarSSI();
						}
						else
						{
							diezYMedia = false;
							if(!diezYNueveYMedia)
								 diezYNueveYMedia = ConexionServidorDailyFx.cargarSSI();
						}
						ConexionServidorDailyFx.cargarVIX();
						if(dia == Calendar.FRIDAY && hora > 16)
						{
							try
							{
								Runtime.getRuntime().exec("/home/santiago/backup");
								HiloDaily.sleep(60000);
								Calendar actual = Calendar.getInstance();
								Error.agregarInfo("Apagando equipo automaticamente: " + actual.get(Calendar.DAY_OF_MONTH) + "/" + (actual.get(Calendar.MONTH) + 1) + "/" + actual.get(Calendar.YEAR) + " " + actual.get(Calendar.HOUR_OF_DAY) + ":" + actual.get(Calendar.MINUTE) + ":" + actual.get(Calendar.SECOND) + "." + actual.get(Calendar.MILLISECOND));
								HiloDaily.sleep(600000);
								for(Par p : Par.values())
									if(p != Par.TODOS)
										p.cerrarDia();
								Runtime.getRuntime().exec("shutdown now -P");
								System.exit(0);
							}
							catch(Exception e)
							{
								System.exit(0);
							}
						}
						HiloDaily.sleep(600000);
						
					}
					catch(Exception e)
					{
						Error.agregar("Error en el hilo monitor de ConexionServidor");
					}
					ponerUltimaActulizacion(System.currentTimeMillis());
				}
			}
		}, 1200000);
		hiloSSIVix.setName("Monitor VIX-SSI");
		AdministradorHilos.agregarHilo(hiloSSIVix);
		HiloDaily hiloPares = new HiloDaily(new RunnableDaily()
		{
			public void run() 
			{
				HiloDaily.sleep(180000);
				while(true)
				{
					try
					{
						HiloDaily.sleep(30000);
						for(Par p : Par.values())
							p.procesarSenales();
					}
					catch(Exception e)
					{
						Error.agregar("Error en el monitor de pares " + e.getMessage());
					}
					ponerUltimaActulizacion(System.currentTimeMillis());
				}
				
			}
		}, 360000L);
		hiloPares.setName("Monitor pares");
		AdministradorHilos.agregarHilo(hiloPares);
		for(SistemaEstrategias sistema : sistemas)
			sistema.iniciarHilo();
	}
	
	public static void salir()
	{
		if(sistemas != null)
			salir(0);
		else
			System.exit(0);
	}
	
	private static void salir(int n)
	{
		if(n == sistemas.size())
		{
			for(IdProveedor p : IdProveedor.values())
			{
				p.darProveedor().escribir();
				p.darProveedor().cerrar();
			}
			for(IdEstrategia e : IdEstrategia.values())
				e.darEstrategia().escribir();
			System.exit(0);
		}
		else
		{
			sistemas.get(n).lockSistema();
			sistemas.get(n).persistir();
			salir(n + 1);
		}	
	}
	
	public static void main(String [] args) throws IOException
	{
		Calendar c = Calendar.getInstance();
		int dia = c.get(Calendar.DAY_OF_WEEK);
		if(dia == Calendar.SATURDAY || dia == Calendar.SUNDAY)
		{
			boolean termino = false;
			while(!termino)
			{
				int hora = c.get(Calendar.HOUR_OF_DAY);
				if(hora > 16 && dia == Calendar.SUNDAY)
				{
					termino = true;
					break;
				}
				HiloDaily.sleep(600000);
				c = Calendar.getInstance();
				dia = c.get(Calendar.DAY_OF_WEEK);
			}
		}
		cargarSistemasEstrategias();
		ParteGrafica pg = new ParteGrafica();
		JFrame framePrincipal = new JFrame();
		framePrincipal.setMinimumSize(new Dimension(259, 244));
		framePrincipal.setSize(259, 244);
		framePrincipal.add(pg);
		framePrincipal.pack();
		framePrincipal.setVisible(true);
		framePrincipal.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		Calendar actual = Calendar.getInstance();
		Error.agregarInfo("Iniciando operaciones automaticamente: " + actual.get(Calendar.DAY_OF_MONTH) + "/" + (actual.get(Calendar.MONTH) + 1) + "/" + actual.get(Calendar.YEAR) + " " + actual.get(Calendar.HOUR_OF_DAY) + ":" + actual.get(Calendar.MINUTE) + ":" + actual.get(Calendar.SECOND) + "." + actual.get(Calendar.MILLISECOND));
		cargarEstrategias();
		cargarProveedores();
		iniciarHilos();
	}
}
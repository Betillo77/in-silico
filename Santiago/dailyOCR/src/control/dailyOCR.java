package control;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import modelo.BidAsk;
import modelo.Estrategia;
import modelo.Senal;
import modelo.SenalEntrada;
import vista.ParteGrafica;

public class dailyOCR
{
	public static ArrayList <BidAsk> preciosActuales;
	static File log = new File("log.txt");
	static ArrayList <SistemaEstrategias> sistemas;
	static Class <?> [] clasesSistemas = {
											SistemaDailyFX.class,
											SistemaJoel.class//,
										  //SistemaTechnical
	};
	
	public static void cargarSistemasEstrategias()
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
	
	public static void iniciarHilos()
	{
		for(SistemaEstrategias sistema : sistemas)
		{
			sistema.iniciarHilo();
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
		cargarSistemasEstrategias();
		ParteGrafica pg = new ParteGrafica();
		JFrame framePrincipal = new JFrame();
		framePrincipal.setMinimumSize(new Dimension(259, 244));
		framePrincipal.setSize(259, 244);
		framePrincipal.add(pg);
		framePrincipal.pack();
		framePrincipal.setVisible(true);
		framePrincipal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		new Thread(new Runnable()
					{
						@Override
						public void run() 
						{
							iniciarHilos();
						}
						
					}).start();
	}
}	

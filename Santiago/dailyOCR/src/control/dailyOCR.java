package control;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;

import control.conexion.ConexionServidor;

import modelo.BidAsk;
import modelo.Estrategia;
import modelo.Senal;
import modelo.SenalEntrada;
import vista.ParteGrafica;

public class dailyOCR
{
	public static ArrayList <BidAsk> preciosActuales;
	static ArrayList <SistemaEstrategias> sistemas;
	static Class <?> [] clasesSistemas = {
										    SistemaDailyFX.class//,
											//SistemaJoel.class,
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
		ConexionServidor.cargarSSI();
		for(SistemaEstrategias sistema : sistemas)
		{
			sistema.cargarEstrategias();
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
				return posible.getSenales();
			}
		}
		Error.agregar("No se encontraron las senales de: " + estrategia.toString());
		return null;
		
	}

	public static Estrategia darEstrategiaSenal(Senal senal)
	{
		for(SistemaEstrategias se : sistemas)
		{
			Estrategia posible = se.darEstrategia(senal.getEstrategia());
			if(posible != null)
			{
				return posible;
			}
		}
		Error.agregar("No se encontraron las senales de: " + senal.getEstrategia().toString());
		return null;
	}
	
	public static synchronized void cerrarSenalManual(Senal senal) 
	{
		synchronized(darEstrategiaSenal(senal).getSenales())
		{
			Estrategia estrategiaSenal = darEstrategiaSenal(senal);
			estrategiaSenal.agregar(new SenalEntrada(senal.getPar(), TipoSenal.HIT, false, senal.getNumeroLotes(), 0), senal, !senal.isManual() && senal.getEstrategia() != IdEstrategia.JOEL);
			estrategiaSenal.escritor.escribir();
			estrategiaSenal.escritor.leerMagicos();
		}
	}
	
	public static synchronized void abrirSenalManual(Senal senal) 
	{
		synchronized(darEstrategiaSenal(senal).getSenales())
		{
			Estrategia estrategiaSenal = darEstrategiaSenal(senal);
			estrategiaSenal.agregar(new SenalEntrada(senal.getPar(), TipoSenal.TRADE, senal.isCompra(), senal.getNumeroLotes(), senal.getPrecioEntrada()), senal, true);
			estrategiaSenal.escritor.escribir();
			estrategiaSenal.escritor.leerMagicos();
		}
	}
	
	public static double precioPar(Par par, boolean compra)
	{
    	int i = 0;
    	while(!(dailyOCR.preciosActuales.get(i).getCurrency().equals(par)))
    	{
    		i++;
    	}
    	if(compra == true)
    	{
    		return dailyOCR.preciosActuales.get(i).getBid();
    	}
    	else
    	{
    		return dailyOCR.preciosActuales.get(i).getAsk();
    	}
	}
	
	public static void main(String [] args) throws IOException
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
		Calendar actual = Calendar.getInstance();
		Error.agregar("Iniciando operaciones automaticamente: " + actual.get(Calendar.DAY_OF_MONTH) + "/" + (actual.get(Calendar.MONTH) + 1) + "/" + actual.get(Calendar.YEAR) + " " + actual.get(Calendar.HOUR_OF_DAY) + ":" + actual.get(Calendar.MINUTE) + ":" + actual.get(Calendar.SECOND) + "." + actual.get(Calendar.MILLISECOND));
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

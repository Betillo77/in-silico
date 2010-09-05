package control;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import modelo.Estrategia;
import modelo.Senal;
import modelo.SistemaEstrategias;
import modelo.dailyFx.SistemaDailyFX;
import vista.ParteGrafica;

public class dailyOCR
{
	private static ArrayList <SistemaEstrategias> sistemas;
	private static Class <?> [] clasesSistemas = {
										    SistemaDailyFX.class//,
											//SistemaJoel.class,
										  //SistemaTechnical
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
	
	private static void iniciarHilos()
	{
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
				return posible.getSenalesCopy();
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
		Error.agregar("No se encontro la estrategia: " + senal.getEstrategia().toString());
		return null;
	}
	
	public static Estrategia darEstrategia(IdEstrategia id)
	{
		for(SistemaEstrategias se : sistemas)
		{
			Estrategia posible = se.darEstrategia(id);
			if(posible != null)
			{
				return posible;
			}
		}
		Error.agregar("No se encontro la estrategia: " + id.toString());
		return null;
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
			System.exit(0);
		else
		{
			synchronized(sistemas.get(n))
			{
				sistemas.get(n).persistir();
				salir(n + 1);
			}
		}	
	}
	
	public static void main(String [] args) throws IOException
	{
		Calendar c = Calendar.getInstance();
		int dia = c.get(Calendar.DAY_OF_WEEK);
		if(dia == Calendar.SATURDAY)
		{
			Runtime.getRuntime().exec("shutdown now -P");
			System.exit(0);
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
		Error.agregar("Iniciando operaciones automaticamente: " + actual.get(Calendar.DAY_OF_MONTH) + "/" + (actual.get(Calendar.MONTH) + 1) + "/" + actual.get(Calendar.YEAR) + " " + actual.get(Calendar.HOUR_OF_DAY) + ":" + actual.get(Calendar.MINUTE) + ":" + actual.get(Calendar.SECOND) + "." + actual.get(Calendar.MILLISECOND));
		iniciarHilos();
	}
}	

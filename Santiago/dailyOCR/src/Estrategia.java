import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import SenalEntrada.TipoSenal;


public class Estrategia 
{	
	public IdEstrategia id;
	public ArrayList <Senal> senales;
	
	public Estrategia(IdEstrategia id)
	{
		this.id = id;
	}
	public static void agregar(SenalEntrada entrada, Senal afectada) 
	{
		if(entrada.tipo.equals(TipoSenal.HIT))
		{
			hit(entrada, afectada);
		}
		else
		{
			trade(entrada, afectada);
		}
	}
	
	private void hit(SenalEntrada entrada, Senal afectada) 
	{
		Senal opuesta;
		if(senales.contains(new Senal()))) 
		{
			opuesta = buscarOpuesta(senal, senales, true);
		}
		else if(senales.contains(new Senal(senal.estrategia, senal.par, Senal.TRADE, false)))
		{
			opuesta = buscarOpuesta(senal, senales, false);
		}
		else
		{
			opuesta = null;
			Error.agregar("No se pudo cerrar " + darNombrePar(senal) + " no esta en la lista de: " + senal.estrategia);
			return;
		}
		Escritor.agregar(senal, opuesta);
		remover(opuesta, senales);
	}
	
	private static void trade(Senal senal, ArrayList <Senal> senales) 
	{
		if(senales.contains(new Senal(senal.estrategia, senal.par, Senal.TRADE, !senal.compra)))
		{
			Senal opuesta = buscarOpuesta(senal, senales, !senal.compra);
			Escritor.agregar(new Senal(senal.estrategia, senal.par, Senal.HIT, false), opuesta);
			remover(opuesta, senales);
		}
		else
		{
			agregar(senal, senales);
		}
	}

	public static Senal buscarOpuesta(Senal senal, ArrayList <Senal> senales, boolean compra)
	{
		Senal opuesta = new Senal(senal.estrategia, senal.par, Senal.TRADE, compra);
		for(Senal s : senales)
		{
			if(s.equals(opuesta))
			{
				opuesta = s;
			}
		}
		return opuesta;
	}
	
	public static void agregar(Senal nueva, ArrayList <Senal> senales) 
	{
		int numero = 0;
		for(Senal s : senales)
		{
			if(s.equals(nueva))
			{
				numero++;
			}
		}
		if(numero <= 1 && nueva.estrategia != Senal.BREAKOUT)
		{
			senales.add(nueva);
			Escritor.agregar(nueva);
		}
		else if(numero == 0)
		{
			senales.add(nueva);
			Escritor.agregar(nueva);
		}
		else
		{
			Error.agregar("Mas de las senales permitidas, no se puede agregar " + darNombrePar(nueva) + " a: " + nueva.estrategia);
		}
	}
	
	public static void leerSenales(File archivo, ArrayList <Senal> senales) 
	{	
		try
		{
			Scanner sc = new Scanner(archivo);
			while(sc.hasNext())
			{
				int estrategia = sc.nextInt();
				int par = sc.nextInt();
				int tipo = sc.nextInt();
				boolean compra = sc.nextInt() == 1;
				int magico = sc.nextInt();
				senales.add(new Senal(estrategia, par, tipo, compra, magico));
			}
			sc.close();
		}
		catch(Exception e)
		{
			Error.agregar("Error leyendo las iniciales " + archivo.getAbsolutePath());
		}
	}
	
	public static void remover(Senal opuesta, ArrayList <Senal> senales) 
	{
		for(int i = 0; i < senales.size(); i++)
		{
			Senal s = senales.get(i);
			if(s.equals(opuesta) && s.magico == opuesta.magico && s.range2 == opuesta.range2)
			{
				senales.remove(i);
				return;
			}
		}
	}
}

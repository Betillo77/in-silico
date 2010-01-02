import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


public class Estrategia 
{	
	public static Senal generarSenal(String estrategia, String cuerpo)
	{
		int estrategia1 = 0;
		if(estrategia.contains("Breakout"))
		{
			estrategia1 = Senal.BREAKOUT;
		}
		else if(estrategia.contains("Range"))
		{
			estrategia1 = Senal.RANGE;
		}
		else if(estrategia.contains("Momentum"))
		{
			estrategia1 = Senal.MOMENTUM;
		}
		else
		{
			Error.agregar(estrategia + " " + cuerpo);
			estrategia1 = Senal.ERROR;
		}
		int par = 0;
		if(estrategia1 == Senal.ERROR)
		{
			par = Senal.ERROR;
		}
		else if(cuerpo.contains("EURUSD"))
		{
			par = Senal.EURUSD;
		}
		else if(cuerpo.contains("USDJPY") || cuerpo.contains("USLOPY") || cuerpo.contains("USCOPY") || cuerpo.contains("USIDJPY") || cuerpo.contains("USCUPY") || cuerpo.contains("USID3PY") || cuerpo.contains("USD3PY"))
		{
			par = Senal.USDJPY;
		}
		else if(cuerpo.contains("GBPUSD") || cuerpo.contains("GBP.D"))
		{
			par = Senal.GBPUSD;
		}
		else if(cuerpo.contains("USDCHF"))
		{
			par = Senal.USDCHF;
		}
		else if(cuerpo.contains("EURCHF"))
		{
			par = Senal.EURCHF;
		}
		else if(cuerpo.contains("AUDUSD"))
		{
			par = Senal.AUDUSD;
		}
		else if(cuerpo.contains("USDCAD"))
		{
			par = Senal.USDCAD;
		}
		else if(cuerpo.contains("NZDUSD") || cuerpo.contains("N2DUSD") || cuerpo.contains("MUSD") || cuerpo.contains("NZD.D"))
		{
			par = Senal.NZDUSD;
		}
		else if(cuerpo.contains("EURJPY") || cuerpo.contains("EUR3PY") || cuerpo.contains("EURWY"))
		{
			par = Senal.EURJPY;
		}
		else if(cuerpo.contains("GBPJPY") || cuerpo.contains("GBP3PY"))
		{
			par = Senal.GBPJPY;
		}
		else if(cuerpo.contains("CHFJPY") || cuerpo.contains("CHF3PY"))
		{
			par = Senal.CHFJPY;
		}
		else if(cuerpo.contains("GBPCHF"))
		{
			par = Senal.GBPCHF;
		}
		else if(cuerpo.contains("EURAUD"))
		{
			par = Senal.EURAUD;
		}
		else if(cuerpo.contains("AUDJPY") || cuerpo.contains("AUD3PY"))
		{
			par = Senal.AUDJPY;
		}
		else
		{
			par = Senal.ERROR;
		}
		int tipo;
		boolean compra = false;
		if(par == Senal.ERROR)
		{
			tipo = Senal.ERROR;
			if(estrategia1 != Senal.ERROR)
			{
				Error.agregar(cuerpo);
			}
		}
		else if(cuerpo.contains("Order Placed") || cuerpo.contains("Optimal") || cuerpo.contains("Near"))
		{
			tipo = Senal.NOIMPORTA;
		}
		else if(cuerpo.contains("Hit"))
		{
			tipo = Senal.HIT;
		}
		else if(cuerpo.contains("Sell"))
		{
			tipo = Senal.TRADE;
			compra = false;
		}
		else if(cuerpo.contains("Buy"))
		{
			tipo = Senal.TRADE;
			compra = true;
		}
		else
		{
			tipo = Senal.ERROR;
			Error.agregar(cuerpo);
		}
		Senal nueva = new Senal(estrategia1, par, tipo, compra);
		return nueva;
	}
	
	public static String darNombrePar(Senal senal)
	{
		int par = senal.par;
		String nombrePar = "";
		if(par == Senal.AUDJPY)
		{
			nombrePar = "AUDJPY";
		}
		else if(par == Senal.AUDUSD)
		{
			nombrePar = "AUDUSD";
		}
		else if(par == Senal.CHFJPY)
		{
			nombrePar = "CHFJPY";
		}
		else if(par == Senal.EURAUD)
		{
			nombrePar = "EURAUD";
		}
		else if(par == Senal.EURCHF)
		{
			nombrePar = "EURCHF";
		}
		else if(par == Senal.EURJPY)
		{
			nombrePar = "EURJPY";
		}
		else if(par == Senal.EURUSD)
		{
			nombrePar = "EURUSD";
		}
		else if(par == Senal.GBPCHF)
		{
			nombrePar = "GBPCHF";
		}
		else if(par == Senal.GBPJPY)
		{
			nombrePar = "GBPJPY";
		}
		else if(par == Senal.GBPUSD)
		{
			nombrePar = "GBPUSD";
		}
		else if(par == Senal.NZDUSD)
		{
			nombrePar = "NZDUSD";
		}
		else if(par == Senal.USDCAD)
		{
			nombrePar = "USDCAD";
		}
		else if(par == Senal.USDCHF)
		{
			nombrePar = "USDCHF";
		}
		else if(par == Senal.USDJPY)
		{
			nombrePar = "USDJPY";
		}
		else
		{
			nombrePar = "ERROR";
		}
		return nombrePar;
	}
	
	public static void intentarAgregar(Senal senal, ArrayList <Senal> senales) 
	{
		if(senal.tipo == Senal.HIT)
		{
			hit(senal, senales);
		}
		else if(senal.tipo == Senal.TRADE)
		{
			trade(senal, senales);
		}
	}
	
	private static void hit(Senal senal, ArrayList <Senal> senales) 
	{
		Senal opuesta;
		if(senales.contains(new Senal(senal.estrategia, senal.par, Senal.TRADE, true))) 
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

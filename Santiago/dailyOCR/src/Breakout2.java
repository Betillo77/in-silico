import java.io.File;
import java.util.ArrayList;


public class Breakout2
{
	public static ArrayList <Senal> senales = new ArrayList <Senal> ();
	
	public static void leerSenales()
	{
		Estrategia.leerSenales(new File(dailyOCR.pathPrincipal + "Breakout2.txt"), senales);
	}
	
	public static void intentarAgregar(Senal senal) 
	{
		if(senal.tipo == Senal.TRADE)
		{
			if(contar(senal.par) == 0)
			{
				agregar(senal.par, senal.compra);
			}
			else
			{
				Error.agregar("Mas de las senales permitidas, no se puede agregar " + Estrategia.darNombrePar(senal) + " a: 4");
			}
		}
		else if(senal.tipo == Senal.HIT)
		{
			if(Breakout.contiene(senal.par))
			{
				if(!dailyOCR.leerEstrategias(senal.par, "BreakoutL") && !dailyOCR.leerEstrategias(senal.par, "Breakout1"))
				{
					Estrategia.intentarAgregar(senal, Breakout.senales);
					return;
				}
			}
			if(contiene(senal.par))
			{
				if(!dailyOCR.leerEstrategias(senal.par, "Breakout2"))
				{
					cerrar(senal.par);
				}
				else
				{
					cerrarUna(senal.par);
				}
			}
			else
			{
				Error.agregar("No se pudo cerrar " + Estrategia.darNombrePar(senal) + " no esta en la lista de: 4, 1");
			}
		}
	}

	public static int contar(int par) 
	{
		int i = 0;
		for(Senal s : senales)
		{
			if(s.par == par)
			{
				i++;
			}
		}
		return i;
	}

	public static boolean contiene(int par) 
	{
		for(Senal s : senales)
		{
			if(s.par == par)
			{
				return true;
			}
		}
		return false;
	}
	
	private static void agregar(int par, boolean compra) 
	{
		for(int i = 0; i < 5; i++)
		{
			Senal nueva = new Senal(Senal.BREAKOUT2, par, Senal.TRADE, compra);
			EscritorB.agregar(nueva);
			senales.add(nueva);
		}
	}
	
	public static void cerrar(int par) 
	{
		for(int i = 0; i < senales.size(); i++)
		{
			Senal s = senales.get(i);
			if(s.par == par)
			{
				EscritorB.agregar(new Senal(Senal.BREAKOUT2, par, Senal.HIT, false), s);
				Estrategia.remover(s, senales);
				i--;
			}
		}
	}
	
	public static void cerrarUna(int par) 
	{
		for(int i = 0; i < senales.size(); i++)
		{
			Senal s = senales.get(i);
			if(s.par == par)
			{
				EscritorB.agregar(new Senal(Senal.BREAKOUT2, par, Senal.HIT, false), s);
				Estrategia.remover(s, senales);
				return;
			}
		}
	}
}

import java.io.File;
import java.util.ArrayList;


public class Breakout
{
	public static ArrayList <Senal> senales = new ArrayList <Senal> ();
	
	public static void leerSenales()
	{
		Estrategia.leerSenales(new File(dailyOCR.pathPrincipal + "Breakout.txt"), senales);
	}

	public static void intentarAgregar(Senal senal) 
	{
		if(senal.tipo == Senal.TRADE)
		{
			Estrategia.intentarAgregar(senal, senales);
		}
		else if(senal.tipo == Senal.HIT)
		{
			if(contiene(senal.par) && !Breakout2.contiene(senal.par))
			{
				Estrategia.intentarAgregar(senal, senales);
			}
			else
			{
				Breakout2.intentarAgregar(senal);
			}
		}
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

	public static void cerrar(int par) 
	{
		for(int i = 0; i < senales.size(); i++)
		{
			Senal s = senales.get(i);
			if(s.par == par)
			{
				Escritor.agregar(new Senal(Senal.BREAKOUT, par, Senal.HIT, false), s);
				Estrategia.remover(s, senales);
				i--;
			}
		}
	}
}

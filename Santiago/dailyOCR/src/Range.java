import java.io.File;
import java.util.ArrayList;


public class Range
{
	public static ArrayList <Senal> senales = new ArrayList <Senal> ();
	
	public static void leerSenales()
	{
		Estrategia.leerSenales(new File(dailyOCR.pathPrincipal + "Range.txt"), senales);
	}
	
	public static void intentarAgregar(Senal senal) 
	{
		if(senal.tipo == Senal.TRADE)
		{
			if(contiene(senal.par))
			{
				senal.range2 = !range2(senal.par);
				if(compra(senal.par) != senal.compra)
				{
					Estrategia.intentarAgregar(new Senal(Senal.RANGE, senal.par, Senal.HIT, false), senales);
				}
			}
			else
			{
				senal.range2 = dailyOCR.leerEstrategias(senal.par, "Range2");
			}
			Estrategia.intentarAgregar(senal, senales);
		}
		else if(senal.tipo == Senal.HIT)
		{
			if(contiene2(senal.par))
			{
				Senal opuesta = opuesta(senal.par, !dailyOCR.leerEstrategias(senal.par, "Range2"));
				Escritor.agregar(senal, opuesta);
				Estrategia.remover(opuesta, senales);
			}
			else if(contiene(senal.par))
			{
				if(range2(senal.par))
				{
					if(!dailyOCR.leerEstrategias(senal.par, "Range2"))
					{
						Estrategia.intentarAgregar(senal, senales);
					}
				}
				else
				{
					if(!dailyOCR.leerEstrategias(senal.par, "RangeL") && !dailyOCR.leerEstrategias(senal.par, "Range1"))
					{
						Estrategia.intentarAgregar(senal, senales);
					}
				}
			}
			else
			{
				Error.agregar("No se pudo cerrar " + Estrategia.darNombrePar(senal) + " no esta en la lista de: 2");
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
	
	public static boolean contiene2(int par) 
	{
		int i = 0;
		for(Senal s : senales)
		{
			if(s.par == par)
			{
				if(i == 0)
				{
					i++;
				}
				else
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean range2(int par) 
	{
		for(Senal s : senales)
		{
			if(s.par == par)
			{
				return s.range2;
			}
		}
		return false;
	}
	
	public static boolean compra(int par) 
	{
		for(Senal s : senales)
		{
			if(s.par == par)
			{
				return s.compra;
			}
		}
		return false;
	}
	
	public static Senal opuesta(int par, boolean range2) 
	{
		for(Senal s : senales)
		{
			if(s.par == par && s.range2 == range2)
			{
				return s;
			}
		}
		return new Senal(Senal.RANGE, par, Senal.TRADE, false);
	}
}

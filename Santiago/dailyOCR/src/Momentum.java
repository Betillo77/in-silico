import java.io.File;
import java.util.ArrayList;


public class Momentum
{	
	public static ArrayList <Senal> senales = new ArrayList <Senal> ();
	
	public static void leerSenales()
	{
		Estrategia.leerSenales(new File(dailyOCR.pathPrincipal + "Momentum.txt"), senales);
	}
	
	public static void intentarAgregar(Senal senal) 
	{
		boolean mandar = false;
		if(senal.tipo == Tipo)
		{
			mandar = !dailyOCR.leerEstrategias(senal.par, "Momentum2");
		}
		else if(senal.tipo == Senal.TRADE)
		{
			mandar = dailyOCR.leerEstrategias(senal.par, "Momentum2");
		}
		if(mandar)
		{
			Estrategia.intentarAgregar(senal, senales);
		}
	}
}

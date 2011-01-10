package analisis;

import java.util.ArrayList;

public class Estadistica 
{
	
	public static double calcularEMA(ArrayList <Double> valores, int periodo)
	{
		double acumulado = 0;
		for(int i = 0; i < 14; i++)
			acumulado += valores.get(i);
		acumulado /= 14;
		double a = 2.0d / (periodo + 1.0d);
		for(int i = 14; i < valores.size(); i++)
			acumulado = a * valores.get(i) + (1 - a) * acumulado;
		return acumulado;
	}

}

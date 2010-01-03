package genericos.genetico;
 
import implementacion.genetico.Cromosoma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class Genetico
{       
	public static Random generator = new Random();
        
	public int azar(int a, int b)
	{
		return generator.nextInt(b - a + 1) + a;
	}
	
	public Cromosoma[] solucionar(Cromosoma[] poblacionInicial, int numeroGeneraciones)
	{
		double mejorAptitud = 0;
		for(int i = 0; i < numeroGeneraciones; i++)
		{
			double mejorLocal = 0;
			Cromosoma mejorCromosomaLocal = null;
			for(Cromosoma x : poblacionInicial)
			{
				if(x.darAptitud() > mejorLocal)
				{
					mejorLocal = x.darAptitud();
					mejorCromosomaLocal = x;
				}
			}
			if(mejorLocal > mejorAptitud)
			{
				mejorAptitud = mejorLocal;
				poblacionInicial[0].establecerMejorActual(mejorCromosomaLocal);
			}
			poblacionInicial = generarSiguientePoblacion(poblacionInicial);
		}
		return poblacionInicial;
	}
        
	/**
	 * Genera y selecciona la nueva población de soluciones a partir
	 * de la población inicial dada, utilizando el esquema de selección
	 * proporcionado.
	 * @param poblacionActual: es un arreglo de cromosomas que representa 
	 * la población actual
	 * @return nuevos: Es un arreglo con la nueva población de cromosomas	
	 */
	public Cromosoma[] generarSiguientePoblacion(Cromosoma[] poblacionActual)
	{
		// Calcula la media de las aptitudes
		double media = 0;
		for(int i = 0; i < poblacionActual.length; i++)
		{
			media += poblacionActual[i].darAptitud();
		}
		media /= poblacionActual.length;
		
		// Determina el número de descendientes que puede tener
        // cada solución
		int [] nDescendientes = new int[poblacionActual.length];
		for(int i = 0; i < poblacionActual.length; i++)
		{
			nDescendientes[i] = poblacionActual[i].numeroDescendientes(media);
		}
		ArrayList <Cromosoma> descendientes = new ArrayList <Cromosoma> ();
        
		// Calcula las nuevas soluciones posibles de la nueva generación
		for(int i = 0; i < poblacionActual.length; i++)
		{
			for(int j = 0; j < nDescendientes[i]; j++)
			{
				int pareja = -1;
				do
				{
					pareja = azar(0, poblacionActual.length - 1);
				}
				while(pareja == i);
				descendientes.add(poblacionActual[i].cruzar(poblacionActual[pareja]));
			}
		}
		
		// Escoge los mejores N, donde N es el tamaño de la poblacion actual, y los retorna
		Collections.sort(descendientes); 
		Cromosoma[] elegidos = new Cromosoma[poblacionActual.length];
		for(int i = 0; i < poblacionActual.length; i++)
		{
			elegidos[i] = descendientes.get(descendientes.size() - 1 - i); // esta es la opción elitista
		//	int d = azar(0, descendientes.size() - 1); // esta es la opcion al azar 
		//	elegidos[i] = descendientes.remove(d); // esta es la opcion al azar 
		}
		return elegidos;
	}
}

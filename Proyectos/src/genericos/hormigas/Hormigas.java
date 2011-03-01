package genericos.hormigas;


import java.lang.reflect.Method;
import java.util.ArrayList;
 
public class Hormigas 
{	
	Method metodoCambio;
	
    /**
     * Funcion que soluciona un problema utilizando el algoritmo de colonia de hormigas
     * @param matrizCostos: Matriz de adyacencia con los costos entre los nodos
     * @param modelo: Modelo establecido para seleccionar los vecinos y los parametros
     * @return solucionOptima: Mejor solucion encontrada
     */
	public ArrayList <Integer> solucionar(double [][] matrizCostos, ModeloHormigas modelo)
	{
		double[][] feromonas = new double[matrizCostos.length][matrizCostos.length];
		double inicializacion = ((double)modelo.darM()) / ((double)(modelo.darQ() * modelo.darInicial()));
		for(int i = 0; i < matrizCostos.length; i++)
			for(int j = 0; j < matrizCostos.length; j++)
				feromonas[i][j] = inicializacion;
		int iteracion = 0;
		double pesoOptimo = modelo.darInicial();
		ArrayList <Integer> solucionOptima = null;
		while(iteracion++ < modelo.darNumeroIteraciones())
		{
			System.gc();
			int hormiga = 0;
			@SuppressWarnings("unchecked")
			ArrayList <Integer> [] hormigas = new ArrayList [modelo.darM()];
			while(hormiga++ < modelo.darM())
			{
				ArrayList <Integer> candidatas = new ArrayList <Integer> ();
				for(int i = 1; i < matrizCostos.length; i++)
					candidatas.add(i);
				int actual = 0;
				ArrayList <Integer> rutaActual = new ArrayList <Integer> ();
				rutaActual.add(actual);
				while(!candidatas.isEmpty())
				{
					double [] probabilidades = new double[candidatas.size()];
					double sumatoria = 0;
					for(int j = 0; j < candidatas.size(); j++)
					{
						probabilidades[j] = Math.pow(feromonas[actual][candidatas.get(j)], modelo.darA()) * Math.pow(1 / matrizCostos[actual][candidatas.get(j)], modelo.darB());
						sumatoria += probabilidades[j];
					}
					for(int j = 0; j < candidatas.size(); j++)
					{
						probabilidades[j] /= sumatoria;
					}
					int escogido = modelo.escoger(probabilidades);
					rutaActual.add(candidatas.get(escogido));
					for(int j = 0; j < rutaActual.size() - 1; j++)
					{
						feromonas[rutaActual.get(j)][rutaActual.get(j + 1)] = feromonas[rutaActual.get(j)][rutaActual.get(j + 1)] * (1 - modelo.darP());
					}
					actual = candidatas.get(escogido);
					candidatas.remove(escogido);
				}
				rutaActual.add(0);
				hormigas[hormiga - 1] = rutaActual;
			}
			for(int i = 0; i < matrizCostos.length; i++)
				for(int j = 0; j < matrizCostos.length; j++)
					feromonas[i][j] = feromonas[i][j] * (1 - modelo.darE());
			double mejorPeso = Double.POSITIVE_INFINITY;
			ArrayList <Integer> mejorSolucion = null;
			for(int i = 0; i < hormigas.length; i++)
			{
				double peso = 0;
				for(int j = 0; j < hormigas[i].size() - 1; j++)
				{
					peso += matrizCostos[hormigas[i].get(j)][hormigas[i].get(j + 1)];
				}
				for(int j = 0; j < hormigas[i].size() - 1; j++)
				{
					feromonas[hormigas[i].get(j)][hormigas[i].get(j + 1)] += 1 / peso;
				}
				if(peso < mejorPeso)
				{
					mejorPeso = peso;
					mejorSolucion = hormigas[i];
				}
			}
			if(mejorPeso < pesoOptimo)
			{
				pesoOptimo = mejorPeso;
				modelo.establecerMejorActual(mejorSolucion, mejorPeso);
				solucionOptima = mejorSolucion;
			}
		}
		return solucionOptima;
	}
}

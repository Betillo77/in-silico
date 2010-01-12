package genericos.genetico;

public interface Cromosoma extends Comparable <Cromosoma>
{
	
    /**
     * Combina este y el cromosoma otro para formar un hijo
     * @param otro: Representa el cromosoma con el cual este se va a cruzar
     * @return hijo: es el producto del cruce de este y el cromosoma otro
     */
	public Cromosoma cruzar(Cromosoma otro);
	
    /**
     * Retorna la aptitud de este cromosoma
     * @return aptitud: aptitud relativa de este cromosoma con respecto al problema
     */
	public double darAptitud();

    /**
     * Retorna el numero de descendientes que este cromosoma deberia tener, teniendo
     * en cuenta no solo el tamaño de la poblacion, sino tambien la media de las
     * aptitudes de los elementos de la población actual
     * @return numeroDescendientes: numero de descendientes que le corresponden a este cromosoma
     */
	public int numeroDescendientes(double media);
	
	 /**
     * Función que establece la mejor solucion encontrada hasta el momento
     * @param mejorSolucion: Solución encontrada
     */
	void establecerMejorActual(Cromosoma mejorSolucion);
}

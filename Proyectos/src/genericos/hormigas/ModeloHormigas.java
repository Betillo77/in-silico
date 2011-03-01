package genericos.hormigas;

import java.util.ArrayList;

public interface ModeloHormigas 
{
    /**
     * @return M: Numero de hormigas en el modelo
     */
	int darM();
	
    /**
     * @return M: Parametro de inicializacion de feromonas
     */
	int darQ();
	
    /**
     * @return Inicial: Aproximacion inicial, no tiene que ser buena
     */
	int darInicial();
	
    /**
     * @return NumeroIteraciones: Numero de veces que se van a simular las N hormigas
     */
	int darNumeroIteraciones();
	
    /**
     * @return A: Importancia relativa de las feromonas con relacion a B
     */
	double darA();
	
    /**
     * @return B: Importancia relativa de la distancia con relacion a A
     */
	double darB();
	
    /**
     * @return P: Indice de evaporacion local, es decir, cada vez que una hormiga pasa
     */
	double darP();
	
    /**
     * @return E: Indice de evaporacion global, es decir, cada iteracion
     */
	double darE();
	
    /**
     * Funcion que escoge un vecino, de entre los posibles
     * @param posibles: Lista de los vecinos, con la probabilidad de cada uno
     * @return escogido: Indice del vecino escogido
     */
	int escoger(double [] posibles);

    /**
     * Funcion que establece la mejor solucion encontrada hasta el momento
     * @param mejorSolucion: Solucion encontrada
     */
	void establecerMejorActual(ArrayList <Integer> mejorSolucion, double valor);
}
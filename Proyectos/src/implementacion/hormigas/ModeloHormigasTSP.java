package implementacion.hormigas;

import genericos.hormigas.Hormigas;
import genericos.hormigas.ModeloHormigas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ModeloHormigasTSP implements ModeloHormigas
{
    static int M = 100; 
    static int Q = 1;   
    static int inicial = 3000;
    static int numIteraciones = 300000;
    static double a = 0.1;    
    static double b = 5;
    static double p = 0.001;
    static double e = 0.1;
    static Random azar = new Random();
    
    Grafico grafico;
    
	public int escoger(double [] m)
	{
		double q = 0.9;
		double q0 = azar.nextDouble();
		if(q <= q0)
		{
		    return probabilistico(m);
		}
		else
		{
		    return deterministico(m, 3);
		}
	}

    /**
     * Opción probabilistica. Escoge entre torneo, ruleta, y el azar.
     * @param  m: Vector con las probabilidades de cada vecino
     * @return indice: Indice escogido
     */
	public static int probabilistico(double [] m)
	{
	    double x = azar.nextDouble();
	    if(x >= 0.1 && x < 0.5)
	    {
	        return torneo(m);
	    }
	    else if(x >= 0.5)
	    {
	        return ruleta(m);
	    }
	    else
	    {
	        return aleatorio(m);
	    }
	}
	
    /**
     * Opción deterministica. Escoge uno entre los N mas cercanos, la mitad del tiempo, la otra mitad escoge el mejor.
     * @param n: Numero de vecinos cercanos entre los cuales escoger
     * @param  m: Vector con las probabilidades de cada vecino
     * @return indice: Indice escogido
     */
	public static int deterministico(double [] m, int n)
	{
		double x = azar.nextDouble();
		if(x < 0.95)
		{
			double mejor = 0;
			int indiceMejor = 0;
			for(int i = 0; i < m.length; i++)
			{
				if(m[i] > mejor)
				{
					mejor = m[i];
					indiceMejor = i;
				}
			}
			return indiceMejor;
		}
		if(m.length < n)
			return probabilistico(m);
		class Valor implements Comparable <Valor>
		{
			int indice; 
			double probabilidad;
			
			public Valor(int indice, double probabilidad)
			{
				this.indice = indice;
				this.probabilidad = probabilidad;
			}
			
			@Override
			public int compareTo(Valor otro) 
			{
				return new Double(probabilidad).compareTo(otro.probabilidad);
			}
		}
		Valor[] valores = new Valor[m.length];
		for(int i = 0; i < m.length; i++)
		{
			valores[i] = new Valor(i, m[i]);
		}
		Arrays.sort(valores);
		valores = Arrays.copyOfRange(valores, valores.length - n, valores.length);
		double[] mNueva = new double[valores.length];
		for(int i = 0; i < mNueva.length; i++)
		{
			mNueva[i] = valores[i].probabilidad;
		}
		double acum = 0;
		for(int i = 0; i < mNueva.length; i++)
		{
			acum += mNueva[i];
		}
		for(int i = 0; i < mNueva.length; i++)
		{
			mNueva[i] /= acum;
		}
		return valores[probabilistico(mNueva)].indice;
	}
	
    /**
     * Torneo. Elige dos al azar y escoge el mejor entre ellos.
     * @param  m: Vector con las probabilidades de cada vecino
     * @return indice: Indice escogido
     */
	public static  int torneo(double [] m)
	{
	    int indice1 = azar.nextInt(m.length);
	    int indice2 = azar.nextInt(m.length);
	    int retornar;
	    if(m[indice1] > m[indice2])
	    {
	        retornar = indice1;
	    }
	    else
	    {
	        retornar = indice2;
	    }
	    return retornar;
	}

    /**
     * Ruleta. Elige un vecino basado en las probabilidades de cada uno.
     * @param  m: Vector con las probabilidades de cada vecino
     * @return indice: Indice escogido
     */
	public static int ruleta(double [] m)
	{
	    double acum = m[0];
	    double numero = azar.nextDouble();
	    int retorno = 0;
	    for(int i = 1;i < m.length; i++)
	    {
	        if(acum > numero)
	        {
	            retorno = i - 1;
	            break;
	        }
	        acum += m[i];
	    }
	    return retorno;
	}
	
    /**
     * Aleatorio. Elige al azar.
     * @param  m: Vector con las probabilidades de cada vecino
     * @return indice: Indice escogido
     */
	public static int aleatorio(double [] m)
	{
		int indice = azar.nextInt(m.length);
		return indice;
	}

	public double darA() 
	{
		return a;
	}

	public double darB() 
	{
		return b;
	}

	public double darE() 
	{
		return e;
	}

	public int darInicial()
	{
		return inicial;
	}

	public int darM() 
	{
		return M;
	}

	public int darNumeroIteraciones()
	{
		return numIteraciones;
	}

	public double darP() 
	{
		return p;
	}

	public int darQ()
	{
		return Q;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		Hormigas h = new Hormigas();
	    Grafico g = new Grafico();
		ModeloHormigasTSP modelo = new ModeloHormigasTSP();
		Object[] entrada = Lectura.leer();
		modelo.grafico = g;
		g.ciudades = (ArrayList <Ciudad>) entrada[0];
		g.setVisible(true);
		h.solucionar((double[][]) entrada[1], modelo);
	}

	public void establecerMejorActual(ArrayList <Integer> mejorSolucion, double pesoMejor) 
	{
		grafico.mejoresActual = mejorSolucion;
		grafico.solucionActual = pesoMejor;
		System.out.println(pesoMejor);
		grafico.repaint();
	}

}

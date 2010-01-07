package implementacion.hormigas;

import genericos.hormigas.Hormigas;
import genericos.hormigas.ModeloHormigas;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ModeloHormigasTSP implements ModeloHormigas
{
    static int M = 100; 
    static int Q = 1;   
    static int inicial = 4000;
    static int numIteraciones = 300000;
    static double a = 0.1;    
    static double b = 5;
    static double p = 0.001;
    static double e = 0.1;
    static Random azar = new Random();
    
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
		if(Grafico.este.termino())
		{
			return 0;
		}
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
		final Grafico g = new Grafico();
		Grafico.este = g;
		Object[] entrada = Lectura.leer();
		HiloGrafico hg = new HiloGrafico((ArrayList <Ciudad>) entrada[0]);
		new Thread(hg).start();
	}

	public void establecerMejorActual(ArrayList <Integer> mejorSolucion, double pesoMejor) 
	{
		Grafico.este.establecerMejorActual(mejorSolucion, pesoMejor);
	}
	
	public static class HiloHormiga implements Runnable 
	{
		ModeloHormigas modelo;
		double[][] matrizDistancias;
		
		@Override
		public void run() 
		{
			Hormigas h = new Hormigas();
			h.solucionar(matrizDistancias, modelo);
		}
	}
	
	public static class HiloGrafico implements Runnable
	{
		ArrayList<Ciudad> ciudades;
		public HiloGrafico(ArrayList <Ciudad> ciudades) 
		{
			this.ciudades = ciudades;
		}

		@Override
		public void run() 
		{
			Grafico.este.ciudades = ciudades;
			FramePrincipal principal = new FramePrincipal();
			principal.setVisible(true);
		}
	}
	
	static class FramePrincipal extends JFrame
	{
		private static final long serialVersionUID = -4984727444905699787L;

		public FramePrincipal()
		{
			super("Colonia de hormigas");
			setBounds(50, 50, 500, 500);
			ButtonGroup bg = new ButtonGroup();
		    JRadioButton modoEdicion = new JRadioButton("Modo edicion");
		    JRadioButton modoSolucion = new JRadioButton("Modo solucion");
		    bg.add(modoEdicion);
		    bg.add(modoSolucion);
		    bg.setSelected(modoEdicion.getModel(), true);
		    modoEdicion.addActionListener(Grafico.este);
		    modoSolucion.addActionListener(Grafico.este);
		    JPanel panel = new JPanel();
		    panel.add(modoEdicion);
		    panel.add(modoSolucion);
		    setLayout(new BorderLayout());
		    add(panel, BorderLayout.SOUTH);
		    add(Grafico.este, BorderLayout.CENTER);
		}
	}
}

class Ciudad
{
	int x;
	int y;
}

class Lectura 
{
	public static Object[] leer()
	{
		Scanner sc;
		try {
			sc = new Scanner(new File("src/implementacion/hormigas/Cities.xml"));
		} catch (FileNotFoundException e) {
			sc = null;
		}
		sc.nextLine();
		sc.nextLine();
		ArrayList <Ciudad> ciudades = new ArrayList <Ciudad> ();
		while(sc.hasNextLine())
		{
			boolean primero = false;
			String primeroS = "";
			String segundoS = "";
			boolean segundoInicial = false;
			boolean segundo = false;
			String a = sc.nextLine();
			if(a.equals("</CityList>"))
				break;
			for(char c : a.toCharArray())
			{
				if(!primero)
				{
					if(c == '"')
					{
						primero = true;
						continue;
					}
				}
				if(primero && !segundoInicial && !segundo)
				{
					if(c == '"')
					{
						segundoInicial = true;
						primero = true;
						continue;
					}
					primeroS += c;
				}
				if(segundoInicial && !segundo)
				{
					if(c == '"')
					{
						segundoInicial = false;
						segundo = true;
						continue;
					}
				}
				if(segundo)
				{
					if(c == '"')
					{
						break;
					}
					segundoS += c;
				}
			}
			Ciudad ci = new Ciudad();
			ci.x = Integer.parseInt(primeroS);
			ci.y = Integer.parseInt(segundoS);
			ciudades.add(ci);
		}
		double [][] datosPrueba = generarDistancias(ciudades);
		Object[] retorno = {ciudades, datosPrueba};
		return retorno;
	}
	
	public static double[][] generarDistancias(ArrayList <Ciudad> ciudades)
	{
		double [][] datosPrueba = new double[ciudades.size()][ciudades.size()];
		for(int i = 0; i < ciudades.size(); i++)
			for(int j = 0; j < ciudades.size(); j++)
			{
				if(i == j)
				{
					datosPrueba[i][j] = Double.POSITIVE_INFINITY;
				}
				else
				{
					datosPrueba[i][j] = Math.sqrt((ciudades.get(i).x - ciudades.get(j).x) * (ciudades.get(i).x - ciudades.get(j).x) + (ciudades.get(i).y - ciudades.get(j).y) * (ciudades.get(i).y - ciudades.get(j).y));
				}
			}
		return datosPrueba;
	}
}



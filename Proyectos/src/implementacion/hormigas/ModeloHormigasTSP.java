package implementacion.hormigas;

import genericos.hormigas.Hormigas;
import genericos.hormigas.ModeloHormigas;

import java.awt.BorderLayout;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
    static ArrayList <Ciudad> ciudadesIniciales = new ArrayList <Ciudad> ();
    
    static
    {
    	ciudadesIniciales.add(new Ciudad(160, 370));
    	ciudadesIniciales.add(new Ciudad(121, 132));
    	ciudadesIniciales.add(new Ciudad(58, 234));
    	ciudadesIniciales.add(new Ciudad(41, 213));
    	ciudadesIniciales.add(new Ciudad(143, 86));
    	ciudadesIniciales.add(new Ciudad(312, 176));
    	ciudadesIniciales.add(new Ciudad(121, 264));
    	ciudadesIniciales.add(new Ciudad(231, 366));
    	ciudadesIniciales.add(new Ciudad(380, 62));
    	ciudadesIniciales.add(new Ciudad(202, 260));
    	ciudadesIniciales.add(new Ciudad(241, 12));
    	ciudadesIniciales.add(new Ciudad(23, 356));
    	ciudadesIniciales.add(new Ciudad(43, 317));
    	ciudadesIniciales.add(new Ciudad(175, 134));
    	ciudadesIniciales.add(new Ciudad(260, 125));
    	ciudadesIniciales.add(new Ciudad(277, 386));
    	ciudadesIniciales.add(new Ciudad(312, 153));
    	ciudadesIniciales.add(new Ciudad(23, 256));
    	ciudadesIniciales.add(new Ciudad(313, 297));
    	ciudadesIniciales.add(new Ciudad(131, 96));
    	ciudadesIniciales.add(new Ciudad(87, 67));
    	ciudadesIniciales.add(new Ciudad(249, 283));
    	ciudadesIniciales.add(new Ciudad(307, 234));
    	ciudadesIniciales.add(new Ciudad(159, 167));
    	ciudadesIniciales.add(new Ciudad(285, 123));
    	ciudadesIniciales.add(new Ciudad(12, 34));
    	ciudadesIniciales.add(new Ciudad(85, 356));
    	ciudadesIniciales.add(new Ciudad(387, 125));
    	ciudadesIniciales.add(new Ciudad(135, 268));
    	ciudadesIniciales.add(new Ciudad(185, 345));
    	ciudadesIniciales.add(new Ciudad(75, 314));
    	ciudadesIniciales.add(new Ciudad(275, 45));
    	ciudadesIniciales.add(new Ciudad(313, 78));
    	ciudadesIniciales.add(new Ciudad(110, 67));
    	ciudadesIniciales.add(new Ciudad(256, 215));
    	ciudadesIniciales.add(new Ciudad(314, 164));
    	ciudadesIniciales.add(new Ciudad(342, 186));
    	ciudadesIniciales.add(new Ciudad(331, 214));
    	ciudadesIniciales.add(new Ciudad(141, 275));
    	ciudadesIniciales.add(new Ciudad(67, 389));
    }
    
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
     * Opcion probabilistica. Escoge entre torneo, ruleta, y el azar.
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
     * Opcion deterministica. Escoge uno entre los N mas cercanos, la mitad del tiempo, la otra mitad escoge el mejor.
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

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		System.setOut(new PrintStream("salida.txt"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(ImageIO.read(new File("h.gif")), "gif", baos);
		byte[] imagen1 = baos.toByteArray();
		BigInteger b = new BigInteger(imagen1);
		System.out.print(b.toString(Character.MAX_RADIX));
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
			
			JApplet applet = new JApplet();
			FramePrincipal principal = new FramePrincipal();
			principal.setVisible(true);
			applet.add(principal);
			applet.setVisible(true);
		}
	}
	
	public static class FramePrincipal extends JApplet
	{
		private static final long serialVersionUID = -4984727444905699787L;

		public void init()
		{
			final Grafico g = new Grafico();
			Grafico.este = g;
			Grafico.este.ciudades = ciudadesIniciales;
			setBounds(50, 50, 500, 500);
			ButtonGroup bg = new ButtonGroup();
		    JRadioButton modoEdicion = new JRadioButton("Modo edicion");
		    JRadioButton modoSolucion = new JRadioButton("Modo solucion");
		    JButton aleatorio = new JButton("Aleatorio");
		    JCheckBox mostrarOptima = new JCheckBox("Mostrar optima");
		    bg.add(modoEdicion);
		    bg.add(modoSolucion);
		    bg.setSelected(modoEdicion.getModel(), true);
		    modoEdicion.addActionListener(Grafico.este);
		    modoSolucion.addActionListener(Grafico.este);
		    mostrarOptima.addActionListener(Grafico.este);
		    aleatorio.addActionListener(Grafico.este);
		    JPanel panel = new JPanel();
		    panel.add(modoEdicion);
		    panel.add(modoSolucion);
		    panel.add(aleatorio);
		    panel.add(mostrarOptima);
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
	
	public Ciudad(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}


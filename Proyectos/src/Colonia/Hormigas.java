package Colonia;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class Hormigas 
{
	double [][] costo;
	double [][] feromonas;
	static final int M = 1000;
	static final int Q = 1;
	static final int Inicial = 3500;
	static final int numIteraciones = 1000;
	static final int a = 5;
	static final int b = 1;
	static final double p = 0.001;
	static final double e = 0.001;
	
	@SuppressWarnings("unchecked")
	public void solucionar(double [][] costoInicial, Grafico g)
	{
		costo = costoInicial;
		feromonas = new double[costo.length][costo.length];
		double inicializacion = M / (Q * Inicial);
		for(int i = 0; i < costo.length; i++)
			for(int j = 0; j < costo.length; j++)
				feromonas[i][j] = inicializacion;
		int iteracion = 0;
		double pesoOptimo = Inicial;
		ArrayList <Integer> solucionOptima = null;
		while(iteracion++ < numIteraciones)
		{
			g.iteracionActual = iteracion;
			Graphics g1 = g.getGraphics();
			g1.setColor(Color.WHITE);
			g1.fillRect(450, 465, 30, 30);	
			g1.setColor(Color.BLACK);
			g1.drawString(iteracion  + "", 460, 475);
			System.gc();
			int hormiga = 0;
			ArrayList <Integer> [] hormigas = new ArrayList [M];
			while(hormiga++ < M)
			{
				ArrayList <Integer> candidatas = new ArrayList <Integer> ();
				for(int i = 1; i < costo.length; i++)
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
						probabilidades[j] = Math.pow(feromonas[actual][candidatas.get(j)], a) * Math.pow(1 / costo[actual][candidatas.get(j)], b);
						sumatoria += probabilidades[j];
					}
					for(int j = 0; j < candidatas.size(); j++)
					{
						probabilidades[j] /= sumatoria;
					}
					int escogido = qAleatorio(probabilidades);
					rutaActual.add(candidatas.get(escogido));
					for(int j = 0; j < rutaActual.size() - 1; j++)
					{
						feromonas[rutaActual.get(j)][rutaActual.get(j + 1)] = feromonas[rutaActual.get(j)][rutaActual.get(j + 1)] * (1 - p);
					}
					actual = candidatas.get(escogido);
					candidatas.remove(escogido);
				}
				rutaActual.add(0);
				hormigas[hormiga - 1] = rutaActual;
			}
			for(int i = 0; i < costo.length; i++)
				for(int j = 0; j < costo.length; j++)
					feromonas[i][j] = feromonas[i][j] * (1 - e);
			double mejorPeso = Double.POSITIVE_INFINITY;
			ArrayList <Integer> mejorSolucion = null;
			for(int i = 0; i < hormigas.length; i++)
			{
				double peso = 0;
				for(int j = 0; j < hormigas[i].size() - 1; j++)
				{
					peso += costo[hormigas[i].get(j)][hormigas[i].get(j + 1)];
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
				solucionOptima = mejorSolucion;
				g.mejoresActual = solucionOptima;
				g.solucionActual = mejorPeso;
				g.soloNumero = false;
				g.repaint();
				g.soloNumero = true;
				double init = M / (Q * mejorPeso);
				for(int i = 0; i < costo.length; i++)
					for(int j = 0; j < costo.length; j++)
						feromonas[i][j] = init;
			}
		}
		System.out.println(pesoOptimo);
		for(int n : solucionOptima)
		{
			System.out.print(n + " ");
		}
	}
	
	public static int qAleatorio(double []m)
	{
	/////////////////////////////
	final double q=0.5;
	double q0=random(); 
	//////////////////////////////

	if(q<=q0)
	{
	    return probabilistico(m);//EXPLORACION
	}
	else{
	    return deterministico(m);//EXPLOTACION

	    }

	}


	public static int probabilistico(double []m)
	{
	    double x=random();
	    if(x>=0.1&&x<0.5)
	    {
	        return torneo(m);
	    }
	    else if(x>=0.5)
	    {
	        return ruleta(m);
	    }
	    else{
	        return aleatorio(m);
	    }

	}
	public static int deterministico(double [] m)
	{
	    int indiceMejor = 0;
	    double mejor = m[0];
	    for(int i = 1; i<m.length; i++)
	    {
	        if (m[i] > mejor)
	        {
	            mejor = m[i];
	            indiceMejor = i;
	        }
	    }
	    return indiceMejor;
	}

	public static double random()
	{
	return Math.random();

	}
	//////////////////////////////////////
	///////////////TORNEO///////////A-1
	/////////////////////////////////////
	public static  int torneo(double []m)
	{
	    

	    int indice1=(int)(random()*m.length);
	    int indice2=(int)(random()*m.length);
	    int retornar;



	    if(m[indice1]>m[indice2])
	    {
	        retornar=indice1;
	    }

	    else
	    {
	        retornar=indice2;
	    }

	return retornar;
	}


	////////////////////////////////////////////////
	//////////////////////RULETA//////////A-2
	////////////////////////////////////////////////
	public static int ruleta(double []m)
	{
	    double acum=m[0];
	    double numero=random();
	    int retorno=0;
	  
	    
	    for(int i=1;i<m.length;i++)
	    {
	        if(acum>numero)
	        {
	            retorno=i-1;
	            break;
	        }
	        acum+=m[i];

	    }

	    return retorno;
	}
	///////////////////////////////////////////////////////////////
	/////////////////////////////ALEATORIO///////////////A-3
	///////////////////////////////////////////////////////////////
 
	public static int aleatorio(double []m)
	{
	int indice=(int)(random()*m.length);
	return indice;
	}
	
	public static double [][] leer(String nombreArchivo){
		
		Scanner scanner;
		double dato;
		try {
			scanner = new Scanner(new File(nombreArchivo));
		} catch (FileNotFoundException e) {
			scanner = new Scanner("");
			e.printStackTrace();
		}
		int tam = scanner.nextInt();
		double [][] datosPrueba = new double[tam][tam];
		for(int i = 0; i < datosPrueba.length; i++)
			for(int j = 0; j < datosPrueba.length; j++){
				dato = scanner.nextDouble();
				if (dato < 0)
					datosPrueba[i][j] = Double.POSITIVE_INFINITY;
				else
					datosPrueba[i][j] = dato;
			}
		
		return datosPrueba;
	}
}

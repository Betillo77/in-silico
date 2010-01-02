package zulutrade.analizador;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class ProveedorDuplicado implements Serializable
{
	private static final long serialVersionUID = 145545L;
	private int[] mejorStop = new int[4];
	private double[] mejorRiesgoBeneficio = new double[4];
	private int[] mejorGanancia = new int[4];
	private ArrayList <ArrayList <ProveedorProbable>> analisisStop = new ArrayList <ArrayList <ProveedorProbable>> ();
	private ArrayList <String[][]> tablasMejores = new ArrayList <String[][]> ();
	private ArrayList <int[]> ganancia = new ArrayList <int[]> ();
	private String nombre;
	
	public ProveedorDuplicado(String nombre, ArrayList <Negocio> negocios) 
	{
		this.nombre = nombre;
		generar(negocios);
	}
	
	public void generar(ArrayList <Negocio> negocios)
	{
		mejorStop = new int[4];
		mejorRiesgoBeneficio = new double[4];
		mejorGanancia = new int[4];
		analisisStop = new ArrayList <ArrayList <ProveedorProbable>> ();
		tablasMejores = new ArrayList <String[][]> ();
		ganancia = new ArrayList <int[]> ();
		generarTablasMejores(negocios);
	}
	
	private void generarTablasMejores(ArrayList <Negocio> negocios)
	{
		for(int lotes = 1; lotes < 5; lotes++)
		{
			int mejorGanancia = 0;
			int mejorStop = 0;
			double mejorRiesgoBeneficio = 0;
			ArrayList <ProveedorProbable> proveedoresProbables = new ArrayList <ProveedorProbable> ();
			int[] gananciaActual = new int[501];
			for(int stop = 0; stop < 501; stop++)
			{
				ProveedorProbable actual = new ProveedorProbable(negocios, lotes, stop);
				gananciaActual[stop] = actual.darGanancia();
				proveedoresProbables.add(actual);
			}
			ganancia.add(gananciaActual);
			String[][] tablaMejores = new String[4][5];
			ArrayList <ProveedorProbable> mejoresAnalisisStop = new ArrayList <ProveedorProbable> ();
			for(int hasta = 1; hasta < 6; hasta++)
			{
				int mejorGananciaInterna = 0;
				int mejorStopInterno = 0;
				double mejorRiesgoBeneficioInterno = -1D;
				ProveedorProbable mejorAnalisisStop = proveedoresProbables.get(0);
				for(int stop = (100 * (hasta - 1)); (stop < (100 * hasta) + 1); stop++)
				{
					double riesgoBeneficioActual = proveedoresProbables.get(stop).darRiesgoBeneficio();
					if(mejorRiesgoBeneficioInterno < riesgoBeneficioActual)
					{
						mejorRiesgoBeneficioInterno = riesgoBeneficioActual;
						mejorAnalisisStop = proveedoresProbables.get(stop);
						mejorStopInterno = mejorAnalisisStop.darStop();
						mejorGananciaInterna = mejorAnalisisStop.darGanancia();
					}
				}
				tablaMejores[0][hasta - 1] = String.valueOf((hasta * 100));
				tablaMejores[1][hasta - 1] = String.valueOf(mejorStopInterno);
				tablaMejores[2][hasta - 1] = "$" + mejorGananciaInterna;
				NumberFormat formateador = new DecimalFormat("#0.0000");
				tablaMejores[3][hasta - 1] = formateador.format(mejorRiesgoBeneficioInterno).replace(',', '.');
				mejorAnalisisStop.generarPromedios();
				mejoresAnalisisStop.add(mejorAnalisisStop);
				if(mejorRiesgoBeneficio < mejorRiesgoBeneficioInterno)
				{
					mejorRiesgoBeneficio = mejorRiesgoBeneficioInterno;
					mejorStop = mejorStopInterno;
					mejorGanancia = mejorGananciaInterna;
				}
			}
			tablasMejores.add(tablaMejores);
			analisisStop.add(mejoresAnalisisStop);
			this.mejorRiesgoBeneficio[lotes - 1] = mejorRiesgoBeneficio;
			this.mejorStop[lotes - 1] = mejorStop;
			this.mejorGanancia[lotes - 1] = mejorGanancia;
		}
	}
	
	public String darNumero(int lotes)
	{
		try
		{
			int fila = darFila(lotes);
			return String.valueOf(analisisStop.get(lotes - 1).get(fila).darAnalisisStop().length);
		}
		catch(Exception e)
		{
			return "0";
		}
	}
	
	public String darMejorRiesgoBeneficio()
	{
		double mejorRiesgo = 0;
		for(int lotes = 1; lotes < 5; lotes++)
		{
			for(int i = 0; i < 5; i++)
			{
				double riesgo = Double.parseDouble(tablasMejores.get(lotes - 1)[3][i]);
				if(mejorRiesgo < riesgo)
				{
					mejorRiesgo = riesgo;
				}
			}
		}
		NumberFormat formateador = new DecimalFormat("#0.0000");
		return formateador.format(mejorRiesgo);
	}
	
	public int darMejorStop(int lotes)
	{
		return mejorStop[lotes - 1];
	}

	public String darMejorGanancia(int lotes)
	{
		int mejorGanancia = this.mejorGanancia[lotes - 1];
		return (mejorGanancia < 100) ? ("  $" + mejorGanancia) : (mejorGanancia < 1000) ? (" $" + mejorGanancia) : ("$" + mejorGanancia);
	}
	
	public int[] darAnalisisStop(int lotes, int fila)
	{
		return analisisStop.get(lotes - 1).get(fila).darAnalisisStop();
	}
	
	public String darPromedioAlto(int lotes)
	{
		try
		{
			int fila = darFila(lotes);
			return analisisStop.get(lotes - 1).get(fila).darPromedioAlto();
		}
		catch(Exception e)
		{
			return "0";
		}
	}
	
	public String darPromedioBajo(int lotes)
	{
		try
		{
			int fila = darFila(lotes);
			return analisisStop.get(lotes - 1).get(fila).darPromedioBajo();
		}
		catch(Exception e)
		{
			return "0";
		}
	}
	
	public String darPromedioCierre(int lotes)
	{
		try
		{
			int fila = darFila(lotes);
			return analisisStop.get(lotes - 1).get(fila).darPromedioCierre();
		}
		catch(Exception e)
		{
			return "0";
		}
	}
	
	public String darDesviacionPromedioCierre(int lotes)
	{
		try
		{
			int fila = darFila(lotes);
			return analisisStop.get(lotes - 1).get(fila).darDesviacionPromedioCierre();
		}
		catch(Exception e)
		{
			return "0";
		}
	}
	
	private int darFila(int lotes)
	{
		double mejorRiesgo = 0;
		int fila = 0;
		for(int i = 0; i < 5; i++)
		{
			double riesgo = Double.parseDouble(tablasMejores.get(lotes - 1)[3][i]);
			if(mejorRiesgo < riesgo)
			{
				mejorRiesgo = riesgo;
				fila = i;
			}
		}
		return fila;
	}

	public String darNombre() 
	{
		return nombre;
	}

	public int[] darGanancia(int lotes)
	{
		return ganancia.get(lotes - 1);
	}

	public String[][] darTablaMejores(int lotes) 
	{
		return tablasMejores.get(lotes - 1);
	}
}

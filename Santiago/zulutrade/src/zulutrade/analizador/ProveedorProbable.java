package zulutrade.analizador;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class ProveedorProbable implements Serializable
{
	private static final long serialVersionUID = 6145547L;
	private double riesgoBeneficio;
	private int stop;
	private int ganancia;
	private int[] recorrido;
	private String promedioCierreD;
	private String promedioCierre;
	private String promedioBajo;
	private String promedioAlto;
	private transient ArrayList <Negocio> negociosTemporales;
	
	public ProveedorProbable(ArrayList <Negocio> negocios, int lotes, int stop) 
	{
		this.stop = stop;
		generarProveedorProbable(negocios, lotes);
	}

	private void generarProveedorProbable(ArrayList <Negocio> negocios, int lotes)
	{
		ArrayList <Negocio> negociosNuevos = new ArrayList <Negocio> ();
		ArrayList <Negocio> negociosAbiertos = new ArrayList <Negocio> ();
		for(Negocio n : negocios)
		{
			if(negociosAbiertos.size() == lotes)
			{
				boolean termino = false;
				for(int i = 0; (i < negociosAbiertos.size()) && (!termino); i++)
				{
					if(negociosAbiertos.get(i).darFin(stop).before(n.darInicio()))
					{
						negociosAbiertos.remove(i);
						negociosAbiertos.add(n);
						negociosNuevos.add(n);
						termino = true;
					}
				}
			}
			else
			{
				negociosNuevos.add(n);
				negociosAbiertos.add(n);
			}
		}
		generarGanancia(negociosNuevos);
		negociosTemporales = negociosNuevos;
	}
	
	public void generarPromedios()
	{
		if(negociosTemporales != null)
		{
			generarPromedioAlto(negociosTemporales);
			generarPromedioBajo(negociosTemporales);
			generarPromedioCierre(negociosTemporales);
			negociosTemporales = null;
		}
	}
	
	private void generarGanancia(ArrayList <Negocio> negocios) 
	{
		ganancia = 0;
		recorrido = new int[negocios.size()];
		int riesgo = 0;
		for(int i = 0; i < negocios.size(); i++)
		{
			ganancia += negocios.get(i).darCierre(stop);
			recorrido[i] = ganancia;
			riesgo += negocios.get(i).darRiesgo(stop);
		}
		riesgoBeneficio = (ganancia + 0.0) / (riesgo + 0.0);
	}

	private void generarPromedioAlto(ArrayList <Negocio> negocios)
	{
		double acumulado = 0;
		for(Negocio actual : negocios)
		{
			acumulado += actual.darAlto();
		}
		acumulado = acumulado / negocios.size();
		NumberFormat formato = new DecimalFormat("#0.00");
		promedioAlto = formato.format(acumulado);
	}
	
	private void generarPromedioBajo(ArrayList <Negocio> negocios)
	{
		double acumulado = 0;
		for(Negocio actual : negocios)
		{
			acumulado += actual.darBajo(stop);
		}
		acumulado = acumulado / negocios.size();
		NumberFormat formato = new DecimalFormat("#0.00");
		promedioBajo = formato.format(acumulado);
	}
	
	private void generarPromedioCierre(ArrayList <Negocio> negocios)
	{
		double acumulado = 0;
		double acumuladoD = 0;
		for(Negocio actual : negocios)
		{
			acumulado += actual.darCierre(stop);
		}
		acumulado = acumulado / negocios.size();
		for(Negocio actual : negocios)
		{
			acumuladoD += (actual.darCierre(stop) - acumulado) * (actual.darCierre(stop) - acumulado);
		}
		acumuladoD = acumuladoD / negocios.size();
		acumuladoD = Math.sqrt(acumuladoD);
		NumberFormat formato = new DecimalFormat("#0.00");
		promedioCierre = formato.format(acumulado);
		promedioCierreD  = formato.format(acumuladoD);
	}	
	
	public String darPromedioAlto() 
	{
		return promedioAlto;
	}
	
	public String darPromedioBajo() 
	{
		return promedioBajo;
	}
	
	public String darPromedioCierre() 
	{
		return promedioCierre;
	}
	
	public String darDesviacionPromedioCierre() 
	{
		return promedioCierreD;
	}
	
	public double darRiesgoBeneficio() 
	{
		return riesgoBeneficio;
	}

	public int darStop() 
	{
		return stop;
	}

	public int darGanancia() 
	{
		return ganancia;
	}

	public int[] darAnalisisStop() 
	{
		return recorrido;
	}
}

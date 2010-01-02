package zulutrade.analizador;


import java.io.Serializable;


public class Negocio implements Serializable, Comparable <Negocio>
{
	private static final long serialVersionUID = 145546L;
	private int bajo;
	private int alto;
	private int cierre;
	private String par;
	private FechaSimple inicio;
	private FechaSimple fin;

	public Negocio(int bajo, int alto, int cierre, String par, FechaSimple inicio, FechaSimple fin)
	{
		this.bajo = bajo;
		this.alto = alto;
		this.cierre = cierre;
		this.par = par;
		this.inicio = inicio;
		this.fin = fin;
	}
	
	public int darBajo()
	{
		return bajo;
	}
	
	public int darBajo(int stop)
	{
		if(bajo > (stop * -1))
		{
			return bajo;
		}
		else
		{
			return (stop * -1);
		}
	}
	
	public int darAlto()
	{
		return alto;
	}
	
	public int darCierre()
	{
		return cierre;
	}
	
	public int darCierre(int stop)
	{
		if(bajo > (stop * -1))
		{
			return cierre;
		}
		else
		{
			return (stop * -1);
		}
	}
	
	public String darPar()
	{
		return par;
	}
	
	public FechaSimple darInicio()
	{
		return inicio;
	}
	
	public FechaSimple darFin()
	{
		return fin;
	}
	
	public FechaSimple darFin(int stop) 
	{
		if(bajo > (stop * -1))
		{
			return fin;
		}
		else
		{
			double duracion = (double)(fin.darMilis() - inicio.darMilis());
			double altoMedio = alto > stop ? ((double)(stop) / 2) : ((double)(alto) / 2);
			double recorrido = ((((double)(stop)) + altoMedio) / ((double)(alto - bajo)));
			return new FechaSimple(inicio.darMilis() + (long)(duracion * recorrido));
		}
	}

	public int darRiesgo(int stop) 
	{
		if(bajo > (stop * -1))
		{
			return (bajo * -1);
		}
		else
		{
			return stop;
		}	
	}
	
	@Override
	public int compareTo(Negocio o)
	{
		if(this.darInicio().after(o.darInicio()))
		{
			return 1;
		}
		else if(this.darInicio().before(o.darInicio()))
	    {
			return -1;
		}
		else
		{
			return 0;
		}		
	}
}
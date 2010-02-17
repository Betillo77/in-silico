import java.util.Calendar;


public class Registro implements Comparable <Registro>
{
	Integrante integrante;
	Calendar fecha;
	int horas;
	int idInterno;
	
	public Registro(Integrante integrante, Calendar fecha, int horas, int idInterno) 
	{
		this.integrante = integrante;
		this.fecha = fecha;
		this.horas = horas;
		this.idInterno = idInterno;
	}

	public int compareTo(Registro otro) 
	{
		return integrante.compareTo(otro.integrante) == 0 && fecha.compareTo(otro.fecha) == 0 && horas == otro.horas ? 0 : -1;
	}

	public String toString()
	{
		return integrante + " - " + fecha.get(Calendar.DATE) + "/" + (fecha.get(Calendar.MONTH) + 1) + "/" + fecha.get(Calendar.YEAR) + " - " + horas + " " + (horas == 1 ? " hora" : "horas");
	}
}

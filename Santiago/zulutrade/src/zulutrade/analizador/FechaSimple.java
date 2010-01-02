package zulutrade.analizador;


import java.io.Serializable;
import java.util.Calendar;


public class FechaSimple implements Comparable <FechaSimple>, Serializable
{
	private static final long serialVersionUID = 6145548L;
	private long milis;
	
	public FechaSimple(Calendar fecha)
	{
		milis = fecha.getTimeInMillis();
	}
	
	public FechaSimple(long fecha)
	{
		milis = fecha;
	}

	public long darMilis()
	{
		return milis;
	}
	
	public boolean before(FechaSimple otra)
	{
		return compareTo(otra) < 0;
	}
	
	public boolean after(FechaSimple otra)
	{
		return compareTo(otra) > 0;
	}
	
	@Override
	public int compareTo(FechaSimple otra)
	{
		long milisOtra = otra.darMilis();
		return (milis > milisOtra) ? 1 : (milis == milisOtra) ? 0 : -1;
	}

}

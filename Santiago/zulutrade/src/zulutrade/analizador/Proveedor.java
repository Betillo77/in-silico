package zulutrade.analizador;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;


public class Proveedor implements Serializable
{
	private static final long serialVersionUID = 145544L;
	private transient ArrayList <Negocio> negocios = new ArrayList <Negocio> ();
	private String nombre;
	private ProveedorDuplicado[] duplicados = new ProveedorDuplicado[5];
	private int numero;
	
	public Proveedor(ArrayList <Negocio> negocios, String nombre)
	{
		this.negocios = negocios;
		this.nombre = nombre;
		generarNumero();
	}
	
	public String darNombre()
	{
		return nombre;
	}
	
	public ArrayList <Negocio> darNegocios()
	{
		return negocios;
	}

	public String darMejorRiesgoBeneficioSemanas(int semanas)
	{
		return duplicados[semanas].darMejorRiesgoBeneficio();
	}
	
	public void generarDuplicados()
	{
		for(int i = 0; i < 5; i++)
		{
			int semanas;
			if(i == 0)
			{
				semanas = 1;
			}
			else if (i == 1)
			{
				semanas = 2;
			}
			else if(i == 2)
			{
				semanas = 4;
			}
			else if(i == 3)
			{
				semanas = 8;
			}
			else
			{
				semanas = 0;
			}
			ArrayList <Negocio> negociosN = new ArrayList <Negocio> ();
			Calendar actualN = darFechaActualMenos(semanas);
			FechaSimple actual = new FechaSimple(actualN);
			for(int j = 0; j < negocios.size(); j++)
			{
				if(semanas == 0 || negocios.get(j).darFin().after(actual))
				{
					negociosN.add(negocios.get(j));
				}
			}
			duplicados[i] = new ProveedorDuplicado(nombre, negociosN);
		}
	}
	
	public ProveedorDuplicado darDuplicado(int indice)
	{
		return duplicados[indice];
	}
	
	private static Calendar darFechaActualMenos(int semanas)
	{
		Calendar nueva = Calendar.getInstance();
		long milis = ((long)604800000) * semanas;
		long buscado = nueva.getTimeInMillis() - milis;
		nueva.setTimeInMillis(buscado);
		return nueva;
	}
	
	public String darPromedioAlto()
	{
		return duplicados[4].darPromedioAlto(4);
	}
	
	public String darPromedioBajo()
	{
		return duplicados[4].darPromedioBajo(4);
	}
	
	public String darPromedioCierre()
	{
		return duplicados[4].darPromedioCierre(4);
	}
	
	private void generarNumero()
	{
		numero = negocios.size();
	}
	
	public String darNumero()
	{
		return String.valueOf(numero);
	}
	
	public static Proveedor crear_proveedor(String ruta, String nombre)
	{
		try 
		{
			ArrayList <Negocio> negocios = new ArrayList <Negocio> ();
			Workbook workbook = Workbook.getWorkbook(new File(ruta));
			Sheet sheet = workbook.getSheet(0); 
			for(int i = 1; i < sheet.getRows(); i++)
			{
				Cell celda = sheet.getCell(3, i); 
				Calendar inicioN = Calendar.getInstance();
				inicioN.setTime(((DateCell) celda).getDate());
				FechaSimple inicio = new FechaSimple(inicioN);
				celda = sheet.getCell(4, i);
				Calendar finN = Calendar.getInstance();
				finN.setTime(((DateCell) celda).getDate());
				FechaSimple fin = new FechaSimple(finN);
				celda = sheet.getCell(8, i);
				int bajo = Integer.parseInt(celda.getContents());
				celda = sheet.getCell(7, i);
				int alto = Integer.parseInt(celda.getContents());
				celda = sheet.getCell(9, i);
				int cierre = Integer.parseInt(celda.getContents());
				celda = sheet.getCell(2, i);
				String par = celda.getContents();
				Negocio n = new Negocio(bajo, alto, cierre, par, inicio, fin);
				negocios.add(n);
			}
			Object[] temp = negocios.toArray();
			Arrays.sort(temp);
			negocios = new ArrayList <Negocio> ();
			for(Object n : temp)
			{
				negocios.add((Negocio) n);
			}
			Proveedor nuevo = new Proveedor(negocios, nombre);
			return nuevo;
		} 
		catch (Exception e)
		{	
			return null;
		}
	}
}
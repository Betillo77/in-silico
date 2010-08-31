package modelo.dailyFx;

import java.util.ArrayList;

import modelo.Escritor;
import modelo.Senal;
import control.dailyOCR;

public class EscritorElite extends Escritor
{

	public EscritorElite(String path, ArrayList <ArrayList <EntradaEscritor> > iniciales) 
	{
		super(path, iniciales);
	}

	protected Senal darSenal(EntradaEscritor entrada) 
	{
		return ((EstrategiaElite) dailyOCR.darEstrategia(entrada.getId())).tienePar(entrada.getId(), entrada.getPar());
	}
}

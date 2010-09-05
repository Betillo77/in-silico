package modelo.dailyFx;

import java.util.ArrayList;

import modelo.EntradaEscritor;
import modelo.Escritor;
import modelo.Senal;
import control.IdEstrategia;
import control.dailyOCR;

public class EscritorElite extends Escritor
{

	public EscritorElite(String path, ArrayList <ArrayList <EntradaEscritor> > iniciales) 
	{
		super(path, iniciales);
	}

	@Override
	protected Senal darSenal(EntradaEscritor entrada) 
	{
		return ((EstrategiaElite) dailyOCR.darEstrategia(IdEstrategia.ELITE)).tienePar(entrada.getId(), entrada.getPar());
	}
}

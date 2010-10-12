package modelo.dailyFx;

import java.util.ArrayList;

import modelo.EntradaEscritor;
import modelo.Escritor;
import modelo.Senal;
import control.IdEstrategia;

public class EscritorElite extends Escritor
{
	public EscritorElite(String path, ArrayList <ArrayList <EntradaEscritor> > iniciales) 
	{
		super(path, iniciales);
	}

	@Override
	protected Senal darSenal(EntradaEscritor entrada) 
	{
		return ((EstrategiaElite) IdEstrategia.ELITE.darEstrategia()).tienePar(entrada.getId(), entrada.getPar());
	}
}

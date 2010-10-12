package control;

import modelo.Estrategia;

public enum IdEstrategia
{
	BREAKOUT1, BREAKOUT2, RANGE1, RANGE2, MOMENTUM1, MOMENTUM2, TECHNICAL, JOEL, ELITE;
	
	Estrategia esta = null;
	
	public Estrategia darEstrategia()
	{
		if(esta == null)
			Error.agregar("Estrategia " + this + " fue llamada antes de ser registrada.");
		return esta;
	}
	
	public void registrarEstrategia(Estrategia e)
	{
		if(e.getId() == this)
			esta = e;
		else
			Error.agregar("Error registrando estrategia " + this);
	}
	
	public static IdEstrategia darEstrategia(int id)
	{
		switch(id)
		{
			case 5: return BREAKOUT2;
			case 6: return MOMENTUM2;
			case 10: return RANGE2;
			case 11: return MOMENTUM1;
			case 12: return BREAKOUT1;
			case 14: return RANGE1;
			default: 
				Error.agregar("id estrategia desconocido: " + id);
				return null;
		}
	}
}
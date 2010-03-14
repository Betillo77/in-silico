package control;

public enum IdEstrategia
{
	BREAKOUT1, BREAKOUT2, RANGE1, RANGE2, MOMENTUM1, MOMENTUM2, TECHNICAL, JOEL, JOELRECOMENDACIONES;
	
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
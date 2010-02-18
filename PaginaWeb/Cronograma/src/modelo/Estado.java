package modelo;

public enum Estado 
{
	SININICIAR, ENPROGRESO, TERMINADO, PROBADO;
	
	public String toString()
	{
		switch(this)
		{
			case SININICIAR: return "Sin iniciar";
			case ENPROGRESO: return "En progreso";
			case TERMINADO:  return "Terminado";
			case PROBADO:    return "Probado";
			default:         return "Error";
		}
	}
}

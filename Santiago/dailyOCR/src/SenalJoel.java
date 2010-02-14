
public class SenalJoel extends Senal {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8940769909212189794L;
	double stop;
	boolean recomendado;
	public SenalJoel(IdEstrategia estrategia, boolean compra, Par par,
			int numeroLotes, double precioEntrada, double stop,boolean recomendado){
		super(estrategia, compra, par, numeroLotes, precioEntrada);
		this.stop=stop;
		this.recomendado=recomendado;
	}
	
	public String toString()
	{
		return estrategia + " " + (compra ? "Compra" : "Venta") + " " + numeroLotes + " Lotes de " + par + " a: " + precioEntrada + " " + (recomendado? "Recomendado" : "Normal") + " " + "precio de parada: " + stop; 
	}

}

package modelo;
import control.IdEstrategia;

public class EntradaEscritor
{
	private IdEstrategia id;
	private Par par;
	private String linea;
	private boolean cierre;
	private int numeroLotes = 0;
	private Senal afectada = null;

	public EntradaEscritor()
	{
	}

	public EntradaEscritor(IdEstrategia i, Par p, String l, boolean c) 
	{
		id = i;
		par = p;
		linea = l;
		cierre = c;
	}

	public EntradaEscritor(IdEstrategia i, Par p, String l, boolean c, int nL, Senal a) 
	{
		this(i, p, l, c);
		numeroLotes = nL;
		afectada = a;
	}

	public void setId(IdEstrategia id) {
		this.id = id;
	}
	
	public IdEstrategia getId() {
		return id;
	}

	public void setPar(Par par) {
		this.par = par;
	}
	
	public Par getPar() {
		return par;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getLinea() {
		return linea;
	}
	
	public void setCierre(boolean cierre) {
		this.cierre = cierre;
	}

	public boolean isCierre() {
		return cierre;
	}

	public void setNumeroLotes(int numeroLotes) {
		this.numeroLotes = numeroLotes;
	}

	public int getNumeroLotes() {
		return numeroLotes;
	}

	public void setAfectada(Senal afectada) {
		this.afectada = afectada;
	}

	public Senal getAfectada() {
		return afectada;
	}
}
package modelo;
import control.IdEstrategia;

public class EntradaEscritor
{
	private IdEstrategia id;
	private Par par;
	private String linea;
	private boolean cierre;

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

	public IdEstrategia getId() {
		return id;
	}

	public void setId(IdEstrategia id) {
		this.id = id;
	}

	public Par getPar() {
		return par;
	}

	public void setPar(Par par) {
		this.par = par;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	public String getLinea() {
		return linea;
	}

	public boolean isCierre() {
		return cierre;
	}

	public void setCierre(boolean cierre) {
		this.cierre = cierre;
	}
}
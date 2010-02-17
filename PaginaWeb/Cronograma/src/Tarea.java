import java.util.ArrayList;

public class Tarea
{
	String idProyecto;
	int id;
	String concepto;
	Tarea padre;
	int tiempo;
	Estado estado;
	int tiempoTrabajado;
	ArrayList <Tarea> preRequisitos;
	ArrayList <Integrante> responsables;
	ArrayList <Tarea> hijos = new ArrayList <Tarea> ();

	public Tarea(String idProyecto, int id, String concepto, Tarea padre, int tiempo, Estado estado, int tiempoTrabajado, ArrayList <Tarea> preRequisitos, ArrayList <Integrante> responsables)
	{
		this.idProyecto = idProyecto;
		this.id = id;
		this.concepto = concepto;
		this.padre = padre;
		this.tiempo = tiempo;
		this.estado = estado;
		this.tiempoTrabajado = tiempoTrabajado;
		this.preRequisitos = preRequisitos;
		this.responsables = responsables;
	}
	
	public String toString() 
	{
		return id + " - " + concepto;
	}
}

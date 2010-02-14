import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CronogramaGrafico extends JApplet 
{

	private static final long serialVersionUID = 79834162436921L;
	
	String idProyecto;

	private JPanel panelPrincipal = null;
	private ArbolTabla arbolTabla = null;  //  @jve:decl-index=0:visual-constraint="543,10"

	/**
	 * This is the default constructor
	 */
	public CronogramaGrafico() {
		super();
	}
	
	public void agregarTarea(Tarea t) {
		t.id = arbolTabla.tareas[arbolTabla.tareas.length - 1].id + 1;
		Tarea[] tareas = arbolTabla.tareas;
		// TODO Conexion con BD
		tareas = Arrays.copyOf(tareas, tareas.length + 1);
		tareas[tareas.length - 1] = t;
		arbolTabla.tareas = tareas;
		t.padre.hijos.add(t);
		((ModeloLogicoArbol) ((AdaptadorArbolTabla) arbolTabla.getModel()).treeTableModel).cambiarModelo();
	}
	
	public void modificarTarea(Tarea t) 
	{
		// TODO Conexion con BD
		for(int i = 0; i < arbolTabla.tareas.length; i++)
			if(t.id == arbolTabla.tareas[i].id)
			{
				if(arbolTabla.tareas[i].padre != null)
				{
					
					if(arbolTabla.tareas[i].padre.id == t.padre.id)
					{
						int indice = arbolTabla.tareas[i].padre.hijos.indexOf(arbolTabla.tareas[i]);
						arbolTabla.tareas[i].padre.hijos.set(indice, t);
					}
					else
					{
						arbolTabla.tareas[i].padre.hijos.remove(arbolTabla.tareas[i]);
						for(Tarea ta : arbolTabla.tareas)
							if(ta.id == t.padre.id)
							{
								ta.hijos.add(t);
								break;
							}
					}
				}
				arbolTabla.tareas[i] = t;
				break;
			}
		((ModeloLogicoArbol) ((AdaptadorArbolTabla) arbolTabla.getModel()).treeTableModel).cambiarModelo();
	}
	

	public void eliminarTarea(int id) {
		// TODO Conexion con BD
    	eliminar(id, arbolTabla.tareas[0]);
    	((ModeloLogicoArbol) ((AdaptadorArbolTabla) arbolTabla.getModel()).treeTableModel).cambiarModelo();
	}
	
	private boolean eliminar(int idCambio, Tarea tarea) {
		if(tarea.id == idCambio)
			return true;
		else
		{
			for(Tarea hija : tarea.hijos)
			{
				if(eliminar(idCambio, hija))
				{
					tarea.hijos.remove(hija);
					break;
				}
			}
			return false;
		}
	}
		
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() {
		Integrante s = new Integrante("Santiago Gutierrez Alzate", "desocupado");
		Integrante s1 = new Integrante("Sebastian Gomez Gonzalez", "sebasutp");
		Integrante s2 = new Integrante("juandavid_1024@hotmail.com", "Juan David");
		Integrante s3 = new Integrante("Adrian", "noga..loco");
		Integrante[] i = {s, s1, s2, s3};
		Tarea t = new Tarea("sga", 0, "prueba1", null, 0, Estado.ENPROGRESO, 0, new ArrayList<Tarea>(), new ArrayList<Integrante>());
		Tarea t1 = new Tarea("sga", 1, "prueba2", null, 0, Estado.ENPROGRESO, 0, new ArrayList<Tarea>(), new ArrayList<Integrante>());
		Tarea t2 = new Tarea("sga", 2, "prueba3", null, 0, Estado.ENPROGRESO, 0, new ArrayList<Tarea>(), new ArrayList<Integrante>());
		Tarea t3 = new Tarea("sga", 3, "prueba4", null, 0, Estado.ENPROGRESO, 0, new ArrayList<Tarea>(), new ArrayList<Integrante>());
		Tarea t4 = new Tarea("sga", 4, "prueba5", null, 0, Estado.ENPROGRESO, 0, new ArrayList<Tarea>(), new ArrayList<Integrante>());
		t1.padre = t;
		t1.preRequisitos.add(t);
		t1.responsables.add(s);
		t1.responsables.add(s3);
		t.hijos.add(t1);
		t.hijos.add(t2);
		t.hijos.add(t3);
		t.hijos.add(t4);
		t1.padre = t;
		t2.padre = t;
		t3.padre = t;
		t4.padre = t;
		t4.preRequisitos.add(t);
		Tarea[] ta = {t, t1, t2, t3, t4};
		arbolTabla = new ArbolTabla(this, new ModeloLogicoArbol(t), i, ta);
		this.setSize(492, 551);
		this.setContentPane(getPanelPrincipal());
		arbolTabla.setVisible(true);
		arbolTabla.setBounds(new Rectangle(386, 71, 56, 34));
		panelPrincipal.setVisible(true);
		this.setVisible(true);
	}

	/**
	 * This method initializes panelPrincipal	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelPrincipal() {
		panelPrincipal = new JPanel();
		panelPrincipal.setLayout(null);
		JScrollPane scroll = new JScrollPane(arbolTabla);
		scroll.setBounds(new Rectangle(0, 0, 488, 549));
		panelPrincipal.add(scroll, null);
		return panelPrincipal;
	}		
}  //  @jve:decl-index=0:visual-constraint="47,10"

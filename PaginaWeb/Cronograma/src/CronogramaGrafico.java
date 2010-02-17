import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CronogramaGrafico extends JApplet 
{

	private static final long serialVersionUID = 79834162436921L;
	
	String idProyecto = "porta";

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
		ConexionBaseDatos.crearTarea(t, idProyecto);
		tareas = Arrays.copyOf(tareas, tareas.length + 1);
		tareas[tareas.length - 1] = t;
		arbolTabla.tareas = tareas;
		t.padre.hijos.add(t);
		((ModeloLogicoArbol) ((AdaptadorArbolTabla) arbolTabla.getModel()).treeTableModel).cambiarModelo();
	}
	
	public void modificarTarea(Tarea t) 
	{
		ConexionBaseDatos.actualizarTarea(t);
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
		ConexionBaseDatos.eliminarTarea(id);
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
		
		ArrayList <Integrante> integrantes = ConexionBaseDatos.darIntegrantes();
		Integrante[] i = new Integrante[integrantes.size()];
		integrantes.toArray(i);
		Arrays.sort(i);
		Collections.sort(integrantes);
		ArrayList <Tarea> tareas = ConexionBaseDatos.leerTareasActuales(idProyecto, integrantes);
		Tarea[] ta = new Tarea[tareas.size()];
		tareas.toArray(ta);
		Tarea raiz = null;
		for(Tarea t : ta)
			if(t.padre == null)
			{
				raiz = t;
				break;
			}
		arbolTabla = new ArbolTabla(this, new ModeloLogicoArbol(raiz), i, ta);
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

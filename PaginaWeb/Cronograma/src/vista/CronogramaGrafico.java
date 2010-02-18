package vista;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import conexion.ConexionBaseDatos;


import modelo.AdaptadorArbolTabla;
import modelo.Integrante;
import modelo.ModeloLogicoArbol;
import modelo.Tarea;

public class CronogramaGrafico extends JApplet 
{
	private static final long serialVersionUID = 79834162436921L;
	
	String idProyecto = "porta";

	private JPanel panelPrincipal = null;
	private ArbolTabla arbolTabla = null;  //  @jve:decl-index=0:visual-constraint="543,10"
	private ArrayList <Integrante> integrantes;

	/**
	 * This is the default constructor
	 */
	public CronogramaGrafico() 
	{
		super();
	}
	
	public void agregarTarea(Tarea t)
	{
		try
		{
			t.id = arbolTabla.tareas[arbolTabla.tareas.length - 1].id + 1;
			ConexionBaseDatos.crearTarea(t, idProyecto);
			actualizarTareas();
			((ModeloLogicoArbol) ((AdaptadorArbolTabla) arbolTabla.getModel()).treeTableModel).cambiarModelo();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error de conexión con el servidor");
			System.exit(0);
		}
	}
	
	public void modificarTarea(Tarea t) 
	{
		try
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
			refrescarArbolTabla();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error de conexión con el servidor");
			System.exit(0);
		}
	}
	

	public void eliminarTarea(int id) 
	{
		try
		{
			ConexionBaseDatos.eliminarTarea(id);
			actualizarTareas();
			refrescarArbolTabla();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error de conexión con el servidor");
			System.exit(0);
		}
	}	

	public Tarea[] darTareasServidor()
	{
		try
		{
			ArrayList <Tarea> tareas = ConexionBaseDatos.leerTareasActuales(idProyecto, integrantes);
			Tarea[] ta = new Tarea[tareas.size()];
			tareas.toArray(ta);
			return ta;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error de conexión con el servidor");
			System.exit(0);
			return null;
		}
	}
	
	public Tarea darRaiz(Tarea[] tareas)
	{
		Tarea raiz = null;
		for(Tarea t : tareas)
			if(t.padre == null)
			{
				raiz = t;
				break;
			}
		return raiz;
	}
	
	public void actualizarTareas()
	{
		Tarea[] ta = darTareasServidor();
		Tarea raiz = darRaiz(ta);
		arbolTabla.tareas = ta;
		arbolTabla.modeloArbolTabla.cambiarRaiz(raiz);
	}
	
	public void refrescarArbolTabla() 
	{
		((ModeloLogicoArbol) ((AdaptadorArbolTabla) arbolTabla.getModel()).treeTableModel).cambiarModelo();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() {
		try
		{
			integrantes = ConexionBaseDatos.darIntegrantes();
			Integrante[] i = new Integrante[integrantes.size()];
			integrantes.toArray(i);
			Arrays.sort(i);
			Collections.sort(integrantes);
			Tarea[] ta = darTareasServidor();
			Tarea raiz = darRaiz(ta);
			arbolTabla = new ArbolTabla(this, new ModeloLogicoArbol(raiz), i, ta);
			this.setSize(800, 600);
			this.setContentPane(getPanelPrincipal());
			arbolTabla.setVisible(true);
			panelPrincipal.setVisible(true);
			this.setVisible(true);
		}
		catch(Exception e)
		{
			try
			{
				JOptionPane.showMessageDialog(null, "Error de conexión con el servidor");
				System.exit(0);
			}
			catch(Exception e1)
			{
				System.exit(0);
			}
		}
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
		scroll.setBounds(new Rectangle(0, 0, 800, 600));
		panelPrincipal.add(scroll, null);
		return panelPrincipal;
	}		
}  //  @jve:decl-index=0:visual-constraint="47,10"

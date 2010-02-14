import java.awt.Component;
import java.awt.Dialog;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DialogoTarea extends JDialog {

	private static final long serialVersionUID = 1L;
	private CronogramaGrafico framePadre = null;
	private JPanel jContentPane = null;
	private JLabel padreLabel = null;
	private JComboBox padre = null;
	private JLabel nombreLabel = null;
	private JTextArea nombre = null;
	private JSlider tiempo = null;
	private JLabel tiempoLabel = null;
	private JList responsables = null;
	private JLabel responsablesLabel = null;
	private JButton agregarResponsable = null;
	private JList requsitos = null;
	private JButton agregarRequsito = null;
	private JLabel requisitosLabel = null;
	private JButton agregar = null;
	private JButton cancelar = null;
	private JScrollPane scrollRequisitos = null;
	private JScrollPane scrollResponsables = null;
	private JLabel numeroHoras = null;
	private Integrante[] integrantes;
	private Vector <Integrante> integrantesAgregados = new Vector <Integrante> ();  //  @jve:decl-index=0:
	private Tarea[] tareas;
	private Vector <Tarea> tareasAgregadas = new Vector <Tarea> ();  //  @jve:decl-index=0:
	private Vector <String> tareasAgregadasId = new Vector <String> ();  //  @jve:decl-index=0:
	private JScrollPane scrollNombre = null; 
	private Tarea modificando = null;
	private JLabel estadoLabel = null;
	private JComboBox estado = null;

	public DialogoTarea(CronogramaGrafico cg, Integrante[] i, Tarea[] t) {
		super((Dialog)null);
		framePadre = cg;
		integrantes = i;
		tareas = t;
		initialize();
	}
	
	public DialogoTarea(CronogramaGrafico cg, Integrante[] i, Tarea[] t, Tarea aEditar) {
		super((Dialog)null);
		framePadre = cg;
		integrantes = i;
		tareas = t;
		initialize();
		padre.setSelectedItem(aEditar.padre);
		nombre.setText(aEditar.concepto);
		tiempo.setValue(aEditar.tiempo);
		numeroHoras.setText(tiempo.getValue() + " horas");
		integrantesAgregados = new Vector <Integrante> (aEditar.responsables);
		responsables.setListData(integrantesAgregados);
		tareasAgregadas = new Vector <Tarea> (aEditar.preRequisitos);
		for(Tarea ta : tareasAgregadas)
			tareasAgregadasId.add(ta.id + "");
		requsitos.setListData(tareasAgregadasId);
		agregar.setText("Modificar");
		estado.setSelectedItem(aEditar.estado);
		modificando = aEditar;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(404, 498);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			estadoLabel = new JLabel();
			estadoLabel.setBounds(new Rectangle(74, 382, 41, 16));
			estadoLabel.setText("Estado");
			numeroHoras = new JLabel();
			numeroHoras.setBounds(new Rectangle(314, 154, 64, 15));
			numeroHoras.setText("1 horas");
			requisitosLabel = new JLabel();
			requisitosLabel.setBounds(new Rectangle(56, 287, 60, 16));
			requisitosLabel.setText("Requisitos");
			responsablesLabel = new JLabel();
			responsablesLabel.setBounds(new Rectangle(35, 195, 81, 16));
			responsablesLabel.setText("Responsables");
			tiempoLabel = new JLabel();
			tiempoLabel.setBounds(new Rectangle(71, 152, 45, 16));
			tiempoLabel.setText("Tiempo");
			nombreLabel = new JLabel();
			nombreLabel.setBounds(new Rectangle(71, 53, 45, 16));
			nombreLabel.setText("Nombre");
			padreLabel = new JLabel();
			padreLabel.setText("Padre");
			padreLabel.setBounds(new Rectangle(81, 14, 34, 16));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(padreLabel, null);
			jContentPane.add(getPadre(), null);
			jContentPane.add(nombreLabel, null);
			jContentPane.add(getTiempo(), null);
			jContentPane.add(tiempoLabel, null);
			jContentPane.add(responsablesLabel, null);
			jContentPane.add(getAgregarResponsable(), null);
			jContentPane.add(getAgregarRequsito(), null);
			jContentPane.add(requisitosLabel, null);
			jContentPane.add(getAgregar(), null);
			jContentPane.add(getCancelar(), null);
			jContentPane.add(getScrollRequisitos(), null);
			jContentPane.add(getScrollResponsables(), null);
			jContentPane.add(numeroHoras, null);
			jContentPane.add(getScrollNombre(), null);
			jContentPane.add(estadoLabel, null);
			jContentPane.add(getEstado(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes padre	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getPadre() {
		if (padre == null) {
			padre = new JComboBox(tareas);
			padre.setBounds(new Rectangle(131, 11, 179, 22));
		}
		return padre;
	}

	/**
	 * This method initializes nombre	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getNombre() {
		if (nombre == null) {
			nombre = new JTextArea();
			nombre.setLineWrap(true);
		}
		return nombre;
	}

	/**
	 * This method initializes tiempo	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getTiempo() {
		if (tiempo == null) {
			tiempo = new JSlider();
			tiempo.setBounds(new Rectangle(125, 153, 189, 16));
			tiempo.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent arg0)
				{
					numeroHoras.setText(tiempo.getValue() + " horas");
				}				
			});
			tiempo.setValue(1);
			tiempo.setMinimum(1);
			tiempo.setMaximum(100);
		}
		return tiempo;
	}

	/**
	 * This method initializes responsables	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getResponsables() {
		if (responsables == null) {
			responsables = new JList();
		}
		return responsables;
	}

	/**
	 * This method initializes agregarResponsable	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAgregarResponsable() {
		if (agregarResponsable == null) {
			agregarResponsable = new JButton();
			agregarResponsable.setBounds(new Rectangle(314, 192, 41, 26));
			agregarResponsable.setText("+");
			final Component este = this;
			agregarResponsable.addActionListener(new ActionListener()
												{
													public void actionPerformed(ActionEvent arg0) 
													{
														Integrante integrante = (Integrante) JOptionPane.showInputDialog(null, "Seleccione el integrante", "Seleccion", JOptionPane.QUESTION_MESSAGE, null, integrantes, integrantes[0]);
														este.requestFocus();
														if(integrante == null || integrantesAgregados.contains(integrante))
															return;
														integrantesAgregados.add(integrante);
														responsables.setListData(integrantesAgregados);
													}
				
												});
		}
		return agregarResponsable;
	}

	/**
	 * This method initializes requsitos	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getRequsitos() {
		if (requsitos == null) {
			requsitos = new JList();
		}
		return requsitos;
	}

	/**
	 * This method initializes agregarRequsito	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAgregarRequsito() {
		if (agregarRequsito == null) {
			agregarRequsito = new JButton();
			agregarRequsito.setBounds(new Rectangle(314, 284, 41, 26));
			agregarRequsito.setText("+");
			final Component este = this;
			agregarRequsito.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					Tarea tarea = (Tarea) JOptionPane.showInputDialog(este, "Seleccione el requisito", "Seleccion", JOptionPane.QUESTION_MESSAGE, null, tareas, tareas[0]);
					este.requestFocus();
					String idTarea = tarea.toString().split(" ")[0];
					if(tareasAgregadas.contains(idTarea))
						return;
					tareasAgregadas.add(tarea);
					tareasAgregadasId.add(idTarea);
					requsitos.setListData(tareasAgregadasId);	
				}

			});
		}
		return agregarRequsito;
	}

	/**
	 * This method initializes agregar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAgregar() {
		if (agregar == null) {
			agregar = new JButton();
			agregar.setBounds(new Rectangle(91, 416, 110, 34));
			agregar.setText("Agregar");
			final JDialog este = this;
			agregar.addActionListener(new ActionListener()
									  {
										@SuppressWarnings("unchecked")
										public void actionPerformed(ActionEvent arg0) 
										{
											Tarea nueva = new Tarea(framePadre.idProyecto, 0, nombre.getText(), (Tarea) padre.getSelectedItem(), tiempo.getValue(), (Estado) estado.getSelectedItem(), 0, new ArrayList(tareasAgregadas), new ArrayList(integrantesAgregados));
											if(modificando != null)
											{
												nueva.id = modificando.id;
												nueva.tiempoTrabajado = modificando.tiempoTrabajado;
												framePadre.modificarTarea(nueva);
											}
											else
											{
												framePadre.agregarTarea(nueva);
											}
											
											este.setVisible(false);
											este.dispose();
										}
									  });
		}
		return agregar;
	}

	/**
	 * This method initializes cancelar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelar() {
		final JDialog este = this;
		if (cancelar == null) {
			cancelar = new JButton();
			cancelar.setBounds(new Rectangle(219, 416, 110, 34));
			cancelar.setText("Cancelar");
			cancelar.addActionListener(new ActionListener()
									   {
											public void actionPerformed(ActionEvent arg0) 
											{
												este.setVisible(false);
												este.dispose();
											}
									   });
		}
		return cancelar;
	}

	/**
	 * This method initializes scrollRequisitos	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollRequisitos() {
		if (scrollRequisitos == null) {
			scrollRequisitos = new JScrollPane();
			scrollRequisitos.setBounds(new Rectangle(132, 284, 176, 73));
			scrollRequisitos.setViewportView(getRequsitos());
		}
		return scrollRequisitos;
	}

	/**
	 * This method initializes scrollResponsables	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollResponsables() {
		if (scrollResponsables == null) {
			scrollResponsables = new JScrollPane();
			scrollResponsables.setBounds(new Rectangle(132, 192, 176, 73));
			scrollResponsables.setViewportView(getResponsables());
		}
		return scrollResponsables;
	}

	/**
	 * This method initializes scrollNombre	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollNombre() {
		if (scrollNombre == null) {
			scrollNombre = new JScrollPane();
			scrollNombre.setBounds(new Rectangle(132, 53, 178, 72));
			scrollNombre.setViewportView(getNombre());
		}
		return scrollNombre;
	}

	/**
	 * This method initializes estado	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getEstado() {
		if (estado == null) {
			estado = new JComboBox();
			estado.setBounds(new Rectangle(131, 380, 179, 22));
			for(Estado e : Estado.values())
				estado.addItem(e);
		}
		return estado;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"

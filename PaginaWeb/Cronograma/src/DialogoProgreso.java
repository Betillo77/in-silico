import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DialogoProgreso extends JDialog {

	private static final long serialVersionUID = 3213290032144325L;
	private JPanel jContentPane = null;
	private JList listaProgreso = null;
	private JButton agregar = null;
	private JButton eliminar = null;
	private JButton reporte = null;
	private final CronogramaGrafico padre;
	private final Tarea tarea;
	private final Vector <Registro> progreso;
	private final Integrante[] integrantes;
	private JScrollPane scrollListaProgreso = null;

	/**
	 * @param owner
	 */
	public DialogoProgreso(CronogramaGrafico p, Integrante[] i, Vector <Registro> prog, Tarea t) {
		initialize();
		padre = p;
		tarea = t;
		integrantes = i;
		progreso = prog;
		listaProgreso.setListData(progreso);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(337, 343);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getAgregar(), null);
			jContentPane.add(getEliminar(), null);
			jContentPane.add(getReporte(), null);
			jContentPane.add(getScrollListaProgreso(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes listaProgreso	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getListaProgreso() {
		if (listaProgreso == null) {
			listaProgreso = new JList();
		}
		return listaProgreso;
	}

	/**
	 * This method initializes agregar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAgregar() {
		if (agregar == null) {
			agregar = new JButton();
			agregar.setBounds(new Rectangle(217, 26, 84, 38));
			agregar.setText("Agregar");
			final Component este = this;
			agregar.addActionListener(new ActionListener() 
									 {
				
										@Override
										public void actionPerformed(ActionEvent e) {
											Integrante integrante = (Integrante) JOptionPane.showInputDialog(null, "Seleccione el integrante", "Integrante", JOptionPane.QUESTION_MESSAGE, null, integrantes, integrantes[0]);
											este.requestFocus();
											String lectura = JOptionPane.showInputDialog(null, "Ingrese la fecha en formato DD-MM-AA", "Fecha");
											este.requestFocus();
											Calendar fecha = Calendar.getInstance();
											try
											{
												String[] arregloFecha = lectura.split("-");
												int dia = Integer.parseInt(arregloFecha[0]);
												int mes = Integer.parseInt(arregloFecha[1]);
												int a = Integer.parseInt(arregloFecha[2]);
												if(dia < 1 || dia > 31 || mes < 1 || mes > 12 || a < 0 || a > 99)
													throw(new Exception(""));
												a += 2000;
												fecha.clear();
												fecha.set(a, mes - 1, dia);
												fecha.setLenient(false);
											}
											catch(Exception ex)
											{
												JOptionPane.showMessageDialog(null, "Fecha invalida");
												este.requestFocus();
												return;
											}
											Object[] hastaCien = new Object[100];
											for(int i = 0; i < 100; i++)
												hastaCien[i] = i + 1;
											int horas = (Integer) JOptionPane.showInputDialog(null, "Seleccione el integrante", "Integrante", JOptionPane.QUESTION_MESSAGE, null, hastaCien, hastaCien[0]);
											este.requestFocus();
											Registro nuevo = new Registro(integrante, fecha, horas);
											//TODO Conexion con BD
											progreso.add(nuevo);
											listaProgreso.setListData(progreso);
										}
									});
		}
		return agregar;
	}

	/**
	 * This method initializes eliminar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getEliminar() {
		if (eliminar == null) {
			eliminar = new JButton();
			eliminar.setBounds(new Rectangle(217, 130, 84, 38));
			eliminar.setText("Eliminar");
			final Component este = this;
			eliminar.addActionListener(new ActionListener() 
			 {
				@Override
				public void actionPerformed(ActionEvent e) {
					int si = JOptionPane.showConfirmDialog(null, "En verdad quiere eliminar: " + listaProgreso.getSelectedValue(), "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
					if(si == JOptionPane.YES_OPTION)
					{
						//TODO Conexion con BD
						progreso.remove(listaProgreso.getSelectedValue());
						listaProgreso.setListData(progreso);
					}
					este.requestFocus();
				}
			 });
		}
		return eliminar;
	}

	/**
	 * This method initializes reporte	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getReporte() {
		if (reporte == null) {
			reporte = new JButton();
			reporte.setBounds(new Rectangle(217, 234, 84, 38));
			reporte.setText("Reporte");
			reporte.addActionListener(new ActionListener() 
			 {
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO Abrir la direccion PHP
					System.out.println(tarea.id + padre.getClass().toString());
				}
			 });
		}
		return reporte;
	}

	/**
	 * This method initializes scrollListaProgreso	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getScrollListaProgreso() {
		if (scrollListaProgreso == null) {
			scrollListaProgreso = new JScrollPane();
			scrollListaProgreso.setBounds(new Rectangle(4, 4, 199, 297));
			scrollListaProgreso.setViewportView(getListaProgreso());
		}
		return scrollListaProgreso;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

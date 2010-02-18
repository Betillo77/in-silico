package vista;
/*
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *   
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution. 
 *   
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.  
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,   
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS 
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;

import modelo.AdaptadorArbolTabla;
import modelo.EditorCeldaAbstracto;
import modelo.Integrante;
import modelo.ListenerArbolTabla;
import modelo.ModeloArbolTabla;
import modelo.Registro;
import modelo.Tarea;
import conexion.ConexionBaseDatos;

/**
 * This example shows how to create a simple JTreeTable component, 
 * by using a JTree as a renderer (and editor) for the cells in a 
 * particular column in the JTable.  
 *
 * @version %I% %G%
 *
 * @author Philip Milne
 * @author Scott Violet
 */

public class ArbolTabla extends JTable {
	
	private static final long serialVersionUID = -3647319769489490826L;
	
	protected TreeTableCellRenderer tree;
	
	public JPopupMenu popupMenu = new JPopupMenu();
	CronogramaGrafico padre;
	ModeloArbolTabla modeloArbolTabla;

	Integrante[] integrantes;
	Tarea[] tareas;
	
    public ArbolTabla(CronogramaGrafico p, ModeloArbolTabla treeTableModel, Integrante[] i, Tarea[] t) {
    	super();
    	modeloArbolTabla = treeTableModel;
    	padre = p;
    	integrantes = i;
    	tareas = t;
    	tree = new TreeTableCellRenderer(treeTableModel); 
    	DefaultTreeCellRenderer cr = new DefaultTreeCellRenderer();
    	//	cr.setLeafIcon(new ImageIcon("prueba.jpg")); TODO
    	tree.setCellRenderer(cr);
    	super.setModel(new AdaptadorArbolTabla(treeTableModel, tree));
    	getTableHeader().getColumnModel().getColumn(0).setMinWidth(305);
    	getTableHeader().getColumnModel().getColumn(0).setMaxWidth(305);
    	getTableHeader().getColumnModel().getColumn(1).setMinWidth(35);
    	getTableHeader().getColumnModel().getColumn(1).setMaxWidth(35);
    	getTableHeader().getColumnModel().getColumn(2).setMinWidth(35);
    	getTableHeader().getColumnModel().getColumn(2).setMaxWidth(35);
    	getTableHeader().getColumnModel().getColumn(3).setMinWidth(70);
    	getTableHeader().getColumnModel().getColumn(3).setMaxWidth(70);
    	getTableHeader().getColumnModel().getColumn(4).setMinWidth(176);
    	getTableHeader().getColumnModel().getColumn(4).setMaxWidth(176);
    	getTableHeader().getColumnModel().getColumn(5).setMinWidth(176);
    	getTableHeader().getColumnModel().getColumn(5).setMaxWidth(176);
    	tree.setSelectionModel(new DefaultTreeSelectionModel() { 
    		
    		private static final long serialVersionUID = 15334879775434L; 		
    		{
    			setSelectionModel(listSelectionModel); 
    		} 
    	}); 
	    JMenuItem nuevo = new JMenuItem("Nuevo");
	    JMenuItem eliminar = new JMenuItem("Eliminar");
	    JMenuItem modificar = new JMenuItem("Modificar");
	    JMenuItem verProgreso = new JMenuItem("Ver progreso");
	    ListenerArbolTabla lat = new ListenerArbolTabla(this);
	    nuevo.addActionListener(lat);
	    eliminar.addActionListener(lat);
	    modificar.addActionListener(lat);
	    verProgreso.addActionListener(lat);
	    popupMenu.add(nuevo);
	    popupMenu.add(eliminar);
	    popupMenu.add(modificar);
	    popupMenu.add(verProgreso);
	    MouseListener popupListener = lat;
	    addMouseListener(popupListener);
	    getTableHeader().addMouseListener(popupListener); 
	    tree.setRowHeight(getRowHeight());
		setDefaultRenderer(ModeloArbolTabla.class, tree); 
		setDefaultEditor(ModeloArbolTabla.class, new TreeTableCellEditor());	
		setShowGrid(false);
		setIntercellSpacing(new Dimension(0, 0));
		DefaultTableCellRenderer rendererCentrado = new DefaultTableCellRenderer();
		rendererCentrado.setHorizontalAlignment(SwingConstants.CENTER);
		getColumn("HPre").setCellRenderer(rendererCentrado);
		getColumn("HTra").setCellRenderer(rendererCentrado);
		getColumn("Estado").setCellRenderer(rendererCentrado);
		getColumn("Pre-requisitos").setCellRenderer(rendererCentrado);
		getColumn("Responsables").setCellRenderer(rendererCentrado);
    }
    
    public int getEditingRow() {
        return (getColumnClass(editingColumn) == ModeloArbolTabla.class) ? -1 : editingRow;  
    }

    public class TreeTableCellRenderer extends JTree implements TableCellRenderer {
    	
		private static final long serialVersionUID = -4402515413278666263L;
		
		protected int visibleRow;
	   
		public TreeTableCellRenderer(TreeModel model) { 
		    super(model); 
		}
	
		public void setBounds(int x, int y, int w, int h) {
		    super.setBounds(x, 0, w, ArbolTabla.this.getHeight());
		}
	
		public void paint(Graphics g) {
		    g.translate(0, -visibleRow * getRowHeight());
		    super.paint(g);
		}
	
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		    if(isSelected)
		    	setBackground(table.getSelectionBackground());
		    else
		    	setBackground(table.getBackground());
		    visibleRow = row;
		    return this;
		}
    }

    public class TreeTableCellEditor extends EditorCeldaAbstracto implements TableCellEditor {
    	
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int r, int c) {
		    return tree;
		}
    }
    
    public boolean editCellAt(int row, int column, EventObject e) {
    	if(((Tarea) getValueAt(row, 0)).hijos.size() == 0 && column == 0)
    	{
    		selectAll();	
    		return super.editCellAt(row, column, e);
    	}
    	selectAll();
    	return super.editCellAt(row, column, e);
    }

    public void nuevo() {
    	new DialogoTarea(padre, integrantes, tareas).setVisible(true);
    }
    
    public void eliminar(int id) {
    	if(id != 0)
    	{
	    	int si = JOptionPane.showConfirmDialog(padre, "En verdad quiere eliminar la tarea con id: " + id, "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
	    	if(si == JOptionPane.YES_OPTION)
	    		padre.eliminarTarea(id);
    	}
    }

	public void modificar(int idCambio) {
		if(idCambio == 0)
			return;
		Tarea aModificar = null;
		for(Tarea t : tareas)
			if(t.id == idCambio)
			{
				aModificar = t;
				break;
			}
		new DialogoTarea(padre, integrantes, tareas, aModificar).setVisible(true);
	}

	public void verProgresoTarea(int id) {
		try
		{
			ArrayList <Registro> registros = ConexionBaseDatos.darRegistros(id, integrantes);
			Tarea aVer = null;
			for(Tarea t : tareas)
				if(t.id == id)
				{
					aVer = t;
					break;
				}
			new DialogoProgreso(padre, integrantes, new Vector <Registro> (registros), aVer).setVisible(true);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error de conexión con el servidor");
			System.exit(0);
		}
	}

}
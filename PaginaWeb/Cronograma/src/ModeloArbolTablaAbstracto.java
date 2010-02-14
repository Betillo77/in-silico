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

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

 
/**
 * An abstract implementation of the TreeTableModel interface, handling 
 * the list of listeners. 
 *
 * @version %I% %G%
 *
 * @author Philip Milne
 */

public abstract class ModeloArbolTablaAbstracto implements ModeloArbolTabla {
	
    protected Object root;     
    protected EventListenerList listenerList = new EventListenerList();
  
    public ModeloArbolTablaAbstracto(Object root) {
        this.root = root; 
    }

    public Object getRoot() {
        return root;
    }

    public boolean isLeaf(Object node) {
        return ((Tarea) node).hijos.isEmpty(); 
    }

    public void valueForPathChanged(TreePath path, Object newValue) {}

    public int getIndexOfChild(Object parent, Object child) {
    	ArrayList<Tarea> hijos = ((Tarea) parent).hijos;
    	return hijos.indexOf(child);
    }

    public void addTreeModelListener(TreeModelListener l) {
        listenerList.add(TreeModelListener.class, l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }

    protected void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == TreeModelListener.class) {
                ((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
            }          
        }
    }

    protected void fireTreeNodesInserted(Object source, Object[] path, int[] childIndices, Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == TreeModelListener.class) {
                ((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
            }          
        }
    }
    
    protected void fireTreeNodesRemoved(Object source, Object[] path, int[] childIndices, Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == TreeModelListener.class) {
                ((TreeModelListener) listeners[i + 1]).treeNodesRemoved(e);
            }          
        }
    }
    
    protected void fireTreeStructureChanged(Object source, Object[] path, int[] childIndices, Object[] children) {
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = new TreeModelEvent(source, path, childIndices, children);
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == TreeModelListener.class) {
            	((TreeModelListener)listeners[i + 1]).treeStructureChanged(e);
            }          
        }
    }
    
    public void cambiarModelo()
    {
        Object[] listeners = listenerList.getListenerList();
        Object[] camino = {root};
        TreeModelEvent e = new TreeModelEvent(root, camino);
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == TreeModelListener.class) {
            	((TreeModelListener)listeners[i + 1]).treeStructureChanged(e);
            }          
        }
    }
    
    

    public Class<?> getColumnClass(int column) 
    {
    	return Object.class; 
    }

    public boolean isCellEditable(Object node, int column) { 
    	return getColumnClass(column) == ModeloArbolTabla.class; 
    }

    public void setValueAt(Object aValue, Object node, int column) {}
}


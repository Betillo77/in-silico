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

/**
 * FileSystemModel is a TreeTableModel representing a hierarchical file 
 * system. Nodes in the FileSystemModel are FileNodes which, when they 
 * are directory nodes, cache their children to avoid repeatedly querying 
 * the real file system. 
 * 
 * @version %I% %G%
 *
 * @author Philip Milne
 * @author Scott Violet
 */

public class ModeloLogicoArbol extends ModeloArbolTablaAbstracto implements ModeloArbolTabla {

    // Names of the columns.
    static protected String[]  cNames = {"Nombre", "Horas presupuestadas", "Horas trabajadas", "Estado", "Pre-requisitos", "Responsables"};

    // Types of the columns.
    static protected Class<?>[]  cTypes = {ModeloArbolTabla.class, Integer.class, Integer.class, Estado.class, String.class, String.class};

    public ModeloLogicoArbol(Tarea inicial) { 
    	super(inicial); 
    }
    
    public int getChildCount(Object node) { 
    	
    	return ((Tarea) node).hijos.size();
    }
    

    public Object getChild(Object node, int i) { 
    	return ((Tarea) node).hijos.get(i);
    }
 
    public boolean isLeaf(Object node) { 
    	return ((Tarea) node).hijos.isEmpty(); 
    }

    public int getColumnCount() {
    	return cNames.length;
    }

    public String getColumnName(int column) {
    	return cNames[column];
    }

    public Class<?> getColumnClass(int column) {
    	return cTypes[column];
    }
 
    public Object getValueAt(Object node, int column) {
		Tarea esta = (Tarea) node; 
	    switch(column) {
		    case 0:	return esta;
		    case 1: return esta.tiempo;
		    case 2: return esta.tiempoTrabajado;
		    case 3: return esta.estado;
		    case 4: String requisitos = "";
		    		for(Tarea pre : esta.preRequisitos)
		    		{
		    			requisitos += pre.id + " ";
		    		}
		    		return requisitos.substring(0, requisitos.length() - 1 >= 0 ? requisitos.length() - 1 : 0);
		    case 5: String responsables = "";
					for(Integrante inte : esta.responsables)
					{	
						responsables += inte.nombre.split(" ")[0] + " ";
					}
					return responsables.substring(0, responsables.length() - 1 >= 0 ? responsables.length() - 1 : 0);
	    }
	    return ""; 
    }
}
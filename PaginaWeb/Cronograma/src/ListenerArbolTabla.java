import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListenerArbolTabla extends MouseAdapter implements ActionListener {
	ArbolTabla arbolTabla;

	ListenerArbolTabla(ArbolTabla at) {
		arbolTabla = at;
	}

	public void actionPerformed(ActionEvent e) {
		int idCambio = ((Tarea) ((AdaptadorArbolTabla) arbolTabla.getModel()).getValueAt(arbolTabla.getSelectedRow(), 0)).id;
		if(e.getActionCommand().equals("Nuevo"))
		{
			arbolTabla.nuevo();
		}
		else if(e.getActionCommand().equals("Eliminar"))
		{
			arbolTabla.eliminar(idCambio);
		}
		else if(e.getActionCommand().equals("Modificar"))
		{
			arbolTabla.modificar(idCambio);
		}
		else
		{
			arbolTabla.verProgresoTarea(idCambio);
		}
	}
	
    public void mousePressed(MouseEvent e) {
        showPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
    	showPopup(e);
    }

    private void showPopup(MouseEvent e) {
    	if (e.isPopupTrigger()) 
        {
          arbolTabla.popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}

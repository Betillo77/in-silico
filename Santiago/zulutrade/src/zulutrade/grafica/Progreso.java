package zulutrade.grafica;

import com.trolltech.qt.gui.*;

public class Progreso extends QDialog {

    Ui_Progreso ui = new Ui_Progreso();

    public Progreso() 
    {
        ui.setupUi(this);
        
    }
    
    public void cambiarActual(String actual)
    {
    	ui.actual.setText(actual);
    	ui.actual.repaint();
    }
    
    public void cambiarProgreso(int porcentaje)
    {
    	ui.progressBar.setValue(porcentaje);
    }
}

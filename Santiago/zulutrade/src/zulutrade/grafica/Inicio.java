package zulutrade.grafica;


import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import zulutrade.analizador.Proveedores;

import com.trolltech.qt.gui.*;


public class Inicio extends QMainWindow 
{

    Ui_Inicio ui = new Ui_Inicio();
    Proveedores proveedores;
    Proveedores potenciales;

    public static void main(String[] args)
    {
        QApplication.initialize(args);

        Inicio inicio = new Inicio();
        inicio.show();

        QApplication.exec();
	}
    
    public Inicio() 
    {
        ui.setupUi(this);
		try
		{
			File archivo = new File("proveedores.data");
			if(!archivo.exists())
			{
				proveedores = new Proveedores();
				potenciales = new Proveedores();
			}
			else
			{	
				FileInputStream fis = new FileInputStream(archivo);
				ObjectInputStream ois = new ObjectInputStream(fis);
				proveedores = (Proveedores)ois.readObject();
				potenciales = (Proveedores)ois.readObject();
				ois.close();
				fis.close();
			}
		}
		catch(Exception e)
		{
			QMessageBox.information(null, "Error", "Se produjo un error en la lectura de los archivos.");
			this.dispose();
		}
		ui.proveedores.clicked.connect(this, "proveedores()");
		ui.potenciales.clicked.connect(this, "potenciales()");
    }
        
    public void proveedores()
    {
    	ProveedoresG proveedoresG = new ProveedoresG(proveedores, false, this);
    	this.hide();
    	proveedoresG.show();
    }
    
    public void potenciales()
    {
    	ProveedoresG proveedoresG = new ProveedoresG(potenciales, true, this);
    	this.hide();
    	proveedoresG.show();
    }
    
    public Proveedores darProveedores()
    {
    	return proveedores;
    }
    
    public Proveedores darPotenciales()
    {
    	return potenciales;
    }   
}

package zulutrade.grafica;


import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import zulutrade.analizador.Proveedor;
import zulutrade.analizador.Proveedores;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;


public class ProveedoresG extends QWidget 
{
    private TablaProveedores tablaProveedores;
    private Inicio padre;
    private boolean potenciales;
    
    public ProveedoresG(Proveedores proveedores, boolean potenciales, Inicio padre)
    {
    	this.padre = padre;
    	this.potenciales = potenciales;
    	actualizar(proveedores, true);
    	if(potenciales)
    	{
    		this.setWindowTitle("Potenciales");
    	}
    	else
    	{
    		this.setWindowTitle("Proveedores");
    	}
    }
    
    public void actualizar(Proveedores proveedores, boolean inicio)
    {
    	if(!inicio)
    	{
	    	for(QObject s : this.children())
	    	{
	    		if(!s.objectName().equals("cargar") && !s.objectName().equals("actual") && !s.objectName().equals("eliminar"))
	    		{
	    			s.dispose();
	    		}
	    		else
	    		{
	    			((QWidget)(s)).hide();
	    		}
    		}

    	}
    	this.tablaProveedores = new TablaProveedores(this, proveedores, potenciales);
    	setupUi(this.tablaProveedores);
        resize(this.tablaProveedores.size());
        this.resize(this.tablaProveedores.width() + 18, this.tablaProveedores.height() + 50);
        this.tablaProveedores.setGeometry(0, 50, this.tablaProveedores.width(), this.tablaProveedores.height());
        if(!inicio)
        {
    	 	if(potenciales)
        	{
        		this.setWindowTitle("Potenciales");
        	}
        	else
        	{
        		this.setWindowTitle("Proveedores");
        	}
        }
    }
    
    public void cargar()
    {
    	String rutaD;
    	if(potenciales)
    	{
    		rutaD = "Potenciales/";
    	}
    	else
    	{
        	rutaD = "Proveedores/";

    	}
    	File directorio = new File(rutaD);
    	File[] archivos =  directorio.listFiles();
    	this.tablaProveedores.darProveedores().eliminarProveedores();
    	QProgressDialog qpd = new QProgressDialog("Creando proveedores", "Abortar", 0, archivos.length, null);
		qpd.show();
		qpd.setWindowModality(Qt.WindowModality.WindowModal);
		qpd.setWindowTitle("Progreso");
		qpd.setValue(0);
    	int numeroArchivos = 0;
    	for(File archivo : archivos)
    	{
    		String ruta = archivo.getPath();
    		String nombreA = archivo.getName();
    		String[] nombreB = nombreA.split(".xls");
    		String nombre = nombreB[0];
    		this.tablaProveedores.darProveedores().agregarProveedor(Proveedor.crear_proveedor(ruta, nombre));
    		numeroArchivos++;
    		qpd.setValue(numeroArchivos);
    	}
    	qpd.hide();
    	qpd.dispose();
    	this.tablaProveedores.darProveedores().generarDuplicados();
        try
        {
        	File archivo = new File("proveedores.data");
        	FileOutputStream fos = new FileOutputStream(archivo);
        	ObjectOutputStream oos = new ObjectOutputStream(fos);
        	oos.writeObject(padre.darProveedores());
        	oos.writeObject(padre.darPotenciales());
        	oos.close();
        	fos.close();
        }
        catch(Exception e)
        {
        	QMessageBox.information(null, "Error", "Se produjo un error en la escritura de los archivos.");
			this.dispose();
        }
    	actualizar(this.tablaProveedores.darProveedores(), false);
    }
    
    public void eliminar()
    {
    	Vector <String> nombres = new Vector <String>();
    	String[] nombresA = this.tablaProveedores.darProveedores().darLista()[0];
    	for(String s : nombresA)
    	{
    		nombres.add(s);
    	}
    	String nombre = QInputDialog.getItem(this, "Eliminar", "Seleccione el proveedor a eliminar:", nombres, 0, false);
    	this.tablaProveedores.darProveedores().eliminarProveedor(nombre);
    	actualizar(this.tablaProveedores.darProveedores(), false);
    }
    
    public void volver()
    {
    	this.hide();
    	padre.show();
    }

    public void setupUi(QWidget q)
    {
        this.setObjectName("ProveedoresGr");
        this.setWindowModality(com.trolltech.qt.core.Qt.WindowModality.NonModal);
        QVBoxLayout verticalLayout_2 = new QVBoxLayout(this);
        verticalLayout_2.setObjectName("verticalLayout_2");
        QHBoxLayout hboxLayout = new QHBoxLayout();
        hboxLayout.setObjectName("hboxLayout");
        QPushButton cargar = new QPushButton(this);
        cargar.setObjectName("cargar");
        cargar.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        hboxLayout.addWidget(cargar);
        QPushButton eliminar = new QPushButton(this);
        eliminar.setObjectName("eliminar");
        eliminar.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        hboxLayout.addWidget(eliminar);
        QPushButton actual = new QPushButton(this);
        actual.setObjectName("actual");
        actual.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        hboxLayout.addWidget(actual); 
        verticalLayout_2.addLayout(hboxLayout);
        verticalLayout_2.addWidget(q);
        cargar.clicked.connect(this, "cargar()");
        eliminar.clicked.connect(this, "eliminar()");
        actual.clicked.connect(this, "volver()");
        this.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("ProveedoresGr", "Form"));
        cargar.setText(com.trolltech.qt.core.QCoreApplication.translate("ProveedoresGr", "Cargar"));
        eliminar.setText(com.trolltech.qt.core.QCoreApplication.translate("ProveedoresGr", "Eliminar"));
        actual.setText(com.trolltech.qt.core.QCoreApplication.translate("ProveedoresGr", "Volver"));
    } 
}

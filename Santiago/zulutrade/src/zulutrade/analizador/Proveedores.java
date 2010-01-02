package zulutrade.analizador;


import java.io.Serializable;
import java.util.ArrayList;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QProgressDialog;


public class Proveedores implements Serializable
{
	private static final long serialVersionUID = 145543L;
	private ArrayList <Proveedor> proveedores = new ArrayList <Proveedor> ();

	public Proveedores(ArrayList <Proveedor> proveedores)
	{
		this.proveedores = proveedores;
	}
	
	public Proveedores()
	{
	}
	
	public void agregarProveedor(Proveedor proveedor)
	{
		proveedores.add(proveedor);
	}
	
	public void eliminarProveedor(String nombre)
	{
		for(Proveedor actual : proveedores)
		{
			if(actual.darNombre().equals(nombre))
			{
				proveedores.remove(actual);
				return;
			}
		}
	}
	
	public Proveedor darProveedor(String nombre)
	{
		for(Proveedor actual : proveedores)
		{
			if(actual.darNombre().equals(nombre))
			{
				return actual;
			}
		}
		return null;
	}
	
	public void generarDuplicados()
	{
		QProgressDialog qpd = new QProgressDialog("Generando duplicados", "Abortar", 0, proveedores.size(), null);
		qpd.show();
		qpd.setWindowModality(Qt.WindowModality.WindowModal);
		qpd.setWindowTitle("Progreso");
		int numeroProveedores = 0;
		qpd.setValue(0);
		for(Proveedor proveedor : proveedores)
		{
			proveedor.generarDuplicados();
			numeroProveedores++;
			qpd.setValue(numeroProveedores);
		}
		qpd.hide();
		qpd.dispose();
	}
	
	public String[][] darLista()
	{
		String[][] lista = new String [10][proveedores.size()];
		for(int i = 0; i < proveedores.size(); i++)
		{
			Proveedor actual = proveedores.get(i);
			lista[0][i] = actual.darNombre();
			lista[1][i] = actual.darNumero();
			lista[2][i] = actual.darPromedioAlto();
			lista[3][i] = actual.darPromedioBajo();
			lista[4][i] = actual.darPromedioCierre();
			lista[5][i] = actual.darMejorRiesgoBeneficioSemanas(0);
			lista[6][i] = actual.darMejorRiesgoBeneficioSemanas(1);
			lista[7][i] = actual.darMejorRiesgoBeneficioSemanas(2);
			lista[8][i] = actual.darMejorRiesgoBeneficioSemanas(3);
			lista[9][i] = actual.darMejorRiesgoBeneficioSemanas(4);
		}
		return lista;	
	}

	public void eliminarProveedores() 
	{
		proveedores = new ArrayList <Proveedor> ();
	}
	
	public int darTamano()
	{
		return proveedores.size();
	}
}

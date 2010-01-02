package zulutrade.grafica;


import java.util.ArrayList;

import zulutrade.analizador.Proveedor;
import zulutrade.analizador.Proveedores;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QStandardItem;
import com.trolltech.qt.gui.QStandardItemModel;
import com.trolltech.qt.gui.QTableView;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;


public class TablaProveedores extends QWidget
{
	private QStandardItemModel modelo;
	private Proveedores proveedores;
	private boolean potenciales;
	private ModeloOrdenar modeloOrdenado; 
	
	
	public TablaProveedores(QWidget padre, Proveedores proveedores, boolean potenciales)
	{
		super(padre);
		this.setWindowTitle("Proveedores");
		int numero = proveedores.darTamano();
		if(numero < 10)
		{
			resize(689, numero * 30 + 45);
		}
		else if (numero < 21)
		{
			resize(696, numero * 30 + 45);
		}
		else
		{
			resize(713, 648);
		}
		this.proveedores = proveedores;
		this.potenciales = potenciales;
	    crearTabla();
	}
	
	public Proveedores darProveedores()
	{
		return proveedores;
	}
	
	public boolean darPotenciales()
	{
		return potenciales;
	}
	
	public void crearTabla()
	{
        QTableView tabla = new QTableView();
        crearModelo();
        tabla.setModel(modeloOrdenado);
        tabla.horizontalHeader().resizeSection(0, 200);
        tabla.horizontalHeader().resizeSection(1, 50);
        tabla.horizontalHeader().resizeSection(2, 50);
        tabla.horizontalHeader().resizeSection(3, 50);
        tabla.horizontalHeader().resizeSection(4, 50);
        tabla.horizontalHeader().resizeSection(5, 50);
        tabla.horizontalHeader().resizeSection(6, 50);
        tabla.horizontalHeader().resizeSection(7, 50);
        tabla.horizontalHeader().resizeSection(8, 50);
        tabla.horizontalHeader().resizeSection(9, 50);
        tabla.doubleClicked.connect(this, "proveedor(QModelIndex)");
        tabla.setSortingEnabled(true);
        QVBoxLayout layout = new QVBoxLayout(this);
        layout.addWidget(tabla);
        setLayout(layout);
	}

	public void crearModelo()
	{
		String[][] lista;
		lista = proveedores.darLista();
		modelo = new QStandardItemModel(lista[0].length, 10, this);
		modeloOrdenado = new ModeloOrdenar(this);
		modeloOrdenado.setSourceModel(modelo);
		modeloOrdenado.setDynamicSortFilter(true);
		modelo.setHeaderData(0, Qt.Orientation.Horizontal,"Nombre");
        modelo.setHeaderData(1, Qt.Orientation.Horizontal,"N�");
        modelo.setHeaderData(2, Qt.Orientation.Horizontal, "Alto");
        modelo.setHeaderData(3, Qt.Orientation.Horizontal, "Bajo");
        modelo.setHeaderData(4, Qt.Orientation.Horizontal, "Cierre");
        modelo.setHeaderData(5, Qt.Orientation.Horizontal, "1");
        modelo.setHeaderData(6, Qt.Orientation.Horizontal, "2");
        modelo.setHeaderData(7, Qt.Orientation.Horizontal, "4");
        modelo.setHeaderData(8, Qt.Orientation.Horizontal, "8");
        modelo.setHeaderData(9, Qt.Orientation.Horizontal, "inicio");
		for(int i = 0; i < lista[0].length; i++)
		{
			QStandardItem nombre = new QStandardItem();
			QStandardItem numero = new QStandardItem();
			QStandardItem alto = new QStandardItem();
			QStandardItem bajo = new QStandardItem();
			QStandardItem cierre = new QStandardItem();
			QStandardItem unaSemana = new QStandardItem();
			QStandardItem dosSemanas = new QStandardItem();
			QStandardItem cuatroSemanas = new QStandardItem();
			QStandardItem ochoSemanas = new QStandardItem();
			QStandardItem inicio = new QStandardItem();
			nombre.setText(lista[0][i]);
			numero.setText(lista[1][i]);
			alto.setText(lista[2][i]);
			bajo.setText(lista[3][i]);
			cierre.setText(lista[4][i]);
			unaSemana.setText(lista[5][i]);
			dosSemanas.setText(lista[6][i]);
			cuatroSemanas.setText(lista[7][i]);
			ochoSemanas.setText(lista[8][i]);
			inicio.setText(lista[9][i]);
			nombre.setEditable(false);
			numero.setEditable(false);
			alto.setEditable(false);
			bajo.setEditable(false);
			cierre.setEditable(false);
			unaSemana.setEditable(false);
			dosSemanas.setEditable(false);
			cuatroSemanas.setEditable(false);
			ochoSemanas.setEditable(false);
			inicio.setEditable(false);
			numero.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			alto.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			bajo.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			cierre.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			unaSemana.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			dosSemanas.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			cuatroSemanas.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			ochoSemanas.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			inicio.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			ArrayList <QStandardItem> fila = new ArrayList <QStandardItem> ();
			fila.add(nombre);
			fila.add(numero);
			fila.add(alto);
			fila.add(bajo);
			fila.add(cierre);
			fila.add(unaSemana);
			fila.add(dosSemanas);
			fila.add(cuatroSemanas);
			fila.add(ochoSemanas);
			fila.add(inicio);
			modelo.insertRow(i, fila);
		}
		modelo.setRowCount(lista[0].length);
	}
	
	public void proveedor(QModelIndex indice)
	{
		String nombre = (String) modeloOrdenado.data(indice.row(), 0);
		Proveedor seleccionado;
		seleccionado = proveedores.darProveedor(nombre);
		ProveedorG Pg = new ProveedorG(null, seleccionado);
		Pg.setWindowTitle(seleccionado.darNombre());
		Pg.show();	
	}
}
package zulutrade.grafica;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import zulutrade.analizador.ProveedorDuplicado;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;


public class AnalisisG extends QWidget 
{

    private Ui_AnalisisG ui = new Ui_AnalisisG();
    private QStandardItemModel modelo;
    private ProveedorDuplicado proveedor;
    private byte imgPNG[];
	private int lotes;

    public AnalisisG(ProveedorDuplicado proveedor, int lotes)
    {
        ui.setupUi(this);
        this.proveedor = proveedor;
        this.lotes = lotes;
        crearAnalisis();
        this.setWindowTitle("Analisis");
    }

    public AnalisisG(QWidget parent)
    {
        super(parent);
        ui.setupUi(this);
    }
    
    public void crearAnalisis()
    {
    	QTableView tabla = ui.tableView;
    	crearModelo();
        tabla.setModel(modelo);
        tabla.horizontalHeader().resizeSection(0, 50);
        tabla.horizontalHeader().resizeSection(1, 50);
        tabla.horizontalHeader().resizeSection(2, 50);
        tabla.horizontalHeader().resizeSection(3, 50);
        tabla.horizontalHeader().resizeSection(4, 50);
        this.setPalette(new QPalette(QColor.white));
        ui.toolButton.clicked.connect(this, "crearGrafica1()");
        ui.tableView.doubleClicked.connect(this,"crearGrafica2(QModelIndex)");
        ui.nombre.setText(proveedor.darNombre());
        ui.bajo.setText(proveedor.darPromedioBajo(lotes));
        ui.alto.setText(proveedor.darPromedioAlto(lotes));
        ui.cierre.setText(proveedor.darPromedioCierre(lotes));
        ui.cierred.setText(proveedor.darDesviacionPromedioCierre(lotes));
    }
    
    public void crearGrafica1()
    {
        int[] ganancia = proveedor.darGanancia(lotes);
        XYSeries series = new XYSeries("Stop");
        for(int i = 0; i < ganancia.length; i++)
        {
        	series.add(i, ganancia[i]);
        }
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYAreaChart("Stop vs ganancia", "Stop", "Ganancia", xySeriesCollection, PlotOrientation.VERTICAL, false, false, false);
        String[][] mejores = proveedor.darTablaMejores(lotes);
        for(int i = 0; i < 5; i++)
        {
        	int y = Integer.parseInt(mejores[2][i].replace("$", ""));
        	int x = Integer.parseInt(mejores[1][i]);
        	XYAnnotation sga = new XYPointerAnnotation("      " + x, x, y, 0);
            chart.getXYPlot().addAnnotation(sga);
        }
        BufferedImage img = chart.createBufferedImage(1000, 800);
        try
        {
           imgPNG = ChartUtilities.encodeAsPNG(img);
        }
        catch(IOException e)
        {
           
        }
        QPixmap qPix = new QPixmap(1000, 800);
        qPix.loadFromData(imgPNG);
        QDialog dialogo = new QDialog(this);
        dialogo.resize(1000, 800);
        QLabel label = new QLabel(dialogo);
        label.resize(1000, 800);
        label.setPixmap(qPix);
        dialogo.setWindowTitle("Grafica");
        dialogo.show();
    }
    
    public void crearGrafica2(QModelIndex indice)
    {
        int[] ganancia = proveedor.darAnalisisStop(lotes, indice.row());
        XYSeries series = new XYSeries("Stop");
        for(int i = 0; i < ganancia.length; i++)
        {
        	series.add(i, ganancia[i]);
        }
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYAreaChart("Acumulado vs tiempo", "Tiempo", "Acumulado", xySeriesCollection, PlotOrientation.VERTICAL, false, false, false);
        BufferedImage img = chart.createBufferedImage(1000, 800);
        try
        {
           imgPNG = ChartUtilities.encodeAsPNG(img);
        }
        catch(IOException e)
        {
           
        }
        QPixmap qPix = new QPixmap(1000, 800);
        qPix.loadFromData(imgPNG);
        QDialog dialogo = new QDialog(this);
        dialogo.resize(1000, 800);
        QLabel label = new QLabel(dialogo);
        label.resize(1000, 800);
        label.setPixmap(qPix);
        dialogo.setWindowTitle("Grafica");
        dialogo.show();
    }
    
    public void crearModelo()
    {
    	String[][] tabla = proveedor.darTablaMejores(lotes);
    	modelo = new QStandardItemModel(5, 4, this);
		modelo.setHeaderData(0, Qt.Orientation.Horizontal,"Hasta");
        modelo.setHeaderData(1, Qt.Orientation.Horizontal,"Stop");
        modelo.setHeaderData(2, Qt.Orientation.Horizontal, "$");
        modelo.setHeaderData(3, Qt.Orientation.Horizontal,"Z");
		for(int i = 0; i < 5; i++)
		{
			QStandardItem mejorHasta = new QStandardItem();
			QStandardItem stop = new QStandardItem();
			QStandardItem ganancia = new QStandardItem();
			QStandardItem z = new QStandardItem();
			mejorHasta.setText(tabla[0][i]);
			stop.setText(tabla[1][i]);
			ganancia.setText(tabla[2][i].replaceAll(" ", ""));
			NumberFormat formateador = new DecimalFormat("#0.0000");
			z.setText(formateador.format(Double.parseDouble(tabla[3][i])));
			mejorHasta.setEditable(false);
			stop.setEditable(false);
			ganancia.setEditable(false);
			z.setEditable(false);
			mejorHasta.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			stop.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			ganancia.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			z.setTextAlignment(Qt.AlignmentFlag.AlignCenter);
			ArrayList <QStandardItem> fila = new ArrayList <QStandardItem> ();
			fila.add(mejorHasta);
			fila.add(stop);
			fila.add(ganancia);
			fila.add(z);
			modelo.insertRow(i, fila);
		}
		modelo.setRowCount(5);
		
    }
}
package analisis;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Grafica extends JFrame
{
	private static final long serialVersionUID = 6174853826093522407L;
	
	protected JLabel label;

	private Grafica(String nombre, String x, String y, JFreeChart jfc)
	{
		setMinimumSize(new Dimension(800, 600));
		setSize(800, 600);
		ImageIcon icon = new ImageIcon(jfc.createBufferedImage(800, 600));
		label = new JLabel(icon);
		add(label);
		pack();
		setVisible(true);
	}
	
	public static void crearChartPuntos(String nombre, String x, String y, double[][] datos)
	{
	    XYSeries series = new XYSeries(nombre);
	    for(int i = 0; i < datos[0].length; i++)
	    {
	        series.add(datos[0][i], datos[1][i]);
	    }
	    XYSeriesCollection xySeriesCollection = new XYSeriesCollection(series);
	    final JFreeChart chart = ChartFactory.createScatterPlot(x + " vs " + y, x, y, xySeriesCollection, PlotOrientation.VERTICAL, false, false, false);
	    new Grafica(nombre, x, y, chart);
//	    new Thread(new Runnable() {
//			
//			@Override
//			public void run() 
//			{
//				while(true)
//				{
//					for(int i = 1; i < 800; i += 5)
//						try {
//							Thread.sleep(1);
//							g.label.setIcon(new ImageIcon(chart.createBufferedImage(i, 600)));
//						} catch (InterruptedException e) {
//							
//						}
//					for(int i = 800; i >= 1; i -= 5)
//						try {
//							Thread.sleep(1);
//							g.label.setIcon(new ImageIcon(chart.createBufferedImage(i, 600)));
//						} catch (InterruptedException e) {
//							
//						}
//				}
//			}
//		}).start();
	}
	
	public static void crearChartProgreso(String nombre, String x, String y, double[] datos)
	{
	    XYSeries series = new XYSeries("Serie ganancia");
	    for(int i = 0; i < datos.length; i++)
	    {
	        series.add(i, datos[i]);
	    }
	    XYSeriesCollection xySeriesCollection = new XYSeriesCollection(series);
	    JFreeChart chart = ChartFactory.createXYAreaChart(x + " vs " + y, x, y, xySeriesCollection, PlotOrientation.VERTICAL, false, false, false);
	    new Grafica(nombre, x, y, chart);
	}
	
}
package dailyBot.vista;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import dailyBot.analisis.Rangos;
import dailyBot.analisis.RegistroHistorial;


public class GraficaProgreso extends JPanel
{
	private static final long serialVersionUID = 6174853826093522407L;
	
	private InfoGrafica info;
	private JLabel label;
	private List <RegistroHistorial> registros;
	private Rangos rangos;

	public GraficaProgreso(Rangos ra, List <RegistroHistorial> re)
	{
		rangos = ra;
		registros = re;
        label = new JLabel();
        info = new InfoGrafica();
        setLayout(new BorderLayout());
        add(info, BorderLayout.CENTER);
        add(label, BorderLayout.EAST);
		actualizarChartProgreso();
	}
	
	public void actualizarChartProgreso()
	{
	    XYSeries series = new XYSeries("Serie ganancia");
	    double acum = 0;
	    int nTransacciones = 0;
	    int i = 0;
	    for(RegistroHistorial r : registros)
	    {
	    	if(rangos.cumple(r, false, ""))
	    	{
	    		nTransacciones++;
	    		acum += r.ganancia;
		        series.add(r.fechaApertura, acum);
	    	}
	        i++;
	    }
	    double media = acum / nTransacciones;
	    double desviacionD = 0;
	    for(RegistroHistorial r : registros)
	    	if(rangos.cumple(r, false, ""))
	    		desviacionD += (r.ganancia - media) * (r.ganancia - media);
	    desviacionD /= nTransacciones;
	    desviacionD = Math.sqrt(desviacionD);
	    info.ganancia.setText(acum + "");
	    NumberFormat df = DecimalFormat.getNumberInstance();
	    df.setMaximumFractionDigits(4);
	    info.promedioPips.setText(df.format(media));
	    int porcentaje = (int) (((nTransacciones + 0.0d) / registros.size()) * 100);
	    String espacios = nTransacciones < 10 ? "    " : nTransacciones < 100 ? "   " : nTransacciones < 1000 ? "  " : " ";
	    String espaciosA = espacios;
	    espacios += "( " + (porcentaje == 100 ? "" : " ") + porcentaje + "%  )";
	    info.numeroTransacciones.setText(espaciosA + nTransacciones + espacios);
	    info.desviacion.setText(df.format(desviacionD));
	    XYSeriesCollection xySeriesCollection = new XYSeriesCollection(series);
	    JFreeChart chart = ChartFactory.createXYAreaChart("Ganancia vs tiempo", "Ganancia", "Tiempo", xySeriesCollection, PlotOrientation.VERTICAL, false, false, false);
	    label.setIcon(new ImageIcon(chart.createBufferedImage(400, 210)));
	}
	
	public List <RegistroHistorial> darRegistros()
	{
		return registros;
	}
}
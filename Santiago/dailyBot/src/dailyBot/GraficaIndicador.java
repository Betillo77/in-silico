package dailyBot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYBoxAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;

import dailyBot.analisis.Indicador;
import dailyBot.analisis.Rangos;
import dailyBot.analisis.RegistroHistorial;
import dailyBot.analisis.Rangos.Rango;


public class GraficaIndicador extends JPanel
{
	private static final long serialVersionUID = 3829875524026705923L;
	
	private JLabel label;
	private List <RegistroHistorial> registros;
	private Rangos rangos;
	private InfoGrafica info;
	boolean unico;
	
	public GraficaIndicador(Rango r, List <RegistroHistorial> re, Indicador i, Rangos ra)
	{
		registros = re;
		label = new JLabel();
        info = new InfoGrafica();
        unico = true;
        rangos = ra;
        setLayout(new BorderLayout());
        add(info, BorderLayout.CENTER);
        add(label, BorderLayout.EAST);
		setVisible(true);
		actualizarGrafica(r, i);
	}

	public void actualizarGrafica(Rango rango, Indicador indicador) 
	{
		XYSeriesCollection dataset = new XYSeriesCollection(); 
	    XYSeries seriesDentro = new XYSeries("Dentro " + indicador);
	    XYSeries seriesFuera = new XYSeries("Fuera " + indicador);
	    double acum = 0;
	    int nTransacciones = 0;
	    for(RegistroHistorial r : registros)
	    	if(unico)
	    	{
		    	if(rango.estaDentro(indicador.calcular(r)))
		    	{
		    		nTransacciones++;
		    		acum += r.ganancia;
		    		seriesDentro.add(indicador.calcular(r), r.ganancia);
		    	}
		    	else
		    		seriesFuera.add(indicador.calcular(r), r.ganancia);
	    	}
	    	else
	    	{
		    	if(rangos.cumple(r, false, false))
		    	{
		    		nTransacciones++;
		    		acum += r.ganancia;
		    		seriesDentro.add(indicador.calcular(r), r.ganancia);
		    	}
		    	else
		    		seriesFuera.add(indicador.calcular(r), r.ganancia);
	    	}
	    double media = acum / nTransacciones;
	    double desviacionD = 0;
	    for(RegistroHistorial r : registros)
	    	if(unico)
	    	{
		    	if(rango.estaDentro(indicador.calcular(r)))
		    		desviacionD += (r.ganancia - media) * (r.ganancia - media);
	    	}
	    	else
	    	{
		    	if(rangos.cumple(r, false, false))
		    		desviacionD += (r.ganancia - media) * (r.ganancia - media);
	    	}
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
	    dataset.addSeries(seriesDentro);
	    dataset.addSeries(seriesFuera);
	    JFreeChart chart = ChartFactory.createScatterPlot(indicador + " vs Ganancia ", indicador.toString(), "Ganancia", dataset, PlotOrientation.VERTICAL, false, false, false);
        XYPlot xyplot = chart.getXYPlot();
        Paint gradientpaint = new Color(0.0f, 0.0f, 0.0f, 1.0f);
        double delta = (indicador.darRango().getMaximo() - indicador.darRango().getMinimo()) / 1000;
        XYBoxAnnotation x = new XYBoxAnnotation(rango.getMinimo(), -100000, rango.getMinimo() + delta, 100000, null, null, gradientpaint);
        xyplot.getRenderer().addAnnotation(x, Layer.BACKGROUND);
        xyplot.getRenderer().setSeriesPaint(0, Color.BLUE);
        x = new XYBoxAnnotation(rango.getMaximo() - delta, -100000, rango.getMaximo(), 100000, null, null, gradientpaint);
        xyplot.getRenderer().addAnnotation(x, Layer.BACKGROUND);
	    label.setIcon(new ImageIcon(chart.createBufferedImage(600, 410)));
		this.setVisible(true);
	}
}

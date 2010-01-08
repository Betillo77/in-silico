import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYPointerAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;


public class EjemploChart 
{
	
	static
	{
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
	}

}

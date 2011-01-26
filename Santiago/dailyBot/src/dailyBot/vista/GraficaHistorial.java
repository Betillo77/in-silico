package dailyBot.vista;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import dailyBot.analisis.Rangos;
import dailyBot.analisis.RegistroHistorial;


public class GraficaHistorial extends JFrame
{
	private static final long serialVersionUID = 8280180608366756689L;
	
	private JLabel graficaPromedioPips;
	private JLabel graficaMeses;
	private Rangos rangos;
	private List <RegistroHistorial> registros;
		
	public GraficaHistorial(Rangos r, List <RegistroHistorial> re, String titulo) 
	{
		super(titulo);
		rangos = r;
		registros = re;
		initialize();
		setPreferredSize(new Dimension(1237, 626));
		pack();
		setVisible(true);
		actualizarGraficas();
	}

	private void initialize() 
	{
		this.setSize(1237, 626);
		graficaMeses = new JLabel();
		graficaMeses.setBounds(new Rectangle(16, 286, 1198, 290));
		graficaPromedioPips = new JLabel();
		graficaPromedioPips.setBounds(new Rectangle(771, 7, 443, 267));
		JPanel jContentPane = new JPanel();
		jContentPane.add(graficaPromedioPips, null);
		jContentPane.add(graficaMeses, null);
		this.setContentPane(jContentPane);
	}
	
    public JFreeChart darGraficaMeses() 
    {
        DefaultCategoryDataset cdts = new DefaultCategoryDataset();
		Calendar fecha1 = Calendar.getInstance();
		fecha1.setTimeInMillis(registros.isEmpty() ? 0 : registros.get(0).fechaApertura);
		Calendar actual = fecha1;
		int acumuladoActual = 0;
		Calendar temp = Calendar.getInstance();
		int cuentaActual = 0;
		for(RegistroHistorial registro : registros)
		{
			if(rangos.cumple(registro, false, ""))
			{
				temp.setTimeInMillis(registro.fechaApertura);
				if(temp.get(Calendar.MONTH) != actual.get(Calendar.MONTH))
				{
					if(cuentaActual != 0)
						cdts.addValue(acumuladoActual, "", mesComoString(actual) + "-" + (actual.get(Calendar.YEAR) - 2000));
					actual = Calendar.getInstance();
					actual.setTimeInMillis(temp.getTimeInMillis());
					acumuladoActual = registro.ganancia;
					cuentaActual = 1;
				}
				else
				{
					acumuladoActual += registro.ganancia;
					cuentaActual++;
				}
			}
		}
		cdts.addValue(acumuladoActual, "", mesComoString(actual) + " " + actual.get(Calendar.YEAR));
        return ChartFactory.createBarChart("Ganancia atravez del tiempo", "Tiempo", "Ganancia", cdts, PlotOrientation.VERTICAL, false, false, false);
    }
    
    public JFreeChart darGraficaPromedioPips() 
    {
        XYSeries series = new XYSeries("Serie pips");
		double ganancia = 0;
		int i = 0;
		for(RegistroHistorial registro : registros)
		{
			if(rangos.cumple(registro, false, ""))
			{
				ganancia += registro.ganancia;
				i++;
				series.add(registro.fechaCierre, ganancia / i);
			}
        }
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection(series);
        return ChartFactory.createXYAreaChart("Promedio Pips vs Tiempo", "Tiempo", "Promedio pips", xySeriesCollection, PlotOrientation.VERTICAL, false, false, false);
    }
    
    public void actualizarGraficas()
    {
    	graficaMeses.setIcon(new ImageIcon(darGraficaMeses().createBufferedImage(1198, 290)));
    	graficaPromedioPips.setIcon(new ImageIcon(darGraficaPromedioPips().createBufferedImage(443, 267)));
    }
	
	public static String mesComoString(Calendar calendario)
	{
		switch(calendario.get(Calendar.MONTH))
		{
			case Calendar.JANUARY:   return "Ene";
			case Calendar.FEBRUARY:  return "Feb";
			case Calendar.MARCH: 	 return "Mar";
			case Calendar.APRIL: 	 return "Abr";
			case Calendar.MAY: 		 return "May";
			case Calendar.JUNE:	 	 return "Jun";
			case Calendar.JULY: 	 return "Jul";
			case Calendar.AUGUST: 	 return "Ago";
			case Calendar.SEPTEMBER: return "Sep";
			case Calendar.OCTOBER: 	 return "Oct";
			case Calendar.NOVEMBER:  return "Nov";
			case Calendar.DECEMBER:  return "Dic";
			default:                 return "ERROR";
		}
	}
}
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class TestConnected 
{
	public static class GraficaConnected extends JPanel
	{
		private static final long serialVersionUID = 3829875524026705923L;
		
		static class Punto
		{
			int x, y, num;
			
			public Punto(int xx, int yy, int n)
			{
				x = xx;
				y = yy;
				num = n;
			}
			
			public int distancia(Punto otro)
			{
				return (x - otro.x) * (x - otro.x) + (y - otro.y) * (y - otro.y);
			}
			
			public void llenarVecinos(List <Punto> todosPuntos)
			{
				for(int i : vecinosN)
					vecinos.add(todosPuntos.get(i));
				int k = vecinos.size();
				for(Punto p : todosPuntos)
				{
					if(p == this)
						continue;
					if(vecinosReales.size() != k)
						vecinosReales.add(p);
					else
					{
						int distanciaAct = distancia(p);
						int indicePeor = 0;
						int peorDistancia = Integer.MIN_VALUE;
						for(int i = 0; i < vecinosReales.size(); i++)
						{
							if(distancia(vecinosReales.get(i)) > peorDistancia)
							{
								peorDistancia = distancia(vecinosReales.get(i));
								indicePeor = i;
							}
						}
						if(distanciaAct < peorDistancia)
						{
							vecinosReales.set(indicePeor, p);
						}
					}
				}
				ArrayList <Integer> distanciasA = new ArrayList <Integer> ();
				for(Punto p : vecinos)
					distanciasA.add(distancia(p));
				ArrayList <Integer> distanciasB = new ArrayList <Integer> ();
				for(Punto p : vecinosReales)
					distanciasB.add(distancia(p));
				Collections.sort(distanciasA);
				Collections.sort(distanciasB);
				if(!distanciasA.equals(distanciasB))
					System.out.println("No son iguales");
				
			}
			ArrayList <Integer> vecinosN = new ArrayList <Integer> ();
			ArrayList <Punto> vecinos = new ArrayList <Punto> ();
			ArrayList <Punto> vecinosReales = new ArrayList <Punto> ();
			
			@Override
			public String toString()
			{
				return num + "";
			}
		}
		
		private List <Punto> puntos;
		private JLabel label;
		
		public GraficaConnected(ArrayList<Punto> arrayList)
		{
			puntos = arrayList;
			label = new JLabel();
	        add(label, BorderLayout.EAST);
			setVisible(true);
			actualizarGrafica(-1);
		}
		
		public void actualizarGrafica(int cual) 
		{
			if(cual < -1 || cual >= puntos.size())
				return;
			XYSeriesCollection dataset = new XYSeriesCollection(); 
		    XYSeries seriesTodos = new XYSeries("Todos");
		    XYSeries seriesVecinos = new XYSeries("Vecinos");
		    XYSeries seriesEste = new XYSeries("Este");
		    int i = -1;
		    for(Punto p : puntos)
		    {
		    	i++;
			    if(cual != -1 && puntos.get(cual).vecinosN.contains(i))
			    	continue;
			    if(i == cual)
			    	continue;
		    	seriesTodos.add(p.x, p.y);
		    }
		    if(cual != -1)
		    	for(Punto p : puntos.get(cual).vecinos)
		    	{
		    		seriesVecinos.add(p.x, p.y);
		    	}
		    if(cual != -1)
		    	seriesEste.add(puntos.get(cual).x, puntos.get(cual).y);
		    dataset.addSeries(seriesEste);
		    dataset.addSeries(seriesVecinos);
		    dataset.addSeries(seriesTodos);
		    JFreeChart chart = ChartFactory.createScatterPlot("Hoja", "Width", "Height", dataset, PlotOrientation.VERTICAL, false, false, false);
		    chart.getXYPlot().getDomainAxis().setRange(new Range(0, 2000));
		    chart.getXYPlot().getRangeAxis().setRange(new Range(0, 2000));
		    label.setIcon(new ImageIcon(chart.createBufferedImage(800, 800)));
		    this.setVisible(true);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		JFrame framePrincipal = new JFrame();
		framePrincipal.setMinimumSize(new Dimension(1000, 1000));
		framePrincipal.setLayout(new BorderLayout());
		final JTextArea jta = new JTextArea();
		Scanner sc = new Scanner(new File("prueba"));
		int nPuntos = sc.nextInt();
		int k = sc.nextInt();
		ArrayList <GraficaConnected.Punto> arreglo = new ArrayList<GraficaConnected.Punto>();
		for(int i = 0; i < nPuntos; i++)
		{
			arreglo.add(new GraficaConnected.Punto(sc.nextInt(), sc.nextInt(), i));
			for(int j = 0; j < k; j++)
				arreglo.get(i).vecinosN.add(sc.nextInt());
		}
		for(GraficaConnected.Punto p : arreglo)
			p.llenarVecinos(arreglo);
		final GraficaConnected g = new GraficaConnected(arreglo);
		jta.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) 
			{
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
                    	try
                    	{
                    	g.actualizarGrafica(Integer.parseInt(jta.getText().trim()));
                    	}
                    	catch(Exception e)
                    	{
                    		
                    	}
                    }
                });	
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		jta.setVisible(true);
		framePrincipal.add(jta, BorderLayout.NORTH);
		framePrincipal.add(g, BorderLayout.EAST);
		framePrincipal.pack();
		framePrincipal.setVisible(true);
	}
}
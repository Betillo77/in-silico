package vista;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import modelo.Par;
import modelo.Estrategia.IdEstrategia;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import control.AnalisisLogica;

public class AnalisisGrafico extends JFrame implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel ganancia = null;
	private JLabel numeroTransacciones = null;
	private JLabel promedioPips = null;
	private JLabel gananciaT = null;
	private JLabel pips = null;
	private JLabel numeroTransaccionesT = null;
	private JLabel transacciones = null;
	private JLabel promedioPipsT = null;
	private JLabel pipsTransaccion = null;
	private Choice choice = null;
	private JLabel Divisa = null;
	private JLabel graficaProgreso = null;
	private JLabel graficaPromedioPips = null;
	private JLabel graficaMeses = null;
	private JRadioButton unaSemana = null;
	private JRadioButton dosSemanas = null;
	private JRadioButton tresSemanas = null;
	private JRadioButton unMes = null;
	private JRadioButton dosMeses = null;
	private JRadioButton tresMeses = null;
	private JRadioButton seisMeses = null;
	private JRadioButton unAnho = null;
	private JRadioButton todo = null;
	private JLabel estrategiaActual = null;
	private ManejadorAnalisisGrafico manejador;
	private JLabel desviacion = null;
	private JLabel desviacionT = null;
	private JLabel pips1 = null;
	
	/**
	 * This is the default constructor
	 */
	public AnalisisGrafico(IdEstrategia estrategia) 
	{
		super();
		manejador = new ManejadorAnalisisGrafico(estrategia);
		manejador.cambiarObjetos(Par.TODOS, 8);
		initialize();
		setPreferredSize(new Dimension(1237, 626));
		pack();
		setVisible(true);
	}

	public AnalisisGrafico()
	{
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(1237, 626);
		this.setContentPane(getJContentPane());
		this.setTitle(manejador.estrategia.toString());
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {	
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		pips1 = new JLabel();
		pips1.setBounds(new Rectangle(185, 158, 26, 19));
		pips1.setText("pips");
		pips1.setFont(new Font(pips1.getFont().getName(), Font.PLAIN, 14));
		desviacionT = new JLabel("", SwingConstants.RIGHT);
		desviacionT.setBounds(new Rectangle(131, 157, 49, 21));
		desviacionT.setText(new DecimalFormat("###.##").format(manejador.darDesviacion()));
		desviacionT.setFont(new Font(desviacionT.getFont().getName(), Font.PLAIN, 14));
		desviacion = new JLabel();
		desviacion.setBounds(new Rectangle(20, 160, 93, 17));
		desviacion.setText("Desviacion");
		estrategiaActual = new JLabel();
		estrategiaActual.setBounds(new Rectangle(16, 10, 258, 24));
		estrategiaActual.setText(manejador.darEstrategia());
		estrategiaActual.setFont(new Font(estrategiaActual.getFont().getName(), Font.BOLD, 28));
		graficaMeses = new JLabel(new ImageIcon(manejador.darGraficaMeses().createBufferedImage(1198, 290)));
		graficaMeses.setBounds(new Rectangle(16, 286, 1198, 290));
		graficaMeses.setText("JLabel");
		graficaPromedioPips = new JLabel(new ImageIcon(manejador.darGraficaPromedioPips().createBufferedImage(443, 267)));
		graficaPromedioPips.setBounds(new Rectangle(771, 7, 443, 267));
		graficaPromedioPips.setText("JLabel");
		graficaProgreso = new JLabel(new ImageIcon(manejador.darGraficaProgreso().createBufferedImage(443, 267)));
		graficaProgreso.setBounds(new Rectangle(300, 7, 443, 267));
		graficaProgreso.setText("JLabel");
		Divisa = new JLabel();
		Divisa.setBounds(new Rectangle(19, 60, 53, 16));
		Divisa.setText("Divisa");
		pipsTransaccion = new JLabel();
		pipsTransaccion.setBounds(new Rectangle(185, 136, 88, 19));
		pipsTransaccion.setText("pips/trans");
		pipsTransaccion.setFont(new Font(pipsTransaccion.getFont().getName(), Font.PLAIN, 14));
		promedioPipsT = new JLabel("", SwingConstants.RIGHT);
		promedioPipsT.setBounds(new Rectangle(131, 135, 49, 21));
		promedioPipsT.setText(new DecimalFormat("###.##").format(manejador.darPromedioPips()));
		promedioPipsT.setFont(new Font(promedioPipsT.getFont().getName(), Font.PLAIN, 14));
		transacciones = new JLabel();
		transacciones.setBounds(new Rectangle(185, 114, 98, 19));
		transacciones.setText("transacciones");
		transacciones.setFont(new Font(transacciones.getFont().getName(), Font.PLAIN, 14));
		numeroTransaccionesT = new JLabel("", SwingConstants.RIGHT);
		numeroTransaccionesT.setBounds(new Rectangle(131, 113, 49, 21));
		numeroTransaccionesT.setText(manejador.darNumeroTransacciones() + "");
		numeroTransaccionesT.setFont(new Font(numeroTransaccionesT.getFont().getName(), Font.PLAIN, 14));
		pips = new JLabel();
		pips.setBounds(new Rectangle(184, 94, 38, 16));
		pips.setText("pips");
		pips.setFont(new Font(pips.getFont().getName(), Font.PLAIN, 14));
		gananciaT = new JLabel("", SwingConstants.RIGHT);
		gananciaT.setBounds(new Rectangle(131, 92, 49, 21));
		gananciaT.setText(manejador.darGanancia() + "");
		gananciaT.setFont(new Font(gananciaT.getFont().getName(), Font.PLAIN, 14));
		promedioPips = new JLabel();
		promedioPips.setBounds(new Rectangle(20, 138, 93, 16));
		promedioPips.setText("Promedio pips");
		numeroTransacciones = new JLabel();
		numeroTransacciones.setBounds(new Rectangle(19, 116, 103, 16));
		numeroTransacciones.setText("N� transacciones");
		ganancia = new JLabel();
		ganancia.setBounds(new Rectangle(19, 94, 82, 16));
		ganancia.setText("Ganancia");
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		jContentPane.add(ganancia, null);
		jContentPane.add(numeroTransacciones, null);
		jContentPane.add(promedioPips, null);
		jContentPane.add(gananciaT, null);
		jContentPane.add(pips, null);
		jContentPane.add(numeroTransaccionesT, null);
		jContentPane.add(transacciones, null);
		jContentPane.add(promedioPipsT, null);
		jContentPane.add(pipsTransaccion, null);
		jContentPane.add(getChoice(), null);
		jContentPane.add(Divisa, null);
		jContentPane.add(graficaProgreso, null);
		jContentPane.add(graficaPromedioPips, null);
		jContentPane.add(graficaMeses, null);
		jContentPane.add(getUnaSemana(), null);
		jContentPane.add(getDosSemanas(), null);
		jContentPane.add(getTresSemanas(), null);
		jContentPane.add(getUnMes(), null);
		jContentPane.add(getDosMeses(), null);
		jContentPane.add(getTresMeses(), null);
		jContentPane.add(getSeisMeses(), null);
		jContentPane.add(getUnAnho(), null);
		jContentPane.add(getTodo(), null);
		jContentPane.add(estrategiaActual, null);
		jContentPane.add(desviacion, null);
		jContentPane.add(desviacionT, null);
		jContentPane.add(pips1, null);
		if(choice.getItemCount() == 0)
		{
			ButtonGroup bg = new ButtonGroup();
			bg.add(unaSemana);
			bg.add(dosSemanas);
			bg.add(tresSemanas);
			bg.add(unMes);
			bg.add(dosMeses);
			bg.add(tresMeses);
			bg.add(seisMeses);
			bg.add(unAnho);
			bg.add(todo);
			bg.setSelected(todo.getModel(), true);
			unaSemana.addActionListener(this);
			dosSemanas.addActionListener(this);
			tresSemanas.addActionListener(this);
			unMes.addActionListener(this);
			dosMeses.addActionListener(this);
			tresMeses.addActionListener(this);
			seisMeses.addActionListener(this);
			unAnho.addActionListener(this);
			todo.addActionListener(this);
			choice.add("TODOS");
			for(Par a : Par.values())
			{
				if(a != Par.TODOS)
					choice.add(a.toString());
			}
			choice.addItemListener(this);
		}
		return jContentPane;
	}

	/**
	 * This method initializes choice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getChoice() {
		if (choice == null) {
			choice = new Choice();
			choice.setBounds(new Rectangle(79, 57, 89, 22));
		}
		return choice;
	}

	/**
	 * This method initializes unaSemana	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getUnaSemana() {
		if (unaSemana == null) {
			unaSemana = new JRadioButton();
			unaSemana.setBounds(new Rectangle(16, 195, 50, 21));
			unaSemana.setText("1s");
		}
		return unaSemana;
	}

	/**
	 * This method initializes dosSemanas	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getDosSemanas() {
		if (dosSemanas == null) {
			dosSemanas = new JRadioButton();
			dosSemanas.setBounds(new Rectangle(56, 195, 50, 21));
			dosSemanas.setText("2s");
		}
		return dosSemanas;
	}

	/**
	 * This method initializes tresSemanas	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getTresSemanas() {
		if (tresSemanas == null) {
			tresSemanas = new JRadioButton();
			tresSemanas.setBounds(new Rectangle(96, 195, 50, 21));
			tresSemanas.setText("3s");
		}
		return tresSemanas;
	}

	/**
	 * This method initializes unMes	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getUnMes() {
		if (unMes == null) {
			unMes = new JRadioButton();
			unMes.setBounds(new Rectangle(136, 195, 50, 21));
			unMes.setText("1m");
		}
		return unMes;
	}

	/**
	 * This method initializes dosMeses	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getDosMeses() {
		if (dosMeses == null) {
			dosMeses = new JRadioButton();
			dosMeses.setBounds(new Rectangle(181, 195, 50, 21));
			dosMeses.setText("2m");
		}
		return dosMeses;
	}

	/**
	 * This method initializes tresMeses	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getTresMeses() {
		if (tresMeses == null) {
			tresMeses = new JRadioButton();
			tresMeses.setBounds(new Rectangle(226, 195, 50, 21));
			tresMeses.setText("3m");
		}
		return tresMeses;
	}

	/**
	 * This method initializes seisMeses	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getSeisMeses() {
		if (seisMeses == null) {
			seisMeses = new JRadioButton();
			seisMeses.setBounds(new Rectangle(16, 215, 50, 21));
			seisMeses.setText("6m");
		}
		return seisMeses;
	}

	/**
	 * This method initializes unAnho	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getUnAnho() {
		if (unAnho == null) {
			unAnho = new JRadioButton();
			unAnho.setBounds(new Rectangle(56, 215, 50, 21));
			unAnho.setText("1a");
		}
		return unAnho;
	}

	/**
	 * This method initializes todo	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getTodo() {
		if (todo == null) {
			todo = new JRadioButton();
			todo.setBounds(new Rectangle(96, 215, 60, 21));
			todo.setText("t");
		}
		return todo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int numero;
		if(e.getSource() instanceof JCheckBox)
		{
	//		Estrategia estrategia = manejador.estrategia.darEstrategia();
	//TODO		estrategia.cambiarActivo(manejador.divisaActual, ((JCheckBox) e.getSource()).isSelected());
			return;
		}
		if(((AbstractButton) e.getSource()).getText().equals("1s"))
		{
			numero = 0;
		}
		else if(((AbstractButton) e.getSource()).getText().equals("2s"))
		{
			numero = 1;
		}
		else if(((AbstractButton) e.getSource()).getText().equals("3s"))
		{
			numero = 2;
		}
		else if(((AbstractButton) e.getSource()).getText().equals("1m"))
		{
			numero = 3;
		}
		else if(((AbstractButton) e.getSource()).getText().equals("2m"))
		{
			numero = 4;
		}
		else if(((AbstractButton) e.getSource()).getText().equals("3m"))
		{
			numero = 5;
		}
		else if(((AbstractButton) e.getSource()).getText().equals("6m"))
		{
			numero = 6;
		}
		else if(((AbstractButton) e.getSource()).getText().equals("1a"))
		{
			numero = 7;
		}
		else
		{
			numero = 8;
		}
		manejador.cambiarObjetos(manejador.divisaActual, numero);
		initialize();
		jContentPane.revalidate();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Par actual = Par.stringToPar(choice.getSelectedItem());
		manejador.cambiarObjetos(actual, manejador.tiempoActual);
		initialize();
		jContentPane.revalidate();
	}
	
	public static void main(String [] args)
	{
		new AnalisisGrafico(IdEstrategia.values()[((IdEstrategia) JOptionPane.showInputDialog(null, "Escoja la estrategia", "Analisis grafico", JOptionPane.QUESTION_MESSAGE, null, IdEstrategia.values(), IdEstrategia.BREAKOUT1)).ordinal()]);
	}
}  //  @jve:decl-index=0:visual-constraint="5,7"


class ManejadorAnalisisGrafico
{
	IdEstrategia estrategia;
	ArrayList <Object> objetos;
	Par divisaActual = Par.TODOS;
	int tiempoActual = 8;
	
	public ManejadorAnalisisGrafico(IdEstrategia estrategia)
	{
		this.estrategia = estrategia;
		cambiarObjetos(divisaActual, tiempoActual);
	}
	
	public long darGanancia() 
	{
		return (Long) objetos.get(3);
	}
	
	public double darDesviacion()
	{
		return (Double) objetos.get(4);
	}

	public int darNumeroTransacciones() 
	{
		return (Integer) objetos.get(2);
	}

	public double darPromedioPips() 
	{
		return (Double) objetos.get(1);
	}

	public JFreeChart darGraficaProgreso() 
	{
		long[][] graficaProgreso = (long[][]) objetos.get(0);
	    XYSeries series = new XYSeries("Serie ganancia");
	    for(int i = 0; i < graficaProgreso[0].length; i++)
	    {
	        series.add(i, graficaProgreso[0][i]);
	    }
	    XYSeriesCollection xySeriesCollection = new XYSeriesCollection(series);
	    return ChartFactory.createXYAreaChart("Ganancia vs Tiempo", "Tiempo", "Ganancia", xySeriesCollection, PlotOrientation.VERTICAL, false, false, false);
	}

	public JFreeChart darGraficaPromedioPips() 
	{
		double[][] graficaPromedioPips = (double[][]) objetos.get(5);
	    XYSeries series = new XYSeries("Serie pips");
	    for(int i = 0; i < graficaPromedioPips[0].length; i++)
	    {
	        series.add(i, graficaPromedioPips[0][i]);
	    }
	    XYSeriesCollection xySeriesCollection = new XYSeriesCollection(series);
	    return ChartFactory.createXYAreaChart("Promedio Pips vs Tiempo", "Tiempo", "Promedio pips", xySeriesCollection, PlotOrientation.VERTICAL, false, false, false);
	}

	@SuppressWarnings("unchecked")
	public JFreeChart darGraficaMeses() 
	{
		ArrayList <String> graficaMeses = (ArrayList <String>) objetos.get(6);
	    DefaultCategoryDataset cdts = new DefaultCategoryDataset();
	    for(int i = 0; i < graficaMeses.size(); i++)
	    {
	    	Scanner sc = new Scanner(graficaMeses.get(i));
	    	cdts.addValue(sc.nextInt(), "", graficaMeses.get(i).substring(graficaMeses.get(i).indexOf(' ')));
	    }
	    return ChartFactory.createBarChart("Ganancia atravez del tiempo", "Tiempo", "Ganancia", cdts, PlotOrientation.VERTICAL, false, false, false);
	}

	public void cambiarObjetos(Par divisaActual, int tiempoActual)
	{
		objetos = AnalisisLogica.retornar(estrategia, divisaActual, tiempoActual);
		this.divisaActual = divisaActual;
		this.tiempoActual = tiempoActual;
	}
	
	public String darEstrategia()
	{
		return estrategia.toString();
	}
}
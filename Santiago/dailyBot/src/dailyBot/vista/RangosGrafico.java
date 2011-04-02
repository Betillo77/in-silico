package dailyBot.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dailyBot.analisis.Indicador;
import dailyBot.analisis.Rangos;
import dailyBot.analisis.RegistroHistorial;
import dailyBot.control.Error;
import dailyBot.modelo.Par;
import dailyBot.modelo.Estrategia.IdEstrategia;

public class RangosGrafico extends JFrame
{
	private static final long serialVersionUID = -3243368885519444393L;

	private ArrayList <RangoGrafico> rangosGrafico = new ArrayList <RangoGrafico> ();
	
	public RangosGrafico(final Rangos rangos, List <RegistroHistorial> registros, final IdEstrategia idEstrategia, final Par par, String titulo)
	{
		super(titulo);
		GraficaProgreso graficaProgreso = new GraficaProgreso(rangos, registros);
		final GraficaIndicador graficaIndicador = new GraficaIndicador(Indicador.VIX.darRango().duplicar(), registros, Indicador.VIX, rangos);
		GraficaHistorial graficaHistorial = new GraficaHistorial(rangos, registros, titulo);
		JPanel panelRangos = new JPanel();
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(6);
		gridLayout.setColumns(1);
		panelRangos.setLayout(gridLayout);
		panelRangos.setSize(700, 600);
		for(Indicador i : Indicador.values())
		{
			RangoGrafico r = new RangoGrafico(this, i.darRango().duplicar(), rangos, graficaProgreso, graficaIndicador, graficaHistorial, i);
			rangosGrafico.add(r);
			panelRangos.add(r);
		}
		final JCheckBox box = new JCheckBox("Filtrar");
		box.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				graficaIndicador.unico = !box.isSelected();
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
        				graficaIndicador.actualizarGrafica(Indicador.VIX.darRango(), Indicador.VIX);
                    }
                });
			}
		});
		panelRangos.add(box);
		JPanel panelGraficas = new JPanel();
		panelGraficas.setLayout(new BorderLayout());
		panelGraficas.add(graficaProgreso, BorderLayout.SOUTH);
		panelGraficas.add(graficaIndicador, BorderLayout.NORTH);
		setLayout(new BorderLayout());
		add(panelRangos, BorderLayout.WEST);
		add(panelGraficas, BorderLayout.EAST);
		final JFrame este = this;
	    addWindowListener(new WindowAdapter() 
	    {
	    	@Override
	        public void windowClosing(WindowEvent e)
	        {
	    		try
	    		{
	    			if(idEstrategia != null && JOptionPane.showConfirmDialog(este, "Guardar el rango?", "Guardar " + idEstrategia + " " + par, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
	    				VentanaPrincipal.conexion.cambiarRangosEstrategia(idEstrategia.ordinal(), par.ordinal(), rangos);
				}
	            catch (Exception e1)
	            {        	
	            	Error.agregarRMI(e1.getMessage() + " Error haciendo la conexion RMI");
	            	System.exit(0);
	            }
	        }
	    });
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		pack();
		setVisible(true);
	}
	
	public void actualizarTodos()
	{
		for(RangoGrafico r : rangosGrafico)
			r.actualizar();
	}
}
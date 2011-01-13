package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modelo.Par;
import modelo.Estrategia.IdEstrategia;
import analisis.Indicador;
import analisis.Rangos;
import analisis.RegistroHistorial;
import control.Error;

public class RangosGrafico extends JPanel
{
	private static final long serialVersionUID = -3243368885519444393L;

	public RangosGrafico(final Rangos rangos, List <RegistroHistorial> registros, final IdEstrategia idEstrategia, final Par par, String titulo)
	{
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(6);
		gridLayout.setColumns(1);
		this.setLayout(gridLayout);
		this.setSize(700, 600);
		GraficaProgreso graficaProgreso = new GraficaProgreso(rangos, registros);
		final GraficaIndicador graficaIndicador = new GraficaIndicador(Indicador.VIX.darRango().duplicar(), registros, Indicador.VIX, rangos);
		GraficaHistorial graficaHistorial = new GraficaHistorial(rangos, registros, titulo);
		for(Indicador i : Indicador.values())
			this.add(new RangoGrafico(i.darRango().duplicar(), rangos.darRango(i), graficaProgreso, graficaIndicador, graficaHistorial, i));
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
		this.add(box);
		if(Toolkit.getDefaultToolkit().getScreenSize().width > 1500)
		{
			final JFrame nuevo = new JFrame(titulo);
			nuevo.setLayout(new BorderLayout());
			nuevo.add(this, BorderLayout.WEST);
		    nuevo.addWindowListener(new WindowAdapter() 
		    {
		    	@Override
		        public void windowClosing(WindowEvent e)
		        {
		    		try
		    		{
		    			if(idEstrategia != null && JOptionPane.showConfirmDialog(nuevo, "Guardar el rango?", "Guardar " + idEstrategia + " " + par, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		    				ParteGrafica.conexion.cambiarRangosEstrategia(idEstrategia.ordinal(), par.ordinal(), rangos);
					}
		            catch (Exception e1)
		            {        	
		            	Error.agregar(e1.getMessage() + " Error haciendo la conexion RMI");
		            	System.exit(0);
		            }
		        }
		    });
			JPanel derecha = new JPanel();
			derecha.setLayout(new BorderLayout());
			derecha.add(graficaProgreso, BorderLayout.SOUTH);
			derecha.add(graficaIndicador, BorderLayout.NORTH);
			nuevo.add(derecha, BorderLayout.EAST);
			nuevo.setPreferredSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
			nuevo.pack();
			nuevo.setVisible(true);
			this.setVisible(true);
		}
		else
		{
			final JFrame izquierda = new JFrame(titulo);
			izquierda.add(this);
		    izquierda.addWindowListener(new WindowAdapter() 
		    {
		    	@Override
		        public void windowClosing(WindowEvent e)
		        {
		    		try
		    		{		    			
		    			if(idEstrategia != null && JOptionPane.showConfirmDialog(izquierda, "Guardar el rango?", "Guardar " + idEstrategia + " " + par, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		    				ParteGrafica.conexion.cambiarRangosEstrategia(idEstrategia.ordinal(), par.ordinal(), rangos);
					}
		            catch (Exception e1)
		            {        	
		            	Error.agregar(e1.getMessage() + " Error haciendo la conexion RMI");
		            	System.exit(0);
		            }
		        }
		    });
			JFrame derecha = new JFrame(idEstrategia + " " + par);
			JPanel panelDerecha = new JPanel();
			panelDerecha.setLayout(new BorderLayout());
			panelDerecha.add(graficaProgreso, BorderLayout.SOUTH);
			panelDerecha.add(graficaIndicador, BorderLayout.NORTH);
			derecha.add(panelDerecha);
			this.setVisible(true);
			izquierda.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
			izquierda.pack();
			izquierda.setVisible(true);
			derecha.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
			derecha.pack();
			derecha.setVisible(true);
		}
	}
}
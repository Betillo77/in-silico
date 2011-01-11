package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import analisis.Indicador;
import analisis.Rangos;
import analisis.RegistroHistorial;

public class RangosGrafico extends JPanel
{
	private static final long serialVersionUID = -3243368885519444393L;

	public RangosGrafico(Rangos rangos, List <RegistroHistorial> registros)
	{
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(5);
		gridLayout.setColumns(1);
		this.setLayout(gridLayout);
		this.setSize(700, 600);
		GraficaProgreso graficaProgreso = new GraficaProgreso(rangos, registros);
		final GraficaIndicador graficaIndicador = new GraficaIndicador(Indicador.VIX.darRango().duplicar(), registros, Indicador.VIX, rangos);
		for(Indicador i : Indicador.values())
			this.add(new RangoGrafico(i.darRango().duplicar(), rangos.darRango(i), graficaProgreso, graficaIndicador, i.toString(), i.darEspaciado(), i));
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
			JFrame nuevo = new JFrame();
			nuevo.setLayout(new BorderLayout());
			nuevo.add(this, BorderLayout.WEST);
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
			JFrame izquierda = new JFrame();
			izquierda.add(this);
			JFrame derecha = new JFrame();
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

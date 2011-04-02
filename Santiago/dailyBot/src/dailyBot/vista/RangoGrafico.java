package dailyBot.vista;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dailyBot.analisis.Indicador;
import dailyBot.analisis.Rangos;
import dailyBot.analisis.Rangos.Rango;


public class RangoGrafico extends JPanel
{
	private static final long serialVersionUID = -7783062359910099807L;
	
	private Rangos rangos;
	private GraficaProgreso graficaProgreso;
    private GraficaIndicador graficaIndicador;
    private GraficaHistorial graficaHistorial;
    private RangosGrafico rangosGrafico;
    private JCheckBox invertido;
    private Indicador indicador;
    private JSlider maximo;
    private JSlider minimo;
    private JLabel nombre;
    
    public RangoGrafico(RangosGrafico rG, Rango original, Rangos r, GraficaProgreso g, GraficaIndicador gI, GraficaHistorial gH, Indicador i)
    {
    	rangosGrafico = rG;
    	rangos = r;
    	graficaProgreso = g;
    	graficaIndicador = gI;
    	graficaHistorial = gH;
    	indicador = i;
        setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints;
        nombre = new JLabel(i.toString());
        nombre.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
        invertido = new javax.swing.JCheckBox();
        invertido.setText("invertido");
        minimo = new JSlider((int) original.getMinimo(), (int) original.getMaximo()); 
        boolean compra = ((int) rangos.darRangoCompra(Indicador.COMPRA).getMinimo()) == 1;
        Rango rango = compra ? rangos.darRangoCompra(indicador) : rangos.darRangoVenta(indicador);
        minimo.setValue((int) ((compra ? rangos.darRangoCompra(i) : rangos.darRangoVenta(i)).getMinimo()));
        minimo.setPreferredSize(new Dimension(600, 39));
        minimo.setMinorTickSpacing(Math.min(1, i.darEspaciado()));
        minimo.setMajorTickSpacing(i.darEspaciado());
        minimo.setPaintTicks(true);
        minimo.setSnapToTicks(true);
        minimo.setPaintLabels(true);
        if(i.tieneLabels())
        	minimo.setLabelTable(i.darLabels());
        else
        	minimo.setLabelTable(minimo.createStandardLabels(Math.max(i.darEspaciado(), 4)));
        minimo.addChangeListener(new ChangeListener() 
        {
			@Override
			public void stateChanged(ChangeEvent e) 
			{
		        boolean compra = ((int) rangos.darRangoCompra(Indicador.COMPRA).getMinimo()) == 1;
		        final Rango rango = compra ? rangos.darRangoCompra(indicador) : rangos.darRangoVenta(indicador);
				if(minimo.getValue() > rango.getMaximo())
				{
					SwingUtilities.invokeLater(new Runnable() 
					{
                        public void run() 
                        {
                        	minimo.setValue((int) rango.getMaximo());
            				rango.setMinimo(minimo.getValue());	
                        }
                    });
				}	
				else
				{
					if(indicador == Indicador.COMPRA)
					{
						if(minimo.getValue() != rangos.darRangoCompra(indicador).getMinimo())
						{
							rangos.darRangoCompra(indicador).setMinimo(minimo.getValue());
							rangosGrafico.actualizarTodos();
						}
					}
					else
						rango.setMinimo(minimo.getValue());
				}
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
        				graficaProgreso.actualizarChartProgreso();
                    }
                });
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
        				graficaIndicador.actualizarGrafica(rango, indicador);
                    }
                });
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
                    	graficaHistorial.actualizarGraficas();
                    }
                });
			}
		});
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        add(minimo, gridBagConstraints);
        add(nombre, new GridBagConstraints());
        maximo = new JSlider((int) original.getMinimo(), (int) original.getMaximo());
        maximo.setValue((int) rango.getMaximo());
        maximo.setPreferredSize(new Dimension(600, 39));
        maximo.setMinorTickSpacing(Math.min(1, i.darEspaciado()));
        maximo.setMajorTickSpacing(i.darEspaciado());
        maximo.setPaintTicks(true);
        maximo.setSnapToTicks(true);
        maximo.setPaintLabels(true);
        if(i.tieneLabels())
        	maximo.setLabelTable(i.darLabels());
        else
        	maximo.setLabelTable(maximo.createStandardLabels(Math.max(i.darEspaciado(), 4)));
        maximo.addChangeListener(new ChangeListener() 
        {
			@Override
			public void stateChanged(ChangeEvent e) 
			{
		        boolean compra = ((int) rangos.darRangoCompra(Indicador.COMPRA).getMinimo()) == 1;
		        final Rango rango = compra ? rangos.darRangoCompra(indicador) : rangos.darRangoVenta(indicador);
				if(maximo.getValue() < rango.getMinimo())
				{
					SwingUtilities.invokeLater(new Runnable() 
					{
                        public void run() 
                        {
                        	maximo.setValue((int) rango.getMinimo());
                        	rango.setMaximo(maximo.getValue());
                        }
                    });
				}
				else
					rango.setMaximo(maximo.getValue());
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
        				graficaProgreso.actualizarChartProgreso();
                    }
                });
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
        				graficaIndicador.actualizarGrafica(rango, indicador);
                    }
                });
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
                    	graficaHistorial.actualizarGraficas();
                    }
                });
			}
		});
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        if(indicador != Indicador.COMPRA)
        	add(maximo, gridBagConstraints);
        invertido.addActionListener(new ActionListener() 
        {	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
		        boolean compra = ((int) rangos.darRangoCompra(Indicador.COMPRA).getMinimo()) == 1;
		        final Rango rango = compra ? rangos.darRangoCompra(indicador) : rangos.darRangoVenta(indicador);
				rango.setInvertido(invertido.isSelected());
				SwingUtilities.invokeLater(new Runnable() 
				{
                    public void run() 
                    {
        				graficaProgreso.actualizarChartProgreso();
        				graficaIndicador.actualizarGrafica(rango, indicador);
                    }
                });
			}
		});
        invertido.setText("invertido");
        invertido.setSelected(rango.isInvertido());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(invertido, gridBagConstraints);
    }

	public void actualizar() 
	{
		 boolean compra = ((int) rangos.darRangoCompra(Indicador.COMPRA).getMinimo()) == 1;
	     Rango rango = compra ? rangos.darRangoCompra(indicador) : rangos.darRangoVenta(indicador);
	     minimo.setValue((int) rango.getMinimo());
	     maximo.setValue((int) rango.getMaximo());
	}
}
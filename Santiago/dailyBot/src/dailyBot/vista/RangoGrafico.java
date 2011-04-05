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
        minimo = new JSlider((int) original.getMinimo(rangos), (int) original.getMaximo(rangos));
        Rango rango = rangos.darRango(indicador);
        if(indicador == Indicador.COMPRA)
        	minimo.setValue(2);
        else
        	minimo.setValue((int) rango.getMinimo(rangos));
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
		        int compra = (int) rangos.darRango(Indicador.COMPRA).getMinimoCompra();
		        final Rango rango = rangos.darRango(indicador);
		        if(indicador != Indicador.COMPRA && compra == 2)
		        	minimo.setValue((int) rango.getMinimo(rangos));
				if(indicador != Indicador.COMPRA && minimo.getValue() > rango.getMaximo(rangos))
				{
					SwingUtilities.invokeLater(new Runnable() 
					{
                        public void run() 
                        {
                        	minimo.setValue((int) rango.getMaximo(rangos));
            				rango.setMinimo(minimo.getValue(), rangos);	
                        }
                    });
				}	
				else
				{
					if(indicador == Indicador.COMPRA)
					{
						if(minimo.getValue() != rangos.darRango(Indicador.COMPRA).getMinimoCompra())
						{
							rangos.darRango(Indicador.COMPRA).setMinimoCompra(minimo.getValue());
							rangosGrafico.actualizarTodos();
						}
					}
					else
						rango.setMinimo(minimo.getValue(), rangos);
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
        maximo = new JSlider((int) original.getMinimoCompra(), (int) original.getMaximoCompra());
        maximo.setValue((int) rango.getMaximo(rangos));
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
		        int compra = (int) rangos.darRango(Indicador.COMPRA).getMinimoCompra();
		        final Rango rango = rangos.darRango(indicador);
		        if(indicador != Indicador.COMPRA && compra == 2)
		        	maximo.setValue((int) rango.getMaximo(rangos));
				if(indicador != Indicador.COMPRA && maximo.getValue() < rango.getMinimo(rangos))
				{
					SwingUtilities.invokeLater(new Runnable() 
					{
                        public void run() 
                        {
                        	maximo.setValue((int) rango.getMinimo(rangos));
                        	rango.setMaximo(maximo.getValue(), rangos);
                        }
                    });
				}
				else
					rango.setMaximo(maximo.getValue(), rangos);
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
		        final Rango rango = rangos.darRango(indicador);
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
	     if(indicador != Indicador.COMPRA)
		 {
		     Rango rango = rangos.darRango(indicador);
		     minimo.setValue((int) rango.getMinimo(rangos));
		     maximo.setValue((int) rango.getMaximo(rangos));
		 }
	}
}
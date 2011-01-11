package vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modelo.Par;
import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;

public class FormatoPares extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private IdProveedor idP;
	private IdEstrategia idE;
	private GraficaProgreso graficaProgreso;
	private GraficaHistorial graficaHistorial;
	
	public FormatoPares(IdProveedor i, IdEstrategia ii, GraficaProgreso gP, GraficaHistorial gH)
	{
		super();
		idP = i;
		idE = ii;
		graficaProgreso = gP;
		graficaHistorial = gH;
		initialize();
	}

	private void initialize() 
	{
		GridLayout gridLayout = new GridLayout();
		int filas = (Par.values().length - 1) % 2 == 0 ? (Par.values().length - 1) / 2 : (Par.values().length - 1) / 2 + 1;
		gridLayout.setRows(filas);
		gridLayout.setColumns(2);
		this.setLayout(gridLayout);
		LinkedList <Par> paresA = new LinkedList <Par> ();
		LinkedList <Par> paresB = new LinkedList <Par> ();
		int cuenta = 0;
		boolean enA = false;
		for(Par p : Par.values())
		{
			if(p == Par.TODOS)
				continue;
			if(cuenta++ == filas)
			{
				enA = true;
			}
			if(enA)
				paresA.add(p);
			else
				paresB.add(p);
		}
		boolean parA = false;
		for(int i = 0; !paresA.isEmpty(); i++)
		{
			if(parA)
				this.add(darBoton(paresA.pollFirst()));
			else
				this.add(darBoton(paresB.pollFirst()));
			parA = !parA;
		}
		this.setVisible(true);
	}

	private JCheckBox darBoton(Par p) 
	{
		JCheckBox nuevo = new JCheckBox();
		nuevo.setText(p.toString());
		nuevo.setSize(new Dimension(30, 30));
		nuevo.setSelected(idP.darProveedor().darActivo(idE, p));
		configurar(p, nuevo);
		return nuevo;
	}
	
	private void configurar(final Par par, JCheckBox box)
	{
    	box.addActionListener(new ActionListener()
    	{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				idP.darProveedor().cambiarActivo(idE, par, ((JCheckBox) e.getSource()).isSelected());
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
                    	graficaHistorial.actualizarGraficas();
                    }
                });
			}
		});
	}
}

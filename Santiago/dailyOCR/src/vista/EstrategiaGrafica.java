package vista;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.Par;
import modelo.Estrategia.IdEstrategia;
import analisis.AnalisisLogica;
import analisis.Rangos;

public class EstrategiaGrafica extends JFrame
{
	private static final long serialVersionUID = 7878714258759106938L;

	IdEstrategia idEstrategia;
	
	public EstrategiaGrafica(IdEstrategia id) 
	{	
		super();
		idEstrategia = id;
		initialize();
	}
	
	public EstrategiaGrafica()
	{
		super();
		idEstrategia = null;
		initialize();
	}

	private void initialize()
	{
		GridLayout gridLayout = new GridLayout(0, 2);
		this.setLayout(gridLayout);
		this.setSize(259, 490);
		for(Par par : Par.values())
			if(par != Par.TODOS)
				this.add(darBotonPar(par));
		pack();
		setVisible(true);
	}

	private JButton darBotonPar(final Par par) 
	{
		JButton botonNuevo = new JButton();
		botonNuevo.setText(par.toString());
		botonNuevo.addActionListener(new java.awt.event.ActionListener() 
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				new RangosGrafico(idEstrategia == null ? new Rangos() : idEstrategia.darEstrategia().getRangos()[par.ordinal()], AnalisisLogica.darRegistrosEstrategia(idEstrategia == null ? IdEstrategia.values()[((IdEstrategia) JOptionPane.showInputDialog(null, "Escoja la estrategia", "Analisis grafico", JOptionPane.QUESTION_MESSAGE, null, IdEstrategia.values(), IdEstrategia.BREAKOUT1)).ordinal()] : idEstrategia, par));
			}
		});
		return botonNuevo;
	}
	
	public static void main(String[] args)
	{
		new EstrategiaGrafica();
	}
}

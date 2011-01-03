package vista;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
import control.dailyOCR;

public class ParteGrafica extends JPanel
{
	private static final long serialVersionUID = 7878714258759106938L;

	public ParteGrafica() {
		super();
		initialize();
	}

	private void initialize()
	{
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(5);
		gridLayout.setColumns(2);
		this.setLayout(gridLayout);
		this.setSize(259, 290);
		for(IdEstrategia id : IdEstrategia.values())
			if(id != IdEstrategia.JOEL && id != IdEstrategia.TECHNICAL)
				this.add(darBotonEstrategia(id));
		for(IdProveedor id : IdProveedor.values())
				this.add(darBotonProveedor(id));
		JButton salir = new JButton();
		salir.setText("Salir");
		salir.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dailyOCR.salir();
			}
		});
		this.add(salir);
	}

	private JButton darBotonEstrategia(final IdEstrategia id) 
	{
		JButton botonNuevo = new JButton();
		botonNuevo.setText(id.toString());
		botonNuevo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				new Tabla(id);
			}
		});
		return botonNuevo;
	}
	
	private JButton darBotonProveedor(final IdProveedor id) 
	{
		JButton botonNuevo = new JButton();
		botonNuevo.setText(id.toString());
		botonNuevo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				new Tabla(id);
				new FormatoProveedor(id);
			}
		});
		return botonNuevo;
	}
	
	public static void main(String[] args)
	{
		JFrame framePrincipal = new JFrame();
		framePrincipal.setMinimumSize(new Dimension(259, 244));
		framePrincipal.setSize(259, 244);
		framePrincipal.add(new ParteGrafica());
		framePrincipal.pack();
		framePrincipal.setVisible(true);
		framePrincipal.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
}
  //  @jve:decl-index=0:visual-constraint="189,13"

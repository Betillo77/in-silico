package vista;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import control.IdEstrategia;




public class ParteGrafica extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton Breakout1 = null;
	private JButton Breakout2 = null;
	private JButton Range1 = null;
	private JButton Range2 = null;
	private JButton Momentum1 = null;
	private JButton Momentum2 = null;
	private JButton orden = null;
	private JButton salir = null;
	private JButton joel = null;
	private JButton technical = null;
	
	/**
	 * This is the default constructor
	 */
	public ParteGrafica() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(5);
		gridLayout.setColumns(2);
		this.setLayout(gridLayout);
		this.setSize(259, 290);
		this.add(getBreakout1(), null);
		this.add(getBreakout2(), null);
		this.add(getRange1(), null);
		this.add(getRange2(), null);
		this.add(getMomentum1(), null);
		this.add(getMomentum2(), null);
		this.add(getTechnical(), null);
		this.add(getJoel(), null);
		this.add(getOrden(), null);
		this.add(getSalir(), null);
	}

	/**
	 * This method initializes Breakout1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBreakout1() {
		if (Breakout1 == null) {
			Breakout1 = new JButton();
			Breakout1.setText("Breakout1");
			Breakout1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					new TablaEstrategia(IdEstrategia.BREAKOUT1);
					new FormatoPares(null, IdEstrategia.BREAKOUT1);
				}
			});
		}
		return Breakout1;
	}

	/**
	 * This method initializes Breakout2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBreakout2() {
		if (Breakout2 == null) {
			Breakout2 = new JButton();
			Breakout2.setText("Breakout2");
			Breakout2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new TablaEstrategia(IdEstrategia.BREAKOUT2);
					new FormatoPares(null, IdEstrategia.BREAKOUT2).setVisible(true);
				}
			});
		}
		return Breakout2;
	}

	/**
	 * This method initializes Range1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRange1() {
		if (Range1 == null) {
			Range1 = new JButton();
			Range1.setText("Range1");
			Range1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new TablaEstrategia(IdEstrategia.RANGE1);
					new FormatoPares(null, IdEstrategia.RANGE1);
				}
			});
		}
		return Range1;
	}

	/**
	 * This method initializes Range2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRange2() {
		if (Range2 == null) {
			Range2 = new JButton();
			Range2.setText("Range2");
			Range2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new TablaEstrategia(IdEstrategia.RANGE2);
					new FormatoPares(null, IdEstrategia.RANGE2);
				}
			});
		}
		return Range2;
	}

	/**
	 * This method initializes Momentum1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMomentum1() {
		if (Momentum1 == null) {
			Momentum1 = new JButton();
			Momentum1.setText("Momentum1");
			Momentum1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new TablaEstrategia(IdEstrategia.MOMENTUM1);
					new FormatoPares(null, IdEstrategia.MOMENTUM1);
				}
			});
		}
		return Momentum1;
	}

	/**
	 * This method initializes Momentum2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getMomentum2() {
		if (Momentum2 == null) {
			Momentum2 = new JButton();
			Momentum2.setText("Momentum2");
			Momentum2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new TablaEstrategia(IdEstrategia.MOMENTUM2);
					new FormatoPares(null, IdEstrategia.MOMENTUM2);
				}
			});
		}
		return Momentum2;
	}

	/**
	 * This method initializes orden	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOrden() {
		if (orden == null) {
			orden = new JButton();
			orden.setText("Ordenar");
			orden.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new FormatoOrdenar();
				}
			});
		}
		return orden;
	}

	/**
	 * This method initializes salir	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSalir() {
		if (salir == null) {
			salir = new JButton();
			salir.setText("Salir");
			salir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return salir;
	}

	/**
	 * This method initializes joel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJoel() {
		if (joel == null) {
			joel = new JButton();
			joel.setText("Joel");
			joel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new TablaEstrategia(IdEstrategia.JOEL);
					new AnalisisGrafico(IdEstrategia.JOEL);
				}
			});
		}
		return joel;
	}

	/**
	 * This method initializes technical	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getTechnical() {
		if (technical == null) {
			technical = new JButton();
			technical.setText("Technical");
			technical.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new TablaEstrategia(IdEstrategia.TECHNICAL);
					new AnalisisGrafico(IdEstrategia.TECHNICAL);
				}
			});
		}
		return technical;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
  //  @jve:decl-index=0:visual-constraint="189,13"

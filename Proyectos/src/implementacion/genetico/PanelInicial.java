package implementacion.genetico;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelInicial extends JPanel {

	private static final long serialVersionUID = 1L;
	public InfoObjeto bicicleta = null;
	public InfoObjeto conejita = null;
	public InfoObjeto elefante = null;
	public InfoObjeto libro = null;
	public InfoObjeto oso = null;
	public InfoObjeto portatil = null;
	public InfoObjeto tren = null;
	public InfoObjeto xbox = null;
	public JButton iniciar = null;
	/**
	 * This is the default constructor
	 */
	public PanelInicial() {
		super();
		initialize();
		setSize(new Dimension(1200,760));
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(867, 656);
		this.setLayout(null);
		this.add(getBicicleta(), null);
		this.add(getConejita(), null);
		this.add(getElefante(), null);
		this.add(getLibro(), null);
		this.add(getOso(), null);
		this.add(getPortatil(), null);
		this.add(getTren(), null);
		this.add(getXbox(), null);
		this.add(getIniciar(), null);
	}
	
	public void iniciar()
	{
		CromosomaPaquete.principal.iniciar();
	}

	/**
	 * This method initializes bicicleta	
	 * 	
	 * @return implementacion.genetico.infoObjeto	
	 */
	private InfoObjeto getBicicleta() {
		if (bicicleta == null) {
			bicicleta = new InfoObjeto("bicicleta.jpg", 35, 17);
			bicicleta.setBounds(new Rectangle(6, 12, 420, 139));
		}
		return bicicleta;
	}

	/**
	 * This method initializes conejita	
	 * 	
	 * @return implementacion.genetico.infoObjeto	
	 */
	private InfoObjeto getConejita() {
		if (conejita == null) {
			conejita = new InfoObjeto("conejita.jpg", 50, 22);
			conejita.setBounds(new Rectangle(7, 160, 420, 139));
		}
		return conejita;
	}

	/**
	 * This method initializes elefante	
	 * 	
	 * @return implementacion.genetico.infoObjeto	
	 */
	private InfoObjeto getElefante() {
		if (elefante == null) {
			elefante = new InfoObjeto("elefante.jpg", 40, 20);
			elefante.setBounds(new Rectangle(7, 307, 420, 139));
		}
		return elefante;
	}

	/**
	 * This method initializes libro	
	 * 	
	 * @return implementacion.genetico.infoObjeto	
	 */
	private InfoObjeto getLibro() {
		if (libro == null) {
			libro = new InfoObjeto("libro.jpg", 15, 8);
			libro.setBounds(new Rectangle(8, 452, 420, 139));
		}
		return libro;
	}

	/**
	 * This method initializes oso	
	 * 	
	 * @return implementacion.genetico.infoObjeto	
	 */
	private InfoObjeto getOso() {
		if (oso == null) {
			oso = new InfoObjeto("oso.jpg",  10, 4);
			oso.setBounds(new Rectangle(437, 12, 420, 139));
		}
		return oso;
	}

	/**
	 * This method initializes portatil	
	 * 	
	 * @return implementacion.genetico.infoObjeto	
	 */
	private InfoObjeto getPortatil() {
		if (portatil == null) {
			portatil = new InfoObjeto("portatil.jpg", 25, 13);
			portatil.setBounds(new Rectangle(438, 160, 420, 139));
		}
		return portatil;
	}

	/**
	 * This method initializes tren	
	 * 	
	 * @return implementacion.genetico.infoObjeto	
	 */
	private InfoObjeto getTren() {
		if (tren == null) {
			tren = new InfoObjeto("tren.jpg",  20, 10);
			tren.setBounds(new Rectangle(439, 307, 420, 139));
		}
		return tren;
	}

	/**
	 * This method initializes xbox	
	 * 	
	 * @return implementacion.genetico.infoObjeto	
	 */
	private InfoObjeto getXbox() {
		if (xbox == null) {
			xbox = new InfoObjeto("xbox.jpg", 30, 16);
			xbox.setBounds(new Rectangle(444, 452, 420, 139));
		}
		return xbox;
	}

	/**
	 * This method initializes iniciar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getIniciar() {
		if (iniciar == null) {
			iniciar = new JButton();
			iniciar.setBounds(new Rectangle(392, 611, 100, 29));
			iniciar.setText("Iniciar");
			iniciar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					iniciar();
				}
			});
		}
		return iniciar;
	}

}  //  @jve:decl-index=0:visual-constraint="7,10"

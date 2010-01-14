package implementacion.genetico;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class InfoObjeto extends JPanel {

	private static final long serialVersionUID = -986967150227481081L;
	
	public JSlider jSlider = null;
	private JLabel Imagen = null;
	private JLabel Peso = null;
	private JLabel Utilidad = null;
	public JSlider jSlider1 = null;
	private JLabel Peso1 = null;
	private JLabel Utilidad1 = null;
	private String archivo = null;
	
	/**
	 * This is the xxx default constructor
	 */
	public InfoObjeto(String archivo, int pesoInicial, int utilidadInicial) {
		super();
		this.archivo = archivo;
		initialize();
		this.jSlider.setValue(pesoInicial);
		this.jSlider1.setValue(utilidadInicial);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(187, 79));
		Utilidad1 = new JLabel();
		Utilidad1.setText("Utilidad");
		Utilidad1.setBounds(new Rectangle(392, 82, 34, 16));
		Peso1 = new JLabel();
		Peso1.setText("Peso");
		Peso1.setBounds(new Rectangle(391, 31, 35, 16));
		Utilidad = new JLabel();
		Utilidad.setText("Utilidad");
		Utilidad.setBounds(new Rectangle(144, 82, 49, 16));
		Peso = new JLabel();
		Peso.setText("Peso");
		Peso.setBounds(new Rectangle(156, 31, 38, 16));
		Imagen = new JLabel(new ImageIcon(CromosomaPaquete.principal.darImagen(archivo)));
		Imagen.setBounds(new Rectangle(17, 14, 111, 111));
        this.setLayout(null);
        this.setSize(new Dimension(439, 138));
        this.add(Imagen, null);
        this.add(Peso, null);
        this.add(getJSlider(), null);
        this.add(Peso1, null);
        this.add(Utilidad, null);
        this.add(getJSlider1(), null);
        this.add(Utilidad1, null);
        jSlider.setMinimum(0);
		jSlider.setMaximum(1000);
		jSlider.setValue(50);
		jSlider.addChangeListener(new ChangeListener()
													{
														public void stateChanged(ChangeEvent arg0) 
														{
															Peso1.setText(jSlider.getValue() + "");
														}
													});
		Peso1.setText(jSlider1.getValue() + "");
		jSlider1.setMinimum(0);
		jSlider1.setMaximum(1000);
		jSlider1.setValue(50);
		jSlider1.addChangeListener(new ChangeListener()
													{
														public void stateChanged(ChangeEvent arg0) 
														{
															Utilidad1.setText(jSlider1.getValue() + "");
														}
			
													});
		Utilidad1.setText(jSlider1.getValue() + "");
	}
	
	/**
	 * This method initializes jSlider	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider() {
		if (jSlider == null) {
			jSlider = new JSlider();
			jSlider.setBounds(new Rectangle(192, 31, 200, 16));
		}
		return jSlider;
	}

	/**
	 * This method initializes jSlider1	
	 * 	
	 * @return javax.swing.JSlider	
	 */
	private JSlider getJSlider1() {
		if (jSlider1 == null) {
			jSlider1 = new JSlider();
			jSlider1.setBounds(new Rectangle(192, 84, 200, 16));
		}
		return jSlider1;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

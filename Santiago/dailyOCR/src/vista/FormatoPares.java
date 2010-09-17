package vista;

import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import modelo.Estrategia;
import modelo.Par;
import modelo.dailyFx.EstrategiaElite;
import control.IdEstrategia;
import control.dailyOCR;

public class FormatoPares extends JDialog {

	private static final long serialVersionUID = 1L;
	private IdEstrategia id;
	private JPanel jContentPane = null;
	private JCheckBox eurusd = null;
	private JCheckBox usdjpy = null;
	private JCheckBox gbpusd = null;
	private JCheckBox usdchf = null;
	private JCheckBox eurchf = null;
	private JCheckBox audusd = null;
	private JCheckBox usdcad = null;
	private JCheckBox nzdusd = null;
	private JCheckBox eurjpy = null;
	private JCheckBox gbpjpy = null;
	private JCheckBox chfjpy = null;
	private JCheckBox gbpchf = null;
	private JCheckBox euraud = null;
	private JCheckBox audjpy = null;
	private boolean elite = false;
	/**
	 * @param owner
	 */
	public FormatoPares(Frame owner, IdEstrategia idEstrategia, boolean e) {
		super(owner, idEstrategia.toString());
		elite = e;
		id = idEstrategia;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 266);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getEurusd(), null);
			jContentPane.add(getUsdjpy(), null);
			jContentPane.add(getGbpusd(), null);
			jContentPane.add(getUsdchf(), null);
			jContentPane.add(getEurchf(), null);
			jContentPane.add(getAudusd(), null);
			jContentPane.add(getUsdcad(), null);
			jContentPane.add(getNzdusd(), null);
			jContentPane.add(getEurjpy(), null);
			jContentPane.add(getGbpjpy(), null);
			jContentPane.add(getChfjpy(), null);
			jContentPane.add(getGbpchf(), null);
			jContentPane.add(getEuraud(), null);
			jContentPane.add(getAudjpy(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes eurusd	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getEurusd() {
		if (eurusd == null) {
			eurusd = new JCheckBox();
			eurusd.setBounds(new Rectangle(34, 14, 84, 24));
			eurusd.setText("EUR/USD");
			configurar(Par.EURUSD, eurusd);
		}
		return eurusd;
	}

	/**
	 * This method initializes usdjpy	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getUsdjpy() {
		if (usdjpy == null) {
			usdjpy = new JCheckBox();
			usdjpy.setBounds(new Rectangle(34, 44, 84, 24));
			usdjpy.setText("USD/JPY");
			configurar(Par.USDJPY, usdjpy);
		}
		return usdjpy;
	}

	/**
	 * This method initializes gbpusd	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getGbpusd() {
		if (gbpusd == null) {
			gbpusd = new JCheckBox();
			gbpusd.setBounds(new Rectangle(34, 74, 84, 24));
			gbpusd.setText("GBP/USD");
			configurar(Par.GBPUSD, gbpusd);
		}
		return gbpusd;
	}

	/**
	 * This method initializes usdchf	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getUsdchf() {
		if (usdchf == null) {
			usdchf = new JCheckBox();
			usdchf.setBounds(new Rectangle(34, 104, 84, 24));
			usdchf.setText("USD/CHF");
			configurar(Par.USDCHF, usdchf);
		}
		return usdchf;
	}

	/**
	 * This method initializes eurchf	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getEurchf() {
		if (eurchf == null) {
			eurchf = new JCheckBox();
			eurchf.setBounds(new Rectangle(34, 134, 84, 24));
			eurchf.setText("EUR/CHF");
			configurar(Par.EURCHF, eurchf);
		}
		return eurchf;
	}

	/**
	 * This method initializes audusd	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getAudusd() {
		if (audusd == null) {
			audusd = new JCheckBox();
			audusd.setBounds(new Rectangle(34, 164, 87, 24));
			audusd.setText("AUD/USD");
			configurar(Par.AUDUSD, audusd);
		}
		return audusd;
	}

	/**
	 * This method initializes usdcad	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getUsdcad() {
		if (usdcad == null) {
			usdcad = new JCheckBox();
			usdcad.setBounds(new Rectangle(34, 194, 87, 24));
			usdcad.setText("USD/CAD");
			configurar(Par.USDCAD, usdcad);
		}
		return usdcad;
	}

	/**
	 * This method initializes nzdusd	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getNzdusd() {
		if (nzdusd == null) {
			nzdusd = new JCheckBox();
			nzdusd.setBounds(new Rectangle(161, 14, 87, 24));
			nzdusd.setText("NZD/USD");
			configurar(Par.NZDUSD, nzdusd);
		}
		return nzdusd;
	}

	/**
	 * This method initializes eurjpy	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getEurjpy() {
		if (eurjpy == null) {
			eurjpy = new JCheckBox();		
			eurjpy.setBounds(new Rectangle(161, 44, 84, 24));
			eurjpy.setText("EUR/JPY");
			configurar(Par.EURJPY, eurjpy);
		}
		return eurjpy;
	}

	/**
	 * This method initializes gbpjpy	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getGbpjpy() {
		if (gbpjpy == null) {
			gbpjpy = new JCheckBox();
			gbpjpy.setBounds(new Rectangle(161, 74, 84, 24));
			gbpjpy.setText("GBP/JPY");
			configurar(Par.GBPJPY, gbpjpy);
		}
		return gbpjpy;
	}

	/**
	 * This method initializes chfjpy	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getChfjpy() {
		if (chfjpy == null) {
			chfjpy = new JCheckBox();
			chfjpy.setBounds(new Rectangle(161, 104, 84, 24));
			chfjpy.setText("CHF/JPY");
			configurar(Par.CHFJPY, chfjpy);
		}
		return chfjpy;
	}

	/**
	 * This method initializes gbpchf	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getGbpchf() {
		if (gbpchf == null) {
			gbpchf = new JCheckBox();
			gbpchf.setBounds(new Rectangle(161, 134, 84, 24));
			gbpchf.setText("GBP/CHF");
			configurar(Par.GBPCHF, gbpchf);
		}
		return gbpchf;
	}

	/**
	 * This method initializes euraud	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getEuraud() {
		if (euraud == null) {
			euraud = new JCheckBox();
			euraud.setBounds(new Rectangle(161, 164, 84, 24));
			euraud.setText("EUR/AUD");
			configurar(Par.EURAUD, euraud);
		}
		return euraud;
	}

	/**
	 * This method initializes audjpy	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getAudjpy() {
		if (audjpy == null) {
			audjpy = new JCheckBox();
			audjpy.setBounds(new Rectangle(161, 194, 84, 24));
			audjpy.setText("AUD/JPY");
			configurar(Par.AUDJPY, audjpy);
		}
		return audjpy;
	}
	
	private void configurar(final Par par, JCheckBox box)
	{
		Estrategia estrategia = dailyOCR.darEstrategia(id);
		final EstrategiaElite eElite = (EstrategiaElite) dailyOCR.darEstrategia(IdEstrategia.ELITE);
		if(elite)
		{
			box.setSelected(eElite.darActivo(estrategia.getId(), par));
		}
		else
		{
			box.setSelected(estrategia.darActivo(par));
		}
		box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Estrategia estrategia = dailyOCR.darEstrategia(id);
				if(elite)
				{
					eElite.cambiarActivo(par, ((JCheckBox) e.getSource()).isSelected(), estrategia.getId());
				}
				else
				{
					estrategia.cambiarActivo(par, ((JCheckBox) e.getSource()).isSelected());
				}
			}
		});
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

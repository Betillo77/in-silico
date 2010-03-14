package vista;

import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import modelo.Estrategia;
import modelo.Senal;


import control.IdEstrategia;
import control.Par;
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
	/**
	 * @param owner
	 */
	public FormatoPares(Frame owner, IdEstrategia idEstrategia) {
		super(owner, idEstrategia.toString());
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.EURUSD)
				{
					eurusd.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			eurusd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.EURUSD, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.USDJPY)
				{
					usdjpy.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			usdjpy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.USDJPY, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.GBPUSD)
				{
					gbpusd.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			gbpusd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.GBPUSD, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.USDCHF)
				{
					usdchf.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			usdchf.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.USDCHF, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.EURCHF)
				{
					eurchf.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			eurchf.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.EURCHF, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.AUDUSD)
				{
					audusd.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			audusd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.AUDUSD, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.USDCAD)
				{
					usdcad.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			usdcad.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.USDCAD, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.NZDUSD)
				{
					nzdusd.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			nzdusd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.NZDUSD, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.EURJPY)
				{
					eurjpy.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			eurjpy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.EURJPY, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.GBPJPY)
				{
					gbpjpy.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			gbpjpy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.GBPJPY, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.CHFJPY)
				{
					chfjpy.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			chfjpy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.CHFJPY, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.GBPCHF)
				{
					gbpchf.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			gbpchf.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.GBPCHF, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.EURAUD)
				{
					euraud.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			euraud.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.EURAUD, ((JCheckBox) e.getSource()).isSelected());
				}
			});
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
			Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
			int i = 0;
			for(Par p : Par.values())
			{
				if(p == Par.AUDJPY)
				{
					audjpy.setSelected(estrategia.getActivos()[i]);
					break;
				}
				i++;
			}
			audjpy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estrategia estrategia = dailyOCR.darEstrategiaSenal(new Senal(id, false, Par.AUDJPY, 0, 0));
					estrategia.cambiarActivo(Par.AUDJPY, ((JCheckBox) e.getSource()).isSelected());
				}
			});
		}
		return audjpy;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

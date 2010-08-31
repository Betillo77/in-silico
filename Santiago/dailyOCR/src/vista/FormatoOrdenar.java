package vista;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class FormatoOrdenar 
{		
	public FormatoOrdenar()
	{
		JFrame frame = new JFrame();
		frame.setSize(400, 100);
		String [] Estrategias = {"BREAKOUT1", "BREAKOUT2", "RANGE1", "RANGE2", "MOMENTUM1", "MOMENTUM2"};
		String [] Pares = {"EURUSD", "USDJPY", "GBPUSD", "USDCHF", "EURCHF", "AUDUSD", "USDCAD","NZDUSD", "EURJPY", "GBPJPY", "CHFJPY", "GBPCHF", "EURAUD", "AUDJPY"};
		String [] NumeroLotes = {"1","2","3","4","5"};
		final JComboBox estrategia = new JComboBox(Estrategias);
		final JComboBox Par1 = new JComboBox(Pares);
		final JComboBox NumeroDeLotes = new JComboBox(NumeroLotes);
		final JRadioButton Comprar = new JRadioButton("Comprar");
		final JRadioButton NoComprar = new JRadioButton("Vender");
		NoComprar.setSelected(true);
		ButtonGroup group = new ButtonGroup();
		group.add(Comprar);
		group.add(NoComprar);
		JButton Enviar = new JButton("Enviar");
		Container c = frame.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(estrategia);
		c.add(Par1);
		c.add(Comprar);
		c.add(NoComprar);
		c.add(NumeroDeLotes);
		c.add(Enviar);
		frame.setVisible(true);
	}
}

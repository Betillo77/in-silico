import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class FormatoOrdenar 
{		
	public FormatoOrdenar()
	{
		JFrame frame = new JFrame();
		frame.setSize(400, 100);
		String [] Estrategias = {"BREAKOUT1", "BREAKOUT2", "RANGE1", "RANGE2", "MOMENTUM1", "MOMENTUM2"};
		String [] Pares = {"EURUSD", "USDJPY", "GBPUSD", "USDCHF", "EURCHF", "AUDUSD", "USDCAD","NZDUSD", "EURJPY", "GBPJPY", "CHFJPY2", "GBPCHF", "EURAUD", "AUDJPY"};
		String [] NumeroLotes = {"1","2","3","4","5"};

		final JComboBox estrategia = new JComboBox(Estrategias);
		final JComboBox Par = new JComboBox(Pares);
		final JComboBox NumeroDeLotes = new JComboBox(NumeroLotes);
		final JRadioButton Comprar = new JRadioButton("Comprar");
		final JRadioButton NoComprar = new JRadioButton("Vender");
		NoComprar.setSelected(true);

		ButtonGroup group = new ButtonGroup();
	
		group.add(Comprar);
		group.add(NoComprar);
	
		final JTextField PrecioEntrada = new JTextField();
		PrecioEntrada.setText("Ingrese aqui el precio de Entrada");
	
		JButton Enviar = new JButton("Enviar");
	
		Enviar.addActionListener(new ActionListener() 
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	IdEstrategia temp = IdEstrategia.BREAKOUT1;
		    	
		    	switch(estrategia.getSelectedIndex())
		    	{
			    	case 0:
			    		temp = IdEstrategia.BREAKOUT1;
			    		break;
			    	case 1:
			    		temp = IdEstrategia.BREAKOUT2;
			    		break;
			    	case 2:
						temp = IdEstrategia.MOMENTUM1;
			    		break;
			    	case 3:     
			    		temp = IdEstrategia.MOMENTUM2;
			    		break;
			    	case 4:
			    		temp = IdEstrategia.RANGE1;
			    		break;
			        case 5:
			        	temp = IdEstrategia.RANGE2;
			        	break;
		    	}
		    	boolean temp2 = false;
		    	if(Comprar.isSelected())
		    	{
		    		temp2 = true;
		    	}
		    	else
		    	{
		    		temp2 = false;
		    	}
		    	
		    	Par temp3 = dailyOCR.convertirPar(Par.getSelectedItem().toString());
		    	
		    	int temp4 = NumeroDeLotes.getSelectedIndex()+1;
		    	
		    	int i = 0;
		    	
		    	while(!(dailyOCR.preciosActuales.get(i).currency.equals(temp3)))
		    	{
		    		i++;
		    	}
		    	double temp5;
		    	if(temp2 == true)
		    	{
		    		temp5 = dailyOCR.preciosActuales.get(i).bid;
		    	}
		    	else
		    	{
		    		temp5 = dailyOCR.preciosActuales.get(i).bid;
		    	}
		    	Senal nueva = new Senal(temp, temp2, temp3, temp4, temp5);
		    	synchronized(dailyOCR.darEstrategiaSenal(nueva).senales)
		    	{
		    		Thread.yield();
		    		dailyOCR.abrirSenalManual(nueva);
		    	}
		    }
		});      
	
		Container c = frame.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(estrategia);
		c.add(Par);
		c.add(Comprar);
		c.add(NoComprar);
		c.add(NumeroDeLotes);
		c.add(Enviar);
		frame.setVisible(true);
	}

	
			public static void main(String []args)
			{
				new FormatoOrdenar();
			}
}

package dailyBot.vista;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Ventana extends JFrame
{
	private static final long serialVersionUID = -8975030219203093917L;

	public Ventana(String nombre, Dimension tamano, Object[][] componentes)
	{
		super(nombre);
		setSize(tamano);
		for(Object[] o : componentes)
			add((Component) o[0], o[1]);
		pack();
		setVisible(true);
	}
}
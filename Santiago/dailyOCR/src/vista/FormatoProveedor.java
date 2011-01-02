package vista;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;

public class FormatoProveedor extends JFrame 
{
	private static final long serialVersionUID = 2552180741753628128L;

	public FormatoProveedor(IdProveedor id)
	{
		super(id.toString());
		JTabbedPane jtp = new JTabbedPane();
		for(IdEstrategia id1 : IdEstrategia.values())
		{
			if(id1 != IdEstrategia.JOEL && id1 != IdEstrategia.TECHNICAL)
				jtp.addTab(id1.toString(), new FormatoPares(id, id1));
		}
		jtp.setVisible(true);
		setMinimumSize(new Dimension(259, 244));
		setSize(259, 244);
		add(jtp);
		pack();
		setVisible(true);
	}
}

package dailyBot.vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import dailyBot.control.Error;
import dailyBot.control.dailyBot;
import dailyBot.control.conexion.ConexionRMI;
import dailyBot.control.conexion.ConexionServidorRMI;
import dailyBot.modelo.Estrategia.IdEstrategia;
import dailyBot.modelo.Proveedor.IdProveedor;

public class ParteGrafica extends JPanel
{
	private static final long serialVersionUID = 7878714258759106938L;
	
	public static ConexionRMI conexion;

	public ParteGrafica() 
	{
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
				dailyBot.salir();
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
				new EstrategiaGrafica(id, true);
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
        System.setSecurityManager(new SecurityManager());
        try 
        {
            String name = "Conexion";
            Registry registry = LocateRegistry.getRegistry("192.168.0.105");
            conexion = new ConexionServidorRMI.Local((ConexionRMI) registry.lookup(name));
        } 
        catch (Exception e)
        {        	
        	Error.agregarRMI(e.getMessage() + " Error haciendo la conexion RMI");
        	System.exit(0);
        }
		JFrame framePrincipal = new JFrame();
		framePrincipal.setMinimumSize(new Dimension(259, 244));
		framePrincipal.setSize(259, 244);
		framePrincipal.add(new ParteGrafica());
		framePrincipal.pack();
		framePrincipal.setVisible(true);
		framePrincipal.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
}
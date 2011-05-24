package dailyBot.vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import dailyBot.control.DailyBot;
import dailyBot.control.Error;
import dailyBot.control.conexion.ConexionRMI;
import dailyBot.control.conexion.ConexionServidorRMI;
import dailyBot.modelo.Estrategia.IdEstrategia;
import dailyBot.modelo.Proveedor.IdProveedor;

public class VentanaPrincipal extends JFrame
{
	private static final long serialVersionUID = 7878714258759106938L;
	
	public static ConexionRMI conexion;

	public VentanaPrincipal() 
	{
		super("DailyBot");
		initialize();
	}

	private void initialize()
	{
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(5);
		gridLayout.setColumns(2);
		setLayout(gridLayout);
		setSize(259, 290);
		for(IdEstrategia id : IdEstrategia.values())
			if(id != IdEstrategia.JOEL && id != IdEstrategia.TECHNICAL)
				this.add(darBotonEstrategia(id));
		for(IdProveedor id : IdProveedor.values())
				this.add(darBotonProveedor(id));
		JButton salir = new JButton();
		salir.setText("Salir");
		salir.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				DailyBot.salir();
			}
		});
		add(salir);
		setSize(new Dimension(259, 244));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private JButton darBotonEstrategia(final IdEstrategia id) 
	{
		JButton botonNuevo = new JButton();
		botonNuevo.setText(id.toString());
		botonNuevo.addActionListener(new java.awt.event.ActionListener() 
		{
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
		botonNuevo.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				new Tabla(id);
				new FormatoProveedor(id);
			}
		});
		return botonNuevo;
	}
	
	public static void iniciar(boolean servidor)
	{
        System.setSecurityManager(new SecurityManager());
        try 
        {
            String name = "Conexion";
            Registry registry = LocateRegistry.getRegistry("186.86.4.148");
            conexion = new ConexionServidorRMI.Local((ConexionRMI) registry.lookup(name));
        } 
        catch (Exception e)
        {        	
        	if(servidor)
        	{
            	Error.agregar(e.getMessage() + " Error haciendo la conexion RMI");
            	Error.reiniciarSinPersistir();
        	}
        	else
        	{
        		Error.agregarRMI(e.getMessage() + " Error haciendo la conexion RMI");
        		System.exit(0);
        	}
        }
        new VentanaPrincipal();
	}
	
	public static void main(String[] args)
	{
		DailyBot.iniciarPropiedades();
		iniciar(false);
	}
}
package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import control.Error;

import modelo.SenalEstrategia;
import modelo.SenalProveedor;
import modelo.Estrategia.IdEstrategia;
import modelo.Proveedor.IdProveedor;
	
public class Tabla extends JFrame
{
	private static final long serialVersionUID = -1585479366228991191L;
	
	IdEstrategia estrategia;
	final static String nombreCol[] = {"Estrategia", "Compra", "Par", "Precio de entrada", "Ganancia"};
	JTable table;
	Object[][] mostrar;
	     
	public Tabla(IdEstrategia estrategia) 
	{
		super(estrategia.toString());
		llenarMostrar(estrategia);
		crearTabla();
	}
	
	public Tabla(IdProveedor proveedor)
	{
		super(proveedor.toString());
		llenarMostrar(proveedor);
		crearTabla();
	}
	
	private void llenarMostrar(IdEstrategia id)
	{
		DecimalFormat df = new DecimalFormat("0.0000");
		List <SenalEstrategia> listaE = null;
		try
		{
			listaE = ParteGrafica.conexion.darSenalesEstrategia(id.ordinal());
		}
		catch(Exception e)
		{
			Error.agregarRMI("Se produjo una excepcion de conexion remota " + e.getMessage());
		}
		mostrar = new Object[listaE.size()][5];
		for(int i = 0; i < listaE.size(); i++)
		{
			mostrar[i][0] = listaE.get(i).getEstrategia().toString();
			mostrar[i][1] = listaE.get(i).isCompra() + " toco: " + listaE.get(i).isTocoStop();
			mostrar[i][2] = listaE.get(i).getPar().toString();
			mostrar[i][3] = df.format(listaE.get(i).getPrecioEntrada()) + " " + df.format(listaE.get(i).darStopDaily()) + " " + df.format(listaE.get(i).darStop());
			mostrar[i][4] = listaE.get(i).darGanancia();
		}
	}
	
	private void llenarMostrar(IdProveedor proveedor) 
	{
		DecimalFormat df = new DecimalFormat("0.0000");
		List <SenalProveedor> listaE = null;
		try
		{
			listaE = ParteGrafica.conexion.darSenalesProveedor(proveedor.ordinal());
		}
		catch(Exception e)
		{
			Error.agregarRMI("Se produjo una excepcion de conexion remota " + e.getMessage());
		}
		mostrar = new Object[listaE.size()][5];
		for(int i = 0; i < listaE.size(); i++)
		{
			mostrar[i][0] = listaE.get(i).getEstrategia().toString();
			mostrar[i][1] = listaE.get(i).isCompra() + " toco: " + listaE.get(i).darTocoStop();
			mostrar[i][2] = listaE.get(i).getPar().toString();
			mostrar[i][3] = df.format(listaE.get(i).darPrecioEntrada()) + " " + df.format(listaE.get(i).darStopDaily()) + " " + df.format(listaE.get(i).darStop());
			mostrar[i][4] = listaE.get(i).darGanancia() + " " + listaE.get(i).getMagico();
		}
	}

	private void crearTabla()
	{
		table = new JTable(mostrar, nombreCol);
		table.setPreferredScrollableViewportSize(new Dimension(1000, 97));
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		ListSelectionModel listMod = table.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMod.addListSelectionListener(table);
		table.setModel(new AbstractTableModel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public int getColumnCount() 
			{
				return 5;
			}

			@Override
			public int getRowCount() 
			{
				return mostrar.length;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) 
			{
				try
				{
					return mostrar[rowIndex][columnIndex];
				}
				catch(Exception e)
				{
					return "";
				}
			}

			@Override
			public boolean isCellEditable(int a, int b)
			{
				return false;
			}

		});
		pack();
		setVisible(true);
	}
}
package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import modelo.Senal;
import control.IdEstrategia;
	
public class TablaEstrategia extends JFrame
{
	private static final long serialVersionUID = -1585479366228991191L;
	
	IdEstrategia estrategia;
	final static String nombreCol[] = {"Estrategia","Compra","Par","Numero de lotes","Precio de entrada","Numero magico"};
	static JTable table;
	static List <Senal> lista;
	     
	public TablaEstrategia(final IdEstrategia estrategia) 
	{
		super(estrategia.toString());
		lista = estrategia.darEstrategia().getSenalesCopy();
		final Object[][] mostrar = new Object[lista.size()][6];
		for(int i = 0; i < lista.size(); i++)
		{
			mostrar[i][0] = lista.get(i).getEstrategia().toString();
			mostrar[i][1] = lista.get(i).isCompra();
			mostrar[i][2] = lista.get(i).getPar().toString();
			mostrar[i][3] = lista.get(i).getNumeroLotes();
			mostrar[i][4] = lista.get(i).getPrecioEntrada();
			int [] temp = lista.get(i).darMagicoCopy();
			String temp2 = "";
			for(int j=0;j<temp.length-1;j++)
			{
				temp2 += temp[j]+", "; 	 
			}
			temp2 += temp[temp.length-1];
			mostrar[i][5] = temp2;   	 
		}
		table = new JTable(mostrar, nombreCol);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
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
				return 6;
			}

			@Override
			public int getRowCount() 
			{
				return lista.size();
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
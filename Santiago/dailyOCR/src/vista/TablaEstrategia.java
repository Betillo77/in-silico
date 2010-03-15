package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

import modelo.Senal;
import control.IdEstrategia;
import control.dailyOCR;
	
public class TablaEstrategia extends JFrame
{
	private static final long serialVersionUID = -1585479366228991191L;
	
	IdEstrategia estrategia;
	final static String nombreCol[] = {"Estrategia","Compra","Par","Numero de lotes","Precio de entrada","Numero magico"};
	static JTable table;
	static List <Senal> lista;
	     
	public TablaEstrategia(IdEstrategia estrategia) 
	{
		super(estrategia.toString());
		lista = dailyOCR.darSenalesEstrategia(estrategia);  
		synchronized(lista)
		{
			final Object[][] mostrar = new Object[lista.size()][6];
			for(int i = 0; i < lista.size(); i++)
			{
				mostrar[i][0] = lista.get(i).getEstrategia().toString();
				mostrar[i][1] = lista.get(i).isCompra();
				mostrar[i][2] = lista.get(i).getPar().toString();
				mostrar[i][3] = lista.get(i).getNumeroLotes();
				mostrar[i][4] = lista.get(i).getPrecioEntrada();
				int [] temp = lista.get(i).getMagico();
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
	        table.addMouseListener(new MouseAdapter()
							        {
							        	public void mouseClicked(MouseEvent e)
							        	{
							        		if (e.getClickCount() == 2)
							        		{
							        			JTable tabla = (JTable) e.getSource();
							        			int temp = JOptionPane.showConfirmDialog(table, "En realidad desea cerrar esta se�al?", "Cerrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							        			if(temp == 0)
							        			{
							        				dailyOCR.cerrarSenalManual(lista.get(tabla.getSelectedRow()));
							        			}
							        		}
							        	}
							        });
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
	        
			Timer t = new Timer(1000, null);
			t.addActionListener(
			new java.awt.event.ActionListener() 
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					for(int i = 0; i < lista.size(); i++)
					{
						try
						{
							mostrar[i][0] = lista.get(i).getEstrategia().toString();
						}
						catch(Exception e1)
						{
							return;
						}
						mostrar[i][1] = lista.get(i).isCompra();
						mostrar[i][2] = lista.get(i).getPar().toString();
						mostrar[i][3] = lista.get(i).getNumeroLotes();
						mostrar[i][4] = lista.get(i).getPrecioEntrada();
						int [] temp = lista.get(i).getMagico();
						String temp2 = "";
						for(int j=0;j<temp.length-1;j++)
						{
							temp2 += temp[j]+", "; 	 
						}
						temp2 += temp[temp.length-1];
						mostrar[i][5] = temp2;   	 
						AbstractTableModel atm = (AbstractTableModel) table.getModel();
						atm.fireTableCellUpdated(i, 0);
						atm.fireTableCellUpdated(i, 1);
						atm.fireTableCellUpdated(i, 2);
						atm.fireTableCellUpdated(i, 3);
						atm.fireTableCellUpdated(i, 4);
						atm.fireTableCellUpdated(i, 5);
					}
				}
			});
			t.start();
		}
	}
}
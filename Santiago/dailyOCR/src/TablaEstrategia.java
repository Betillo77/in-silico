import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
	
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
	//	setDefaultCloseOperation(JFrame.)
		lista = dailyOCR.darSenalesEstrategia(estrategia);   
		final Object[][] mostrar = new Object[lista.size()][6];
		for(int i = 0; i < lista.size(); i++)
		{
			mostrar[i][0] = lista.get(i).estrategia.toString();
			mostrar[i][1] = lista.get(i).compra;
			mostrar[i][2] = lista.get(i).par.toString();
			mostrar[i][3] = lista.get(i).numeroLotes;
			mostrar[i][4] = lista.get(i).precioEntrada;
			int [] temp = lista.get(i).magico;
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
        			int temp = JOptionPane.showConfirmDialog(table, "En realidad desea cerrar esta señal?", "Cerrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
						mostrar[i][0] = lista.get(i).estrategia.toString();
					}
					catch(Exception e1)
					{
						return;
					}
					mostrar[i][1] = lista.get(i).compra;
					mostrar[i][2] = lista.get(i).par.toString();
					mostrar[i][3] = lista.get(i).numeroLotes;
					mostrar[i][4] = lista.get(i).precioEntrada;
					int [] temp = lista.get(i).magico;
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
	   
	     

	public static void main(String[] args) 
	{	 
		Senal a=new Senal(IdEstrategia.BREAKOUT1,true,Par.AUDUSD,2,0.32);
		int [] testing={0,0,0,0,0,9};
		a.magico=testing;
		ArrayList<Senal> test=new ArrayList<Senal>();
		test.add(a);
		test.add(a);
		test.add(a);
		test.add(a);
		test.add(a);
		test.add(a);
		TablaEstrategia prueba = new TablaEstrategia(IdEstrategia.BREAKOUT1);
		prueba.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}
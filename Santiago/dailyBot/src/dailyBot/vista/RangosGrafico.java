package dailyBot.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dailyBot.analisis.Indicador;
import dailyBot.analisis.Rangos;
import dailyBot.analisis.RegistroHistorial;
import dailyBot.control.Error;
import dailyBot.modelo.Estrategia.IdEstrategia;
import dailyBot.modelo.Par;
import dailyBot.modelo.Proveedor.IdProveedor;

public class RangosGrafico extends JFrame
{
	private static final long serialVersionUID = -3243368885519444393L;
	
	public RangosGrafico(final Rangos[] rangos, List <RegistroHistorial> registros, final IdEstrategia idEstrategia, final Par par, String titulo)
	{
		super(titulo);
		final JFrame este = this;
		JTabbedPane panel = new JTabbedPane();
		for(int i = 0; i < rangos.length; i++)
		{
			JComponent actual = new RangosProveedor(rangos[i], registros, idEstrategia, par, titulo, IdProveedor.values()[i]);
			panel.addTab(IdProveedor.values()[i].toString(), actual);
		}
		add(panel);
	    addWindowListener(new WindowAdapter() 
	    {
	    	@Override
	        public void windowClosing(WindowEvent e)
	        {
	    		try
	    		{
	    			if(idEstrategia != null && JOptionPane.showConfirmDialog(este, "Guardar el rango?", "Guardar " + idEstrategia + " " + par, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
	    			{
	    				for(int i = 0; i < rangos.length; i++)
	    					VentanaPrincipal.conexion.cambiarRangosProveedor(i, idEstrategia.ordinal(), par.ordinal(), rangos[i]);
	    			}
				}
	            catch (Exception e1)
	            {        	
	            	Error.agregarRMI(e1.getMessage() + " Error haciendo la conexion RMI");
	            	System.exit(0);
	            }
	        }
	    });
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		pack();
		setVisible(true);
	}
	
	class RangosProveedor extends JPanel
	{
		private static final long serialVersionUID = -6260738056337041545L;
		
		private ArrayList <RangoGrafico> rangosGrafico = new ArrayList <RangoGrafico> ();
		
		public RangosProveedor(final Rangos rangos, List <RegistroHistorial> registros, final IdEstrategia idEstrategia, final Par par, String titulo, IdProveedor idProveedor)
		{
			GraficaProgreso graficaProgreso = new GraficaProgreso(rangos, registros);
			final GraficaIndicador graficaIndicador = new GraficaIndicador(Indicador.VIX.darRango().duplicar(), registros, Indicador.VIX, rangos);
			GraficaHistorial graficaHistorial = new GraficaHistorial(rangos, registros, idProveedor.toString());
			JPanel panelRangos = new JPanel();
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(6);
			gridLayout.setColumns(1);
			panelRangos.setLayout(gridLayout);
			panelRangos.setSize(350, 600);
			for(Indicador i : Indicador.values())
			{
				RangoGrafico r = new RangoGrafico(this, i.darRango().duplicar(), rangos, graficaProgreso, graficaIndicador, graficaHistorial, i);
				rangosGrafico.add(r);
				panelRangos.add(r);
			}
			graficaIndicador.unico = false;
			JPanel panelGraficas = new JPanel();
			panelGraficas.setLayout(new BorderLayout());
			panelGraficas.add(graficaProgreso, BorderLayout.SOUTH);
			panelGraficas.add(graficaIndicador, BorderLayout.NORTH);
			setLayout(new BorderLayout());
			add(panelRangos, BorderLayout.WEST);
			add(panelGraficas, BorderLayout.EAST);
		}
	
		public void actualizarTodos()
		{
			for(RangoGrafico r : rangosGrafico)
				r.actualizar();
		}
	}
}
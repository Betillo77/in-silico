package vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoGrafica extends JPanel 
{
	private static final long serialVersionUID = 3143214002376719420L;
	
    JLabel desviacion;
    private JLabel desviacionL;
    JLabel ganancia;
    private JLabel gananciaL;
    JLabel numeroTransacciones;
    private JLabel numeroTransaccionesL;
    JLabel promedioPips;
    private JLabel promedioPipsL;
	
	public InfoGrafica() {
        gananciaL = new JLabel();
        promedioPipsL = new JLabel();
        numeroTransaccionesL = new JLabel();
        desviacionL = new JLabel();
        ganancia = new JLabel();
        promedioPips = new JLabel();
        numeroTransacciones = new JLabel();
        desviacion = new JLabel();
        gananciaL.setText("Ganancia:");
        promedioPipsL.setText("Promedio pips:");
        numeroTransaccionesL.setText("Transacciones:");
        desviacionL.setText("Desviacion:");
        ganancia.setText("jLabel1");
        promedioPips.setText("jLabel2");
        numeroTransacciones.setText("jLabel3");
        desviacion.setText("jLabel4");
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(desviacion)
                        .addComponent(desviacionL)
                        .addComponent(numeroTransacciones)
                        .addComponent(numeroTransaccionesL)
                        .addComponent(promedioPips)
                        .addComponent(promedioPipsL)
                        .addComponent(ganancia)
                        .addComponent(gananciaL))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(100, 100, 100)
                    .addComponent(gananciaL)
                    .addGap(12, 12, 12)
                    .addComponent(ganancia)
                    .addGap(18, 18, 18)
                    .addComponent(promedioPipsL)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(promedioPips)
                    .addGap(18, 18, 18)
                    .addComponent(numeroTransaccionesL)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(numeroTransacciones)
                    .addGap(18, 18, 18)
                    .addComponent(desviacionL)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(desviacion))
            );
    }

    public static void main(String[] args)
    {
    	JFrame jf = new JFrame();
    	jf.add(new InfoGrafica());
    	jf.pack();
    	jf.setVisible(true);
    }
}

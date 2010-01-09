package implementacion.genetico;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class grafico extends JFrame {

	private static final long serialVersionUID = 1L;
	
	int Mactual [] = {4,2,1,2,5,8,2,3};
	int pesos [] = {2,3,3,6,3,2,7,9};
	int utilidades [] = {2,3,3,6,3,2,7,9};;
	
	public grafico() {
	
		setSize(new Dimension(1200,780));	
		//repaint();
	}
	
	@Override
	public synchronized void paint(Graphics g){
		String []direccion={"avioneta.jpg","balon1.jpg","batman.jpg","casita.jpg","muñeco.jpg","patin.jpg","peluche2.jpeg","superman.jpg"};
		super.paint(g);
		g.drawString("Peso", 1000, 100);
		g.drawString("Aptitud", 1100, 100);
		int y=700;
		int x=50;
		int pt=0;
		int ut=0;
		int i=0;
		try {
			for(i=0;i<Mactual.length;i++){
				y=700;
				for(int j=0;j<Mactual[i];j++)
				{
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					g.drawImage(ImageIO.read(new File(direccion[i])),x,y,null);
					pt+=pesos[i];
					ut+=utilidades[i];
					y-=55;
					
				}
				char c = (char) ('a'+i);
				String a = c+" : "+Mactual[i];
				g.drawString(a, x, 760);
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				x+=100;	
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawString(""+pt, 1000, 120);
		g.drawString(""+ut, 1100, 120);
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		grafico f=new grafico();
		f.setVisible(true);
		f.repaint();
		
	}

}

package implementacion.genetico;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class grafico extends JFrame implements MouseListener
{

	private static final long serialVersionUID = 1L;
	
	int Mactual [] = {4,2,1,2,5,8,2,3};
	int pesos [] =      {2,3,3,6,3,2,7,9};
	int utilidades [] = {2,3,3,6,3,2,7,9};
	static Random random = new Random();
	
	static BufferedImage santa;
	
	static
	{
		try
		{
			santa = ImageIO.read(new File("santa-claus.jpg"));
		} 
		catch (IOException e) 
		{
		}
	}
	
    
	public grafico() {
	
		setSize(new Dimension(1200,760));	
		//repaint();
	}
	
	@Override
	public synchronized void paint(Graphics g){
		String [] direccion = {"tren.jpg", "oso.jpg", "libro.jpg", "conejita.jpg", "xbox.jpg", "portatil.jpg", "bicicleta.jpg", "elefante.jpg"};
		ArrayList <Rectangle2D> figuras = new ArrayList <Rectangle2D> ();
		super.paint(g);
		g.drawImage(santa, 0, 0, 1200, 760, 0, 0, 1900, 1200, this);
		int pt=0;
		int ut=0;
		int i=0;
		try 
		{
			for(i = 0;i < Mactual.length; i++)
			{
				for(int j = 0;j < Mactual[i]; j++)
				{
					try 
					{
						Thread.sleep(150);
					} 
					catch (InterruptedException e) 
					{
					}
					Point puntoEscritura = darPuntoValido(figuras);
					g.drawImage(ImageIO.read(new File(direccion[i])), puntoEscritura.x, puntoEscritura.y, puntoEscritura.x + 50, puntoEscritura.y + 50, 0, 0, 130, 130, this);
					pt+=pesos[i];
					ut+=utilidades[i];
					
				}
			}
			
		} 
		catch (IOException e) 
		{
		}
		g.drawString("Peso:    " +  pt, 840, 90);
		g.drawString("Aptitud:  " + ut, 840, 120); 
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	public static Point darPuntoValido(ArrayList <Rectangle2D> figuras) 
	{
		while(true)
		{
			int x = random.nextInt(900);
			int y = random.nextInt(500);
			if(isInsideEllipse(x + 127, y + 131) && isInsideEllipse(x, y) && isInsideEllipse(x, y + 131) && isInsideEllipse(x + 127, y))
			{
				Rectangle2D este = new Rectangle2D.Double(x, y, 50, 50);
				boolean siSirve = true;
				for(Rectangle2D otro : figuras)
				{
					if(este.intersects(otro))
					{
						siSirve = false;
						break;
					}	
				}
				if(siSirve)
				{
					figuras.add(este);
					return new Point(x, y);
				}
			}
				
		}
	}

	
	 static boolean isInsideEllipse(int px,int py) 
	 {
		 Ellipse2D.Double elipse = new Ellipse2D.Double(103, 30, 750, 392);
		 return elipse.contains(px, py);
	 }  
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		grafico f=new grafico();
		f.setVisible(true);
		f.repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

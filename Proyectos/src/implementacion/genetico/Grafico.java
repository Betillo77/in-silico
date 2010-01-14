package implementacion.genetico;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class Grafico extends Canvas
{

	private static final long serialVersionUID = 1L;
	
	int Mactual [] = {4,2,1,2,5,8,2,3};
	int pesos [] =      {2,3,3,6,3,2,7,9};
	int utilidades [] = {2,3,3,6,3,2,7,9};
	static Random random = new Random();
	
	static Image santa;
	static Image [] imagenes;
	
	static
	{
		santa = CromosomaPaquete.principal.darImagen("santa-claus.jpg");
		String [] direccion = {"tren.jpg", "oso.jpg", "libro.jpg", "conejita.jpg", "xbox.jpg", "portatil.jpg", "bicicleta.jpg", "elefante.jpg"};
		imagenes = new Image[direccion.length];
		for(int i = 0; i < direccion.length; i++)
		{
			imagenes[i] = CromosomaPaquete.principal.darImagen(direccion[i]);
		}
	}
	
    
	public Grafico() 
	{
		setSize(new Dimension(1200,760));	
	}
	
	@Override
	public synchronized void paint(Graphics g){
		ArrayList <Rectangle2D> figuras = new ArrayList <Rectangle2D> ();
		g.drawImage(santa, 0, 0, 1200, 760, 0, 0, 1900, 1200, this);
		int pt=0;
		int ut=0;
		int i=0;
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
					g.drawImage(imagenes[i], puntoEscritura.x, puntoEscritura.y, puntoEscritura.x + 50, puntoEscritura.y + 50, 0, 0, 130, 130, this);
					pt+=pesos[i];
					ut+=utilidades[i];
					
				}
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
		Ellipse2D.Double elipse = new Ellipse2D.Double(103, 30, 800, 480);
		return elipse.contains(px, py);
	}  
}

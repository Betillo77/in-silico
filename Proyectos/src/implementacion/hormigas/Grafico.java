package implementacion.hormigas;
import genericos.hormigas.Hormigas;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Grafico extends JFrame
{
	private static final long serialVersionUID = 1L;
	public ArrayList <Integer> mejoresActual = new ArrayList <Integer> ();
	public double solucionActual = 0;
	public double iteracionActual = 0;
	public boolean soloNumero = true;
	public ArrayList <Ciudad> ciudades;
	public Grafico()
	{
		setSize(new Dimension(500, 500));
	}
	
	@Override
	public synchronized void paint(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 500, 500);
		g.setColor(Color.BLACK);
		for(Ciudad a : ciudades)
		{
			g.fillOval(a.x + 20, a.y + 20, 6, 6);
		}
		g.drawString((int)solucionActual  + "", 460, 460);
		try 
		{
			g.drawImage(ImageIO.read(new File("src/implementacion/hormigas/hormiga.gif")), 100, 100, null);
			Thread.sleep(500);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 500, 500);
			g.setColor(Color.BLACK);
			for(Ciudad a : ciudades)
			{
				g.fillOval(a.x + 20, a.y + 20, 6, 6);
			}
			g.drawString((int)solucionActual  + "", 460, 460);
			Thread.sleep(500);
			g.drawImage(ImageIO.read(new File("src/implementacion/hormigas/hormiga.gif")), 100, 100, null);
			Thread.sleep(500);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 500, 500);
			g.setColor(Color.BLACK);
			for(Ciudad a : ciudades)
			{
				g.fillOval(a.x + 20, a.y + 20, 6, 6);
			}
			g.drawString((int)solucionActual  + "", 460, 460);
		} 
		catch (IOException e1) 
		{
		} 
		catch (InterruptedException e) 
		{
		}
		for(int i = 0; i < mejoresActual.size() - 1; i++)
		{
			if(i != 0)
				g.drawLine(ciudades.get(mejoresActual.get(i - 1)).x + 23, ciudades.get(mejoresActual.get(i - 1)).y + 23,ciudades.get(mejoresActual.get(i)).x + 23, ciudades.get(mejoresActual.get(i)).y + 23);
			g.setColor(Color.RED);
			g.drawLine(ciudades.get(mejoresActual.get(i)).x + 23, ciudades.get(mejoresActual.get(i)).y + 23,ciudades.get(mejoresActual.get(i + 1)).x + 23, ciudades.get(mejoresActual.get(i + 1)).y + 23);
			g.setColor(Color.BLACK);
			try 
			{
				Thread.sleep(150);
			}
			catch (InterruptedException e) 
			{
			}
		}
		if(mejoresActual.size() > 1)
			g.drawLine(ciudades.get(mejoresActual.get(mejoresActual.size() - 2)).x + 23, ciudades.get(mejoresActual.get(mejoresActual.size() - 2)).y + 23,ciudades.get(mejoresActual.get(mejoresActual.size() - 1)).x + 23, ciudades.get(mejoresActual.get(mejoresActual.size() - 1)).y + 23);
	}
	static double mejor;
}
